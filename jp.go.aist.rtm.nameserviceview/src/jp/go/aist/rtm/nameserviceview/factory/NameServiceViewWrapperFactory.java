package jp.go.aist.rtm.nameserviceview.factory;

import jp.go.aist.rtm.nameserviceview.model.nameservice.NameServerNamingContext;
import jp.go.aist.rtm.nameserviceview.model.nameservice.NameServiceReference;
import jp.go.aist.rtm.nameserviceview.model.nameservice.impl.NameServiceReferenceImpl;
import jp.go.aist.rtm.toolscommon.factory.MappingRuleFactory;
import jp.go.aist.rtm.toolscommon.model.core.WrapperObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.SynchronizationManager;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;

import org.omg.CosNaming.Binding;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContextExt;

/**
 * RtcLinkの内部で使用されるドメインオブジェクトを作成するファクトリ
 * <p>
 * 内部では、同期フレームワークを使用している。
 */
public class NameServiceViewWrapperFactory {

    private static NameServiceViewWrapperFactory __instance = null;
    
    private SynchronizationManager synchronizationManager;

    /**
     * コンストラクタ
     * <p>
     * 他のマッピングルールを使用したファクトリを作成することができるようにコンストラクタを公開するが、基本的にはgetInstance()を利用してシングルトンを作成すること
     * 
     * @param mappingRules
     */
    public NameServiceViewWrapperFactory(MappingRule[] mappingRules) {
    	 synchronizationManager = new SynchronizationManager(mappingRules);
    }

    /**
     * ファクトリのシングルトンを取得する
     * 
     * @return ファクトリのシングルトン
     */
    public static NameServiceViewWrapperFactory getInstance() {
        if (__instance == null) {
            __instance = new NameServiceViewWrapperFactory(MappingRuleFactory
                    .getMappingRule());
        }

        return __instance;
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
