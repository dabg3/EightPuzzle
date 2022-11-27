package com.ziccolella.puzzle;

import javax.swing.*;

import com.ziccolella.puzzle.restart.EightRestart;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.awt.event.ActionEvent;

/*
 * EightBoard manages the elements to be displayed and their layout.
 */
public class EightBoard extends JFrame {

    private EightController controller;
    private JButton restart_butt;
    private JButton flip_butt;

    public ArrayList<EightTile> tiles = new ArrayList<>();
    public EightTile hole_tile;

    private ArrayList<EightRestart.Listener> restart_listeners = new ArrayList<>();

    public EightBoard() {
        initComponents();
        restart();
    }

    private void initComponents() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new GridLayout(2, 1, 3, 3));
        this.add(new ControlPanel());
        this.add(new BoardGrid());
        this.pack();
    }

    //Defining the 3x3 Grid
    class BoardGrid extends JPanel {

        public BoardGrid() {

            //From docu parameters are (int rows, int cols, int hgap, int vgap)
            this.setLayout(new GridLayout(Options.ROWS, Options.COLUMNS, 3, 3));

            //Initialize tiles
            for (int row = 0; row < Options.ROWS; row++) {
                for (int col = 0; col < Options.COLUMNS; col++) {

                    //Create Eightile
                    EightTile tile = new EightTile((row * Options.COLUMNS) + col);

                    //Add click behaviour
                    tile.addActionListener(e -> click(e)); //LAMBDA <3

                    //The controller is registered as VetoableChangeListener to all tiles(So tile can ask if a move is valid)
                    tile.addVetoableChangeListener(controller);

                    //All tiles and the controller are registered as listeners to the Restart event
                    //(so Board has to mantain a reference of each tile's listeners in order to callback)
                    EightBoard.this.addEightRestartListener(tile);

                    //BoardGrid

                    //Save reference
                    restart_listeners.add(tile);
                    tiles.add(tile);

                    //Add to the GUI
                    add(tile);
                }
            }

        }

        public void click(ActionEvent e) {
            EightTile clicked_tile = (EightTile) e.getSource();
            hole_tile.setText(clicked_tile.getText());

            hole_tile.setEnabled(true);
            clicked_tile.setEnabled(false);

            hole_tile = clicked_tile;
            controller.setText("OK");
        }
    }

    class ControlPanel extends JPanel {
        public ControlPanel() {
            this.setLayout(new GridLayout(1, 3, 0, 3));

            controller = new EightController();
            restart_listeners.add(controller);
            this.add(controller);

            restart_butt = new JButton("Restart");
            restart_butt.addActionListener(e -> restart());
            this.add(restart_butt);

            flip_butt = new JButton("Flip");
            flip_butt.addActionListener(e -> flip(e));
            this.add(flip_butt);
        }

        public void flip(ActionEvent e) {

        }
    }


    public void restart() {
        //Initialize the array of labels
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
        ArrayList<Integer> lab = new ArrayList<>(Arrays.asList(labels));

        hole_tile = tiles.get(lab.indexOf(9));

        restart_listeners.forEach((EightRestartListener) -> EightRestartListener.restart(new EightRestart.Event(this, lab)));
    }

    //Restart Event implementation

    public synchronized void addEightRestartListener(EightRestart.Listener l) {
        restart_listeners.add(l);
    }

    public synchronized void removeEightRestartListener(EightRestart.Listener l) {
        restart_listeners.remove(l);
    }


}



