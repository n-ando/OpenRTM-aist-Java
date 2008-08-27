package jp.go.aist.rtm.logview.view;

import java.util.LinkedList;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class Graph {
	
	private int width = 900;
	private int height = 600;   // Windowの大きさ
	private int maxData = 40;
	private int minData = 0;
	private int stepScale = 40;   // データの最大値，最小値，目盛幅
	
	private int FontSmallSize = 8;   // 大，小のフォントサイズ
	private Font fontSmall;
	private int leftMatgin = 30;		//左側余白
	private int rightMargin = 10;		//右側余白
	private int topMargin = 10;		//上側余白
	private int bottomMargin = 20;   	//下側余白
	private int fontMargin = 10;   	//文字用余白
	private int col;   	 // 横軸のデータ数
	private int row;   	 // 縦軸のデータ数


	
	private Composite composite;

	public Graph(Composite comp) {
		this.composite = comp;
		// フォントの設定
		Display display = composite.getDisplay();
		fontSmall  = new Font( display, "TimesRoman", FontSmallSize, SWT.BOLD );
	}
	
	public Image drawGraph(LinkedList<String> labelData, double[][] drawData, int maxValue, int minValue, int stepValue) {
		Display display = composite.getDisplay();
        this.width = composite.getSize().x;
        this.height = composite.getSize().y;
        //
		Image image = new Image(display, this.width, this.height);
        GC gc = new GC(image);
        gc.setBackground(display.getSystemColor(SWT.COLOR_GRAY));
        gc.fillRectangle(0,0,this.width,this.height);
        //
		// ｘ軸とｙ軸
		int xRight = leftMatgin + fontMargin;
		int xLeft = width - rightMargin;
		int yTop = height - bottomMargin;
		int yBottom = topMargin;
		gc.drawLine(xRight, yTop-1, xLeft, yTop-1);
		gc.drawLine(xRight, yTop, xLeft, yTop);
		gc.drawLine(xRight, yTop+1, xLeft, yTop+1);
		gc.drawLine(xRight-1, yTop, xRight-1, yBottom);
		gc.drawLine(xRight, yTop, xRight, yBottom);
		gc.drawLine(xRight+1, yTop, xRight+1, yBottom);
        if(labelData==null || drawData==null || labelData.size()<2){
            gc.dispose();
    		return image;
        }
    	col   = labelData.size();
    	row   = drawData.length;
		maxData = maxValue;
		minData = minValue;
		stepScale = stepValue;
		// ｘ軸の目盛と目盛り線
		gc.setFont(fontSmall);
		int stepX = (xLeft - xRight) / (col-1);
		int labelXposX = xRight + stepX;
		int labelXposY = height - 2 * bottomMargin / 3;
		for(int intIdx = 0; intIdx < col; intIdx++) {
			if(labelData!=null) {
				gc.drawString(labelData.get(intIdx), labelXposX, labelXposY);
			}
			labelXposX += stepX;
		}
		// ｙ軸の目盛と目盛り線
		int stepY = (maxData - minData) / stepScale;
		stepY = (yTop - yBottom) / stepY;
		int labelY = minData;
		int labelYposY = yTop;
		gc.drawString(Integer.toString(labelY), leftMatgin/2, labelYposY);
		while (labelY < maxData) {
			labelY += stepScale;
			labelYposY -= stepY;
			gc.drawLine(xRight, labelYposY, xLeft, labelYposY);
			gc.drawString(Integer.toString(labelY), leftMatgin/2, labelYposY);
		}

		Color cl[] = new Color [9];
		cl[0] = display.getSystemColor(SWT.COLOR_MAGENTA);
		cl[1] = display.getSystemColor(SWT.COLOR_BLUE);
		cl[2] = display.getSystemColor(SWT.COLOR_BLACK);
		cl[3] = display.getSystemColor(SWT.COLOR_CYAN);
		cl[4] = display.getSystemColor(SWT.COLOR_DARK_BLUE);
		cl[5] = display.getSystemColor(SWT.COLOR_GREEN);
		cl[6] = display.getSystemColor(SWT.COLOR_YELLOW);
		cl[7] = display.getSystemColor(SWT.COLOR_DARK_GRAY);
		cl[8] = display.getSystemColor(SWT.COLOR_RED);

		for(int intIdx1 = 0; intIdx1 < row; intIdx1++) {
			int posx = xRight;
			int drawColor  = intIdx1 % 9;
			gc.setForeground(cl[drawColor]);
			for(int intIdx2 = 0; intIdx2 < col; intIdx2++) {
				int posy = (int)(yTop - (yTop - yBottom) *
                     (drawData[intIdx1][intIdx2] - minData) / (maxData - minData));
				if( yTop<posy ) posy = yTop;
				if( posy<yBottom ) posy = yBottom;
				if (intIdx2 > 0)
					gc.drawLine(labelXposX, labelXposY, posx, posy);
				labelXposX = posx;
				labelXposY = posy;
				posx += stepX;
			}
		}
        gc.dispose();
		
		return image;
	}

}
