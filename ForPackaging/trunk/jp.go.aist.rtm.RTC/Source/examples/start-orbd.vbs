'orbd起動用スクリプト
'本スクリプトは環境変数TEMPが設定されていることを前提とします
'なお、環境変数TEMPは通例OSによりデフォルトで設定されています


'起動用オブジェクトの取得
Set objShell = WScript.CreateObject("WScript.Shell")

strMode = objShell.Environment("Process").Item("PROCESSOR_ARCHITECTURE")

'JDKのレジストリキーをセット
If UCase(strMode) = "X86" Then
	regJDKkey  = "HKLM\SOFTWARE\JavaSoft\Java Development Kit"
Else
	regJDKkey  = "HKLM\SOFTWARE\Wow6432Node\JavaSoft\Java Development Kit"
End If

'レジストリからJDKカレントバージョンを取得
'objShell.RegRead("HKLM\SOFTWARE\JavaSoft\Java Development Kit\CurrentVersion")

'これが、例えば"1.5"だとすると、
'HKLM\SOFTWARE\JavaSoft\Java Development Kit\1.5\JavaHome
'にJDKカレントバージョンのルートフォルダが記載されている
Javahome  = regJDKkey & "\" & objShell.RegRead(regJDKkey & "\CurrentVersion") & "\JavaHome"

'JDKカレントバージョンのルートフォルダJavahomeの下のbin\orbd.exeが目的の実行ファイル
targetexe = """" & objShell.RegRead(Javahome) & "\bin\orbd.exe"""

'目的の実行ファイルtargetexeを適切なオプションをつけて実行させる
objShell.Run targetexe & " -ORBInitialPort 2809 -ORBInitialHost localhost -defaultdb ""%TEMP%""\orb.db"
'これは例えば、次のようなことをやってる。ただし、作業ディレクトリをユーザーのtempフォルダに指定。
'cf:objShell.Run """C:\Program Files\Java\jdk1.5.0_14\bin\orbd.exe"" -ORBInitialPort 2809 -ORBInitialHost localhost"

'一応オブジェクトを開放
Set objShell = Nothing


' **********************************************************
' OS バージョンの取得
' **********************************************************
Function GetOSVersion()

    Dim strComputer, Wmi, colTarget, strWork, objRow, aData

    strComputer = "."
    Set Wmi = GetObject("winmgmts:{impersonationLevel=impersonate}!\\" & strComputer & "\root\cimv2")
    Set colTarget = Wmi.ExecQuery( "select Version from Win32_OperatingSystem" )

    For Each objRow in colTarget
        strWork = objRow.Version
        Next

        aData = Split( strWork, "." )
        strWork = aData(0) & "." & aData(1)

        GetOSVersion = CDbl( strWork )

End Function