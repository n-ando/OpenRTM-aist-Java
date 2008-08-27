package jp.go.aist.rtm.systemeditor.ui.views.configurationview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.systemeditor.RTSystemEditorPlugin;
import jp.go.aist.rtm.systemeditor.factory.SystemEditorWrapperFactory;
import jp.go.aist.rtm.systemeditor.ui.views.configurationview.configurationwrapper.ComponentConfigurationWrapper;
import jp.go.aist.rtm.systemeditor.ui.views.configurationview.configurationwrapper.ConfigurationSetConfigurationWrapper;
import jp.go.aist.rtm.systemeditor.ui.views.configurationview.configurationwrapper.NamedValueConfigurationWrapper;
import jp.go.aist.rtm.toolscommon.corba.CorbaUtil;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.ConfigurationSet;
import jp.go.aist.rtm.toolscommon.model.component.LifeCycleState;
import jp.go.aist.rtm.toolscommon.model.component.NameValue;
import jp.go.aist.rtm.toolscommon.model.component.SystemDiagram;
import jp.go.aist.rtm.toolscommon.model.component.impl.ComponentImpl;
import jp.go.aist.rtm.toolscommon.model.component.impl.ConfigurationSetImpl;
import jp.go.aist.rtm.toolscommon.ui.views.propertysheetview.RtcPropertySheetPage;
import jp.go.aist.rtm.toolscommon.util.AdapterUtil;

import org.apache.commons.lang.StringUtils;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.CheckboxCellEditor;
import org.eclipse.jface.viewers.ICellModifier;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Item;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.omg.CORBA.Any;
import org.omg.CORBA.TCKind;
import org.omg.CORBA.TypeCodePackage.BadKind;

/**
 * ConfigurationViewを定義するクラス
 * <p>
 * Applyボタン押下までは、修正の適用は保留され、変更された場所は色がついて表示される。
 * NameValueの値の編集ができるのはStirngクラスのみであり、それ以外のオブジェクトが含まれていた場合には、編集することはできない（削除は可能）
 */
public class ConfigurationView extends ViewPart {
	private static final String PROPERTY_ACTIVE_CONFIGSET = "PROPERTY_ACTIVE_CONFIGSET";

	private static final String PROPERTY_CONFIG_SET = "PROPERTY_CONFIG_SET";

	private static final String PROPERTY_KEY = "PROPERTY_KEY";

	private static final String PROPERTY_VALUE = "PROPERTY_VALUE";

	private static final String LAST_NAMESERVICE_ADDRESS = ConfigurationView.class
			.getCanonicalName()
			+ "lastNameServiceAddress";

	private static final String MODIFY_COLOR = "MODIFY_COLOR";

	private static final String CANT_MODIFY_COLOR = "CANT_MODIFY_COLOR";

	private static final int BUTTON_WIDTH = 70;

	private Composite composite;

	private AbstractComponent targetComponent;

	private ComponentConfigurationWrapper copiedComponent;

	private ComponentConfigurationWrapper originalComponent;

	private Object cachedConfiguSetList;

	private Table leftTable;

	private Table rightTable;

	private Label componentNameLabel;

	private Label configrationSetNameLabel;

	private TableViewer leftTableViewer;

	private TableViewer rightTableViewer;

	private Button addConfigurationSetButton;

	private Button deleteConfigurationSetButton;

	private Button deleteNamedValueButton;

	private Button addNamedValueButton;

	private Button applyButton;

	private Button cancelButton;

	private static ColorRegistry colorRegistry = new ColorRegistry();
	static {
		colorRegistry.put(MODIFY_COLOR, new RGB(255, 192, 192));
		colorRegistry.put(CANT_MODIFY_COLOR, new RGB(198, 198, 198));
	}

