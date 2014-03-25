package RTMExamples.Composite;

import jp.go.aist.rtm.RTC.Manager;

public class Composite {

    public static void main(String[] args) {
        final Manager manager = Manager.init(args);

	manager.activateManager();
	
	manager.runManager();

    }
}
