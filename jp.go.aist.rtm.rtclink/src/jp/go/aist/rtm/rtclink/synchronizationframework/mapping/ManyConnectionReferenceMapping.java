package jp.go.aist.rtm.rtclink.synchronizationframework.mapping;

import org.eclipse.emf.ecore.EReference;

/**
 * 多参照コネクションのマッピングを定義するためのクラス
 */
public abstract class ManyConnectionReferenceMapping extends
		ManyReferenceMapping {

	public ManyConnectionReferenceMapping(EReference localFeature) {
		super(localFeature);
	}

}
