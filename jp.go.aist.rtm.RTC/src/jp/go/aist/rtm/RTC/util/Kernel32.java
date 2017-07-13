package jp.go.aist.rtm.RTC.util;
import com.sun.jna.Library;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinDef;


import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.PointerType;
import com.sun.jna.win32.StdCallLibrary;
import com.sun.jna.win32.W32APIOptions;


/**
 * {@.ja Kernel32 クラス}
 * {@.en Kernel32 class}
 * <p>
 * {@.ja WindowsAPIラッパクラス
 * JNAを使用してWindowsAPIを呼び出す}
 * {@.en WindowsAPI wrappers class
 * Calls windowsAPI via JNA.}
 *
 */
public interface Kernel32 extends Library,StdCallLibrary, WinNT {
    Kernel32 INSTANCE = (Kernel32)Native.loadLibrary("kernel32" , 
                                                     Kernel32.class);

    int GetProcessAffinityMask(
        final int pid, 
        final PointerType lpProcessAffinityMask, 
        final PointerType lpSystemAffinityMask
    );
    WinDef.DWORD SetThreadAffinityMask(
        final int pid, 
        final WinDef.DWORD lpProcessAffinityMask
    );

    boolean SetProcessAffinityMask(
        final int pid, 
        final WinDef.DWORD dwProcessAffinityMask
    );

    int GetCurrentThread();

    int GetCurrentProcess();

    int GetCurrentThreadId();

    int GetCurrentProcessId();

    HANDLE OpenProcess(
        int dwDesiredAccess,  // アクセスフラグ
        boolean bInheritHandle,    // ハンドルの継承オプション
        int dwProcessId       // プロセス識別子
    );
    boolean CloseHandle(
        HANDLE hObject                // オブジェクトのハンドル
    );
}
