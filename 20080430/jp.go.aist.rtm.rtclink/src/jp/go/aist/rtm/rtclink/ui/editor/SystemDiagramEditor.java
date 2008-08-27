package jp.go.aist.rtm.rtclink.ui.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.EventObject;

import jp.go.aist.rtm.rtclink.RtcLinkPlugin;
import jp.go.aist.rtm.rtclink.factory.CorbaWrapperFactory;
import jp.go.aist.rtm.rtclink.manager.PreferenceManager;
import jp.go.aist.rtm.rtclink.model.component.ComponentFactory;
import jp.go.aist.rtm.rtclink.model.component.SystemDiagram;
import jp.go.aist.rtm.rtclink.restoration.Restoration;
import jp.go.aist.rtm.rtclink.restoration.Result;
import jp.go.aist.rtm.rtclink.ui.editor.action.ChangeComponentDirectionAction;
import jp.go.aist.rtm.rtclink.ui.editor.action.MoveComponentAction;
import jp.go.aist.rtm.rtclink.ui.editor.action.OpenAction;
import jp.go.aist.rtm.rtclink.ui.editor.action.OpenAndRestoreAction;
import jp.go.aist.rtm.rtclink.ui.editor.dnd.SystemDiagramDropTargetListener;
import jp.go.aist.rtm.rtclink.ui.editor.editpart.AutoScrollAutoexposeHelper;
import jp.go.aist.rtm.rtclink.ui.editor.editpart.factory.SystemDiagramEditPartFactory;
import jp.go.aist.rtm.rtclink.ui.views.propertysheetview.RtcPropertySheetPage;

import org.eclipse.core.internal.resources.File;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.gef.AutoexposeHelper;
import org.eclipse.gef.ContextMenuProvider;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.ui.actions.SaveAction;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.internal.editors.text.JavaFileEditorInput;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.IPropertySheetPage;

/**
 * SystemDiagramEditorクラス
 */
public class SystemDiagramEditor extends GraphicalEditor {

	/**
	 * システムダイアグラムの拡張子
	 */
	public static final String FILE_EXTENTION = "rtclink";

	/**
	 * RTCLinkのプロジェクト名
	 */
	public static String RTCLINK_PROJECT_NAME = "RTCLink_Files";

	/**
	 * RTCLink新規エディタ
	 */
	public static String RTCLINK_NEW_EDITOR_PATH = "RTCLink";

	/**
	 * システムダイアグラムエディタのID
	 */
	public static final String SYSTEM_DIAGRAM_EDITOR_ID = "jp.go.aist.rtm.rtclink.ui.editor.SystemDiagramEditor";

	private String title = "SystemEditor";

	/**
	 * コンストラクタ
	 */
	public SystemDiagramEditor() {
		setEditDomain(new DefaultEditDomain(this));
	}

	private SystemDiagram systemDiagram;

