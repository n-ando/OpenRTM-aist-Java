package jp.go.aist.rtm.RTC.executionContext;

import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.equalFunctor;
import jp.go.aist.rtm.RTC.util.NVUtil;
import jp.go.aist.rtm.RTC.util.Properties;
import jp.go.aist.rtm.RTC.util.TimeValue;
import RTC.ExecutionContextService;
//import RTC.ExecutionContextProfile;
import RTC.ExecutionKind;
import RTC.ExecutionKindHolder;
import RTC.LightweightRTObject;
import RTC.ReturnCode_t;
import RTC.RTCListHolder;
import RTC.RTObject;
import RTC.RTObjectHelper;
import _SDOPackage.NVListHolder;
  /**
   * {@.ja ExecutionContextProfile クラス}
   * {@.en ExecutionContextProfile class}
   * <p>
   * {@.ja Periodic Sampled Data Processing(周期実行用)ExecutionContextクラス。}
   * {@.en Periodic Sampled Data Processing (for the execution cycles)
   * ExecutionContext class}
   */
public class ExecutionContextProfile {
    public static final double DEEFAULT_PERIOD = 0.000001;

    /**
     * {@.ja デフォルトコンストラクタ}
     * {@.en Default Constructor}
     * <p>
     * {@.ja デフォルトコンストラクタ
     * プロファイルに以下の項目を設定する。
     *  - kind : PERIODIC
     *  - rate : 0.0}
     * {@.en Default Constructor
     * Set the following items to profile.
     *  - kind : PERIODIC
     *  - rate : 0.0}
     */
    public ExecutionContextProfile(ExecutionKind kind) {
        rtcout = new Logbuf("periodic_ecprofile");
        m_period = new TimeValue(DEEFAULT_PERIOD);
        m_ref = null;
        rtcout.println(Logbuf.TRACE, "ExecutionContextProfile()");
        rtcout.println(Logbuf.DEBUG, "Actual rate: "
                                    + m_period.sec()
                                    + " [sec], "
                                    + m_period.usec()
                                    + " [usec]");
        // profile initialization
        m_profile.kind = kind;
        m_profile.rate = 1.0 / m_period.toDouble();
        m_profile.owner = null;
        m_profile.participants = new RTObject[0];
        m_profile.properties = new _SDOPackage.NameValue[0];
    }
    public ExecutionContextProfile(){
        this(ExecutionKind.PERIODIC);
    }
    /**
     * {@.ja CORBA オブジェクトの設定}
     * {@.en Sets the reference to the CORBA object}
     * <p>
     * {@.ja ExecutioncontextService の CORBA オブジェクト参照をセットする。
     * セットされると、それまでセットされていたオブジェクト参照は
     * releaseされる。セットするオブジェクト参照は有効な参照でなければ
     * ならない。}
     * {@.en This operation sets a object reference to
     * ExecutionContextService.  After setting a new object reference,
     * old reference is released.  The object reference have to be
     * valid reference.}
     *
     * @param ec_ptr 
     *   {@.ja ExecutionContextServiceのCORBAオブジェクト}
     *   {@.en A CORBA object reference of ExecutionContextService}
     */
    public void setObjRef(ExecutionContextService ec_ptr){
        rtcout.println(Logbuf.TRACE, "setObjRef()");
        synchronized (m_profile){
            m_ref = (ExecutionContextService)ec_ptr._duplicate();
        }
    }

    /**
     * {@.ja CORBA オブジェクト参照の取得}
     * {@.en Get the reference to the CORBA object}
     * <p>
     * {@.ja 本オブジェクトの ExecutioncontextService としての CORBA オブジェ
     * クト参照を取得する。}
     * {@.en Get the reference to the CORBA object as
     * ExecutioncontextService of this object.}
     *
     * @return 
     *   {@.ja CORBA オブジェクト参照}
     *   {@.en The reference to CORBA object}
     *
     */
    public ExecutionContextService getObjRef(){
        rtcout.println(Logbuf.TRACE, "getObjRef()");
        synchronized (m_profile){
            return (ExecutionContextService)m_ref._duplicate();
        }
    }

