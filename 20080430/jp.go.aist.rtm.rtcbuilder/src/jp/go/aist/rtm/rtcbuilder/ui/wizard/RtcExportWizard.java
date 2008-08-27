package jp.go.aist.rtm.rtcbuilder.ui.wizard;

import java.util.List;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class RtcExportWizard extends Wizard implements IExportWizard {
	    private IStructuredSelection selection;

	    private RtcExportWizardPage mainPage;

	    /**
	     * Creates a wizard for exporting workspace resources to a zip file.
	     */
	    public RtcExportWizard() {
	        AbstractUIPlugin plugin = (AbstractUIPlugin) Platform.getPlugin(PlatformUI.PLUGIN_ID);
	        IDialogSettings workbenchSettings = plugin.getDialogSettings();
	        IDialogSettings section = workbenchSettings.getSection("RtcExportWizard");//$NON-NLS-1$
	        if (section == null) {
				section = workbenchSettings.addNewSection("RtcExportWizard");//$NON-NLS-1$
			}
	        setDialogSettings(section);
	    }

	    /* (non-Javadoc)
	     * Method declared on IWizard.
	     */
	    public void addPages() {
	        super.addPages();
	        mainPage = new RtcExportWizardPage(selection);
	        addPage(mainPage);
	    }

	    /* (non-Javadoc)
	     * Method declared on IWorkbenchWizard.
	     */
	    public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
	        this.selection = currentSelection;
	        List selectedResources = IDE.computeSelectedResources(currentSelection);
	        if (!selectedResources.isEmpty()) {
	            this.selection = new StructuredSelection(selectedResources);
	        }
	        setWindowTitle("エクスポート");
	        setNeedsProgressMonitor(true);
	    }

	    /* (non-Javadoc)
	     * Method declared on IWizard.
	     */
	    public boolean performFinish() {
	        return mainPage.finish();
	    }
}
