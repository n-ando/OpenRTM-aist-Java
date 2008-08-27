package jp.go.aist.rtm.systemeditor.restoration;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.go.aist.rtm.nameserviceview.model.nameservice.NameservicePackage;
import jp.go.aist.rtm.systemeditor.factory.SystemEditorWrapperFactory;
import jp.go.aist.rtm.toolscommon.factory.CorbaWrapperFactory;
import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;
import jp.go.aist.rtm.toolscommon.model.core.CorePackage;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceImpl;

public class Main {

	/**
	 * 指定されたXMLファイルを読み込み、XMLファイルの内容をシステムに反映する。
	 * XMLファイルの場所は、ファイルパスもしくはURI(Webサーバ等可能)で指定する。
	 * <p>
	 * [重要]マッピングルールは、実行ファイル直下に、別途作成したファイル(MAPPING_RULES)に指定する
	 * 
	 * コンソールからRtcLinkのXMLファイルを読み込み、XMLファイルの内容に沿って以下を行う。
	 * <LI>１．RtcLinkのXMLに含まれるすべてのRTCにアクセス可能であるか確認する。</LI>
	 * <LI>２．RtcLinkのXMLに含まれるすべてのコンフィグレーション情報を復元する</LI>
	 * <LI>３．RtcLinkのXMLに含まれるすべてのコネクションを接続する</LI>
	 * <LI>４・RtcLinkのXMLに含まれるすべてのRTCに対して、Start要求を送信する。</LI>
	 * 
	 * ３．では、既に同じコネクションIDが存在すれば、接続は行われない <br>
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		Result result = new Result() {
			private boolean success = true;

			public void putResult(String resultPart) {
				System.out.println(resultPart);
			}

			public boolean isSuccess() {
				return success;
			}

			public void setSuccess(boolean success) {
				this.success = success;
			}
		};

		if (args.length < 1) {
			System.out.println("XMLファイルのファイルパス、またはURIを指定してください。");
			result.setSuccess(false);
		}

		URI xmlUri = null;
		if (result.isSuccess()) {
			try {
				xmlUri = URI.createFileURI(args[0]);
			} catch (RuntimeException e) {
				// void
			}
			if (xmlUri == null) {
				xmlUri = URI.createURI(args[0]);
			}

			if (xmlUri == null) {
				result.putResult("XMLファイルが見つかりません。");
				result.setSuccess(false);
				return;
			}
		}

		if (result.isSuccess()) {
			execute(xmlUri, result);
		}

		if (result.isSuccess()) {
			result.putResult("[結果] 成功しました。\r\n");
		} else {
			result.putResult("[結果] 失敗しました。\r\n");
		}

	}

	/**
	 * 実行メイン
	 * 
	 * @param xmlUri
	 * @param result
	 */
	public static void execute(URI xmlUri, Result result) {
		SystemDiagram systemDiagram = loadFile(xmlUri, result);
		if (result.isSuccess()) {
			Restoration.execute(systemDiagram, result);
		}
	}

	public static SystemDiagram loadFile(URI xmlUri, Result result) {
		CorePackage.eINSTANCE.getEFactoryInstance();
		NameservicePackage.eINSTANCE.getEFactoryInstance();
		ComponentPackage.eINSTANCE.getEFactoryInstance();

//		Resource resource = new XMIResourceImpl(xmlUri);
		Resource resource = new XMLResourceImpl(xmlUri);
		try {
			resource.load(Collections.EMPTY_MAP);
		} catch (MalformedURLException e) {
			result.putResult("解決できないURIです[ " + xmlUri.toString() + " ]");
			result.setSuccess(false);
			return null;
		} catch (FileNotFoundException e) {
			result.putResult("XMLファイルが見つかりませんでした[ " + xmlUri.toString()
					+ " ]");
			result.setSuccess(false);
			return null;
		} catch (IOException e) {
			result.putResult("XMLファイルの読み込みに失敗しました。\r\n"
					+ "System Diagram以外のファイルが読み込まれていないか確認してください。");
			result.setSuccess(false);
			return null;
		}

		MappingRule[] mappingRule = getMappingRuleFromPropertyFile(result);

		SystemEditorWrapperFactory systemEditorWrapperFactory = new SystemEditorWrapperFactory(
				mappingRule);
		SystemEditorWrapperFactory.setInstance(systemEditorWrapperFactory);
		CorbaWrapperFactory corbaWrapperFactory = new CorbaWrapperFactory(mappingRule);
		CorbaWrapperFactory.setInstance(corbaWrapperFactory);
		SystemDiagram systemDiagram = null;
		try {
			systemDiagram = (SystemDiagram) systemEditorWrapperFactory
					.loadContentFromResource(resource);
		} catch (IOException e) {
			throw new RuntimeException(); // system error
		}

		return systemDiagram;
	}

	/**
	 * ファイルからマッピングルールを作成する
	 * 
	 * @return
	 * @throws ClassNotFoundException
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 */
	private static MappingRule[] getMappingRuleFromPropertyFile(Result result) {

		List<MappingRule> mappingRule = new ArrayList<MappingRule>();

		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream("./MAPPING_RULES")));

			while (reader.ready()) {
				String value = reader.readLine();

				int lastIndexOf = value.lastIndexOf(".");

				ClassLoader classLoader = Main.class.getClassLoader();
				if (classLoader == null) {

					classLoader = ClassLoader.getSystemClassLoader();

				}

				Class clazz = classLoader.loadClass(value.substring(0,
						lastIndexOf));
				Field field = clazz.getDeclaredField(value
						.substring(lastIndexOf + ".".length()));

				mappingRule.add((MappingRule) field.get(clazz.newInstance()));
			}

			reader.close();

		} catch (Exception e) {
			e.printStackTrace();
			result.setSuccess(false);
		}

		return mappingRule.toArray(new MappingRule[mappingRule.size()]);
	}
}
