package io.pivotal.pal.tracker;

import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeEntryRepository {
    TimeEntry create(TimeEntry timeEntry);
    List<TimeEntry> list();
    TimeEntry find(long timeEntryId);
    TimeEntry update(long id, TimeEntry timeEntry);
    void delete(long id);

}
