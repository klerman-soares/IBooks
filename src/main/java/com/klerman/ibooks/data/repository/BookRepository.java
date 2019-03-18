package com.klerman.ibooks.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.klerman.ibooks.data.entity.Book;

@Repository
public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
	
	public Book findByName(String name);

}
