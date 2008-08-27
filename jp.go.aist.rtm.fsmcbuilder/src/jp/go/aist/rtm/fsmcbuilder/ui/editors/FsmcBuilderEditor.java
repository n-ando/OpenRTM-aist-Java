package jp.go.aist.rtm.fsmcbuilder.ui.editors;

import java.util.ArrayList;
import java.util.EventObject;

import jp.go.aist.rtm.fsmcbuilder.FsmcBuilderContextMenuProvider;
import jp.go.aist.rtm.fsmcbuilder.FsmcBuilderPlugin;
import jp.go.aist.rtm.fsmcbuilder.model.ModelFactory;
import jp.go.aist.rtm.fsmcbuilder.model.ModelPackage;
import jp.go.aist.rtm.fsmcbuilder.model.State;
import jp.go.aist.rtm.fsmcbuilder.model.StateMachineDiagram;
import jp.go.aist.rtm.fsmcbuilder.model.Transition;
import jp.go.aist.rtm.fsmcbuilder.ui.editors.action.MoveElementAction;
import jp.go.aist.rtm.fsmcbuilder.ui.editpart.StateMachineEditPartFactory;
import jp.go.aist.rtm.fsmcbuilder.ui.editpart.tree.TreeEditPartFactory;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.parts.ScrollableThumbnail;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.gef.DefaultEditDomain;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.KeyHandler;
import org.eclipse.gef.KeyStroke;
import org.eclipse.gef.LayerConstants;
import org.eclipse.gef.editparts.ScalableRootEditPart;
import org.eclipse.gef.editparts.ZoomManager;
import org.eclipse.gef.palette.ConnectionCreationToolEntry;
import org.eclipse.gef.palette.CreationToolEntry;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PanningSelectionToolEntry;
import org.eclipse.gef.requests.CreationFactory;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.gef.ui.actions.AlignmentAction;
import org.eclipse.gef.ui.actions.DirectEditAction;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.gef.ui.actions.ZoomInAction;
import org.eclipse.gef.ui.actions.ZoomOutAction;
import org.eclipse.gef.ui.parts.ContentOutlinePage;
import org.eclipse.gef.ui.parts.GraphicalEditorWithPalette;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.jface.action.IAction;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.actions.ActionFactory;
import org.eclipse.ui.part.IPageSite;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.ui.views.properties.IPropertySheetEntry;
import org.eclipse.ui.views.properties.IPropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetPage;
import org.eclipse.ui.views.properties.PropertySheetSorter;

public class FsmcBuilderEditor extends GraphicalEditorWithPalette {
	public static final String FSMC_BUILDER_EDITOR_ID = FsmcBuilderEditor.class.getName();

	private StateMachineDiagram stateMachineModel = null;

	public FsmcBuilderEditor() {
		DefaultEditDomain domain = new DefaultEditDomain(this);
		setEditDomain(domain);
	}

