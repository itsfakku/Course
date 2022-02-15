package com.btec.cms.service;

import com.btec.cms.dto.TopicDto;
import com.btec.cms.dto.TopicResponse;
import com.btec.cms.dto.UserDto;
import com.btec.cms.exception.ResourceAlreadyExistsException;
import com.btec.cms.exception.ResourceDeleteException;
import com.btec.cms.exception.ResourceNotFoundException;

import java.util.List;

public interface TopicService {

  /**
   * Get all topics
   *
   * @return {@link List<TopicDto>} list of topics l
   */
  List<TopicResponse> getAllTopics();

  /**
   * Get total number of topics
   *
   * @return total number of topics
   */
  long totalTopics();

  /**
   * Create new topic
   *
   * @param topicDto new topic detail information
   * @return {@link TopicResponse} new topic
   * @exception ResourceAlreadyExistsException if topic name existed
   * @exception ResourceNotFoundException if not found any teacher
   */
  TopicResponse createTopic(TopicDto topicDto);

  /**
   * Find topic by id
   *
   * @param id topic id
   * @return {@link TopicDto} topic found
   * @exception ResourceNotFoundException not found topic
   */
  TopicDto findTopicById(Long id);

  /**
   * Delete topic by id
   *
   * @param id topic id
   * @exception ResourceNotFoundException not found topic
   * @exception ResourceDeleteException if topic has at least 1 course or users
   */
  void deleteTopic(Long id);

  /**
   * Update topic detail information
   *
   * @param topicDto topic new information
   * @param id topic id
   * @return {@link TopicResponse} updated topic
   * @exception ResourceNotFoundException if not found topic
   * @exception ResourceNotFoundException if not found any teacher
   */
  TopicResponse updateTopic(TopicDto topicDto, Long id);

  /**
   * Get teacher in topic
   *
   * @param topicId topic id
   * @return {@link List< UserDto >} List teachers in topic
   * @exception ResourceNotFoundException not found topic
   * @exception ResourceNotFoundException not found teacher in topic
   */
  List<UserDto> getTeacherInTopic(Long topicId);
}
