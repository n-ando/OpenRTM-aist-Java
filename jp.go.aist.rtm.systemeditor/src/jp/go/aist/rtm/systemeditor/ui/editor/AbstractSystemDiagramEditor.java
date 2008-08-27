package jp.go.aist.rtm.systemeditor.ui.editor;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.EventObject;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.xml.datatype.DatatypeFactory;

import jp.go.aist.rtm.systemeditor.RTSystemEditorPlugin;
import jp.go.aist.rtm.systemeditor.factory.SystemEditorWrapperFactory;
import jp.go.aist.rtm.systemeditor.ui.action.OpenCompositeComponentAction;
import jp.go.aist.rtm.systemeditor.ui.dialog.ProfileInformationDialog;
import jp.go.aist.rtm.systemeditor.ui.editor.action.ChangeComponentDirectionAction;
import jp.go.aist.rtm.systemeditor.ui.editor.action.MoveComponentAction;
import jp.go.aist.rtm.systemeditor.ui.editor.action.OpenAction;
import jp.go.aist.rtm.systemeditor.ui.editor.dnd.SystemDiagramDropTargetListener;
import jp.go.aist.rtm.systemeditor.ui.editor.editpart.AutoScrollAutoexposeHelper;
import jp.go.aist.rtm.systemeditor.ui.editor.editpart.factory.SystemDiagramEditPartFactory;
import jp.go.aist.rtm.systemeditor.ui.util.ComponentUtil;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagramKind;
import jp.go.aist.rtm.toolscommon.synchronizationframework.SynchronizationSupport;
import jp.go.aist.rtm.toolscommon.ui.views.propertysheetview.RtcPropertySheetPage;

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
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.ui.actions.SaveAction;
import org.eclipse.gef.ui.parts.GraphicalEditor;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogConstants;
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
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.views.properties.IPropertySheetPage;

import com.sun.org.apache.xerces.internal.jaxp.datatype.DatatypeFactoryImpl;

public abstract class AbstractSystemDiagramEditor extends GraphicalEditor {

	/**
	 * システムダイアグラムの拡張子
	 */
	public static final String FILE_EXTENTION = "xml";

	/**
	 * RTCLinkのプロジェクト名
	 */
	public static String RTCLINK_PROJECT_NAME = "RTSE_Files";

	private String title = null;

	private SystemDiagram systemDiagram;

	private SystemDiagramPropertyChangeListener systemDiagramPropertyChangeListener;

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

