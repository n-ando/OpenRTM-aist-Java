package jp.go.aist.rtm.toolscommon.synchronizationframework.mapping;

import org.eclipse.emf.ecore.EStructuralFeature;


/**
 * コンストラクタの引数を表すクラス
 * <p>
 * コンストラクタといいながら、実際にはDIスタイルでセッタを使用してセットする。
 * またセッタを設定すればtargetClassはセッタの型から自動的に判断する。
 */
public class ConstructorParamMapping {
	private EStructuralFeature feature;

	private Class targetClass;

	public ConstructorParamMapping(EStructuralFeature feature) {
		this.feature = feature;
	}

	public ConstructorParamMapping(Class targetClass, EStructuralFeature feature) {
		this.feature = feature;
		this.targetClass = targetClass;
	}

	public EStructuralFeature getFeature() {
		return feature;
	}

	public void setFeature(EStructuralFeature feature) {
		this.feature = feature;
	}

	public Class getTargetClass() {
		Class result = targetClass;
		if (result == null) {
			result = feature.getEType().getInstanceClass();
		}
		
		return result;
	}
}
