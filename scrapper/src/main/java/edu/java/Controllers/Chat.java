package edu.java.Controllers;

import edu.java.DTO.AllLinksDTO;
import edu.java.DTO.RequestDTO;
import edu.java.DTO.ResponseDTO;
import edu.java.Response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

public interface Chat {
    @Operation(summary = "Зарегистрировать чат")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Чат зарегистрирован"),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class))) })
    @PostMapping(value = "/tg-chat/{id}",
                 produces = { "application/json" })
    ResponseEntity<Long> registerChat(Long id);
    @Operation(summary = "Удалить чат")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Чат успешно удалён"),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json"
                         , schema = @Schema(implementation = ApiErrorResponse.class))
        ),
        @ApiResponse(responseCode = "404",
                     description = "Чат не существует",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class))) })
    @DeleteMapping(value = "/tg-chat/{id}",
                   produces = { "application/json" })
    ResponseEntity<Long> deleteChat(Long id);
    @GetMapping("/tg-chat/{id}/links")
    ResponseEntity<AllLinksDTO> getLinks(Long id);
    @PostMapping("/tg-chat/{id}/links")
    ResponseEntity<Long> trackLink(RequestDTO dto);
    @DeleteMapping("/tg-chat/{id}/links")
    ResponseEntity<Long> untrackLink(RequestDTO dto);
}
