package model;

import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Observable;

/**
 * Class which captures the behavior of the application
 *
 * @author Panchenko Alexandr
 */
public class Model extends Observable {

    private ArrayList<Task> taskList = new ArrayList<Task>();
    private static final int MINUTE = 60;
    private static final int HOUR = 60 * MINUTE;
    private static final int DAY = 24 * HOUR;
    private static final Logger LOGGER = Logger.getLogger(Model.class);

    /**
     * <strong>[Constructor]</strong>
     * Initializes taskList
     */
    public Model() {
        XMLParser.readXML(taskList);
    }

    /**
     * Notify Observers, that taskList was changed and rewrite XML file.
     */
    @Override
    public void notifyObservers() {
        super.setChanged();
        super.notifyObservers();
        XMLParser.writeXML(taskList);
        LOGGER.info("TaskList was changed.");
    }

    /**
     * <strong>[Method - Getter]</strong>
     * Returns taskList
     *
     * @return taskList
     */
    public ArrayList<Task> getTaskList() {
        return taskList;
    }

    /**
     * Make string representation of time in seconds.
     *
     * @param time should be parsed
     * @return String representation of time in seconds.
     */
    public static String formatSeconds(int time) {
        if (time <= 0)
            return null;
        StringBuffer text = new StringBuffer("");
        if (time > DAY) {
            text.append(time / DAY).append(" days ");
            time %= DAY;
        }
        if (time > HOUR) {
            text.append(time / HOUR).append(" hours ");
            time %= HOUR;
        }
        if (time > MINUTE) {
            text.append(time / MINUTE).append(" minutes ");
            time %= MINUTE;
        }
        if (time > 0)
            text.append(time + " seconds");
        return text.toString().trim();
    }


}

