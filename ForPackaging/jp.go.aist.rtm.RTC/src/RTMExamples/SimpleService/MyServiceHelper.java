package RTMExamples.SimpleService;


/**
* RTMExamples/SimpleService/MyServiceHelper.java .
* IDL-to-Java コンパイラ (ポータブル), バージョン "3.1" で生成
* 生成元: src/RTMExamples/MyService.idl
* 2008年7月17日 22時25分31秒 JST
*/

abstract public class MyServiceHelper
{
  private static String  _id = "IDL:SimpleService/MyService:1.0";

  public static void insert (org.omg.CORBA.Any a, RTMExamples.SimpleService.MyService that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static RTMExamples.SimpleService.MyService extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (RTMExamples.SimpleService.MyServiceHelper.id (), "MyService");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static RTMExamples.SimpleService.MyService read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_MyServiceStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, RTMExamples.SimpleService.MyService value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static RTMExamples.SimpleService.MyService narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof RTMExamples.SimpleService.MyService)
      return (RTMExamples.SimpleService.MyService)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      RTMExamples.SimpleService._MyServiceStub stub = new RTMExamples.SimpleService._MyServiceStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static RTMExamples.SimpleService.MyService unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof RTMExamples.SimpleService.MyService)
      return (RTMExamples.SimpleService.MyService)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      RTMExamples.SimpleService._MyServiceStub stub = new RTMExamples.SimpleService._MyServiceStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}
