package model;


import java.io.Serializable;
import java.util.Date;

/**
 * Class that describe Task and provides methods for its processing.
 * <p/>
 * Every {@code Task} has:
 * <ul>
 * <li><em>Title</em>, that contain short desctiption of task
 * <li><em>Active state</em>, which indicates whether the task is active
 * <li><em>Repeat state</em>, which indicates whether the task is repeatable
 * <li><em>Repeat interval</em>, which indicates, if task is repeatable,
 * how often that task occurs
 * <li><em>Time values</em>. If task is repeatable, time values is represented
 * by start and end of time interval. If it's not repeatable, time value
 * describe when this task occur
 * </ul>
 *
 * @author Panchenko Alexandr
 */
public class Task implements Cloneable, Serializable {


    private String title;
    private Date time;
    private boolean repeated;
    private boolean active;
    private Date start;
    private Date end;
    private int interval;

    /**
     * <strong>[Constructor]</strong>
     * <p/>
     * Creates new Task that:
     * <ul>
     * <li>is <em>active</em>
     * <li>is named as {@code title}
     * <li>is <em><strong>non</strong>-repeatable</em>
     * <li>occurs <em>once</em> at {@code time}
     *
     * @param title sets the title of the task
     * @param time  the point in time when this task is going to happen
     */
    public Task(String title, Date time) {
        if (time == null) {
            throw new IllegalArgumentException("Time can not be null");
        }
        this.setTitle(title);
        this.title = title;
        this.time = time;
        this.repeated = false;
        this.active = false;

    }

    /**
     * <strong>[Constructor]</strong>
     * <p/>
     * Creates new Task that:
     * <ul>
     * <li>is <em>active</em>
     * <li>is named as {@code title}
     * <li>is <em><strong>repeatable</strong></em>
     * <li>occurs <em>between</em> at {@code start} and {@code end}
     * <li>is <em>repeated</em> every {@code interval}
     *
     * @param title    sets the title of the task
     * @param start    the point in time when this task occurs in the first time
     * @param end      the point in time when this task will happen for the
     *                 last time
     * @param interval sets the repeat interval of the task
     */
    public Task(String title, Date start, Date end, int interval) {
        this.setTitle(title);
        this.active = false;
        this.repeated = true;
        this.setTime(start, end, interval);

    }

    /**
     * <strong>[Method - Setter]</strong>
     * <p/>
     * Change current task's {@link Task#title}.
     *
     * @param title new {@link Task#title} of the task
     */
    public void setTitle(String title) {
        if (title == null || title.length() <= 0) {
            throw new IllegalArgumentException("Title should not be empty.");
        }
        this.title = title;
    }

