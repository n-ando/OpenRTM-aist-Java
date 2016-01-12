package jp.go.aist.rtm.RTC;

import OpenRTM.Logger;
import OpenRTM.LoggerHelper;

import _SDOPackage.NVListHolder;

import jp.go.aist.rtm.RTC.port.CorbaConsumer;
import jp.go.aist.rtm.RTC.util.CallbackFunction;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.Properties;
  /**
   * 
   * 
   * 
   */
public class LoggerConsumer implements SdoServiceConsumerBase, CallbackFunction, ObjectCreator<SdoServiceConsumerBase>, ObjectDestructor{
    /**
     * {@.ja ctor of LoggerConsumer}
     * {@.en ctor of LoggerConsumer}
     */
    public LoggerConsumer(){
        m_rtobj = null;
    }


    /**
     * {@.ja 初期化}
     * {@.en Initialization}
     */
    public boolean init(RTObject_impl rtobj,
                      final _SDOPackage.ServiceProfile profile){

        if (!m_observer.setObject(LoggerHelper.narrow(profile.service))) {
          // narrowing failed
          return false;
        }

        m_rtobj = rtobj;
        m_profile = profile;
        //
        NVListHolder nvholder = 
                new NVListHolder(profile.properties);
        Properties prop = new Properties();
        NVUtil.copyToProperties(prop, nvholder);
        return true;
    }

    /**
     * {@.ja 再初期化}
     * {@.en Re-initialization}
     */
    public boolean reinit(final _SDOPackage.ServiceProfile profile){
        if (!m_observer._ptr()._is_equivalent(profile.service)) {
            CorbaConsumer<Logger> tmp = new CorbaConsumer<Logger>();
            if (!tmp.setObject(profile.service)) {
                return false;
            }
            m_observer.releaseObject();
            m_observer.setObject(profile.service);
        }
        m_profile= profile;
        //
        NVListHolder nvholder = 
                new NVListHolder(profile.properties);
        Properties prop = new Properties();
        NVUtil.copyToProperties(prop, nvholder);
        return true;
    }

    /**
     * {@.ja ServiceProfile を取得する}
     * {@.en getting ServiceProfile}
     */
    public final _SDOPackage.ServiceProfile getProfile() {
        return m_profile;
    }
    
    /**
     * {@.ja 終了処理}
     * {@.en Finalization}
     */
    public void finalize(){
    }
    //============================================================
    // Heartbeat related functions
    /**
     * {@.ja }
     * {@.en }
     */
    public void doOperate(){
    }

    /**
     * <p> creator_ </p>
     * 
     * @return Object Created instances
     *
     */
    public SdoServiceConsumerBase creator_() {
        return new ComponentObserverConsumer();
    }
    /**
     * <p> destructor_ </p>
     * 
     * @param obj    The target instances for destruction
     *
     */
    public void destructor_(Object obj) {
        obj = null;
    }
    private RTObject_impl m_rtobj;
    private _SDOPackage.ServiceProfile m_profile;
    private CorbaConsumer<OpenRTM.Logger> m_observer =
            new CorbaConsumer<OpenRTM.Logger>(OpenRTM.Logger.class);

};

