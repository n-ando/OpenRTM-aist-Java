package jp.go.aist.rtm.RTC.port;
import jp.go.aist.rtm.RTC.port.publisher.PublisherBase;

public interface OnConnect {

    /**
     *  <p> run  </p>
     *  <p> Method of callback.  </p>
     *  @param id
     *  @param publisher
     */
    void run(final String id, PublisherBase publisher);
    
}

