package model;

import java.util.*;

/**
 * This class allows to find tasks which are planned certain time
 *
 * @author Alexandr Panchenko
 */

public class Tasks {

    /**
     * Build calendar and put it into SortedMap
     *
     * @param tasks list with tasks
     * @param start start time for calendar
     * @param end   end time for calendar
     * @return calendar
     */
    public static SortedMap<Date, Set<Task>> calendar(Iterable<Task> tasks, Date start, Date end) {
        SortedMap<Date, Set<Task>> map = new TreeMap<Date, Set<Task>>();
        for (Task task : tasks) {
            Date time = task.nextTimeAfter(start);
            while (time != null && !end.before(time)) {
                if (!map.containsKey(time)) {
                    Set<Task> set = new HashSet<Task>();
                    set.add(task);
                    map.put((Date) time.clone(), set);
                } else {
                    Set<Task> set = map.get(time);
                    set.add(task);
                }
                time = task.nextTimeAfter(time);
            }
        }
        return map;
    }
}