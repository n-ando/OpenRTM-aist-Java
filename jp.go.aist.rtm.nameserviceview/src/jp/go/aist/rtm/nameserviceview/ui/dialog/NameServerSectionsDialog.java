package jp.go.aist.rtm.nameserviceview.ui.dialog;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import jp.go.aist.rtm.nameserviceview.NameServiceViewPlugin;
import jp.go.aist.rtm.nameserviceview.corba.NameServerAccesser;
import jp.go.aist.rtm.nameserviceview.manager.NameServerManager;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

/**
 * ネームサーバを追加するダイアログ
 * <p>
 * コンボボックスには、接続が成功したことのあるアドレスの一覧が表示され、最後に接続したアドレスが選択される。
 */
public class NameServerSectionsDialog extends Dialog {

	/**
	 * 接続要求結果 接続に失敗したことを示す定数
	 */
	public static final int CANT_CONNECT = 1;

	/**
	 * 接続要求結果 接続に成功し、ネームサーバのオブジェクト作成に失敗したことを示す定数
	 */
	public static final int CANT_CREATE_OBJECT_TREE = 2;

	/**
	 * 接続要求結果 成功
	 */
	public static final int SUCCESS = 0;

	/**
	 * 接続が成功したことのあるアドレスの一覧を保存する、ワークスペース永続文字列へのキー
	 */
	public static final String COMBO_ITEMS_KEY = NameServerSectionsDialog.class
			.getName()
			+ ".combo.items";

	/**
	 * 最後に接続が成功したアドレスのインデックスを保存する、ワークスペース永続文字列へのキー
	 */
	public static final String COMBO_SELECTION_INDEX_KEY = NameServerSectionsDialog.class
			.getName()
			+ ".combo.selectIndex";

	private Combo combo;

	private String value = "";

	private Label message;

	/**
	 * コンストラクタ
	 * 
	 * @param shell
	 */
	public NameServerSectionsDialog(Shell shell) {
		super(shell);
		setShellStyle(getShellStyle() | SWT.CENTER | SWT.RESIZE);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Control createDialogArea(Composite parent) {
		GridLayout gridLayout = new GridLayout();

		Composite mainComposite = (Composite) super.createDialogArea(parent);
		mainComposite.setLayout(gridLayout);
		mainComposite.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label label = new Label(mainComposite, SWT.NONE);
		label.setText("ネームサーバのアドレスを入力してください。");
		GridData labelLayloutData = new GridData(
				GridData.HORIZONTAL_ALIGN_BEGINNING);
		label.setLayoutData(labelLayloutData);
		labelLayloutData.heightHint = 20;

		GridLayout comboLayout = new GridLayout(2, false);
		Composite comboComposite = new Composite(mainComposite, SWT.NONE);
		comboComposite.setLayout(comboLayout);
		comboComposite.setLayoutData(new GridData(GridData.FILL_BOTH));
		combo = new Combo(comboComposite, SWT.NONE);
		GridData comboGridData = new GridData(GridData.HORIZONTAL_ALIGN_CENTER);
		comboGridData.minimumWidth = 180;
		comboGridData.horizontalAlignment = GridData.FILL;
		comboGridData.grabExcessHorizontalSpace = true;
		combo.setLayoutData(comboGridData);
		Label comboLabel = new Label(comboComposite, SWT.NONE);
		comboLabel.setText("（Address:Port)");
		comboLabel
				.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_CENTER));

		message = new Label(mainComposite, SWT.NONE);
		message.setLayoutData(new GridData(GridData.FILL_BOTH));

