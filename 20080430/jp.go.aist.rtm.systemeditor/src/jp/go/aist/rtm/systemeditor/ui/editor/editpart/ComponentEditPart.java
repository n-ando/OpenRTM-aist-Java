package jp.go.aist.rtm.systemeditor.ui.editor.editpart;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import jp.go.aist.rtm.systemeditor.manager.SystemEditorPreferenceManager;
import jp.go.aist.rtm.systemeditor.ui.action.OpenCompositeComponentAction;
import jp.go.aist.rtm.systemeditor.ui.editor.action.ChangeComponentDirectionAction;
import jp.go.aist.rtm.systemeditor.ui.editor.editpolicy.ChangeDirectionEditPolicy;
import jp.go.aist.rtm.systemeditor.ui.editor.editpolicy.ComponentComponentEditPolicy;
import jp.go.aist.rtm.systemeditor.ui.editor.editpolicy.EditPolicyConstraint;
import jp.go.aist.rtm.systemeditor.ui.editor.figure.ComponentLayout;
import jp.go.aist.rtm.systemeditor.ui.util.Draw2dUtil;
import jp.go.aist.rtm.toolscommon.model.component.AbstractComponent;
import jp.go.aist.rtm.toolscommon.model.component.Component;
import jp.go.aist.rtm.toolscommon.model.component.ComponentPackage;
import jp.go.aist.rtm.toolscommon.model.component.ComponentSpecification;
import jp.go.aist.rtm.toolscommon.model.component.ExecutionContext;
import jp.go.aist.rtm.toolscommon.model.component.LifeCycleState;
import jp.go.aist.rtm.toolscommon.model.core.CorePackage;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.Panel;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gef.ui.actions.ActionRegistry;
import org.eclipse.jface.action.IAction;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.PlatformUI;

/**
 * コンポーネントのEditPart
 * <p>
 * GEFの仕様では子供のEditPartは親のEditPartに含まれなければならないが、ポートをコンポーネントからはみ出して表示しなければならない。
 * これを満たしながら、一見コンポーネントの外にポートが出ているように見せるために、コンポーネントのボディのドローイングの範囲を狭めることで実現しているため、特殊な実装になっている。
 */
public class ComponentEditPart extends AbstractEditPart {

	/**
	 * コンポーネントの周りとコンポーネントのボディまでのスペース
	 */
	public static final int SPACE = 7;

	private final PropertyChangeSupport propertyChangeSupport = new PropertyChangeSupport(
			this);

	private ComponentFloatingLabel componentLabel;

