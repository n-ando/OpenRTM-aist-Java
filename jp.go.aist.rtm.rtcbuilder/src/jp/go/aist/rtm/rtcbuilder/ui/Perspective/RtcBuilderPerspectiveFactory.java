package jp.go.aist.rtm.rtcbuilder.ui.Perspective;

import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;

import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstants;
import jp.go.aist.rtm.rtcbuilder.ui.views.ComponentBuildView;

public class RtcBuilderPerspectiveFactory implements IPerspectiveFactory {

	public void createInitialLayout(IPageLayout layout) {
		String editor = layout.getEditorArea();
		
		IFolderLayout left = layout.createFolder("navigator", IPageLayout.LEFT, 0.2f, editor);
		left.addView(JavaUI.ID_PACKAGES);
		
		IFolderLayout bottom = layout.createFolder("rtcView", IPageLayout.BOTTOM, 0.7f, editor);
		bottom.addView(ComponentBuildView.VIEW_ID);
		
		layout.addActionSet(IRtcBuilderConstants.NEWEDITOR_ACTION_ID);
	}
}
