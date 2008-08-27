package jp.go.aist.rtm.rtcbuilder.ui.editors;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.go.aist.rtm.rtcbuilder.RtcBuilderPlugin;
import jp.go.aist.rtm.rtcbuilder.generator.param.DataPortParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.GeneratorParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.RtcParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.ServicePortInterfaceParam;
import jp.go.aist.rtm.rtcbuilder.generator.param.ServicePortParam;
import jp.go.aist.rtm.rtcbuilder.ui.StringUtil;
import jp.go.aist.rtm.rtcbuilder.ui.preference.ComponentPreferenceManager;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.forms.DetailsPart;
import org.eclipse.ui.forms.IDetailsPage;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.MasterDetailsBlock;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;

/**
 * Configページ
 */
public class ServicePortEditorFormPage extends AbstractEditorFormPage {

	private static final String IDL_EXTENTION = "idl";

	private TreeViewer servicePortViewer;
	//
	private Text nameText;
	private Combo positionCombo;
	//
	private Text descriptionText;
	private Text ifoverviewText;
	//
	private Text interfaceNameText;
	private Combo directionCombo;
	private Text instanceNameText;
	private Text varNameText;
	private Text idlFileText;
	private Text interfaceTypeText;
	private Text idlPathText;
	//
	private Text ifdetailText;
	private Text ifargumentText;
	private Text ifreturnText;
	private Text ifexceptionText;
	private Text ifpreconditionText;
	private Text ifpostconditionText;
	//
	private String defaultPortName;
	//
	private String defaultIFName;
	private String defaultIFInstanceName;
	private String defaultIFVarName;
	
	/**
	 * コンストラクタ
	 * 
	 * @param editor
	 *            親のエディタ
	 */
	public ServicePortEditorFormPage(RtcBuilderEditor editor) {
		super(editor, "id", "サービスポート");
		defaultPortName =ComponentPreferenceManager.getInstance().getServicePort_Name();
		defaultIFName =ComponentPreferenceManager.getInstance().getServiceIF_Name();
		defaultIFInstanceName =ComponentPreferenceManager.getInstance().getServiceIF_InstanceName();
		defaultIFVarName =ComponentPreferenceManager.getInstance().getServiceIF_VarName();
	}

	/**
	 * {@inheritDoc}
	 */
	protected void createFormContent(IManagedForm managedForm) {
		ServicePortMasterBlock block = new ServicePortMasterBlock();
		block.createContent(managedForm);

		load();
	}

//	private void hookContextMenu() {
//		MenuManager menuMgr = new MenuManager();
//		menuMgr.setRemoveAllWhenShown(true);
//		menuMgr.addMenuListener(new IMenuListener() {
//			public void menuAboutToShow(IMenuManager manager) {
//				ServicePortEditorFormPage.this.fillContextMenu(manager);
//			}
//		});
//		Menu menu = menuMgr.createContextMenu(this.getEditor().get .getContainer());
//		getContainer().setMenu(menu);
//		((IEditorSite) getSite()).registerContextMenu(menuMgr,
//				new ISelectionProvider() {
//
//					public void addSelectionChangedListener(
//							ISelectionChangedListener listener) {
//					}
//
//					public ISelection getSelection() {
//						return new StructuredSelection(ServicePortEditorFormPage.this);
//					}
//
//					public void removeSelectionChangedListener(
//							ISelectionChangedListener listener) {
//					}
//
//					public void setSelection(ISelection selection) {
//					}
//
//				}, false);
//	}
//
//	private void fillContextMenu(IMenuManager manager) {
//		manager.add(new Separator());
//		// drillDownAdapter.addNavigationActions(manager);
//		// Other plug-ins can contribute there actions here
//		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
//	}

	private Text createLabelAndFile(FormToolkit toolkit, Composite composite,
			final String extention, String labelString) {
		GridData gd;

		Label label = toolkit.createLabel(composite, labelString);
		final Text text = toolkit.createText(composite, "");
		text.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				update();
			}

