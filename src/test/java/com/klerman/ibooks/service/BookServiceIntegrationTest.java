package com.klerman.ibooks.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.junit.Before;
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

import com.klerman.ibooks.data.entity.Author;
import com.klerman.ibooks.data.entity.Book;
import com.klerman.ibooks.data.entity.Category;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ActiveProfiles("test")
public class BookServiceIntegrationTest {
	
	@Autowired
	private BookService bookService;

	@Autowired
	CategoryService categoryService;

	@Autowired
	AuthorService authorService;
	
	Category category;
	Author author;
	LocalDate dateNow;
	 
	@Before
	public void init() {
		category = categoryService.saveCategory(new Category("Computers & Technology"));
		author = authorService.saveAuthor(new Author("James Patterson"));
		dateNow = LocalDate.of(2019, 02, 20);
	}
	
	@Test
	public void saveBookHappyPathTest() {
		String bookName = "Book Name";
		Book book = new Book(bookName, category, author, dateNow);
		Book newBook = bookService.save(book);

		assertNotNull(newBook);
		assertNotNull(newBook.getId());
		assertEquals(bookName, newBook.getName());
	}
	
	@Test (expected = ConstraintViolationException.class)
	public void saveBookWithNoCategoryTest() {
		String bookName = "Head First";
		Book book = new Book();
		book.setName(bookName);
		Set<Author> authorList = new HashSet<Author>();
		authorList.add(author);
		book.setAuthorList(authorList);
		book.setPublicationDate(dateNow);
		bookService.save(book);
	}
	
	@Test (expected = ConstraintViolationException.class)
	public void saveBookWithNoAuthorListTest() {
		String bookName = "Head First";
		Book book = new Book();
		book.setName(bookName);
		book.setCategory(category);
		book.setPublicationDate(dateNow);
		bookService.save(book);
	}
	
	@Test (expected = ConstraintViolationException.class)
	public void saveBookWithEmptyAuthorListTest() {
		String bookName = "Head First";
		Book book = new Book();
		book.setName(bookName);
		book.setCategory(category);
		Set<Author> authorList = new HashSet<Author>();
		book.setAuthorList(authorList);
		book.setPublicationDate(dateNow);
		bookService.save(book);
	}
	
	@Test (expected = ConstraintViolationException.class)
	public void saveBookWithNoNameTest() {
		Book book = new Book();
		book.setCategory(category);
		book.setPublicationDate(dateNow);
		bookService.save(book);
	}

	@Test
	public void findAllBooksTest() {
		// Book
		bookService.save(new Book("Testing Java Microservices", category, author, dateNow));
		bookService.save(new Book("Algorithms Illuminated", category, author, dateNow));
		
		Pageable pageable = PageRequest.of(0, 2);
		Page<Book> page = bookService.findAll(pageable);
		assertEquals("List of books", 2, page.getTotalElements());
	}
	
	@Test
	public void findOneTest () {
		// Book
		Book book = bookService.save(new Book("Testing Java Microservices", category, author, dateNow));
		long bookId =  book.getId();
		Book foundBook = bookService.findOne(bookId);
		assertNotNull (foundBook);
		assertEquals (bookId, foundBook.getId());
	}
	
	@Test
	public void deleteBookTest() {
		// Book
		Book book = bookService.save(new Book("Testing Java Microservices", category, author, dateNow));
		long bookId =  book.getId();
		bookService.deleteBook(bookId);
		Book foundBook = bookService.findOne(bookId);
		assertNull(foundBook);
	}

}
