package id.co.cimbniaga.financialproductmanagement.service;

import id.co.cimbniaga.financialproductmanagement.dto.MessageRequestDTO;
import id.co.cimbniaga.financialproductmanagement.model.Messages;
import id.co.cimbniaga.financialproductmanagement.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Messages create(MessageRequestDTO messageRequestDTO){
        Messages messages = new Messages();
        messages.setDetail(messageRequestDTO.getDetail());
        messages.setActivityType(messageRequestDTO.getActivityType());
        return messageRepository.save(messages);
    }

    public Messages updateById(long id, MessageRequestDTO messageRequestDTO){
        Optional<Messages> newData = messageRepository.findById(id);
        if(newData.isPresent()) {
            Messages messages = newData.get();
            messages.setActivityType(messageRequestDTO.getActivityType());
            messages.setDetail(messageRequestDTO.getDetail());
            return messageRepository.save(messages);
        }
        return null;
    }
}
