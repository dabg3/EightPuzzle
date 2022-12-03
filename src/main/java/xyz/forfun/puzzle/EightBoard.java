package xyz.forfun.puzzle;

import xyz.forfun.puzzle.label.LabelChangeEvent;
import xyz.forfun.puzzle.label.TileLabelChangeListener;
import xyz.forfun.puzzle.restart.RestartButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyVetoException;

import static xyz.forfun.puzzle.Options.HOLE_VALUE;

/*
 * EightBoard manages the elements to be displayed and their layout.
 */
public class EightBoard extends JFrame implements TileLabelChangeListener {

    private EightController controller;
    private RestartButton restart;

    private EightTile hole;

    public EightBoard() {
        initComponents();
        restart.doClick();
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
        restart = new RestartButton();
        restart.addRestartListener(controller);
        control.add(controller);
        control.add(restart);

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
            restart.addRestartListener(tile);
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
    }

    private void onTileClick(ActionEvent evt) {
        EightTile clickedTile = (EightTile) evt.getSource();
        try {
            clickedTile.setLabel(HOLE_VALUE);
        } catch (PropertyVetoException e) {
            System.err.println("Illegal move! tile position:" + ((EightTile) evt.getSource()).getPosition());
            //TODO: flash tile background
        }
    }

    @Override
    public void tileLabelChange(LabelChangeEvent evt) throws PropertyVetoException {
        EightTile tile = evt.getSource();
        int label = (int) evt.getNewValue();
        if (evt.isRestartChange()) {
            tile.setText(String.valueOf(label));
            tile.setEnabled(label != HOLE_VALUE);
            if (label == HOLE_VALUE) {
                hole = tile;
            }
            return;
        }
        tile.setEnabled(label != HOLE_VALUE);
        tile.setText(String.valueOf(label));
        if (label == HOLE_VALUE) {
            EightTile lastHole = hole;
            hole = tile;
            lastHole.setLabel((int) evt.getOldValue());
        }
    }


    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new EightBoard().setVisible(true);
        });
    }

}



