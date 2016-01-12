package RTMExamples.SimpleService;


/**
* RTMExamples/SimpleService/EchoListHolder.java .
* IDL-to-Java コンパイラ (ポータブル), バージョン "3.1" で生成
* 生成元: src/RTMExamples/MyService.idl
* 2008年7月17日 22時25分31秒 JST
*/

public final class EchoListHolder implements org.omg.CORBA.portable.Streamable
{
  public String value[] = null;

  public EchoListHolder ()
  {
  }

  public EchoListHolder (String[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = RTMExamples.SimpleService.EchoListHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    RTMExamples.SimpleService.EchoListHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return RTMExamples.SimpleService.EchoListHelper.type ();
  }

}
