package pl.polsl.rest.api.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.polsl.rest.api.dto.LoggedInDto;
import pl.polsl.rest.api.dto.MessageDto;
import pl.polsl.rest.api.dto.MessageSentDto;
import pl.polsl.rest.api.service.HttpSessionService;
import pl.polsl.rest.api.service.MessageService;
import pl.polsl.rest.api.service.WebsocketService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

import static java.util.Objects.nonNull;

@RestController()
@RequestMapping("/api/test")
public class TestController {

    public TestController() {
    }

    @GetMapping("")
    ResponseEntity<?> handleGetMessages() {
        return ResponseEntity.ok("Hi!");
    }


}
