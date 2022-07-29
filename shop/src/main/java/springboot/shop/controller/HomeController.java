package springboot.shop.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springboot.shop.entity.Product;
import springboot.shop.repository.ProductRepo;

@Controller
@RequestMapping("/home")
public class HomeController {
	@Autowired
	ProductRepo productRepo;

	@GetMapping("")
	public String home(Model model) {
		model.addAttribute("productList", productRepo.findAll());
		return "/client/home";
	}

}