			public void keyPressed(KeyEvent e) {
			}
		});
		gd = new GridData(GridData.FILL_HORIZONTAL);
		text.setLayoutData(gd);

		Button checkButton = toolkit.createButton(composite, "Browse...",
				SWT.PUSH);
		gd = new GridData(GridData.HORIZONTAL_ALIGN_END);
		checkButton.setLayoutData(gd);
		checkButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(getEditorSite().getShell());
				dialog.setFilterExtensions(new String[] { "*." + extention });
				if (text.getText().length() > 0)
					dialog.setFileName(text.getText());
				String newPath = dialog.open();
				if (newPath != null) {
					text.setText(newPath);
					update();
				}
			}
		});
		text.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				update();
			}

			public void keyPressed(KeyEvent e) {
			}
		});

		return text;
	}

	public void update() {
		if(servicePortViewer != null ) {
			TreeItem[] selections = servicePortViewer.getTree().getSelection();
			if( selections.length > 0 ) {
				TreeItem selection = selections[0];
				if( selection.getData() instanceof ServicePortParam ) {
					if( nameText.getText()==null || nameText.getText().length()==0 ) {
						((ServicePortParam)selection.getData()).setName(" ");
					} else {
						((ServicePortParam)selection.getData()).setName(getText(nameText.getText()));
					}
					((ServicePortParam)selection.getData()).setPositionByIndex(positionCombo.getSelectionIndex());
					//
					((ServicePortParam)selection.getData()).setDocDescription(descriptionText.getText());
					((ServicePortParam)selection.getData()).setDocIfDescription(ifoverviewText.getText());
				} else if( selection.getData() instanceof ServicePortInterfaceParam ) {
					((ServicePortInterfaceParam)selection.getData()).setName(interfaceNameText.getText());
					((ServicePortInterfaceParam)selection.getData()).setIndex(directionCombo.getSelectionIndex());
					((ServicePortInterfaceParam)selection.getData()).setInstanceName(instanceNameText.getText());
					((ServicePortInterfaceParam)selection.getData()).setVarName(varNameText.getText());
					((ServicePortInterfaceParam)selection.getData()).setIdlFile(idlFileText.getText());
					((ServicePortInterfaceParam)selection.getData()).setInterfaceType(interfaceTypeText.getText());
					((ServicePortInterfaceParam)selection.getData()).setIdlSearchPath(idlPathText.getText());
					//
					((ServicePortInterfaceParam)selection.getData()).setDocDescription(ifdetailText.getText());
					((ServicePortInterfaceParam)selection.getData()).setDocArgument(ifargumentText.getText());
					((ServicePortInterfaceParam)selection.getData()).setDocReturn(ifreturnText.getText());
					((ServicePortInterfaceParam)selection.getData()).setDocException(ifexceptionText.getText());
					((ServicePortInterfaceParam)selection.getData()).setDocPreCondition(ifpreconditionText.getText());
					((ServicePortInterfaceParam)selection.getData()).setDocPostCondition(ifpostconditionText.getText());
				}
			}
			servicePortViewer.setInput(editor.getRtcParam().getServicePorts());
			//
			editor.updateEMFServiceOutPorts(editor.getRtcParam().getServicePorts());
		}

		editor.updateDirty();
	}

	/**
	 * データをロードする
	 */
	public void load() {
		GeneratorParam generator = editor.getGeneratorParam();
		if( generator.getRtcParams().size() > 0 ) {
			RtcParam rtcParam = generator.getRtcParams().get(0);
			if( servicePortViewer != null )
				servicePortViewer.setInput(rtcParam.getServicePorts());
		}
	}

	public String validateParam() {
		String result = null;

		RtcParam rtcParam = editor.getRtcParam();
		Set<String> checkSet = new HashSet<String>(); 
		
		for(ServicePortParam serviceport : rtcParam.getServicePorts()) {
			//ServicePort name
			if( serviceport.getName()==null || serviceport.getName().trim().length()==0 ) {
				result = "ServicePort name を入力してください。";
				return result;
			}
			if( !StringUtil.checkDigitAlphabet(serviceport.getName()) ) {
				result = "ServicePort name は半角英数字を入力してください。";
				return result;
			}
			//重複
			if( checkSet.contains(serviceport.getName()) ) {
				result = "ServicePort の名称が重複しています。";
				return result;
			} else {
				checkSet.add(serviceport.getName());
			}
			for(ServicePortInterfaceParam ifparam : serviceport.getServicePortInterfaces()) {
				//ServiceInterface name
				if( ifparam.getName()==null || ifparam.getName().length()==0 ) {
					result = "Service Interface name を入力してください。";
					return result;
				}
				if( !StringUtil.checkDigitAlphabet(ifparam.getName()) ) {
					result = "Service Interface name は半角英数字を入力してください。";
					return result;
				}
				//ServiceInterface Instance name
				if( ifparam.getInstanceName()==null || ifparam.getInstanceName().length()==0 ) {
					result = "Service Interface Instance name を入力してください。";
					return result;
				}
				if( !StringUtil.checkDigitAlphabet(ifparam.getInstanceName()) ) {
					result = "Service Interface Instance name は半角英数字を入力してください。";
					return result;
				}
				//ServiceInterface Instance type
				if( ifparam.getInterfaceType()==null || ifparam.getInterfaceType().length()==0 ) {
					result = "Service Interface type を入力してください。";
					return result;
				}
				if( !StringUtil.checkDigitAlphabet(ifparam.getInterfaceType()) ) {
					result = "Service Interface type は半角英数字を入力してください。";
					return result;
				}
			}
		}
		return null;
	}

	//Master Block クラス
	private class ServicePortMasterBlock extends MasterDetailsBlock {

		@Override
		protected void createMasterPart(final IManagedForm managedForm, Composite parent) {
			FormToolkit toolkit = managedForm.getToolkit();
			Section section = toolkit.createSection(parent, Section.TITLE_BAR);
			section.setText("RT-Component Service Ports");
			Composite client = toolkit.createComposite(section);
			client.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
			client.setLayout(new GridLayout(2, false));
			Tree tree = toolkit.createTree(client, SWT.BORDER);
			servicePortViewer = new TreeViewer(tree);
			servicePortViewer.setContentProvider(new ServiceParamContentProvider());
			servicePortViewer.setLabelProvider(new ServiceParamLabelProvider());
			servicePortViewer.setInput(editor.getRtcParam().getServicePorts());
			GridData gridData = new GridData(GridData.FILL_BOTH);
			gridData.grabExcessHorizontalSpace = true;
			gridData.verticalSpan  = 4;
			tree.setLayoutData(gridData);
			//
			Button addButton = managedForm.getToolkit().createButton(client, "  Add Port  ", SWT.PUSH);
			addButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					((List) servicePortViewer.getInput())
							.add(new ServicePortParam(defaultPortName, 0));
					update();
					servicePortViewer.refresh();
					servicePortViewer.expandAll();
				}
			});
			gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			gridData.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
			addButton.setLayoutData(gridData);
			//
			Button addinterfaceButton = managedForm.getToolkit().createButton(client, "Add Interface", SWT.PUSH);
			addinterfaceButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					TreeItem[] selections = servicePortViewer.getTree().getSelection();
					TreeItem selection = selections[0];
					if( selection.getData() instanceof ServicePortParam ) {
						((ServicePortParam)selection.getData()).getServicePortInterfaces()
							.add(new ServicePortInterfaceParam((ServicePortParam)selection.getData() ,
									defaultIFName, defaultIFInstanceName, defaultIFVarName, "", "", "", 0));
						update();
					}
					servicePortViewer.refresh();
					servicePortViewer.expandAll();
				}
			});
			gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			gridData.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
			addinterfaceButton.setLayoutData(gridData);
			//
			Button deleteButton = managedForm.getToolkit().createButton(client, "   Delete   ", SWT.PUSH);
			deleteButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					TreeItem[] selections = servicePortViewer.getTree().getSelection();
					TreeItem selection = selections[0];
					if( selection.getData() instanceof ServicePortParam ) {
						((List) servicePortViewer.getInput()).remove((ServicePortParam)selection.getData());
						update();
					} else	if( selection.getData() instanceof ServicePortInterfaceParam ) {
						((ServicePortInterfaceParam)selection.getData()).getParent().getServicePortInterfaces()
							.remove(selection.getData());
						update();
					}
					servicePortViewer.refresh();
					servicePortViewer.expandAll();
				}
			});
			gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			gridData.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
			deleteButton.setLayoutData(gridData);
			Label label = toolkit.createLabel(client, "");
			gridData = new GridData(GridData.HORIZONTAL_ALIGN_FILL);
			gridData.verticalAlignment = GridData.VERTICAL_ALIGN_CENTER;
			label.setLayoutData(gridData);
			//
			final SectionPart sectionPart = new SectionPart(section);
			managedForm.addPart(sectionPart);
			servicePortViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent event) {
					managedForm.fireSelectionChanged(sectionPart, event.getSelection());
				}
			});
			section.setClient(client);
		}

		@Override
		protected void createToolBarActions(IManagedForm managedForm) {
//			final ScrolledForm form = managedForm.getForm();
//			Action haction = new Action("horizontal", Action.AS_RADIO_BUTTON) {
//				public void run() {
//					sashForm.setOrientation(SWT.HORIZONTAL);
//					form.reflow(true);
//				}
//			};
//			haction.setChecked(true);
//			haction.setImageDescriptor(RtcBuilderPlugin.
//					imageDescriptorFromPlugin(RtcBuilderPlugin.PLUGIN_ID, "icons/RTCBuilder.png"));
//			Action vaction = new Action("vertical", Action.AS_RADIO_BUTTON) {
//				public void run() {
//					sashForm.setOrientation(SWT.VERTICAL);
//					form.reflow(true);
//				}
//			};
//			vaction.setChecked(false);
//			vaction.setImageDescriptor(RtcBuilderPlugin.
//					imageDescriptorFromPlugin(RtcBuilderPlugin.PLUGIN_ID, "icons/RTCBuilder.png"));
//			form.getToolBarManager().add(haction);
//			form.getToolBarManager().add(vaction);
		}

		@Override
		protected void registerPages(DetailsPart detailsPart) {
			detailsPart.registerPage(ServicePortParam.class, new ServicePortDetailsPage());
			detailsPart.registerPage(ServicePortInterfaceParam.class, new ServicePortInterfaceDetailsPage());
		}
	}

	//ServicePort Detail Block クラス
	private class ServicePortDetailsPage implements IDetailsPage {
		private IManagedForm form;

		public void createContents(Composite parent) {
			parent.setLayout(new FillLayout());
			FormToolkit toolkit = form.getToolkit();
			Section section = toolkit.createSection(parent, Section.TITLE_BAR);
			section.setText("RT-Component Service Port Profile");
			Composite client = toolkit.createComposite(section, SWT.NULL);
			client.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
			form.getToolkit().paintBordersFor(client);
			client.setLayout(new GridLayout(2, false));
			//
			nameText = createLabelAndText(toolkit, client, "Name :");
			positionCombo = createLabelAndCombo(toolkit, client,
					"Position :", DataPortParam.COMBO_ITEM);
			
			createSrvPortDocumentSection(form, client);
			section.setClient(client);
		}

		private void createSrvPortDocumentSection(IManagedForm managedForm, Composite parent) {

			GridLayout gl;

			Section section = managedForm.getToolkit().createSection(
					parent,
					Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
			section.setText("Documentation");
			GridData gridData = new GridData();
			gridData.grabExcessHorizontalSpace = true;
			gridData.horizontalAlignment = GridData.FILL;
			gridData.horizontalSpan = 2;
			section.setLayoutData(gridData);
			Composite composite = managedForm.getToolkit().createComposite(section,
					SWT.NULL);
			composite.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
			managedForm.getToolkit().paintBordersFor(composite);
			gl = new GridLayout(2, false);
			composite.setLayout(gl);
			section.setClient(composite);

			descriptionText = createLabelAndText(managedForm.getToolkit(), composite,
					"概要説明:", SWT.MULTI | SWT.V_SCROLL);
			gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.heightHint = 70;
			descriptionText.setLayoutData(gridData);
			ifoverviewText = createLabelAndText(managedForm.getToolkit(), composite,
					"I/F概要説明 :", SWT.MULTI | SWT.V_SCROLL);
			ifoverviewText.setLayoutData(gridData);
		}

		public void commit(boolean onSave) {
		}

		public void dispose() {
		}

		public void initialize(IManagedForm form) {
			this.form = form;
		}

		public boolean isDirty() {
			return false;
		}

		public boolean isStale() {
			return false;
		}

		public void refresh() {
		}

		public void setFocus() {
		}

		public boolean setFormInput(Object input) {
			return false;
		}

		public void selectionChanged(IFormPart part, ISelection selection) {
			IStructuredSelection structuredSelection = (IStructuredSelection)selection;
			ServicePortParam servicePort = (ServicePortParam)structuredSelection.getFirstElement();
			nameText.setText(servicePort.getName());
			positionCombo.select(servicePort.getPositionByIndex());
			//
			descriptionText.setText(servicePort.getDocDescription());
			ifoverviewText.setText(servicePort.getDocIfDescription());
		}
	}
	
	//ServicePortInterface Detail Block クラス
	private class ServicePortInterfaceDetailsPage implements IDetailsPage {
		private IManagedForm form;

		public void createContents(Composite parent) {
			parent.setLayout(new FillLayout());
			FormToolkit toolkit = form.getToolkit();
			Section section = toolkit.createSection(parent, Section.TITLE_BAR);
			section.setText("RT-Component Service Port Interface Profile");
			Composite client = toolkit.createComposite(section, SWT.NULL);
			client.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
			form.getToolkit().paintBordersFor(client);
			client.setLayout(new GridLayout(3, false));
			//
			interfaceNameText = createLabelAndText(toolkit, client, "Interface Name :");
			Label label = toolkit.createLabel(client, "");
			directionCombo = createLabelAndCombo(toolkit, client,
					"Direction :", ServicePortInterfaceParam.COMBO_ITEM);
			label = toolkit.createLabel(client, "");
			instanceNameText = createLabelAndText(toolkit, client, "Instance Name :");
			label = toolkit.createLabel(client, "");
			varNameText = createLabelAndText(toolkit, client, "Var Name :");
			label = toolkit.createLabel(client, "");
			idlFileText = createLabelAndFile(toolkit, client, IDL_EXTENTION, "IDL file :");
			interfaceTypeText = createLabelAndText(toolkit, client, "Interface Type :");
			label = toolkit.createLabel(client, "");
			idlPathText = createLabelAndDirectory(toolkit, client, "IDL path :");
			
			createSrvPortIfDocumentSection(form, client);
			section.setClient(client);
		}

		private void createSrvPortIfDocumentSection(IManagedForm managedForm, Composite parent) {

			GridLayout gl;

			Section section = managedForm.getToolkit().createSection(
					parent,
					Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
			section.setText("Documentation");
			GridData gridData = new GridData();
			gridData.grabExcessHorizontalSpace = true;
			gridData.horizontalAlignment = GridData.FILL;
			gridData.horizontalSpan = 2;
			section.setLayoutData(gridData);
			Composite composite = managedForm.getToolkit().createComposite(section,
					SWT.NULL);
			composite.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
			managedForm.getToolkit().paintBordersFor(composite);
			gl = new GridLayout(2, false);
			composite.setLayout(gl);
			section.setClient(composite);

			ifdetailText = createLabelAndText(managedForm.getToolkit(), composite,
					"概要説明:", SWT.MULTI | SWT.V_SCROLL);
			gridData = new GridData(GridData.FILL_HORIZONTAL);
			gridData.heightHint = 70;
			ifdetailText.setLayoutData(gridData);
			ifargumentText = createLabelAndText(managedForm.getToolkit(), composite,
					"引数:", SWT.MULTI | SWT.V_SCROLL);
			ifargumentText.setLayoutData(gridData);
			ifreturnText = createLabelAndText(managedForm.getToolkit(), composite,
					"戻り値:", SWT.MULTI | SWT.V_SCROLL);
			ifreturnText.setLayoutData(gridData);
			ifexceptionText = createLabelAndText(managedForm.getToolkit(), composite,
					"例外:", SWT.MULTI | SWT.V_SCROLL);
			ifexceptionText.setLayoutData(gridData);
			ifpreconditionText = createLabelAndText(managedForm.getToolkit(), composite,
					"事前条件:", SWT.MULTI | SWT.V_SCROLL);
			ifpreconditionText.setLayoutData(gridData);
			ifpostconditionText = createLabelAndText(managedForm.getToolkit(), composite,
					"事後条件:", SWT.MULTI | SWT.V_SCROLL);
			ifpostconditionText.setLayoutData(gridData);
		}

		public void commit(boolean onSave) {
		}

		public void dispose() {
		}

		public void initialize(IManagedForm form) {
			this.form = form;
		}

		public boolean isDirty() {
			return false;
		}

		public boolean isStale() {
			return false;
		}

		public void refresh() {
		}

		public void setFocus() {
		}

		public boolean setFormInput(Object input) {
			return false;
		}

		public void selectionChanged(IFormPart part, ISelection selection) {
			IStructuredSelection structuredSelection = (IStructuredSelection)selection;
			ServicePortInterfaceParam serviceInterface = (ServicePortInterfaceParam)structuredSelection.getFirstElement();
			interfaceNameText.setText(serviceInterface.getName());
			directionCombo.select(serviceInterface.getIndex());
			instanceNameText.setText(serviceInterface.getInstanceName());
			varNameText.setText(serviceInterface.getVarName());
			idlFileText.setText(serviceInterface.getIdlFile());
			interfaceTypeText.setText(serviceInterface.getInterfaceType());
			idlPathText.setText(serviceInterface.getIdlSearchPath());
			//
			ifdetailText.setText(serviceInterface.getDocDescription());
			ifargumentText.setText(serviceInterface.getDocArgument());
			ifreturnText.setText(serviceInterface.getDocReturn());
			ifexceptionText.setText(serviceInterface.getDocException());
			ifpreconditionText.setText(serviceInterface.getDocPreCondition());
			ifpostconditionText.setText(serviceInterface.getDocPostCondition());
		}
	}

	private class ServiceParamContentProvider implements ITreeContentProvider {

		public Object[] getChildren(Object parentElement) {
			if(parentElement instanceof ServicePortParam) {
				ServicePortParam servicePort = (ServicePortParam)parentElement;
				return servicePort.getServicePortInterfaces().toArray();
			} else if(parentElement instanceof Collection) {
				Collection collection = (Collection)parentElement;
				return collection.toArray();
			}
			return null;
		}

		public Object getParent(Object element) {
			return null;
		}

		public boolean hasChildren(Object element) {
			return !(element instanceof ServicePortInterfaceParam);
		}

		public Object[] getElements(Object inputElement) {
			return getChildren(inputElement);
		}

		public void dispose() {
		}

		public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		}
	}
	
	private class ServiceParamLabelProvider extends LabelProvider {
		Image ImagePort, ImageReqIF, ImageProIF;
		
		public ServiceParamLabelProvider() {
			ImageDescriptor descPort;
			ImageDescriptor descReqIF;
			ImageDescriptor descProIF;
			try {
				URL url = RtcBuilderPlugin.getDefault().getBundle().getEntry("/");
				descPort = ImageDescriptor.createFromURL(new URL(url ,"icons/SrvPort.png"));
				descReqIF = ImageDescriptor.createFromURL(new URL(url ,"icons/ReqIF.png"));
				descProIF = ImageDescriptor.createFromURL(new URL(url ,"icons/ProIF.png"));
				ImageProIF = descProIF.createImage();
				ImagePort = descPort.createImage();
				ImageReqIF = descReqIF.createImage();
			} catch(MalformedURLException e) {
				descPort = ImageDescriptor.getMissingImageDescriptor();
			}
		}

		public String getText(Object element) {
			if( element instanceof ServicePortParam ) {
				ServicePortParam servicePort = (ServicePortParam)element;
				return servicePort.getName();
			} else if( element instanceof ServicePortInterfaceParam ) {
				ServicePortInterfaceParam serviceInterface = (ServicePortInterfaceParam)element;
				return serviceInterface.getName();
			}
			return super.getText(element);
		}
		
		public Image getImage(Object element) {
			if(element instanceof ServicePortParam) {
				return ImagePort;
			} else if(element instanceof ServicePortInterfaceParam) {
				ServicePortInterfaceParam serviceInterface = (ServicePortInterfaceParam)element;
				if(serviceInterface.getDirection().equals(ServicePortInterfaceParam.INTERFACE_DIRECTION_PROVIDED)) {
					return ImageProIF;
				} else {
					return ImageReqIF;
				}
			}
			return super.getImage(element);
		}

		@Override
		public void dispose() {
			if( !servicePortViewer.getControl().isDisposed() ) {
				ImagePort.dispose();
				ImageReqIF.dispose();
				ImageProIF.dispose();
			}
			super.dispose();
		}
	}
}
