package com.klerman.ibooks.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.klerman.ibooks.data.entity.Book;

public interface BookService {
	
	Page<Book> findAll(Pageable pageable);
	
	Book findOne(Long id);
	
	Book save(Book book);
	
	void deleteBook(Long id);

}
