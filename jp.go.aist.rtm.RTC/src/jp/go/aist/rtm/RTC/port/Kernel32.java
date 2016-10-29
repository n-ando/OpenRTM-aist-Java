package jp.go.aist.rtm.RTC.port;
import com.sun.jna.Library;
import com.sun.jna.platform.win32.WinNT;
/*
import com.sun.jna.Native;
import com.sun.jna.platform.win32.BaseTSD.SIZE_T;
import com.sun.jna.platform.win32.WinDef.DWORD;
import com.sun.jna.platform.win32.WinDef.DWORDByReference;
import com.sun.jna.platform.win32.WinDef.PVOID;
import com.sun.jna.platform.win32.WinDef.BOOL;
import com.sun.jna.platform.win32.WinNT.HANDLE;
*/


import com.sun.jna.Native;
import com.sun.jna.Pointer;
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
    Kernel32 INSTANCE = (Kernel32)Native.loadLibrary("kernel32" , Kernel32.class);
    HANDLE CreateFileMappingA(
        HANDLE  hFile,                // ファイルハンドル
        WinNT.SECURITY_ATTRIBUTES psa,// セキュリティ指定
        int   fdwProtect,           // ファイルデータ保護属性
        int   dwMaxSizeHigh,        // サイズの上位32ビット
        int   dwMaxSizeLow,         // サイズの下位32ビット
        String  pszName               // オブジェクトの名前
    );
    Pointer MapViewOfFile(
        HANDLE hMappingObject,        // マッピングオブジェクトのハンドル
        int  dwDesiredAccess,       // データのアクセス方法
        int  dwFileOffsetHigh,      // オフセットの上位32ビット
        int  dwFileOffsetLow,       // オフセットの下位32ビット
        int dwNumberOfByteToMap    // マッピングをするバイト数
    );
    boolean UnmapViewOfFile(
        Pointer pBaseAddress            // ビューのベースアドレス
    );
    boolean CloseHandle(
        HANDLE hObject                // オブジェクトのハンドル
    );
/*
    HANDLE CreateFileMappingA(
        HANDLE  hFile,                // ファイルハンドル
        WinNT.SECURITY_ATTRIBUTES psa,// セキュリティ指定
        DWORD   fdwProtect,           // ファイルデータ保護属性
        DWORD   dwMaxSizeHigh,        // サイズの上位32ビット
        DWORD   dwMaxSizeLow,         // サイズの下位32ビット
        String  pszName               // オブジェクトの名前
    );
    PVOID MapViewOfFile(
        HANDLE hMappingObject,        // マッピングオブジェクトのハンドル
        DWORD  dwDesiredAccess,       // データのアクセス方法
        DWORD  dwFileOffsetHigh,      // オフセットの上位32ビット
        DWORD  dwFileOffsetLow,       // オフセットの下位32ビット
        SIZE_T dwNumberOfByteToMap    // マッピングをするバイト数
    );
    BOOL UnmapViewOfFile(
        PVOID pBaseAddress            // ビューのベースアドレス
    );
    BOOL CloseHandle(
        HANDLE hObject                // オブジェクトのハンドル
    );
*/
}
