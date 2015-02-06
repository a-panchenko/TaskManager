package controller;


import model.Model;
import model.Task;
import org.apache.log4j.Logger;
import view.AddTaskView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;

/**
 * Class that describe how to add new Task according to User's input.
 *
 * @author Panchenko Alexandr
 */
public class AddController implements Controller {
    private Model model;
    private AddTaskView addTaskView;
    private static final Logger LOGGER = Logger.getLogger(AddController.class);

    /**
     * <strong>[Constructor]</strong>
     *
     * @param model       captures the behavior of the application
     * @param addTaskView represent information for user
     */
    public AddController(Model model, AddTaskView addTaskView) {
        this.model = model;
        this.addTaskView = addTaskView;
    }

    /**
     * Listen to user's input and add new Task if it possible.
     */
    @Override
    public void control() {
        ActionListener addButtonActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    String title = addTaskView.getTitleField().getText();
                    boolean isActive;
                    isActive = addTaskView.getActivityChooser().getModel().getSelectedItem().equals("Active");
                    boolean isRepeatable;
                    isRepeatable = addTaskView.getRepetitiveChooser().getModel().getSelectedItem().equals("Repetitive");
                    Date startTime = addTaskView.getStartDateChooser().getDate();
                    Date endTime = null;
                    long interval = 0;
                    Task task;
                    if (isRepeatable) {
                        endTime = addTaskView.getEndDateChooser().getDate();
                        interval = (addTaskView.getSecondsField().getValue() != null) ?
                                (Long) addTaskView.getSecondsField().getValue() : 0;
                        interval += (addTaskView.getMinutesField().getValue() != null) ?
                                (Long) addTaskView.getMinutesField().getValue() * 60 : 0;
                        interval += (addTaskView.getHoursField().getValue() != null) ?
                                (Long) addTaskView.getHoursField().getValue() * 60 * 60 : 0;
                        interval += (addTaskView.getDaysField().getValue() != null) ?
                                (Long) addTaskView.getDaysField().getValue() * 60 * 60 * 24 : 0;
                    }
                    if (isRepeatable) {
                        task = new Task(title, startTime, endTime, (int) interval);
                    } else {
                        task = new Task(title, startTime);
                    }
                    task.setActive(isActive);
                    model.getTaskList().add(task);
                    LOGGER.info("New task with title " + task.getTitle() + " was added into list");
                    model.notifyObservers();
                    addTaskView.closeView();
                } catch (IllegalArgumentException ex) {
                    addTaskView.showMessage(ex.getMessage());
                    LOGGER.error("Exception", ex);
                }

            }
        };
        ActionListener cancelButtonActionListener = new ActionListener() {
            public void actionPerformed(ActionEvent actionEvent) {
                addTaskView.dispose();
            }
        };
        addTaskView.getjButton1().addActionListener(addButtonActionListener);
        addTaskView.getjButton2().addActionListener(cancelButtonActionListener);
    }
}
