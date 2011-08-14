package RTMExamples.SimpleService;

/**
* RTMExamples/SimpleService/MyServiceHolder.java .
* IDL-to-Java コンパイラ (ポータブル), バージョン "3.1" で生成
* 生成元: src/RTMExamples/MyService.idl
* 2008年7月17日 22時25分31秒 JST
*/

public final class MyServiceHolder implements org.omg.CORBA.portable.Streamable
{
  public RTMExamples.SimpleService.MyService value = null;

  public MyServiceHolder ()
  {
  }

  public MyServiceHolder (RTMExamples.SimpleService.MyService initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = RTMExamples.SimpleService.MyServiceHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    RTMExamples.SimpleService.MyServiceHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return RTMExamples.SimpleService.MyServiceHelper.type ();
  }

}
