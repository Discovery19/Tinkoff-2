package edu.scrapper.api.repositories;

import java.net.URI;
import java.time.OffsetDateTime;
import java.util.List;

public interface SchedulerRepository {
    List<URI> findLinksBefore(OffsetDateTime time);

    OffsetDateTime getLastUpdate(URI url);

    Long getIdByLink(URI url);

    void updateCheckedAt(URI url, OffsetDateTime time);

    void updateUpdatedAt(URI url, OffsetDateTime time);

    List<Long> getSubscribedChats(Long linkId);
}
