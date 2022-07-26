package dung.spring.webbanhang.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dung.spring.webbanhang.entity.Category;
import dung.spring.webbanhang.repository.CategoryRepo;

@Controller
@RequestMapping("/category")
public class CategoryController {
	@Autowired
	CategoryRepo categoryRepo;

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("category", new Category());
		return "category/create";
	}

	@PostMapping("/create")
	public String create(@ModelAttribute("category") @Valid Category category, BindingResult bindingresult) {
		if (bindingresult.hasErrors()) {
			return "category/create";
		}
		categoryRepo.save(category);
		return "redirect:/category/search";
	}
	
	@GetMapping("/update")
	public String update(@RequestParam("id") int id, Model model) {
		Category category = categoryRepo.getById(id);
		model.addAttribute("category", category);
		return "category/update";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute Category category) {
		Category OldOne = categoryRepo.getById(category.getId());
		OldOne.setName(category.getName());
		categoryRepo.save(OldOne);
		return "redirect:/category/search";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("id") int id) {
		categoryRepo.deleteById(id);
		return "redirect:/category/search";
	}

	
	
	
	@GetMapping("/search")
	public String search(Model model, @RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "size", required = false) Integer size) {
			
		for(int i = 5; i <= 15; i=i+4) {
			
		}
		if (size == null)
			size = 3; // max records per page
		if (page == null)
			page = 0; // trang hien tai

		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

		if (name != null && !name.isEmpty()) {
			Page<Category> pageCategory = categoryRepo.search("%" + name + "%", pageable);
			model.addAttribute("categorylist", pageCategory.toList());
			model.addAttribute("totalPage", pageCategory.getTotalPages());

//ở đây có 3 page 0,1,2
		} else if (id != null) {

			Category category = categoryRepo.findById(id).orElse(null);
			if (category != null) {
				model.addAttribute("categorylist", Arrays.asList(category));
				model.addAttribute("totalPage", 0);
				 } else//
					 model.addAttribute("totalPage", 0);

			} else {

				// if(s==null) {
				Page<Category> pageCategory = categoryRepo.findAll(pageable);
				model.addAttribute("categorylist", pageCategory.toList());
				model.addAttribute("totalPage", pageCategory.getTotalPages());
//ở đây có 4 page 0,1,2,3,4
			}
			model.addAttribute("page", page);
			model.addAttribute("name", name == null ? "" : name);
			model.addAttribute("size", size);
			model.addAttribute("id", id == null ? "" : id);
			return "category/search";

		}
	
	

	
	}

