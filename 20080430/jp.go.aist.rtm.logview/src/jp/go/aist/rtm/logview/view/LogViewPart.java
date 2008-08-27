package jp.go.aist.rtm.logview.view;

import java.util.LinkedList;

import jp.go.aist.rtm.logview.RTC.LogView;
import jp.go.aist.rtm.logview.preferences.PreferenceManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class LogViewPart extends ViewPart implements PaintListener, Runnable  {

    public static final String LOGVIEW_ID = "jp.go.aist.rtm.logview.view";

    private double[][] drawData = new double[0][0];
    private LinkedList<String> labelData = new LinkedList<String>();
	private int m_maxDataNum = 100;
	private int m_maxDataY;
	private int m_minDataY;
	private int m_scaleStepY;
	private Graph m_graph; 
	private Text m_maxYText; 
	private Text m_minYText; 
	private Text m_scaleStepYText; 
	private Text m_redrawPeriodText; 
	private Canvas m_canvas;
	private Thread m_updateThread;
	private int m_redrawPeriod;
	private int m_redrawPeriod_Input;
	private boolean m_inputFromText;
	private static LogView logViewComp = null;

	public LogViewPart() {
		super();
		String[] args = new String[]{"-f",
								PreferenceManager.getInstance().getConfigurationFile(
										PreferenceManager.CONFIGURATION_FILE)};
		m_redrawPeriod = PreferenceManager.getInstance().getRedrawPeriod(
								PreferenceManager.REDRAW_RERIOD);
		m_inputFromText = false;
		if( logViewComp==null ) {
			logViewComp = new LogView();
			logViewComp.open(this, args);
		} else {
			logViewComp.setView(this);
		}
	    drawData = new double[2][m_maxDataNum];
//		testView test = new testView();
//		test.init(this);
//		test.open();
	}

	public int getRedrawInterval() {
		if( m_inputFromText ) {
			m_inputFromText = false;
			return m_redrawPeriod_Input;
		}
		return m_redrawPeriod;
	}
	
	public void exitComponent() {
		logViewComp = null;
	}
	
	public void setRedrawInterval(int interval) {
		if(m_redrawPeriod != interval) {
			m_redrawPeriod = interval;
			try {
				Display display = m_canvas.getDisplay();
				if( !display.isDisposed() ) {
					display.asyncExec(new Runnable() {
						public void run() {
							m_redrawPeriodText.setText(Integer.valueOf(m_redrawPeriod).toString());
						}
					});
				}
			} catch(Exception ex) {
			}
		}
	}
	
	public void addDrawData(String labelData, double[] appendData) {
		if(this.labelData.size()>m_maxDataNum-1) {
			this.labelData.removeFirst();
		}
		this.labelData.addLast(labelData);
		if(drawData[0].length>m_maxDataNum-1) {
			for(int intIdx2=0;intIdx2<drawData.length;intIdx2++) {
				for(int intIdx=0;intIdx<m_maxDataNum-1;intIdx++){
					drawData[intIdx2][intIdx] = drawData[intIdx2][intIdx+1]; 
				}
				drawData[intIdx2][m_maxDataNum-1] = appendData[intIdx2]; 
			}
		}
	}

	public void setDrawData(LinkedList<String> labelData, double[][] drawData) {
		this.labelData = labelData;
		this.drawData = drawData;
	}

	public void createPartControl(Composite parent) {
		GridLayout gl;
		GridData gd;

		gl = new GridLayout();
		gl.numColumns = 2;
		parent.setLayout(gl);

		SashForm sashForm = new SashForm(parent, SWT.HORIZONTAL);
		gd = new GridData();
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessVerticalSpace = true;
		gd.verticalAlignment = SWT.FILL;
		sashForm.setLayoutData(gd);

		createLeftControl(sashForm);
		createRightControl(sashForm);

		sashForm.setWeights(new int[] { 80, 20 });
	}
	//

	public void setFocus() {
	}

	public void paintControl(PaintEvent e) {
		Image bufferedImage = null;
		GC bufferedGC = null;
		try {
			bufferedImage = m_graph.drawGraph(labelData, drawData, m_maxDataY, m_minDataY, m_scaleStepY);
			bufferedGC = new GC(bufferedImage);
			e.gc.drawImage(bufferedImage, 0, 0);
		} catch(Exception ex) {
		} finally {
			bufferedGC.dispose();
			bufferedImage.dispose();
		}
    }
	
	public void run() {
		try{
			Display display = m_canvas.getDisplay();
			while(true) {
				display.asyncExec(new Runnable() {
					public void run() {
						if( !m_canvas.isDisposed() ) m_canvas.redraw();
					}});
				Thread.sleep(m_redrawPeriod);
			}
		} catch ( InterruptedException ex ) {
		}
	}

	public void dispose() {
		if( m_updateThread != null ) {
			m_updateThread.interrupt();
		}
		m_canvas.dispose();
		super.dispose();
	}
	
    private void createLeftControl(SashForm sashForm) {
		GridLayout gl;
		GridData gd;

		Composite composite = new Composite(sashForm, SWT.FILL);
		gl = new GridLayout();
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		gl.numColumns = 1;
		composite.setLayout(gl);
		//
//		Canvas canvas = new Canvas(composite, SWT.FILL);
		m_canvas = new Canvas(composite, SWT.NO_BACKGROUND);
		m_canvas.setBackground(composite.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.verticalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.grabExcessVerticalSpace = true;
		gd.horizontalSpan = 0;
		gd.verticalSpan = 0;
		m_canvas.setLayoutData(gd);
		//
		m_maxDataY = 40;
		m_minDataY = 0;
		m_scaleStepY = 40;
		//
		m_graph = new Graph(composite);
		//
		m_canvas.addPaintListener(this);
		m_updateThread = new Thread(this);
        m_updateThread.start();
	}
	private void createRightControl(SashForm sashForm) {
		GridLayout gl;
		GridData gd;

		final Composite composite = new Composite(sashForm, SWT.FILL);
		gl = new GridLayout();
		gl.marginWidth = 0;
		gl.marginHeight = 0;
		composite.setLayout(gl);

		Composite settingComposite = new Composite(composite, SWT.NONE);
		gl = new GridLayout();
		gl.numColumns = 2;
		gl.marginHeight = 0;
		settingComposite.setLayout(gl);
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		gd.horizontalSpan = 0;
		gd.verticalSpan = 0;
		settingComposite.setLayoutData(gd);

		Label maxYLabel = new Label(settingComposite,SWT.NULL);
		maxYLabel.setText("Ymax:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.END;
		maxYLabel.setLayoutData(gd);
		m_maxYText = new Text(settingComposite, SWT.SINGLE | SWT.BORDER | SWT.RIGHT);
		m_maxYText.setTextLimit(6);
		m_maxYText.setText(Integer.valueOf(m_maxDataY).toString());
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		m_maxYText.setLayoutData(gd);
		//
		Label minYLabel = new Label(settingComposite,SWT.NULL);
		minYLabel.setText("Ymin:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.END;
		minYLabel.setLayoutData(gd);
		m_minYText = new Text(settingComposite, SWT.SINGLE | SWT.BORDER | SWT.RIGHT);
		m_minYText.setTextLimit(6);
		m_minYText.setText(Integer.valueOf(m_minDataY).toString());
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		m_minYText.setLayoutData(gd);
		//
		Label stepYLabel = new Label(settingComposite,SWT.NULL);
		stepYLabel.setText("Ystep:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.END;
		stepYLabel.setLayoutData(gd);
		m_scaleStepYText = new Text(settingComposite, SWT.SINGLE | SWT.BORDER | SWT.RIGHT);
		m_scaleStepYText.setTextLimit(6);
		m_scaleStepYText.setText(Integer.valueOf(m_scaleStepY).toString());
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		m_scaleStepYText.setLayoutData(gd);
		//
		Label redrawPeriodLabel = new Label(settingComposite,SWT.NULL);
		redrawPeriodLabel.setText("RedrawCycle:");
		gd = new GridData();
		gd.horizontalAlignment = SWT.END;
		stepYLabel.setLayoutData(gd);
		m_redrawPeriodText = new Text(settingComposite, SWT.SINGLE | SWT.BORDER | SWT.RIGHT);
		m_redrawPeriodText.setTextLimit(6);
		m_redrawPeriodText.setText(Integer.valueOf(m_redrawPeriod).toString());
		gd = new GridData();
		gd.horizontalAlignment = SWT.FILL;
		gd.grabExcessHorizontalSpace = true;
		m_redrawPeriodText.setLayoutData(gd);

		Button applyButton = new Button(settingComposite, SWT.TOP);
		applyButton.setText("Apply");
		gd = new GridData();
		gd.horizontalSpan = 2;
		gd.horizontalAlignment = SWT.END;
		applyButton.setLayoutData(gd);
		applyButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				applyInputValue();
			}
		});
	}

	private void applyInputValue(){
		int inputMax = m_maxDataY;
		int inputMin = m_minDataY;
		int inputStep = m_scaleStepY;
		int redrawPeriod = m_redrawPeriod;
		try {
			inputMax = Integer.valueOf(m_maxYText.getText()).intValue();
		} catch (Exception ex) {
		}
		try {
			inputMin= Integer.valueOf(m_minYText.getText()).intValue();
		} catch (Exception ex) {
		}
		try {
			inputStep= Integer.valueOf(m_scaleStepYText.getText()).intValue();
		} catch (Exception ex) {
		}
		if( inputMax>inputMin && inputStep!=0 && inputStep<= (inputMax-inputMin) ) {
			m_maxDataY = inputMax;
			m_minDataY = inputMin;
			m_scaleStepY = inputStep;
		}
		//
		try {
			redrawPeriod= Integer.valueOf(m_redrawPeriodText.getText()).intValue();
			if( 0<redrawPeriod ) {
				m_redrawPeriod = redrawPeriod;
				m_redrawPeriod_Input = redrawPeriod;
				m_inputFromText = true;
			}
		} catch (Exception ex) {
		}
	}
}
