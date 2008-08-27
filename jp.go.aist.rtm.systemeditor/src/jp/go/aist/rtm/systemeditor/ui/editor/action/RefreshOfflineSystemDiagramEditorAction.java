//package jp.go.aist.rtm.systemeditor.ui.editor.action;
//
//import java.lang.reflect.InvocationTargetException;
//import java.util.Iterator;
//
//import jp.go.aist.rtm.systemeditor.ui.action.OpenCompositeComponentAction;
//import jp.go.aist.rtm.systemeditor.ui.editor.OfflineSystemDiagramEditor;
//import jp.go.aist.rtm.systemeditor.ui.util.ComponentUtil;
//import jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification;
//import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;
//import jp.go.aist.rtm.toolscommon.model.component.util.SpecificationUtil;
//
//import org.eclipse.core.runtime.IProgressMonitor;
//import org.eclipse.jface.action.IAction;
//import org.eclipse.jface.dialogs.MessageDialog;
//import org.eclipse.jface.dialogs.ProgressMonitorDialog;
//import org.eclipse.jface.operation.IRunnableWithProgress;
//import org.eclipse.jface.viewers.ISelection;
//import org.eclipse.ui.IEditorActionDelegate;
//import org.eclipse.ui.IEditorPart;
//import org.eclipse.ui.IPropertyListener;
//
///**
// * ネームサーバのリフレッシュを行うアクション
// */
//public class RefreshOfflineSystemDiagramEditorAction implements
//		IEditorActionDelegate {
//	private OfflineSystemDiagramEditor targetEditor;
//
//	private IAction iaction;
//
//	public void setActiveEditor(IAction action, IEditorPart targetEditor) {
//		this.iaction = action;
//		this.targetEditor = (OfflineSystemDiagramEditor) targetEditor;
//		if (targetEditor != null) {
//			targetEditor.addPropertyListener(new IPropertyListener() {
//				public void propertyChanged(Object source, int propId) {
//					iaction.setEnabled(isEnabled());
//				}
//			});
//		}
//	}
//
//	public void run(IAction action) {
//		ProgressMonitorDialog dialog = new ProgressMonitorDialog(targetEditor
//				.getSite().getShell());
//		final IRunnableWithProgress runable = new IRunnableWithProgress() {
//			public void run(IProgressMonitor monitor)
//					throws InvocationTargetException, InterruptedException {
//
//				SystemDiagram rootSystemDiagram = ComponentUtil
//						.getRootSystemDiagram(targetEditor.getSystemDiagram());
//				monitor.beginTask("オフラインシステムエディタをリフレッシュします。", rootSystemDiagram
//						.getComponents().size() + 1);
//				int i = 0;
//				for (Iterator iterator = rootSystemDiagram.getComponents()
//						.iterator(); iterator.hasNext();) {
//					Object object = iterator.next();
//					if (object instanceof ComponentSpecification
//							&& ((ComponentSpecification) object).getPathId() != null) {
//						try {
//							monitor.worked(++i);
//							monitor.subTask("コンポーネント"
//									+ ((ComponentSpecification) object)
//											.getInstanceNameL()
//									+ "リフレッシュしています...");
//							if (((ComponentSpecification) object).isCompositeComponent()){
//								if (((ComponentSpecification) object).getOpenCompositeComponentAction() != null){
//									OpenCompositeComponentAction action = (OpenCompositeComponentAction) ((ComponentSpecification) object)
//											.getOpenCompositeComponentAction();
//									for (Iterator ite = action
//											.getCompositeComponentEditor()
//											.getSystemDiagram().getComponents()
//											.iterator(); ite.hasNext();) {
//										Object component = ite.next();
//										if (!((ComponentSpecification)component).isCompositeComponent()) {
//											SpecificationUtil
//												.reloadComponent(
//													((ComponentSpecification) component)
//															.getPathId(),
//													(ComponentSpecification) component);
//										}
//										
//									}
//								}
//							}else {
//								SpecificationUtil
//									.reloadComponent(
//											((ComponentSpecification) object)
//													.getPathId(),
//											(ComponentSpecification) object);
//							}
//						} catch (Exception e) {
//							monitor.subTask("オフラインシステムエディタのリフレッシュが失敗しました。");
//							monitor.done();
//							throw new InvocationTargetException(e);
//						}
//					}
//				}
//				monitor.subTask("オフラインシステムエディタをリフレッシュしました。");
//				monitor.done();
//			}
//		};
//
//		try {
//			dialog.run(false, false, runable);
//		} catch (InvocationTargetException e) {
//			MessageDialog.openWarning(targetEditor.getSite().getShell(),
//					"Warning", "オフラインシステムエディタのリフレッシュが失敗しました。");
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//	}
//
//	public void selectionChanged(IAction action, ISelection selection) {
//		action.setEnabled(isEnabled());
//	}
//
//	private boolean isEnabled() {
//		if (targetEditor != null && targetEditor.getSystemDiagram() != null) {
//			return !targetEditor.getSystemDiagram().getComponents().isEmpty();
//		}
//
//		return true;
//	}
//
//}
