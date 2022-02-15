package com.btec.cms.api;

import com.btec.cms.dto.TopicDto;
import com.btec.cms.dto.UserDto;
import com.btec.cms.exception.ResourceAlreadyExistsException;
import com.btec.cms.exception.ResourceDeleteException;
import com.btec.cms.exception.ResourceNotFoundException;
import com.btec.cms.service.TopicService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/** API for Topic */
@RestController
@RequestMapping("/topics")
@CrossOrigin(origins = "*")
public class TopicApi {

  final TopicService topicService;
  private static final Logger log = LoggerFactory.getLogger(TopicApi.class);

  public TopicApi(TopicService topicService) {
    this.topicService = topicService;
  }

  /**
   * API to get all topics <br>
   * Link: <code>/topics/</code> <br>
   * Method: GET
   *
   * @return {@link List<TopicDto>} list of topics
   */
  @GetMapping()
  public ResponseEntity<?> getAllTopics() {
    return ResponseEntity.ok(topicService.getAllTopics());
  }

  /**
   * API to get total number of topics <br>
   * Link: <code>/topics/total</code> <br>
   * Method: GET
   *
   * @return total number of topics
   */
  @GetMapping("/total")
  public long getTotalTopics() {
    log.info("Get total number of topics");
    return topicService.totalTopics();
  }

  /**
   * API to create new topic <br>
   * Link: <code>/topics</code> <br>
   * Method: POST
   *
   * @param topicDto new topic detail information
   * @return {@link TopicDto} new topic
   * @exception ResourceAlreadyExistsException if topic name existed
   * @exception ResourceNotFoundException if teacher is empty
   */
  @PostMapping()
  public ResponseEntity<?> createTopic(@RequestBody TopicDto topicDto) {
    log.info("Create new topic");
    return ResponseEntity.ok(topicService.createTopic(topicDto));
  }

  /**
   * API to get topic by id <br>
   * Link: <code>/topics/{id}</code> <br>
   * Method: GET
   *
   * @param id topic id
   * @return {@link TopicDto} topic found
   * @exception ResourceNotFoundException if not found topic
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getTopicById(@PathVariable Long id) {
    log.info("Get topic by id");
    return ResponseEntity.ok(topicService.findTopicById(id));
  }
  /**
   * API to delete topic by id <br>
   * Link: <code>/topics/{id}</code> <br>
   * Method: DELETE
   *
   * @param id topic id
   * @exception ResourceNotFoundException not found topic with id
   * @exception ResourceDeleteException if topic has at least 1 course or users
   */
  @DeleteMapping("/{id}")
  public void deleteTopic(@PathVariable Long id) {
    log.info("Delete topic");
    topicService.deleteTopic(id);
  }

  /**
   * API to edit topic by id <br>
   * Link: <code>/topics/{id}</code> <br>
   * Method: PUT
   *
   * @param id topic id
   * @exception ResourceNotFoundException not found topic with id
   * @exception ResourceAlreadyExistsException if topic name existed
   * @exception ResourceDeleteException if topic has at least 1 course or users
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> updatedTopic(@RequestBody TopicDto topicDto, @PathVariable Long id) {
    log.info("Update topic with id: {}", id);
    return ResponseEntity.ok(topicService.updateTopic(topicDto, id));
  }

  /**
   * Get teachers in topic <br>
   * Link: <code>/topics/{id}/teacher</code> <br>
   * Method: GET
   *
   * @param id topic id
   * @return {@link List< UserDto >} list teacher in topic
   * @exception ResourceNotFoundException not found topic
   * @exception ResourceNotFoundException not found teacher in topic
   */
  @GetMapping("/{id}/teachers")
  public ResponseEntity<?> getUsersByTopic(@PathVariable Long id) {
    log.info("Get teacher in topic");
    return ResponseEntity.ok(topicService.getTeacherInTopic(id));
  }
}
