package xyz.forfun.puzzle;

import xyz.forfun.puzzle.label.LabelChangeEvent;
import xyz.forfun.puzzle.label.TileLabelChangeListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;

import static xyz.forfun.puzzle.Options.HOLE_VALUE;
import static xyz.forfun.puzzle.Options.RESTART_VALUE;

/*
 * EightBoard manages the elements to be displayed and their layout.
 */
public class EightBoard extends JFrame implements TileLabelChangeListener {

    private EightController controller;
    //private RestartAction restart = new RestartAction();

    private EightTile hole;

    public EightBoard() {
        initComponents();
        //restart.actionPerformed(null); //init board
    }

    /*
     * GUI layout
     */

    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(2, 1, 3, 3));
        this.add(initControlPanel());
        this.add(initBoard());
        this.pack();
    }

    private JPanel initControlPanel() {
        JPanel control = new JPanel(new GridLayout(1, 3, 0, 3));
        controller = new EightController();
        control.add(controller);
        //restart.addPropertyChangeListener(controller);

        JButton restart_butt = new JButton("Restart");
        //restart_butt.addActionListener(restart);
        control.add(restart_butt);

        /*
        flip_butt = new JButton("Flip");
        flip_butt.addActionListener(e -> flip(e));
        control.add(flip_butt);
        */
        return control;
    }

    private JPanel initBoard() {
        JPanel board = new JPanel(new GridLayout(Options.ROWS, Options.COLUMNS, 3, 3));
        for (int pos = 1; pos <= Options.ROWS * Options.COLUMNS; pos++) {
            EightTile tile = new EightTile(pos);
            addListeners(tile);
            board.add(tile);
        }
        return board;
    }

    /*
     * GUI actions
     */

    private void addListeners(EightTile tile) {
        tile.addVetoableChangeListener(controller);
        tile.addPropertyChangeListener("label", this);
        tile.addActionListener(this::onTileClick);
        //restart.addPropertyChangeListener(tile);
    }

    private void onTileClick(ActionEvent evt) {
        EightTile clickedTile = (EightTile) evt.getSource();
        try {
            clickedTile.setLabel(HOLE_VALUE);
        } catch (PropertyVetoException e) {
            //TODO: flash tile background
        }
    }

    @Override
    public void tileLabelChange(LabelChangeEvent evt) {
        // hole label update should be last instruction (no race conditions)
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (!evt.getPropertyName().equalsIgnoreCase("label")) {
            return;
        }
        if (!(evt.getSource() instanceof EightTile tile)) {
            throw new RuntimeException("");
        }
        int oldValue = (int) evt.getOldValue();
        int newValue = (int) evt.getNewValue();

        if (newValue == HOLE_VALUE) {
            tile.setText("");
            tile.setEnabled(false);
            updateHoleLabel(oldValue);
            hole = tile;
        } else {
           tile.setText(String.valueOf(newValue));
           tile.setEnabled(true);
        }
    }



    private void updateHoleLabel(int value) {
        if (hole == null) {
            return;
        }
        try {
            hole.setLabel(value);
        } catch (PropertyVetoException e) {
            throw new IllegalStateException("Unable to update hole label");
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new EightBoard().setVisible(true);
        });
    }

}



