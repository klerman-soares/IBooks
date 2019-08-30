package com.klerman.ibooks.controller;

import com.klerman.ibooks.data.entity.Author;
import com.klerman.ibooks.service.AuthorService;
import com.klerman.ibooks.util.PageWrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping (value="/author")
public class AuthorController {
	
	public static final String VIEW_EDIT_FORM = "author-edit";
	public static final String VIEW_LIST = "author-list";
	public static final String REDIRECT_VIEW_LIST = "redirect:/author/list";
	public static final String VIEW_ERROR = "error";
	
	@Autowired
	AuthorService authorService;
	
	@RequestMapping (value= {"/", "/list"})
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String categoryList(
			Model model, 
			@PageableDefault(
					size=PageWrapper.MAX_PAGE_ITEM_DISPLAY, 
					sort={"id"}, 
					direction=Direction.DESC) Pageable pageable) {
		PageWrapper<Author> page = new PageWrapper<Author> (authorService.findAll(pageable), "/author/list");
		model.addAttribute("page", page);		
		
		
		Page<Author> pages = authorService.findAll(pageable);
		
		model.addAttribute("authorList", pages.getContent());
		return "author-list";
	}
	
	public String handleErrors() {
		return "error";
	}
}
