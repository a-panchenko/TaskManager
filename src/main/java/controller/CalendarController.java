package controller;

import model.Model;
import model.Task;
import model.Tasks;
import org.apache.log4j.Logger;
import view.CalendarView;

import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * Class describe building of calendar
 *
 * @author Panchenko Alexandr
 */
public class CalendarController implements Controller {

    private Model model;
    private CalendarView calendarView;
    private DefaultTableModel tblModel;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy");
    private static final Logger LOGGER = Logger.getLogger(CalendarController.class);
    private Date startDate;
    private Date endDate;

    /**
     * <strong>[Constructor]</strong>
     *
     * @param model        captures the behavior of the application
     * @param calendarView represent information for user
     */
    public CalendarController(Model model, CalendarView calendarView) {
        this.model = model;
        this.calendarView = calendarView;
        this.tblModel = (DefaultTableModel) calendarView.getjTable().getModel();
    }

    /**
     * <strong>[Method - Setter]</strong>
     *
     * @param startDate start of calendar
     * @param endDate   end of calendar
     */
    public void setDates(Date startDate, Date endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    /**
     * Build calendar.
     */
    @Override
    public void control() {
        SortedMap<Date, Set<Task>> map = Tasks.calendar(model.getTaskList(), startDate, endDate);
        for (Map.Entry<Date, Set<Task>> pair : map.entrySet()) {
            Date date = pair.getKey();
            for (Task task : pair.getValue()) {
                StringBuilder infoBuilder = new StringBuilder();
                if (task.isRepeated()) {
                    infoBuilder.append(task.getTitle() + " from " + dateFormat.format(task.getStartTime()) +
                            " to " + dateFormat.format(task.getEndTime()) + " every " +
                            Model.formatSeconds(task.getRepeatInterval()));
                } else {
                    infoBuilder.append(task.getTitle() + " at " + dateFormat.format(task.getStartTime()));
                }
                if (!task.isActive()) {
                    infoBuilder.append(" inactive ");
                }
                tblModel.addRow(new Object[]{dateFormat.format(date), infoBuilder.toString()});
                LOGGER.info("Calendar was drawn up");
            }
        }

    }

}
