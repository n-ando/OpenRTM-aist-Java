package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.port.CorbaConsumer;
import jp.go.aist.rtm.RTC.port.PortBase;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.ORBUtil;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
import org.omg.CosNaming.NameComponent;
import org.omg.CosNaming.NamingContext;
import org.omg.CosNaming.NamingContextExtHelper;
import org.omg.CosNaming.BindingIteratorHolder;
import org.omg.CosNaming.BindingHolder;
import org.omg.CosNaming.Binding;
import org.omg.CosNaming.BindingListHolder;
import org.omg.CosNaming.BindingType;

import RTC.RTCListHolder;
import RTC.RTObject;
import RTC.RTObjectHelper;
  /**
   * {@.ja CORBA 用 NamingServer 管理クラス。}
   * {@.en NamingServer management class for CORBA}
   *
   * <p>
   * {@.ja CORBA 用 NamingServer 管理用クラス。
   * CORBA コンポーネントの NamingService への登録、解除などを管理する。}
   * {@.en NamingServer management class for CORBA.
   * Manage to register and unregister CORBA components to NamingService.}
   */
class NamingOnCorba implements NamingBase {

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * <p>
     * {@.ja コンストラクタ。第2引数に与えるネームサービス名は、ネームサービ
     * スのホスト名とポート番号を ":" で区切ったものである。ポート番号
     * が省略された場合、2809番ポートが使用される。}
     * {@.en Constructor. Naming service name that is given at the second
     * argument is host name and port number hoined with ":". If the
     * port number is abbreviated, the default port number 2809 is
     * used.}
     * </p>
     *
     * @param orb 
     *   {@.ja ORB}
     *   {@.en ORB}
     * @param names 
     *   {@.ja NamingServer 名称}
     *   {@.en Name of NamingServer}
     *
     */
    public NamingOnCorba(ORB orb, final String names) {
        try {
            m_cosnaming = new CorbaNaming(orb, names);
        } catch (Exception e) {
            e.printStackTrace();
        }

        rtcout = new Logbuf("manager.NamingOnCorba");

    }
    
    /**
     * {@.en Checks that the string is IPaddress. }
     */
    private boolean isIpAddressFormat(String string){
        java.util.regex.Pattern pattern 
            = java.util.regex.Pattern.compile(
               "(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})");
        java.util.regex.Matcher matcher = pattern.matcher(string);
        return matcher.matches();
    }

    /**
     * {@.ja 指定したオブジェクトのNamingServiceへバインド}
     * {@.en Bind the specified objects to NamingService}
     *
     * <p> 
     * {@.ja 指定したオブジェクトを指定した名称で CORBA NamingService へバイ
     * ンドする。}
     * {@.en Bind the specified objects to CORBA NamingService 
     * by specified names.}
     * </p>
     * 
     * @param name 
     *   {@.ja バインド時の名称}
     *   {@.en Names at the binding}
     * @param rtobj 
     *   {@.ja バインド対象オブジェクト}
     *   {@.en The target objects for the binding}
     *
     */
    public void bindObject(final String name, final RTObject_impl rtobj) {
        rtcout.println(Logbuf.TRACE, "bindObject(" + name + ",rtobj)");
        try{
            m_cosnaming.rebindByString(name, rtobj.getObjRef(), true);
        } catch ( Exception ex ) {
        }
    }

    public void bindObject(final String name, final PortBase port) {
        rtcout.println(Logbuf.TRACE, "bindObject(" + name + ",rtobj)");
        try{
            m_cosnaming.rebindByString(name, port.getPortRef(), true);
        } catch ( Exception ex ) {
        }
    }
    /**
     * {@.ja 指定したManagerServantをNamingServiceへバインド}
     * {@.en Bind the specified ManagerServants to NamingService}
     *
     * @param name 
     *   {@.ja バインド時の名称}
     *   {@.en Names at the binding}
     * @param mgr 
     *   {@.ja バインド対象ManagerServant}
     *   {@.en The target ManagerServants for the binding}
     */
    public void bindObject(final String name, final ManagerServant mgr) {
        rtcout.println(Logbuf.TRACE, "bindObject(" + name + ",mgr)");
        try{
            m_cosnaming.rebindByString(name, mgr.getObjRef(), true);
        } catch ( Exception ex ) {
        }
    }
    /**
     *
     * {@.ja 指定した CORBA オブジェクトのNamingServiceへバインド}
     * {@.en Binds specified CORBA object to NamingService.}
     * <p>
     * {@.ja 指定した CORBA オブジェクトを指定した名称で CORBA NamingService へ
     * バインドする。}
     * {@.en Binds specified CORBA object to NamingService.}
     *
     * @param name 
     *   {@.ja バインド時の名称}
     *   {@.en The name to be bound to the NamingService}
     *
     * @param port 
     *   {@.ja バインド対象オブジェクト}
     *   {@.en The target objects to be bound to the object}
     */
    public void bindPortObject(final String name, final PortBase port){
        rtcout.println(Logbuf.TRACE, 
                "bindPortObject(name = " + name + ",port)");
        try{
            m_cosnaming.rebindByString(name, port.getPortRef(), true);
        }
        catch ( Exception ex ) {
            rtcout.println(Logbuf.ERROR, ex.toString());
        }

        return;
    }


