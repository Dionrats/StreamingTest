package nl.quintor.StreamingTest.repository;

import org.springframework.stereotype.Repository;

@Repository
public interface IMessageBroker {
    void sendMessage(String topicName, String msg);
   // void listen(String message);
}
