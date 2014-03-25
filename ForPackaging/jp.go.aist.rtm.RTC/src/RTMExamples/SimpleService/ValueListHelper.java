package RTMExamples.SimpleService;


/**
* RTMExamples/SimpleService/ValueListHelper.java .
* IDL-to-Java コンパイラ (ポータブル), バージョン "3.1" で生成
* 生成元: src/RTMExamples/MyService.idl
* 2008年7月17日 22時25分31秒 JST
*/

abstract public class ValueListHelper
{
  private static String  _id = "IDL:SimpleService/ValueList:1.0";

  public static void insert (org.omg.CORBA.Any a, float[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static float[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_float);
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (RTMExamples.SimpleService.ValueListHelper.id (), "ValueList", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static float[] read (org.omg.CORBA.portable.InputStream istream)
  {
    float value[] = null;
    int _len0 = istream.read_long ();
    value = new float[_len0];
    istream.read_float_array (value, 0, _len0);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, float[] value)
  {
    ostream.write_long (value.length);
    ostream.write_float_array (value, 0, value.length);
  }

}
