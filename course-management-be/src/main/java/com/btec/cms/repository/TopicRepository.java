package com.btec.cms.repository;

import com.btec.cms.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<TopicEntity, Long> {

  /**
   * Check exists topic
   *
   * @param name topic name
   * @return if topic exist or not
   */
  boolean existsByName(String name);

  /**
   * Find topic by name
   *
   * @param name topic name
   * @return {@link TopicEntity} topic found
   */
  TopicEntity findByName(String name);
}
