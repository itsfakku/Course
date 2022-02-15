package com.btec.cms.service;

import com.btec.cms.dto.CategoryDto;
import com.btec.cms.exception.ResourceAlreadyExistsException;
import com.btec.cms.exception.ResourceDeleteException;
import com.btec.cms.exception.ResourceNotFoundException;

import java.util.List;

public interface CategoryService {

  /**
   * Get all categories
   *
   * @return {@link List<CategoryDto>} list categories
   */
  List<CategoryDto> getAllCategories();

  /**
   * Create new category
   *
   * @param categoryDto new category information
   * @return {@link CategoryDto} created category
   * @exception ResourceAlreadyExistsException existed category name
   */
  CategoryDto createCategory(CategoryDto categoryDto);

  /**
   * Update category information
   *
   * @param categoryDto new category information
   * @param id category id
   * @return {@link CategoryDto} updated category
   * @exception ResourceAlreadyExistsException existed category name
   * @exception ResourceNotFoundException category not existed
   */
  CategoryDto updateCategory(CategoryDto categoryDto, Long id);

  /**
   * Delete category by id
   *
   * @param id category id
   * @exception ResourceNotFoundException not found category
   * @exception ResourceDeleteException if category has at least 1 course
   */
  void deleteCategory(Long id);

  /**
   * Get total amount of categories
   *
   * @return {@link Integer} total amount of categories
   */
  long totalCategories();

  /**
   * Get category by id
   *
   * @param id category id
   * @return {@link CategoryDto} found category detail information
   * @exception ResourceNotFoundException if not found category
   */
  CategoryDto getCategoryById(Long id);
}
