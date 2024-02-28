package edu.java.service;
//CHECKSTYLE:OFF: checkstyle:ImportOrder
import edu.java.client.ScrapperAPIClient;
import edu.java.DTO.RequestDTO;
import edu.java.MyDataBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import edu.java.DTO.AllLinksDTO;

@Service
@Slf4j
//CHECKSTYLE:OFF: checkstyle:MultipleStringLiterals
public class ChatService {
    private final ScrapperAPIClient scrapperAPIClient;

    public ChatService(ScrapperAPIClient scrapperAPIClient) {
        this.scrapperAPIClient = scrapperAPIClient;
    }

    public Mono<Long> registerChat(Long id) {
        log.info("сервис регистрации");
        return scrapperAPIClient.registerChat(id)
            .doOnSuccess(success -> {
                log.info("Чат с ID " + id + " зарегистрирован");
                MyDataBase.register(id);
            });
    }

    public Mono<Long> deleteChat(Long id) {
        return scrapperAPIClient.deleteChat(id)
            .doOnSuccess(success -> log.info("Чат с ID " + id + " удалён"));
    }

    public Mono<AllLinksDTO> getLinks(Long id) {
        log.info("Здесь все отслеживаемые ссылки");
        return scrapperAPIClient.getLinks(id)
            .doOnSuccess(success -> log.info("Список ссылок"));
    }

    public Mono<Long> trackLink(RequestDTO dto) {
        return scrapperAPIClient.trackLink(dto)
            .doOnSuccess(success -> log.info("Ссылка " + dto.link() + " теперь отслеживается"));
    }

    public Mono<Long> untrackLink(RequestDTO dto) {
        return scrapperAPIClient.untrackLink(dto)
            .doOnSuccess(success -> log.info("Ссылка " + dto.link() + " больше не отслеживается"));
    }
}
