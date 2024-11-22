package id.co.cimbniaga.financialproductmanagement.service;
import id.co.cimbniaga.financialproductmanagement.dto.MessageRequestDTO;
import id.co.cimbniaga.financialproductmanagement.model.Messages;
import id.co.cimbniaga.financialproductmanagement.repository.MessageRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class MessageServiceTest {

    @Autowired
    private MessageService messageService;

    @MockBean
    private MessageRepository messageRepository;

    private MessageRequestDTO messageRequestDTO;
    private Messages message;

    @BeforeEach
    void setUp() {
        // Initialize DTO and model objects for tests
        messageRequestDTO = new MessageRequestDTO();
        messageRequestDTO.setDetail("Test message detail");
        messageRequestDTO.setActivityType("Test activity");

        message = new Messages();
        message.setId(1L);
        message.setDetail("Test message detail");
        message.setActivityType("Test activity");
    }

    @Test
    void create_shouldReturnSavedMessage() {
        // Given
        when(messageRepository.save(any(Messages.class))).thenReturn(message);

        // When
        Messages result = messageService.create(messageRequestDTO);

        // Then
        assertNotNull(result);
        assertEquals("Test message detail", result.getDetail());
        assertEquals("Test activity", result.getActivityType());
        verify(messageRepository, times(1)).save(any(Messages.class));  // Verify save is called once
    }

    @Test
    void updateById_shouldUpdateAndReturnMessage() {
        // Given
        when(messageRepository.findById(1L)).thenReturn(Optional.of(message));
        when(messageRepository.save(any(Messages.class))).thenReturn(message);

        // When
        Messages updatedMessage = messageService.updateById(1L, messageRequestDTO);

        // Then
        assertNotNull(updatedMessage);
        assertEquals("Test message detail", updatedMessage.getDetail());
        assertEquals("Test activity", updatedMessage.getActivityType());
        verify(messageRepository, times(1)).findById(1L); // Verify findById is called once
        verify(messageRepository, times(1)).save(any(Messages.class));  // Verify save is called once
    }

    @Test
    void updateById_shouldReturnNullWhenMessageNotFound() {
        // Given
        when(messageRepository.findById(1L)).thenReturn(Optional.empty());

        // When
        Messages updatedMessage = messageService.updateById(1L, messageRequestDTO);

        // Then
        assertNull(updatedMessage);
        verify(messageRepository, times(1)).findById(1L);  // Verify findById is called once
        verify(messageRepository, times(0)).save(any(Messages.class));  // Verify save is not called
    }
}
