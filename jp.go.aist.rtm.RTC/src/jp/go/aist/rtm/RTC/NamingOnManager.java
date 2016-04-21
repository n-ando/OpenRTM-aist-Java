package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.log.Logbuf;
import jp.go.aist.rtm.RTC.port.CorbaConsumer;
import jp.go.aist.rtm.RTC.port.PortBase;
import jp.go.aist.rtm.RTC.util.CORBA_SeqUtil;
import jp.go.aist.rtm.RTC.util.Properties;

import org.omg.CORBA.ORB;
import org.omg.CORBA.Object;
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
   * {@.ja Manager 用 NamingServer 管理クラス}
   * {@.en NamingServer management class for Manager}
   *
   * <p>
   * {@.ja Manager 用 NamingServer 管理用クラス。
   * Manager コンポーネントの NamingService への登録、解除などを管理する。}
   * {@.en NamingServer management class for Manager.
   * Manage to register and unregister Manager components to NamingService.}
   */
class NamingOnManager implements NamingBase {

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     * <p>
     *
     * @param orb 
     *   {@.ja ORB}
     *   {@.en ORB}
     * @param mgr
     *   {@.ja Manager}
     *   {@.en Manager}
     *
     */
    public NamingOnManager(ORB orb, Manager mgr) {
/*
        try {
            m_cosnaming = new CorbaNaming(orb, names);
        } catch (Exception e) {
            e.printStackTrace();
        }
*/
        rtcout = new Logbuf("NamingOnManager");
        m_cosnaming = null;
        m_orb = orb;
        m_mgr = mgr;

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
/*
        try{
            m_cosnaming.rebindByString(name, rtobj.getObjRef(), true);
        } catch ( Exception ex ) {
        }
*/
    }

    public void bindObject(final String name, final PortBase port) {
        rtcout.println(Logbuf.TRACE, "bindObject(" + name + ",rtobj)");
/*
        try{
            m_cosnaming.rebindByString(name, port.getPortRef(), true);
        } catch ( Exception ex ) {
        }
*/
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
/*
        try{
            m_cosnaming.rebindByString(name, mgr.getObjRef(), true);
        } catch ( Exception ex ) {
        }
*/
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
/*
        try{
            m_cosnaming.rebindByString(name, port.getPortRef(), true);
        }
        catch ( Exception ex ) {
            rtcout.println(Logbuf.ERROR, ex.toString());
        }
*/
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
/*
        try {
            m_cosnaming.unbind(name);
        } catch (Exception ex) {
        }
*/
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
        //return m_cosnaming.isAlive();
        return true;
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

        int length = 500;
        BindingListHolder bl = new BindingListHolder();
        BindingIteratorHolder bi = new BindingIteratorHolder();

        context.list(length,bl,bi);
        BindingHolder bindholder = new BindingHolder();
        while (bi.value.next_one(bindholder)) {
            if(bindholder.value.binding_type==BindingType.ncontext){
                try{
                    NamingContext next_context 
                        = NamingContextExtHelper.narrow(
                            context.resolve(bindholder.value.binding_name));
                    get_RTC_by_Name(next_context, name, rtcs);
                }
                catch(Exception ex){
                    rtcout.println(Logbuf.ERROR, "catch exception");
                    rtcout.println(Logbuf.ERROR, ex.toString());
                    return;
                }
            }
            else if(bindholder.value.binding_type==BindingType.nobject){
                if(bindholder.value.binding_name[0].id.equals(name) && 
                        bindholder.value.binding_name[0].kind.equals("rtc")){
                    try{
                        CorbaConsumer cc = new CorbaConsumer();
                        cc.setObject(context.resolve(
                                        bindholder.value.binding_name));
                        RTObject obj = RTObjectHelper.narrow(cc.getObject());
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
            rtcout.println(Logbuf.PARANOID, "tmps[0]:"+tmps[0]);
            if(tmps[0].equals("rtcloc")){
                String tag = tmps[0];
                String url = tmps[1];
                rtcout.println(Logbuf.PARANOID, "tmps[1]:"+tmps[1]);
                String[] elements = url.split("/");
                if(elements.length > 1){
                    String host = elements[0];
                    rtcout.println(Logbuf.PARANOID, "host:"+host);
          
                    String rtc_name = url.substring(host.length()+1);
                    rtcout.println(Logbuf.PARANOID, "rtc_name:"+rtc_name);
          
                    RTM.Manager mgr = getManager(host);
                    if(mgr!=null){
                        rtc_list.value = 
                                mgr.get_components_by_name(rtc_name);

                        RTM.Manager[] slaves = mgr.get_slave_managers();
                        for(int ic=0;ic<slaves.length;++ic){
                            try{
                                RTObject[] rtobjects = 
                                    slaves[ic].get_components_by_name(rtc_name);
                                System.arraycopy(
                                    rtobjects, 0, 
                                    rtc_list.value, rtc_list.value.length, 
                                    rtobjects.length);
//                                rtc_list.extend(
//                                    slaves[ic].get_components_by_name(rtc_name));
                            }
                            catch (Exception ex) {
                                rtcout.println(Logbuf.DEBUG, ex.toString());
                                mgr.remove_slave_manager(slaves[ic]);
                            }
                        }
                    }
                    return rtc_list.value;
                }
            }
        }

      
        return rtc_list.value;
    }
    /**
     *
     * {@.ja 指定ホスト名、ポート名でManagerのオブジェクトリファレンスを取得}
     * {@.en Gets RTC objects of Manager.}
     * @param name
     *   {@.ja 指定ホスト名、ポート名}
     *   {@.en hostname portname}
     * @return 
     *   {@.ja Managerのオブジェクトリファレンス}
     *   {@.en Manager object}
     * virtual Manager_ptr getManager(string name);
     */
    public RTM.Manager getManager(String name){
        RTM.Manager mgr;
        if(name.equals("*")){
            ManagerServant mgr_sev = m_mgr.getManagerServant();
            if(mgr_sev.is_master()){
                mgr = mgr_sev.getObjRef();
            }
            else{
                RTM.Manager[] masters = mgr_sev.get_master_managers();
                if(masters.length >0){
                    mgr = masters[0];
                }
                else{
                    mgr = mgr_sev.getObjRef();
                }
            }
            return mgr;
        }
        try{
            String mgrloc = "corbaloc:iiop:";
            Properties prop = m_mgr.getConfig();
            String manager_name = prop.getProperty("manager.name");
            mgrloc += name;
            mgrloc += "/" + manager_name;
      
            Object mobj = m_orb.string_to_object(mgrloc);
            mgr = RTM.ManagerHelper.narrow(mobj);
      
            rtcout.println(Logbuf.DEBUG, "corbaloc: "+mgrloc);
            return mgr;
        }
        catch (Exception ex) {
            rtcout.println(Logbuf.DEBUG, ex.toString());
        }
        return null;
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

    private ORB m_orb;
    private Manager m_mgr;
}

