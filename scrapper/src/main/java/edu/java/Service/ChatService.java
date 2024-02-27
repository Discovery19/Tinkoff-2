package edu.java.Service;

import edu.java.DTO.AllLinksDTO;
import edu.java.DTO.RequestDTO;
import edu.java.DTO.ResponseDTO;
import edu.java.MyDataBase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@Slf4j
public class ChatService {
    public void registerChat(Long id) {
        log.info("Чат с ID " + id + " зарегистрирован");
        MyDataBase.register(id);
    }

    public void deleteChat(Long id) {
        log.info("Чат с ID " + id + " удалён");
    }
    public AllLinksDTO getLinks(Long id) {
        log.info("Здесь все отслеживаемые ссылки");
        return MyDataBase.getLinks(id);
    }
    public void trackLink(RequestDTO dto) {
        MyDataBase.add(dto.id(), dto.link());
        log.info("Ссылка "+ dto.link()+" теперь отслеживается");
    }
    public void untrackLink(RequestDTO dto) {
        MyDataBase.remove(dto.id(), dto.link());
        log.info("Ссылка "+ dto.link()+" больше не отслеживается");
    }
}
