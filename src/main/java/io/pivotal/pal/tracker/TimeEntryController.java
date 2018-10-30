package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    @Autowired
    TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }
    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        //TimeEntryRepository timeEntryRepository = new InMemoryTimeEntryRepository();
        timeEntryToCreate = timeEntryRepository.create(timeEntryToCreate);
        ResponseEntity<TimeEntry> responseEntity = new ResponseEntity<>(timeEntryToCreate, HttpStatus.CREATED);
        return responseEntity;
    }
    @GetMapping("/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable("id") long timeEntryId) {
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
    @PutMapping("/{id}")
     public @ResponseBody  ResponseEntity update(@PathVariable("id") long timeEntryId, @RequestBody TimeEntry expected)

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
    @GetMapping
    public ResponseEntity<List<TimeEntry>> list() {
        List<TimeEntry> lst =timeEntryRepository.list();
        ResponseEntity <List<TimeEntry>> responseEntity = new ResponseEntity<>(lst, HttpStatus.OK);
        return responseEntity;
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable("id") long timeEntryId) {

        ResponseEntity<TimeEntry> responseEntity;
        timeEntryRepository.delete(timeEntryId);

        responseEntity = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        return responseEntity;
    }
}
