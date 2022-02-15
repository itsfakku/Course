package com.btec.cms.service.impl;

import com.btec.cms.dto.CategoryDto;
import com.btec.cms.entity.CategoryEntity;
import com.btec.cms.exception.ResourceAlreadyExistsException;
import com.btec.cms.exception.ResourceDeleteException;
import com.btec.cms.exception.ResourceNotFoundException;
import com.btec.cms.repository.CategoryRepository;
import com.btec.cms.repository.CourseRepository;
import com.btec.cms.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

  private final CategoryRepository categoryRepository;
  private final CourseRepository courseRepository;
  private final ModelMapper modelMapper;
  private static final Logger log = LoggerFactory.getLogger(CategoryServiceImpl.class);

  @Autowired
  public CategoryServiceImpl(
      CategoryRepository categoryRepository,
      CourseRepository courseRepository,
      ModelMapper modelMapper) {
    this.categoryRepository = categoryRepository;
    this.courseRepository = courseRepository;
    this.modelMapper = modelMapper;
  }

  @Override
  public List<CategoryDto> getAllCategories() {
    return categoryRepository.findAll().stream()
        .map(category -> modelMapper.map(category, CategoryDto.class))
        .collect(Collectors.toList());
  }

  @Override
  public CategoryDto createCategory(CategoryDto categoryDto) {
    log.info("Create new category");

    // check blank name
    if (isBlankName(categoryDto.getName()))
      throw new IllegalArgumentException("Category name can not be empty");

    CategoryEntity categoryEntity = modelMapper.map(categoryDto, CategoryEntity.class);
    if (categoryRepository.existsByName(categoryEntity.getName())) {
      throw new ResourceAlreadyExistsException("Category name", categoryDto.getName());
    }
    categoryEntity.setName(categoryDto.getName());

    CategoryEntity createdCategory = categoryRepository.save(categoryEntity);
    return modelMapper.map(createdCategory, CategoryDto.class);
  }

  @Override
  public CategoryDto updateCategory(CategoryDto categoryDto, Long id) {
    Instant instant = Instant.now();
    CategoryEntity categoryEntity =
        categoryRepository
            .findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Not found category with id: " + id));

    // check blank name
    if (isBlankName(categoryDto.getName()))
      throw new IllegalArgumentException("Category name can not be empty");

    if (categoryRepository.existsByName(categoryDto.getName())) {
      throw new ResourceAlreadyExistsException("Category name", categoryDto.getName());
    }
    categoryEntity.setName(categoryDto.getName());
    categoryEntity.setDescription(categoryDto.getDescription());
    categoryEntity.setModifiedDate(Date.from(instant));

    final CategoryEntity updatedCategory = categoryRepository.save(categoryEntity);
    log.info("Category with id {} has been updated", id);
    return modelMapper.map(updatedCategory, CategoryDto.class);
  }

  @Override
  public void deleteCategory(Long id) {

    Optional<CategoryEntity> categoryEntity = categoryRepository.findById(id);
    if (!categoryEntity.isPresent())
      throw new ResourceNotFoundException("Not found category with id: " + id);

    Long categoryId = categoryEntity.get().getId();
    if (courseRepository.getTotalCountByCategoryId(categoryId) > 0)
      throw new ResourceDeleteException("Category contains 1 or more courses cannot delete");

    categoryRepository.delete(categoryEntity.get());
  }

  @Override
  public long totalCategories() {

    long total = categoryRepository.count();

    log.info("Total amount of category: {}", total);
    return total;
  }

  public CategoryDto getCategoryById(Long id) {

    log.info("Get category detail information by id");

    return categoryRepository
        .findById(id)
        .map(category -> modelMapper.map(category, CategoryDto.class))
        .orElseThrow(() -> new ResourceNotFoundException("Not found category with id: " + id));
  }

  private boolean isBlankName(String name) {
    return name == null || name.trim().isEmpty();
  }
}
