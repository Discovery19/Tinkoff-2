package edu.scrapper.api.repositories.jdbc;

import edu.scrapper.api.repositories.SchedulerRepository;
import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class JdbcSchedulerRepository implements SchedulerRepository {
    private final JdbcTemplate jdbcTemplate;

    @Override
    public List<URI> findLinksBefore(OffsetDateTime time) {
        return jdbcTemplate.queryForList("select url from links where last_checked < (?)", URI.class, time);
    }

    @Override
    public OffsetDateTime getLastUpdate(URI url) {
        return jdbcTemplate.queryForObject(
            "select updated_at from links where url = ?",
            OffsetDateTime.class,
            url.toString()
        );
    }

    @Override
    public Long getIdByLink(URI url) {
        return jdbcTemplate.queryForObject("SELECT id FROM links WHERE url = ?", Long.class, url.toString());
    }

    @Override
    public void updateCheckedAt(URI url, OffsetDateTime time) {
        jdbcTemplate.update("UPDATE links SET last_checked = ? WHERE url = ?", time, url.toString());
    }

    @Override
    public void updateUpdatedAt(URI url, OffsetDateTime time) {
        jdbcTemplate.update("update links set updated_at = ? where url = ?", time, url.toString());
    }

    @Override
    public List<Long> getSubscribedChats(Long linkId) {
        return jdbcTemplate.queryForList("select chat_id from link_chat where link_id = ?", Long.class, linkId);
    }
}
