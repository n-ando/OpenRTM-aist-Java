package jp.go.aist.rtm.rtcbuilder.ui.compare;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import jp.go.aist.rtm.rtcbuilder.RtcBuilderPlugin;
import jp.go.aist.rtm.rtcbuilder.Generator.MergeHandler;

import org.eclipse.compare.CompareConfiguration;
import org.eclipse.compare.CompareViewerPane;
import org.eclipse.compare.IEncodedStreamContentAccessor;
import org.eclipse.compare.ITypedElement;
import org.eclipse.compare.contentmergeviewer.TextMergeViewer;
import org.eclipse.compare.structuremergeviewer.DiffNode;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.DialogSettings;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.presentation.IPresentationDamager;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.IPresentationRepairer;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.text.source.SourceViewer;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

/**
 * 文字列の差異を可視化し、オリジナル・ジェネレート・マージの３つの内の1つを選択するダイアログ
 */
public class CompareResultDialog extends Dialog {

	/**
	 * オリジナルボタンのラベル
	 */
	public static final String ORIGINAL_LABEL = "Original";

	/**
	 * ジェネレートボタンのラベル
	 */
	public static final String GENERATE_LABEL = "Generate";

	/**
	 * マージボタンのラベル
	 */
	public static final String MERGE_LAGEL = "Merge";

	protected Rectangle fNewBounds;

	private String mOriginal;

	private String mGenerate;

	private String mcompareName;

	private boolean canMerge;

	private TextMergeViewer fViewer;

	private static CompareResultDialog fgThis;

	private int fPrefix;

	private int fSuffix;

	private final static String DIALOG_BOUNDS_KEY = "CompareResultDialogBounds"; //$NON-NLS-1$

	private static final String X = "x"; //$NON-NLS-1$

	private static final String Y = "y"; //$NON-NLS-1$

	private static final String WIDTH = "width"; //$NON-NLS-1$

	private static final String HEIGHT = "height"; //$NON-NLS-1$

	/**
	 * コンストラクタ
	 * 
	 * @param parentShell
	 *            親のシェル
	 * @param target
	 *            比較対象の情報
	 */
	public CompareResultDialog(Shell parentShell, CompareTarget target) {
		super(parentShell);
		fgThis = this;
		setShellStyle(getShellStyle() | SWT.RESIZE | SWT.MAX);
		mcompareName = target.getTargetName();
		mOriginal = target.getOriginalSrc();
		mGenerate = target.getGenerateSrc();
		canMerge = target.canMerge();
		computePrefixSuffix();
	}

	/**
	 * {@inheritDoc}
	 */
	protected void configureShell(Shell newShell) {
		super.configureShell(newShell);
		newShell.setText("結果比較");
	}

	/**
	 * {@inheritDoc}
	 */
	protected void createButtonsForButtonBar(Composite parent) {
		createButton(parent, MergeHandler.PROCESS_ORIGINAL_ID, ORIGINAL_LABEL,
				true);
		if (canMerge) {
			createButton(parent, MergeHandler.PROCESS_MERGE_ID, MERGE_LAGEL,
					true);
		}

		createButton(parent, MergeHandler.PROCESS_GENERATE_ID, GENERATE_LABEL,
				true);
		GridLayout layout = (GridLayout) parent.getLayout();
		layout.horizontalSpacing = 100;
		parent.setLayout(layout);
		GridData data = new GridData(GridData.HORIZONTAL_ALIGN_CENTER
				| GridData.VERTICAL_ALIGN_CENTER);
		parent.setLayoutData(data);
	}

