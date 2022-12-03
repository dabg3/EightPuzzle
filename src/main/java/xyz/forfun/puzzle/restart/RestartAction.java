package xyz.forfun.puzzle.restart;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

class RestartAction extends AbstractAction {

    RestartAction() {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Integer[] labels = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        int temp;

        //SHUFFLE
        for (int i = 0; i < 1000; i++) {
            int a = ThreadLocalRandom.current().nextInt(0, 9);
            int b = ThreadLocalRandom.current().nextInt(0, 9);
            temp = labels[a];
            labels[a] = labels[b];
            labels[b] = temp;
        }
        List<Integer> labelsByPosition = Arrays.asList(labels);
        putValue("initializedLabels", labelsByPosition);
    }

}