		action = new OpenCompositeComponentAction(this);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

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


	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void initializeGraphicalViewer() {
		GraphicalViewer viewer = getGraphicalViewer();
		viewer
				.addDropTargetListener((TransferDropTargetListener) new SystemDiagramDropTargetListener(
						viewer));

		ContextMenuProvider provider = new AbstractSystemDiagramContextMenuProvider(
				viewer, getActionRegistry());
		viewer.setContextMenu(provider);
		((IEditorSite) getSite()).registerContextMenu(provider, viewer, false);

		buildKeyHandler(viewer);

		systemDiagramPropertyChangeListener = new SystemDiagramPropertyChangeListener(
				PlatformUI.getWorkbench().getActiveWorkbenchWindow()
						.getActivePage());
		systemDiagram
				.addPropertyChangeListener(systemDiagramPropertyChangeListener);
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

		viewer.setKeyHandler(keyHandler);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void doSave(IProgressMonitor monitor) {
		IEditorInput input = getEditorInput();
		IFile file = null;
		OpenCompositeComponentAction action = null;
		if (this.systemDiagram.getOpenCompositeComponentAction() != null) {
			action = (OpenCompositeComponentAction) this.systemDiagram
					.getOpenCompositeComponentAction();
		}
		while (action != null) {
			input = action.getParentSystemDiagramEditor().getEditorInput();
			action = (OpenCompositeComponentAction) action
					.getParentSystemDiagramEditor().getSystemDiagram()
					.getOpenCompositeComponentAction();
		}
		boolean newOpenEditor = input instanceof NullEditorInput; // 新規エディタ
		if (newOpenEditor) {
			doSaveAs();
			return;
		}
		if (!ComponentUtil.isSystemDiagramSynchronized(systemDiagram)) {
			MessageDialog.openInformation(getSite().getShell(), "Information",
					"複合コンポーネント【"
							+ ((OpenCompositeComponentAction) systemDiagram
									.getOpenCompositeComponentAction())
									.getCompositeComponent().getInstanceNameL()
							+ "】が同期されていない為、保存できません。");
			return;
		}
		if( getSystemDiagram().getKind() == SystemDiagramKind.OFFLINE_LITERAL ) {
			ProfileInformationDialog dialog = new ProfileInformationDialog(getSite().getShell());
//			dialog.setInputPath(oldFile.getFullPath().toOSString());
			dialog.setSystemId(getSystemDiagram().getSystemId());
			dialog.setOverWrite(true);
			if( dialog.open() == IDialogConstants.OK_ID ) {
				 String systemId = "RTSystem:" + dialog.getInputVendor() + "." 
				 					+ dialog.getInputSystemName() + ":"
				 					+ dialog.getInputVersion();
				 getSystemDiagram().setSystemId(systemId);
				 DatatypeFactory dateFactory = new DatatypeFactoryImpl();
				 getSystemDiagram().setUpdateDate(dateFactory.newXMLGregorianCalendar(new GregorianCalendar()).toString());
			} else {
				return;
			}
		}
		file = ((IFileEditorInput) input).getFile();
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
		IEditorInput input = getEditorInput();
		IFile oldFile = null;
		OpenCompositeComponentAction action = null;
		if (this.systemDiagram.getOpenCompositeComponentAction() != null) {
			action = (OpenCompositeComponentAction) this.systemDiagram
					.getOpenCompositeComponentAction();
		}
		while (action != null) {
			input = action.getParentSystemDiagramEditor().getEditorInput();
			action = (OpenCompositeComponentAction) action
					.getParentSystemDiagramEditor().getSystemDiagram()
					.getOpenCompositeComponentAction();
		}
		if (!ComponentUtil.isSystemDiagramSynchronized(systemDiagram)) {
			MessageDialog.openInformation(getSite().getShell(), "Information",
					"複合コンポーネント【"
							+ ((OpenCompositeComponentAction) systemDiagram
									.getOpenCompositeComponentAction())
									.getCompositeComponent().getInstanceNameL()
							+ "】が同期されていない為、保存できません。");
			return;
		}
		boolean newOpenEditor = input instanceof NullEditorInput; // 新規エディタ
		if (newOpenEditor) {
			// void
		} else {
			oldFile = ((FileEditorInput) input).getFile();
		}

		IFile newFile = null;
		if( getSystemDiagram().getKind() == SystemDiagramKind.OFFLINE_LITERAL ) {
			ProfileInformationDialog dialog = new ProfileInformationDialog(getSite().getShell());
//			dialog.setInputPath(oldFile.getFullPath().toOSString());
//			dialog.setSystemId(getSystemDiagram().getSystemId());
			if( dialog.open() == IDialogConstants.OK_ID ) {
				 IPath result = new Path(dialog.getInputPath());
				 newFile = createNewFile(result);
				 String systemId = "RTSystem:" + dialog.getInputVendor() + "." 
				 					+ dialog.getInputSystemName() + ":"
				 					+ dialog.getInputVersion();
				 getSystemDiagram().setSystemId(systemId);
				 DatatypeFactory dateFactory = new DatatypeFactoryImpl();
				 getSystemDiagram().setCreationDate(dateFactory.newXMLGregorianCalendar(new GregorianCalendar()).toString());
				 getSystemDiagram().setUpdateDate(dateFactory.newXMLGregorianCalendar(new GregorianCalendar()).toString());
			} else {
				return;
			}
		} else {
			newFile = createNewFilebySelection(oldFile, SWT.SAVE);
		}

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
									save(target, monitor);
								} catch (CoreException e) {
									MessageDialog.openError(getSite().getShell(), "エラー",
											"ファイルの保存に失敗しました。" + target.getName());
								}
							}
						});
			} catch (Exception e) {
				throw new RuntimeException(e); // SystemError
			}
		}
	}

	private IFile createNewFilebySelection(IFile oldFile, int style) {
		final IPath newPath = getFilePathByDialog(oldFile, style);
		return createNewFile(newPath);
	}

	private IFile createNewFile(IPath newPath) {
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
		}
		dialog.setFilterExtensions(new String[] { "*." + FILE_EXTENTION });

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

	protected void save(IFile file, IProgressMonitor progressMonitor)
			throws CoreException {
		SystemDiagram systemDiagram = null;
		List<AbstractSystemDiagramEditor> editors = new ArrayList<AbstractSystemDiagramEditor>();
		OpenCompositeComponentAction action = null;
		editors.add(this);
		// 子エディタを取得
		for (Iterator iterator = this.systemDiagram.getComponents().iterator(); iterator
				.hasNext();) {
			AbstractComponent compnent = (AbstractComponent) iterator.next();
			if (compnent.isCompositeComponent()
					&& compnent.getOpenCompositeComponentAction() != null) {
				editors.add(((OpenCompositeComponentAction) compnent
						.getOpenCompositeComponentAction())
						.getCompositeComponentEditor());
			}
		}
		// 親エディタを取得
		if (this.systemDiagram.getOpenCompositeComponentAction() != null) {
			action = (OpenCompositeComponentAction) this.systemDiagram
					.getOpenCompositeComponentAction();
			while (action != null) {
				editors.add(action.getParentSystemDiagramEditor());

				systemDiagram = action.getParentSystemDiagramEditor()
						.getSystemDiagram();
				for (Iterator iterator = this.systemDiagram.getComponents()
						.iterator(); iterator.hasNext();) {
					AbstractComponent component = (AbstractComponent) iterator
							.next();
					AbstractComponent localObject = null;
					if (component.getCorbaBaseObject() != null) {
						localObject = (AbstractComponent) SynchronizationSupport
								.findLocalObjectByRemoteObject(
										new Object[] { component
												.getCorbaBaseObject() },
										systemDiagram);
					} else {
						localObject = ComponentUtil.findComponentByPathId(
								component, systemDiagram);
					}

					if (localObject != null) {
						localObject.getConstraint().setX(
								component.getConstraint().getX());
						localObject.getConstraint().setY(
								component.getConstraint().getY());
						localObject.getConstraint().setHeight(
								component.getConstraint().getHeight());
						localObject.getConstraint().setWidth(
								component.getConstraint().getWidth());
					}
				}

				action = (OpenCompositeComponentAction) action
						.getParentSystemDiagramEditor().getSystemDiagram()
						.getOpenCompositeComponentAction();
			}
		} else {
			systemDiagram = this.systemDiagram;
		}
		if (null == progressMonitor)
			progressMonitor = new NullProgressMonitor();

		progressMonitor.beginTask("Saving " + file.getLocation().toOSString(),
				2);

		try {
			progressMonitor.worked(1);

			Resource resource = null;
			ResourceSet resourceSet = new ResourceSetImpl();
			resource = resourceSet.createResource(URI
					.createFileURI(file.getLocation().toOSString()));

			SystemEditorWrapperFactory.getInstance().saveContentsToResource(
					resource, systemDiagram);

			file.refreshLocal(IResource.DEPTH_ZERO, null);
			for (AbstractSystemDiagramEditor editor : editors) {
				editor.changeFile(file);
			}
			progressMonitor.worked(2);
			progressMonitor.done();
		} catch (FileNotFoundException e) {
			progressMonitor.done();
			IStatus status = new Status(IStatus.ERROR, RTSystemEditorPlugin.getDefault()
					.getClass().getName(), 0, "Error writing file.", e);
			throw new CoreException(status);
		} catch (Exception e) {
			progressMonitor.done();
			IStatus status = new Status(IStatus.ERROR, RTSystemEditorPlugin.getDefault()
					.getClass().getName(), 0, "Error writing file.", e);
			throw new CoreException(status);
		}
	}

	public void changeFile(IFile file) {
		if (this.systemDiagram.getOpenCompositeComponentAction() != null) {
			title = ((OpenCompositeComponentAction) this.systemDiagram
					.getOpenCompositeComponentAction()).getCompositeComponent()
					.getInstanceNameL();
		} else {
			setInput(new FileEditorInput(file));
			title = file.getLocation().lastSegment();
		}
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
		if( this instanceof OfflineSystemDiagramEditor ) {
			input = new NullEditorInput();
		}
		IEditorInput newInput = load(input, site, false);
		super.init(site, newInput);
	}

	protected abstract IEditorInput load(IEditorInput input,
			final IEditorSite site, final boolean doReplace)
			throws PartInitException;

	private SimpleDateFormat formater = new SimpleDateFormat(
			"yyyy_MM_dd_hh_mm_ss_SSS");

	protected IFile getOutsideIFileLink(IPath path) {
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

		if (getSystemDiagramPropertyChangeListener() != null) {
			getSystemDiagramPropertyChangeListener().dispose();
		}

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
		return (title == null) ? getDiagramName() : title;
	}

	protected abstract String getDiagramName();

	public void open(boolean restore) {
		boolean save = false;
		if (isDirty()) {
			save = MessageDialog.openQuestion(getSite().getShell(), "",
					"ファイルが保存されていません。保存しますか？");
		}

		if (save) {
			doSave(null);
		}

		IFile createNewFile = createNewFilebySelection(null, SWT.OPEN);
		if (createNewFile != null) {
			try {
				load(new FileEditorInput(createNewFile), getEditorSite(),
						restore);
			} catch (PartInitException e) {
				e.printStackTrace(); // system error
				if (e.getStatus().getException() != null)
					MessageDialog.openError(getSite().getShell(), "", e
							.getStatus().getException().getMessage());
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

	/**
	 * 
	 */
	protected final class SystemDiagramPropertyChangeListener implements
			PropertyChangeListener {
		private IWorkbenchPage page;

		private SystemDiagramPropertyChangeListener(IWorkbenchPage page) {
			super();
			this.page = page;
		}

		/**
		 * {@inheritDoc}
		 */
		public void propertyChange(PropertyChangeEvent evt) {
			if (evt.getPropertyName().equals("SYSTEM_DIAGRAM_COMPONENTS")) {
				final PropertyChangeEvent changeEvent = evt;
				if (evt.getOldValue() instanceof AbstractComponent
						&& ((AbstractComponent) evt.getOldValue())
								.isCompositeComponent()) {
					try {
						page.getWorkbenchWindow().getShell().getDisplay()
								.asyncExec(new Runnable() {
									public void run() {
										List<AbstractComponent> components = new ArrayList<AbstractComponent>();
										components
												.add((AbstractComponent) changeEvent
														.getOldValue());
										components
												.addAll(((AbstractComponent) changeEvent
														.getOldValue())
														.getAllComponents());
										for (AbstractComponent tmpComponent : components) {
											OpenCompositeComponentAction action = (OpenCompositeComponentAction) tmpComponent
													.getOpenCompositeComponentAction();
											if (action != null) {
												page
														.closeEditor(
																action
																		.getCompositeComponentEditor(),
																false);
												action
														.setCompositeComponentEditorOpened(false);
											}
										}
									}
								});
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		public void dispose() {
			if (systemDiagram != null) {
				systemDiagram.removePropertyChangeListener(this);
			}
		}
	}

	abstract public String getEditorId();

	public SystemDiagramPropertyChangeListener getSystemDiagramPropertyChangeListener() {
		return systemDiagramPropertyChangeListener;
	}

	protected void setSystemDiagram(SystemDiagram systemDiagram) {
		this.systemDiagram = systemDiagram;
	}
}
