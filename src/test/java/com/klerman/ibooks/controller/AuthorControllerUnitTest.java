package com.klerman.ibooks.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
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

import com.klerman.ibooks.data.entity.Author;
import com.klerman.ibooks.service.AuthorService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
@TestPropertySource(properties = 
		 "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration")
@ActiveProfiles("test")
public class AuthorControllerUnitTest {
	
	private static final String TEST_AUTHOR_NAME = "Author Name";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AuthorService authorService;
	
	@InjectMocks
	private AuthorController authorController;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testAuthorList() throws Exception {
		Author mockAuthor = new Author(TEST_AUTHOR_NAME);
		List<Author> authors = new ArrayList<>();
		authors.add(mockAuthor);
		Page<Author> page = new PageImpl<Author>(authors);
		given(authorService.findAll(any(Pageable.class))).willReturn(page);
		
		this.mockMvc.perform(get("/author/"))
			.andExpect(model().attributeExists("page"))
			.andExpect(status().isOk())
        	.andExpect(view().name(AuthorController.VIEW_LIST));		
	}
}
