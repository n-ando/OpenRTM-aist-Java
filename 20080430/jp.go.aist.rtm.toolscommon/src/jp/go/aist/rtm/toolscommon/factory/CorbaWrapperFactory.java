package jp.go.aist.rtm.toolscommon.factory;

import jp.go.aist.rtm.toolscommon.model.core.WrapperObject;
import jp.go.aist.rtm.toolscommon.synchronizationframework.SynchronizationManager;
import jp.go.aist.rtm.toolscommon.synchronizationframework.mapping.MappingRule;

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
	 * シングルトンをセットする。（基本的に使用してはならない。コマンドラインからの実行のために追加）
	 * 
	 */
	public static void setInstance(CorbaWrapperFactory instance) {
		__instance = instance;
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
     * SynchronizationManagerを取得する
     */
    public SynchronizationManager getSynchronizationManager() {
        return synchronizationManager;
    }
}
