package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class InMemoryTimeEntryRepository implements TimeEntryRepository{

    Long index = 1L;
    Map<Long,TimeEntry> localDB = new HashMap<>();

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId(index);
        localDB.put(index,timeEntry);
        index++;
         return timeEntry;
    }

    public List<TimeEntry> list() {
        List<TimeEntry> timeEntries = new ArrayList<>(localDB.values());

        return timeEntries;
    }

    public TimeEntry find(long timeEntryId) {
        return localDB.get(timeEntryId);
    }

    public TimeEntry update(long id, TimeEntry timeEntry) {
         timeEntry.setId(id);
        localDB.put(id,timeEntry);
        return localDB.get(id);
    }

    public void delete(long id) {
        localDB.remove(id);
    }




}
