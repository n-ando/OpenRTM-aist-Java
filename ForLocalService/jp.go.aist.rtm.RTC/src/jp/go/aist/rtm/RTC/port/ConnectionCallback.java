package jp.go.aist.rtm.RTC.port;

/**
 * callback functor base classes
 * <p>ConnectCallback</p>
 * <p> Callback interface for connect/notify_connect() funcs </p>
 *
 * <p>This is the interface for callback functor for connect/notify_connect()
 * invocation in Port. Argument is RTC::ConnectorProfile that is given
 * these functions. </p>
 *
 * @param profile ConnectorProfile
 *
 */
public interface ConnectionCallback {
    /**
     *  <p> run  </p>
     *  <p> Method of callback.  </p>
     *  @param profile
     */
    void run(RTC.ConnectorProfileHolder profile);
};

