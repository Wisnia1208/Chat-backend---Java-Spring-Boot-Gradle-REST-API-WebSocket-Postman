package pl.polsl.rest.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.rest.api.dto.*;
import pl.polsl.rest.api.service.HttpSessionService;
import pl.polsl.rest.api.service.MessageService;
import pl.polsl.rest.api.service.WebsocketService;

import javax.servlet.http.HttpSession;

import java.util.Optional;

import static java.util.Objects.nonNull;

@RestController()
@RequestMapping("/api/messages")
public class MessagesController {

    private final HttpSessionService sessionService;
    private final MessageService messageService;

    public MessagesController(final HttpSessionService sessionService, final MessageService messageService) {
        this.sessionService = sessionService;
        this.messageService = messageService;
    }

    // TODO: 12
    @PostMapping("")
    ResponseEntity<?> handleSendMessage(@RequestBody final MessageDto dto, final HttpSession session) {

        return ResponseEntity.ok(messageService.sendMessage(dto, session));
    }

    // TODO: 10
    @GetMapping("/{id}")
    ResponseEntity<?> handleGetMessages(@PathVariable("id") final int userId, final HttpSession session) {

        return ResponseEntity.ok(new MessagesDto(messageService.getMessagesBetweenUsers(sessionService.getUserId(session), userId)));
    }

    private boolean isMessageDetailsNotEmpty(final MessageDto messageDetails) {
        final var text = messageDetails.messageText();
        return nonNull(text) && !text.isEmpty() && nonNull(messageDetails.messageReceiverId());
    }

}
