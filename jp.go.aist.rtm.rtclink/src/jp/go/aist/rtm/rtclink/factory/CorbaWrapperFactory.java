package jp.go.aist.rtm.rtclink.factory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.rtclink.model.component.Connector;
import jp.go.aist.rtm.rtclink.model.core.WrapperObject;
import jp.go.aist.rtm.rtclink.model.nameservice.NameServerNamingContext;
import jp.go.aist.rtm.rtclink.model.nameservice.NameServiceReference;
import jp.go.aist.rtm.rtclink.model.nameservice.impl.NameServiceReferenceImpl;
import jp.go.aist.rtm.rtclink.synchronizationframework.SynchronizationManager;
import jp.go.aist.rtm.rtclink.synchronizationframework.mapping.MappingRule;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;

/**
 * RtcLinkの内部で使用されるドメインオブジェクトを作成するファクトリ
 * <p>
 * 内部では、同期フレームワークを使用している。
 */
public class CorbaWrapperFactory {

    private static CorbaWrapperFactory __instance = null;

    private SynchronizationManager synchronizationManager;

    /**
     * コンストラクタ
     * <p>
     * 他のマッピングルールを使用したファクトリを作成することができるようにコンストラクタを公開するが、基本的にはgetInstance()を利用してシングルトンを作成すること
     * 
     * @param mappingRules
     */
    public CorbaWrapperFactory(MappingRule[] mappingRules) {
        synchronizationManager = new SynchronizationManager(mappingRules);
    }

    /**
     * ファクトリのシングルトンを取得する
     * 
     * @return ファクトリのシングルトン
     */
    public static CorbaWrapperFactory getInstance() {
        if (__instance == null) {
            __instance = new CorbaWrapperFactory(MappingRuleFactory
                    .getMappingRule());
        }

        return __instance;
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
     * ネームコンテクストオブジェクトとネームサーバ名から、ネームサーバのドメインオブジェクトを作成する
     * 
     * @param namingContext
     *            ネーミングコンテクスト
     * @param nameServerName
     *            ネームサーバ名
     * @return ネームサーバのドメインオブジェクト
     */
    public NameServerNamingContext getNameServiceContextCorbaWrapper(
            NamingContextExt namingContext, String nameServerName) {

        NameServiceReference nameServiceReference = new NameServiceReferenceImpl();
        nameServiceReference.setNameServerName(nameServerName);
        Binding binding = new Binding();
        binding.binding_name = new NameComponent[] {};
        nameServiceReference.setBinding(binding);
        nameServiceReference.setRootNamingContext(namingContext);

        NameServerNamingContext result = (NameServerNamingContext) createWrapperObject(
                namingContext, nameServiceReference);

        return result;
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
        synchronizationManager.assignSynchonizationSupport(object);

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
            throws IOException {
        resource.getContents().add(content);

        resource.save(Collections.EMPTY_MAP);
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

        synchronizationManager.assignSynchonizationSupport(copy);

        return copy;
    }

    /**
     * SynchronizationManagerを取得する
     */
    public SynchronizationManager getSynchronizationManager() {
        return synchronizationManager;
    }
}
