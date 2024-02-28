package edu.java.controllers;

import edu.java.DTO.AllLinksDTO;
import edu.java.DTO.RequestDTO;
import edu.java.response.ApiErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import reactor.core.publisher.Mono;

public interface Chat {
    //добавлю и на остальные, если это норм
    @Operation(summary = "Зарегистрировать чат")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Чат зарегистрирован"),
        @ApiResponse(responseCode = "400", description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))})
    @PostMapping(value = "/tg-chat/{id}",
                 produces = {"application/json"})
    Mono<Long> registerChat(Long id);

    @Operation(summary = "Удалить чат")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200",
                     description = "Чат успешно удалён"),
        @ApiResponse(responseCode = "400",
                     description = "Некорректные параметры запроса",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(responseCode = "404",
                     description = "Чат не существует",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = ApiErrorResponse.class)))})
    @DeleteMapping(value = "/tg-chat/{id}",
                   produces = {"application/json"})
    Mono<Long> deleteChat(Long id);

    @GetMapping("/tg-chat/{id}/links")
    Mono<AllLinksDTO> getLinks(Long id);

    @PostMapping("/tg-chat/{id}/links")
    Mono<Long> trackLink(RequestDTO dto);

    @DeleteMapping("/tg-chat/{id}/links")
    Mono<Long> untrackLink(RequestDTO dto);
}
