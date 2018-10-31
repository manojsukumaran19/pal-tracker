package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/time-entries")
public class TimeEntryController {

    @Autowired
    TimeEntryRepository timeEntryRepository;
    private final CounterService counter;
    private final GaugeService gauge;

    public TimeEntryController(TimeEntryRepository timeEntryRepository,        CounterService counter,
                               GaugeService gauge) {
        this.timeEntryRepository = timeEntryRepository;
        this.counter = counter;
        this.gauge = gauge;

    }

    @PostMapping
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        //TimeEntryRepository timeEntryRepository = new InMemoryTimeEntryRepository();
        timeEntryToCreate = timeEntryRepository.create(timeEntryToCreate);
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());
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
