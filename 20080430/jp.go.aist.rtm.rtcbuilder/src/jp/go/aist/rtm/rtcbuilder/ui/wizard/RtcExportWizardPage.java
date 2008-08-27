package jp.go.aist.rtm.rtcbuilder.ui.wizard;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import jp.go.aist.rtm.rtcbuilder.ui.preference.ExportPreferenceManager;

import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.dialogs.WizardExportResourcesPage;
import org.eclipse.ui.internal.wizards.datatransfer.ArchiveFileExportOperation;

import sun.net.ProgressMonitor;

public class RtcExportWizardPage extends	WizardExportResourcesPage {

    private final static String STORE_DESTINATION_NAMES_ID = "RtcExportWizardPage.STORE_DESTINATION_NAMES_ID"; //$NON-NLS-1$
    private final static String STORE_CREATE_STRUCTURE_ID = "RtcExportWizardPage.STORE_CREATE_STRUCTURE_ID"; //$NON-NLS-1$
    private final static String STORE_COMPRESS_CONTENTS_ID = "RtcExportWizardPage.STORE_COMPRESS_CONTENTS_ID"; //$NON-NLS-1$

    private Combo destinationNameField;
    private Button destinationBrowseButton;
    //
    private Button sourceArchiveButton;
    private Button binaryArchiveButton;
    private Button sourcebinaryArchiveButton;
    //
    private Button compressContentsCheckbox;
    private Button zipFormatButton;
//    private Button bzipFormatButton;
    private Button targzFormatButton;
    //
    private Button createDirectoryStructureButton;
    private Button createSelectionOnlyButton;
    //
    private org.eclipse.swt.widgets.List targetProjectList;
    private IResource[] targetProjects;
    //
    private ArrayList<String> sourceExt = new ArrayList<String>();
    private ArrayList<String> sourceFile = new ArrayList<String>();
    private ArrayList<String> binaryExt = new ArrayList<String>();
    private ArrayList<String> binaryFile = new ArrayList<String>();
    private ArrayList<String> sourcebinaryExt = new ArrayList<String>();
    private ArrayList<String> sourcebinaryFile = new ArrayList<String>();

    /**
	 *	Create an instance of this class. 
	 *
	 *	@param name java.lang.String
	 */
	protected RtcExportWizardPage(String name, IStructuredSelection selection) {
	    super(name, selection);
	}

	/**
	 * Create an instance of this class
	 * @param selection the selection
	 */
	public RtcExportWizardPage(IStructuredSelection selection) {
	    this("RtcExportPage1", selection); //$NON-NLS-1$
	    setTitle("RTコンポーネント エクスポート");
	    setDescription("RTコンポーネントをエクスポートします。");
	    //
	    sourceExt = ExportPreferenceManager.getInstance().getExportSourceExt();
	    sourceFile = ExportPreferenceManager.getInstance().getExportSourceFile();
	    ////
	    binaryExt = ExportPreferenceManager.getInstance().getExportBinaryExt();
	    binaryFile = ExportPreferenceManager.getInstance().getExportBinaryFile();
	    ////
	    sourcebinaryExt = ExportPreferenceManager.getInstance().getExportSourceBinaryExt();
	    sourcebinaryFile = ExportPreferenceManager.getInstance().getExportSourceBinaryFile();
	}

    /** (non-Javadoc)
     * Method declared on IDialogPage.
     */
    public void createControl(Composite parent) {
        initializeDialogUnits(parent);

        Composite composite = new Composite(parent, SWT.NULL);
        composite.setLayout(new GridLayout());
        composite.setLayoutData(new GridData(GridData.VERTICAL_ALIGN_FILL
                | GridData.HORIZONTAL_ALIGN_FILL));
        composite.setFont(parent.getFont());

    	createTargetProjectGroup(composite);
    	createDestinationGroup(composite);
    	createKindGroup(composite);
    	createOptionsGroup(composite);
    	
        restoreWidgetValues();

    	setControl(composite);
        setPageComplete(false);
    }

