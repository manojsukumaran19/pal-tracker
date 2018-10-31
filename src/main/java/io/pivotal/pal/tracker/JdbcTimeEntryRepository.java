package io.pivotal.pal.tracker;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.util.List;

import static java.sql.Statement.RETURN_GENERATED_KEYS;
@Repository
public class JdbcTimeEntryRepository implements TimeEntryRepository{

    private JdbcTemplate jtemplate;

    public JdbcTimeEntryRepository(DataSource dsc) {
        jtemplate=new JdbcTemplate(dsc);
    }

    public JdbcTimeEntryRepository() {
        
    }

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        KeyHolder keyHolder= new GeneratedKeyHolder();
        jtemplate.update(connection->{
                    PreparedStatement statement=connection.prepareStatement("Insert into  time_entries (project_id, user_id, date, hours) VALUES (?, ?, ?, ?)",RETURN_GENERATED_KEYS);
                    statement.setLong(1,timeEntry.getProjectId());
                    statement.setLong(2,timeEntry.getUserId());
                    statement.setDate(3, Date.valueOf(timeEntry.getDate()));
                    statement.setInt(4,timeEntry.getHours());
                   return statement;
                },keyHolder  );
        return find(keyHolder.getKey().longValue());

    }

    @Override
    public List<TimeEntry> list() {

        return jtemplate.query("select id,project_id, user_id,date,hours from time_entries",mapper);
    }

    @Override
    public TimeEntry find(long timeEntryId) {
        return jtemplate.query("select * from time_entries where id=?", new Object[] {timeEntryId},extractor);
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        TimeEntry timeEntries;
        timeEntries=find(id);
        if (timeEntries!=null)
          jtemplate.update("update time_entries set project_id =?,user_id=?, date=?,hours=? where id=?",
                     timeEntry.getProjectId(),
                     timeEntry.getUserId(),
                     timeEntry.getDate(),
                     timeEntry.getHours(),
                     id);
         return find(id);
    }

    @Override
    public void delete(long id) {
        jtemplate.update("delete from time_entries where id=?",id);

    }
    private final RowMapper<TimeEntry> mapper = (rs, rowNum) -> new TimeEntry(
            rs.getLong("id"),
            rs.getLong("project_id"),
            rs.getLong("user_id"),
            rs.getDate("date").toLocalDate(),
            rs.getInt("hours")
    );

    private final ResultSetExtractor<TimeEntry> extractor =
            (rs) -> rs.next() ? mapper.mapRow(rs, 1) : null;
}