    /**
     * <strong>[Method - Getter]</strong>
     * <p/>
     * Returns a {@link Task#title} of the task in string.
     *
     * @return string with current task's title
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * <strong>[Method - Setter]</strong>
     * <p/>
     * Change {@link Task#active} flag.
     * <p/>
     * <strong><em>Warning:</em></strong> turning task off means that some
     * methods, that works with tasks, may ignote such tasks.
     *
     * @param active {@code true} if you want turn off your task and
     *               {@code false} if turn on.
     */
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * <strong>[Method - Getter]</strong>
     * <p/>
     * Returns {@link Task#active} state
     *
     * @return {@code true} if task is active and {@code false} if not
     */
    public boolean isActive() {
        return this.active;
    }

    /**
     * <strong>[Method - Getter]</strong>
     * Returns time of task.
     *
     * @return Returns time of task if it isn't repeated and start time if it is.
     */
    public Date getTime() {
        if (repeated) {
            return getStartTime();
        }
        return (Date) time.clone();
    }

    /**
     * <strong>[Method - Setter]</strong>
     * Set time of task. Make it nonrepetitive if it is.
     *
     * @param time Cannot be less or equal 0.
     */
    public void setTime(Date time) {
        if (time == null) {
            throw new IllegalArgumentException("Date should not be NULL.");
        }
        if (repeated) {
            repeated = false;
        }
        this.time = time;
    }

    /**
     * <strong>[Method - Getter]</strong>
     * Returns start Date of task.
     *
     * @return Returns time of task if it isn't repeated and start time if it is.
     */
    public Date getStartTime() {
        return this.repeated ? (Date) start.clone() : getTime();
    }

    /**
     * <strong>[Method - Getter]</strong>
     * Returns end Date of task.
     *
     * @return Returns time of task if it is not repeated and end time if it is.
     */
    public Date getEndTime() {
        return this.repeated ? (Date) end.clone() : getTime();
    }

    /**
     * <strong>[Method - Getter]</strong>
     * Returns repeat interval of task.
     *
     * @return Returns interval of task if it is repeated and 0 if it is not.
     */
    public int getRepeatInterval() {
        return this.repeated ? interval : 0;
    }

    /**
     * <strong>[Method - Setter]</strong>
     * Sets start time, end time and repeat interval. If task is not repeated, set it repeated.
     *
     * @param start    Cannot be more than end.
     * @param end      Cannot less than start.
     * @param interval Cannot be less than 0.
     */
    public void setTime(Date start, Date end, int interval) {
        if (start == null || end == null) {
            throw new IllegalArgumentException("Dates should not be null.");
        }
        if (start.after(end)) {
            throw new IllegalArgumentException("Start Date cannot be before End Date");
        }
        if (interval < 0) {
            throw new IllegalArgumentException("Repeated interval cannot be " +
                    "less than 0");
        }
        if (!this.repeated) {
            this.repeated = true;
        }
        this.start = start;
        this.end = end;
        this.interval = interval;
    }

    /**
     * <strong>[Method - Getter]</strong>
     * <p/>
     * Returns {@link Task#repeated} state
     *
     * @return {@code true} if task is repetitive and {@code false} if not
     */
    public boolean isRepeated() {
        return this.repeated;
    }


    /**
     * Determines if this task old.
     *
     * @param time before this time task is considered old
     * @return true if it tooks more a specified time
     */
    public boolean isOld(int time) {
        Date currentDate = new Date();
        Date lastDate = new Date(currentDate.getTime() - time);
        return getEndTime().before(lastDate);
    }

    /**
     * @param current Cannot be less than 0 or equal to it.
     * @return Returns next time of task after current.
     */
    public Date nextTimeAfter(Date current) {
        if (current == null) {
            throw new IllegalArgumentException("Argument can't be NULL");
        }
        if (!isActive() || (!isRepeated() && !current.before(getTime()))) {
            return null;
        }
        if (!isRepeated()) {
            return getTime();
        }

        if (isActive() && isRepeated()) {
            if (end.before(current)) {
                return null;
            }
            if (current.getTime() == 0) {
                return this.start;
            }
            if (current.before(start)) {
                return start;
            }

            Date time = getStartTime();
            while (!time.after(current)) {
                if (time.getTime() + getRepeatInterval() * 1000 > getEndTime()
                        .getTime()) {
                    return null;
                }
                time.setTime(time.getTime() + getRepeatInterval() * 1000);
            }

            return time;
        }
        return null;
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param object the reference object with which to compare.
     * @return {@code true} if this object is the same as object argument, or
     * {@code false} otherwise.
     */
    @Override
    public boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        if (object == this) {
            return true;
        }

        if (!(object instanceof Task)) {
            return false;
        }

        Task other = (Task) object;
        if (!(other.title.equals(title) && (other.active == active)
                && (other.repeated == repeated))) {
            return false;
        }

        if (repeated) {
            return start.equals(other.start) && end.equals(other.end)
                    && (other.interval == interval);
        }

        return getTime().equals(other.getTime());


    }

    /**
     * Returns a hash code value for the Task. This method is
     * supported for the benefit of hash tables such as those provided by
     * {@link java.util.HashMap}
     *
     * @return int hash code
     */
    @Override
    public int hashCode() {
        int result = title.hashCode();
        result = 31 * result + (repeated ? 1 : 0);
        result = 31 * result + (active ? 1 : 0);
        if (!repeated) {
            result = 31 * result + time.hashCode();
        } else {
            result = 31 * result + start.hashCode();
            result = 31 * result + end.hashCode();
            result = 31 * result + interval;
        }
        return result;
    }

    /**
     * Returns a string containing of the Task
     *
     * @return a string containing of the Task
     */
    @Override
    public String toString() {
        String result = title + " ";
        if (active) {
            result += "active ";
        } else {
            result += "inactive ";
        }
        if (repeated) {
            result += "repeated from " + start + " to "
                    + end + " every " + interval;
        } else {
            result += "time: " + time;
        }
        return result;
    }

    /**
     * Returns a clone of this list
     *
     * @return clone of this list
     */
    @Override
    public Task clone() {
        Task taskClone = null;
        try {
            taskClone = (Task) super.clone();
        } catch (CloneNotSupportedException e) {
        } // Won't happen
        return taskClone;
    }

}