package com.klerman.ibooks.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.klerman.ibooks.data.entity.Author;
import com.klerman.ibooks.data.repository.AuthorRepository;

@Service
public class AuthorServiceImpl implements AuthorService {
	
	@Autowired
	private AuthorRepository authorRepository;

	@Override
	public Page<Author> findAll(Pageable pageable) {		
		return authorRepository.findAll(pageable);
	}
	
	@Override
	public List<Author> findAll() {
		List<Author> list = new ArrayList<>();
		authorRepository.findAll().forEach(author -> {
			list.add(author);
		});
		return list;
	}

	@Override
	public Author findOne(Long id) {
		return authorRepository.findById(id).orElse(null);
	}

	@Override
	public Author saveAuthor(Author author) {
		return authorRepository.save(author);
	}

	@Override
	public void deleteAuthor(Long id) {
		authorRepository.deleteById(id);
	}

}
