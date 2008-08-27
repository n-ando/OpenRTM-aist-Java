package jp.go.aist.rtm.systemeditor.ui.editor;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

import jp.go.aist.rtm.repositoryView.RepositoryViewPlugin;
import jp.go.aist.rtm.repositoryView.ui.views.RepositoryView;
import jp.go.aist.rtm.systemeditor.ui.util.ComponentUtil;
import jp.go.aist.rtm.systemeditor.ui.util.ProfileHandler;
import jp.go.aist.rtm.toolscommon.model.component.ComponentFactory;
import jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagramKind;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.FileEditorInput;

/**
 * SystemDiagramEditorクラス
 */
public class OfflineSystemDiagramEditor extends AbstractSystemDiagramEditor {

	/**
	 * システムダイアグラムエディタのID
	 */
	public static final String OFFLINE_SYSTEM_DIAGRAM_EDITOR_ID = "jp.go.aist.rtm.systemeditor.ui.editor.OfflineSystemDiagramEditor";

	private String diagramName = "Offline System Diagram";

	/**
	 * コンストラクタ
	 */
	public OfflineSystemDiagramEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void initializeGraphicalViewer() {

		super.initializeGraphicalViewer();

		GraphicalViewer viewer = getGraphicalViewer();

		getSystemDiagram().setEditorId(getEditorId());
		getSystemDiagram().setKind(SystemDiagramKind.OFFLINE_LITERAL);
		viewer.setContents(getSystemDiagram());
	}

	protected IEditorInput load(IEditorInput input, final IEditorSite site,
			final boolean doReplace) throws PartInitException {
		boolean newOpenEditor = input instanceof NullEditorInput;// 新規エディタ

		IEditorInput result = input;
		if (newOpenEditor) {
			// void
		} else if (!(input instanceof FileEditorInput)) {
			IPath path = ((IPathEditorInput) input).getPath();
			IFile file = getOutsideIFileLink(path);
			result = new FileEditorInput(file);
		}

		if (newOpenEditor) {
			setSystemDiagram(ComponentFactory.eINSTANCE.createSystemDiagram());
		} else if (result instanceof FileEditorInput) {
			try {
				final String strPath =((FileEditorInput) result).getPath().toOSString();

				ProgressMonitorDialog dialog = new ProgressMonitorDialog(site
						.getShell());
				IRunnableWithProgress runable = new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor)
							throws InvocationTargetException,
							InterruptedException {

						monitor.beginTask("ファイルのオープンを行っています", 100);
						monitor.subTask("ファイルをオープンし、必要に応じてツリーから最新情報の取得を行っています...");
						monitor.internalWorked(20);

						try {
							RepositoryView repositoryViewerPart = 
								  (RepositoryView)getSite().getWorkbenchWindow().getActivePage().
								  findView(RepositoryViewPlugin.PLUGIN_ID +  ".view");
							ArrayList models = (ArrayList)repositoryViewerPart.getModel();
							
							ProfileHandler handler = new ProfileHandler();
							SystemDiagram diagram = handler.convertToSystemEditor(strPath, models, getSystemDiagramEditor());

							for (Object object : diagram.getComponents()) {
								if (!(object instanceof ComponentSpecification)) {
									throw new InvocationTargetException(new Exception());
								}
							}
							setSystemDiagram(diagram);
						} catch (Exception e) {
							monitor.done();
							throw new InvocationTargetException(e,
									"ファイルの読み込みに失敗しました。\r\nOffline System Diagram以外のファイルが読み込まれていないか確認してください。\n" + e.getMessage());
						}
						monitor.done();
					}
				};

				dialog.run(false, false, runable);
			} catch (Exception e) {
				throw new PartInitException("オープンに失敗しました。", e);
			}
		}

		if (newOpenEditor) {
			diagramName = "Offline System Diagram";
		} else if (result instanceof FileEditorInput) {
			diagramName = ((FileEditorInput) result).getPath().lastSegment();
		}

		getCommandStack().markSaveLocation(); // loadだがＯＫ
		firePropertyChange(IEditorPart.PROP_TITLE);

		this.setInput(result);
		ComponentUtil.setConnectorProfiles(getSystemDiagram());
		GraphicalViewer graphicalViewer2 = getGraphicalViewer();
		if (graphicalViewer2 != null) { // 初期ロードの場合には存在しない。別途後でロードする
			graphicalViewer2.setContents(getSystemDiagram());
		}

		getCommandStack().markSaveLocation();
		firePropertyChange(IEditorPart.PROP_TITLE);
		getSystemDiagram().setEditorId(OFFLINE_SYSTEM_DIAGRAM_EDITOR_ID);
		return result;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected String getDiagramName() {

		return diagramName;
	}

	@Override
	public String getEditorId() {
		return OFFLINE_SYSTEM_DIAGRAM_EDITOR_ID;
	}
	public OfflineSystemDiagramEditor getSystemDiagramEditor() {
		return this;
	}


}
