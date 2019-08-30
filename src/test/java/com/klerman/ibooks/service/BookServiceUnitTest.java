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

import com.klerman.ibooks.data.entity.Book;
import com.klerman.ibooks.data.repository.BookRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class BookServiceUnitTest {
	
	@Mock
	private BookRepository bookRepository;
	
	@InjectMocks
	private BookServiceImpl bookService;
		
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	private static final String BOOK_NAME = "Java: How to program";
	private static final long BOOK_ID = 1L;
	
	@Test
	public void testFindAllBooks () {
		Book book = new Book();
		book.setName(BOOK_NAME);
		
		List<Book> bookList = new ArrayList<>();
		bookList.add(book);
		Page<Book> page = new PageImpl<>(bookList);
		PageRequest pageRequest = PageRequest.of(0,2);
		
		when(this.bookRepository.findAll(pageRequest)).thenReturn(page);
		Page<Book> pageResult =  this.bookService.findAll(pageRequest);
		assertEquals(pageResult.getNumberOfElements(), page.getNumberOfElements());
	}
	
	@Test
	public void testSaveBook() {
		Book mockBook = new Book();
		mockBook.setName(BOOK_NAME);
		
		when(bookRepository.save(any(Book.class))).thenReturn(mockBook);
		
		// Save the book
		Book newBook = bookService.save(new Book());
		
		assertEquals(BOOK_NAME, newBook.getName());
	}
	
	@Test
	public void testFindBook() {
		Book book = new Book();
		book.setName(BOOK_NAME);
		Optional<Book> mockBook = Optional.of(book);
		
		when(bookRepository.findById(any(Long.class))).thenReturn(mockBook);
		
		Book newBook = bookService.findOne(BOOK_ID);
		assertEquals(BOOK_NAME, newBook.getName());
	}
}
