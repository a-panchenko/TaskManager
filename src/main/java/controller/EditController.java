package controller;

import model.Model;
import model.Task;
import org.apache.log4j.Logger;
import view.AddTaskView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Class that describe how to edit Task according to User's input.
 *
 * @author Panchenko Alexandr
 */
public class EditController implements Controller {

    private Model model;
    private AddTaskView editTaskView;
    private int taskID;
    private static final Logger LOGGER = Logger.getLogger(EditController.class);

    /**
     * <strong>[Constructor]</strong>
     *
     * @param model        captures the behavior of the application
     * @param editTaskView represent information for user
     */
    public EditController(Model model, AddTaskView editTaskView, int taskID) {
        this.model = model;
        this.editTaskView = editTaskView;
        this.taskID = taskID;

        Task task = model.getTaskList().get(taskID);
        editTaskView.getTitleField().setText(task.getTitle());
        editTaskView.getActivityChooser().setSelectedIndex(task.isActive() ? 0 : 1);
        editTaskView.getRepetitiveChooser().setSelectedIndex(task.isRepeated() ? 0 : 1);
        editTaskView.getStartDateChooser().setDate(task.getStartTime());
        if (task.isRepeated()) {
            editTaskView.getEndDateChooser().setDate(task.getEndTime());
            long interval = task.getRepeatInterval();
            long days = (int) (interval / (24 * 60 * 60));
            long hours = (int) (interval - days * 24 * 60 * 60) / (60 * 60);
            long minutes = (int) ((interval - hours * 60 * 60 - days * 24 * 60 * 60) / 60);
            long seconds = (int) (interval - minutes * 60 - hours * 60 * 60 - days * 24 * 60 * 60);
            editTaskView.getSecondsField().setValue(seconds);
            editTaskView.getMinutesField().setValue(minutes);
            editTaskView.getHoursField().setValue(hours);
            editTaskView.getDaysField().setValue(days);
        }
    }

    /**
     * Listen to user's input and edit current Task if it possible.
     */
    @Override
    public void control() {
        ActionListener editButtonListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String title = editTaskView.getTitleField().getText();
                    boolean isActive;
                    isActive = editTaskView.getActivityChooser().getModel().getSelectedItem().equals("Active");
                    boolean isRepeatable;
                    isRepeatable = editTaskView.getRepetitiveChooser().getModel().getSelectedItem().equals("Repetitive");
                    Date startTime = editTaskView.getStartDateChooser().getDate();
                    Date endTime = null;
                    long interval = 0;
                    Task task;
                    if (isRepeatable) {
                        endTime = editTaskView.getEndDateChooser().getDate();
                        interval = (editTaskView.getSecondsField().getValue() != null) ?
                                (Long) editTaskView.getSecondsField().getValue() : 0;
                        interval += (editTaskView.getMinutesField().getValue() != null) ?
                                (Long) editTaskView.getMinutesField().getValue() * 60 : 0;
                        interval += (editTaskView.getHoursField().getValue() != null) ?
                                (Long) editTaskView.getHoursField().getValue() * 60 * 60 : 0;
                        interval += (editTaskView.getDaysField().getValue() != null) ?
                                (Long) editTaskView.getDaysField().getValue() * 60 * 60 * 24 : 0;
                    }
                    if (isRepeatable) {
                        task = new Task(title, startTime, endTime, (int) interval);
                    } else {
                        task = new Task(title, startTime);
                    }
                    task.setActive(isActive);
                    model.getTaskList().set(taskID, task);
                    model.notifyObservers();
                    editTaskView.dispose();
                    LOGGER.info("Task " + task.getTitle() + " was edit.");
                } catch (IllegalArgumentException ex) {
                    editTaskView.showMessage(ex.getMessage());
                    LOGGER.error("Exception", ex);
                }
            }
        };
        ActionListener cancelButtonActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                editTaskView.dispose();
            }
        };
        editTaskView.getjButton1().addActionListener(editButtonListener);
        editTaskView.getjButton2().addActionListener(cancelButtonActionListener);
    }
}
