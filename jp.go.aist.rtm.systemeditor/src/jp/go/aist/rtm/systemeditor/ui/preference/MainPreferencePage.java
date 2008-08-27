package jp.go.aist.rtm.systemeditor.ui.preference;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;

/**
 * RtcLinkê›íËÇÃÉÅÉCÉì
 * <p>
 * ì¡Ç…âΩÇ‡ï\é¶ÇµÇ»Ç¢
 */
public class MainPreferencePage extends PreferencePage implements
		IWorkbenchPreferencePage {
	@Override
	/**
	 * {@inheritDoc}
	 */
	protected Control createContents(Composite parent) {
		Composite composite = new Composite(parent, SWT.NONE);
		return composite;
	}

	/**
	 * {@inheritDoc}
	 */
	public void init(IWorkbench workbench) {
	}
}
