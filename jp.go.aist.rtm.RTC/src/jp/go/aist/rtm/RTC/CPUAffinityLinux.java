package jp.go.aist.rtm.RTC;

import java.util.ArrayList;
import java.util.BitSet;

import com.sun.jna.Platform;

import jp.go.aist.rtm.RTC.util.cpu_set_t;
import jp.go.aist.rtm.RTC.util.Libc;
import jp.go.aist.rtm.RTC.log.Logbuf;


  /**
   * {@.ja CPU affinity Class For Linux }
   * {@.en CPU affinity Class For Linux }
   *
   */
public class CPUAffinityLinux implements ICPUAffinity {

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     *
     */
    public CPUAffinityLinux(){
        rtcout = new Logbuf("CPUAffinityLinux");
    }
    
    /**
     * {@.ja プロセスのCPUアフィニティを設定}
     * {@.en Setting the CPU affinity of a process}
     * 
     * <p>
     * @param cpu_set
     *   {@.ja CPU番号のリスト(ビット表現)}
     *   {@.en The list in which the number of the CPU is stocked}
     * 
     * @return 
     *   {@.ja 結果(成功:true，失敗:false)}
     *   {@.en Setup result (Successful:true, Failed:false)}
     */
    @Override
    public boolean setProcessAffinity(BitSet cpu_set){
        rtcout.println(Logbuf.DEBUG, "setProcessAffinity");
        rtcout.println(Logbuf.DEBUG, "cpu_set:" + cpu_set.isEmpty());
        final Libc lib = Libc.INSTANCE;
        final int pid = lib.getpid();
        rtcout.println(Logbuf.DEBUG, "pid:" + pid);
        final cpu_set_t cpuset = new cpu_set_t();
        final int size = cpu_set_t.SIZE_OF_CPU_SET_T;
        rtcout.println(Logbuf.DEBUG, "size:" + size);
        final long[] bits = cpu_set.toLongArray();
        for (int i = 0; i < bits.length; i++) {
            rtcout.println(Logbuf.DEBUG, "bit[" + i + "]:"+bits[i]);
            rtcout.println(Logbuf.DEBUG, "Platform64:"+Platform.is64Bit());
            if (Platform.is64Bit()) {
                cpuset.__bits[i].setValue(bits[i]);
            } else {
                cpuset.__bits[i * 2].setValue(bits[i] & 0xFFFFFFFFL);
                cpuset.__bits[i * 2 + 1].setValue((bits[i] >>> 32) & 0xFFFFFFFFL);
            }
        }

        if (lib.sched_setaffinity(pid, size, cpuset) != 0) {
            rtcout.println(Logbuf.DEBUG, "false");
            return false;
        }
        else{
            rtcout.println(Logbuf.DEBUG, "true");
            return true;
        }
    }
    /**
     * {@.ja スレッドのCPUアフィニティを設定}
     * {@.en Setting the CPU affinity of a process}
     * 
     * <p>
     * @param cpu_num_list
     *   {@.ja CPU番号のリスト}
     *   {@.en The list in which the number of the CPU is stocked}
     * 
     * @return 
     *   {@.ja 結果(成功:true，失敗:false)}
     *   {@.en Setup result (Successful:true, Failed:false)}
     */
    @Override
    public boolean setThreadAffinity(ArrayList<Integer> cpu_num_list){
        final int SYS_gettid = Platform.is64Bit() ? 186 : 224;

        BitSet cpu_set = new BitSet();
        cpu_set.clear();

        for(int ic=0;ic<cpu_num_list.size();++ic){
            int num = cpu_num_list.get(ic).intValue();
            cpu_set.set(num);
        }
        final Libc lib = Libc.INSTANCE;
        int tid = lib.syscall(SYS_gettid);
        rtcout.println(Logbuf.DEBUG, "tid:" + tid);

        final cpu_set_t cpuset = new cpu_set_t();
        final int size = cpu_set_t.SIZE_OF_CPU_SET_T;
        final long[] bits = cpu_set.toLongArray();
        for (int ic = 0; ic < bits.length; ic++) {
            if (Platform.is64Bit()) {
                cpuset.__bits[ic].setValue(bits[ic]);
            } else {
                cpuset.__bits[ic * 2].setValue(bits[ic] & 0xFFFFFFFFL);
                cpuset.__bits[ic * 2 + 1].setValue((bits[ic] >>> 32) & 0xFFFFFFFFL);
            }
        }


        if (lib.sched_setaffinity(tid, size, cpuset) != 0) {
            return false;
        }
        else{
            return true;
        }
    }
    /**
     * {@.ja Logging用フォーマットオブジェクト}
     * {@.en Format object for Logging}
     */
    protected Logbuf rtcout;
}
