package jp.go.aist.rtm.rtctemplate.ui.editors;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.go.aist.rtm.rtctemplate.RtctemplatePlugin;
import jp.go.aist.rtm.rtctemplate.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtctemplate.generator.param.RtcParam;

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
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.dialogs.ProgressMonitorDialog;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionFilter;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IPathEditorInput;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.editor.FormEditor;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.IURIEditorInput;

/**
 * RtcTemplateエディタ
 */
public class RtcTemplateEditor extends FormEditor implements IActionFilter {
	public static final String RTC_TEMPLATE_EDITOR_ID = RtcTemplateEditor.class
			.getName();

	public static final String FILE_EXTENTION = "rtctemplate";

	public static final String RTCTEMPLATE_PROJECT_NAME = "RTCTemplate_Files";

	public static String RTCTEMPLATE_NEW_EDITOR_PATH = "RTCTemplate";

	private boolean isDirty;

	private String title;

	private GeneratorParam generatorParam;

	private ConfigFormPage configFormPage;

	public RtcTemplateEditor() {
	}

	private IEditorInput load(IEditorInput input, IEditorSite site)
			throws PartInitException {
		boolean newOpenEditor = input instanceof NullEditorInput;// 新規エディタ

		IEditorInput result = input;
		if (newOpenEditor) {
			generatorParam = new GeneratorParam();
			generatorParam.getRtcParams().add(new RtcParam(generatorParam));
		} else if (input instanceof FileEditorInput) {
//		} else if (input instanceof JavaFileEditorInput) {
//			IPath path = ((JavaFileEditorInput) input).getPath();
//		} else if (input instanceof FileStoreEditorInput) {
//			URI uri = ((FileStoreEditorInput) input).getURI();
//			IPath path = new Path(uri.getPath());

//			IFile file = getOutsideIFileLink(path);
//			result = new FileEditorInput(file);

			String className = input.getClass().getName();
			IPath path = null;

			if( getEclipseVersion().startsWith("3.3") ) {
				if (className.equals("org.eclipse.ui.ide.FileStoreEditorInput")) {
					IURIEditorInput uri = (IURIEditorInput) input;
					path = new Path(uri.getURI().getPath());
				}
			} else {
				if (className
						.equals("org.eclipse.ui.internal.editors.text.JavaFileEditorInput")) {
					IPathEditorInput pei = (IPathEditorInput) input;
					path = pei.getPath();
				}
			}
			if (path != null) {
				IFile file = getOutsideIFileLink(path);
				result = new FileEditorInput(file);
			}
		}

		if (newOpenEditor) {

		} else if (result instanceof FileEditorInput) {
			FileEditorInput fileEditorInput = ((FileEditorInput) result);

			try {
				ObjectInputStream objInput = new ObjectInputStream(
						fileEditorInput.getFile().getContents());

				generatorParam = (GeneratorParam) objInput.readObject();

			} catch (IOException e) {
				throw new PartInitException(
						"ファイルの読み込みに失敗しました。\r\nRTCTemplate以外のファイルが読み込まれていないか確認してください",
						e);
			} catch (ClassNotFoundException e) {
				throw new PartInitException(
						"ファイルの読み込みに失敗しました。\r\nRTCTemplate以外のファイルが読み込まれていないか確認してください。",
						e);
			} catch (CoreException e) {
				throw new RuntimeException(e); // system error
			}
		}

		if (newOpenEditor) {
			title = "RtcTemplate";
		} else if (result instanceof FileEditorInput) {
			title = ((FileEditorInput) result).getPath().lastSegment();
		}

		isDirty = false;
		firePropertyChange(IEditorPart.PROP_TITLE);

		if (configFormPage != null) {
			configFormPage.load();
		}

		this.setInput(result);

		return result;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void init(IEditorSite site, IEditorInput input)
			throws PartInitException {
		IEditorInput newInput = load(input, site);
		super.init(site, newInput);
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager();
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				RtcTemplateEditor.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(getContainer());
		getContainer().setMenu(menu);
		((IEditorSite) getSite()).registerContextMenu(menuMgr,
				new ISelectionProvider() {

					public void addSelectionChangedListener(
							ISelectionChangedListener listener) {
					}

					public ISelection getSelection() {
						return new StructuredSelection(RtcTemplateEditor.this);
					}

					public void removeSelectionChangedListener(
							ISelectionChangedListener listener) {
					}

					public void setSelection(ISelection selection) {
					}

				}, false);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(new Separator());
		// drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	@Override
	protected void createPages() {
		super.createPages();
		hookContextMenu();
	}

	/**
	 * {@inheritDoc}
	 */
	protected FormToolkit createToolkit(Display display) {
		return new FormToolkit(getSite().getShell().getDisplay());
	}

	/**
	 * {@inheritDoc}
	 */
	protected void addPages() {
		try {
			configFormPage = new ConfigFormPage(this);
			addPage(0, configFormPage);
		} catch (PartInitException e) {
			throw new RuntimeException(e); // system error
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void doSave(IProgressMonitor monitor) {
		boolean newOpenEditor = getEditorInput() instanceof NullEditorInput;// 新規エディタ

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

	/**
	 * {@inheritDoc}
	 */
	public void doSaveAs() {
		boolean newOpenEditor = getEditorInput() instanceof NullEditorInput;// 新規エディタ

		IFile oldFile = null;
		if (newOpenEditor) {
			// void
		} else {
			oldFile = ((FileEditorInput) getEditorInput()).getFile();
		}

		final IPath newPath = getFilePathByDialog(oldFile);

		if (newPath == null)
			return;

		if (newPath.toFile().exists() == false) {
			try {
				newPath.toFile().createNewFile();
			} catch (IOException e) {
				MessageDialog.openError(getSite().getShell(), "エラー",
						"ファイルの作成に失敗しました。" + newPath.toOSString());
				return;
			}
		}

		final IFile newFile = getOutsideIFileLink(newPath);

		ProgressMonitorDialog progressMonitorDialog = new ProgressMonitorDialog(
				getSite().getShell());

		try {
			progressMonitorDialog.run(false, false,
					new IRunnableWithProgress() {
						public void run(IProgressMonitor monitor)
								throws InvocationTargetException,
								InterruptedException {
							try {
								if (newPath.toFile().exists() == false) {
									try {
										newPath.toFile().createNewFile();
									} catch (IOException e) {
										throw new RuntimeException(e); // SystemError
									}
								}

								save(newFile, monitor);
								// getMultiPageCommandStackListener().markSaveLocations();
							} catch (CoreException e) {
							}
						}
					});
		} catch (Exception e) {
			throw new RuntimeException(e); // SystemError
		}

	}

	/**
	 * {@inheritDoc}
	 */
	private void save(IFile file, IProgressMonitor progressMonitor)
			throws CoreException {

		if (null == progressMonitor)
			progressMonitor = new NullProgressMonitor();

		progressMonitor.beginTask("Saving " + file.getLocation().toOSString(),
				2);

		try {

			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ObjectOutputStream objout = new ObjectOutputStream(out);

			progressMonitor.worked(15);

			objout.writeObject(generatorParam);

			objout.flush();
			byte[] bytes = out.toByteArray();

			InputStream source = new ByteArrayInputStream(bytes);

			file.setContents(source, true, true, null);

			file.refreshLocal(IResource.DEPTH_ZERO, null);

			setInput(new FileEditorInput(file));

			isDirty = false;
			firePropertyChange(IEditorPart.PROP_DIRTY);

			title = file.getLocation().lastSegment();
			firePropertyChange(IEditorPart.PROP_TITLE);

			progressMonitor.done();
		} catch (FileNotFoundException e) {
			IStatus status = new Status(IStatus.ERROR, RtctemplatePlugin
					.getDefault().getClass().getName(), 0,
					"Error writing file.", e);
			throw new CoreException(status);
		} catch (IOException e) {
			IStatus status = new Status(IStatus.ERROR, RtctemplatePlugin
					.getDefault().getClass().getName(), 0,
					"Error writing file.", e);
			throw new CoreException(status);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isSaveAsAllowed() {
		return true;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isDirty() {
		return isDirty;
	}

	private void setDirty(boolean isDirty) {
		this.isDirty = isDirty;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void firePropertyChange(int propertyId) {
		super.firePropertyChange(propertyId);
	}

	/**
	 * エディタをダーティにする。
	 */
	public void updateDirty() {
		setDirty(true);
		firePropertyChange(IEditorPart.PROP_DIRTY);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * GeneratorParamを取得する
	 */
	public GeneratorParam getGeneratorParam() {
		return generatorParam;
	}

	/**
	 * RtcParamを取得する
	 */
	public RtcParam getRtcParam() {
		return generatorParam.getRtcParams().get(0);
	}

	private SimpleDateFormat formater = new SimpleDateFormat(
			"yyyy_MM_dd_hh_mm_ss_SSS");

	/**
	 * Workspace外部のファイルに対してリンクを作成することで、Eclipse内でファイルを扱えるようにする
	 */

	private IFile getOutsideIFileLink(IPath path) {
		IWorkspace ws = ResourcesPlugin.getWorkspace();
		IProject project = ws.getRoot().getProject(RTCTEMPLATE_PROJECT_NAME);
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

	private IPath getFilePathByDialog(IFile defaultFile) {
		FileDialog dialog = new FileDialog(getSite().getShell(), SWT.SAVE);

		if (defaultFile != null) {
			dialog.setFileName(defaultFile.toString());
		}
		dialog.setFilterExtensions(new String[] { "*." + FILE_EXTENTION, "*.*" });

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

	public void open() {
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
				load(new FileEditorInput(createNewFile), getEditorSite());
			} catch (PartInitException e) {
				e.printStackTrace(); // system error
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

	public boolean testAttribute(Object target, String name, String value) {
		boolean result = false;
		if ("dirty".equals(name)) {
			if (isDirty()) {
				result = "true".equalsIgnoreCase(value);
			} else {
				result = "false".equalsIgnoreCase(value);
			}
		}

		return result;
	}
	
	private String getEclipseVersion() {
		 return System.getProperty("osgi.framework.version");
	 }

}
