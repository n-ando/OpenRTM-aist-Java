package jp.go.aist.rtm.rtcbuilder.ui.editors;

import jp.go.aist.rtm.rtcbuilder.IRtcBuilderConstants;
import jp.go.aist.rtm.rtcbuilder.ui.editors.xmlEditor.ColorManager;
import jp.go.aist.rtm.rtcbuilder.ui.editors.xmlEditor.XMLConfiguration;
import jp.go.aist.rtm.rtcbuilder.ui.editors.xmlEditor.XMLPartitionScanner;

import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.ITextOperationTarget;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.TextViewerUndoManager;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.source.CompositeRuler;
import org.eclipse.jface.text.source.LineNumberRulerColumn;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;

public class RtcXmlEditorFormPage extends AbstractEditorFormPage {

	private SourceViewer RTCXmlViewer;
	private Document rtcDocument;
	private boolean canDirty = true;
	//
	private ColorManager colorManager;
	//
	private final int KEYCODE_A = 97;
	private final int KEYCODE_Y = 97;
	private final int KEYCODE_Z = 97;

	/**
	 * コンストラクタ
	 * 
	 * @param editor
	 *            親のエディタ
	 */
	public RtcXmlEditorFormPage(RtcBuilderEditor editor) {
		super(editor, "id", IRtcBuilderConstants.DEFAULT_RTC_XML);
		rtcDocument = new Document();
	}

	/**
	 * {@inheritDoc}
	 */
	protected void createFormContent(IManagedForm managedForm) {
		GridLayout gl;
		gl = new GridLayout();
		gl.numColumns = 1;

		managedForm.getForm().getBody().setLayout(gl);

		ScrolledForm form = managedForm.getToolkit().createScrolledForm(
				managedForm.getForm().getBody());
		gl = new GridLayout(1, false);
		form.setLayout(gl);
		GridData gd = new GridData(GridData.FILL_BOTH);
		form.setLayoutData(gd);

		form.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		managedForm.getToolkit().paintBordersFor(form.getBody());
		form.getBody().setLayout(gl);

		Composite composite = managedForm.getToolkit().createComposite(form.getBody(), SWT.NULL);
		composite.setData(FormToolkit.KEY_DRAW_BORDER, FormToolkit.TEXT_BORDER);
		gd = new GridData(GridData.FILL_BOTH);
		composite.setLayoutData(gd);
		composite.setLayout(new FillLayout(SWT.VERTICAL));

		CompositeRuler ruler = new CompositeRuler();
		LineNumberRulerColumn lineCol = new LineNumberRulerColumn();
		lineCol.setBackground(form.getDisplay().getSystemColor(SWT.COLOR_WHITE));
		lineCol.setForeground(form.getDisplay().getSystemColor(SWT.COLOR_BLACK));
		ruler.addDecorator(0, lineCol);
		
		RTCXmlViewer = new SourceViewer(composite, ruler , SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		RTCXmlViewer.addTextListener(new SourceTextListener());
		RTCXmlViewer.getTextWidget().addKeyListener(new KeyListener() {
			public void keyPressed(KeyEvent e) {
				if( (e.stateMask & SWT.CTRL)!=0 && e.keyCode == KEYCODE_A ) {
					RTCXmlViewer.doOperation(ITextOperationTarget.SELECT_ALL);
				} else if( (e.stateMask & SWT.CTRL)!=0 && e.keyCode == KEYCODE_Y ) {
					RTCXmlViewer.doOperation(ITextOperationTarget.REDO);
				} else if( (e.stateMask & SWT.CTRL)!=0 && e.keyCode == KEYCODE_Z ) {
					RTCXmlViewer.doOperation(ITextOperationTarget.UNDO);
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		});
		final TextViewerUndoManager undoMgr = new TextViewerUndoManager(99);
		RTCXmlViewer.setUndoManager(undoMgr);
		undoMgr.connect(RTCXmlViewer);
		RTCXmlViewer.getTextWidget().addVerifyListener(new VerifyListener() {
			public void verifyText(VerifyEvent e) {
				undoMgr.endCompoundChange();
			}
		});
		
		IDocumentPartitioner partitioner = new FastPartitioner(
						new XMLPartitionScanner(),
		    	        new String[] { 
							XMLPartitionScanner.XML_TAG,
							XMLPartitionScanner.XML_COMMENT,
							XMLPartitionScanner.XML_DOCTAG
							});
	    rtcDocument.setDocumentPartitioner(partitioner);
	    partitioner.connect(rtcDocument);
		colorManager = new ColorManager();
	    RTCXmlViewer.configure(new XMLConfiguration(colorManager));

		load();
		RTCXmlViewer.setDocument(rtcDocument);

		//
	}

	public void update() {
		editor.getRtcParam().setRtcXml(RTCXmlViewer.getDocument().get());
		if( canDirty ) {
			editor.updateDirty();
		} else {
			canDirty = true;
		}
	}

	/**
	 * データをロードする
	 */
	public void load() {
		if( RTCXmlViewer != null ) {
			rtcDocument.set(editor.getRtcParam().getRtcXml());
			canDirty = false;
		}
	}
	
	public String validateParam() {
		return null;
	}

	private class SourceTextListener implements ITextListener {
		public void textChanged(TextEvent event) {
			update();
		}
	}
}
