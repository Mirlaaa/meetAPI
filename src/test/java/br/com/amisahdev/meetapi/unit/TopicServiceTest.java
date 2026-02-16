package br.com.amisahdev.meetapi.unit;


import br.com.amisahdev.meetapi.dto.request.TopicRequest;
import br.com.amisahdev.meetapi.dto.response.TopicResponse;
import br.com.amisahdev.meetapi.exception.TopicDoesNotExistsException;
import br.com.amisahdev.meetapi.mapper.TopicMapper;
import br.com.amisahdev.meetapi.model.TopicEntity;
import br.com.amisahdev.meetapi.repository.TopicRepository;
import br.com.amisahdev.meetapi.service.TopicService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TopicServiceTest {

    @Mock
    private TopicRepository topicRepository;

    @Mock
    private TopicMapper topicMapper;

    @InjectMocks
    private TopicService topicService;

    private TopicEntity topicEntity;
    private TopicResponse topicResponse;
    private UUID topicId;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);

        topicId = UUID.randomUUID();
        topicEntity = TopicEntity.builder()
                .id(topicId)
                .title("java")
                .active(true)
                .build();

        topicResponse = new TopicResponse(
                topicId,
                "java"
        );
    }

    @Test
    void shouldFindAllTopics() {
        when(topicRepository.findAll()).thenReturn(List.of(topicEntity));
        when(topicMapper.map(topicEntity)).thenReturn(topicResponse);

        List<TopicResponse> result = topicService.findAll();

        assertEquals(1, result.size());
        assertEquals(topicResponse, result.get(0));
        verify(topicRepository, times(1)).findAll();
        verify(topicMapper, times(1)).map(topicEntity);
    }

    @Test
    void shouldFindByIdWhenExists() {
        when(topicRepository.findById(topicId)).thenReturn(Optional.of(topicEntity));
        when(topicMapper.map(topicEntity)).thenReturn(topicResponse);

        TopicResponse result = topicService.findById(topicId);

        assertEquals(topicResponse, result);
        verify(topicRepository, times(1)).findById(topicId);
    }

    @Test
    void shouldThrowExceptionWhenFindByIdNotExists() {
        when(topicRepository.findById(topicId)).thenReturn(Optional.empty());

        assertThrows(TopicDoesNotExistsException.class, () -> topicService.findById(topicId));
        verify(topicRepository, times(1)).findById(topicId);
    }

    @Test
    void shouldFindByTitleWhenExists() {
        when(topicRepository.findByTitle("java")).thenReturn(Optional.of(topicEntity));
        when(topicMapper.map(topicEntity)).thenReturn(topicResponse);

        Optional<TopicResponse> result = topicService.findByTitle("java");

        assertTrue(result.isPresent());
        assertEquals(topicResponse, result.get());
        verify(topicRepository, times(1)).findByTitle("java");
    }

    @Test
    void shouldSaveNewTopic() {
        TopicRequest request = new TopicRequest("Spring Boot");

        TopicEntity newTopicEntity = TopicEntity.builder()
                .title("spring boot")
                .active(true)
                .build();

        TopicResponse newTopicResponse = new TopicResponse(UUID.randomUUID(), "Spring boot");

        when(topicRepository.findByTitleEqualsIgnoreCase("spring boot")).thenReturn(Optional.empty());
        when(topicRepository.save(any(TopicEntity.class))).thenReturn(newTopicEntity);
        when(topicMapper.map(newTopicEntity)).thenReturn(newTopicResponse);

        TopicResponse result = topicService.save(request);

        assertEquals(newTopicResponse, result);
        verify(topicRepository, times(1)).save(any(TopicEntity.class));
    }

    @Test
    void shouldReturnExistingTopicOnSaveIfExists() {
        TopicRequest request = new TopicRequest("java");

        when(topicRepository.findByTitleEqualsIgnoreCase("java")).thenReturn(Optional.of(topicEntity));
        when(topicMapper.map(topicEntity)).thenReturn(topicResponse);

        TopicResponse result = topicService.save(request);

        assertEquals(topicResponse, result);
        verify(topicRepository, never()).save(any());
    }

    @Test
    void shouldUpdateTopicWhenExists() {
        TopicRequest request = new TopicRequest("JAVA UPDATED");
        TopicEntity updatedEntity = TopicEntity.builder()
                .id(topicId)
                .title("java updated")
                .active(true)
                .build();
        TopicResponse updatedResponse = new TopicResponse(topicId, "java updated");

        when(topicRepository.findByTitleEqualsIgnoreCase("java updated")).thenReturn(Optional.of(topicEntity));
        when(topicRepository.save(topicEntity)).thenReturn(updatedEntity);
        when(topicMapper.map(updatedEntity)).thenReturn(updatedResponse);

        TopicResponse result = topicService.update(topicId, request);

        assertEquals(updatedResponse, result);
        verify(topicRepository, times(1)).save(topicEntity);
    }

    @Test
    void shouldThrowExceptionOnUpdateIfNotExists() {
        TopicRequest request = new TopicRequest("Nonexistent");

        when(topicRepository.findByTitleEqualsIgnoreCase("nonexistent")).thenReturn(Optional.empty());

        assertThrows(TopicDoesNotExistsException.class, () -> topicService.update(topicId, request));
        verify(topicRepository, never()).save(any());
    }
}
