package jp.go.aist.rtm.systemeditor.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.systemeditor.ui.util.ProfileHandler;
import jp.go.aist.rtm.toolscommon.factory.MappingRuleFactory;
import jp.go.aist.rtm.toolscommon.model.component.Connector;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagramKind;
import jp.go.aist.rtm.toolscommon.model.core.WrapperObject;
import jp.go.aist.rtm.toolscommon.profiles.util.XmlHandler;
import jp.go.aist.rtm.toolscommon.synchronizationframework.SynchronizationManager;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.openrtp.namespaces.rts.RtsProfile;

public class SystemEditorWrapperFactory {

	private static SystemEditorWrapperFactory __instance = null;

	private SynchronizationManager synchronizationManager;

	/**
	 * コンストラクタ
	 * <p>
	 * 他のマッピングルールを使用したファクトリを作成することができるようにコンストラクタを公開するが、基本的にはgetInstance()を利用してシングルトンを作成すること
	 * 
	 * @param mappingRules
	 */
	public SystemEditorWrapperFactory(MappingRule[] mappingRules) {
		synchronizationManager = new SynchronizationManager(mappingRules);
	}

	/**
	 * ファクトリのシングルトンを取得する
	 * 
	 * @return ファクトリのシングルトン
	 */
	public static SystemEditorWrapperFactory getInstance() {
		if (__instance == null) {
			__instance = new SystemEditorWrapperFactory(MappingRuleFactory
					.getMappingRule());
		}

		return __instance;
	}

	/**
	 * シングルトンをセットする。（基本的に使用してはならない。コマンドラインからの実行のために追加）
	 * 
	 */
	public static void setInstance(SystemEditorWrapperFactory instance) {
		__instance = instance;
	}

	/**
	 * XMLからドメインオブジェクトツリーを復元する
	 * <p>
	 * 内部では、EMFのオブジェクトをロードし、同期フレームワークのオブジェクトの復元を行う
	 * 
	 * @param resource
	 *            リソース
	 * @return ドメインオブジェクトルート
	 * @throws IOException
	 *             ファイルが読み込めない場合など
	 */
	public EObject loadContentFromResource(Resource resource)
			throws IOException {
		resource.load(Collections.EMPTY_MAP);
		EObject object = (EObject) resource.getContents().get(0);
		getSynchronizationManager().assignSynchonizationSupport(object);

		Rehabilitation.rehabilitation(object);

		return object;
	}

	/**
	 * XMLにオブジェクトツリーを保存する
	 * <p>
	 * 内部では、EMFのオブジェクトをセーブする。 同期フレームワークのオブジェクトはセーブされないので、ロード時に復元を行う必要がある。
	 * 
	 * @param resource
	 *            リソース
	 * @param content
	 *            ドメインオブジェクトルート
	 * @throws IOException
	 *             ファイルに保存できない場合など
	 */
	public void saveContentsToResource(Resource resource, EObject content)
			throws Exception {
		if( ((SystemDiagram)content).getKind() == SystemDiagramKind.OFFLINE_LITERAL ) {
			ProfileHandler handler = new ProfileHandler();
			RtsProfile profile = handler.convertToProfile(content);
			XmlHandler xmlHandler = new XmlHandler();
			String targetFileName =	resource.getURI().devicePath();
			xmlHandler.saveXmlRts(profile, targetFileName);
		} else {
			resource.getContents().add(content);
			HashMap options = new HashMap();
			// options.put(XMLResource.OPTION_ENCODING, "ISO-8859-1");
			options.put(XMLResource.OPTION_ENCODING, "UTF-8");
			resource.save(options);
		}
	}

	/**
	 * 対象のオブジェクトをコピーします
	 * 
	 * @param component
	 * @return
	 */
	public WrapperObject copy(EObject obj) {
		WrapperObject copy = (WrapperObject) EcoreUtil.copy(obj);
		List<Connector> connectors = new ArrayList<Connector>();
		for (Iterator iter = copy.eAllContents(); iter.hasNext();) {
			Object e = iter.next();
			if (e instanceof Connector) {
				connectors.add((Connector) e);
			}
		}
		for (Connector connector : connectors) {
			EcoreUtil.remove(connector);
		}
		getSynchronizationManager().assignSynchonizationSupport(copy);

		return copy;
	}

	/**
	 * リモートオブジェクトを渡し、ドメインオブジェクトを作成する
	 * 
	 * @param remoteObject
	 *            リモートオブジェクト
	 * @return 作成されたドメインオブジェクト
	 */
	public WrapperObject createWrapperObject(Object... remoteObjects) {
		return (WrapperObject) synchronizationManager
				.createLocalObject(remoteObjects);
	}

	/**
	 * SynchronizationManagerを取得する
	 */
	public SynchronizationManager getSynchronizationManager() {
		return synchronizationManager;
	}
}
