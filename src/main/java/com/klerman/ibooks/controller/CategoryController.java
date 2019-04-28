package com.klerman.ibooks.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.klerman.ibooks.data.entity.Category;
import com.klerman.ibooks.service.CategoryService;
import com.klerman.ibooks.util.PageWrapper;

@Controller
@RequestMapping("/category")
public class CategoryController {
	
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping (value= {"/","/list"})
	@PreAuthorize("hasRole('ROLE_USER')")
	public String categoryList (Model model, Pageable pageable) {
		PageWrapper<Category> page = new PageWrapper<Category> (categoryService.findAll(pageable), "/category/list");
		model.addAttribute("page", page);		

		return "category-list";
	}
	
	@RequestMapping (value= {"/edit", "/edit/{id}"}, method = RequestMethod.GET)
	public String  categoryEditForm(Model model, @PathVariable(required=false, name="id") Long id) {
		if (null != id) {
            model.addAttribute("category", categoryService.findOne(id));
        } else {
            model.addAttribute("category", new Category());
        }
		return "category-edit";
	}
	
	@RequestMapping (value="/edit", method = RequestMethod.POST)
	public String categorySave(Model model, Category category) {
		categoryService.saveCategory(category);
		//model.addAttribute("categories", categoryService.findAll());
		return "category-list";
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String categoryDelete(Model model, @PathVariable(required = true, name = "id") Long id, Pageable pageable) {
		categoryService.deleteCategory(id);
		
		PageWrapper<Category> page = new PageWrapper<Category> (categoryService.findAll(pageable), "/category/list");
		model.addAttribute("page", page);
		
		return "category-list";
	}
	
	public String handleErrors() {
		return "error";
	}

}