	@SuppressWarnings("unchecked")
	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void createActions() {
		super.createActions();

		IAction action;

		action = new MoveComponentAction(this,
				MoveComponentAction.MOVE_UP_ACTION_ID);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MoveComponentAction(this,
				MoveComponentAction.MOVE_DOWN_ACTION_ID);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MoveComponentAction(this,
				MoveComponentAction.MOVE_RIGHT_ACTION_ID);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MoveComponentAction(this,
				MoveComponentAction.MOVE_LEFT_ACTION_ID);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MoveComponentAction(this,
				MoveComponentAction.MOVE_UP_L_ACTION_ID);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MoveComponentAction(this,
				MoveComponentAction.MOVE_DOWN_L_ACTION_ID);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MoveComponentAction(this,
				MoveComponentAction.MOVE_RIGHT_L_ACTION_ID);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MoveComponentAction(this,
				MoveComponentAction.MOVE_LEFT_L_ACTION_ID);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new ChangeComponentDirectionAction(this,
				ChangeComponentDirectionAction.HORIZON_DIRECTION_ACTION_ID);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new ChangeComponentDirectionAction(this,
				ChangeComponentDirectionAction.VERTICAL_DIRECTION_ACTION_ID);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		// getActionRegistry().registerAction(
		// new AllComponentActionDelegateWrapper(
		// AllComponentActionDelegate.ALL_START_ACTION_ID,
		// "All Start"));
		// getActionRegistry().registerAction(
		// new AllComponentActionDelegateWrapper(
		// AllComponentActionDelegate.ALL_STOP_ACTION_ID,
		// "All Stop"));
		// getActionRegistry().registerAction(
		// new AllComponentActionDelegateWrapper(
		// AllComponentActionDelegate.ALL_ACTIVATE_ACTION_ID,
		// "All Activate"));
		// getActionRegistry().registerAction(
		// new AllComponentActionDelegateWrapper(
		// AllComponentActionDelegate.ALL_DEACTIVATE_ACTION_ID,
		// "All Deactivate"));

		action = new SaveAction(this) {
			@Override
			protected boolean calculateEnabled() {
				return true;
			}

			@Override
			protected void init() {
				setId(ActionFactory.SAVE_AS.getId());
				setText("Save As...");
				setToolTipText("Save As...");
			}

			public void run() {
				doSaveAs();
			}
		};
		getActionRegistry().registerAction(action);
		getPropertyActions().add(action.getId());

		action = new OpenAction(this);
		getActionRegistry().registerAction(action);
		getPropertyActions().add(action.getId());

		action = new OpenAndRestoreAction(this);
		getActionRegistry().registerAction(action);
		getPropertyActions().add(action.getId());
	}

	@Override
	protected void createGraphicalViewer(Composite parent) {
		GraphicalViewer viewer = new ScrollingGraphicalViewer() {

			@Override
			public EditPart findObjectAtExcluding(Point pt, Collection exclude,
					Conditional condition) {
				EditPart result = super.findObjectAtExcluding(pt, exclude,
						condition);
				if (condition instanceof AutoexposeHelper.Search
						&& ((AutoexposeHelper.Search) condition).result == null) {
					((AutoexposeHelper.Search) condition).result = (AutoexposeHelper) getRootEditPart()
							.getAdapter(AutoexposeHelper.class);
				}

				// if (condition instanceof AutoexposeHelper.Search
				// && ((AutoexposeHelper.Search) condition).result != null) {
				// System.out.println();
				// result = super.findObjectAtExcluding(pt, exclude,
				// condition);
				// }

				return result;
			}
		};
		viewer.createControl(parent);
		setGraphicalViewer(viewer);
		configureGraphicalViewer();
		hookGraphicalViewer();
		initializeGraphicalViewer();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();

		GraphicalViewer viewer = getGraphicalViewer();
		viewer.setRootEditPart(new ScalableRootEditPart() {
			@Override
			public Object getAdapter(Class adapter) {
				if (adapter == AutoexposeHelper.class) {
					return new AutoScrollAutoexposeHelper(this);
				}

				return super.getAdapter(adapter);
			}
		});
		viewer.setEditPartFactory(new SystemDiagramEditPartFactory(
				getActionRegistry()));
	}