    protected void createOptionsGroup(Composite parent) {
	    Group optionsGroup = new Group(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        optionsGroup.setLayout(layout);
        optionsGroup.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
                | GridData.GRAB_HORIZONTAL));
        optionsGroup.setText("オプション");
        optionsGroup.setFont(parent.getFont());
    	
    	Font font = optionsGroup.getFont();
    	optionsGroup.setLayout(new GridLayout(2, true));
    	
    	Composite left = new Composite(optionsGroup, SWT.NONE);
    	left.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
    	left.setLayout(new GridLayout(1, true));

        createFileFormatOptions(left, font);
        
        // compress... checkbox
        compressContentsCheckbox = new Button(left, SWT.CHECK
                | SWT.LEFT);
        compressContentsCheckbox.setText("ファイルの内容を圧縮");
        compressContentsCheckbox.setFont(font);

        Composite right = new Composite(optionsGroup, SWT.NONE);
        right.setLayoutData(new GridData(SWT.LEFT, SWT.TOP, true, false));
        right.setLayout(new GridLayout(1, true));

        createDirectoryStructureOptions(right, font);

        // initial setup
        createDirectoryStructureButton.setSelection(true);
        createSelectionOnlyButton.setSelection(false);
        compressContentsCheckbox.setSelection(true);
    }
    
    protected void createDirectoryStructureOptions(Composite optionsGroup, Font font) {
        createDirectoryStructureButton = new Button(optionsGroup, SWT.RADIO
                | SWT.LEFT);
        createDirectoryStructureButton.setText("ファイルのディレクトリ構造を作成");
        createDirectoryStructureButton.setSelection(false);
        createDirectoryStructureButton.setFont(font);

        createSelectionOnlyButton = new Button(optionsGroup, SWT.RADIO
                | SWT.LEFT);
        createSelectionOnlyButton.setText("選択したディレクトリのみ作成");
        createSelectionOnlyButton.setSelection(true);
        createSelectionOnlyButton.setFont(font);
    }

    protected void createFileFormatOptions(Composite optionsGroup, Font font) {
        zipFormatButton = new Button(optionsGroup, SWT.RADIO | SWT.LEFT);
        zipFormatButton.setText("ZIPフォーマットで保管");
        zipFormatButton.setSelection(true);
        zipFormatButton.setFont(font);

//        bzipFormatButton = new Button(optionsGroup, SWT.RADIO | SWT.LEFT);
//        bzipFormatButton.setText("BZIPフォーマットで保管");
//        bzipFormatButton.setSelection(false);
//        bzipFormatButton.setFont(font);

        targzFormatButton = new Button(optionsGroup, SWT.RADIO | SWT.LEFT);
        targzFormatButton.setText("tarフォーマットで保管");
        targzFormatButton.setSelection(false);
        targzFormatButton.setFont(font);
    }    

    protected void createKindGroup(Composite parent) {
	    Group kindGroup = new Group(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        kindGroup.setLayout(layout);
        kindGroup.setLayoutData(new GridData(GridData.HORIZONTAL_ALIGN_FILL
                | GridData.GRAB_HORIZONTAL));
        kindGroup.setText("アーカイブ方式");
        kindGroup.setFont(parent.getFont());
    	
    	Font font = kindGroup.getFont();
    	kindGroup.setLayout(new GridLayout(3, true));
    	
        sourceArchiveButton = new Button(kindGroup, SWT.RADIO | SWT.LEFT);
        sourceArchiveButton.setText("ソース");
        sourceArchiveButton.setSelection(true);
        sourceArchiveButton.setFont(font);

        binaryArchiveButton = new Button(kindGroup, SWT.RADIO | SWT.LEFT);
        binaryArchiveButton.setText("バイナリ");
        binaryArchiveButton.setSelection(false);
        binaryArchiveButton.setFont(font);

        sourcebinaryArchiveButton = new Button(kindGroup, SWT.RADIO | SWT.LEFT);
        sourcebinaryArchiveButton.setText("ソース+バイナリ");
        sourcebinaryArchiveButton.setSelection(false);
        sourcebinaryArchiveButton.setFont(font);
    }

    protected void createDestinationGroup(Composite parent) {

        Font font = parent.getFont();
        // destination specification group
        Composite destinationSelectionGroup = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 3;
        destinationSelectionGroup.setLayout(layout);
        destinationSelectionGroup.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL));
        destinationSelectionGroup.setFont(font);

        Label destinationLabel = new Label(destinationSelectionGroup, SWT.NONE);
        destinationLabel.setText("宛先ディレクトリ");
        destinationLabel.setFont(font);

        // destination name entry field
        destinationNameField = new Combo(destinationSelectionGroup, SWT.SINGLE
                | SWT.BORDER);
        destinationNameField.addListener(SWT.Modify, this);
        destinationNameField.addListener(SWT.Selection, this);
        GridData data = new GridData(GridData.HORIZONTAL_ALIGN_FILL
                | GridData.GRAB_HORIZONTAL);
        data.widthHint = SIZING_TEXT_FIELD_WIDTH;
        destinationNameField.setLayoutData(data);
        destinationNameField.setFont(font);
        destinationNameField.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				doValidate();
			}

			public void keyPressed(KeyEvent e) {
			}
		});

        // destination browse button
        destinationBrowseButton = new Button(destinationSelectionGroup,
                SWT.PUSH);
        destinationBrowseButton.setText("参照");
        destinationBrowseButton.addListener(SWT.Selection, this);
        destinationBrowseButton.setFont(font);
        setButtonLayoutData(destinationBrowseButton);

        new Label(parent, SWT.NONE); // vertical spacer
    }

    protected void createTargetProjectGroup(Composite parent) {

        Font font = parent.getFont();
        // destination specification group
        Composite targetProjectSelectionGroup = new Composite(parent, SWT.NONE);
        GridLayout layout = new GridLayout();
        layout.numColumns = 3;
        targetProjectSelectionGroup.setLayout(layout);
        targetProjectSelectionGroup.setLayoutData(new GridData(
                GridData.HORIZONTAL_ALIGN_FILL | GridData.VERTICAL_ALIGN_FILL));
        targetProjectSelectionGroup.setFont(font);

        Label targetProjectLabel = new Label(targetProjectSelectionGroup, SWT.NONE);
        targetProjectLabel.setText("対象プロジェクト");
        targetProjectLabel.setFont(font);

        targetProjectList = new org.eclipse.swt.widgets.List(targetProjectSelectionGroup, SWT.BORDER | SWT.SINGLE | SWT.V_SCROLL);
        GridData gd = new GridData(GridData.FILL_BOTH);
        gd.heightHint = 80;
        targetProjectList.setLayoutData(gd);
        targetProjectList.addSelectionListener(new SelectionListener() {

			public void widgetDefaultSelected(SelectionEvent e) {
			}

			public void widgetSelected(SelectionEvent e) {
				doValidate();
			}
        });
        try {
            IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
			targetProjects = root.members();
			for(int intIdx=0; intIdx<targetProjects.length; intIdx++) {
				targetProjectList.add(targetProjects[intIdx].getName());
			}
		} catch (CoreException e) {
			e.printStackTrace();
		}
    }

    private void doValidate() {
    	if( destinationNameField.getText() != null && !destinationNameField.getText().equals("") &&
    			targetProjectList.getSelectionIndex() >= 0) {
    		setPageComplete(true);
    	} else {
    		setPageComplete(false);
    	}
    }

    public void handleEvent(Event event) {
        Widget source = event.widget;

        if (source == destinationBrowseButton) {
			handleDestinationBrowseButtonPressed();
		}
	}

    protected void handleDestinationBrowseButtonPressed() {
        DirectoryDialog dialog = new DirectoryDialog(getContainer().getShell(),
                SWT.SAVE);
        dialog.setText("RTCエクスポート");
        String selectedDirectoryName = dialog.open();

        if (selectedDirectoryName != null) {
            setErrorMessage(null);
            destinationNameField.setText(selectedDirectoryName);
            doValidate();
        }
    }

    public boolean finish() {
    	List resourcesToExport = getTargetResources();
    	
        if (!ensureTargetIsValid()) {
			return false;
		}

//        //Save dirty editors if possible but do not stop if not all are saved
        saveDirtyEditors();
        // about to invoke the operation so save our state
        saveWidgetValues();

        return executeExportOperation(new ArchiveFileExportOperation(null,
                resourcesToExport, getDestinationValue()));
    }
    
    protected boolean executeExportOperation(ArchiveFileExportOperation op) {
        op.setCreateLeadupStructure(createDirectoryStructureButton
                .getSelection());
        op.setUseCompression(compressContentsCheckbox.getSelection());
        op.setUseTarFormat(targzFormatButton.getSelection());

        try {
            getContainer().run(true, true, op);
        } catch (InterruptedException e) {
            return false;
        } catch (InvocationTargetException e) {
            displayErrorDialog(e.getTargetException());
            return false;
        }

        IStatus status = op.getStatus();
        if (!status.isOK()) {
            ErrorDialog.openError(getContainer().getShell(),
                    "エクスポートの問題",
                    null, // no special message
                    status);
            return false;
        }
        MessageDialog.openInformation(getContainer().getShell(), "エクスポート", "エクスポート処理が完了しました");

        return true;
    }
    
    private List getTargetResources() {
    	List resourceList = new ArrayList();
    	ArrayList<String> targetExt = new ArrayList<String>();
    	ArrayList<String> targetFile = new ArrayList<String>();
    	
    	if( sourceArchiveButton.getSelection() ) {
    		targetExt = sourceExt;
    		targetFile = sourceFile;
    	} else if( binaryArchiveButton.getSelection()) {
    		targetExt = binaryExt;
    		targetFile = binaryFile;
    	} else if( sourcebinaryArchiveButton.getSelection()) {
    		targetExt = sourcebinaryExt;
    		targetFile = sourcebinaryFile;
    	}
    	//
    	try {
        	IProject targetProject = (IProject)targetProjects[targetProjectList.getSelectionIndex()];
			IResource[] targets = targetProject.members();
			searchTarget(resourceList, targetExt, targetFile, targets);
		} catch (CoreException e) {
			e.printStackTrace();
		}
    	return resourceList;
    }
    
    private void searchTarget(List resourceList, ArrayList<String> targetExt, ArrayList<String> targetFile,
    							IResource[] sourceRes) {

    	for(int intIdx=0;intIdx<sourceRes.length;intIdx++) {
			if( sourceRes[intIdx].getType() == IResource.FILE ) {
				if( targetExt.contains(sourceRes[intIdx].getFileExtension()) ) {
					resourceList.add(sourceRes[intIdx]);
				}
				for( String filename : targetFile ) {
					if( sourceRes[intIdx].getName().startsWith(filename) ) {
						resourceList.add(sourceRes[intIdx]);
						break;
					}
				}
			} else if( sourceRes[intIdx].getType() == IResource.FOLDER ) {
				try {
					IResource[] resources = ((IFolder)sourceRes[intIdx]).members();
					searchTarget(resourceList, targetExt, targetFile, resources);
				} catch (CoreException e) {
					e.printStackTrace();
				}
				
			}
		}
    }
    
    private boolean ensureTargetIsValid() {
        String targetPath = getDestinationValue();

        if (!ensureTargetDirectoryIsValid(targetPath)) {
			return false;
		}

        if (!ensureTargetFileIsValid(new File(targetPath))) {
			return false;
		}

        return true;
    }

    private String getDestinationValue() {
        String idealSuffix = getOutputSuffix();
        String destinationText = destinationNameField.getText().trim();

        // only append a suffix if the destination doesn't already have a . in 
        // its last path segment.  
        // Also prevent the user from selecting a directory.  Allowing this will 
        // create a ".zip" file in the directory
        if (destinationText.length() != 0
                && !destinationText.endsWith(File.separator)) {
            int dotIndex = destinationText.lastIndexOf('.');
            if (dotIndex != -1) {
                // the last path seperator index
                int pathSepIndex = destinationText.lastIndexOf(File.separator);
                if (pathSepIndex != -1 && dotIndex < pathSepIndex) {
                    destinationText += File.separator + targetProjects[targetProjectList.getSelectionIndex()].getName() + idealSuffix;
                }
            } else {
                destinationText += File.separator + targetProjects[targetProjectList.getSelectionIndex()].getName() + idealSuffix;
            }
        }
        return destinationText;
    }

    private String getOutputSuffix() {
    	if(zipFormatButton.getSelection()) {
        	return ".zip"; //$NON-NLS-1$
//    	} else if(bzipFormatButton.getSelection()) {
//            	return ".bz2"; //$NON-NLS-1$
    	} else if(compressContentsCheckbox.getSelection()) {
    		return ".tar.gz"; //$NON-NLS-1$
    	} else {
    		return ".tar"; //$NON-NLS-1$
    	}
    }
    
    private boolean ensureTargetFileIsValid(File targetFile) {
        if (targetFile.exists() && targetFile.isDirectory()) {
            displayErrorDialog("エクスポート先はディレクトリーではなくファイルでなければなりません。");
            destinationNameField.setFocus();
            return false;
        }

        if (targetFile.exists()) {
            if (targetFile.canWrite()) {
                if (!queryYesNoQuestion("ターゲット・ファイルはすでに存在しています。  上書きしますか?")) {
					return false;
				}
            } else {
                displayErrorDialog("エクスポート先はすでに存在していて上書きできません。");
                destinationNameField.setFocus();
                return false;
            }
        }

        return true;
    }

    private boolean ensureTargetDirectoryIsValid(String fullPathname) {
        int separatorIndex = fullPathname.lastIndexOf(File.separator);

        if (separatorIndex == -1) {
			return true;
		}

        return ensureTargetIsValid(new File(fullPathname.substring(0,
                separatorIndex)));
    }

    private boolean ensureTargetIsValid(File targetDirectory) {
        if (targetDirectory.exists() && !targetDirectory.isDirectory()) {
            displayErrorDialog("ターゲット・ディレクトリーはファイルとしてすでに存在しています。");
            destinationNameField.setFocus();
            return false;
        }

        return ensureDirectoryExists(targetDirectory);
    }

    private boolean ensureDirectoryExists(File directory) {
        if (!directory.exists()) {
            if (!queryYesNoQuestion("ターゲット・ディレクトリーが存在しません。  作成しますか?")) {
				return false;
			}

            if (!directory.mkdirs()) {
                displayErrorDialog("ターゲット・ディレクトリーを作成できませんでした。");
                destinationNameField.setFocus();
                return false;
            }
        }
        return true;
    }

    protected void internalSaveWidgetValues() {
        // update directory names history
        IDialogSettings settings = getDialogSettings();
        if (settings != null) {
            String[] directoryNames = settings
                    .getArray(STORE_DESTINATION_NAMES_ID);
            if (directoryNames == null) {
				directoryNames = new String[0];
			}

            directoryNames = addToHistory(directoryNames, destinationNameField.getText().trim());
            settings.put(STORE_DESTINATION_NAMES_ID, directoryNames);

            settings.put(STORE_CREATE_STRUCTURE_ID,
                    createDirectoryStructureButton.getSelection());

            settings.put(STORE_COMPRESS_CONTENTS_ID, compressContentsCheckbox
                    .getSelection());
        }
    }
    
    protected void restoreWidgetValues() {
        IDialogSettings settings = getDialogSettings();
        if (settings != null) {
            String[] directoryNames = settings
                    .getArray(STORE_DESTINATION_NAMES_ID);
            if (directoryNames == null || directoryNames.length == 0) {
				return;
			}

            // destination
            setDestinationValue(directoryNames[0]);
            for (int i = 0; i < directoryNames.length; i++) {
				addDestinationItem(directoryNames[i]);
			}

            boolean setStructure = settings
                    .getBoolean(STORE_CREATE_STRUCTURE_ID);

            createDirectoryStructureButton.setSelection(setStructure);
            createSelectionOnlyButton.setSelection(!setStructure);

            compressContentsCheckbox.setSelection(settings
                    .getBoolean(STORE_COMPRESS_CONTENTS_ID));
        }
    }

    protected void addDestinationItem(String value) {
        destinationNameField.add(value);
    }
    protected void setDestinationValue(String value) {
        destinationNameField.setText(value);
    }
}
