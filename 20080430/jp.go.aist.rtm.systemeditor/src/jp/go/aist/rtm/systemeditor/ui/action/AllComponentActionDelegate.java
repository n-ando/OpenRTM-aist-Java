package jp.go.aist.rtm.systemeditor.ui.action;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jp.go.aist.rtm.systemeditor.ui.editor.SystemDiagramEditor;
import jp.go.aist.rtm.toolscommon.corba.CorbaUtil;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.LifeCycleState;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;
import jp.go.aist.rtm.toolscommon.model.core.ModelElement;
import jp.go.aist.rtm.toolscommon.model.core.Visiter;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorActionDelegate;
import org.eclipse.ui.IEditorPart;

import RTC.ExecutionContext;
import RTC.ExecutionContextHelper;

/**
 * AllStart,AllStopを実行するアクション
 */
public class AllComponentActionDelegate implements IEditorActionDelegate {

	/**
	 * AllStartに使用されるID。この値が、Plugin.XMLに指定されなければならない。
	 */
	public static final String ALL_START_ACTION_ID = AllComponentActionDelegate.class
			.getName()
			+ ".AllStart";

	/**
	 * AllStopに使用されるID。この値が、Plugin.XMLに指定されなければならない。
	 */
	public static final String ALL_STOP_ACTION_ID = AllComponentActionDelegate.class
			.getName()
			+ ".AllStop";

	/**
	 * AllActivateに使用されるID。この値が、Plugin.XMLに指定されなければならない。
	 */
	public static final String ALL_ACTIVATE_ACTION_ID = AllComponentActionDelegate.class
			.getName()
			+ ".AllActivate";

	/**
	 * AllDeactivateに使用されるID。この値が、Plugin.XMLに指定されなければならない。
	 */
	public static final String ALL_DEACTIVATE_ACTION_ID = AllComponentActionDelegate.class
			.getName()
			+ ".AllDeactivate";

	private SystemDiagramEditor targetEditor;

	/**
	 * アクションのメインの実行メソッド
	 * <p>
	 * 実行可否の確認を求め、実行を行う。
	 */
	public void run(final IAction action) {
		final SystemDiagram systemDiagram = targetEditor.getSystemDiagram();

		String comfirmMessage = "良いですか？";
		if (ALL_START_ACTION_ID.equals(action.getId())) {
			comfirmMessage = "すべてのコンポーネントをStartしても良いですか？";
		} else if (ALL_STOP_ACTION_ID.equals(action.getId())) {
			comfirmMessage = "すべてのコンポーネントをStopしても良いですか？";
		} else if (ALL_ACTIVATE_ACTION_ID.equals(action.getId())) {
			comfirmMessage = "すべてのコンポーネントをActivateしても良いですか？";
		} else if (ALL_DEACTIVATE_ACTION_ID.equals(action.getId())) {
			comfirmMessage = "すべてのコンポーネントをDeactivateしても良いですか？";
		}

		boolean isOk = MessageDialog.openConfirm(targetEditor.getSite()
				.getShell(), "確認", comfirmMessage);
		if (isOk) {

			ProgressMonitorDialog dialog = new ProgressMonitorDialog(
					targetEditor.getSite().getShell());
			IRunnableWithProgress runable = new IRunnableWithProgress() {
				public void run(IProgressMonitor monitor)
						throws InvocationTargetException, InterruptedException {

					monitor.beginTask("コンポーネントの状態を変更します", 100);

					monitor.subTask("コンポーネントへリクエストを送っています...");

					try {
						if (ALL_START_ACTION_ID.equals(action.getId())) {
							doAllStart(systemDiagram);
						} else if (ALL_STOP_ACTION_ID.equals(action.getId())) {
							doAllStop(systemDiagram);
						} else if (ALL_ACTIVATE_ACTION_ID
								.equals(action.getId())) {
							doAllActivate(systemDiagram);
						} else if (ALL_DEACTIVATE_ACTION_ID.equals(action
								.getId())) {
							doAllDectivate(systemDiagram);
						}
					} catch (Exception e) {
						throw new InvocationTargetException(e);
					}

					monitor.subTask("コンポーネントへリクエストを送りました。");
					monitor.done();
				}

			};

			try {
				dialog.run(false, false, runable);
			} catch (InvocationTargetException e) {
				MessageDialog.openError(targetEditor.getSite().getShell(),
						"エラー", "リクエストの送信中にエラーが発生しました:\r\n" + e.getMessage());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void doAllStop(SystemDiagram systemDiagram) {
		List<ExecutionContext> executionContextList = getExecutionContextList(systemDiagram);

		for (ExecutionContext executionContext : executionContextList) {
			try {
				executionContext.stop();
			} catch (Exception e) {
				e.printStackTrace(); // system error
			}
		}
	}

	private void doAllStart(SystemDiagram systemDiagram) {
		List<ExecutionContext> executionContextList = getExecutionContextList(systemDiagram);

		for (ExecutionContext executionContext : executionContextList) {
			try {
				executionContext.start();
			} catch (Exception e) {
				e.printStackTrace(); // system error
			}
		}
	}

	private void doAllActivate(SystemDiagram systemDiagram) {
		systemDiagram.accept(new Visiter() {
			public void visit(ModelElement element) {
				if (element instanceof Component) {
					for (Iterator iter = ((Component) element)
							.getLifeCycleStates().iterator(); iter.hasNext();) {
						LifeCycleState lifeCycleState = (LifeCycleState) iter
								.next();
						lifeCycleState.activateR();
					}
				}
			}
		});
	}

	private void doAllDectivate(SystemDiagram systemDiagram) {
		systemDiagram.accept(new Visiter() {
			public void visit(ModelElement element) {
				if (element instanceof Component) {
					for (Iterator iter = ((Component) element)
							.getLifeCycleStates().iterator(); iter.hasNext();) {
						LifeCycleState lifeCycleState = (LifeCycleState) iter
								.next();
						lifeCycleState.deactivateR();
					}
				}
			}
		});
	}

	/**
	 * ExcutionContextを重複しないように取得する
	 * 
	 * @param systemDiagram
	 * @return
	 */
	private List<ExecutionContext> getExecutionContextList(
			SystemDiagram systemDiagram) {
		Set<String> executionContextStringList = new HashSet<String>();
		for (Iterator iter = systemDiagram.eAllContents(); iter.hasNext();) {
			Object obj = iter.next();
			if (obj instanceof Component) {
				ExecutionContext[] get_contexts = ((Component) obj)
						.getCorbaObjectInterface().get_contexts();
				if (get_contexts != null) {
					for (ExecutionContext context : get_contexts) {
						executionContextStringList.add(context.toString());
					}
				}
			}
		}

		List<ExecutionContext> result = new ArrayList<ExecutionContext>();
		for (String executionContextString : executionContextStringList) {
			result.add(ExecutionContextHelper.narrow(CorbaUtil
					.stringToObject(executionContextString)));
		}

		return result;
	}

	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
		this.targetEditor = (SystemDiagramEditor) targetEditor;
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}
}
