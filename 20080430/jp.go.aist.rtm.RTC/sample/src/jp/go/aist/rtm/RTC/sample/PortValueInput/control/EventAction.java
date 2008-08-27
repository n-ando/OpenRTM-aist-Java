package jp.go.aist.rtm.RTC.sample.PortValueInput.control;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import jp.go.aist.rtm.RTC.sample.PortValueInput.model.PortValue;
import jp.go.aist.rtm.RTC.sample.PortValueInput.model.PortValueListener;

public class EventAction extends WindowAdapter implements PortValueListener {

    public EventAction(PortValue[] portValues) {

        // TODO 初期化を行う
        _portValues = portValues;
    }

    public void onChanged(final PortValue portValue) {

        // TODO 値の入力に応じた処理を行う
        System.out.println("-------------------------");
        System.out.println("name: " + portValue.getName());
        System.out.println("data: " + portValue.getData());
        System.out.println("enabled: " + portValue.isEnabled());
        System.out.println("-------------------------");
    }

    public void windowClosed(WindowEvent ev) {
        
        // TODO 終了時の処理を記述する
        System.out.println("*** EXIT ****");
        System.exit(0);
    }
    
    private PortValue[] _portValues;
}
