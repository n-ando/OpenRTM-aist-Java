// -*-Java-*-
/*!
 * @file  MyServiceSVC_impl.java
 * @brief Service implementation code of MyService.idl
 *
 */

package RTMExamples.AutoTest;

/*!
 * @class MyServiceSVC_impl
 * Example class implementing IDL interface MyService
 */
public class MyServiceSVC_impl extends MyServicePOA{
    
    public MyServiceSVC_impl() {
        // Please add extra constructor code here.
    }

    /*
     * Methods corresponding to IDL attributes and operations
     */
    public String echo(String msg) {
        // Please insert your code here and remove the following warning pragma
        // TODO "Code missing in function <String echo(String msg)>"
	m_msg = msg;
	m_isNew = true;
        return null;
    }

    public String[] get_echo_history() {
        // Please insert your code here and remove the following warning pragma
        // TODO "Code missing in function <EchoList get_echo_history()>"
        return null;
    }

    public void set_value(float value) {
        // Please insert your code here and remove the following warning pragma
        // TODO "Code missing in function <void set_value(float value)>"
    }

    public float get_value() {
        // Please insert your code here and remove the following warning pragma
        // TODO "Code missing in function <float get_value()>"
        return 0;
    }

    public float[] get_value_history() {
        // Please insert your code here and remove the following warning pragma
        // TODO "Code missing in function <ValueList get_value_history()>"
        return null;
    }

    public String get_echo_message() {
	if (m_isNew) {
	    m_isNew = false;
	    return m_msg;
	}
	return "";
    }
    private String m_msg = new String();
    private Boolean m_isNew = false;
//  End of example implementational code
}
