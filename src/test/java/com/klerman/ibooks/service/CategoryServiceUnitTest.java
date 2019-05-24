package com.klerman.ibooks.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import com.klerman.ibooks.data.entity.Category;
import com.klerman.ibooks.data.repository.CategoryRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class CategoryServiceUnitTest {
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@InjectMocks
	private CategoryServiceImpl categoryService;
		
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	private static final long CATEGORY_ID = 1L;
	private static final String CATEGORY_NAME = "Category Name";	
	
	@Test
	public void testFindAll () {
		Category category = new Category(CATEGORY_NAME);
		
		List<Category> categoryList = new ArrayList<>();
		categoryList.add(category);
		Page<Category> page = new PageImpl<>(categoryList);
		PageRequest pageRequest = PageRequest.of(0,2);
		
		when(this.categoryRepository.findAll(pageRequest)).thenReturn(page);
		Page<Category> pageResult =  categoryService.findAll(pageRequest);
		assertEquals(pageResult.getNumberOfElements(), page.getNumberOfElements());
	}
	
	@Test
	public void testSaveBook() {
		Category categoryMock = new Category(CATEGORY_NAME);
		
		when(categoryRepository.save(any(Category.class))).thenReturn(categoryMock);
		
		Category newCategory = categoryService.saveCategory(new Category());
		
		assertEquals(CATEGORY_NAME, newCategory.getName());
	}
	
	@Test
	public void testFindCategory() {
		Category category = new Category(CATEGORY_NAME);
		Optional<Category> mockCategory = Optional.of(category);
		
		when(categoryRepository.findById(any(Long.class))).thenReturn(mockCategory);
		
		Category newCategory = categoryService.findOne(CATEGORY_ID);
		assertEquals(CATEGORY_NAME, newCategory.getName());
	}
}
