package com.klerman.ibooks.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.klerman.ibooks.data.entity.Category;

public interface CategoryService {

	Page<Category> findAll(Pageable pageable);

	Category findOne(Long id);

	Category saveCategory(Category category);

	void deleteCategory(Long id);

}