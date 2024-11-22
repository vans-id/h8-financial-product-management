package id.co.cimbniaga.financialproductmanagement.controller;


import id.co.cimbniaga.financialproductmanagement.dto.MessageRequestDTO;
import id.co.cimbniaga.financialproductmanagement.model.Messages;
import id.co.cimbniaga.financialproductmanagement.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth/messages")
public class MessageController {

    @Autowired
    private MessageService messageService;


    @PostMapping
    public ResponseEntity<?> create(@RequestBody MessageRequestDTO messageRequestDTO){
        try{
            Messages messages = messageService.create(messageRequestDTO);
            return ResponseEntity.ok(messages);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @PostMapping("/{id}")
    public  ResponseEntity<?>updateById(@PathVariable Long id, @RequestBody MessageRequestDTO messageRequestDTO){
        try {
            Messages messages = messageService.updateById(id,messageRequestDTO);
            return ResponseEntity.status(HttpStatus.OK).body(messages);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}
