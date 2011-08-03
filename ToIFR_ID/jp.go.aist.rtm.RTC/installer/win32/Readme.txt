OpenRTM-aist-Java Windows インストーラー作成ツールの解説

　作成日： 2010/3/29
　作成者： 白田

●　目次
1. 前提条件
2. ディレクトリ構成
3. ファイル構成
4. インストール動作
5. アンインストール動作


●　説明
1. 前提条件

　　本ツールは、Windows Installer XML(WiX)を使用して、msiファイルを
　　作成するものです。

　　本ツールを実行するには、下記ソフトウエアがインストールされている事を
　　前提とします。

　　　・Python2.4, 2.5, 2.6 の何れか
　　　・PyYAML-3.09.win32-py2.4, 2.5, 2.6 の何れか
　　　・WiX3.5 Toolset
　　　・環境変数の「PATH」と「PYTHONPATH」に、使用するPython情報が設定
　　　　されている事。


2. ディレクトリ

　　ローカルPCに、以下のディレクトリ構成でインストール対象ファイルが
　　準備してある事を前提としています。

　　C:\distribution
　　　　│
　　　　└─ OpenRTM-aist-Java-1.0.0
　　　　　　　└─ Source
　　　　　　　　　　├─examples
　　　　　　　　　　├─jar
　　　　　　　　　　├─JavaDoc
　　　　　　　　　　└─JavaDocEn

　　(1) OpenRTM-aist-Java-1.0.0 は、
　　　　Java版OpenRTM-aistのインストール対象ファイルであり、
　　　　buildRTC により作成されたファイルを含む。
　　　　下記ディレクトリより、ディレクトリ毎コピーして下さい。
　　　　　\jp.go.aist.rtm.RTC\installer\resources\Source

　　※上記構成は、msi作成バッチ build.cmd で定義しており、変更可能です。
　　　変更する場合、build.cmd, OpenRTMjavawxs.py の整合を取って下さい。


3. ファイル構成

　　[このディレクトリ]
　　　　│
　　　　├─ Bitmaps
　　　　│　　├─ bannrbmp.bmp 　インストール用バナー画像　※1
　　　　│　　└─ dlgbmp.bmp 　　インストール用ダイアログ画像　※1
　　　　├─ License.rtf　　　　　ライセンス　※1
　　　　├─ WiLangId.vbs 　　　　ユーティリティ　※1
　　　　├─ WiSubStg.vbs 　　　　ユーティリティ　※1
　　　　├─ langs.txt　　　　　　ランゲージ一覧　※1
　　　　├─ makewxs.py 　　　　　wxsdファイルジェネレータ　※1
　　　　├─ uuid.py　　　　　　　UUIDジェネレータ　※1
　　　　├─ yat.py 　　　　　　　Yamlテンプレートプロセッサ　※1
　　　　│
　　　　│
　　　　├─ License.txt　　ライセンス
　　　　├─ build.cmd　　　msi作成バッチ
　　　　├─ cleanup.cmd　　テンポラリファイル削除バッチ
　　　　│
　　　　├─ OpenRTM-aist-Java.wxs.in　OpenRTM-aist用WiXテンプレート
　　　　├─ WixUI_Mondo_RTM.wxs 　　　OpenRTM用ユーザー・インタフェイス
　　　　│
　　　　├─ OpenRTMjavawxs.py　　　OpenRTM-aist用wxsファイルジェネレータ
　　　　│
　　　　└─ WixUI_**-**.wxl　　　各国語メッセージローカライズ　※2


　　　※1は、C++版よりコピーしたもの。

　　　※2は、WixUI_Mondo_RTM.wxs 用に、インストールの種類を選択する画面で
　　　　表示するメッセージを、オリジナルのローカライズファイルを元に、
　　　　InstallScopeDlgDescription のメッセージを
　　　　SetupTypeDlgDescription よりコピーしています。


　　[ビルド後に使用するファイル]
　　　　│
　　　　├─ OpenRTM-aist-Java-1.0.0.msi　英語のインストーラー
　　　　│
　　　　└─ OpenRTM-aist-Java-1.0.0_**-**.msi　各国語毎のインストーラー

　　　※build.cmd を実行すると、複数のテンポラリファイルとmsiファイルが
　　　　作成されます。
　　　　任意のmsiファイルをご使用下さい。


4. インストール動作

　(1) インストールを行う場合、インストール先PCのレジストリをチェックし、
　　　JDK1.5, 1.6 の何れかが登録済みであるか判定します。
　　　何れも登録されていなければ、エラーメッセージを表示して、
　　　インストールを終了します。

　(2) JDK1.5, 1.6 の何れかが登録済みである場合
　　・Program Files フォルダへ ランタイムファイル、クラスリファレンス
　　　ファイル、examplesファイル をインストールします。

　(3) スタートボタンのプログラムメニューで
　　　・OpenRTM-aist -> Java -> documents より
　　　　日本語と英語のクラスリファレンスを選択出来ます。
　　　・OpenRTM-aist -> Java -> examples より
　　　　「Start orbd」と各example を選択出来ます。


5. アンインストール動作

　(1) アンインストールを行う場合、インストールファイルとは別に、
　　　Program Files\OpenRTM-aist\1.0\examples\Java ディレクトリ の
　　　ログファイル(*.log)を削除します。

以上