	/**
	 * {@inheritDoc}
	 */
	public Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);
		GridLayout layout = new GridLayout();
		layout.numColumns = 1;
		composite.setLayout(layout);

		CompareViewerPane pane = new CompareViewerPane(composite, SWT.BORDER
				| SWT.FLAT);
		pane.setText(mcompareName);
		GridData data = new GridData(GridData.FILL_HORIZONTAL
				| GridData.FILL_VERTICAL);
		data.widthHint = convertWidthInCharsToPixels(120);
		data.heightHint = convertHeightInCharsToPixels(13);
		pane.setLayoutData(data);

		Control previewer = createPreviewer(pane);
		pane.setContent(previewer);
		GridData gd = new GridData(GridData.FILL_BOTH);
		previewer.setLayoutData(gd);
		applyDialogFont(parent);
		return composite;
	}

	/**
	 * プレビュー部を作成する
	 * 
	 * @param parent
	 *            親のコントロール
	 * @return 作成したプレビュー部
	 */
	private Control createPreviewer(Composite parent) {
		final CompareConfiguration compareConfiguration = new CompareConfiguration();
		compareConfiguration.setLeftLabel("Original");
		compareConfiguration.setLeftEditable(false);
		compareConfiguration.setRightLabel("Generate");
		compareConfiguration.setRightEditable(false);
		compareConfiguration.setProperty(
				CompareConfiguration.IGNORE_WHITESPACE, Boolean.FALSE);

		fViewer = new CompareResultMergeViewer(parent, SWT.NONE,
				compareConfiguration);
		fViewer.setInput(new DiffNode(new CompareElement(mOriginal),
				new CompareElement(mGenerate)));

		Control control = fViewer.getControl();
		control.addDisposeListener(new DisposeListener() {
			public void widgetDisposed(DisposeEvent e) {
				if (compareConfiguration != null)
					compareConfiguration.dispose();
			}
		});
		return control;
	}

	/**
	 * {@inheritDoc}
	 */
	protected Point getInitialSize() {
		int width = 0;
		int height = 0;

		final Shell s = getShell();
		if (s != null) {
			s.addControlListener(new ControlListener() {
				public void controlMoved(ControlEvent arg) {
					fNewBounds = s.getBounds();
				}

				public void controlResized(ControlEvent arg) {
					fNewBounds = s.getBounds();
				}
			});
		}
		IDialogSettings bounds = RtcBuilderPlugin.getDefault()
				.getDialogSettings().getSection(DIALOG_BOUNDS_KEY);
		if (bounds == null) {
			return super.getInitialSize();
		} else {
			try {
				width = bounds.getInt(WIDTH);
			} catch (NumberFormatException e) {
				width = 400;
			}
			try {
				height = bounds.getInt(HEIGHT);
			} catch (NumberFormatException e) {
				height = 300;
			}
		}
		return new Point(width, height);
	}

	/**
	 * {@inheritDoc}
	 */
	protected Point getInitialLocation(Point initialSize) {
		Point loc = super.getInitialLocation(initialSize);

		IDialogSettings bounds = RtcBuilderPlugin.getDefault()
				.getDialogSettings().getSection(DIALOG_BOUNDS_KEY);
		if (bounds != null) {
			try {
				loc.x = bounds.getInt(X);
			} catch (NumberFormatException e) {
			}
			try {
				loc.y = bounds.getInt(Y);
			} catch (NumberFormatException e) {
			}
		}
		return loc;
	}

	/**
	 * 窓サイズを保存する
	 */
	private void saveBounds(Rectangle bounds) {
		IDialogSettings dialogBounds = RtcBuilderPlugin.getDefault()
				.getDialogSettings().getSection(DIALOG_BOUNDS_KEY);
		if (dialogBounds == null) {
			dialogBounds = new DialogSettings(DIALOG_BOUNDS_KEY);
			RtcBuilderPlugin.getDefault().getDialogSettings().addSection(
					dialogBounds);
		}
		dialogBounds.put(X, bounds.x);
		dialogBounds.put(Y, bounds.y);
		dialogBounds.put(WIDTH, bounds.width);
		dialogBounds.put(HEIGHT, bounds.height);
	}

	/**
	 * 内容の差異の始めと最後の位置を算出する
	 */
	private void computePrefixSuffix() {
		int end = Math.min(mOriginal.length(), mGenerate.length());
		int i = 0;
		for (; i < end; i++)
			if (mOriginal.charAt(i) != mGenerate.charAt(i))
				break;
		fPrefix = i;

		int j = mOriginal.length() - 1;
		int k = mGenerate.length() - 1;
		int l = 0;
		for (; k >= fPrefix && j >= fPrefix; k--, j--) {
			if (mOriginal.charAt(j) != mGenerate.charAt(k))
				break;
			l++;
		}
		fSuffix = l;
	}

	/**
	 * 比較結果のビューワクラス
	 */
	private static class CompareResultMergeViewer extends TextMergeViewer {
		/**
		 * コンストラクタ
		 * 
		 * @param parent
		 *            親コントロール
		 * @param style
		 *            スタイル
		 * @param configuration
		 *            コンペア設定
		 */
		private CompareResultMergeViewer(Composite parent, int style,
				CompareConfiguration configuration) {
			super(parent, style, configuration);
		}

		/**
		 * {@inheritDoc}
		 */
		protected void configureTextViewer(TextViewer textViewer) {
			if (textViewer instanceof SourceViewer) {
				((SourceViewer) textViewer)
						.configure(new CompareResultViewerConfiguration());
			}
		}
	}

	/**
	 * 比較結果のビューワの設定
	 */
	public static class CompareResultViewerConfiguration extends
			SourceViewerConfiguration {

		/**
		 * IPresentationDamagerとIPresentationRepairerの実現クラス
		 */
		public static class SimpleDamagerRepairer implements
				IPresentationDamager, IPresentationRepairer {
			private IDocument fDocument;

			/**
			 * {@inheritDoc}
			 */
			public void setDocument(IDocument document) {
				fDocument = document;
			}

			/**
			 * {@inheritDoc}
			 */
			public IRegion getDamageRegion(ITypedRegion partition,
					DocumentEvent event, boolean changed) {
				return new Region(0, fDocument.getLength());
			}

			/**
			 * {@inheritDoc}
			 */
			public void createPresentation(TextPresentation presentation,
					ITypedRegion damage) {
				int suffix = CompareResultDialog.fgThis.fSuffix;
				int prefix = CompareResultDialog.fgThis.fPrefix;
				TextAttribute attr = new TextAttribute(Display.getDefault()
						.getSystemColor(SWT.COLOR_RED), null, SWT.BOLD);
				presentation.addStyleRange(new StyleRange(prefix, fDocument
						.getLength()
						- suffix - prefix, attr.getForeground(), attr
						.getBackground(), attr.getStyle()));
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public IPresentationReconciler getPresentationReconciler(
				ISourceViewer sourceViewer) {
			PresentationReconciler reconciler = new PresentationReconciler();
			SimpleDamagerRepairer dr = new SimpleDamagerRepairer();
			reconciler.setDamager(dr, IDocument.DEFAULT_CONTENT_TYPE);
			reconciler.setRepairer(dr, IDocument.DEFAULT_CONTENT_TYPE);
			return reconciler;
		}
	}

	/**
	 * 比較範囲
	 */
	private static class CompareElement implements ITypedElement,
			IEncodedStreamContentAccessor {
		private String fContent;

		/**
		 * コンストラクタ
		 * 
		 * @param content
		 *            内容
		 */
		public CompareElement(String content) {
			fContent = content;
		}

		/**
		 * {@inheritDoc}
		 */
		public String getName() {
			return "<no name>"; //$NON-NLS-1$
		}

		/**
		 * {@inheritDoc}
		 */
		public Image getImage() {
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public String getType() {
			return "txt"; //$NON-NLS-1$
		}

		/**
		 * {@inheritDoc}
		 */
		public InputStream getContents() {
			try {
				return new ByteArrayInputStream(fContent.getBytes("UTF-8")); //$NON-NLS-1$
			} catch (UnsupportedEncodingException e) {
				return new ByteArrayInputStream(fContent.getBytes());
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public String getCharset() throws CoreException {
			return "UTF-8"; //$NON-NLS-1$
		}
	}

	/**
	 * {@inheritDoc}
	 */
	protected void buttonPressed(int buttonId) {
		setReturnCode(buttonId);
		if (fNewBounds != null)
			saveBounds(fNewBounds);
		close();
	}
	// public boolean close() {
	// boolean closed= super.close();
	// if (closed && fNewBounds != null)
	// saveBounds(fNewBounds);
	// return closed;
	// }

}
