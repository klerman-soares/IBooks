package com.klerman.ibooks.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
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
public class CategoryControllerUnitTest {
	
	private static final long TEST_CATEGORY_ID = 1L;
	private static final String TEST_CATEGORY_NAME = "Category Name";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private CategoryService categoryService;
	
	@InjectMocks
	private CategoryController categoryController;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testCategoryList() throws Exception {
		Category mockCategory = new Category(TEST_CATEGORY_NAME);
		List<Category > categories = new ArrayList<>();
		categories.add(mockCategory);
		Page<Category> page = new PageImpl<Category>(categories);
		given(categoryService.findAll(any(Pageable.class))).willReturn(page);
		
		this.mockMvc.perform(get("/category/"))
			.andExpect(model().attributeExists("page"))
			.andExpect(status().isOk())
        	.andExpect(view().name(CategoryController.VIEW_LIST));		
	}
	
	@Test
	public void testCategoryEditFormWithId() throws Exception {
		Category mockCategory = new Category(TEST_CATEGORY_NAME);

		given(categoryService.findOne(any(Long.class))).willReturn(mockCategory);
		this.mockMvc
			.perform(
					get("/category/edit/{id}", TEST_CATEGORY_ID)
			)
			.andExpect(status().isOk())
			.andExpect(model().attribute("category", mockCategory))
			.andExpect(view().name(CategoryController.VIEW_EDIT_FORM));
	}
	
	@Test
	public void testCategoryEditFormNoId() throws Exception {
		this.mockMvc.perform(get("/category/edit"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("category", new Category()))
    	.andExpect(view().name(CategoryController.VIEW_EDIT_FORM));
	}
	
	@Test
	public void testSaveCategoryHappyPath() throws Exception {
		// setup mock Category returned the mock service component
		Category mockCategory = new Category(TEST_CATEGORY_NAME);
		given(categoryService.saveCategory(any(Category.class))).willReturn(mockCategory);
		
		this.mockMvc
			.perform(
				post("/category/edit")
				.param("id", String.valueOf(TEST_CATEGORY_ID))
				.param("name", TEST_CATEGORY_NAME)
				.sessionAttr("category", new Category())
				)
		.andExpect(status().isFound())
		.andExpect(view().name(CategoryController.REDIRECT_VIEW_LIST));
	}
	
	@Test
	public void testCategorySaveWithErrors() throws Exception {		
		this.mockMvc
			.perform(
				post("/category/edit")
					.param("id", String.valueOf(TEST_CATEGORY_ID))
					.sessionAttr("category", new Category())
				)
			.andExpect(model().attributeHasErrors("category"))
			.andExpect(model().attributeHasFieldErrors("category", "name"))
			.andExpect(status().isOk())
			.andExpect(view().name(CategoryController.VIEW_EDIT_FORM));
	}

}
