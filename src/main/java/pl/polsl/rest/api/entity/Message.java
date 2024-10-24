package pl.polsl.rest.api.entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.OptionalInt;

//TODO: 6
@Data
@Entity
@Table(name = "message")
public class Message {

    public Message(){}
    public Message(OptionalInt id, String content, int receiverID, int senderID)
    {
        if(id.isPresent())
            this.id = id.getAsInt();

        this.content = content;
        this.receiverID = receiverID;
        this.senderID = senderID;
    }

    @Id
    @GeneratedValue
    @Column(name = "message_id")
    private int id;

    @Column(name = "content", unique = false)
    private String content;

    @Column(name = "receiver_id", unique = false)
    private int receiverID;

    @Column(name = "sender_id", unique = false)
    private int senderID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public int getReceiverID() {
        return receiverID;
    }

    public void setReceiverID(int receiverID) {
        this.receiverID = receiverID;
    }

    public int getSenderID() {
        return senderID;
    }

    public void setSenderID(int senderID) {
        this.senderID = senderID;
    }
}
