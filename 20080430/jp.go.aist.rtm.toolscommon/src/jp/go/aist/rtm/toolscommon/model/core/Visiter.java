package jp.go.aist.rtm.toolscommon.model.core;

/**
 * Visiterパターンを使用して、RTCLinkのオブジェクトを走査するためのクラス
 */
public interface Visiter {
	void visit(ModelElement element);
}
