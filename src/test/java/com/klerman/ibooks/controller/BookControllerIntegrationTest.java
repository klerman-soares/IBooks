package com.klerman.ibooks.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.klerman.ibooks.data.entity.Author;
import com.klerman.ibooks.data.entity.Book;
import com.klerman.ibooks.data.entity.Category;
import com.klerman.ibooks.service.AuthorService;
import com.klerman.ibooks.service.BookService;
import com.klerman.ibooks.service.CategoryService;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc(secure = false)
@TestPropertySource(properties = 
		 "spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration")
@ActiveProfiles("test")
public class BookControllerIntegrationTest {
	
	public static final String VIEWS_BOOK_EDIT_FORM = "book-edit";
	public static final String VIEWS_BOOK_LIST = "book-list";
	
	private static final long TEST_BOOK_ID = 1L;
	private static final String TEST_BOOK_NAME = "Book Name";
	private static final String TEST_CATEGORY_NAME = "Category Name";
	private static final String TEST_AUTHOR_NAME = "Author Name";
	private static final String TEST_PUBLICATION_DATE_STR = "20/02/2019";
	
	@Autowired
	BookController bookController;
	
	@Autowired
	private BookService bookService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	AuthorService authorService;
	
	@Autowired
    private MockMvc mockMvc;
	
	Category category;
	Author author;
	LocalDate dateNow;
	
	@Before
	public void setup() {
		category = categoryService.saveCategory(new Category(TEST_CATEGORY_NAME));
		author = authorService.saveAuthor(new Author(TEST_AUTHOR_NAME));
		dateNow = LocalDate.of(2019, 02, 20);
	}
		
	@Test
	public void testBookList() throws Exception {
		this.mockMvc.perform(get("/book/"))
			.andExpect(model().attributeExists("page"))
			.andExpect(status().isOk())
        	.andExpect(view().name(VIEWS_BOOK_LIST));		
	}
	
	@Test
	public void testBookEditFormWithId() throws Exception {
		Book book = new Book(TEST_BOOK_NAME, category, author, dateNow);
		Book newBook = bookService.save(book);
		
		this.mockMvc
			.perform(
					get("/book/edit/{id}", newBook.getId())
			)
			.andExpect(status().isOk())
			.andExpect(model().attribute("book", newBook))
			.andExpect(view().name(VIEWS_BOOK_EDIT_FORM));
	}
	
	@Test
	public void testBookEditFormNoId() throws Exception {
		this.mockMvc.perform(get("/book/edit"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("book", new Book()))
    	.andExpect(view().name(VIEWS_BOOK_EDIT_FORM));
	}
	
	@Test
	public void testBookSaveSuccess() throws Exception {		
		this.mockMvc
			.perform(
				post("/book/edit")
					.param("id", String.valueOf(TEST_BOOK_ID))
					.param("name", TEST_BOOK_NAME)
					.param("authorList", author.getId().toString())
					.param("category", category.getId().toString())
					.param("publicationDate", TEST_PUBLICATION_DATE_STR)
					.sessionAttr("book", new Book())
				)
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/book/list"));
	}
	
	@Test
	public void testBookSaveWithErrors() throws Exception {	
		this.mockMvc
			.perform(
				post("/book/edit")
					.param("id", String.valueOf(TEST_BOOK_ID))
					.sessionAttr("book", new Book())
				)
			.andExpect(model().attributeHasErrors("book"))
			.andExpect(model().attributeHasFieldErrors("book", "name"))
			.andExpect(model().attributeHasFieldErrors("book", "category"))
			.andExpect(model().attributeHasFieldErrors("book", "authorList"))
			.andExpect(model().attributeHasFieldErrors("book", "publicationDate"))
			.andExpect(status().isOk())
			.andExpect(view().name(VIEWS_BOOK_EDIT_FORM));
	}
	
	@Test
	public void testBookSaveWithNoCategory() throws Exception {		
		this.mockMvc
			.perform(
				post("/book/edit")
					.param("id", String.valueOf(TEST_BOOK_ID))
					.param("name", TEST_BOOK_NAME)
					.param("authorList", "1")
					.param("publicationDate", TEST_PUBLICATION_DATE_STR)
					.sessionAttr("book", new Book())
				)
			.andExpect(model().attributeHasErrors("book"))
			.andExpect(model().attributeHasFieldErrors("book", "category"))
			.andExpect(status().isOk())
			.andExpect(view().name(VIEWS_BOOK_EDIT_FORM));
	}
	
	@Test
	public void testBookDelete() throws Exception {
		Book book = new Book(TEST_BOOK_NAME, category, author, dateNow);
		Book newBook = bookService.save(book);
		
		this.mockMvc
			.perform(
				get("/book/delete/{id}", newBook.getId())
			)
			.andExpect(status().isFound())
			.andExpect(redirectedUrl("/book/list"));
	}
}
