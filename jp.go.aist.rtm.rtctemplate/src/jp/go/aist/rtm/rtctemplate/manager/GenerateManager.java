package jp.go.aist.rtm.rtctemplate.manager;

import java.util.List;

import jp.go.aist.rtm.rtctemplate.generator.GeneratedResult;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;

public interface GenerateManager {

	public List<GeneratedResult> doGenerate(GeneratorParam generatorParam);

}
