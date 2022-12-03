package xyz.forfun.puzzle.restart;

import javax.swing.*;

public class RestartButton extends JButton {

    private RestartAction restartAction;

    public RestartButton() {
        super("Restart");
        restartAction = new RestartAction();
        this.addActionListener(restartAction);
    }

    public void addRestartListener(RestartListener listener) {
        restartAction.addPropertyChangeListener(listener);
    }

    public void removeRestartListener(RestartListener listener) {
        restartAction.removePropertyChangeListener(listener);
    }
}
