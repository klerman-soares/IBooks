package com.klerman.ibooks.service;

import java.util.ArrayList;
import java.util.List;

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
	public List<Category> findAll() {
		List<Category> list = new ArrayList<>();
		categoryRepository.findAll().forEach(category -> {
			list.add(category);
		});
		return list;
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
