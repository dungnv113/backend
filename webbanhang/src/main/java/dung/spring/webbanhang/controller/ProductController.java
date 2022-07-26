package dung.spring.webbanhang.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
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
import org.springframework.web.multipart.MultipartFile;

import dung.spring.webbanhang.entity.Category;
import dung.spring.webbanhang.entity.Product;
import dung.spring.webbanhang.repository.CategoryRepo;
import dung.spring.webbanhang.repository.ProductRepo;

@Controller
@RequestMapping("/product")
public class ProductController {
	@Autowired
	ProductRepo productRepo;
	@Autowired
	CategoryRepo categoryRepo;

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("product", new Product());
		model.addAttribute("categories", categoryRepo.findAll());
		return "product/create";
	}

	@PostMapping("/create")
	public String addProduct(Model model,@ModelAttribute("product") @Valid Product product, BindingResult bindingresult, @RequestParam("categoryId") int categoryId,
			@RequestParam(name = "file", required = false) MultipartFile file) throws SQLException {
		if (bindingresult.hasErrors()) {
			return "product/create";
		}
		if (file != null && file.getSize() > 0) {
			final String FOLDER = "D:/";
			String filename = file.getOriginalFilename();
			File outputFile = new File(FOLDER + filename);
			try {
				file.transferTo(outputFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			product.setImage("/product/download?filename=" + filename);
		}
		if(categoryId==1) {
			model.addAttribute("select",1);
			return "product/create";
		}
		product.setCategory(categoryRepo.getById(categoryId));
		productRepo.save(product);
		return "redirect:/product/search";
	}

	@GetMapping("/download")
	public void search(@RequestParam("filename") String filename, HttpServletResponse response) throws IOException {
		final String FOLDER = "D:/";
		File file = new File(FOLDER + filename);
		if (file.exists())
			Files.copy(file.toPath(), response.getOutputStream());
	}

	@GetMapping("/update")
	public String update(@RequestParam("id") int id, Model model) {
		Product product = productRepo.getById(id);
		model.addAttribute("categories", categoryRepo.findAll());
//		model.addAttribute("categoryId",productRepo.getById(id).getCategory().getId());
		model.addAttribute("product", product);
		return "product/update";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute Product product, @RequestParam("categoryId") int categoryId,@RequestParam(name = "file", required = false) MultipartFile file) {
		
		product.setCategory(categoryRepo.getById(categoryId));
		Product OldOne = productRepo.getById(product.getId());
		if (file != null && file.getSize() > 0) {
			final String FOLDER = "D:/";
			String filename = file.getOriginalFilename();
			File outputFile = new File(FOLDER + filename);
			try {
				file.transferTo(outputFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			OldOne.setImage("/product/download?filename=" + filename);
		}
		
		OldOne.setName(product.getName());
		OldOne.setDescription(product.getDescription());
		OldOne.setPrice(product.getPrice());
		OldOne.setCategory(categoryRepo.getById(categoryId));
//		OldOne.setImage(product.getImage());
		productRepo.save(OldOne);
		return "redirect:/product/search";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("id") int id) {
		productRepo.deleteById(id);
		return "redirect:/product/search";
	}

	@GetMapping("/search")
	public String search(Model model, @RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "categoryId", required = false) Integer categoryId,
			@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "size", required = false) Integer size) {

		if (size == null)
			size = 3; // max records per page
		if (page == null)
			page = 0; // trang hien tai

		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

		if (name != null && !name.isEmpty()) {
			Page<Product> pageProduct = productRepo.searchName("%" + name + "%", pageable);
			model.addAttribute("productlist", pageProduct.toList());
			model.addAttribute("totalPage", pageProduct.getTotalPages());

//ở đây có 3 page 0,1,2
		} else if (id != null) {

			Product product = productRepo.findById(id).orElse(null);
			if (product != null) {
				model.addAttribute("productlist", Arrays.asList(product));
				model.addAttribute("totalPage", 0);
			} else//
				model.addAttribute("totalPage", 0);

		} else {

			// if(s==null) {
			Page<Product> pageProduct = productRepo.findAll(pageable);
			model.addAttribute("productlist", pageProduct.toList());
			model.addAttribute("totalPage", pageProduct.getTotalPages());
//ở đây có 4 page 0,1,2,3,4
		}
		List<Category> categories = categoryRepo.findAll(); // gui department sang view de so xuong phogn ban
		model.addAttribute("categoryId", categoryId == null ? "" : categoryId);
		model.addAttribute("categories", categories);
		model.addAttribute("page", page);
		model.addAttribute("name", name == null ? "" : name);
		model.addAttribute("size", size);
		model.addAttribute("id", id == null ? "" : id);
		return "product/search";

	}
}
