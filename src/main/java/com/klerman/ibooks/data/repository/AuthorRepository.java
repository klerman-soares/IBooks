package com.klerman.ibooks.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.klerman.ibooks.data.entity.Author;

@Repository
public interface AuthorRepository extends PagingAndSortingRepository<Author, Long> {
	
	public Author findByName(String name);

}
