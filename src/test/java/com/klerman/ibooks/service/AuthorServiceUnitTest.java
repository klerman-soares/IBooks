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

import com.klerman.ibooks.data.entity.Author;
import com.klerman.ibooks.data.repository.AuthorRepository;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE)
public class AuthorServiceUnitTest {
	
	@Mock
	private AuthorRepository authorRepository;
	
	@InjectMocks
	private AuthorServiceImpl authorService;
		
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}
	
	private static final long AUTHOR_ID = 1L;
	private static final String AUTHOR_NAME = "Author Name";	
	
	@Test
	public void testFindAll () {
		Author author = new Author(AUTHOR_NAME);
		
		List<Author> authorList = new ArrayList<>();
		authorList.add(author);
		Page<Author> page = new PageImpl<>(authorList);
		PageRequest pageRequest = PageRequest.of(0,2);
		
		when(this.authorRepository.findAll(pageRequest)).thenReturn(page);
		Page<Author> pageResult =  authorService.findAll(pageRequest);
		assertEquals(pageResult.getNumberOfElements(), page.getNumberOfElements());
	}
	
	@Test
	public void testSaveAuthor() {
		Author authorMock = new Author(AUTHOR_NAME);		
		when(authorRepository.save(any(Author.class))).thenReturn(authorMock);		
		Author newAuthor = authorService.saveAuthor(new Author());
		
		assertEquals(AUTHOR_NAME, newAuthor.getName());
	}
	
	@Test
	public void testFindAuthor() {
		Author authorMock = new Author(AUTHOR_NAME);
		Optional<Author> mockAuthor = Optional.of(authorMock);
		
		when(authorRepository.findById(any(Long.class))).thenReturn(mockAuthor);
		
		Author newAuthor = authorService.findOne(AUTHOR_ID);
		assertEquals(AUTHOR_NAME, newAuthor.getName());
	}

}
