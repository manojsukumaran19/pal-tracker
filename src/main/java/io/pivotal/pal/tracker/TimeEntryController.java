package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class TimeEntryController {

    @Autowired
    TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    public ResponseEntity create(TimeEntry timeEntryToCreate) {
        //TimeEntryRepository timeEntryRepository = new InMemoryTimeEntryRepository();
        timeEntryToCreate = timeEntryRepository.create(timeEntryToCreate);
        ResponseEntity<TimeEntry> responseEntity = new ResponseEntity<>(timeEntryToCreate, HttpStatus.CREATED);
        return responseEntity;
    }

    public ResponseEntity<TimeEntry> read(long timeEntryId) {
        ResponseEntity<TimeEntry> responseEntity;
        TimeEntry tmEntry=null;
        tmEntry = timeEntryRepository.find(timeEntryId);
        if (tmEntry==null) {
            responseEntity = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        else
        {
            responseEntity = new ResponseEntity<>(tmEntry, HttpStatus.OK);
        }
        return responseEntity;
    }

    public ResponseEntity update(long timeEntryId, TimeEntry expected)

    {
        ResponseEntity<TimeEntry> responseEntity;
        expected = timeEntryRepository.update(timeEntryId,expected);
        if (expected==null) {
            responseEntity = new ResponseEntity<>(expected, HttpStatus.NOT_FOUND);
        }
        else
        {
           responseEntity = new ResponseEntity<>(expected, HttpStatus.OK);
        }

        return responseEntity;
    }

    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> lst =timeEntryRepository.list();
        ResponseEntity <List<TimeEntry>> responseEntity = new ResponseEntity<>(lst, HttpStatus.OK);
        return responseEntity;
    }

    public ResponseEntity<TimeEntry> delete(long timeEntryId) {

        ResponseEntity<TimeEntry> responseEntity;
        timeEntryRepository.delete(timeEntryId);

        responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return responseEntity;
    }
}
