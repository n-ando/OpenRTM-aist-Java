package jp.go.aist.rtm.rtcbuilder.ui.editors;

import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import jp.go.aist.rtm.rtcbuilder.RtcBuilderPlugin;
import jp.go.aist.rtm.rtcbuilder.model.component.BuildView;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

public abstract class AbstractEditorFormPage extends FormPage {

	protected RtcBuilderEditor editor;
	protected BuildView buildview;

	/**
	 * コンストラクタ
	 * 
	 */
	public AbstractEditorFormPage(RtcBuilderEditor editor, String id, String name) {
		super(editor, id, name);
		this.editor = editor;
		this.buildview = editor.getEMFmodel();
	}
	
	protected Composite createSectionBase(IManagedForm managedForm,
			ScrolledForm form, String sectionName, int colNum) {
		GridLayout gl;

		Section section = managedForm.getToolkit().createSection(
				form.getBody(),
				Section.TITLE_BAR | Section.EXPANDED | Section.TWISTIE);
		section.setText(sectionName);
		GridData gridData = new GridData();
		gridData.grabExcessHorizontalSpace = true;
		gridData.horizontalAlignment = GridData.FILL;
		section.setLayoutData(gridData);
		Composite composite = managedForm.getToolkit().createComposite(section,
				SWT.NULL);
		composite.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		managedForm.getToolkit().paintBordersFor(composite);
		gl = new GridLayout(colNum, false);
		composite.setLayout(gl);
		section.setClient(composite);
		return composite;
	}

	protected Text createLabelAndText(FormToolkit toolkit, Composite composite,
			String labelString) {
		return createLabelAndText(toolkit, composite, labelString, SWT.NONE);
	}
	protected Text createLabelAndText(FormToolkit toolkit, Composite composite,
			String labelString, int style) {
		Label label = toolkit.createLabel(composite, labelString);
		final Text text = toolkit.createText(composite, "", style);
		text.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
				update();
			}

			public void keyPressed(KeyEvent e) {
			}
		});
		text.addMouseListener(new MouseListener() {
			public void mouseDoubleClick(MouseEvent e) {
			}

			public void mouseDown(MouseEvent e) {
				text.setSelection(0, text.getText().length());
			}

			public void mouseUp(MouseEvent e) {
				text.setSelection(0, text.getText().length());
			}

		});
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		text.setLayoutData(gridData);
		return text;
	}
	
	protected Combo createLabelAndCombo(FormToolkit toolkit, Composite composite,
			String labelString, String[] items) {
		Label label = toolkit.createLabel(composite, labelString);
		Combo combo = new Combo(composite, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.setItems(items);
		combo.select(0);
		// combo.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		// toolkit.paintBordersFor(combo);
		combo.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				update();
			}
		});
		combo.addSelectionListener(new SelectionListener() {
			  public void widgetDefaultSelected(SelectionEvent e){
			  }
			  public void widgetSelected(SelectionEvent e){
					update();
			  }
			});
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		combo.setLayoutData(gridData);

		return combo;
	}

	protected Text createLabelAndDirectory(FormToolkit toolkit,
								Composite composite, String labelString) {
		GridData gd;

		if(!labelString.equals("")) {
			Label label = toolkit.createLabel(composite, labelString);
		}
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
		checkButton.addSelectionListener(new SelectionAdapter() {
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(getEditorSite()
						.getShell());
				if (text.getText().length() > 0)
					dialog.setFilterPath(text.getText());
				String newPath = dialog.open();
				if (newPath != null) {
					text.setText(newPath);
					update();
				}
			}
		});

		return text;
	}

	protected String getValue(String value) {
		String result = value;
		if (result == null) {
			result = "";
		}

		return result;
	}

	protected String getText(String text) {
		String result = text;
		if ("".equals(result)) {
			result = null;
		}
		return result;
	}

	protected Combo createEditableCombo(FormToolkit toolkit, Composite composite,
			String labelString, String key) {
		Label label = toolkit.createLabel(composite, labelString);
		Combo combo = new Combo(composite, SWT.DROP_DOWN);
		loadDefaultComboValue(combo, key);

		combo.select(0);
		combo.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				update();
			}
		});
		combo.addSelectionListener(new SelectionListener() {
			  public void widgetDefaultSelected(SelectionEvent e){
			  }
			  public void widgetSelected(SelectionEvent e){
					update();
			  }
			});
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		combo.setLayoutData(gridData);

		return combo;
	}

	/**
	 * ワークスペースの永続情報から、コンボのリストと選択インデックスをロードする
	 * 
	 * @param combo
	 */
	protected void loadDefaultComboValue(Combo combo, String key) {
		String defaultString = RtcBuilderPlugin.getDefault().getPreferenceStore()
				.getString(key);
		StringTokenizer tokenize = new StringTokenizer(defaultString, ",");
		while (tokenize.hasMoreTokens()) {
			combo.add(tokenize.nextToken());
		}
	}

	/**
	 * 入力したカテゴリを永続情報に設定する
	 * 
	 * @param combo
	 */
	protected void addDefaultComboValue(Combo combo, String key) {
		String value = combo.getText(); // local

		List<String> valueList = Arrays.asList(combo.getItems());
		if (valueList.contains(value) == false) {
			String defaultString = RtcBuilderPlugin.getDefault()
					.getPreferenceStore().getString(key);

			String newString = "";
			if ("".equals(defaultString)) {
				newString = value;
			} else {
				newString = value + "," + defaultString;
			}

			RtcBuilderPlugin.getDefault().getPreferenceStore().setValue(key, newString);
		}
	}

	abstract protected void update();
	abstract protected String validateParam();

}