	/**
	 * {@inheritDoc}
	 */
	public void createPartControl(Composite parent) {
		GridLayout gl;
		GridData gd;

		gl = new GridLayout();
		gl.numColumns = 2;
		parent.setLayout(gl);

		SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
		gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessVerticalSpace = true;
		gd.verticalAlignment = SWT.FILL;
		sashForm.setLayoutData(gd);

		createLeftControl(sashForm);

		createRightControl(sashForm);

		sashForm.setWeights(new int[] { 30, 70 });

		Composite executionButtonComposite = new Composite(parent, SWT.NONE);
		gl = new GridLayout();
		executionButtonComposite.setLayout(gl);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessVerticalSpace = true;
		gd.verticalAlignment = SWT.FILL;
		executionButtonComposite.setLayoutData(gd);

		getSite().getWorkbenchWindow().getSelectionService()
				.addSelectionListener(selectionListener);
		getSite().setSelectionProvider(new ISelectionProvider() {
			public void addSelectionChangedListener(
					ISelectionChangedListener listener) {
			}

			public ISelection getSelection() {
				StructuredSelection result = null;
				if (targetComponent != null) {
					result = new StructuredSelection(targetComponent);
				}

				return result;
			}

			public void removeSelectionChangedListener(
					ISelectionChangedListener listener) {
			}

			public void setSelection(ISelection selection) {
			}
		});

		applyButton = new Button(executionButtonComposite, SWT.TOP);
		applyButton.setText("Apply");
		gd = new GridData();
		gd.widthHint = BUTTON_WIDTH;
		applyButton.setLayoutData(gd);
		applyButton.setEnabled(false);
		applyButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				if (targetComponent instanceof Component) {
					if (((Component)targetComponent).getComponentState() == LifeCycleState.RTC_ACTIVE
							&& isActiveConfigurationSetModified()) {
						boolean isOk = MessageDialog.openConfirm(getViewSite()
								.getShell(), "確認",
								"アクティブなコンフィグレーションセットが変更されています。\r\n変更を適用しても良いですか？");
						if (isOk == false) {
							return;
						}
					}
				}
				int selectionIndex = leftTable.getSelectionIndex();

				List<ConfigurationSet> newConfigurationSetList = createNewConfigurationSetList(copiedComponent);
				List<ConfigurationSet> originalConfigurationSetList = createNewConfigurationSetList(originalComponent);

				int activeConfigurationIndex = copiedComponent
						.getConfigurationSetList().indexOf(
								copiedComponent.getActiveConfigSet());

				ConfigurationSet newActiveConfigurationSet = null;
				if (activeConfigurationIndex != -1) {
					newActiveConfigurationSet = newConfigurationSetList
							.get(activeConfigurationIndex);
				}

				boolean result = targetComponent.updateConfigurationSetListR(
						newConfigurationSetList, newActiveConfigurationSet,
						originalConfigurationSetList);
				if (result == false) {
					MessageDialog.openError(getSite().getShell(), "エラー",
							"更新に失敗しました。");
				}
				if (targetComponent instanceof Component) {
					ComponentImpl.synchronizeLocalAttribute((Component) targetComponent,
							ComponentPackage.eINSTANCE
							.getAbstractComponent_ConfigurationSets());	
				}
				buildData();

				leftTable.setSelection(selectionIndex);
				refreshRightData();
				
			}

		});

		cancelButton = new Button(executionButtonComposite, SWT.TOP);
		cancelButton.setText("Cancel");
		gd = new GridData();
		gd.widthHint = BUTTON_WIDTH;
		cancelButton.setLayoutData(gd);
		cancelButton.setEnabled(false);
		cancelButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				buildData();
			}
		});
	}

	/**
	 * アクティブなコンフィグレーションを修正したかどうか
	 * 
	 * @return
	 */
	private boolean isActiveConfigurationSetModified() {
		boolean result = false;
		if ((copiedComponent.getActiveConfigSet() != null) == (targetComponent
				.getActiveConfigurationSet() != null) == false) {
			result = true;
		}
		if (result == false) {
			if (copiedComponent.getActiveConfigSet() != null) {
				if (targetComponent.getActiveConfigurationSet() != null) {
					if (copiedComponent.getActiveConfigSet()
							.getConfigurationSet() != null) {
						if (copiedComponent.getActiveConfigSet()
								.getConfigurationSet().getId().equals(
										targetComponent
												.getActiveConfigurationSet()
												.getId()) == false) {
							result = true;
						}
					} else {
						result = true;
					}
				} else {
					result = true;
				}
			} else {
				if (targetComponent.getActiveConfigurationSet() != null) {
					result = true;
				}
			}
		}
		if (result == false) {
			if (copiedComponent.getActiveConfigSet() != null) {
				if (copiedComponent.getActiveConfigSet().getNamedValueList()
						.size() != targetComponent.getActiveConfigurationSet()
						.getConfigurationData().size()) {
					result = true;
				}
			}
		}
		if (result == false) {
			if (copiedComponent.getActiveConfigSet() != null) {
				for (NamedValueConfigurationWrapper namedValue : copiedComponent
						.getActiveConfigSet().getNamedValueList()) {
					if (namedValue.isKeyModified()
							|| namedValue.isValueModified()) {
						result = true;
						break;
					}
				}
			}
		}
		return result;
	}

	/**
	 * 編集後の新しいConfigurationSetを作成する。
	 * <p>
	 * 
	 * @param copiedComponent
	 * @return
	 */
	private List<ConfigurationSet> createNewConfigurationSetList(
			ComponentConfigurationWrapper copiedComponent) {
		ArrayList<ConfigurationSet> result = new ArrayList<ConfigurationSet>();
		for (ConfigurationSetConfigurationWrapper configset : copiedComponent
				.getConfigurationSetList()) {
			ConfigurationSet configurationSet = new ConfigurationSetImpl();
			for (NamedValueConfigurationWrapper namedValue : configset
					.getNamedValueList()) {
				configurationSet.getConfigurationData().add(
						(NameValue) SystemEditorWrapperFactory.getInstance()
								.createWrapperObject(namedValue.getKey(),
										namedValue.getValue()));
			}

			configurationSet.setId(configset.getId());

			result.add(configurationSet);
		}

		return result;
	}

	private void createLeftControl(SashForm sashForm) {
		GridLayout gl;
		GridData gd;

		final Composite composite = new Composite(sashForm, SWT.FILL);
		gl = new GridLayout();
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		gl.numColumns = 1;
		composite.setLayout(gl);

		Composite componentNameComposite = new Composite(composite, SWT.NONE);
		gl = new GridLayout();
		gl.numColumns = 2;
		gl.marginHeight = 0;
		componentNameComposite.setLayout(gl);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 0;
		gd.verticalSpan = 0;
		componentNameComposite.setLayoutData(gd);

		Label componentNameLabelConst = new Label(componentNameComposite,
				SWT.NONE);
		gd = new GridData();
		gd.horizontalSpan = 0;
		gd.verticalSpan = 0;
		componentNameLabelConst.setLayoutData(gd);
		componentNameLabelConst.setText("ComponentName:");

		componentNameLabel = new Label(componentNameComposite, SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 0;
		gd.verticalSpan = 0;
		componentNameLabel.setLayoutData(gd);

		leftTableViewer = new TableViewer(composite, SWT.FULL_SELECTION
				| SWT.SINGLE | SWT.BORDER);
		leftTableViewer.setColumnProperties(new String[] {
				PROPERTY_ACTIVE_CONFIGSET, PROPERTY_CONFIG_SET });
		leftTableViewer.setContentProvider(new ArrayContentProvider());
		leftTableViewer.setLabelProvider(new ConfigSetLabelProvider());
		leftTableViewer.setCellModifier(new LeftTableCellModifier(
				leftTableViewer));

		leftTableViewer.setCellEditors(new CellEditor[] {
				new CheckboxCellEditor(leftTableViewer.getTable()),
				new TextCellEditor(leftTableViewer.getTable()) });

		leftTable = leftTableViewer.getTable();
		leftTable.setLinesVisible(true);
		leftTable.setHeaderVisible(true);
		gd = new GridData();
		gd.verticalAlignment = SWT.FILL;
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessVerticalSpace = true;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 0;
		gd.verticalSpan = 0;
		leftTable.setLayoutData(gd);

		gl = new GridLayout(1, false);
		gl.numColumns = 1;
		leftTable.setLayout(gl);

		final TableColumn activeCol = new TableColumn(leftTable, SWT.RIGHT);
		activeCol.setText("active");
		activeCol.setWidth(50);

		final TableColumn configCol = new TableColumn(leftTable, SWT.LEFT);
		configCol.setText("config");
		activeCol.setWidth(50);

		ControlAdapter controlAdapter = new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				composite.getParent().forceFocus();
				int width = composite.getClientArea().width - 2
						* leftTable.getBorderWidth() - activeCol.getWidth();
				configCol.setWidth(Math.max(10, width));
			}
		};

		activeCol.addControlListener(controlAdapter);
		composite.addControlListener(controlAdapter);

		Composite buttonCompsite = new Composite(composite, SWT.BOTTOM);
		gl = new GridLayout();
		gl.numColumns = 2;
		buttonCompsite.setLayout(gl);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		buttonCompsite.setLayoutData(gd);

		addConfigurationSetButton = new Button(buttonCompsite, SWT.NONE);
		addConfigurationSetButton.setText("Add");
		addConfigurationSetButton.setEnabled(false);
		gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.END;
		gd.widthHint = BUTTON_WIDTH;
		addConfigurationSetButton.setLayoutData(gd);
		addConfigurationSetButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				ConfigurationSetConfigurationWrapper configurationSetConfigurationWrapper = new ConfigurationSetConfigurationWrapper(
						null, null);
				configurationSetConfigurationWrapper
						.setId(createDefaultConfigurationSetName("configSet")); // modified_name

				copiedComponent
						.addConfigurationSet(configurationSetConfigurationWrapper);

				if (copiedComponent.getConfigurationSetList().size() == 1) { // コンフィグレーションセットが追加した１つしかない場合には、それをアクティブにする。
					copiedComponent
							.setActiveConfigSet(configurationSetConfigurationWrapper);
				}

				refreshLeftData();
				leftTable.forceFocus();
				leftTable.setSelection(leftTable.getItemCount() - 1);
				updateDeleteConfigurationSetButtonEnable();
				refreshRightData();
			}
		});

		deleteConfigurationSetButton = new Button(buttonCompsite, SWT.NONE);
		deleteConfigurationSetButton.setText("Delete");
		gd = new GridData();
		gd.horizontalAlignment = SWT.END;
		gd.widthHint = BUTTON_WIDTH;
		deleteConfigurationSetButton.setLayoutData(gd);
		deleteConfigurationSetButton.setEnabled(false);
		deleteConfigurationSetButton
				.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						if (leftTable.getSelectionIndex() != -1) {
							int selectionIndex = leftTable.getSelectionIndex();
							int activeConfigurationIndex = copiedComponent
									.getConfigurationSetList().indexOf(
											copiedComponent
													.getActiveConfigSet());

							int newIndex = Math.min(
									leftTable.getItemCount() - 1 - 1,
									selectionIndex);

							copiedComponent
									.removeConfigurationSet(copiedComponent
											.getConfigurationSetList().get(
													selectionIndex));

							if (selectionIndex == activeConfigurationIndex) {
								if (leftTable.getItemCount() >= 1) {
									ConfigurationSetConfigurationWrapper configurationSetConfigurationWrapper = null;
									if (leftTable.getItemCount() > 1) {
										configurationSetConfigurationWrapper = copiedComponent
												.getConfigurationSetList().get(
														newIndex);
									}

									copiedComponent
											.setActiveConfigSet(configurationSetConfigurationWrapper);
								}
							}

							refreshLeftData();
							leftTableViewer.refresh(); // ActiveなConfigurationSetを削除する場合に必要

							if (leftTable.getItemCount() >= 1) {
								leftTable.forceFocus();
								leftTable.setSelection(newIndex);
								updateDeleteConfigurationSetButtonEnable();
							}

							refreshRightData();
						}
					}
				});
		leftTableViewer
				.addPostSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						updateDeleteConfigurationSetButtonEnable();
					}
				});
	}

	private void createRightControl(SashForm sashForm) {
		GridLayout gl;
		GridData gd;

		final Composite composite = new Composite(sashForm, SWT.FILL);
		gl = new GridLayout();
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		composite.setLayout(gl);

		Composite configurationNameComposite = new Composite(composite,
				SWT.NONE);
		gl = new GridLayout();
		gl.numColumns = 2;
		gl.marginHeight = 0;
		configurationNameComposite.setLayout(gl);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 0;
		gd.verticalSpan = 0;
		configurationNameComposite.setLayoutData(gd);

		Label configurationNameLabelConst = new Label(
				configurationNameComposite, SWT.NONE);
		gd = new GridData();
		gd.horizontalSpan = 0;
		gd.verticalSpan = 0;
		configurationNameLabelConst.setLayoutData(gd);
		configurationNameLabelConst.setText("ConfigurationSet:");

		configrationSetNameLabel = new Label(configurationNameComposite,
				SWT.BORDER);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 0;
		gd.verticalSpan = 0;
		configrationSetNameLabel.setLayoutData(gd);

		rightTableViewer = new TableViewer(composite, SWT.FULL_SELECTION
				| SWT.SINGLE | SWT.BORDER);
		rightTableViewer.setColumnProperties(new String[] { PROPERTY_KEY,
				PROPERTY_VALUE });
		rightTableViewer.setContentProvider(new ArrayContentProvider());
		rightTableViewer.setLabelProvider(new MapEntryLabelProvider());
		rightTableViewer.setCellModifier(new RightTableCellModifier(
				rightTableViewer));
		rightTableViewer.setCellEditors(new CellEditor[] {
				new TextCellEditor(rightTableViewer.getTable()),
				new TextCellEditor(rightTableViewer.getTable()) });
		// rightTableViewer.setSorter(new ViewerSorter() {
		// @Override
		// public int compare(Viewer viewer, Object e1, Object e2) {
		// ConfigurationSetConfigurationWrapper currentConfugurationSet =
		// copiedComponent
		// .getConfigurationSetList().get(
		// leftTable.getSelectionIndex());
		//
		// List<NamedValueConfigurationWrapper> namedValueList =
		// currentConfugurationSet
		// .getNamedValueList();
		//
		// return namedValueList.indexOf(e1) - namedValueList.indexOf(e2);
		// }
		// });

		rightTable = rightTableViewer.getTable();
		rightTable.setLinesVisible(true);
		leftTable.addSelectionListener(new SelectionListener() {
			// caution leftTable
			public void widgetSelected(SelectionEvent e) {
				refreshRightData();
			}

			public void widgetDefaultSelected(SelectionEvent e) {
				refreshRightData();
			}
		});

		gd = new GridData();
		gd.verticalAlignment = SWT.FILL;
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessVerticalSpace = true;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 0;
		gd.verticalSpan = 0;
		rightTable.setLayoutData(gd);

		rightTable.setHeaderVisible(true);

		final TableColumn keyCol = new TableColumn(rightTable, SWT.LEFT);
		keyCol.setText("name");
		keyCol.setWidth(150);

		final TableColumn valueCol = new TableColumn(rightTable, SWT.LEFT);
		valueCol.setText("Value");
		valueCol.setWidth(300);

		// createTableEditor(rightTable, null);

		ControlAdapter controlAdapter = new ControlAdapter() {
			public void controlResized(ControlEvent e) {
				composite.getParent().forceFocus();
				int width = composite.getClientArea().width - 2
						* rightTable.getBorderWidth() - keyCol.getWidth();
				valueCol.setWidth(Math.max(10, width));
			}
		};

		keyCol.addControlListener(controlAdapter);
		composite.addControlListener(controlAdapter);

		Composite buttonCompsite = new Composite(composite, SWT.BOTTOM);
		gl = new GridLayout();
		gl.numColumns = 2;
		buttonCompsite.setLayout(gl);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		buttonCompsite.setLayoutData(gd);

		addNamedValueButton = new Button(buttonCompsite, SWT.NONE);
		addNamedValueButton.setText("Add");
		addNamedValueButton.setEnabled(false);
		gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.END;
		gd.widthHint = BUTTON_WIDTH;
		addNamedValueButton.setLayoutData(gd);
		addNamedValueButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ConfigurationSetConfigurationWrapper currentConfugurationSet = copiedComponent
						.getConfigurationSetList().get(
								leftTable.getSelectionIndex());

				NamedValueConfigurationWrapper namedValueConfigurationWrapper = new NamedValueConfigurationWrapper(
						null, null);
				namedValueConfigurationWrapper
						.setKey(createDefaultNamedValueKey("name")); // modified_key
				Any valueAny = CorbaUtil.getOrb().create_any();
				valueAny.insert_string("value");
				namedValueConfigurationWrapper.setValue(valueAny);// modified_value

				currentConfugurationSet
						.addNamedValue(namedValueConfigurationWrapper);

				refreshRightData();

				rightTable.forceFocus();
				rightTable.setSelection(rightTable.getItemCount() - 1);

				updateDeleteNamedValueButtonEnable();
			}
		});

		deleteNamedValueButton = new Button(buttonCompsite, SWT.NONE);
		deleteNamedValueButton.setText("Delete");
		gd = new GridData();
		gd.horizontalAlignment = SWT.END;
		gd.widthHint = BUTTON_WIDTH;
		deleteNamedValueButton.setLayoutData(gd);
		deleteNamedValueButton.setEnabled(false);
		deleteNamedValueButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (rightTable.getSelectionIndex() != -1) {
					ConfigurationSetConfigurationWrapper currentConfugurationSet = copiedComponent
							.getConfigurationSetList().get(
									leftTable.getSelectionIndex());

					int selectionIndex = rightTable.getSelectionIndex();

					currentConfugurationSet
							.removeNamedValue(currentConfugurationSet
									.getNamedValueList().get(selectionIndex));

					refreshRightData();

					if (rightTable.getItemCount() >= 1) {
						rightTable.forceFocus();
						rightTable.setSelection(Math.min(rightTable
								.getItemCount() - 1, selectionIndex));
						updateDeleteNamedValueButtonEnable();
					}
				}
			}
		});
		rightTableViewer
				.addPostSelectionChangedListener(new ISelectionChangedListener() {
					public void selectionChanged(SelectionChangedEvent event) {
						updateDeleteNamedValueButtonEnable();
					}
				});
	}

	private String createDefaultNamedValueKey(String preString) {
		ConfigurationSetConfigurationWrapper currentConfugurationSet = copiedComponent
				.getConfigurationSetList().get(leftTable.getSelectionIndex());

		int number = 1;
		for (;;) {
			boolean isExist = false;
			for (NamedValueConfigurationWrapper current : currentConfugurationSet
					.getNamedValueList()) {
				if ((preString + "_" + number).equals(current.getKey())) {
					isExist = true;
					break;
				}
			}

			if (isExist == false) {
				break;
			}

			++number;
		}

		return (preString + "_" + number);
	}

	private String createDefaultConfigurationSetName(String preString) {
		int number = 1;
		for (;;) {
			boolean isExist = false;
			for (ConfigurationSetConfigurationWrapper current : copiedComponent
					.getConfigurationSetList()) {
				if ((preString + "_" + number).equals(current.getId())) {
					isExist = true;
					break;
				}
			}

			if (isExist == false) {
				break;
			}

			++number;
		}

		return preString + "_" + number;
	}

	private void setLastNameServiceAddress(String address) {
		RTSystemEditorPlugin.getDefault().getPreferenceStore().setValue(
				LAST_NAMESERVICE_ADDRESS, address);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public Object getAdapter(Class adapter) {
		if (adapter.equals(IPropertySheetPage.class)) {
			return new RtcPropertySheetPage();
		}

		return super.getAdapter(adapter);
	}

	/**
	 * 選択を監視するリスナ
	 */
	private ISelectionListener selectionListener = new ISelectionListener() {
		public void selectionChanged(IWorkbenchPart part, ISelection selection) {
			targetComponent = null;
			if (selection instanceof IStructuredSelection) {
				IStructuredSelection sSelection = (IStructuredSelection) selection;
				if (AdapterUtil.getAdapter(sSelection.getFirstElement(),
						AbstractComponent.class) != null) {
					targetComponent = (AbstractComponent) AdapterUtil.getAdapter(
							sSelection.getFirstElement(), AbstractComponent.class);
					if (targetComponent instanceof Component
							&& !(targetComponent.eContainer() instanceof SystemDiagram)) {
						ComponentImpl.synchronizeLocalAttribute((Component) targetComponent,
								null);
					}
				}
			}
			buildData();
		}
	};

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
	}

	private void buildData() {
		copiedComponent = null;
		if (targetComponent != null) {
			copiedComponent = createConfigurationWrapper(targetComponent);
			originalComponent = createConfigurationWrapper(targetComponent);
		}

		refreshData();
	}

	private void refreshData() {
		applyButton.setEnabled(false);
		cancelButton.setEnabled(false);

		refreshLeftData();

		if (copiedComponent != null) {
			leftTable.setSelection(copiedComponent.getConfigurationSetList()
					.indexOf(copiedComponent.getActiveConfigSet()));
		}

		refreshRightData();
		if (copiedComponent != null) {
			applyButton.setEnabled(true);
			cancelButton.setEnabled(true);
		}
	}

	private void refreshLeftData() {
		leftTableViewer.setInput(Collections.EMPTY_LIST);
		componentNameLabel.setText("");
		addConfigurationSetButton.setEnabled(false);

		if (copiedComponent != null) {
			addConfigurationSetButton.setEnabled(true);
			componentNameLabel.setText(targetComponent.getInstanceNameL());
			leftTableViewer.setInput(copiedComponent.getConfigurationSetList());

			if (leftTable.getItemCount() > 0) {
				leftTable.select(0);
			}
		}

		updateDeleteConfigurationSetButtonEnable();
	}

	private void updateDeleteConfigurationSetButtonEnable() {
		boolean deleteConfigurationSetEnabled = false;
		if (leftTable.getSelectionIndex() != -1) {
			deleteConfigurationSetEnabled = true;
		}

		deleteConfigurationSetButton.setEnabled(deleteConfigurationSetEnabled);
	}

	private void refreshRightData() {
		rightTableViewer.setInput(Collections.EMPTY_LIST);
		configrationSetNameLabel.setText("");
		addNamedValueButton.setEnabled(false);

		if (copiedComponent != null) {
			if (leftTable.getSelectionIndex() != -1) {
				addNamedValueButton.setEnabled(true);
				ConfigurationSetConfigurationWrapper currentConfugurationSet = copiedComponent
						.getConfigurationSetList().get(
								leftTable.getSelectionIndex());

				configrationSetNameLabel.setText(currentConfugurationSet
						.getId());

				rightTableViewer.setInput(currentConfugurationSet
						.getNamedValueList());
			}
		}

		updateDeleteNamedValueButtonEnable();
	}

	private void updateDeleteNamedValueButtonEnable() {
		boolean deleteNamedValueEnabled = false;
		if (rightTable.getSelectionIndex() != -1) {
			deleteNamedValueEnabled = true;
		}

		deleteNamedValueButton.setEnabled(deleteNamedValueEnabled);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void dispose() {
		getSite().getWorkbenchWindow().getSelectionService()
				.removeSelectionListener(selectionListener);
		super.dispose();
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void setFocus() {
	}

	/**
	 * ConfigurationViewの内部モデルであるComponentConfigurationWrapperを作成する
	 */
	public ComponentConfigurationWrapper createConfigurationWrapper(
			AbstractComponent target) {
		ComponentConfigurationWrapper result = new ComponentConfigurationWrapper();
		List<ConfigurationSetConfigurationWrapper> configurationSetList = result
				.getConfigurationSetList();

		for (Iterator iter = target.getConfigurationSets().iterator(); iter
				.hasNext();) {
			ConfigurationSet configurationSet = (ConfigurationSet) iter.next();

			ConfigurationSetConfigurationWrapper configurationSetConfigurationWrapper = new ConfigurationSetConfigurationWrapper(
					configurationSet, configurationSet.getId());

			List<NamedValueConfigurationWrapper> namedValueList = configurationSetConfigurationWrapper
					.getNamedValueList();

			for (Iterator iterator = configurationSet.getConfigurationData()
					.iterator(); iterator.hasNext();) {

				NameValue nameValue = (NameValue) iterator.next();
				NamedValueConfigurationWrapper namedValueConfigurationWrapper = new NamedValueConfigurationWrapper(
						nameValue.getName(), nameValue.getValue());

				namedValueList.add(namedValueConfigurationWrapper);
				Collections.sort(namedValueList);
			}

			if (target.getActiveConfigurationSet() != null
					&& target.getActiveConfigurationSet().getId().equals(
							configurationSet.getId())) {
				result.setActiveConfigSet(configurationSetConfigurationWrapper);
			}

			configurationSetList.add(configurationSetConfigurationWrapper);
			Collections.sort(configurationSetList);
		}

		return result;
	}

	/**
	 * 左テーブルのCellModifierクラス
	 */
	public class LeftTableCellModifier implements ICellModifier {
		private TableViewer viewer;

		public LeftTableCellModifier(TableViewer viewer) {
			this.viewer = viewer;
		}

		public boolean canModify(Object element, String property) {
			return true;
		}

		public Object getValue(Object element, String property) {
			Object result = null;
			if (PROPERTY_ACTIVE_CONFIGSET.equals(property)) {
				result = Boolean.TRUE;
			} else if (PROPERTY_CONFIG_SET.equals(property)) {
				result = ((ConfigurationSetConfigurationWrapper) element)
						.getId();
			}

			return result;
		}

		public void modify(Object element, String property, Object value) {
			ConfigurationSetConfigurationWrapper configurationSet = null;
			if (element instanceof Item) {
				configurationSet = ((ConfigurationSetConfigurationWrapper) ((Item) element)
						.getData());
			}

			if (PROPERTY_ACTIVE_CONFIGSET.equals(property)) {
				copiedComponent.setActiveConfigSet(configurationSet);

				viewer.refresh();
			} else if (PROPERTY_CONFIG_SET.equals(property)) {
				boolean isDuplicate = false;
				for (ConfigurationSetConfigurationWrapper current : copiedComponent
						.getConfigurationSetList()) {
					if (configurationSet != current
							&& ((String) value).equals(current.getId())) {
						isDuplicate = true;
						break;
					}
				}

				String newConfigurationSetName = (String) value;
				if (isDuplicate) {
					MessageDialog.openWarning(viewer.getControl().getShell(),
							"警告", "既にその名前は使用されています。");
					newConfigurationSetName = createDefaultConfigurationSetName((String) value);
				}

				configurationSet.setId(newConfigurationSetName);

				viewer.update(configurationSet, null);
			}

		}
	}

	/**
	 * 右テーブルのCellModifierクラス
	 */
	public class RightTableCellModifier implements ICellModifier {
		private TableViewer viewer;

		public RightTableCellModifier(TableViewer viewer) {
			this.viewer = viewer;
		}

		public boolean canModify(Object element, String property) {
			NamedValueConfigurationWrapper item = (NamedValueConfigurationWrapper) element;

			boolean result = false;
			if (PROPERTY_KEY.equals(property)) {
				result = true;
			} else if (PROPERTY_VALUE.equals(property)) {
				try {
					if (item.getValue().type().kind() == TCKind.tk_wstring) {
						item.getValue().extract_wstring();
					} else {
						item.getValue().extract_string();
					}
					result = true;
				} catch (Exception e) {
					// void
				}
			}

			return result;
		}

		public Object getValue(Object element, String property) {
			NamedValueConfigurationWrapper item = (NamedValueConfigurationWrapper) element;

			String result = null;
			if (PROPERTY_KEY.equals(property)) {
				result = item.getKey();
			} else if (PROPERTY_VALUE.equals(property)) {
				try {
					if (item.getValue().type().kind() == TCKind.tk_wstring) {
						result = item.getValue().extract_wstring();
					} else {
						result = item.getValue().extract_string();
					}
				} catch (Exception e) {
					try {
						result = item.getValue().type().name();
					} catch (BadKind e1) {
						result = "<Unknown>";
					}
				}
			}

			return result;
		}

		public void modify(Object element, String property, Object value) {
			if (element instanceof TableItem == false) {
				return;
			}
			NamedValueConfigurationWrapper item = ((NamedValueConfigurationWrapper) ((TableItem) element)
					.getData());

			ConfigurationSetConfigurationWrapper currentConfugurationSet = copiedComponent
					.getConfigurationSetList().get(
							leftTable.getSelectionIndex());

			if (PROPERTY_KEY.equals(property)) {
				boolean isDuplicate = false;
				for (NamedValueConfigurationWrapper current : currentConfugurationSet
						.getNamedValueList()) {
					if (item != current
							&& ((String) value).equals(current.getKey())) {
						isDuplicate = true;
						break;
					}
				}

				String newKey = (String) value;
				if (isDuplicate) {
					MessageDialog.openWarning(viewer.getControl().getShell(),
							"警告", "既にそのキーは使用されています。");
					newKey = createDefaultNamedValueKey((String) value);
				}

				item.setKey(newKey);
			} else if (PROPERTY_VALUE.equals(property)) {
				Any valueAny = CorbaUtil.getOrb().create_any();
				if (StringUtils.isAsciiPrintable((String) value)) {
					valueAny.insert_string((String) value);
				} else {
					valueAny.insert_wstring((String) value);
				}

				item.setValue(valueAny);
			}

			viewer.update(item, null);
		}
	}

	/**
	 * LabelProviderクラス
	 */
	public class ConfigSetLabelProvider extends LabelProvider implements
			ITableLabelProvider, ITableColorProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			Image result = null;
			if (columnIndex == 0) {
				ConfigurationSetConfigurationWrapper item = (ConfigurationSetConfigurationWrapper) element;
				if (item == copiedComponent.getActiveConfigSet()) {
					result = RTSystemEditorPlugin
							.getCachedImage("icons/Radiobutton_Checked.png");
				} else {
					result = RTSystemEditorPlugin
							.getCachedImage("icons/Radiobutton_Unchecked.png");
				}
			}

			return result;
		}

		public String getColumnText(Object element, int columnIndex) {
			ConfigurationSetConfigurationWrapper item = (ConfigurationSetConfigurationWrapper) element;

			String result = null;
			if (columnIndex == 1) {
				result = getModiedLabelString(item.isNameModified())
						+ item.getId();
			}

			return result;
		}

		public Color getBackground(Object element, int columnIndex) {
			ConfigurationSetConfigurationWrapper configurationSetConfigurationWrapper = (ConfigurationSetConfigurationWrapper) element;

			boolean isModify = false;
			if (columnIndex == 0) {
				if (isActiveConfigurationSetModified()) {
					if (targetComponent.getActiveConfigurationSet() == configurationSetConfigurationWrapper
							.getConfigurationSet()
							|| copiedComponent.getActiveConfigSet() == configurationSetConfigurationWrapper) {
						isModify = true;
					}
				}
			} else if (columnIndex == 1) {
				isModify = configurationSetConfigurationWrapper
						.isNameModified();
			}

			Color color = null;
			if (isModify) {
				color = colorRegistry.get(MODIFY_COLOR);
			}

			return color;
		}

		public Color getForeground(Object element, int columnIndex) {
			return null;
		}
	}

	/**
	 * LabelProviderクラス
	 */
	public class MapEntryLabelProvider extends LabelProvider implements
			ITableLabelProvider, ITableColorProvider {

		public Image getColumnImage(Object element, int columnIndex) {
			return null;
		}

		public String getColumnText(Object element, int columnIndex) {
			NamedValueConfigurationWrapper item = (NamedValueConfigurationWrapper) element;

			String result = null;
			if (columnIndex == 0) {
				result = getModiedLabelString(item.isKeyModified())
						+ item.getKey();
			} else if (columnIndex == 1) {
				try {
					if (item.getValue().type().kind() == TCKind.tk_wstring) {
						result = getModiedLabelString(item.isValueModified())
								+ item.getValue().extract_wstring();
					} else {
						result = getModiedLabelString(item.isValueModified())
								+ item.getValue().extract_string();
					}
				} catch (Exception e) {
					try {
						result = item.getValue().type().id();
					} catch (BadKind e1) {
						result = "<Unknown>";
					}
				}
			}

			return result;
		}

		public Color getForeground(Object element, int columnIndex) {
			return null;
		}

		public Color getBackground(Object element, int columnIndex) {
			Color color = null;
			NamedValueConfigurationWrapper namedValueConfigurationWrapper = (NamedValueConfigurationWrapper) element;
			if (columnIndex == 0
					&& namedValueConfigurationWrapper.isKeyModified()
					|| columnIndex == 1
					&& namedValueConfigurationWrapper.isValueModified()) {
				color = colorRegistry.get(MODIFY_COLOR);
			}

			if (columnIndex == 1) {
				try {
					NamedValueConfigurationWrapper item = (NamedValueConfigurationWrapper) element;
					if (item.getValue().type().kind() == TCKind.tk_wstring) {
						item.getValue().extract_wstring();
					} else {
						item.getValue().extract_string();
					}
				} catch (Exception e) { // Stringに変換できない値
					color = colorRegistry.get(CANT_MODIFY_COLOR);
				}
			}

			return color;
		}
	}

	private String getModiedLabelString(boolean bool) {
		String result = "";
		if (bool) {
			result = "*";
		}

		return result;
	}
}
