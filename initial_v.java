package com.ziccolella.puzzle;

import java.awt.Color;
import javax.swing.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

public class EightBoard extends javax.swing.JFrame {

    private int blankRow = 3;
    private int blankCol = 3;
    private DefaultTableModel model = new DefaultTableModel(4, 4);
    private Random rand = new Random();

    public EightBoard() {
        initComponents();//call this method?
        reset();//reset the puzzle to starting form
        jTable2.setShowGrid(true);//make the grid
        jTable2.setGridColor(Color.BLACK);//set the grid color to black
    }

    public void reset() {
        blankRow = 3;//set the blank space to 3,3 on the table
        blankCol = 3;
        int n = 0;//make a new counter
        String[] nums = new String[16];//make a new string array

        for (int i = 0; i < 15; i++) {//make the string array equal to the corresponding numbers to be used in the table
            String num = Integer.toString((i + 1));//make the int a string
            nums[i] = num;//set the position equal to that number
        }

        nums[15] = " ";//make the last spot blank

        for (int i = 0; i < 4; i++) {//move through the table
            for (int j = 0; j < 4; j++) {
                jTable2.setValueAt(nums[n], i, j);//set the positions of the grid equal to the array of strings
                n++;//add to count so we keep moving through the array of strings
            }
        }
        jTable2.setBackground(Color.WHITE);

    }

    private void checkForSolved() {
        //create necessary variables
        int count = 1;
        boolean check = true;
        for (int i = 0; i < model.getRowCount(); i++) {//move through the table row by row
            for (int x = 0; x < model.getColumnCount(); x++) {//then column by column

                if (i == 3 && x == 3) {//if the space where the blank tile should be

                    if (model.getValueAt(3, 3) != " ") {//and its not the blank tile
                        check = false;//then the puzzle is not solved
                        return;
                    }
                } else {
                    String test = Integer.toString(count);//make a string variable that is the number of tiles the for loops have gone through
                    if (test != model.getValueAt(i, x)) {//if that number does not equal the number on the tile
                        check = false;//false
                        return;
                    }
                }
                count++;//count up the number of tiles the loops have gone through
            }
        }

        if (check == true) {//if the check was true then the puzzle has been solved
            jTable2.setBackground(Color.yellow);//change the color of the puzzle to yellow
        }

    }

