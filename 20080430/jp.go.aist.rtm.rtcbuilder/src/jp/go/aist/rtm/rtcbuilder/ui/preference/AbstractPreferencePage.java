package jp.go.aist.rtm.rtcbuilder.ui.preference;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

public abstract class AbstractPreferencePage extends PreferencePage  implements 
						IWorkbenchPreferencePage {

	public AbstractPreferencePage() {
	}

	public AbstractPreferencePage(String title) {
		super(title);
	}

	public AbstractPreferencePage(String title, ImageDescriptor image) {
		super(title, image);
	}

	@Override
	protected Control createContents(Composite parent) {
		return null;
	}

	public void init(IWorkbench workbench) {
	}

	protected Combo createLabelAndCombo(Group baseGroup, String labelString, String[] items) {
		Label label = new Label(baseGroup, SWT.NULL);
		label.setText(labelString);
		Combo combo = new Combo(baseGroup, SWT.DROP_DOWN | SWT.READ_ONLY);
		combo.setItems(items);
		combo.select(0);
		combo.addKeyListener(new KeyListener() {
			public void keyReleased(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				updateStatus();
			}
		});
		combo.addSelectionListener(new SelectionListener() {
			  public void widgetDefaultSelected(SelectionEvent e){
			  }
			  public void widgetSelected(SelectionEvent e){
				  updateStatus();
			  }
			});
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL);
		combo.setLayoutData(gridData);

		return combo;
	}

	protected Text createLabelAndText(Group baseGroup, String labelString) {
		GridData gd;
		Label label = new Label(baseGroup, SWT.NULL);
		label.setText(labelString);
		Text targetText = new Text(baseGroup, SWT.SINGLE | SWT.BORDER | SWT.LEFT);
		targetText.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent e) {
				updateStatus();
			}
		});
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		targetText.setLayoutData(gd);
		
		return targetText;
	}

	protected Group createGroup(Composite composite, String label) {
		GridLayout gridLayout;
		GridData gd;
		Group targetGroup = new Group(composite, SWT.NONE);
		gridLayout = new GridLayout();
		gridLayout.numColumns = 2;
		targetGroup.setLayout(gridLayout);
		gd = new GridData(GridData.FILL_HORIZONTAL);
		targetGroup.setLayoutData(gd);

		targetGroup.setText(label);
		return targetGroup;
	}

	/**
	 * èÛë‘ÇïœçXÇµÇΩç€Ç…åƒÇ—èoÇ∑Ç±Ç∆
	 */
	private void updateStatus() {
		setValid(validate());
	}

	protected abstract boolean validate();
}
