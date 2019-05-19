package com.klerman.ibooks.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.klerman.ibooks.data.entity.Category;
import com.klerman.ibooks.service.CategoryService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
@TestPropertySource(properties = 
		 "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration")
@ActiveProfiles("test")
public class CategoryControllerIntegrationTest {
	
	private static final Long TEST_CATEGORY_ID = 1L;
	private static final String TEST_CATEGORY_NAME = "Test Category Name";
	
	@Autowired
	CategoryController categoryController;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Test
	public void testCategoryList() throws Exception {
		this.mockMvc.perform(get("/category/"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("page"))
        	.andExpect(view().name(CategoryController.VIEWS_LIST));		
	}
	
	@Test
	public void testCategoryEditFormSuccess() throws Exception {
		Category category = new Category(TEST_CATEGORY_NAME);		
		Category newCategory = categoryService.saveCategory(category);
		
		this.mockMvc
			.perform(
					get("/category/edit/{id}", category.getId())
			)
			.andExpect(status().isOk())
			.andExpect(model().attribute("category", newCategory))
			.andExpect(view().name(CategoryController.VIEWS_EDIT_FORM));
	}

	
	@Test
	public void testCategoryEditFormNoId() throws Exception {		
		this.mockMvc
			.perform(get("/category/edit/"))
			.andExpect(status().isOk())
			.andExpect(model().attribute("category", new Category()))
			.andExpect(view().name(CategoryController.VIEWS_EDIT_FORM));
	}
	
	@Test
	public void testCategorySaveSuccess() throws Exception {		
		this.mockMvc
			.perform(
				post("/category/edit")
					.param("id", String.valueOf(TEST_CATEGORY_ID))
					.param("name", TEST_CATEGORY_NAME)
				)
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/category/list"));
	}
	
	@Test
	public void testCategorySaveHasErrors() throws Exception {		
		this.mockMvc
			.perform(
				post("/category/edit")
					.param("id", String.valueOf(TEST_CATEGORY_ID))
				)
			.andExpect(model().attributeHasErrors("category"))
			.andExpect(model().attributeHasFieldErrors("category", "name"))
			.andExpect(status().isOk())
			.andExpect(view().name(CategoryController.VIEWS_EDIT_FORM));
	}
	
	@Test
	public void testCategoryDelete() throws Exception {
		Category category = new Category(TEST_CATEGORY_NAME);		
		Category newCategory = categoryService.saveCategory(category);
		
		this.mockMvc
			.perform(
				get("/category/delete/{id}", newCategory.getId())
			)
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/category/list"));
	}
	
	@Test
	public void testCategoryDeleteIdNotFound() throws Exception {		
		this.mockMvc
			.perform(
				get("/category/delete/{id}", 1)
			)
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/category/list"));
	}
}
