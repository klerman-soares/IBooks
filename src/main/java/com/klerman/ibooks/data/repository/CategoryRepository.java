package com.klerman.ibooks.data.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.klerman.ibooks.data.entity.Category;

@Repository
public interface CategoryRepository extends PagingAndSortingRepository<Category, Long> {
	
	public Category findByName(String name);

}
