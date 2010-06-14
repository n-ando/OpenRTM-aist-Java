package jp.go.aist.rtm.RTC;

import jp.go.aist.rtm.RTC.buffer.CdrRingBuffer;
import jp.go.aist.rtm.RTC.PeriodicTask;
import jp.go.aist.rtm.RTC.port.publisher.PublisherFlush;
import jp.go.aist.rtm.RTC.port.publisher.PublisherNew;
import jp.go.aist.rtm.RTC.port.publisher.PublisherPeriodic;
import jp.go.aist.rtm.RTC.port.InPortCorbaCdrProvider;
import jp.go.aist.rtm.RTC.port.InPortCorbaCdrConsumer;
import jp.go.aist.rtm.RTC.port.OutPortCorbaCdrProvider;
import jp.go.aist.rtm.RTC.port.OutPortCorbaCdrConsumer;

/**
 * {@.ja Factory初期処理用クラス}
 * {@.en Class for Factory initial processing}
 */
public class FactoryInit {
    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     */
    public FactoryInit() {
    }

    /**
     * {@.ja Factory初期化処理}
     * {@.en Factory initialization}
     */
    public static void init() {
	// Buffers
	CdrRingBuffer.CdrRingBufferInit();
	
	// Threads
	PeriodicTask.PeriodicTaskInit();
	
	// Publishers
	PublisherFlush.PublisherFlushInit();
	PublisherNew.PublisherNewInit();
	PublisherPeriodic.PublisherPeriodicInit();
	
	// Providers/Consumer
	InPortCorbaCdrProvider.InPortCorbaCdrProviderInit();
	InPortCorbaCdrConsumer.InPortCorbaCdrConsumerInit();
	OutPortCorbaCdrConsumer.OutPortCorbaCdrConsumerInit();
	OutPortCorbaCdrProvider.OutPortCorbaCdrProviderInit();
    }
}
