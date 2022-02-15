package com.btec.cms.api;

import com.btec.cms.dto.CategoryDto;
import com.btec.cms.exception.ResourceAlreadyExistsException;
import com.btec.cms.exception.ResourceDeleteException;
import com.btec.cms.exception.ResourceNotFoundException;
import com.btec.cms.service.CategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
@CrossOrigin(origins = "*")
public class CategoryApi {
  @Autowired CategoryService categoryService;
  private static final Logger log = LoggerFactory.getLogger(CategoryApi.class);

  /**
   * API to get all categories <br>
   * Link: <code>/categories</code> <br>
   * Method: GET
   *
   * @return {@link List<CategoryDto>} list of categories
   */
  @GetMapping()
  public ResponseEntity<?> getAllCategories() {
    log.info("Get all categories");
    return ResponseEntity.ok(categoryService.getAllCategories());
  }

  /**
   * API to create new category <br>
   * Link: <code>/categories</code> <br>
   * Method: POST
   *
   * @param categoryDto new category information
   * @return {@link CategoryDto} new created category
   * @exception ResourceAlreadyExistsException existed category name
   */
  @PostMapping()
  public ResponseEntity<?> createCategory(@RequestBody CategoryDto categoryDto) {
    return ResponseEntity.ok(categoryService.createCategory(categoryDto));
  }

  /**
   * API to update category <br>
   * Link: <code>/categories/{id}</code> <br>
   * Method: PUT
   *
   * @param categoryDto new category information
   * @param id category id
   * @return {@link CategoryDto} updated category
   * @exception ResourceNotFoundException not found category with id
   * @exception ResourceAlreadyExistsException existed category name
   */
  @PutMapping("/{id}")
  public ResponseEntity<?> updatedCategory(
      @RequestBody CategoryDto categoryDto, @PathVariable(name = "id") Long id) {
    log.info("Updated category with id: {}", id);
    return ResponseEntity.ok(categoryService.updateCategory(categoryDto, id));
  }

  /**
   * API to delete category <br>
   * Link: <code>/categories/{id}</code> <br>
   * Method: DELETE
   *
   * @param id category id
   * @exception ResourceNotFoundException not found category with id
   * @exception ResourceDeleteException if category has at least 1 course
   */
  @DeleteMapping("/{id}")
  public void deleteCategory(@PathVariable(name = "id") Long id) {
    log.info("Delete category with id : {}", id);
    categoryService.deleteCategory(id);
  }

  /**
   * API to get total categories<br>
   * Link: <code>/categories/total</code> <br>
   * Method: GET
   *
   * @return long value total amount of categories
   */
  @GetMapping("/total")
  public long totalCategories() {
    log.info("Get total amount of categories");
    return categoryService.totalCategories();
  }
  /**
   * Get category by id
   *
   * @param id category id
   * @return {@link CategoryDto} category found detail information
   * @exception ResourceNotFoundException if not found category
   */
  @GetMapping("/{id}")
  public ResponseEntity<?> getCategoryById(@PathVariable Long id) {
    log.info("Get detail information of category {}", id);
    return ResponseEntity.ok(categoryService.getCategoryById(id));
  }
}
