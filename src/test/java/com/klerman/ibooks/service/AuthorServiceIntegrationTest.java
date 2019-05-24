package com.klerman.ibooks.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import javax.validation.ConstraintViolationException;

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

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
@ActiveProfiles("test")
public class AuthorServiceIntegrationTest {
	
	private static final String TEST_AUTHOR_NAME = "Test Author Name";
	
	@Autowired
	AuthorService authoritService;
	
	@Test
	public void saveAuthorHappyPathTest() {
		Author author = new Author(TEST_AUTHOR_NAME);
		Author newAuthor = authoritService.saveAuthor(author);

		assertNotNull(newAuthor);
		assertNotNull(newAuthor.getId());
		assertEquals(TEST_AUTHOR_NAME, newAuthor.getName());
	}
	
	@Test (expected = ConstraintViolationException.class)
	public void saveAuthorWithNoCategoryTest() {
		Author author = new Author();
		authoritService.saveAuthor(author);
	}
	
	@Test
	public void findAllAuthorsTest() {
		authoritService.saveAuthor(new Author(TEST_AUTHOR_NAME));
		Pageable pageable = PageRequest.of(0, 2);
		Page<Author> page = authoritService.findAll(pageable);
		assertTrue(page.getTotalElements() > 0);
	}
	
	@Test
	public void findOneTest () {
		Author newAuthor = authoritService.saveAuthor(new Author(TEST_AUTHOR_NAME));
		long authorId =  newAuthor.getId();
		Author foundAuthor = authoritService.findOne(authorId);
		assertNotNull (foundAuthor);
		assertEquals(foundAuthor, newAuthor);
	}
	
	@Test
	public void deleteAuthorTest() {
		Author newAuthor = authoritService.saveAuthor(new Author(TEST_AUTHOR_NAME));
		long authorId =  newAuthor.getId();
		authoritService.deleteAuthor(authorId);
		Author foundAuthor = authoritService.findOne(authorId);
		assertNull(foundAuthor);
	}

}
