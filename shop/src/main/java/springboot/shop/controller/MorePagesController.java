package springboot.shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/wishlist")
public class MorePagesController {
	@GetMapping("")
	public String morepages1() {
	return "client/wishlist";
}
}