    /**
     * {@.ja ExecutionContext の実行周期(Hz)を設定する}
     * {@.en Set execution rate(Hz) of ExecutionContext}
     * <p>
     * {@.ja Active 状態にてRTコンポーネントが実行される周期(単位:Hz)を設定す
     * る。実行周期の変更は、DataFlowComponentAction の
     * on_rate_changed によって各RTコンポーネントに伝達される。}
     * {@.en This operation shall set the rate (in hertz) at which this
     * context’s Active participating RTCs are being called.  If the
     * execution kind of the context is PERIODIC, a rate change shall
     * result in the invocation of on_rate_changed on any RTCs
     * realizing DataFlowComponentAction that are registered with any
     * RTCs participating in the context.}
     *
     * @param rate 
     *   {@.ja 処理周期(単位:Hz)}
     *   {@.en Execution cycle(Unit:Hz)}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード
     *         RTC_OK: 正常終了
     *         BAD_PARAMETER: 設定値が負の値}
     *   {@.en The return code of ReturnCode_t type
     *         RTC_OK: Succeed
     *         BAD_PARAMETER: Invalid value. The value might be negative.}
     *
     */
    public ReturnCode_t setRate(double rate) {
        rtcout.println(Logbuf.TRACE, "setRate("+rate+")");
        if (rate < 0.0) { 
            return ReturnCode_t.BAD_PARAMETER; 
        }

        synchronized (m_profile){
            m_profile.rate = rate;
            m_period = new TimeValue(1.0/rate);
            return ReturnCode_t.RTC_OK;
        }
    }
    public ReturnCode_t setPeriod(double period) {
        rtcout.println(Logbuf.TRACE, "setPeriod("+period+" [sec])");
        if (period < 0.0) { 
            return ReturnCode_t.BAD_PARAMETER; 
        }

        synchronized (m_profile){
            m_profile.rate = 1.0 / period;
            m_period = new TimeValue(period);
            return ReturnCode_t.RTC_OK;
        }
    }
    public ReturnCode_t setPeriod(TimeValue period) {
        rtcout.println(Logbuf.TRACE, "setPeriod("+period.toDouble()+" [sec])");
        if (period.toDouble() < 0.0) { 
            return ReturnCode_t.BAD_PARAMETER; 
        }

        synchronized (m_profile){
            m_profile.rate = 1.0 / period.toDouble();
            m_period = period;
            return ReturnCode_t.RTC_OK;
        }
    }

    /**
     * {@.ja ExecutionContext の実行周期(Hz)を取得する}
     * {@.en Get execution rate(Hz) of ExecutionContext}
     * <p>
     * {@.ja Active 状態にてRTコンポーネントが実行される周期(単位:Hz)を取得す
     * る。}
     * {@.en This operation shall return the rate (in hertz) at which its
     * Active participating RTCs are being invoked.}
     *
     * @return 
     *   {@.ja 処理周期(単位:Hz)}
     *   {@.en Execution cycle(Unit:Hz)}
     *
     */
    public double getRate() {
        synchronized (m_profile){
            return m_profile.rate;
        }
    }
    public TimeValue getPeriod(){
        synchronized (m_profile){
            return m_period;
        }
    }

    /**
     * {@.ja ExecutionKind を文字列化する}
     * {@.en Converting ExecutionKind enum to string }
     * <p>
     * {@.ja ExecutionKind で定義されている PERIODIC, EVENT_DRIVEN,
     * OTHER を文字列化する。}
     * {@.en This function converts enumeration (PERIODIC, EVENT_DRIVEN,
     * OTHER) defined in RTC::ExecutionKind to string.}
     *
     * @param kind 
     *   {@.ja ExecutionKind}
     *   {@.en ExecutionKind}
     * @return 
     *   {@.en 文字列化されたExecutionKind}
     *   {@.en String of ExecutionKind}
     *
     */
    public final String getKindString(ExecutionKind kind){
        ExecutionKindHolder ekh = new ExecutionKindHolder(kind);
        try {
            return ekh._type().name();
        }
        catch(Exception ex) {
            return "";
        }
    }
    public final String getKindString() {
      //return getKindString(m_profile.kind);
        String str = new String();
        synchronized (m_profile){
            str = getKindString(m_profile.kind);
        }
      return str;
    }

