package nl.quintor.StreamingTest.service;

import nl.quintor.StreamingTest.Listener;
import nl.quintor.StreamingTest.repository.IMessageBroker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class MessageService {

    @Autowired
    @Qualifier("kafka")
    private IMessageBroker messageBroker;

    @Autowired
    private Listener listener;

    public void sendMessage(String topicName, String message) {
        messageBroker.sendMessage(topicName, message);
    }

    public void sendMessages(String topicName, String message, int amount) {
        for (int i = 0; i < amount; i++) {
            messageBroker.sendMessage(topicName, message + amount);
        }
    }

    public void retrieveMessage() throws InterruptedException {
        System.out.println(listener.countDownLatch1.await(60, TimeUnit.SECONDS));
    }

}
