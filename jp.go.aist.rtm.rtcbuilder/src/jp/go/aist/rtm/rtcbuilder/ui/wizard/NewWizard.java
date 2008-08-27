package jp.go.aist.rtm.rtcbuilder.ui.wizard;

import java.io.ByteArrayInputStream;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;

import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstants;
import jp.go.aist.rtm.rtcbuilder.generator.ProfileHandler;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExecutableExtension;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.dialogs.WizardNewProjectCreationPage;
import org.eclipse.ui.wizards.newresource.BasicNewProjectResourceWizard;

import com.sun.org.apache.xerces.internal.jaxp.datatype.DatatypeFactoryImpl;

public class NewWizard extends Wizard implements INewWizard, IExecutableExtension {

	private WizardNewProjectCreationPage newProjectPage;
	private IConfigurationElement configElement;

	public NewWizard() {
		super();
		setWindowTitle("RT-Component Builder Project");
	}

	public boolean performFinish() {
		IProject projectHandle = newProjectPage.getProjectHandle();
		try {
			projectHandle.create(null);
			projectHandle.open(null);
			//
			DatatypeFactory dateFactory = new DatatypeFactoryImpl();
			String dateTime = dateFactory.newXMLGregorianCalendar(new GregorianCalendar()).toString();
			ProfileHandler handler = new ProfileHandler();
			String xmlFile = handler.createInitialRtcXml(dateTime);
			//
			IFile rtcxml = projectHandle.getFile(IRtcBuilderConstants.DEFAULT_RTC_XML);
			rtcxml.create(new ByteArrayInputStream(xmlFile.getBytes()), false, null);
		} catch (CoreException ex) {
			System.out.println(ex);
			return false;
		}
		// パースペクティブを切り替え
		BasicNewProjectResourceWizard.updatePerspective(configElement);
		return true;
	}
	
    public void setInitializationData(
		IConfigurationElement cfig, String propertyName, Object data) {
        configElement = cfig;
    }
	

	public void init(IWorkbench workbench, IStructuredSelection selection) {
	}

	public void addPages() {
		newProjectPage = new WizardNewProjectCreationPage("ProjectCreation");
		addPage(newProjectPage);
	}

}
