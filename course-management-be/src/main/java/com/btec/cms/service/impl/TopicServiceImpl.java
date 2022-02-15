package com.btec.cms.service.impl;

import com.btec.cms.common.BaseConstants;
import com.btec.cms.dto.TopicDto;
import com.btec.cms.dto.TopicResponse;
import com.btec.cms.dto.UserDto;
import com.btec.cms.entity.TopicEntity;
import com.btec.cms.entity.UserEntity;
import com.btec.cms.exception.ResourceAlreadyExistsException;
import com.btec.cms.exception.ResourceDeleteException;
import com.btec.cms.exception.ResourceNotFoundException;
import com.btec.cms.repository.CourseRepository;
import com.btec.cms.repository.TopicRepository;
import com.btec.cms.repository.UserRepository;
import com.btec.cms.service.TopicService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TopicServiceImpl implements TopicService {

  private final TopicRepository topicRepository;
  private final UserRepository userRepository;
  private final CourseRepository courseRepository;
  private final ModelMapper modelMapper;
  private static final Logger log = LoggerFactory.getLogger(TopicServiceImpl.class);

  @Autowired
  public TopicServiceImpl(
      TopicRepository topicRepository,
      UserRepository userRepository,
      CourseRepository courseRepository,
      ModelMapper modelMapper) {
    this.topicRepository = topicRepository;
    this.userRepository = userRepository;
    this.courseRepository = courseRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<TopicResponse> getAllTopics() {
    log.info("Get all topics");
    return topicRepository.findAll().stream()
        .map(topicEntity -> modelMapper.map(topicEntity, TopicResponse.class))
        .collect(Collectors.toList());
  }

  @Override
  public long totalTopics() {
    long total = topicRepository.count();
    log.info("Total number of topics: {}", total);
    return total;
  }

  @Override
  @Transactional
  public TopicResponse createTopic(TopicDto topicDto) {
    log.info("Create new topic");

    // check blank name
    if (isBlankName(topicDto.getName()))
      throw new ResourceNotFoundException("Topic name can not be empty");

    // check exist topic
    if (topicRepository.existsByName(topicDto.getName()))
      throw new ResourceAlreadyExistsException("Topic name", topicDto.getName());
    // valid teachers is not empty
    if (topicDto.getTeacherIds().isEmpty())
      throw new ResourceNotFoundException("Teacher can not be empty");

    // get teacher by id return set
    Set<UserEntity> teachers =
        topicDto.getTeacherIds().stream()
            .map(
                id ->
                    userRepository
                        .findByIdAndRoleId(id, BaseConstants.ROLE_ID_TEACHER)
                        .orElseThrow(
                            () ->
                                new ResourceNotFoundException("Not found teacher with id: " + id)))
            .collect(Collectors.toSet());

    TopicEntity topic = modelMapper.map(topicDto, TopicEntity.class);

    TopicEntity createdTopic = topicRepository.save(topic);
    log.info("Created Topic No Teacher {}", createdTopic);
    createdTopic.setUsers(teachers);

    createdTopic = topicRepository.save(topic);
    log.info("Created Topic {}", createdTopic);
    return modelMapper.map(createdTopic, TopicResponse.class);
  }

  @Override
  public TopicDto findTopicById(Long id) {
    log.info("Get topic by id {}", id);

    return topicRepository
        .findById(id)
        .map(
            topic -> {
              TopicDto topicDto = modelMapper.map(topic, TopicDto.class);
              topicDto.setTeacherIds(
                  topic.getUsers().stream().map(UserEntity::getId).collect(Collectors.toSet()));
              return topicDto;
            })
        .orElseThrow(() -> new ResourceNotFoundException("Not found topic with id " + id));
  }

  @Override
  public void deleteTopic(Long id) {
    log.info("Delete topic with id {}", id);
    Optional<TopicEntity> topic = topicRepository.findById(id);
    // check exist topic
    if (!topic.isPresent()) throw new ResourceNotFoundException("Not found topic with id " + id);

    Long topicId = topic.get().getId();

    // validate course is empty
    if (courseRepository.getTotalCountByTopicId(topicId) > 0)
      throw new ResourceDeleteException("Topic contains 1 or more courses cannot delete");

    // validate user is empty
    if (userRepository.getTotalCountByTopicId(id) > 0)
      throw new ResourceDeleteException("Topic contains 1 or more users cannot delete");

    topicRepository.delete(topic.get());
  }

  @Override
  public TopicResponse updateTopic(TopicDto topicDto, Long id) {
    log.info("Update topic");
    Instant instant = Instant.now();

    // check exist topic
    TopicEntity topic =
        topicRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Not found topic with id: " + id));

    // valid teachers is not empty
    if (topicDto.getTeacherIds().isEmpty())
      throw new ResourceNotFoundException("Teacher can not be empty");

    // get teacher by id return set
    Set<UserEntity> teachers =
        topicDto.getTeacherIds().stream()
            .map(
                teacherId ->
                    userRepository
                        .findByIdAndRoleId(teacherId, BaseConstants.ROLE_ID_TEACHER)
                        .orElseThrow(
                            () ->
                                new ResourceNotFoundException(
                                    "Not found teacher with id: " + teacherId)))
            .collect(Collectors.toSet());

    topic.setUsers(teachers);
    topic.setDescription(topicDto.getDescription());
    topic.setModifiedDate(Date.from(instant));

    TopicEntity updatedTopic = topicRepository.save(topic);

    log.info("Updated topic {}", updatedTopic);

    return modelMapper.map(updatedTopic, TopicResponse.class);
  }

  @Override
  public List<UserDto> getTeacherInTopic(Long topicId) {
    log.info("Get teachers in topic");
    if (!topicRepository.findById(topicId).isPresent())
      throw new ResourceNotFoundException("Not found topic with id: " + topicId);

    List<UserDto> teacherList =
        userRepository.getUserByTopic(topicId).stream()
            .map(userEntity -> modelMapper.map(userEntity, UserDto.class))
            .collect(Collectors.toList());
    if (teacherList.isEmpty()) throw new ResourceNotFoundException("Not found teacher in topic");
    return teacherList;
  }

  private boolean isBlankName(String name) {
    return name == null || name.trim().isEmpty();
  }
}
