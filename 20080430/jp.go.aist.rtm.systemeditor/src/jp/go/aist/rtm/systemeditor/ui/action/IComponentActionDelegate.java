package jp.go.aist.rtm.systemeditor.ui.action;

import java.lang.reflect.InvocationTargetException;
import java.util.Iterator;

import jp.go.aist.rtm.toolscommon.model.component.Component;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IObjectActionDelegate;
import org.eclipse.ui.IWorkbenchPart;

/**
 * RtcLinkの個々のコンポーネントそれぞれに対するアクション
 */
public class IComponentActionDelegate implements IObjectActionDelegate {
	/**
	 * Startに使用されるID。この値が、Plugin.xmlに指定されなければならない。
	 */
	public static final String START_ACTION_ID = IComponentActionDelegate.class
			.getName()
			+ ".executioncontext.Start";

	/**
	 * Stopに使用されるID。この値が、Plugin.xmlに指定されなければならない。
	 */
	public static final String STOP_ACTION_ID = IComponentActionDelegate.class
			.getName()
			+ ".executioncontext.Stop";

	/**
	 * Activateに使用されるID。この値が、Plugin.xmlに指定されなければならない。
	 */
	public static final String ACTIVATE_ACTION_ID = IComponentActionDelegate.class
			.getName()
			+ ".Activate";

	/**
	 * Deactivateに使用されるID。この値が、Plugin.xmlに指定されなければならない。
	 */
	public static final String DEACTIVATE_ACTION_ID = IComponentActionDelegate.class
			.getName()
			+ ".Deactivate";

	/**
	 * Resetに使用されるID。この値が、Plugin.xmlに指定されなければならない。
	 */
	public static final String RESET_ACTION_ID = IComponentActionDelegate.class
			.getName()
			+ ".Reset";

	/**
	 * Finalizeに使用されるID。この値が、Plugin.xmlに指定されなければならない。
	 */
	public static final String FINALIZE_ACTION_ID = IComponentActionDelegate.class
			.getName()
			+ ".Finalize";

	/**
	 * Exitに使用されるID。この値が、Plugin.xmlに指定されなければならない。
	 */
	public static final String EXIT_ACTION_ID = IComponentActionDelegate.class
			.getName()
			+ ".Exit";

	private ISelection selection;

	private IWorkbenchPart targetPart;

	/**
	 * {@inheritDoc}
	 */
	public void setActivePart(IAction action, IWorkbenchPart targetPart) {
		this.targetPart = targetPart;
	}

	/**
	 * アクション内部で使用される、メッセージとコマンドをまとめたインタフェース
	 */
	public interface MessageAndCommand {
		public String getConfirmMessage();

		public int run();
	}

	/**
	 * {@inheritDoc}
	 */
	public void run(final IAction action) {

		for (Iterator iter = ((IStructuredSelection) selection).iterator(); iter
				.hasNext();) {

			final Component component = (Component) iter.next();

			MessageAndCommand command = null;
			if ((START_ACTION_ID).equals(action.getId())) {
				command = new MessageAndCommand() {
					public String getConfirmMessage() {
						return "Startしても良いですか？";
					}

					public int run() {
						return component.startR();
					}
				};
			} else if (STOP_ACTION_ID.equals(action.getId())) {
				command = new MessageAndCommand() {
					public String getConfirmMessage() {
						return "Stopしても良いですか？";
					}

					public int run() {
						return component.stopR();
					}
				};
			} else if (ACTIVATE_ACTION_ID.equals(action.getId())) {
				command = new MessageAndCommand() {
					public String getConfirmMessage() {
						return "Activateしても良いですか？";
					}

					public int run() {
						return component.activateR();
					}
				};
			} else if (DEACTIVATE_ACTION_ID.equals(action.getId())) {
				command = new MessageAndCommand() {
					public String getConfirmMessage() {
						return "Deactivateしても良いですか？";
					}

					public int run() {
						return component.deactivateR();
					}
				};
			} else if (RESET_ACTION_ID.equals(action.getId())) {
				command = new MessageAndCommand() {
					public String getConfirmMessage() {
						return "Resetしても良いですか？";
					}

					public int run() {
						return component.resetR();
					}
				};
			} else if (EXIT_ACTION_ID.equals(action.getId())) {
				command = new MessageAndCommand() {
					public String getConfirmMessage() {
						return "Exitしても良いですか？";
					}

					public int run() {
						return component.exitR();
					}
				};
			} else if (FINALIZE_ACTION_ID.equals(action.getId())) {
				command = new MessageAndCommand() {
					public String getConfirmMessage() {
						return "Finalizeしても良いですか？";
					}

					public int run() {
						return component.finalizeR();
					}
				};
			} else {
				throw new RuntimeException("システムエラー");
			}

			boolean isOK = MessageDialog.openConfirm(targetPart.getSite()
					.getShell(), "確認", command.getConfirmMessage());
			if (isOK == false) {
				return;
			}

			final MessageAndCommand finalCommand = command;

			final int[] returnCode = new int[1]; // final配列化することで、クロージャ内で返り値を設定することができるようにする。
			ProgressMonitorDialog dialog = new ProgressMonitorDialog(targetPart
					.getSite().getShell());
			IRunnableWithProgress runable = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {

					monitor.beginTask("コンポーネントの状態を変更します", 100);

					monitor.worked(20);
					monitor.subTask("コンポーネントへリクエストを送っています...");

					returnCode[0] = finalCommand.run();

					monitor.subTask("コンポーネントへリクエストを送りました。");
					monitor.done();
				}
			};

			try {
				dialog.run(false, false, runable);
			} catch (Exception e) {
				e.printStackTrace(); // system error
			}

			if (Component.RETURNCODE_OK == returnCode[0]) {
				// void
			} else if (Component.RETURNCODE_ERROR == returnCode[0]) {
				MessageDialog.openError(targetPart.getSite().getShell(), "エラー",
						"エラーが発生しました。");
			} else if (Component.RETURNCODE_BAD_PARAMETER == returnCode[0]) {
				MessageDialog.openError(targetPart.getSite().getShell(), "エラー",
						"不正なパラメータです。");
			} else if (Component.RETURNCODE_UNSUPPORTED == returnCode[0]) {
				MessageDialog.openError(targetPart.getSite().getShell(), "エラー",
						"サポートされていません。");
			} else if (Component.RETURNCODE_OUT_OF_RESOURCES == returnCode[0]) {
				MessageDialog.openError(targetPart.getSite().getShell(), "エラー",
						"リソース不足のエラーが発生しました。");
			} else if (Component.RETURNCODE_PRECONDITION_NOT_MET == returnCode[0]) {
				MessageDialog.openError(targetPart.getSite().getShell(), "エラー",
						"事前条件が不正です。");
			}

		}

	}

	/**
	 * {@inheritDoc}
	 */
	public void selectionChanged(IAction action, ISelection selection) {
		this.selection = selection;
	}
}