	/**
	 * 設定の変更に対するリスナ
	 */
	PropertyChangeListener preferenceListener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			systemDiagram.setSynchronizeInterval(PreferenceManager
					.getInstance().getInterval(
							PreferenceManager.SYNC_SYSTEMEDITOR_INTERVAL));
		}
	};

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void initializeGraphicalViewer() {
		GraphicalViewer viewer = getGraphicalViewer();
		viewer
				.addDropTargetListener((TransferDropTargetListener) new SystemDiagramDropTargetListener(
						viewer));

		ContextMenuProvider provider = new SystemDiagramContextMenuProvider(
				viewer, getActionRegistry());
		viewer.setContextMenu(provider);
		((IEditorSite) getSite()).registerContextMenu(provider, viewer, false);

		buildKeyHandler(viewer);

		PreferenceManager.getInstance().addPropertyChangeListener(
				preferenceListener);

		viewer.setContents(systemDiagram);
	}

	private void buildKeyHandler(GraphicalViewer viewer) {
		KeyHandler keyHandler = new KeyHandler();
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_UP, 0),
				getActionRegistry().getAction(
						MoveComponentAction.MOVE_UP_ACTION_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_DOWN, 0),
				getActionRegistry().getAction(
						MoveComponentAction.MOVE_DOWN_ACTION_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_RIGHT, 0),
				getActionRegistry().getAction(
						MoveComponentAction.MOVE_RIGHT_ACTION_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_LEFT, 0),
				getActionRegistry().getAction(
						MoveComponentAction.MOVE_LEFT_ACTION_ID));

		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_UP, SWT.SHIFT),
				getActionRegistry().getAction(
						MoveComponentAction.MOVE_UP_L_ACTION_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_DOWN, SWT.SHIFT),
				getActionRegistry().getAction(
						MoveComponentAction.MOVE_DOWN_L_ACTION_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_RIGHT, SWT.SHIFT),
				getActionRegistry().getAction(
						MoveComponentAction.MOVE_RIGHT_L_ACTION_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_LEFT, SWT.SHIFT),
				getActionRegistry().getAction(
						MoveComponentAction.MOVE_LEFT_L_ACTION_ID));

