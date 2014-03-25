package jp.go.aist.rtm.RTC.util;

//public final class IiopAddressCompHolder implements org.omg.CORBA.portable.Streamable
public final class IiopAddressCompHolder
{
  public jp.go.aist.rtm.RTC.util.IiopAddressComp value = null;

  public IiopAddressCompHolder ()
  {
  }

  public IiopAddressCompHolder (jp.go.aist.rtm.RTC.util.IiopAddressComp initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = jp.go.aist.rtm.RTC.util.IiopAddressCompHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    jp.go.aist.rtm.RTC.util.IiopAddressCompHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return jp.go.aist.rtm.RTC.util.IiopAddressCompHelper.type ();
  }

}
