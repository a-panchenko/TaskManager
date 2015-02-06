/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.*;

/**
 * Class draw calendar UI.
 * @author Panchenko Alexandr
 */
public class CalendarView extends javax.swing.JFrame  implements Viewable {

    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;

    /**
     * <strong>[Constructor]</strong>
     */
    public CalendarView() {
        this.openView();
    }

    /**
     * Shows dialog with message
     * @param message message to show
     */
    @Override
    public void showMessage(String message) {

    }

    /**
     * Create and open view
     */
    @Override
    public void openView() {
        this.setVisible(true);
        initComponents();
    }

    /**
     * Dispose view
     */
    @Override
    public void closeView() {
        this.dispose();
    }

    /**
     * Returns table with tasks
     * @return jTable
     */
    public JTable getjTable(){
        return jTable1;
    }


    /**
     * Build and draw all components of UI
     */
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Calendar");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Title ", "Infirmation"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        this.jTable1.getColumnModel().getColumn(0).setMaxWidth(150);
        this.jTable1.getColumnModel().getColumn(0).setMinWidth(120);
        jScrollPane3.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 519, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 303, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }


}