//		keyHandler.put(KeyStroke.getPressed(115, SWT.CONTROL),
//				getActionRegistry().getAction(ActionFactory.SAVE.getId()));
		keyHandler.put(KeyStroke.getPressed(SWT.F2, 0),
				getActionRegistry().getAction(ActionFactory.SAVE.getId()));
		keyHandler.put(KeyStroke.getPressed(SWT.DEL, 127, 0),
				getActionRegistry().getAction(ActionFactory.DELETE.getId()));

		viewer.setKeyHandler(keyHandler);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void doSave(IProgressMonitor monitor) {
		boolean newOpenEditor = getEditorInput() instanceof NullEditorInput; // 新規エディタ

		if (newOpenEditor) {
			doSaveAs();
			return;
		}

		IFile file = ((IFileEditorInput) getEditorInput()).getFile();

		try {
			save(file, monitor);
		} catch (CoreException e) {
			ErrorDialog.openError(getSite().getShell(), "Error During Save",
					"The current model could not be saved.", e.getStatus());
		}
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void doSaveAs() {
		boolean newOpenEditor = getEditorInput() instanceof NullEditorInput; // 新規エディタ

		IFile oldFile = null;
		if (newOpenEditor) {
			// void
		} else {
			oldFile = ((FileEditorInput) getEditorInput()).getFile();
		}

		IFile newFile = createNewFile(oldFile, SWT.SAVE);

		if (newFile != null) {
			final IFile target = newFile; // final
			ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(
					getSite().getShell());

			try {
				progressMonitorDialog.run(false, false,
						new IRunnableWithProgress() {
							public void run(IProgressMonitor monitor)
									throws InvocationTargetException,
									InterruptedException {
								try {
									// if (newPath.toFile().exists() == false) {
									// try {
									// newPath.toFile().createNewFile();
									// } catch (IOException e) {
									// throw new RuntimeException(e); //
									// SystemError
									// }
									// }

									save(target, monitor);
									// getMultiPageCommandStackListener().markSaveLocations();
								} catch (CoreException e) {
								}
							}
						});
			} catch (Exception e) {
				throw new RuntimeException(e); // SystemError
			}
		}
	}

	private IFile createNewFile(IFile oldFile, int style) {
		final IPath newPath = getFilePathByDialog(oldFile, style);

		IFile newFile = null;
		if (newPath != null) {
			if (newPath.toFile().exists() == false) {
				try {
					newPath.toFile().createNewFile();
				} catch (IOException e) {
					MessageDialog.openError(getSite().getShell(), "エラー",
							"ファイルの作成に失敗しました。" + newPath.toOSString());
				}
			}

			if (newPath.toFile().exists()) {
				newFile = getOutsideIFileLink(newPath);
			}
		}
		return newFile;
	}

	private IPath getFilePathByDialog(IFile defaultFile, int style) {
		FileDialog dialog = new FileDialog(getSite().getShell(), style);

		if (defaultFile != null) {
			dialog.setFileName(defaultFile.toString());
			dialog.setFilterExtensions(new String[] { "*." + FILE_EXTENTION });
		}

		String pathString = dialog.open();

		if (pathString == null) {
			return null;
		}

		IPath result = new Path(pathString);
		if (result.getFileExtension() == null) {
			result = result.addFileExtension(FILE_EXTENTION);
		}

		return result;
	}

	private void save(IFile file, IProgressMonitor progressMonitor)
			throws CoreException {

		if (null == progressMonitor)
			progressMonitor = new NullProgressMonitor();

		progressMonitor.beginTask("Saving " + file.getLocation().toOSString(),
				2);

		try {
			progressMonitor.worked(1);

			ResourceSet resourceSet = new ResourceSetImpl();
			Resource resource = resourceSet.createResource(URI
					.createFileURI(file.getLocation().toOSString()));

			CorbaWrapperFactory.getInstance().saveContentsToResource(resource,
					systemDiagram);

			file.refreshLocal(IResource.DEPTH_ZERO, null);

			changeFile(file);

			progressMonitor.done();
		} catch (FileNotFoundException e) {
			IStatus status = new Status(IStatus.ERROR, RtcLinkPlugin
					.getDefault().getClass().getName(), 0,
					"Error writing file.", e);
			throw new CoreException(status);
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, RtcLinkPlugin
					.getDefault().getClass().getName(), 0,
					"Error writing file.", e);
			throw new CoreException(status);
		}
	}

	private void changeFile(IFile file) {

		setInput(new FileEditorInput(file));

		title = file.getLocation().lastSegment();
		getCommandStack().markSaveLocation();
		firePropertyChange(IEditorPart.PROP_TITLE);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void commandStackChanged(EventObject event) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(event);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public boolean isDirty() {
		return getCommandStack().isDirty();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		IEditorInput newInput = load(input, site, false);
		super.init(site, newInput);
	}

	private IEditorInput load(IEditorInput input, final IEditorSite site,
			final boolean doReplace) throws PartInitException {
		boolean newOpenEditor = input instanceof NullEditorInput;// 新規エディタ

		IEditorInput result = input;
		if (newOpenEditor) {
			// void
		} else if (input instanceof JavaFileEditorInput) {
			IPath path = ((JavaFileEditorInput) input).getPath();

			IFile file = getOutsideIFileLink(path);

			result = new FileEditorInput(file);
		}

		if (newOpenEditor) {
			systemDiagram = ComponentFactory.eINSTANCE.createSystemDiagram();
		} else if (result instanceof FileEditorInput) {
			try {
				ResourceSet resourceSet = new ResourceSetImpl();

				final Resource resource = resourceSet.createResource(URI
						.createFileURI(((FileEditorInput) result).getPath()
								.toOSString()));
				if (systemDiagram != null) {
					systemDiagram.setSynchronizeInterval(0);
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
							systemDiagram = (SystemDiagram) CorbaWrapperFactory
									.getInstance().loadContentFromResource(
											resource);
						} catch (IOException e) {
							throw new InvocationTargetException(e,
									"ファイルの読み込みに失敗しました。\r\nRtcLink以外のファイルが読み込まれていないか確認してください。");
						}
						monitor.internalWorked(35);

						if (doReplace) {
							monitor.subTask("システムの復元を行っています...");
							doReplace(systemDiagram, site);
						}
						monitor.done();
					}
				};

				dialog.run(false, false, runable);
			} catch (Exception e) {
				throw new PartInitException("オープンに失敗しました。", e);
			}
		}

		systemDiagram.setSynchronizeInterval(PreferenceManager.getInstance()
				.getInterval(PreferenceManager.SYNC_SYSTEMEDITOR_INTERVAL));

		if (newOpenEditor) {
			title = "SystemDiagram";
		} else if (result instanceof FileEditorInput) {
			title = ((FileEditorInput) result).getPath().lastSegment();
		}

		getCommandStack().markSaveLocation(); // loadだがＯＫ
		firePropertyChange(IEditorPart.PROP_TITLE);

		this.setInput(result);

		GraphicalViewer graphicalViewer2 = getGraphicalViewer();
		if (graphicalViewer2 != null) { // 初期ロードの場合には存在しない。別途後でロードする
			graphicalViewer2.setContents(systemDiagram);
		}

		getCommandStack().markSaveLocation();
		firePropertyChange(IEditorPart.PROP_TITLE);

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
			Dialog dialog = new jp.go.aist.rtm.rtclink.ui.dialog.ErrorDialog(
					site.getShell(), "エラー", null, "復元に失敗しました。\r\n", buffer
							.toString(), MessageDialog.ERROR);
			dialog.open();
		}
	}

	private SimpleDateFormat formater = new SimpleDateFormat(
			"yyyy_MM_dd_hh_mm_ss_SSS");

	private IFile getOutsideIFileLink(IPath path) {
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		IProject project = ws.getRoot().getProject(RTCLINK_PROJECT_NAME);
		if (!project.exists())
			try {
				project.create(null);
			} catch (CoreException e) {
				throw new RuntimeException(e); // systemError
			}
		if (!project.isOpen())
			try {
				project.open(null);
			} catch (CoreException e) {
				throw new RuntimeException(e); // systemError
			}

		IFile fileLink = null;
		try {
			for (IResource r : project.members()) {
				if (r.getType() == IResource.FILE) {
					File file = (File) r;
					if (file.isLinked()
							&& (path.equals(file.getRawLocation()) || path
									.equals(file.getRawLocation()))) {
						fileLink = file;
						break;
					}
				}
			}
		} catch (CoreException e2) {
			// void
		}

		if (fileLink == null) {
			fileLink = project.getFile(formater.format(new Date()) + "__"
					+ path.lastSegment());
			try {
				project.refreshLocal(IResource.DEPTH_INFINITE, null);
			} catch (CoreException e1) {
				throw new RuntimeException(e1); // systemError
			}
			if (fileLink.exists() == false) {
				try {
					fileLink.createLink(path, IResource.NONE, null);
				} catch (CoreException e) {
					throw new RuntimeException(e); // systemError
				}
			}
		}

		return fileLink;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void dispose() {
		super.dispose();

		PreferenceManager.getInstance().removePropertyChangeListener(
				preferenceListener);

		systemDiagram.setSynchronizeInterval(-1);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Object getAdapter(Class type) {
		if (type.equals(IPropertySheetPage.class)) {
			return new RtcPropertySheetPage();
		}

		return super.getAdapter(type);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String getTitle() {
		return title;
	}

	public void open(boolean restore) {
		boolean save = false;
		if (isDirty()) {
			save = MessageDialog.openConfirm(getSite().getShell(), "",
					"ファイルが保存されていません。保存しますか？");
		}

		if (save) {
			doSave(null);
		}

		IFile createNewFile = createNewFile(null, SWT.OPEN);
		if (createNewFile != null) {
			try {
				load(new FileEditorInput(createNewFile), getEditorSite(),
						restore);
			} catch (PartInitException e) {
				e.printStackTrace(); // system error
				if(e.getStatus().getException() != null)
					MessageDialog.openError(getSite().getShell(), "", e.getStatus().getException().getMessage());
			}
		}
	}

	/**
	 * SystemDiagramを取得する
	 * 
	 * @return
	 */
	public SystemDiagram getSystemDiagram() {
		return systemDiagram;
	}

}
