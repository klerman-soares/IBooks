package com.klerman.ibooks.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import com.klerman.ibooks.data.entity.Category;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ActiveProfiles("test")
public class CategoryServiceIntegrationTest {
	
	private static final String TEST_CATEGORY_NAME = "Test Category Name";
	
	@Autowired
	CategoryService categoryService;
	
	@Test
	public void saveCategoryHappyPathTest() {
		Category category = new Category(TEST_CATEGORY_NAME);
		Category newCategory = categoryService.saveCategory(category);

		assertNotNull(newCategory);
		assertNotNull(newCategory.getId());
		assertEquals(TEST_CATEGORY_NAME, newCategory.getName());
	}
	
	@Test (expected = TransactionSystemException.class)
	public void saveBookWithNoCategoryTest() {
		Category category = new Category();
		categoryService.saveCategory(category);
	}
	
	@Test
	public void findAllBooksTest() {
		categoryService.saveCategory(new Category(TEST_CATEGORY_NAME));
		categoryService.saveCategory(new Category(TEST_CATEGORY_NAME));
		
		Pageable pageable = PageRequest.of(0, 2);
		Page<Category> page = categoryService.findAll(pageable);
		assertEquals("List of categories", 2, page.getTotalElements());
	}
	
	@Test
	public void findOneTest () {
		Category newCategory = categoryService.saveCategory(new Category(TEST_CATEGORY_NAME));
		long categoryId =  newCategory.getId();
		Category foundcategory = categoryService.findOne(categoryId);
		assertNotNull (foundcategory);
		assertEquals(foundcategory, newCategory);
	}
	
	@Test
	public void deleteBookTest() {
		Category newCategory = categoryService.saveCategory(new Category(TEST_CATEGORY_NAME));
		long categoryId =  newCategory.getId();
		categoryService.deleteCategory(categoryId);
		Category foundcategory = categoryService.findOne(categoryId);
		assertNull(foundcategory);
	}
}
