package springboot.shop.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import springboot.shop.entity.ShoppingCart;
import springboot.shop.repository.OrderDetailsRepo;
import springboot.shop.repository.ShoppingCartRepo;
import springboot.shop.repository.UserRepo;

@Controller
@RequestMapping("/cart")
public class CartController {
	@Autowired
	OrderDetailsRepo orderDetailsRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	ShoppingCartRepo shoppingCartRepo;
	@GetMapping
	public String product(@RequestParam(name="quantity") int quantity, HttpSession session) {
		User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		String id = currentUser.getUsername();
//		System.out.println(currentUser.getUsername());
//		userRepo.findByUsername(currentUser.getUsername());
//		System.out.println(userRepo.findByUsername(currentUser.getUsername()));
		springboot.shop.entity.User user = userRepo.findByUsername(currentUser.getUsername());
		shoppingCartRepo.searchbyUser(user.getId());
		ShoppingCart shoppingCart = shoppingCartRepo.searchbyUser(user.getId());
		System.out.println(session.getAttribute("productId"));
		return "client/cart";
	}

}
