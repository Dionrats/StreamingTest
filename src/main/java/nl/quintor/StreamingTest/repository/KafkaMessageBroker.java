package nl.quintor.StreamingTest.repository;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Repository;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import java.util.concurrent.CountDownLatch;


@Repository
@Qualifier("kafka")
public class KafkaMessageBroker implements IMessageBroker {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String GROUP_ID = "poc";


    public void sendMessage(String topicName, String msg) {
        ListenableFuture<SendResult<String, String>> future =
                kafkaTemplate.send(topicName, 0, "key", msg);

        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + msg +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + msg + "] due to : " + ex.getMessage());
            }
        });
    }

    @KafkaListener(id = "foo", topics = "${kafka.topicname}", groupId = "${kafka.groupId}", containerFactory = "kafkaListenerContainerFactory")
    public void listen(ConsumerRecord<?, ?> record) {
        System.out.println(record);
    }

//    @KafkaListener(topics = "ProofOfConcept", groupId = GROUP_ID)
//    public void listen( @Payload String message,
//                        @Header(KafkaHeaders.RECEIVED_PARTITION_ID) int partition) {
//        System.out.println("Received Message: " + message + " from partition: " + partition);
//    }
}
