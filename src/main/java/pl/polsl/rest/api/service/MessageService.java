package pl.polsl.rest.api.service;

import lombok.SneakyThrows;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import pl.polsl.rest.api.dto.DataDto;
import pl.polsl.rest.api.dto.MessageDto;

import pl.polsl.rest.api.dto.MessageSentDto;
import pl.polsl.rest.api.dto.MessagesDto;
import pl.polsl.rest.api.entity.Message;
import pl.polsl.rest.api.entity.User;
import pl.polsl.rest.api.repository.MessageRepository;
import pl.polsl.rest.api.repository.UserRepository;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Stream;

@Service
public class MessageService {

    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final WebsocketService websocketService;

    private final HttpSessionService sessionService;

    public MessageService(
                          final UserRepository userRepository,
                          final MessageRepository messageRepository,
                          final WebsocketService websocketService,
                          final HttpSessionService sessionService) {
        this.userRepository = userRepository;
        this.websocketService = websocketService;
        this.sessionService = sessionService;
        this.messageRepository = messageRepository;
    }

    // TODO: 11
    @SneakyThrows
    public Optional<MessageDto> sendMessage(final MessageDto dto, final HttpSession session) {

        var mess =messageRepository.save(new Message(OptionalInt.empty(), dto.messageText(), dto.messageReceiverId(), sessionService.getUserId(session)));
        if(mess.getId()!=0)
            return Optional.of(mapToDto(mess));

        return Optional.empty();
    }

    // TODO: 9
    public List<MessageDto> getMessagesBetweenUsers(final int user1, final int user2) {
        final List<MessageDto> dtoList = new ArrayList<>();
        List<Message> messages = messageRepository.findMessagesBySenderIDOrReceiverID(user1,user2);
        for(Message m : messages)
        {
            dtoList.add(mapToDto(m));
        }
        return dtoList;
    }

    // TODO: 8
    private MessageDto mapToDto(Message m) {

        return new MessageDto(m.getId(), m.getSenderID(), m.getReceiverID(), m.getContent());
    }

}