    /**
     * {@.ja ExecutionKind を設定する}
     * {@.en Set the ExecutionKind}
     * <p>
     * {@.ja この ExecutionContext の ExecutionKind を設定する}
     * {@.en This operation sets the kind of the execution context.}
     *
     * @param kind 
     *   {@.ja ExecutionKind}
     *   {@.en ExecutionKind}
     *
     */
    public ReturnCode_t setKind(ExecutionKind kind) {
        rtcout.println(Logbuf.TRACE, "setKind("+getKindString(kind)+")");
        synchronized (m_profile){
            m_profile.kind = kind;
            return ReturnCode_t.RTC_OK;
        }
    }

    /**
     * {@.ja ExecutionKind を取得する}
     * {@.en Get the ExecutionKind}
     * <p>
     * {@.ja 本 ExecutionContext の ExecutionKind を取得する}
     * {@.en This operation shall report the execution kind of the execution
     * context.}
     *
     * @return 
     *   {@.ja ExecutionKind}
     *   {@.en ExecutionKind}
     */
    public ExecutionKind getKind() {
        synchronized (m_profile) {
            rtcout.println(Logbuf.TRACE, getKindString(m_profile.kind)
                                        + " = getKind()");
            return m_profile.kind;
        }
    }

    /**
     * {@.ja Ownerコンポーネントをセットする。}
     * {@.en Setting owner component of the execution context}
     * <p>
     * {@.ja このECのOwnerとなるRTCをセットする。}
     * {@.en This function sets an RT-Component to be owner of the 
     * execution context.}
     *
     * @param comp 
     *   {@.ja OwnerとなるRTコンポーネント}
     *   {@.en an owner RT-Component of this execution context}
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t setOwner(LightweightRTObject comp) {
        rtcout.println(Logbuf.TRACE, "setOwner()");
        if (comp==null) {
            rtcout.println(Logbuf.ERROR,"nil reference is given.");
            return ReturnCode_t.BAD_PARAMETER;
        }
        RTObject rtobj;
        rtobj = RTObjectHelper.narrow(comp);
        if (rtobj==null) {
            rtcout.println(Logbuf.ERROR,"Narrowing failed.");
            return ReturnCode_t.BAD_PARAMETER;
        }
        synchronized (m_profile) {
            m_profile.owner = (RTObject)rtobj._duplicate();
            return ReturnCode_t.RTC_OK;
        }
    }

    /**
     * {@.ja Ownerコンポーネントの参照を取得する}
     * {@.en Getting a reference of the owner component}
     * <p>
     * {@.ja このECのOwnerであるRTCの参照を取得する。}
     * {@.en This function returns a reference of the owner RT-Component of
     * this execution context}
     *
     * @return 
     *   {@.ja OwnerRTコンポーネントの参照}
     *   {@.en a reference of the owner RT-Component}
     */
    public final RTObject getOwner() {
        rtcout.println(Logbuf.TRACE, "getOwner()");
        synchronized (m_profile) {
            return (RTObject)m_profile.owner._duplicate();
        }
    }

    /**
     * {@.ja RTコンポーネントを追加する}
     * {@.en Add an RT-component}
     * <p>
     * {@.ja 指定したRTコンポーネントを参加者リストに追加する。追加されたRTコ
     * ンポーネントは attach_context が呼ばれ、Inactive 状態に遷移する。
     * 指定されたRTコンポーネントがnullの場合は、BAD_PARAMETER が返され
     * る。指定されたRTコンポーネントが DataFlowComponent 以外の場合は、
     * BAD_PARAMETER が返される。}
     * {@.en The operation causes the given RTC to begin participating in
     * the execution context.  The newly added RTC will receive a call
     * to LightweightRTComponent::attach_context and then enter the
     * Inactive state.  BAD_PARAMETER will be invoked, if the given
     * RT-Component is null or if the given RT-Component is other than
     * DataFlowComponent.}
     *
     * @param comp 
     *   {@.ja 追加対象RTコンポーネント}
     *   {@.en The target RT-Component for add}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     */
    public ReturnCode_t addComponent(LightweightRTObject comp) {
        rtcout.println(Logbuf.TRACE,"addComponent()");
        if (comp==null) {
            rtcout.println(Logbuf.ERROR,"A nil reference was given.");
            return ReturnCode_t.BAD_PARAMETER;
        }
        RTObject rtobj = RTObjectHelper.narrow(comp);
        if (rtobj==null) {
            rtcout.println(Logbuf.ERROR,"Narrowing was failed.");
            return ReturnCode_t.RTC_ERROR;
        }
        synchronized (m_profile) {
            RTCListHolder holder = new RTCListHolder(m_profile.participants);
            CORBA_SeqUtil.push_back(holder, rtobj);
            m_profile.participants = holder.value;
            return ReturnCode_t.RTC_OK;
        }
    }

