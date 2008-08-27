package jp.go.aist.rtm.repositoryView.ui.action;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;

import jp.go.aist.rtm.repositoryView.model.LocalRVRootItem;
import jp.go.aist.rtm.repositoryView.model.RepositoryViewFactory;
import jp.go.aist.rtm.repositoryView.model.RepositoryViewItem;
import jp.go.aist.rtm.repositoryView.model.RepositoryViewLeafItem;
import jp.go.aist.rtm.repositoryView.ui.views.RepositoryView;
import jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification;
import jp.go.aist.rtm.toolscommon.util.ProfileHandler;

import org.eclipse.jface.action.IAction;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class ViewActionLoadDirecroty implements IViewActionDelegate  {

	private RepositoryView view;
	
	public void init(IViewPart view) {
		this.view = (RepositoryView) view;
	}

	public void run(IAction action) {

		DirectoryDialog directoryDialog = new DirectoryDialog(view.getSite().getShell(),SWT.NULL);
		directoryDialog.setText("ディレクトリの選択");
		directoryDialog.setMessage("ディレクトリを選択してください");
		String filePath = directoryDialog.open();

		if (filePath == null) {
    		return;
    	}
    	File dir = new File(filePath);
    	File[] files = dir.listFiles(new Filter());
		
    	TreeViewer viewer = this.view.getViewer();
    	RepositoryViewItem rootItem = new RepositoryViewItem("root", 0);
    	rootItem.setChildren((ArrayList)viewer.getInput());
    	
    	RepositoryViewItem itemFirst = rootItem.getChild(filePath);
    	if( itemFirst == null ) {
    		itemFirst = new LocalRVRootItem(filePath);
    		((ArrayList)viewer.getInput()).add(itemFirst);
    	}
    	ComponentSpecification module = null;
    	ProfileHandler handler = new ProfileHandler();
		for (int intIdx = 0; intIdx < files.length; intIdx++){
    		try {
				module = handler.createComponent(files[intIdx].toString());
			} catch (Exception e) {
				MessageDialog.openError(view.getSite().getShell(), "エラー",	
				"Profile の読み込みに失敗しました");
				return;
			}
			module.setAliasName(module.getInstanceNameL() + "(" + files[intIdx].getName() + ")");
			module.setPathURI("file://localhost/" + files[intIdx].getName());
			RepositoryViewFactory.buildTree(itemFirst, module, RepositoryViewLeafItem.RTC_LEAF);
		}
    	viewer.refresh();
	}

	public void selectionChanged(IAction action, ISelection selection) {
	}

	class Filter implements FileFilter {
		public boolean accept(File file) {
			if (!file.isFile()) {
				return false;
			}
			String s = file.getName();
			int x = s.lastIndexOf('.');
			if ( x < 0) {
				return false;
			}
			String extention = s.substring(x+1).toLowerCase();
			if (extention.endsWith("xml")) {
				return true;
			}
			return false;
		}
	}

	
}