    /**
     * {@.ja 指定した CORBA オブジェクトをNamingServiceからアンバインド}
     * {@.en Unbind the specified CORBA objects from NamingService}
     *
     * <p> 
     * {@.ja 指定した CORBA オブジェクトを CORBA NamingService から
     * アンバインドする。}
     * {@.en Unbind the specified CORBA objects from CORBA NamingService.}
     * 
     * @param name 
     *   {@.ja アンバインド対象オブジェクト}
     *   {@.en The target objects for the unbinding}
     *
     *
     */
    public void unbindObject(final String name) {
        rtcout.println(Logbuf.TRACE, "unbindObject(name  = " +name+")");
        try {
            m_cosnaming.unbind(name);
        } catch (Exception ex) {
        }
    }

    /**
     * {@.ja ネームサーバの生存を確認する。}
     * {@.en Check if the name service is alive}
     * 
     * @return 
     *   {@.ja true:生存している, false:生存していない}
     *   {@.en rue: alive, false:non not alive}
     *
     * 
     */
    public boolean isAlive() {
        rtcout.println(Logbuf.TRACE, "isAlive()");
        return m_cosnaming.isAlive();
    }

    /**
     *
     * {@.ja RTCの検索}
     * {@.en Finds RTCs}
     * <p>
     * {@.ja ネーミングサービスからRTCをインスタンス名から検索し、
     * 一致するRTCのリストを取得する}
     * {@.en Finds RTCis from the inside of NamingService}
     *
     *
     * @param context 
     *   {@.ja 現在検索中のコンテキスト}
     *   {@.en context}
     *
     * @param name
     *   {@.ja RTCのインスタンス名}
     *   {@.en Instance name of RTC}
     *
     * @param rtcs
     *   {@.ja RTCのリスト}
     *   {@.en List of RTC}
     *
     */
    public void get_RTC_by_Name(NamingContext context, String name, 
            RTCListHolder rtcs){

        rtcout.println(Logbuf.PARANOID, "get_RTC_by_Name("+name+")");
        int length = 500;
        BindingListHolder bl = new BindingListHolder();
        BindingIteratorHolder bi = new BindingIteratorHolder();

        context.list(length,bl,bi);
        rtcout.println(Logbuf.PARANOID, "bl="+bl);
        rtcout.println(Logbuf.PARANOID, "bl.vlaue="+bl.value);
        rtcout.println(Logbuf.PARANOID, "bl.vlaue.length="+bl.value.length);
        rtcout.println(Logbuf.PARANOID, "bi="+bi);
        rtcout.println(Logbuf.PARANOID, "bi.vlaue="+bi.value);
        rtcout.println(Logbuf.PARANOID, 
                "BindingType.ncontext.valeu()="+BindingType.ncontext.value());
        rtcout.println(Logbuf.PARANOID, 
                "BindingType.nobject.vlaue()="+BindingType.nobject.value());
        BindingHolder bindholder = new BindingHolder();
        Binding bindings[] = bl.value;
        for(int ic=0;ic<bindings.length;++ic) {
            BindingType type = bindings[ic].binding_type;
            rtcout.println(Logbuf.PARANOID, 
                "bindings[ic].binding_type.value()="
                +type.value());
            if(type.value()==BindingType.ncontext.value()){
                try{
                    NamingContext next_context 
                        = NamingContextExtHelper.narrow(
                            context.resolve(bindings[ic].binding_name));

                    get_RTC_by_Name(next_context, name, rtcs);
                }
                catch(Exception ex){
                    rtcout.println(Logbuf.ERROR, "catch exception");
                    rtcout.println(Logbuf.ERROR, ex.toString());
                    return;
                }
            }
            else if(type.value()==BindingType.nobject.value()){
                NameComponent names[] = bindings[ic].binding_name;
                rtcout.println(Logbuf.PARANOID, 
                        "bindings[ic].binding_name[0].id="
                        +names[0].id);
                rtcout.println(Logbuf.PARANOID, 
                        "bindings[ic].binding_name[0].kind="
                        +names[0].kind);
                if(names[0].id.equals(name) && 
                        names[0].kind.equals("rtc")){
                    try{
                        rtcout.println(Logbuf.PARANOID, "names="+names);
                        rtcout.println(Logbuf.PARANOID, "context="+context);
                        RTObject obj = 
                            RTObjectHelper.narrow(context.resolve(names));
                        CORBA_SeqUtil.push_back(rtcs, obj);
                    }
                    catch (Exception ex) {
                        rtcout.println(Logbuf.ERROR, "catch exception");
                        rtcout.println(Logbuf.ERROR, ex.toString());
                    }
                }
            }
        }
    }
    /**
     *
     * {@.ja rtcname形式でRTCのオブジェクトリファレンスを取得する}
     * {@.en Gets RTC objects by rtcname form.}
     *
     * @return 
     *   {@.ja RTCのオブジェクトリファレンス}
     *   {@.en List of RTObjects}
     * virtual RTCList string_to_component(string name) = 0;
     */
    public RTObject[] string_to_component(String name){
        rtcout.println(Logbuf.PARANOID, "string_to_component("+name+")");
        RTCListHolder rtc_list = new RTCListHolder();
        String[] tmps = name.split("://");
        if(tmps.length > 1){
            rtcout.println(Logbuf.PARANOID, "tmps[0]="+tmps[0]);
            if(tmps[0].equals("rtcname")){
                String tag = tmps[0];
                String url = tmps[1];
                rtcout.println(Logbuf.PARANOID, "tmps[1]="+tmps[1]);
                String[] elements = url.split("/");
                if(elements.length > 1){
                    String host = elements[0];
                    rtcout.println(Logbuf.PARANOID, "host="+host);
          
                    String rtc_name = url.substring(host.length()+1);
                    rtcout.println(Logbuf.PARANOID, "rtc_name="+rtc_name);
          
                    try{
                        CorbaNaming cns;
                        rtcout.println(Logbuf.PARANOID, "host="+host);
                        if(host.equals("*")){
                            cns = m_cosnaming;
                        }
                        else{
                            ORB orb = ORBUtil.getOrb();
                            String[] names = host.split(":");
                            if (names.length == 1 && !names[0].equals("")) {
                                host += ":2809";
                            }
                            rtcout.println(Logbuf.PARANOID, "host="+host);
                            cns = new CorbaNaming(orb,host);
                        }
                        String[] names = rtc_name.split("/");
                        rtcout.println(Logbuf.PARANOID, 
                                    "names.length="+names.length);
            
                        if(names.length == 2 && names[0].equals("*")){
                            rtcout.println(Logbuf.PARANOID, 
                                        "cns="+cns);
                            NamingContext root_cxt = cns.getRootContext();
                            rtcout.println(Logbuf.PARANOID, 
                                        "names[1]="+names[1]);
                            get_RTC_by_Name(root_cxt, names[1], rtc_list);
                            return rtc_list.value;
                        }
                        else{
                            rtcout.println(Logbuf.PARANOID, 
                                    "names[0]="+names[0]);
                            rtc_name += ".rtc";
                            rtcout.println(Logbuf.PARANOID, 
                                    "rtc_name.rtc="+rtc_name);
                            Object obj = cns.resolveStr(rtc_name);
                            if(obj == null){
                                rtcout.println(Logbuf.PARANOID, 
                                    rtc_name + " is not found on "+host+".");
                                return null;
                            }
                            CORBA_SeqUtil.push_back(rtc_list, 
                                    RTObjectHelper.narrow(obj));
                            return rtc_list.value;
                        }
                    }
                    catch (Exception ex) {
                       String crlf = System.getProperty("line.separator");
                       rtcout.println(Logbuf.PARANOID,
                               rtc_name + " is not found on "+host+"."
                               + crlf + ex.toString());
                       return null;
                    }
                }
            }
        }

      
        return rtc_list.value;
    }

    public CorbaNaming getCorbaNaming() {
        return m_cosnaming;
    }



    private CorbaNaming m_cosnaming;
    /**
     * {@.ja Logging用フォーマットオブジェクト}
     * {@.en Format object for Logging}
     */
    protected Logbuf rtcout;
}
