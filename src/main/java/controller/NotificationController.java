package controller;

import model.Model;
import model.Task;
import model.Tasks;
import model.XMLParser;
import org.apache.log4j.Logger;
import view.MainView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Class allow to control which tasks were missed and which task should be done now.
 *
 * @author Panchenko Alexandr
 */
public class NotificationController implements Runnable {

    private Model model;
    private MainView mainView;
    private static final Logger LOGGER = Logger.getLogger(NotificationController.class);

    public NotificationController(MainView mainView, Model model) {
        this.model = model;
        this.mainView = mainView;
    }

    /**
     * Checks whether it is time to perform the task and show message if it is.
     */
    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        long lastVisit = XMLParser.getLasVisit();
        Date currentDate = new Date();
        StringBuffer stringBuffer = new StringBuffer("You have missed task(s): \n");
        SortedMap<Date, Set<Task>> map = Tasks.calendar(model.getTaskList(), new Date(lastVisit), currentDate);
        if (!map.isEmpty()) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
            for (Map.Entry<Date, Set<Task>> pair : map.entrySet()) {
                Date date = pair.getKey();
                for (Task task : pair.getValue())
                    if (task.isActive())
                        stringBuffer.append(dateFormat.format(date) + " : " + task.getTitle() + "\n");
            }
            mainView.showMessage(stringBuffer.toString());
        }
        while (!thread.isInterrupted()) {
            currentDate = new Date();
            stringBuffer = new StringBuffer();
            int size = model.getTaskList().size();
            for (int i = 0; i < size; i++) {
                Task task = model.getTaskList().get(i);
                Date nextTime = task.nextTimeAfter(currentDate);

                if (nextTime != null && (nextTime.getTime() - currentDate.getTime()) < 1000 &&
                        (nextTime.getTime() - task.getTime().getTime()) >= 0) {

                    stringBuffer.append(task.getTitle() + " should be done at " + nextTime + "\n");
                }
            }
            if (stringBuffer.length() > 0) mainView.showMessage(stringBuffer.toString());
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                LOGGER.error("Exception", e);
            }
        }
    }
}
