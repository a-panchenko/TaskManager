package controller;

import model.Model;
import model.Task;
import model.XMLParser;
import org.apache.log4j.Logger;
import view.AddTaskView;
import view.CalendarView;
import view.MainView;

import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Observable;
import java.util.Observer;

/**
 * Main class. Creates main View and main Controller.
 */
public class MainController implements Observer, Controller {

    private Model model;
    private MainView mainView;
    private DefaultTableModel tblModel;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
    private static final Logger LOGGER = Logger.getLogger(MainController.class);


    public static void main(String[] args) {
        MainView mainView = new MainView();
        Model model = new Model();
        MainController mainController = new MainController(model, mainView);
        mainController.control();
    }

    /**
     * <strong>[Constructor]</strong>
     *
     * @param model    captures the behavior of the application
     * @param mainView represent information for user
     */
    public MainController(Model model, MainView mainView) {
        this.model = model;
        this.mainView = mainView;
        model.addObserver(this);
        tblModel = (DefaultTableModel) mainView.getTable().getModel();
        for (Task task : model.getTaskList()) {
            StringBuilder infoBuilder = new StringBuilder();
            if (task.isRepeated()) {
                infoBuilder.append(" from " + dateFormat.format(task.getStartTime()) +
                        " to " + dateFormat.format(task.getEndTime()) + " every " +
                        Model.formatSeconds(task.getRepeatInterval()));
            } else {
                infoBuilder.append(" at " + dateFormat.format(task.getStartTime()));
            }
            if (!task.isActive()) {
                infoBuilder.append(" inactive ");
            }
            tblModel.addRow(new Object[]{task.getTitle(), infoBuilder.toString()});
        }
        NotificationController notification = new NotificationController(mainView, model);
        Thread notificationThread = new Thread(notification);
        notificationThread.setDaemon(true);
        notificationThread.start();

    }

    /**
     * Listen to users's input.
     */
    @Override
    public void control() {
        ActionListener addButtonActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                AddTaskView addTaskView = new AddTaskView("Add Task");
                AddController addController = new AddController(model, addTaskView);
                addController.control();
            }
        };

        ActionListener shCalendarActionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (mainView.getStartDateChooser().getDate() == null || mainView.getEndDateChooser().getDate() == null ||
                        mainView.getEndDateChooser().getDate().before(mainView.getStartDateChooser().getDate())) {
                    mainView.showMessage("Please, enter correct dates.");
                    LOGGER.warn("Illegal arguments in dateChooser");
                } else {
                    CalendarView calendarView = new CalendarView();
                    CalendarController calendarController = new CalendarController(model, calendarView);
                    calendarController.setDates(mainView.getStartDateChooser().getDate(),
                            mainView.getEndDateChooser().getDate());
                    calendarController.control();
                }
            }
        };

        WindowListener windowListener = new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                XMLParser.writeXML(model.getTaskList());
            }
        };

        ActionListener editButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int taskID = mainView.getTable().getSelectedRow();
                if (taskID >= 0) {
                    AddTaskView editView = new AddTaskView("Edit Task");
                    EditController editController = new EditController(model, editView, taskID);
                    editView.getjButton1().setText("Save edit");
                    editController.control();
                } else {
                    mainView.showMessage("Please, select task for editing.");
                    LOGGER.warn("There is no selected item");
                }
            }
        };

        ActionListener deleteButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int taskID = mainView.getTable().getSelectedRow();
                if (taskID >= 0) {
                    DeleteController deleteController = new DeleteController(mainView, model);
                    deleteController.control();
                } else {
                    mainView.showMessage("Please, select task for deleting.");
                    LOGGER.warn("There is no selected item");
                }

            }
        };


        mainView.getAddTaskButton().addActionListener(addButtonActionListener);
        mainView.getShCalendarButton().addActionListener(shCalendarActionListener);
        mainView.getEditTaskButton().addActionListener(editButtonListener);
        mainView.addWindowListener(windowListener);
        mainView.getDeleteTaskButton().addActionListener(deleteButtonListener);

    }

    /**
     * Redraw table if list with tasks was updated
     *
     * @param o
     * @param arg
     */
    @Override
    public void update(Observable o, Object arg) {
        tblModel.setRowCount(0);
        for (Task task : model.getTaskList()) {
            StringBuilder infoBuilder = new StringBuilder();
            if (task.isRepeated()) {
                infoBuilder.append(" from " + dateFormat.format(task.getStartTime()) +
                        " to " + dateFormat.format(task.getEndTime()) + " every " +
                        Model.formatSeconds(task.getRepeatInterval()));
            } else {
                infoBuilder.append(" at " + dateFormat.format(task.getStartTime()));
            }
            if (!task.isActive()) {
                infoBuilder.append(" inactive ");
            }
            tblModel.addRow(new Object[]{task.getTitle(), infoBuilder.toString()});
        }
    }


}
