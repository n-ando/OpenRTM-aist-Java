package jp.go.aist.rtm.systemeditor.ui.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import jp.go.aist.rtm.systemeditor.factory.SystemEditorWrapperFactory;
import jp.go.aist.rtm.systemeditor.manager.SystemEditorPreferenceManager;
import jp.go.aist.rtm.systemeditor.restoration.Restoration;
import jp.go.aist.rtm.systemeditor.restoration.Result;
import jp.go.aist.rtm.systemeditor.ui.editor.action.OpenAndRestoreAction;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.ComponentFactory;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.MessageDialog;
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
public class SystemDiagramEditor extends AbstractSystemDiagramEditor {

	/**
	 * システムダイアグラムエディタのID
	 */
	public static final String SYSTEM_DIAGRAM_EDITOR_ID = "jp.go.aist.rtm.systemeditor.ui.editor.SystemDiagramEditor";

	private String diagramName = "System Diagram";

	/**
	 * コンストラクタ
	 */
	public SystemDiagramEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void createActions() {
		super.createActions();

		IAction action;

		action = new OpenAndRestoreAction(this);
		getActionRegistry().registerAction(action);
		getPropertyActions().add(action.getId());
	}

	/**
	 * 設定の変更に対するリスナ
	 */
	PropertyChangeListener preferenceListener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			getSystemDiagram()
					.setSynchronizeInterval(
							SystemEditorPreferenceManager
									.getInstance()
									.getInterval(
											SystemEditorPreferenceManager.SYNC_SYSTEMEDITOR_INTERVAL));
		}
	};

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void initializeGraphicalViewer() {

		super.initializeGraphicalViewer();

		GraphicalViewer viewer = getGraphicalViewer();

		ContextMenuProvider provider = new SystemDiagramContextMenuProvider(
				viewer, getActionRegistry());
		viewer.setContextMenu(provider);
		((IEditorSite) getSite()).registerContextMenu(provider, viewer, false);

		SystemEditorPreferenceManager.getInstance().addPropertyChangeListener(
				preferenceListener);

		getSystemDiagram().setEditorId(getEditorId());
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
				ResourceSet resourceSet = new ResourceSetImpl();

				final Resource resource = resourceSet.createResource(URI
						.createFileURI(((FileEditorInput) result).getPath()
								.toOSString()));
				if (getSystemDiagram() != null) {
					getSystemDiagram().setSynchronizeInterval(0);
				}

				ProgressMonitorDialog dialog = new ProgressMonitorDialog(site
						.getShell());
				IRunnableWithProgress runable = new IRunnableWithProgress() {
					public void run(IProgressMonitor monitor)
							throws InvocationTargetException,
							InterruptedException {

						monitor.beginTask("ファイルのオープンを行っています", 100);

						monitor
								.subTask("ファイルをオープンし、必要に応じてツリーから最新情報の取得を行っています...");
						monitor.internalWorked(20);

						try {
							SystemDiagram diagram = (SystemDiagram) SystemEditorWrapperFactory
							.getInstance().loadContentFromResource(
									resource);
							for (Object object : diagram.getComponents()) {
								if (!(object instanceof Component)) {
									throw new InvocationTargetException(new Exception(),
									"ファイルの読み込みに失敗しました。\r\nSystem Diagram以外のファイルが読み込まれていないか確認してください。");
								}
							}
							setSystemDiagram(diagram);
						} catch (IOException e) {
							throw new InvocationTargetException(e,
									"ファイルの読み込みに失敗しました。\r\nSystem Diagram以外のファイルが読み込まれていないか確認してください。");
						}
						monitor.internalWorked(35);

						if (doReplace) {
							monitor.subTask("システムの復元を行っています...");
							doReplace(getSystemDiagram(), site);
						}
						monitor.done();
					}
				};

				dialog.run(false, false, runable);
			} catch (Exception e) {
				throw new PartInitException("オープンに失敗しました。", e);
			}
		}

		getSystemDiagram()
				.setSynchronizeInterval(
						SystemEditorPreferenceManager
								.getInstance()
								.getInterval(
										SystemEditorPreferenceManager.SYNC_SYSTEMEDITOR_INTERVAL));

		if (newOpenEditor) {
			diagramName = "System Diagram";
		} else if (result instanceof FileEditorInput) {
			diagramName = ((FileEditorInput) result).getPath().lastSegment();
		}

		getCommandStack().markSaveLocation(); // loadだがＯＫ
		firePropertyChange(IEditorPart.PROP_TITLE);

		this.setInput(result);

		GraphicalViewer graphicalViewer2 = getGraphicalViewer();
		if (graphicalViewer2 != null) { // 初期ロードの場合には存在しない。別途後でロードする
			graphicalViewer2.setContents(getSystemDiagram());
		}

		getCommandStack().markSaveLocation();
		firePropertyChange(IEditorPart.PROP_TITLE);
		getSystemDiagram().setEditorId(SYSTEM_DIAGRAM_EDITOR_ID);
		return result;
	}

	/**
	 * ロード時の復元を行います。
	 */
	private void doReplace(SystemDiagram systemDiagram, IEditorSite site) {
		final StringBuffer buffer = new StringBuffer();
		Result resultHolder = new Result() {
			private boolean success;

			public boolean isSuccess() {
				return success;
			}

			public void setSuccess(boolean success) {
				this.success = success;
			}

			public void putResult(String resultPart) {
				buffer.append(resultPart + "\r\n");
			}
		};
		Restoration.execute(systemDiagram, resultHolder);
		if (resultHolder.isSuccess() == false) {
			Dialog dialog = new jp.go.aist.rtm.toolscommon.ui.dialog.ErrorDialog(
					site.getShell(), "エラー", null, "復元に失敗しました。\r\n", buffer
							.toString(), MessageDialog.ERROR);
			dialog.open();
		}
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void dispose() {
		super.dispose();

		SystemEditorPreferenceManager.getInstance()
				.removePropertyChangeListener(preferenceListener);

		getSystemDiagram().setSynchronizeInterval(-1);
	}

	@Override
	public String getEditorId() {
		return SYSTEM_DIAGRAM_EDITOR_ID;
	}

	@Override
	protected String getDiagramName() {

		return diagramName;
	}
}
