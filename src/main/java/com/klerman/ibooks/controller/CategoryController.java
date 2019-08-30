package com.klerman.ibooks.controller;

import com.klerman.ibooks.data.entity.Category;
import com.klerman.ibooks.service.CategoryService;
import com.klerman.ibooks.util.PageWrapper;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/category")
@PreAuthorize("hasRole('ROLE_ADMIN')")
public class CategoryController {

    public static final String VIEW_EDIT_FORM = "category-edit";
    public static final String VIEW_LIST = "category-list";
    public static final String REDIRECT_VIEW_LIST = "redirect:/category/list";
    public static final String VIEW_ERROR = "error";
	
	@Value("${msg.category.save.success}") 
	String msgSaveSuccess;
	
	@Value("${msg.category.delete.success}") 
	String msgDeleteSuccess;
	
	@Autowired
	CategoryService categoryService;
	
	@RequestMapping (value= {"/","/list"})	
	public String categoryList (
			Model model, 
			@PageableDefault (
					size=PageWrapper.MAX_PAGE_ITEM_DISPLAY,
					sort={"id"}, 
					direction=Direction.DESC) Pageable pageable
	) {
		PageWrapper<Category> page = new PageWrapper<Category> (categoryService.findAll(pageable), "/category/list");
		model.addAttribute("page", page);		

		return VIEW_LIST;
	}
	
	@RequestMapping (value= {"/edit", "/edit/{id}"}, method = RequestMethod.GET)
	public String  categoryEditForm(Model model, @PathVariable(required=false, name="id") Long id) {
		if (null != id) {
            model.addAttribute("category", categoryService.findOne(id));
        } else {
            model.addAttribute("category", new Category());
        }
		return VIEW_EDIT_FORM;
	}
	
	@RequestMapping (value="/edit", method = RequestMethod.POST)
	public String categorySave(
			Model model, 
			@Valid Category category,
			BindingResult bindingResult,
			RedirectAttributes redirectAttributes) {
		if (bindingResult.hasErrors()) {			
			return VIEW_EDIT_FORM;
		} else {
			categoryService.saveCategory(category);
			redirectAttributes.addFlashAttribute("msgOperationResult",msgSaveSuccess);
			return REDIRECT_VIEW_LIST;
		}		
	}
	
	@RequestMapping(value="/delete/{id}", method = RequestMethod.GET)
	public String categoryDelete (
			Model model, 
			@PathVariable(required = true, name = "id") Long id, 
			RedirectAttributes redirectAttributes) {
		categoryService.deleteCategory(id);
		redirectAttributes.addFlashAttribute("msgOperationResult", msgDeleteSuccess);
		return REDIRECT_VIEW_LIST;
	}

	public String handleErrors() {
		return VIEW_ERROR;
	}

}
