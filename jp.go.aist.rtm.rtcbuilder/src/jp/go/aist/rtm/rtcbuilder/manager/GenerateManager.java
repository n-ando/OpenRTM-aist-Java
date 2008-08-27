package jp.go.aist.rtm.rtcbuilder.manager;

import java.util.List;

import jp.go.aist.rtm.rtcbuilder.generator.GeneratedResult;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;

public abstract class GenerateManager {

	public abstract List<GeneratedResult> generateTemplateCode(RtcParam rtcParam);
	public abstract String getManagerKey();

}
