package edu.java.Controllers;

import edu.java.DTO.AllLinksDTO;
import edu.java.DTO.RequestDTO;
import edu.java.Service.ChatService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("/tg-chat")
public class ChatController implements Chat{
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }
//    @PostMapping("/{id}")
    public ResponseEntity<Long> registerChat(@PathVariable Long id) {
        chatService.registerChat(id);
        return ResponseEntity.ok(id);
//        return ResponseEntity.ok(HttpStatus.OK);
    }
//    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteChat(@PathVariable Long id) {
        chatService.deleteChat(id);
        return ResponseEntity.ok(id);
    }

//    @GetMapping("/{id}/links")
    public ResponseEntity<AllLinksDTO> getLinks(@PathVariable Long id) {
        AllLinksDTO dto = chatService.getLinks(id);
        if (dto.getLinks() != null && !dto.getLinks().isEmpty()) {
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

//    @PostMapping("/{id}/links")
    public ResponseEntity<Long> trackLink(@RequestBody RequestDTO dto) {
        chatService.trackLink(dto);
        return ResponseEntity.ok(dto.id());
    }

//    @DeleteMapping("/{id}/links")
    public ResponseEntity<Long> untrackLink(@RequestBody RequestDTO dto) {
        chatService.untrackLink(dto);
        return ResponseEntity.ok(dto.id());
    }
}
