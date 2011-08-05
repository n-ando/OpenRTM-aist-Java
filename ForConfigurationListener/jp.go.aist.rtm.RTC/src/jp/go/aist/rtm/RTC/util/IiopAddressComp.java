package jp.go.aist.rtm.RTC.util;


//public final class IiopAddressComp implements org.omg.CORBA.portable.IDLEntity
public final class IiopAddressComp
{
  public String HostID = null;
  public short Port = (short)0;

  public IiopAddressComp ()
  {
  } // ctor

  public IiopAddressComp (String _HostID, short _Port)
  {
    HostID = _HostID;
    Port = _Port;
  } // ctor

} // class IiopAddressComp
