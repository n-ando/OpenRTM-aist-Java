package jp.go.aist.rtm.rtclink.rcp;

import org.eclipse.jface.action.GroupMarker;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.IContributionItem;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.actions.ContributionItemFactory;
import org.eclipse.ui.actions.ActionFactory.IWorkbenchAction;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.internal.ActionSetContributionItem;

public class ApplicationActionBarAdvisor extends ActionBarAdvisor {

	private IAction saveAction, saveAsAction, quitAction, aboutAction, helpAction;
	private IWorkbenchAction preferenceAction;
	private IContributionItem viewAction;

	public ApplicationActionBarAdvisor(IActionBarConfigurer configurer) {
        super(configurer);
    }

    protected void makeActions(IWorkbenchWindow window) {
    	saveAction = ActionFactory.SAVE.create(window);
        register(saveAction);
        //
    	saveAsAction = ActionFactory.SAVE_AS.create(window);
        register(saveAsAction);
        //
		quitAction = ActionFactory.QUIT.create(window);
        register(quitAction);
        //
		aboutAction = ActionFactory.ABOUT.create(window);
        register(aboutAction);
		//
        viewAction = ContributionItemFactory.VIEWS_SHORTLIST.create(window);
        //
		preferenceAction = ActionFactory.PREFERENCES.create(window);
		register(preferenceAction);
		//
//		helpAction = ActionFactory.ABOUT.create(window);
//		helpAction.setText("&Help Index");
//        register(helpAction);
    }

    protected void fillMenuBar(IMenuManager menuBar) {
		MenuManager fileMenu = new MenuManager("&File", IWorkbenchActionConstants.M_FILE);
		menuBar.add(fileMenu);
        fileMenu.add(saveAction);
        fileMenu.add(saveAsAction);
		fileMenu.add(new Separator());
		fileMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		fileMenu.add(new Separator());
        fileMenu.add(quitAction);
        //
//		IMenuManager toolsMenu = new MenuManager("&Window", IWorkbenchActionConstants.M_WINDOW);
		IMenuManager toolsMenu = new MenuManager("&Window", "RtcLink_Window");
		menuBar.add(toolsMenu);
		toolsMenu.add(viewAction);
//		toolsMenu.add(new GroupMarker(IWorkbenchActionConstants.MB_ADDITIONS));
		toolsMenu.add(new Separator());
		toolsMenu.add(preferenceAction);
        //
		MenuManager helpMenu = new MenuManager("&Help", IWorkbenchActionConstants.M_HELP);
//		MenuManager helpMenu = new MenuManager("&Help", "RtcLink_Help");
		menuBar.add(helpMenu);
        helpMenu.add(aboutAction);
//        helpMenu.add(new Separator());
//        helpMenu.add(helpAction);
    }

//	@Override
//	public void fillActionBars(int flags) {
//		
//		if( (flags & FILL_MENU_BAR) != 0 ) {
//		      // トップレベル メニューの取得
//		      IMenuManager menuManager = configurer.getMenuManager();
//		      // ファイル メニューの作成
//		      menuManager.add(createFileMenuManager(window));
//		      // ウィンドウ メニューの作成
//		      menuManager.add(createWindowMenuManager(window));
//		    }
//		super.fillActionBars(flags);
//	}
}