	@Override
	protected void initializeGraphicalViewer() {
		stateMachineModel = ModelFactory.eINSTANCE.createStateMachineDiagram();
		GraphicalViewer viewer = getGraphicalViewer();
		//
		MenuManager menuMgr = viewer.getContextMenu();
		((IEditorSite) getSite()).registerContextMenu(menuMgr, viewer, false);
		viewer.setContents(stateMachineModel);
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
//		Map map = new HashMap();
//		map.put(XMLResource.OPTION_ENCODING, "UTF-8");
//		try {
//			resource.save(map);
//			getCommandStack().markSaveLocation();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}

	@Override
	protected void configureGraphicalViewer() {
		super.configureGraphicalViewer();
		GraphicalViewer viewer = getGraphicalViewer();

		ScalableRootEditPart rootEditPart = new ScalableRootEditPart();
		viewer.setRootEditPart(rootEditPart);

	    viewer.setEditPartFactory(new StateMachineEditPartFactory());
		
	    ZoomManager manager = rootEditPart.getZoomManager();
	    double[] zoomLevels = new double[] {
	      0.25,0.5,0.75,1.0,1.5,2.0,2.5,3.0,4.0,5.0,10.0,20.0
	    };
	    manager.setZoomLevels(zoomLevels);
	    
	    ArrayList<String> zoomContributions = new ArrayList<String>();
	    zoomContributions.add(ZoomManager.FIT_ALL);
	    zoomContributions.add(ZoomManager.FIT_HEIGHT);
	    zoomContributions.add(ZoomManager.FIT_WIDTH);
	    manager.setZoomLevelContributions(zoomContributions);

	    IAction action = new ZoomInAction(manager);
	    getActionRegistry().registerAction(action);
	    action = new ZoomOutAction(manager);
	    getActionRegistry().registerAction(action);

	    buildKeyHandler(getGraphicalViewer());

		FsmcBuilderContextMenuProvider menuProvider = new FsmcBuilderContextMenuProvider(viewer, getActionRegistry());
		viewer.setContextMenu(menuProvider);
		getSite().registerContextMenu(menuProvider, viewer);
	}

	private void buildKeyHandler(GraphicalViewer viewer) {
		KeyHandler keyHandler = new KeyHandler();
		keyHandler.put(	KeyStroke.getPressed(SWT.DEL, 127, 0),
				getActionRegistry().getAction(ActionFactory.DELETE.getId()));
		keyHandler.put( KeyStroke.getPressed(SWT.F2, 0),
				getActionRegistry().getAction(GEFActionConstants.DIRECT_EDIT));
		//
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_UP, 0),
				getActionRegistry().getAction(MoveElementAction.MOVE_UP_ACTION_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_DOWN, 0),
				getActionRegistry().getAction(MoveElementAction.MOVE_DOWN_ACTION_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_RIGHT, 0),
				getActionRegistry().getAction(MoveElementAction.MOVE_RIGHT_ACTION_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_LEFT, 0),
				getActionRegistry().getAction(MoveElementAction.MOVE_LEFT_ACTION_ID));

		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_UP, SWT.SHIFT),
				getActionRegistry().getAction(MoveElementAction.MOVE_UP_L_ACTION_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_DOWN, SWT.SHIFT),
				getActionRegistry().getAction(MoveElementAction.MOVE_DOWN_L_ACTION_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_RIGHT, SWT.SHIFT),
				getActionRegistry().getAction(MoveElementAction.MOVE_RIGHT_L_ACTION_ID));
		keyHandler.put(KeyStroke.getPressed(SWT.ARROW_LEFT, SWT.SHIFT),
				getActionRegistry().getAction(MoveElementAction.MOVE_LEFT_L_ACTION_ID));

		viewer.setKeyHandler(keyHandler);
	}

	public void commandStackChanged(EventObject arg0) {
		firePropertyChange(IEditorPart.PROP_DIRTY);
		super.commandStackChanged(arg0);
	}

	private class EcoreCreationFactoy implements CreationFactory {
		
		private EClass eClass;
		
		public EcoreCreationFactoy(EClass class1) {
			super();
			eClass = class1;
		}
		
		public Object getNewObject() {
			return ModelFactory.eINSTANCE.create(eClass);
		}
		
		public Object getObjectType() {
			return eClass;
		}
	}

	protected PaletteRoot getPaletteRoot() {
		PaletteRoot root = new PaletteRoot();

		PaletteGroup group = new PaletteGroup("選択");
		group.add(new PanningSelectionToolEntry()); 
//		group.add(new MarqueeToolEntry());
		root.add(group);
		
		PaletteDrawer drawer = new PaletteDrawer("要素");
		drawer.add(new CreationToolEntry("State", "Stateを作成",
				new EcoreCreationFactoy(ModelPackage.eINSTANCE.getState()),
				FsmcBuilderPlugin.getImageDescriptor("icons/state16.gif"),
				FsmcBuilderPlugin.getImageDescriptor("icons/state16.gif")));
		drawer.add(new CreationToolEntry("InitialState", "InitialStateを作成",
				new EcoreCreationFactoy(ModelPackage.eINSTANCE.getInitialState()),
				FsmcBuilderPlugin.getImageDescriptor("icons/initialState.gif"),
				FsmcBuilderPlugin.getImageDescriptor("icons/initialState.gif")));
		drawer.add(new CreationToolEntry("FinalState", "FinalStateを作成",
				new EcoreCreationFactoy(ModelPackage.eINSTANCE.getFinalState()),
				FsmcBuilderPlugin.getImageDescriptor("icons/finalState.gif"),
				FsmcBuilderPlugin.getImageDescriptor("icons/finalState.gif")));
		drawer.add(new CreationToolEntry("Choice", "Choiceを作成",
				new EcoreCreationFactoy(ModelPackage.eINSTANCE.getChoice()),
				FsmcBuilderPlugin.getImageDescriptor("icons/choice.png"),
				FsmcBuilderPlugin.getImageDescriptor("icons/choice.png")));
		root.add(drawer);
		
		drawer = new PaletteDrawer("コネクタ");
		drawer.add(new ConnectionCreationToolEntry("transition", "transitionを設定",
				new EcoreCreationFactoy(ModelPackage.eINSTANCE.getTransition()),
				FsmcBuilderPlugin.getImageDescriptor("icons/transition16.gif"),
				FsmcBuilderPlugin.getImageDescriptor("icons/transition16.gif")));
		
		root.add(drawer);
		return root;
	}

	public Object getAdapter(Class type) {
		if( type.equals(IPropertySheetPage.class) ) {
			FsmcPropertySheetPage psPage = new FsmcPropertySheetPage();
			FsmcPropertySheetSorter sorter = new FsmcPropertySheetSorter();
			psPage.setSorter(sorter);
			return psPage;
		} else if( type == ZoomManager.class ) {
		      return ((ScalableRootEditPart) getGraphicalViewer().getRootEditPart()).getZoomManager();
		} else if( type == IContentOutlinePage.class ) {
		      return new FsmcContentOutlinePage();
	    }

		return super.getAdapter(type);
	}
	
	protected void createActions() {
		super.createActions();
		ActionRegistry registry = getActionRegistry();

		IAction action = new DirectEditAction( (IWorkbenchPart)this );
		registry.registerAction(action);

		getSelectionActions().add(action.getId());

		//要素の整列
	    action = new AlignmentAction( (IWorkbenchPart)this, PositionConstants.LEFT );
	    registry.registerAction(action);
	    getSelectionActions().add(action.getId());

	    action = new AlignmentAction( (IWorkbenchPart)this, PositionConstants.CENTER );
	    registry.registerAction(action);
	    getSelectionActions().add(action.getId());

	    action = new AlignmentAction( (IWorkbenchPart)this, PositionConstants.RIGHT );
	    registry.registerAction(action);
	    getSelectionActions().add(action.getId());

	    action = new AlignmentAction( (IWorkbenchPart)this, PositionConstants.TOP );
	    registry.registerAction(action);
	    getSelectionActions().add(action.getId());

	    action = new AlignmentAction( (IWorkbenchPart)this, PositionConstants.MIDDLE) ;
	    registry.registerAction(action);
	    getSelectionActions().add(action.getId());

	    action = new AlignmentAction( (IWorkbenchPart)this, PositionConstants.BOTTOM );
	    registry.registerAction(action);
	    getSelectionActions().add(action.getId());
	    //
	    //要素の移動
		action = new MoveElementAction(this, MoveElementAction.MOVE_UP_ACTION_ID );
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MoveElementAction(this,	MoveElementAction.MOVE_DOWN_ACTION_ID);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MoveElementAction(this, MoveElementAction.MOVE_RIGHT_ACTION_ID);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MoveElementAction(this,	MoveElementAction.MOVE_LEFT_ACTION_ID);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MoveElementAction(this, MoveElementAction.MOVE_UP_L_ACTION_ID);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MoveElementAction(this, MoveElementAction.MOVE_DOWN_L_ACTION_ID);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MoveElementAction(this,	MoveElementAction.MOVE_RIGHT_L_ACTION_ID);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());

		action = new MoveElementAction(this, MoveElementAction.MOVE_LEFT_L_ACTION_ID);
		getActionRegistry().registerAction(action);
		getSelectionActions().add(action.getId());
	}
	
	private class FsmcPropertySheetPage extends PropertySheetPage {
		@Override
		protected void setSorter(PropertySheetSorter sorter) {
			super.setSorter(sorter);
		}
	}

	private class FsmcPropertySheetSorter extends PropertySheetSorter {

		@Override
		public void sort(IPropertySheetEntry[] entries) {
			EditPart targetEditPart =	(EditPart)((IStructuredSelection)getGraphicalViewer().getSelection()).getFirstElement();
			Object target = targetEditPart.getModel();
			IPropertySheetEntry[] newentries = new IPropertySheetEntry[entries.length];
			if( target instanceof Transition || target instanceof State ) {
				String[] keys;
				if( target instanceof Transition ) {
					keys = Transition.PropertyKeys;
				} else {
					keys = State.PropertyKeys;
				}
					
				for( int intIdx=0; intIdx<entries.length; intIdx++ ) {
					newentries[intIdx] = getEntry(keys[intIdx], entries);
				}
			}
		}
		
		private IPropertySheetEntry getEntry(String key, IPropertySheetEntry[] entries) {
			for( int intIdx=0; intIdx<entries.length; intIdx++ ) {
				if( entries[intIdx].getDisplayName().equals(key) ) {
					return entries[intIdx];
				}
			}
			return null;
		}
		
	}
	private class FsmcContentOutlinePage extends ContentOutlinePage {

	    private SashForm sash;
	    private ScrollableThumbnail thumbnail;
	    private DisposeListener disposeListener;

	    public FsmcContentOutlinePage() {
	      super(new TreeViewer());
	    }

	    public void init(IPageSite pageSite) {
	      super.init(pageSite);
	      ActionRegistry registry = getActionRegistry();
	      IActionBars bars = pageSite.getActionBars();

	      String id = ActionFactory.UNDO.getId();
	      bars.setGlobalActionHandler(id, registry.getAction(id));

	      id = ActionFactory.REDO.getId();
	      bars.setGlobalActionHandler(id, registry.getAction(id));

	      id = ActionFactory.DELETE.getId();
	      bars.setGlobalActionHandler(id, registry.getAction(id));
	      bars.updateActionBars();
	    }

	    public void createControl(Composite parent) {
	    	sash = new SashForm(parent, SWT.VERTICAL);

	    	getViewer().createControl(sash);
	    	getViewer().setEditDomain(getEditDomain());
	    	getViewer().setEditPartFactory(new TreeEditPartFactory());
	    	getViewer().setContents(stateMachineModel);
	    	getSelectionSynchronizer().addViewer(getViewer());
	    	//
	    	Canvas canvas = new Canvas(sash, SWT.BORDER);
	    	LightweightSystem lws = new LightweightSystem(canvas);

	    	thumbnail = new ScrollableThumbnail((Viewport) ((ScalableRootEditPart) getGraphicalViewer()
	              .getRootEditPart()).getFigure());
	    	thumbnail.setSource(((ScalableRootEditPart) getGraphicalViewer().getRootEditPart())
	          .getLayer(LayerConstants.PRINTABLE_LAYERS));
	      
	    	lws.setContents(thumbnail);

			disposeListener = new DisposeListener() {
				public void widgetDisposed(DisposeEvent e) {
					if( thumbnail != null ) {
						thumbnail.deactivate();
						thumbnail = null;
					}
				}
			};
	      getGraphicalViewer().getControl().addDisposeListener(disposeListener);
	    }

	    public Control getControl() {
	    	return sash;
	    }
	    
	    public void dispose() {
	        getSelectionSynchronizer().removeViewer(getViewer());
	        if( getGraphicalViewer().getControl() != null && !getGraphicalViewer().getControl().isDisposed() )
	        	getGraphicalViewer().getControl().removeDisposeListener(disposeListener);
	        super.dispose();
	    }
	}
}
