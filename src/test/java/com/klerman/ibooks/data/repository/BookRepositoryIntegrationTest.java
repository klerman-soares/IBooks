package com.klerman.ibooks.data.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.time.LocalDate;
import java.util.ArrayList;

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
	
	private static final String BOOK_NAME = "Book Name";
	String CATEGORY_NAME = "Category Name";
	String CATEGORY_NAME2 = "Category Name 2";
	String AUTHOR_NAME = "Author Name";
	String AUTHOR_NAME2 = "Author Name 2";
	
	LocalDate dateNow = LocalDate.of(2019, 02, 20);
	
	@Before
	public void setup() throws Exception {
		// setup data scenario
		Category category = new Category(CATEGORY_NAME);
		entityManager.persist(category);		
		Author newAuthor = new Author(AUTHOR_NAME);
		entityManager.persist(newAuthor);		
		Author newAuthor2 = new Author(AUTHOR_NAME2);
		entityManager.persist(newAuthor2);
		Book aNewBook = new Book(BOOK_NAME, category, newAuthor, dateNow);
		
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
		Category bookCategory = categoryRepository.findByName(CATEGORY_NAME);
		Author bookAuthor = authorRepository.findByName(AUTHOR_NAME);
		
		Book aNewBook = new Book(BOOK_NAME, bookCategory, bookAuthor, dateNow);
		
		Book savedBook = bookRepository.save(aNewBook);
		assertThat("Saving a new Book", aNewBook, is(equalTo(savedBook)));
	}
	
	@Test
	public void testUpdateBookName () {
		String newName = "NewName";
		Book book = bookRepository.findByName(BOOK_NAME);
		book.setName(newName);
		Book savedBook = bookRepository.save(book);
		assertThat(newName, is(equalTo(savedBook.getName())));
	}
	
	@Test
	public void testUpdateBookCategory () {
		Book book = bookRepository.findByName(BOOK_NAME);
		Category newCategory = new Category(CATEGORY_NAME2);
		book.setCategory(newCategory);
		Book savedBook =book = bookRepository.save(book);
		assertThat(newCategory, is(equalTo(savedBook.getCategory())));
	}
	
	@Test
	public void testUpdateAuthorList () {
		Book book = bookRepository.findByName(BOOK_NAME);
		Author author = authorRepository.findByName(AUTHOR_NAME2);
		book.getAuthorList().add(author);
		Book savedBook = bookRepository.save(book);	
		
		assertTrue(savedBook.getAuthorList().contains(author));		
	}
	
	@Test
	public void testDeleteBook() {
		Book book = bookRepository.findByName(BOOK_NAME);
		bookRepository.delete(book);
		Book deletedBook = bookRepository.findByName(BOOK_NAME);
		assertNull("This book was supposed to be deleted", deletedBook);
	}
     
	
	@Test
    public void testFindByName() {
        Book foundBook = bookRepository.findByName(BOOK_NAME);        
        assertThat(foundBook.getName(), is(equalTo(BOOK_NAME)));
    }
}