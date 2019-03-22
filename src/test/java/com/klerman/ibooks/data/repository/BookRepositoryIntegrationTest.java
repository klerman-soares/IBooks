package com.klerman.ibooks.data.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import com.klerman.ibooks.data.entity.Author;
import com.klerman.ibooks.data.entity.Book;
import com.klerman.ibooks.data.entity.Category;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class BookRepositoryIntegrationTest {
	
	@Autowired
    private TestEntityManager entityManager;

	@Autowired
	private BookRepository bookRepository;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Autowired
	private AuthorRepository authorRepository;
	
	String bookName = "Java How to Program";
	String categoryName = "Software Development";	
	String authorName = "Stephen King";
	String authorName2 = "Agatha Christie";
	
	@Before // setup()
	public void before() throws Exception {
		// setup data scenario
		Category aNewCategory = new Category(categoryName);
		entityManager.persist(aNewCategory);
		
		Author newAuthor = new Author(authorName);
		entityManager.persist(newAuthor);
		
		Author newAuthor2 = new Author(authorName2);
		entityManager.persist(newAuthor2);

		// setup data scenario
		Book aNewBook = new Book();
		
		aNewBook.setName(bookName);
		aNewBook.setCategory(aNewCategory);
		aNewBook.getAuthorList().add(newAuthor);
		
		// save test data
		entityManager.persist(aNewBook);
	}
	
	@Test
	public void testFindAll () {
		ArrayList<Book> list = new ArrayList<>();
		bookRepository.findAll().forEach(book -> {
			list.add(book);
		});
		assertThat(1, is(list.size()));
	}
	
	@Test
	public void testAddBook () {
		Category bookCategory = categoryRepository.findByName(categoryName);
		Author bookAuthor = authorRepository.findByName(authorName);
		
		Set<Author> authorList = new HashSet<>();
		authorList.add( bookAuthor);
		
		Book aNewBook = new Book(bookName, bookCategory, authorList);
		
		Book savedBook = bookRepository.save(aNewBook);
		assertThat("Saving a new Book", aNewBook, is(equalTo(savedBook)));
	}
	
	@Test
	public void testUpdateBookName () {
		String newName = "NewName";
		Book book = bookRepository.findByName(bookName);
		book.setName(newName);
		Book savedBook = bookRepository.save(book);
		assertThat("", newName, is(equalTo(savedBook.getName())));
	}
	
	@Test
	public void testUpdateBookCategory () {
		Book book = bookRepository.findByName(bookName);
		Category newCategory = new Category("NewCategory");
		book.setCategory(newCategory);
		Book savedBook =book = bookRepository.save(book);
		assertThat("Updating book category", newCategory, is(equalTo(savedBook.getCategory())));
	}
	
	@Test
	public void testUpdateAuthorList () {
		Book book = bookRepository.findByName(bookName);
		Author author = authorRepository.findByName(authorName2);
		book.getAuthorList().add(author);
		Book savedBook = bookRepository.save(book);	
		
		assertTrue("", savedBook.getAuthorList().contains(author));		
	}
	
	@Test
	public void testDeleteBook() {
		Book book = bookRepository.findByName(bookName);
		bookRepository.delete(book);
		Book deletedBook = bookRepository.findByName(bookName);
		assertNull("This book was sopposed to be deleted", deletedBook);
	}
     
	
	@Test
    public void testFindByName() {
        // Find an inserted record
        Book foundBook = bookRepository.findByName(bookName);        
        assertThat(foundBook.getName(), is(equalTo(bookName)));
    }
}