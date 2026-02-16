package br.com.amisahdev.meetapi.service;

import br.com.amisahdev.meetapi.dto.request.TopicRequest;
import br.com.amisahdev.meetapi.dto.response.TopicResponse;
import br.com.amisahdev.meetapi.exception.TopicDoesNotExistsException;
import br.com.amisahdev.meetapi.mapper.TopicMapper;
import br.com.amisahdev.meetapi.model.TopicEntity;
import br.com.amisahdev.meetapi.repository.TopicRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Optional.of;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    public List<TopicResponse> findAll() {
        return of(topicRepository.findAll())
                .orElse(new ArrayList<>())
                .stream()
                .map(topicMapper::map).toList();

    }

    public TopicResponse findById(final UUID topicId) {
        return topicRepository.findById(topicId)
                .map(topicMapper::map)
                .orElseThrow(TopicDoesNotExistsException::new);
    }

    public Optional<TopicResponse> findByTitle(final String title) {
        return topicRepository.findByTitle((title))
                .map(topicMapper::map);
    }

    @Transactional
    public TopicResponse save(final TopicRequest topicRequest) {
        final String titleNormalized = topicRequest.title().toLowerCase();

        final Optional<TopicEntity> topicExists = topicRepository.findByTitleEqualsIgnoreCase(titleNormalized);

        if (topicExists.isPresent()) {
            return topicMapper.map(topicExists.get());
        }

        final TopicEntity topic = TopicEntity.builder()
                .title(titleNormalized)
                .active(true).build();

        return topicMapper.map(topicRepository.save(topic));
    }

    @Transactional
    public TopicEntity save(final String topicTitle) {
        final String titleNormalized = topicTitle.toLowerCase();

        final Optional<TopicEntity> topicExists = topicRepository.findByTitleEqualsIgnoreCase(titleNormalized);

        if (topicExists.isPresent()) {
            return topicExists.get();
        }

        final TopicEntity topic = TopicEntity.builder()
                .title(titleNormalized)
                .active(true).build();

        return topicRepository.save(topic);
    }

    @Transactional
    public TopicResponse update(final UUID topicId, final TopicRequest topicRequest) {
        final String titleNormalized = topicRequest.title().toLowerCase();
        final TopicEntity topicExists = topicRepository.findByTitleEqualsIgnoreCase(titleNormalized)
                .orElseThrow(TopicDoesNotExistsException::new);

        topicExists.setTitle(titleNormalized);
        return topicMapper.map(topicRepository.save(topicExists));
    }
}
