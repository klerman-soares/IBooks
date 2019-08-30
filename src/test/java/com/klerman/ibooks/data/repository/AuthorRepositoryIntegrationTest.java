package com.klerman.ibooks.data.repository;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

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

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace=Replace.NONE)
public class AuthorRepositoryIntegrationTest {
	
	private static final String AUTHOR_NAME = "Author Name";
	private static final String AUTHOR_NEW_NAME = "Author New Name";
	
	@Autowired
    private TestEntityManager entityManager;

	@Autowired
	private AuthorRepository auhtorRepository;

	@Before
	public void setup() throws Exception {
		// setup data scenario
		Author author = new Author(AUTHOR_NAME);
		entityManager.persist(author);
	}
	
	@Test
	public void testFindAllCategories () {
		ArrayList<Author> list = new ArrayList<>();
		auhtorRepository.findAll().forEach(author -> {
			list.add(author);
		});
		assertThat(1, is(list.size()));
	}
	
	@Test
	public void testAddAuthor () {
		Author author = new Author(AUTHOR_NAME);
		
		Author savedAuthor = auhtorRepository.save(author);
		assertThat(author, is(equalTo(savedAuthor)));
	}
	
	@Test
	public void testUpdateAuthorName () {
		Author author = auhtorRepository.findByName(AUTHOR_NAME);
		author.setName(AUTHOR_NEW_NAME);
		Author savedAuthor = auhtorRepository.save(author);
		assertThat(AUTHOR_NEW_NAME, is(equalTo(savedAuthor.getName())));
	}
	
	@Test
	public void testDeleteAuthor() {
		Author author = auhtorRepository.findByName(AUTHOR_NAME);
		auhtorRepository.delete(author);
		Author deletedAuthor = auhtorRepository.findByName(AUTHOR_NAME);
		assertNull("This author was supposed to be deleted", deletedAuthor);
	}     
	
	@Test
    public void testFindByName() {
		Author foundAuthor = auhtorRepository.findByName(AUTHOR_NAME);        
        assertThat(foundAuthor.getName(), is(equalTo(AUTHOR_NAME)));
    }

}
