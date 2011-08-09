package RTMExamples.SimpleService;


/*
 * Example implementational code for IDL interface MyService
 */
public class MyServiceSVC_impl extends MyServicePOA{
    
    Object mobj;
    
    public MyServiceSVC_impl() {
        // Please add extra constructor code here.
    }
    public MyServiceSVC_impl(Object obj) {
        // Please add extra constructor code here.
        this.mobj = obj;
    }

    /*
     * Methods corresponding to IDL attributes and operations
     */
    public String echo(final String msg) {
        if( m_echoList.value==null ) m_echoList.value = new String[0];
        this.push_back(m_echoList, msg);
        System.out.println( "MyService::echo() was called." );
        System.out.println( "Message: " + msg );
        return msg;
    }

    public String[] get_echo_history() {
        System.out.println( "MyService::get_echo_history() was called." );
        for(int intIdx=0;intIdx<m_echoList.value.length;intIdx++) {
            System.out.println(intIdx + ": " + m_echoList.value[intIdx]);
        }
//      CORBA_SeqUtil::for_each(m_echoList, seq_print<char*>());
      
        EchoListHolder el = new EchoListHolder(m_echoList.value);
        return el.value;
    }

    public void set_value(float value) {
        if( m_valueList.value==null ) m_valueList.value = new float[0];
        this.push_back(m_valueList, value);
        m_value = value;

        System.out.println( "MyService::set_value() was called." );
        System.out.println( "Current value: " + m_value );
    }

    public float get_value() {
        System.out.println( "MyService::get_value() was called." );
        System.out.println( "Current value: " + m_value );

        return m_value;
    }

    public float[] get_value_history() {
        System.out.println( "MyService::get_value_history() was called." );
        for(int intIdx=0;intIdx<m_valueList.value.length;intIdx++) {
            System.out.println(intIdx + ": " + m_valueList.value[intIdx]);
        }

        ValueListHolder vl = new ValueListHolder(m_valueList.value);
        return vl.value;
    }

    private void push_back(EchoListHolder seq, String elem) {
        int len;
        if( seq==null ) {
            len = 0;
            seq = new EchoListHolder();
        } else {
            len = seq.value.length;
        }
        String[] newlist = new String[len + 1];
        for (int intIdx = 0; intIdx < len; intIdx++) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

    private void push_back(ValueListHolder seq, float elem) {
        int len;
        if( seq==null ) {
            len = 0;
            seq = new ValueListHolder();
        } else {
            len = seq.value.length;
        }
        float[] newlist = new float[len + 1];
        for (int intIdx = 0; intIdx < len; intIdx++) {
            newlist[intIdx] = seq.value[intIdx];
        }
        newlist[len] = elem;
        seq.value = newlist;
    }

     private float m_value;
     private EchoListHolder m_echoList = new EchoListHolder();
     private ValueListHolder m_valueList = new ValueListHolder();
}
