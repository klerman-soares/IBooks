package com.klerman.ibooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.klerman.ibooks.data.entity.Author;
import com.klerman.ibooks.service.AuthorService;
import com.klerman.ibooks.util.PageWrapper;

@Controller
@RequestMapping (value="/author")
public class AuthorController {
	
	@Autowired
	AuthorService authorService;
	
	@RequestMapping (value= {"/", "/list"})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String categoryList(Model model, Pageable pageable) {
		PageWrapper<Author> page = new PageWrapper<Author> (authorService.findAll(pageable), "/author/list");
		model.addAttribute("page", page);		
		
		
		Page<Author> pages = authorService.findAll(pageable);
		
		model.addAttribute("authorList", pages.getContent());
		return "author-list";
	}

}
