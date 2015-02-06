package controller;

import model.Model;
import org.apache.log4j.Logger;
import view.MainView;

/**
 * Class describe deleting of task.
 *
 * @author Panchenko Alexandr
 */

public class DeleteController implements Controller {

    private MainView mainView;
    private Model model;
    private static final Logger LOGGER = Logger.getLogger(DeleteController.class);

    /**
     * <strong>[Constructor]</strong>
     *
     * @param model    captures the behavior of the application
     * @param mainView represent information for user
     */
    public DeleteController(MainView mainView, Model model) {
        this.mainView = mainView;
        this.model = model;
    }

    /**
     * Delete task after users's confirmation.
     */
    @Override
    public void control() {
        boolean isConfirmed = mainView.confirmDelete("Are you sure you want to delete the task?");

        if (isConfirmed) {
            model.getTaskList().remove(mainView.getTable().getSelectedRow());
            model.notifyObservers();
            LOGGER.info("One task was deleted");
        }
    }
}
