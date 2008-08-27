package jp.go.aist.rtm.rtcbuilder.generator.param;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * ジェネレータの引数となるクラス
 */
public class GeneratorParam implements Serializable {

	List<RtcParam> rtcParams = new ArrayList<RtcParam>();

	public List<RtcParam> getRtcParams() {
		return rtcParams;
	}
}
