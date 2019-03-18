package com.klerman.ibooks.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.klerman.ibooks.data.entity.Author;

public interface AuthorService {
	
	Page<Author> findAll (Pageable pageable);
	
	Author findOne(Long id);
	
	Author saveAuthor(Author author);
	
	void deleteAuthor(Long id);

}
