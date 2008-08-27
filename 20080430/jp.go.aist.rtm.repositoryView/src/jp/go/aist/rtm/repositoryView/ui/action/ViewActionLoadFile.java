package jp.go.aist.rtm.repositoryView.ui.action;

import java.io.File;
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
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IViewActionDelegate;
import org.eclipse.ui.IViewPart;

public class ViewActionLoadFile implements IViewActionDelegate {

	private RepositoryView view;
	
	public void init(IViewPart view) {
		this.view = (RepositoryView)view;
	}

	public void run(IAction action) {

		//対象ファイルの選択
		FileDialog fileDialog = new FileDialog(view.getSite().getShell(), SWT.OPEN);
		fileDialog.setFilterNames(new String[]{"XMLファイル(.xml)"});
		fileDialog.setFilterExtensions(new String[]{"*.xml"});
		String targetFileName = fileDialog.open();
		
		ComponentSpecification module = null;
    	ProfileHandler handler = new ProfileHandler();
		try {
			module = handler.createComponent(targetFileName);
		} catch (Exception e) {
			MessageDialog.openError(view.getSite().getShell(), "エラー",	
					"Profile の読み込みに失敗しました");
			return;
		}
		module.setPathId(targetFileName.substring(0, targetFileName.lastIndexOf(File.separator)));
		String fileName = targetFileName.substring(targetFileName.lastIndexOf(File.separator) + 1);
		module.setAliasName(module.getInstanceNameL() + "(" + fileName + ")");
		module.setPathURI("file://localhost/" + targetFileName);
		
    	TreeViewer viewer = this.view.getViewer();
    	RepositoryViewItem rootItem = new RepositoryViewItem("root", 0);
    	rootItem.setChildren((ArrayList)viewer.getInput());

 		RepositoryViewItem itemFirst = rootItem.getChild(module.getPathId());
 		if( itemFirst==null ) {
    		itemFirst = new LocalRVRootItem(module.getPathId());
			rootItem.addChild(itemFirst);
 		}
		RepositoryViewFactory.buildTree(itemFirst, module, RepositoryViewLeafItem.RTC_LEAF);
		viewer.refresh();
	}

	public void selectionChanged(IAction action, ISelection selection) {
		
	}
}