    private boolean shift(int row, int col) {//shift method
        boolean check = true;//make check boolean
        String temp;//make a temp string to hold the tile value while shifting the tile
        //start at the first tile 0,0 and move through each adjacent tile check to see if that is the tile given by the program
        if (row == 0 && col == 0) {
            if (jTable2.getValueAt(1, 0) == " ") {//check if the adjacent tile is the blank tile
                temp = (String) jTable2.getValueAt(0, 0);//make the temp string equal to the string in the tile
                jTable2.setValueAt(" ", 0, 0);//set the old tile equal to blank
                jTable2.setValueAt(temp, 1, 0);//set the new tile equal to the the old tile
            }
            if (jTable2.getValueAt(0, 1) == " ") {//now check the other possible adjacent tile
                temp = (String) jTable2.getValueAt(0, 0);
                jTable2.setValueAt(" ", 0, 0);
                jTable2.setValueAt(temp, 0, 1);
            }
        }
        //do the same for the next tile
        if (row == 1 && col == 0) {
            if (jTable2.getValueAt(0, 0) == " ") {
                temp = (String) jTable2.getValueAt(1, 0);
                jTable2.setValueAt(" ", 1, 0);
                jTable2.setValueAt(temp, 0, 0);
            }
            if (jTable2.getValueAt(2, 0) == " ") {
                temp = (String) jTable2.getValueAt(1, 0);
                jTable2.setValueAt(" ", 1, 0);
                jTable2.setValueAt(temp, 2, 0);
            }//except do it for three adjacent ties because now there are three
            if (jTable2.getValueAt(1, 1) == " ") {
                temp = (String) jTable2.getValueAt(1, 0);
                jTable2.setValueAt(" ", 1, 0);
                jTable2.setValueAt(temp, 1, 1);
            }
        }

        if (row == 2 && col == 0) {
            if (jTable2.getValueAt(1, 0) == " ") {
                temp = (String) jTable2.getValueAt(2, 0);
                jTable2.setValueAt(" ", 2, 0);
                jTable2.setValueAt(temp, 1, 0);
            }
            if (jTable2.getValueAt(3, 0) == " ") {
                temp = (String) jTable2.getValueAt(2, 0);
                jTable2.setValueAt(" ", 2, 0);
                jTable2.setValueAt(temp, 3, 0);
            }
            if (jTable2.getValueAt(2, 1) == " ") {
                temp = (String) jTable2.getValueAt(2, 0);
                jTable2.setValueAt(" ", 2, 0);
                jTable2.setValueAt(temp, 2, 1);
            }
        }

        if (row == 3 && col == 0) {
            if (jTable2.getValueAt(2, 0) == " ") {
                temp = (String) jTable2.getValueAt(3, 0);
                jTable2.setValueAt(" ", 3, 0);
                jTable2.setValueAt(temp, 2, 0);
            }
            if (jTable2.getValueAt(3, 1) == " ") {
                temp = (String) jTable2.getValueAt(3, 0);
                jTable2.setValueAt(" ", 3, 0);
                jTable2.setValueAt(temp, 3, 1);
            }
        }

        if (row == 0 && col == 3) {
            if (jTable2.getValueAt(1, 3) == " ") {
                temp = (String) jTable2.getValueAt(0, 3);
                jTable2.setValueAt(" ", 0, 3);
                jTable2.setValueAt(temp, 1, 3);
            }
            if (jTable2.getValueAt(0, 2) == " ") {
                temp = (String) jTable2.getValueAt(0, 3);
                jTable2.setValueAt(" ", 0, 3);
                jTable2.setValueAt(temp, 0, 2);
            }
        }

        if (row == 1 && col == 3) {
            if (jTable2.getValueAt(0, 3) == " ") {
                temp = (String) jTable2.getValueAt(1, 3);
                jTable2.setValueAt(" ", 1, 3);
                jTable2.setValueAt(temp, 0, 3);
            }
            if (jTable2.getValueAt(2, 3) == " ") {
                temp = (String) jTable2.getValueAt(1, 3);
                jTable2.setValueAt(" ", 1, 3);
                jTable2.setValueAt(temp, 2, 3);
            }
            if (jTable2.getValueAt(1, 2) == " ") {
                temp = (String) jTable2.getValueAt(1, 3);
                jTable2.setValueAt(" ", 1, 3);
                jTable2.setValueAt(temp, 1, 2);
            }
        }

        if (row == 2 && col == 3) {
            if (jTable2.getValueAt(1, 3) == " ") {
                temp = (String) jTable2.getValueAt(2, 3);
                jTable2.setValueAt(" ", 2, 3);
                jTable2.setValueAt(temp, 1, 3);
            }
            if (jTable2.getValueAt(3, 3) == " ") {
                temp = (String) jTable2.getValueAt(2, 3);
                jTable2.setValueAt(" ", 2, 3);
                jTable2.setValueAt(temp, 3, 3);
            }
            if (jTable2.getValueAt(2, 2) == " ") {
                temp = (String) jTable2.getValueAt(2, 3);
                jTable2.setValueAt(" ", 2, 3);
                jTable2.setValueAt(temp, 2, 2);
            }
        }

        if (row == 3 && col == 3) {
            if (jTable2.getValueAt(2, 3) == " ") {
                temp = (String) jTable2.getValueAt(3, 3);
                jTable2.setValueAt(" ", 3, 3);
                jTable2.setValueAt(temp, 2, 3);
            }
            if (jTable2.getValueAt(3, 2) == " ") {
                temp = (String) jTable2.getValueAt(3, 3);
                jTable2.setValueAt(" ", 3, 3);
                jTable2.setValueAt(temp, 3, 2);
            }
        }

        if (row == 0 && col == 2) {
            if (jTable2.getValueAt(1, 2) == " ") {
                temp = (String) jTable2.getValueAt(0, 2);
                jTable2.setValueAt(" ", 0, 2);
                jTable2.setValueAt(temp, 1, 2);
            }
            if (jTable2.getValueAt(0, 1) == " ") {
                temp = (String) jTable2.getValueAt(0, 2);
                jTable2.setValueAt(" ", 0, 2);
                jTable2.setValueAt(temp, 0, 1);
            }
            if (jTable2.getValueAt(0, 3) == " ") {
                temp = (String) jTable2.getValueAt(0, 2);
                jTable2.setValueAt(" ", 0, 2);
                jTable2.setValueAt(temp, 0, 3);
            }
        }

        if (row == 1 && col == 2) {
            if (jTable2.getValueAt(0, 2) == " ") {
                temp = (String) jTable2.getValueAt(1, 2);
                jTable2.setValueAt(" ", 1, 2);
                jTable2.setValueAt(temp, 0, 2);
            }
            if (jTable2.getValueAt(2, 2) == " ") {
                temp = (String) jTable2.getValueAt(1, 2);
                jTable2.setValueAt(" ", 1, 2);
                jTable2.setValueAt(temp, 2, 2);
            }
            if (jTable2.getValueAt(1, 1) == " ") {
                temp = (String) jTable2.getValueAt(1, 2);
                jTable2.setValueAt(" ", 1, 2);
                jTable2.setValueAt(temp, 1, 1);
            }
            if (jTable2.getValueAt(1, 3) == " ") {
                temp = (String) jTable2.getValueAt(1, 2);
                jTable2.setValueAt(" ", 1, 2);
                jTable2.setValueAt(temp, 1, 3);
            }
        }

        if (row == 2 && col == 2) {
            if (jTable2.getValueAt(1, 2) == " ") {
                temp = (String) jTable2.getValueAt(2, 2);
                jTable2.setValueAt(" ", 2, 2);
                jTable2.setValueAt(temp, 1, 2);
            }
            if (jTable2.getValueAt(3, 2) == " ") {
                temp = (String) jTable2.getValueAt(2, 2);
                jTable2.setValueAt(" ", 2, 2);
                jTable2.setValueAt(temp, 3, 2);
            }
            if (jTable2.getValueAt(2, 1) == " ") {
                temp = (String) jTable2.getValueAt(2, 2);
                jTable2.setValueAt(" ", 2, 2);
                jTable2.setValueAt(temp, 2, 1);
            }
            if (jTable2.getValueAt(2, 3) == " ") {
                temp = (String) jTable2.getValueAt(2, 2);
                jTable2.setValueAt(" ", 2, 2);
                jTable2.setValueAt(temp, 2, 3);
            }
        }

        if (row == 3 && col == 2) {
            if (jTable2.getValueAt(2, 2) == " ") {
                temp = (String) jTable2.getValueAt(3, 2);
                jTable2.setValueAt(" ", 3, 2);
                jTable2.setValueAt(temp, 2, 2);
            }
            if (jTable2.getValueAt(3, 1) == " ") {
                temp = (String) jTable2.getValueAt(3, 2);
                jTable2.setValueAt(" ", 3, 2);
                jTable2.setValueAt(temp, 3, 1);
            }
            if (jTable2.getValueAt(3, 3) == " ") {
                temp = (String) jTable2.getValueAt(3, 2);
                jTable2.setValueAt(" ", 3, 2);
                jTable2.setValueAt(temp, 3, 3);
            }
        }

        if (row == 0 && col == 1) {
            if (jTable2.getValueAt(1, 1) == " ") {
                temp = (String) jTable2.getValueAt(0, 1);
                jTable2.setValueAt(" ", 0, 1);
                jTable2.setValueAt(temp, 1, 1);
            }
            if (jTable2.getValueAt(0, 0) == " ") {
                temp = (String) jTable2.getValueAt(0, 1);
                jTable2.setValueAt(" ", 0, 1);
                jTable2.setValueAt(temp, 0, 0);
            }
            if (jTable2.getValueAt(0, 2) == " ") {
                temp = (String) jTable2.getValueAt(0, 1);
                jTable2.setValueAt(" ", 0, 1);
                jTable2.setValueAt(temp, 0, 2);
            }
        }

        if (row == 1 && col == 1) {
            if (jTable2.getValueAt(0, 1) == " ") {
                temp = (String) jTable2.getValueAt(1, 1);
                jTable2.setValueAt(" ", 1, 1);
                jTable2.setValueAt(temp, 0, 1);
            }
            if (jTable2.getValueAt(2, 1) == " ") {
                temp = (String) jTable2.getValueAt(1, 1);
                jTable2.setValueAt(" ", 1, 1);
                jTable2.setValueAt(temp, 2, 1);
            }
            if (jTable2.getValueAt(1, 0) == " ") {
                temp = (String) jTable2.getValueAt(1, 1);
                jTable2.setValueAt(" ", 1, 1);
                jTable2.setValueAt(temp, 1, 0);
            }
            if (jTable2.getValueAt(1, 2) == " ") {
                temp = (String) jTable2.getValueAt(1, 1);
                jTable2.setValueAt(" ", 1, 1);
                jTable2.setValueAt(temp, 1, 2);
            }
        }

        if (row == 2 && col == 1) {
            if (jTable2.getValueAt(1, 1) == " ") {
                temp = (String) jTable2.getValueAt(2, 1);
                jTable2.setValueAt(" ", 2, 1);
                jTable2.setValueAt(temp, 1, 1);
            }
            if (jTable2.getValueAt(3, 1) == " ") {
                temp = (String) jTable2.getValueAt(2, 1);
                jTable2.setValueAt(" ", 2, 1);
                jTable2.setValueAt(temp, 3, 1);
            }
            if (jTable2.getValueAt(2, 0) == " ") {
                temp = (String) jTable2.getValueAt(2, 1);
                jTable2.setValueAt(" ", 2, 1);
                jTable2.setValueAt(temp, 2, 0);
            }
            if (jTable2.getValueAt(2, 2) == " ") {
                temp = (String) jTable2.getValueAt(2, 1);
                jTable2.setValueAt(" ", 2, 1);
                jTable2.setValueAt(temp, 2, 2);
            }
        }

        if (row == 3 && col == 1) {
            if (jTable2.getValueAt(2, 1) == " ") {
                temp = (String) jTable2.getValueAt(3, 1);
                jTable2.setValueAt(" ", 3, 1);
                jTable2.setValueAt(temp, 2, 1);
            }
            if (jTable2.getValueAt(3, 0) == " ") {
                temp = (String) jTable2.getValueAt(3, 1);
                jTable2.setValueAt(" ", 3, 1);
                jTable2.setValueAt(temp, 3, 0);
            }
            if (jTable2.getValueAt(3, 2) == " ") {
                temp = (String) jTable2.getValueAt(3, 1);
                jTable2.setValueAt(" ", 3, 1);
                jTable2.setValueAt(temp, 3, 2);
            }
        }

        return check;//return the check variable
    }