    /**
     * {@.ja RTコンポーネントを参加者リストから削除する}
     * {@.en Remove the RT-Component from participant list}
     * <p>
     * {@.ja 指定したRTコンポーネントを参加者リストから削除する。削除された
     * RTコンポーネントは detach_context が呼ばれる。指定されたRTコンポー
     * ネントが参加者リストに登録されていない場合は、BAD_PARAMETER が返
     * される。}
     * {@.en This operation causes a participant RTC to stop participating 
     * in the execution context.
     * The removed RTC will receive a call to
     * LightweightRTComponent::detach_context.
     * BAD_PARAMETER will be returned, if the given RT-Component is not
     * participating in the participant list.}
     *
     * @param comp 
     *   {@.ja 削除対象RTコンポーネント}
     *   {@.en The target RT-Component for delete}
     *
     * @return 
     *   {@.ja ReturnCode_t 型のリターンコード}
     *   {@.en The return code of ReturnCode_t type}
     *
     */
    public ReturnCode_t removeComponent(LightweightRTObject comp) {
        rtcout.println(Logbuf.TRACE,"removeComponent()");
        if (comp==null) {
            rtcout.println(Logbuf.ERROR,"A nil reference was given.");
            return ReturnCode_t.BAD_PARAMETER;
        }
        RTObject rtobj = RTObjectHelper.narrow(comp);
        if (rtobj==null) {
            rtcout.println(Logbuf.ERROR,"Narrowing was failed.");
            return ReturnCode_t.RTC_ERROR;
        }
        synchronized (m_profile) {
            RTCListHolder holder = new RTCListHolder(m_profile.participants); 
            int index;
            index = CORBA_SeqUtil.find(holder,
                                  new find_participant(rtobj));
            if (index < 0) {
                rtcout.println(Logbuf.ERROR,
                            "The given RTObject does not exist in the EC.");
                return ReturnCode_t.BAD_PARAMETER;
            }
            CORBA_SeqUtil.erase(holder, index);
            m_profile.participants = holder.value;
        }
        return ReturnCode_t.RTC_OK;
    }

    /**
     * {@.ja RTコンポーネントの参加者リストを取得する}
     * {@.en Getting participant RTC list}
     * <p>
     * {@.ja 現在登録されている参加者RTCのリストを取得する。この関数はコンポー
     * ネントリストのメンバ変数への参照を返すので、リスト使用前に
     * ExecutionContextProfile::lock() でロックし、リスト使用後は
     * ExecutionContextProfile::unlock() でロックを開放しなければならな
     * い。}
     * {@.en This function returns a list of participant RTC of the
     * execution context.  Since the function returns a reference to
     * the member variable of component list, user have to lock by
     * ExecutionContextProfile::lock() before using the list, and user
     * also have to release the unlock by
     * ExecutionContextProfile::unlock().}
     *
     * @return 
     *   {@.ja 参加者RTCのリスト}
     *   {@.en Participants RTC list}
     *
     */
    public final RTObject[] getComponentList() {
        synchronized (m_profile){
            rtcout.println(Logbuf.TRACE,"getComponentList("
                                    + m_profile.participants.length
                                    +")");
            return m_profile.participants;
        }
    }

    /**
     * {@.ja Propertiesをセットする}
     * {@.en Setting Properties}
     * <p>
     * {@.ja ExecutionContextProfile::properties をセットする。}
     * {@.en This function sets ExecutionContextProfile::properties by
     * Properties.}
     *
     * @param props 
     *   {@.ja ExecutionContextProfile::properties にセットするプロパティー}
     *   {@.en Properties to be set to ExecutionContextProfile::properties.}
     */
    public void setProperties(Properties props) {
        rtcout.println(Logbuf.TRACE,"setProperties()");
        String str = new String();
        str = props._dump(str,props,0);
        rtcout.println(Logbuf.TRACE,str);
        synchronized (m_profile) {
            NVListHolder holder = new NVListHolder(m_profile.properties);
            NVUtil.copyFromProperties(holder, props);
            m_profile.properties = holder.value;
        }
    }

