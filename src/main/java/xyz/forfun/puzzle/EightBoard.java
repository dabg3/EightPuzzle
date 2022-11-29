package xyz.forfun.puzzle;

import xyz.forfun.puzzle.restart.RestartAction;

import javax.swing.*;

import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/*
 * EightBoard manages the elements to be displayed and their layout.
 */
public class EightBoard extends JFrame implements PropertyChangeListener {

    private EightController controller;
     private RestartAction restart = new RestartAction();

    public ArrayList<EightTile> tiles = new ArrayList<>();
    public EightTile hole_tile;

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
        for (int pos = 0; pos < Options.ROWS * Options.COLUMNS; pos++) {
                EightTile tile = new EightTile(pos + 1);
                tile.addVetoableChangeListener(controller);
                tile.addPropertyChangeListener(this);
                restart.addPropertyChangeListener(tile);
                board.add(tile);
                tiles.add(tile);
        }
        return board;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {

    }

}



