package com.klerman.ibooks.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.klerman.ibooks.data.entity.Category;
import com.klerman.ibooks.service.CategoryService;
import com.klerman.ibooks.util.PageWrapper;

@Controller
@RequestMapping("/category")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CategoryController {
	
	public static final String VIEWS_EDIT_FORM = "category-edit";
	public static final String VIEWS_LIST = "category-list";
	
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping (value= {"/","/list"})	
	public String categoryList (
			Model model, 
			@PageableDefault(
					size=PageWrapper.MAX_PAGE_ITEM_DISPLAY,
					sort={"id"}, 
					direction=Direction.DESC) Pageable pageable
	) {
		PageWrapper<Category> page = new PageWrapper<Category> (categoryService.findAll(pageable), "/category/list");
		model.addAttribute("page", page);		

		return VIEWS_LIST;
	}
	
	@RequestMapping (value= {"/edit", "/edit/{id}"}, method = RequestMethod.GET)
	public String  categoryEditForm(Model model, @PathVariable(required=false, name="id") Long id) {
		if (null != id) {
            model.addAttribute("category", categoryService.findOne(id));
        } else {
            model.addAttribute("category", new Category());
        }
		return VIEWS_EDIT_FORM;
	}
	
	@RequestMapping (value="/edit", method = RequestMethod.POST)
	public String categorySave(
			Model model, 
			@Valid Category category,
			BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {			
			return VIEWS_EDIT_FORM;
		} else {
			categoryService.saveCategory(category);
			return "redirect:/category/list";
		}		
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String categoryDelete(Model model, @PathVariable(required = true, name = "id") Long id, Pageable pageable) {
		categoryService.deleteCategory(id);		
		return "redirect:/category/list";
	}
	
	public String handleErrors() {
		return "error";
	}

}
