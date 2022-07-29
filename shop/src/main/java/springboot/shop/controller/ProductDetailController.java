package springboot.shop.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springboot.shop.entity.Product;
import springboot.shop.repository.ProductRepo;

@Controller
@RequestMapping("/product")
public class ProductDetailController {
	@Autowired
	ProductRepo productRepo;

	@GetMapping("/detail")
	public String product(@RequestParam("id") int id, Model model) {
		Product product = productRepo.getById(id);
		model.addAttribute(product);
		return "client/product-detail";
	}

}