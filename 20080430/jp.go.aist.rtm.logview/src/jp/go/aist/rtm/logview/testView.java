package jp.go.aist.rtm.logview;

import java.util.LinkedList;
import java.util.Vector;

import jp.go.aist.rtm.logview.view.LogViewPart;

public class testView implements Runnable {
	private LogViewPart view;
    private LinkedList<String> labelData = new LinkedList<String>();
    private double[][] drawData = new double [2][100];
//    private Vector<LinkedList<Double>> drawData = new Vector<LinkedList<Double>>();
    private LinkedList<Double> drawData1 = new LinkedList<Double>();
    private LinkedList<Double> drawData2 = new LinkedList<Double>();
    private Double[] drawData12 = new Double[100];
    private Double[] drawData22 = new Double[100];
	
	public void init(LogViewPart view) {
		this.view = view;
        for(int intidx=0;intidx<100;intidx++) {
        	labelData.addLast("");
        }
        double delta = Math.PI / 30;
        double theta = 0;
        for(int intidx2=0;intidx2<100;intidx2++) {
			drawData[0][intidx2] = 20*Math.sin(theta)+20;
			drawData[1][intidx2] = 20*Math.cos(theta)+20;
			theta += delta;
        }
        view.setDrawData(labelData, drawData);
	}

    public int open() {
      Thread t = new Thread(this);
      t.start();
      return 0;
  }

	public void run() {
		double[] appendData = new double[2];
		int intIdx = 0;
        while(true) {
	        double delta = Math.PI / 30;
	        double theta = delta*intIdx;
//	        for(int intidx2=0;intidx2<100;intidx2++) {
////	        	if(drawData1.size()>99) {
////	        		drawData1.removeFirst();
////	        	}
////				drawData1.add(20*Math.sin(theta)+20);
////	        	if(drawData2.size()>99) {
////	        		drawData2.removeFirst();
////	        	}
////				drawData2.add(20*Math.cos(theta)+20);
////				drawData1.set(intidx2, 20*Math.sin(theta)+20);
////				drawData2.set(intidx2, 20*Math.cos(theta)+20);
////				drawData[0] = 20*Math.sin(theta)+20;
////				drawData[1] = 20*Math.cos(theta)+20;
				appendData[0] = 20*Math.sin(theta)+20;
				appendData[1] = 20*Math.cos(theta)+20;
				theta += delta;
//	        }
////	        drawData.set(0, drawData1);
////	        drawData.set(1, drawData2);
////	        drawData1.toArray(drawData12);
////	        drawData[0] = drawData12;
////	        drawData2.toArray(drawData22);
////	        drawData[1] = drawData22;
//	        view.setDrawData(labelData, drawData);
			view.addDrawData("", appendData);
			intIdx++;
	        try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        }
	}
}
