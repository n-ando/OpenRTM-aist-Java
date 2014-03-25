package RTMExamples.SimpleService;


/**
* RTMExamples/SimpleService/MyServicePOA.java .
* IDL-to-Java コンパイラ (ポータブル), バージョン "3.1" で生成
* 生成元: src/RTMExamples/MyService.idl
* 2008年7月17日 22時25分31秒 JST
*/

public abstract class MyServicePOA extends org.omg.PortableServer.Servant
 implements RTMExamples.SimpleService.MyServiceOperations, org.omg.CORBA.portable.InvokeHandler
{

  // Constructors

  private static java.util.Hashtable _methods = new java.util.Hashtable ();
  static
  {
    _methods.put ("echo", new java.lang.Integer (0));
    _methods.put ("get_echo_history", new java.lang.Integer (1));
    _methods.put ("set_value", new java.lang.Integer (2));
    _methods.put ("get_value", new java.lang.Integer (3));
    _methods.put ("get_value_history", new java.lang.Integer (4));
  }

  public org.omg.CORBA.portable.OutputStream _invoke (String $method,
                                org.omg.CORBA.portable.InputStream in,
                                org.omg.CORBA.portable.ResponseHandler $rh)
  {
    org.omg.CORBA.portable.OutputStream out = null;
    java.lang.Integer __method = (java.lang.Integer)_methods.get ($method);
    if (__method == null)
      throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);

    switch (__method.intValue ())
    {
       case 0:  // SimpleService/MyService/echo
       {
         String msg = in.read_string ();
         String $result = null;
         $result = this.echo (msg);
         out = $rh.createReply();
         out.write_string ($result);
         break;
       }

       case 1:  // SimpleService/MyService/get_echo_history
       {
         String $result[] = null;
         $result = this.get_echo_history ();
         out = $rh.createReply();
         RTMExamples.SimpleService.EchoListHelper.write (out, $result);
         break;
       }

       case 2:  // SimpleService/MyService/set_value
       {
         float value = in.read_float ();
         this.set_value (value);
         out = $rh.createReply();
         break;
       }

       case 3:  // SimpleService/MyService/get_value
       {
         float $result = (float)0;
         $result = this.get_value ();
         out = $rh.createReply();
         out.write_float ($result);
         break;
       }

       case 4:  // SimpleService/MyService/get_value_history
       {
         float $result[] = null;
         $result = this.get_value_history ();
         out = $rh.createReply();
         RTMExamples.SimpleService.ValueListHelper.write (out, $result);
         break;
       }

       default:
         throw new org.omg.CORBA.BAD_OPERATION (0, org.omg.CORBA.CompletionStatus.COMPLETED_MAYBE);
    }

    return out;
  } // _invoke

  // Type-specific CORBA::Object operations
  private static String[] __ids = {
    "IDL:SimpleService/MyService:1.0"};

  public String[] _all_interfaces (org.omg.PortableServer.POA poa, byte[] objectId)
  {
    return (String[])__ids.clone ();
  }

  public MyService _this() 
  {
    return MyServiceHelper.narrow(
    super._this_object());
  }

  public MyService _this(org.omg.CORBA.ORB orb) 
  {
    return MyServiceHelper.narrow(
    super._this_object(orb));
  }


} // class MyServicePOA
