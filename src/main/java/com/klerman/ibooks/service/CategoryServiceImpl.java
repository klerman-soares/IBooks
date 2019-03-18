package com.klerman.ibooks.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.klerman.ibooks.data.entity.Category;
import com.klerman.ibooks.data.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public Page<Category> findAll(Pageable pageable) {
		return categoryRepository.findAll(pageable);
	}

	@Override
	public Category findOne (Long id) {
		return categoryRepository.findById(id).orElse(null);
	}

	@Override
	public Category saveCategory(Category category) {
		return categoryRepository.save(category);
	}	

	@Override
	public void deleteCategory (Long id) {
		categoryRepository.deleteById(id);
	}
}
