package com.btec.cms.repository;

import com.btec.cms.entity.CategoryEntity;
import com.btec.cms.entity.TopicEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

  /**
   * Check exists category
   *
   * @param name category name
   * @return if category exist or not
   */
  boolean existsByName(String name);

  /**
   * Find category by name
   *
   * @param name category name
   * @return {@link CategoryEntity} category found
   */
  CategoryEntity findByName(String name);
}
