package jp.go.aist.rtm.RTC;

import java.util.ArrayList;
import java.util.BitSet;

import com.sun.jna.Platform;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.LongByReference;

import jp.go.aist.rtm.RTC.util.cpu_set_t;
import jp.go.aist.rtm.RTC.util.Kernel32;
import jp.go.aist.rtm.RTC.log.Logbuf;

  /**
   * {@.ja CPU affinity Class For Windows }
   * {@.en CPU affinity Class For Windows }
   *
   */
public class CPUAffinityWindows implements ICPUAffinity {

    /**
     * {@.ja コンストラクタ}
     * {@.en Constructor}
     *
     *
     */
    public CPUAffinityWindows(){
        rtcout = new Logbuf("CPUAffinityWindows");
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
        final int pid = Kernel32.INSTANCE.GetCurrentProcess();
        rtcout.println(Logbuf.DEBUG, "pid:" + pid);
        //System.out.println("pid:" + Kernel32.INSTANCE.GetCurrentProcessId());

        //PROCESS_QUERY_INFORMATION(0x400) 
        //PROCESS_SET_INFORMATION(0x200)
        Kernel32.INSTANCE.OpenProcess(0x0600, false, pid);

        WinDef.DWORD aff;
        long[] longs = cpu_set.toLongArray();
        switch (longs.length) {
            case 0:
                aff = new WinDef.DWORD(0);
                break;
            case 1:
                aff = new WinDef.DWORD(longs[0]);
                break;
            default:
                aff = new WinDef.DWORD(longs[0]);
                rtcout.println(Logbuf.DEBUG, "Windows API does not support "
                               + "more than 64 CPUs for thread affinity");
        }

        Kernel32.INSTANCE.SetProcessAffinityMask(pid, aff);
        final LongByReference cpuset1 = new LongByReference(0);
        final LongByReference cpuset2 = new LongByReference(0);
        int result = Kernel32.INSTANCE.GetProcessAffinityMask(pid,
                                    cpuset1,
                                    cpuset2);
        long[] longs_temp = new long[1];
        longs_temp[0] = cpuset1.getValue();
        if (BitSet.valueOf(longs_temp) != cpu_set) {
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
     * @param cpu_set
     *   {@.ja CPU番号のリスト}
     *   {@.en The list in which the number of the CPU is stocked}
     * 
     * @return 
     *   {@.ja 結果(成功:true，失敗:false)}
     *   {@.en Setup result (Successful:true, Failed:false)}
     */
    @Override
    public boolean setThreadAffinity(ArrayList<Integer> cpu_num_list){
        rtcout.println(Logbuf.DEBUG, "setThreadAffinity");

        BitSet cpu_set = new BitSet();
        cpu_set.clear();

        for(int ic=0;ic<cpu_num_list.size();++ic){
            int num = cpu_num_list.get(ic).intValue();
            cpu_set.set(num);
        }
        rtcout.println(Logbuf.DEBUG, "cpu_set:" + cpu_set.isEmpty());

        final int pid = Kernel32.INSTANCE.GetCurrentThread();
        rtcout.println(Logbuf.DEBUG, "pid:" + pid);
        //System.out.println("pid:" + Kernel32.INSTANCE.GetCurrentThreadId());

        WinDef.DWORD aff;
        long[] longs = cpu_set.toLongArray();
        switch (longs.length) {
            case 0:
                aff = new WinDef.DWORD(0);
                break;
            case 1:
                aff = new WinDef.DWORD(longs[0]);
                break;
            default:
                aff = new WinDef.DWORD(longs[0]);
                rtcout.println(Logbuf.DEBUG, "Windows API does not support "
                               + "more than 64 CPUs for thread affinity");
        }

        WinDef.DWORD result = Kernel32.INSTANCE.SetThreadAffinityMask(pid, aff);
        if (result != aff) {
            rtcout.println(Logbuf.DEBUG, "false");
            return false;
        }
        else{
            rtcout.println(Logbuf.DEBUG, "true");
            return true;
        }
    }
    /**
     * {@.ja Logging用フォーマットオブジェクト}
     * {@.en Format object for Logging}
     */
    protected Logbuf rtcout;
}