    private void scramble() {//scramble
        Random dong = new Random();//create new random

        for (int i = 0; i < 100; i++) {//thi is how many times the tile will randomly be shifted
            int randomNum1 = dong.nextInt((3 - 0) + 1) + 0;//create 2 new randoms
            int randomNum2 = dong.nextInt((3 - 0) + 1) + 0;
            shift(randomNum1, randomNum2);//these randoms will become the new shift spots
        }
        jTable2.setShowGrid(true);//show the new grid

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        ScrambleBtn = new javax.swing.JButton();
        resetBtn = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);

        ScrambleBtn.setText("Scramble");
        ScrambleBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ScrambleBtnActionPerformed(evt);
            }
        });

        resetBtn.setText("Reset");
        resetBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resetBtnActionPerformed(evt);
            }
        });

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable2.setRowHeight(50);
        jTable2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                jTable2MousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(jTable2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(13, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ScrambleBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resetBtn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addComponent(ScrambleBtn)
                .addGap(18, 18, 18)
                .addComponent(resetBtn)
                .addContainerGap(118, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void resetBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resetBtnActionPerformed
        // TODO add your handling code here:
        reset();
    }//GEN-LAST:event_resetBtnActionPerformed

    private void ScrambleBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ScrambleBtnActionPerformed
        scramble();
    }//GEN-LAST:event_ScrambleBtnActionPerformed

    private void jTable2MousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable2MousePressed
        // TODO add your handling code here:
        JTable target = (JTable) evt.getSource();
        int row = target.getSelectedRow();
        int col = target.getSelectedColumn();
        shift(row, col);
        checkForSolved();
    }//GEN-LAST:event_jTable2MousePressed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EightBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EightBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EightBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EightBoard.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new EightBoard().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton ScrambleBtn;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable2;
    private javax.swing.JButton resetBtn;
    // End of variables declaration//GEN-END:variables
}
