package xyz.forfun.puzzle;

import xyz.forfun.puzzle.restart.RestartAction;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;

/*
 * EightBoard manages the elements to be displayed and their layout.
 */
public class EightBoard extends JFrame implements PropertyChangeListener {

    private EightController controller;
     private RestartAction restart = new RestartAction();

    public EightBoard() {
        initComponents();
        restart.actionPerformed(null); //init board
    }

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
        restart.addPropertyChangeListener(controller);

        JButton restart_butt = new JButton("Restart");
        restart_butt.addActionListener(restart);
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
                tile.addVetoableChangeListener(controller);
                tile.addPropertyChangeListener(this);
                tile.addActionListener(this::onTileClick);
                restart.addPropertyChangeListener(tile);
                board.add(tile);
        }
        return board;
    }

    private void onTileClick(ActionEvent evt) {
        EightTile clickedTile = (EightTile) evt.getSource();
        try {
            clickedTile.setLabel(Options.HOLE_VALUE);
        } catch (PropertyVetoException e) {
            //flash the tile red
        }
    }

    @Override
    public synchronized void propertyChange(PropertyChangeEvent evt) {
        if (!evt.getPropertyName().equalsIgnoreCase("label")) {
            return;
        }
        if (!(evt.getSource() instanceof EightTile tile)) {
            throw new RuntimeException("");
        }
        int oldValue = (int) evt.getOldValue();
        int newValue = (int) evt.getNewValue();
        if (oldValue == Options.RESTART_VALUE) {
           tile.setText(String.valueOf(newValue));
           return;
        }
        if (oldValue == Options.HOLE_VALUE) {
            tile.setText(String.valueOf(newValue));
        } else {
            tile.setText("");
        }
    }

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            new EightBoard().setVisible(true);
        });
    }

}



