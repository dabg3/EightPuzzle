package xyz.forfun.puzzle.restart;

import xyz.forfun.puzzle.Options;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class RestartAction extends AbstractAction {

    RestartAction() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        List<Integer> labelsByPosition = new ArrayList<>(Options.ROWS * Options.COLUMNS);
        for (int i = 0; i <= Options.ROWS * Options.COLUMNS; i++) {
            labelsByPosition.add(i);
        }

        Collections.shuffle(labelsByPosition);

        putValue("initializedLabels", labelsByPosition);
    }

}
