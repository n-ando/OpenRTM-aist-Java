package jp.go.aist.rtm.toolscommon.synchronizationframework.mapping;

import jp.go.aist.rtm.toolscommon.synchronizationframework.LocalObject;

/**
 * クラスのマッピングを定義するためのクラス
 * <p>
 * ConstructorParamMappingsにマップ可能なリモートオブジェクトと対応する
 */
public class ClassMapping {
	private Class localClass;

	private ConstructorParamMapping[] constructorParamMappings;

	private boolean allowZombie;

	/**
	 * コンストラクタ
	 * 
	 * @param localClass
	 *            ローカルオブジェクトのクラス
	 * @param remoteClass
	 *            リモートオブジェクトのクラス
	 * @param allowZombie ゾンビ（リモートオブジェクトが死んだ状態）でも存在させるか
	 * 			
	 */
	public ClassMapping(Class localClass,
			ConstructorParamMapping[] constructorParamMappings,boolean allowZombie) {
		this.localClass = localClass;
		this.constructorParamMappings = constructorParamMappings;
		this.allowZombie = allowZombie;
	}

	/**
	 * コンストラクタ
	 * 
	 * @param localClass
	 *            ローカルオブジェクトのクラス
	 * @param remoteClass
	 *            リモートオブジェクトのクラス
	 */
	public ClassMapping(Class localClass,
			ConstructorParamMapping[] constructorParamMappings) {
		this(localClass,constructorParamMappings,false);
	}

	/**
	 * このクラスマッピングが、リモートオブジェクトに関連づいたマッピングであるかどうか
	 * <p>
	 * リモートオブジェクトと親ローカルオブジェクト、およびリンクから、 このクラスマッピングが関連づいたマッピングであるかどうか判断する。<br>
	 * CORBAオブジェクトの場合、narrowして比較する必要があるため、オーバーライドすること。
	 * 
	 * @param parent
	 *            親のローカルオブジェクト
	 * @param remoteObjects
	 * @param link
	 *            関連をあらわすリンク(リモートオブジェクト自体であることが多い)
	 * @return
	 */
	public boolean isTarget(LocalObject parent, Object[] remoteObjects,
			java.lang.Object link) {
		boolean result = true;
		for (int i = 0; i < remoteObjects.length; i++) {
			if (constructorParamMappings[i].getTargetClass().isAssignableFrom(
					remoteObjects[i].getClass()) == false) {
				result = false;
			}
		}

		return result;
	}

	/**
	 * リモートオブジェクトから、ローカルオブジェクトを作成する。
	 * 
	 * @param parent
	 *            親のローカルオブジェクト
	 * @param remoteObjects
	 *            リモートオブジェクト
	 * @param link
	 *            関連をあらわすリンク(リモートオブジェクト自体であることが多い)
	 * @return
	 */
	public LocalObject createLocalObject(LocalObject parent,
			Object[] remoteObjects, java.lang.Object link) {
		try {
			LocalObject result = (LocalObject) localClass.newInstance();
			for (int i = 0; i < constructorParamMappings.length; ++i) {
				result.eSet(constructorParamMappings[i].getFeature(),
						remoteObjects[i]);
			}

			return result;
		} catch (InstantiationException e) {
			throw new RuntimeException(e); // system error
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e); // system error
		}
	}

	/**
	 * ローカルオブジェクトのクラス
	 * 
	 * @return ローカルオブジェクトのクラス
	 */
	public Class getLocalClass() {
		return localClass;
	}

	/**
	 * narrowする
	 * 
	 * @param remoteObjects
	 * @return
	 */
	public Object[] narrow(Object[] remoteObjects) {
		return remoteObjects;
	}

	/**
	 * ConstructorParamMappingを取得する
	 * 
	 * @return
	 */
	public ConstructorParamMapping[] getConstructorParamMappings() {
		return constructorParamMappings;
	}

	/**
	 * ゾンビ（リモートオブジェクトが死んだ状態）でも存在させるか
	 * @return
	 */
	public boolean allowZombie() {
		return allowZombie;
	}

}
