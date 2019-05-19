package com.klerman.ibooks.controller;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import com.klerman.ibooks.service.BookService;
import com.klerman.ibooks.controller.BookController;
import com.klerman.ibooks.data.entity.Book;


@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerUnitTest {
	/*
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private BookService bookService;
	
	@InjectMocks
	private OneController bookController;
	
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);	
	}
	
	@Test
	public void testAddBookHappyPath() throws Exception {
		// setup mock Book returned the mock service component
		Book mockBook = new Book("Java How to program");
		
		when(bookService.addBook(any(Book.class)))
			.thenReturn(mockBook);
		
		// simulate the form bean that would POST from the web page
		Book aBook = new Book("Spring Unit Test");
		
		// simulate the form submit (POST)
		mockMvc
			.perform(get("/books/addBook", aBook))
			.andExpect(status().isOk())
			.andReturn();
	}
	
	@Test
	public void testAddBookBizServiceRuleNotSatisfied() throws Exception {
		// setup a mock response of NULL object returned from the mock service component
		when(bookService.addBook(any(Book.class)))
		.thenReturn(null);
		
		// simulate the form bean that would POST from the web page
				Book aBook = new Book("Spring Unit Test");
		
		// simulate the form submit (POST)
		mockMvc
			.perform(get("/books/addBook", aBook))
			.andExpect(status().is(200))
			.andReturn();
	}
	*/
}
