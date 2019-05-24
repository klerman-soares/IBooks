package com.klerman.ibooks.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.time.LocalDate;
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
public class BookControllerUnitTest {
	
	private static final long TEST_BOOK_ID = 1L;
	private static final String TEST_BOOK_NAME = "Book Name";
	private static final String TEST_CATEGORY_NAME = "Category Name";
	private static final String TEST_AUTHOR_NAME = "Author Name";
	private static final String TEST_PUBLICATION_DATE_STR = "20/02/2019";
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BookService bookService;
	
	@Autowired
	CategoryService categoryService;
	
	@Autowired
	AuthorService authorService;
	
	@InjectMocks
	private BookController bookController;
	
	Category category;
	Author author;
	LocalDate dateNow;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		
		category = categoryService.saveCategory(new Category(TEST_CATEGORY_NAME));
		author = authorService.saveAuthor(new Author(TEST_AUTHOR_NAME));
		dateNow = LocalDate.of(2019, 02, 20);
	}
	
	@Test
	public void testBookList() throws Exception {
		Book mockBook = new Book(TEST_BOOK_NAME, category, author, dateNow);
		List<Book> books = new ArrayList<>();
		books.add(mockBook);
		Page<Book> page = new PageImpl<Book>(books);
		given(bookService.findAll(any(Pageable.class))).willReturn(page);
		
		this.mockMvc.perform(get("/book/"))
			.andExpect(model().attributeExists("page"))
			.andExpect(status().isOk())
        	.andExpect(view().name(BookController.VIEW_LIST));		
	}
	
	@Test
	public void testBookEditFormWithId() throws Exception {
		Book mockBook = new Book(TEST_BOOK_NAME, category, author, dateNow);

		given(bookService.findOne(any(Long.class))).willReturn(mockBook);
		this.mockMvc
			.perform(
					get("/book/edit/{id}", TEST_BOOK_ID)
			)
			.andExpect(status().isOk())
			.andExpect(model().attribute("book", mockBook))
			.andExpect(view().name(BookController.VIEW_EDIT_FORM));
	}
	
	@Test
	public void testBookEditFormNoId() throws Exception {
		this.mockMvc.perform(get("/book/edit"))
		.andExpect(status().isOk())
		.andExpect(model().attribute("book", new Book()))
    	.andExpect(view().name(BookController.VIEW_EDIT_FORM));
	}
	
	@Test
	public void testSaveBookHappyPath() throws Exception {
		// setup mock Book returned the mock service component
		Book mockBook = new Book(TEST_BOOK_NAME, category, author, dateNow);
		given(bookService.save(any(Book.class))).willReturn(mockBook);
		
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
		.andExpect(view().name(BookController.REDIRECT_VIEW_LIST));
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
			.andExpect(view().name(BookController.VIEW_EDIT_FORM));
	}
}
