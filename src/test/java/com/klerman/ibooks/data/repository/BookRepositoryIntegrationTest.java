package com.klerman.ibooks.data.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

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
	
	@Test
    public void testFindByName() {
		
		// setup data scenario
		Category aNewCategory = new Category();
		String categoryName = "Software Development";
		aNewCategory.setName(categoryName);
		entityManager.persist(aNewCategory);
		
		String authorName = "Agatha Christie";
		Author newAuthor = new Author(authorName);
		entityManager.persist(newAuthor);
		
		String authorName2 = "Klerman Renan";
		Author newWriter2 = new Author(authorName2);
		entityManager.persist(newWriter2);
		
		// setup data scenario
		Book aNewBook = new Book();
		String name = "Java How to Program";
		aNewBook.setName(name);
		aNewBook.setCategory(aNewCategory);
		aNewBook.getAuthorList().add(newAuthor);
		aNewBook.getAuthorList().add(newWriter2);
		System.out.println(aNewBook.toString());
        
		// save test data
		entityManager.persist(aNewBook);

        // Find an inserted record
        Book foundBook = bookRepository.findByName(name);
        
        assertThat(foundBook.getName(), is(equalTo(name)));
    }
	
	@Test
	public void testBooksAndCategories() {
		Category cat1 = new Category("Programming Language");
		
		assertThat("", is(equalTo("")));
	}
}