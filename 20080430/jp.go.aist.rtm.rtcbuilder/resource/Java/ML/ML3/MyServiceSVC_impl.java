// -*-Java-*-
/*!
 * @file  MyServiceSVC_impl.java
 * @brief Service implementation code of MyServiceType1.idl
 *
 */
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
    public String echo(TestStruct msg) {
        // Please insert your code here and remove the following warning pragma
        // TODO "Code missing in function <String echo(TestStruct msg)>"
        return null;
    }

    public String[] get_echo_history(String[] echoList) {
        // Please insert your code here and remove the following warning pragma
        // TODO "Code missing in function <EchoList get_echo_history(EchoList echoList)>"
        return null;
    }

    public void set_value(float value) {
        // Please insert your code here and remove the following warning pragma
        // TODO "Code missing in function <void set_value(float value)>"
    }

    public float get_value(TestStructHolder value) {
        // Please insert your code here and remove the following warning pragma
        // TODO "Code missing in function <float get_value(TestStruct value)>"
        return 0;
    }

    public float[] get_value_history() {
        // Please insert your code here and remove the following warning pragma
        // TODO "Code missing in function <ValueList get_value_history()>"
        return null;
    }

    public float get_test_value(float test) {
        // Please insert your code here and remove the following warning pragma
        // TODO "Code missing in function <Values get_test_value(Values test)>"
        return 0;
    }

    public void get_test_value2(TestStructHolder test) {
        // Please insert your code here and remove the following warning pragma
        // TODO "Code missing in function <void get_test_value2(StrVal test)>"
    }

//  End of example implementational code
}
