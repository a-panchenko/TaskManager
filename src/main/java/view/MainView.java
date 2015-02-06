package view;

import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class MainView extends javax.swing.JFrame implements Viewable {


    private javax.swing.JButton addTaskButton;
    private javax.swing.JButton deleteTaskButton;
    private javax.swing.JButton editTaskButton;
    private com.toedter.calendar.JDateChooser endDateChooser;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton shCalendarButton;
    private com.toedter.calendar.JDateChooser startDateChooser;
    private javax.swing.JTable taskTable;

    /**
     * <strong>[Constructor]</strong>
     */
    public MainView() {
        this.openView();
    }

    /**
     * Create and sets visible view
     */
    @Override
    public void openView() {
        initComponents();
        this.setVisible(true);
    }

    /**
     * Dispose view
     */
    @Override
    public void closeView() {
        this.dispose();
    }

    /**
     * Ask user question and returns answer
     * @param question
     * @return true if user agree and false if not.
     */
    public boolean confirmDelete(String question) {
        return JOptionPane.showConfirmDialog(this, question, "Warning", JOptionPane.YES_OPTION) == JOptionPane.YES_OPTION;
    }

    /**
     * Shows dialog with message
     * @param message message to show
     */
    @Override
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(null, message, "Warning", JOptionPane.WARNING_MESSAGE);
    }

    /**
     * Build and draw all components of UI
     */
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        deleteTaskButton = new javax.swing.JButton();
        addTaskButton = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        shCalendarButton = new javax.swing.JButton();
        startDateChooser = new com.toedter.calendar.JDateChooser();
        endDateChooser = new com.toedter.calendar.JDateChooser();
        editTaskButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        taskTable = new javax.swing.JTable();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Task Manager");

        deleteTaskButton.setText("Delete Task");

        addTaskButton.setText("Add Task");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        jLabel2.setText("Calendar Panel");

        shCalendarButton.setText("Show Calendar");

        startDateChooser.setDateFormatString("HH:mm dd.MM.yyyy");

        endDateChooser.setDateFormatString("HH:mm dd.MM.yyyy");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel2)
                                .addGap(84, 84, 84))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addGap(89, 89, 89)
                                                .addComponent(shCalendarButton))
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                                .addContainerGap()
                                                .addComponent(startDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(endDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(13, 13, 13)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(startDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(endDateChooser, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(shCalendarButton)
                                .addGap(0, 12, Short.MAX_VALUE))
        );

        editTaskButton.setText("Edit Task");

        taskTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{

                },
                new String[]{
                        "Title", "Information"
                }
        ) {
            Class[] types = new Class[]{
                    java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                    false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        taskTable.getTableHeader().setReorderingAllowed(false);
        this.taskTable.getColumnModel().getColumn(0).setMaxWidth(150);
        this.taskTable.getColumnModel().getColumn(0).setMinWidth(70);
        jScrollPane1.setViewportView(taskTable);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(58, 58, 58)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(addTaskButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(deleteTaskButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(editTaskButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addContainerGap())
                        .addComponent(jScrollPane1)
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(addTaskButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(editTaskButton)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(deleteTaskButton))
                                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
        );
        setMinimumSize(new Dimension(540, 320));
        pack();
        setLocationRelativeTo(null);
    }

    /**
     * Returns addTaskButton
     * @return addTaskButton
     */
    public JButton getAddTaskButton() {
        return addTaskButton;
    }

    /**
     * Returns editTaskButton
     * @return editaskButton
     */
    public JButton getEditTaskButton() {
        return editTaskButton;
    }

    /**
     * Returns deleteTaskButton
     * @return deleteTaskButton
     */
    public JButton getDeleteTaskButton() {
        return deleteTaskButton;
    }

    /**
     * Returns shCalendarButton
     * @return shCalendarButton
     */
    public JButton getShCalendarButton() {
        return shCalendarButton;
    }

    /**
     * Returns table with tasks
     * @return taskTable
     */
    public JTable getTable() {
        return taskTable;
    }

    /**
     * Returns startDateChooser
     * @return startDateChooser
     */
    public JDateChooser getStartDateChooser() {
        return startDateChooser;
    }

    /**
     * Returns endDateChooser
     * @return endDateChooser
     */
    public JDateChooser getEndDateChooser() {
        return endDateChooser;
    }
}