    /**
     * {@.ja Propertiesを取得する}
     * {@.en Setting Properties}
     * <p>
     * {@.ja ExecutionContextProfile::properties を取得する。}
     *
     * @return
     *   {@.ja Propertiesに変換されたExecutionContextProfile::properties}
     *   {@.en This function sets ExecutionContextProfile::properties by
     * Properties.}
     *
     */
    public final Properties getProperties() {
        rtcout.println(Logbuf.TRACE,"getProperties()");
        Properties props  = new Properties(); 
        synchronized (m_profile) {
            NVListHolder holder = new NVListHolder(m_profile.properties);
            NVUtil.copyToProperties(props, holder);
            String str = new String();
            str = props._dump(str,props,0);
            rtcout.println(Logbuf.TRACE,str);
        }
        return props;
    }

    /**
     * {@.ja Profileを取得する}
     * {@.en Getting Profile}
     * <p>
     * {@.ja RTC::ExecutionContextProfile を取得する。取得した
     * ExecutionContextProfile の所有権は呼び出し側にある。取得されたオ
     * ブジェクトが不要になった場合、呼び出し側が開放する責任を負う。}
     * {@.en This function gets RTC::ExecutionContextProfile.  The ownership
     * of the obtained ExecutionContextProfile is given to caller. The
     * caller should release obtained object when it is unneccessary
     * anymore.}
     *
     * @return 
     *   {@.ja RTC::ExecutionContextProfile}
     *   {@.en RTC::ExecutionContextProfile}
     *
     */
    public RTC.ExecutionContextProfile getProfile() {
        rtcout.println(Logbuf.TRACE,"getProfile()");
        RTC.ExecutionContextProfile p;
        synchronized (m_profile){
            p = new RTC.ExecutionContextProfile(m_profile.kind,
                                                m_profile.rate,
                                                m_profile.owner,
                                                m_profile.participants,
                                                m_profile.properties);
        }
        return p;
    
    }


    /**
     * {@.ja ExecutionContextProfileをロックする}
     * {@.en Getting a lock of RTC::ExecutionContextProfile}
     * <p>
     * {@.ja このオブジェクトが管理する RTC::ExecutionContextProfile をロックする。
     * ロックが不要になった際にはunlock()でロックを解除しなければならない。}
     * {@.en This function locks  RTC::ExecutionContextProfile in the object.
     * The lock should be released when the lock is unneccessary.}
     *
     */
    public void lock() {
        //m_profileMutex.lock();
    } 

    /**
     * {@.ja ExecutionContextProfileをアンロックする}
     * {@.en Release a lock of the RTC::ExecutionContextProfile}
     * <p>
     * {@.ja このオブジェクトが管理する RTC::ExecutionContextProfile をアンロッ
     * クする。}
     * {@.en This function release the lock of RTC::ExecutionContextProfile
     * in the object.}
     *
     */
    public void unlock() {
        //m_profileMutex.unlock();
    }

    /**
     * {@.ja Logger}
     * {@.en Logger}
     */
    private Logbuf rtcout;
    /**
     * {@.ja ECProfile}
     * {@.en ECProfile}
     */
    private RTC.ExecutionContextProfile m_profile 
                                        = new RTC.ExecutionContextProfile();

    /**
     * {@.ja mutex of ExecutionContextProfile}
     * {@.en mutex ExecutionContextProfile}
     */
    //private String m_profileMutex = new String();

    /**
     * {@.ja ExecutionContext の実行周期}
     * {@.en Execution cycle of ExecutionContext}
     */
    private TimeValue m_period;

    /**
     * {@.ja ExecutionContextService オブジェクトへの参照}
     * {@.en Reference to ExecutionContextService object}
     */
    private ExecutionContextService m_ref;

    private class find_participant  implements equalFunctor {
        private RTObject m_comp;
        public find_participant(RTObject comp) {
            m_comp = (RTObject)comp._duplicate();
        }
        public boolean equalof(final java.lang.Object comp) {
            return m_comp._is_equivalent((RTObject)comp);
        }
    };
}; // class ExecutionContextProfile
