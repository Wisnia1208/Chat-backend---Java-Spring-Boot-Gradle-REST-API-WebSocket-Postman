package pl.polsl.rest.api.repository;//TODO: 7

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.polsl.rest.api.entity.Message;
import pl.polsl.rest.api.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface MessageRepository extends CrudRepository<Message, Integer> {

    List<Message> findMessagesBySenderIDOrReceiverID(final int senderID, final int receiverID);

}