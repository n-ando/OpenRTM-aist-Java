package jp.go.aist.rtm.RTC.util;
import com.sun.jna.Library;
import com.sun.jna.Native;


  /**
   * {@.ja LinuxのCライブラリ用インターフェイス}
   * {@.en Interface for C library}
   *
   *
   */
public interface Libc extends Library {
    Libc INSTANCE = (Libc) Native.loadLibrary("libc", Libc.class);

    int getpid();
    int sched_setaffinity(final int pid,
                          final int cpusetsize,
                          final cpu_set_t cpuset);
    int syscall(int number, Object... args);

}