	/**
	 * コンストラクタ
	 * 
	 * @param actionRegistry
	 *            ActionRegistry
	 */
	public ComponentEditPart(ActionRegistry actionRegistry) {
		super(actionRegistry);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected IFigure createFigure() {
		AbstractComponent component = getModel();

		Figure result = new Panel() {

			@Override
			/**
			 * {@inheritDoc}
			 */
			protected boolean useLocalCoordinates() {
				return true;
			}

			@Override
			/**
			 * {@inheritDoc}
			 * <p>
			 * コンポーネントの外にポートが出ているように見せるために、コンポーネントのボディのドローイングの範囲を狭めている
			 */
			protected void paintFigure(Graphics graphics) {
				if (isOpaque()) {
					Rectangle bound = new Rectangle(getBounds());
					graphics.fillRectangle(bound.expand(-SPACE, -SPACE));

					Color saveForegroundColor = graphics.getForegroundColor();
					// graphics.setForegroundColor(ColorConstants.black);
					graphics.drawRectangle(bound);
					graphics.setForegroundColor(saveForegroundColor);
				}
			}

			@Override
			/**
			 * {@inheritDoc}
			 * <p>
			 * コンポーネントの外にポートが出ているように見せるため、空実装
			 */
			protected void paintBorder(Graphics graphics) {
				// void
			}

			@Override
			/**
			 * {@inheritDoc}
			 * <p>
			 * コンポーネントの制約が変更されるたびに、ラベルも移動させる。
			 * （責務の分離からすればあまりよくないが、ファイル内に閉じているのでここに実装にした）
			 */
			public void setBounds(Rectangle rect) {
				super.setBounds(rect);

				Rectangle newComponentLabelRectangle = componentLabel
						.getTextBounds().getCopy();
				newComponentLabelRectangle.x = rect.getCenter().x
						- componentLabel.getTextBounds().width / 2;
				newComponentLabelRectangle.y = rect.getBottom().y;

				componentLabel.setBounds(newComponentLabelRectangle);

				propertyChangeSupport.firePropertyChange("Bounds", null, rect);
			}

		};

		result.addMouseListener(new MouseListener.Stub() {
			@Override
			/**
			 * {@inheritDoc}
			 * <p>
			 * コンポーネントを右クリック（+Shift）して、方向を変換する機能の実装
			 */
			public void mousePressed(MouseEvent me) {
				if (me.button == 2) { // right click
					IAction action;
					if (me.getState() == MouseEvent.SHIFT) {
						action = getActionRegistry()
								.getAction(
										ChangeComponentDirectionAction.VERTICAL_DIRECTION_ACTION_ID);
					} else {
						action = getActionRegistry()
								.getAction(
										ChangeComponentDirectionAction.HORIZON_DIRECTION_ACTION_ID);
					}

					action.run();
				}
			}
		});

		ComponentLayout layout = new ComponentLayout(getModel());
		result.setLayoutManager(layout);

		result.setBackgroundColor(ColorConstants.orange);

		// 注意：ComponentLabelの親はSystemDiagram
		componentLabel = new ComponentFloatingLabel(
				((AbstractGraphicalEditPart) getParent()).getFigure());
		componentLabel.setText(getModel().getInstanceNameL());
		componentLabel.setSize(30, 10);

		return result;
	}

	/**
	 * 設定マネージャを監視するリスナ
	 */
	PropertyChangeListener preferenceChangeListener = new PropertyChangeListener() {
		public void propertyChange(PropertyChangeEvent evt) {
			refreshConponent();
		}
	};

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void activate() {
		super.activate();
		if (getModel().isCompositeComponent()) {
			for (Iterator iterator = getModel().getAllComponents().iterator(); iterator
					.hasNext();) {
				AbstractComponent component = (AbstractComponent) iterator.next();
				component.eAdapters().add(this);
			}
		}
		SystemEditorPreferenceManager.getInstance().addPropertyChangeListener(
				preferenceChangeListener);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public void deactivate() {
		componentLabel.deactivate();
		super.deactivate();
		if (getModel().isCompositeComponent()) {
			for (Iterator iterator = getModel().getAllComponents().iterator(); iterator
					.hasNext();) {
				AbstractComponent component = (AbstractComponent) iterator.next();
				component.eAdapters().remove(this);
			}
		}
		SystemEditorPreferenceManager.getInstance()
				.removePropertyChangeListener(preferenceChangeListener);
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new ComponentComponentEditPolicy());

		installEditPolicy(EditPolicyConstraint.CHANGE_DIRECTION_ROLE,
				new ChangeDirectionEditPolicy());
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected void refreshVisuals() {
		getFigure().setBackgroundColor(getNewBodyColor());

		getFigure().setForegroundColor(getNewBorderColor());

		Rectangle modelRec = Draw2dUtil.toDraw2dRectangle(getModel()
				.getConstraint());

		Rectangle newRectangle = modelRec.getCopy();

		((GraphicalEditPart) getParent()).setLayoutConstraint(this,
				getFigure(), newRectangle);
	}

	/**
	 * 最新のボーダー部の色を取得する
	 * 
	 * @return
	 */
	public Color getNewBorderColor() {
		Color exexucitonContextColor = SystemEditorPreferenceManager
				.getInstance().getColor(
						SystemEditorPreferenceManager.COLOR_RTC_STATE_UNKNOWN);
		if (getModel() instanceof Component) {
			Component component = (Component) getModel();
			if (component.getExecutionContextState() == ExecutionContext.STATE_RUNNING) {
				exexucitonContextColor = SystemEditorPreferenceManager
						.getInstance()
						.getColor(
								SystemEditorPreferenceManager.COLOR_RTC_EXECUTION_CONTEXT_RUNNING);
			} else if (component.getExecutionContextState() == ExecutionContext.STATE_STOPPED) {
				exexucitonContextColor = SystemEditorPreferenceManager
						.getInstance()
						.getColor(
								SystemEditorPreferenceManager.COLOR_RTC_EXECUTION_CONTEXT_STOPPED);
			} else if (component.getExecutionContextState() == ExecutionContext.STATE_UNKNOWN) {
				exexucitonContextColor = SystemEditorPreferenceManager
						.getInstance()
						.getColor(
								SystemEditorPreferenceManager.COLOR_RTC_STATE_UNKNOWN);
			}
		}
		

		return exexucitonContextColor;
	}

	/**
	 * 最新のボディ部の色を取得する
	 * 
	 * @return
	 */
	public Color getNewBodyColor() {
		Color stateColor = SystemEditorPreferenceManager
				.getInstance()
				.getColor(SystemEditorPreferenceManager.COLOR_RTC_STATE_UNKNOWN);
		if (getModel() instanceof Component) {
			Component component = (Component) getModel();
			if (component.isCompositeComponent()) {
				boolean isError = false;
				int componentCount = 0;
				int activeCount = 0;
				stateColor = SystemEditorPreferenceManager.getInstance().getColor(
						SystemEditorPreferenceManager.COLOR_RTC_STATE_INACTIVE);
				for (Iterator<Component> iterator = component.getAllComponents()
						.iterator(); iterator.hasNext();) {
					Component temp = iterator.next();
					if (!temp.isCompositeComponent()){
						componentCount++;
						if (temp.getComponentState() == LifeCycleState.RTC_ERROR) {
							isError = true;
							break;
						} else if (temp.getComponentState() == LifeCycleState.RTC_ACTIVE) {
							activeCount++;
						}
					}
				}
				if (isError) {
					stateColor = SystemEditorPreferenceManager.getInstance().getColor(
							SystemEditorPreferenceManager.COLOR_RTC_STATE_ERROR);
				}else if (componentCount > 0
						&& componentCount == activeCount){
					stateColor = SystemEditorPreferenceManager.getInstance().getColor(
							SystemEditorPreferenceManager.COLOR_RTC_STATE_ACTIVE);
				}
			} else {
				if (component.getComponentState() == LifeCycleState.RTC_ACTIVE) {
					stateColor = SystemEditorPreferenceManager.getInstance().getColor(
							SystemEditorPreferenceManager.COLOR_RTC_STATE_ACTIVE);
				} else if (component.getComponentState() == LifeCycleState.RTC_CREATED) {
					stateColor = SystemEditorPreferenceManager.getInstance().getColor(
							SystemEditorPreferenceManager.COLOR_RTC_STATE_CREATED);
				} else if (component.getComponentState() == LifeCycleState.RTC_ERROR) {
					stateColor = SystemEditorPreferenceManager.getInstance().getColor(
							SystemEditorPreferenceManager.COLOR_RTC_STATE_ERROR);
				} else if (component.getComponentState() == LifeCycleState.RTC_INACTIVE) {
					stateColor = SystemEditorPreferenceManager.getInstance().getColor(
							SystemEditorPreferenceManager.COLOR_RTC_STATE_INACTIVE);
				} else if (component.getComponentState() == LifeCycleState.RTC_UNKNOWN) {
					stateColor = SystemEditorPreferenceManager.getInstance().getColor(
							SystemEditorPreferenceManager.COLOR_RTC_STATE_UNKNOWN);
				}
			}
			
		} else if (getModel() instanceof ComponentSpecification){
			stateColor = SystemEditorPreferenceManager.getInstance().getColor(
					SystemEditorPreferenceManager.COLOR_RTC_STATE_INACTIVE);
		}
		

		return stateColor;
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	public AbstractComponent getModel() {
		return (AbstractComponent) super.getModel();
	}

	/**
	 * {@inheritDoc}component.eAdapters().add(this);
	 */
	public void notifyChanged(Notification notification) {
		if (ComponentPackage.eINSTANCE.getAbstractComponent_Components().equals(
						notification.getFeature())) {
			if (notification.getEventType() == Notification.ADD) {
				AbstractComponent component = (AbstractComponent)notification.getNewValue();
				component.eAdapters().add(this);
				if (component.isCompositeComponent()) {
					for (Iterator iterator = component.getAllComponents()
							.iterator(); iterator.hasNext();) {
						((AbstractComponent) iterator.next()).eAdapters().add(this);
					}
				}
			}else if (notification.getEventType() == Notification.REMOVE) {
				((AbstractComponent)notification.getOldValue()).eAdapters().remove(this);
			}
			refreshConponent();
			((SystemDiagramEditPart)getParent()).refreshSystemDiagram();
		} else if (ComponentPackage.eINSTANCE
				.getAbstractComponent_CompositeComponent().equals(
						notification.getFeature())
				|| CorePackage.eINSTANCE.getModelElement_Constraint().equals(
						notification.getFeature())
				|| ComponentPackage.eINSTANCE.getComponent_ComponentState()
						.equals(notification.getFeature())
				|| ComponentPackage.eINSTANCE
						.getComponent_ExecutionContextState().equals(
								notification.getFeature())
				|| ComponentPackage.eINSTANCE
						.getAbstractComponent_OutportDirection().equals(
								notification.getFeature())) {
			refreshConponent();
		}else if (getModel() instanceof ComponentSpecification) {
			refreshConponent();
		}
	}

	private void refreshConponent() {
		PlatformUI.getWorkbench().getDisplay().asyncExec(new Runnable() {
			public void run() {
				if (isActive()) {
					refresh();
					refreshChildren();
					getFigure().invalidate();
				}
			}
		});
	}

	@Override
	/**
	 * {@inheritDoc}
	 */
	protected List getModelChildren() {
		List result = new ArrayList();
		result.addAll(getModel().getAllInPorts());
		result.addAll(getModel().getAllOutPorts());
		result.addAll(getModel().getAllServiceports());

		return result;
	}

	/**
	 * コンポーネントFigureの変更の通知を行うリスナを登録する
	 * 
	 * @param listener
	 */
	public void addPropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.addPropertyChangeListener(listener);
	}

	/**
	 * コンポーネントFigureの変更の通知を行うリスナを削除する
	 * 
	 * @param listener
	 */
	public void removePropertyChangeListener(PropertyChangeListener listener) {
		propertyChangeSupport.removePropertyChangeListener(listener);
	}

	/**
	 * システムダイアグラムのコンポーネントに表示されるラベル
	 */
	public class ComponentFloatingLabel extends Label {

		/**
		 * コンストラクタ
		 * 
		 * @param parentFigure
		 *            親フィギュア
		 */
		public ComponentFloatingLabel(IFigure parentFigure) {
			setParent(parentFigure);
			parentFigure.add(this);
		}

		/**
		 * 削除する場合に呼び出されることを意図する
		 */
		public void deactivate() {
			getParent().remove(this);
		}

		@Override
		/**
		 * {@inheritDoc}
		 */
		public boolean isFocusTraversable() {
			return false;
		}

		@Override
		/**
		 * {@inheritDoc}
		 */
		public boolean isRequestFocusEnabled() {
			return false;
		}

		@Override
		/**
		 * {@inheritDoc}
		 */
		protected boolean isMouseEventTarget() {
			return false;
		}
	}

	@Override
	public void performRequest(Request req) {
		if (req.getType().equals(RequestConstants.REQ_OPEN)) {
			IAction action = getActionRegistry().getAction(
					OpenCompositeComponentAction.ACTION_ID);
			OpenCompositeComponentAction openAction = null;
			if (getModel().isCompositeComponent()) {
				openAction = (OpenCompositeComponentAction) getModel().getOpenCompositeComponentAction();
				if (openAction == null) {
					openAction = new OpenCompositeComponentAction(
							((OpenCompositeComponentAction) action)
									.getParentSystemDiagramEditor());
				}
				getModel().setOpenCompositeComponentAction(openAction);
				openAction.setCompositeComponent(getModel());
				openAction.run();
			}
		}
		super.performRequest(req);
	}
}
