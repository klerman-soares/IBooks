package com.klerman.ibooks.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.klerman.ibooks.data.entity.Author;

public interface AuthorService {
	
	Page<Author> findAll (Pageable pageable);
	
	List<Author> findAll ();
	
	Author findOne(Long id);
	
	Author saveAuthor(Author author);
	
	void deleteAuthor(Long id);

}