		Label line = new Label(mainComposite, SWT.SEPARATOR | SWT.HORIZONTAL);
		line.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL));

		combo.setFocus();
		loadDefaultComboValue(combo);

		return mainComposite;
	}

	/**
	 * ワークスペースの永続情報から、コンボのリストと選択インデックスをロードする
	 * 
	 * @param combo
	 */
	private void loadDefaultComboValue(Combo combo) {
		String defaultString = NameServiceViewPlugin.getDefault().getPreferenceStore()
				.getString(COMBO_ITEMS_KEY);
		StringTokenizer tokenize = new StringTokenizer(defaultString, ",");
		while (tokenize.hasMoreTokens()) {
			combo.add(tokenize.nextToken());
		}

		combo.select(NameServiceViewPlugin.getDefault().getPreferenceStore().getInt(
				COMBO_SELECTION_INDEX_KEY));
	}

	/**
	 * 成功したアドレスおよび選択インデックスを、永続情報に設定する
	 * 
	 * @param combo
	 */
	private void addDefaultComboValue(Combo combo) {
		String value = combo.getText(); // local

		List<String> valueList = Arrays.asList(combo.getItems());
		if (valueList.contains(value) == false) {
			String defaultString = NameServiceViewPlugin.getDefault().getPreferenceStore()
					.getString(COMBO_ITEMS_KEY);

			String newString = "";
			if ("".equals(defaultString)) {
				newString = value;
			} else {
				newString = value + "," + defaultString;
			}

			NameServiceViewPlugin.getDefault().getPreferenceStore().setValue(
					COMBO_ITEMS_KEY, newString);
		}

		int selectionIndex = valueList.indexOf(value);
		if (selectionIndex == -1) { // 新しい入力を行った場合
			selectionIndex = 0;
		}
		NameServiceViewPlugin.getDefault().getPreferenceStore().setValue(
				COMBO_SELECTION_INDEX_KEY, selectionIndex);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void configureShell(Shell shell) {
		super.configureShell(shell);
		shell.setText("Connect Name Server");
	}

	@Override
	/**
	 * {@inheritDoc}
	 * <p>
	 * OKボタン押下時に実行を行い、問題ないならば、成功したアドレスを保存する。
	 */
	protected void okPressed() {
		value = combo.getText();
		if (execute(value)) {
			addDefaultComboValue(combo);
			super.okPressed();
		} else {
			combo.setFocus();
		}
	}

	/**
	 * アドレスのバリデートを行い、問題ない場合にはアドレスを追加する
	 * <P>
	 * バリデートとしては以下を行う
	 * <ol>
	 * <li>必須チェック</li>
	 * <li>接続済みアドレスであるかどうか</li>
	 * <li>接続できるか</li>
	 * <li>オブジェクトツリーが作成できるか</li>
	 * </ol>
	 * 
	 * @param address
	 * @return
	 */
	private boolean execute(final String address) {
		if ("".equals(address)) {
			message.setText("アドレスを設定してください。");
			return false;
		}

		boolean isExist = NameServerManager.getInstance().isExist(address);
		if (isExist) {
			message.setText("既に接続済みのアドレスです。");
			return false;
		}

		ProgressMonitorDialog dialog = new ProgressMonitorDialog(getShell());
		ConnectToNameServer runable = new ConnectToNameServer(address);
		try {
			dialog.run(false, false, runable);
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		if (runable.getResult() == CANT_CONNECT) {
			message.setText("ネームサービスへの接続に失敗しました。");
			return false;
		} else if (runable.getResult() == CANT_CREATE_OBJECT_TREE) {
			message.setText("オブジェクトツリーの作成に失敗しました。");
			return false;
		}

		return true;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * ネームサーバへアクセスし、オブジェクト群を構築して、ネームサーバを追加する
	 */
	private final class ConnectToNameServer implements IRunnableWithProgress {
		private final String value;

		private int result;

		/**
		 * 結果を返す。（CANT_CONNECT,CANT_CREATE_OBJECT_TREE,SUCCESS）
		 * 
		 * @return 結果
		 */
		public int getResult() {
			return result;
		}

		private ConnectToNameServer(String value) {
			super();
			this.value = value;
		}

		/**
		 * {@inheritDoc}
		 */
		public void run(IProgressMonitor monitor)
				throws InvocationTargetException, InterruptedException {
			result = CANT_CONNECT;
			try {
				monitor.beginTask("ネームサーバへの接続を行っています...", 100);
				monitor.worked(20);
				boolean validateNameService = NameServerAccesser.getInstance()
						.validateNameServerAddress(value);

				if (validateNameService) {
					result = CANT_CREATE_OBJECT_TREE;
					monitor.worked(50);
					monitor.setTaskName("ネームサーバからオブジェクトを取得しています...");

					NameServerManager.getInstance().addNameServer(value);

					result = SUCCESS;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			monitor.done();
		}
	}
	
	@Override
	protected Point getInitialSize() {
		return getShell().computeSize(SWT.DEFAULT, SWT.DEFAULT, true);
	}

}
