package nl.quintor.StreamingTest.controller;

import nl.quintor.StreamingTest.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/messages")
public class MessageController {

    @Value(value = "${kafka.topicname}")
    private String topicName;

    @Autowired
    private MessageService messageService;

    @GetMapping("/send/{message}")
    public String sendMessage(@PathVariable("message") String message, @RequestParam("amount") int amount) {
        messageService.sendMessages(topicName, message, amount);
        return "Message send {" + message + "}";
    }

    @GetMapping("/retrieve")
    public String retrieveMessage() throws InterruptedException {
         messageService.retrieveMessage();
         return "OK";
    }


}
