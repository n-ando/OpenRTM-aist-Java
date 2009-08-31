package jp.go.aist.rtm.RTC.port;

public enum ReturnCode {
    PORT_OK,
    PORT_ERROR,
    BUFFER_FULL,
    BUFFER_EMPTY,
    BUFFER_TIMEOUT,
    SEND_FULL,
    SEND_TIMEOUT,
    RECV_EMPTY,
    RECV_TIMEOUT,
    INVALID_ARGS,
    PRECONDITION_NOT_MET,
    CONNECTION_LOST,
    UNKNOWN_ERROR
}


