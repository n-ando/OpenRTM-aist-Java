package jp.go.aist.rtm.RTC;

import java.util.ArrayList;
import java.util.BitSet;
import java.lang.Integer;

  /**
   * {@.ja CPU affinity用インターフェイス}
   * {@.en Interface for CPU affinity}
   *
   *
   */
public interface ICPUAffinity {
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
    public boolean setProcessAffinity(BitSet cpu_set);

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
    public boolean setThreadAffinity(ArrayList<Integer> cpu_num_list);
}
