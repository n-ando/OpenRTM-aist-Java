package RTMExamples.ConfigSample;

import RTMExamples.ConfigSample.ConfigSampleImpl;
import RTMExamples.ConfigSample.VectorHolder;
import jp.go.aist.rtm.RTC.util.DoubleHolder;
import jp.go.aist.rtm.RTC.util.IntegerHolder;
import jp.go.aist.rtm.RTC.util.StringHolder;

public class ConfigSampleStub {
    private ConfigSampleImpl m_config;
    
    public ConfigSampleStub(ConfigSampleImpl config) {
        m_config = config;
    }
    public IntegerHolder get_int_param0() {
        return m_config.m_int_param0;
    }
    public IntegerHolder get_int_param1() {
        return m_config.m_int_param1;
    }
    public DoubleHolder get_double_param0() {
        return m_config.m_double_param0;
    }
    public DoubleHolder get_double_param1() {
        return m_config.m_double_param1;
    }
    public StringHolder get_str_param0() {
        return m_config.m_str_param0;
    }
    public StringHolder get_str_param1() {
        return m_config.m_str_param1;
    }
    public VectorHolder get_vector_param0() {
        return m_config.m_vector_param0;
    }
}
