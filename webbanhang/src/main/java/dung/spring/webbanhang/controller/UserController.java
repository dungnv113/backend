package dung.spring.webbanhang.controller;

import java.util.Arrays;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import dung.spring.webbanhang.entity.Product;
import dung.spring.webbanhang.entity.User;
import dung.spring.webbanhang.repository.UserRepo;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	UserRepo userRepo;

//	@PreAuthorize("hasRole('ROLE_ADMIN')")        //trc khi vao ham se xac thuc xem role admin thi moi duoc vao
//	@PostAuthorize("#returnObject.username== authentication.principal.username or hasRole('ROLE_ADMIN')") //Kiem tra username tra ve giong username cua thang dang dang nhap
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("user", new User());
		return "user/create";
	}

	@PostMapping("/create")
	public String addUser(@ModelAttribute("user") @Valid User user, BindingResult bindingresult) {
		if (bindingresult.hasErrors()) {
			return "user/create";
		}
		user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
		userRepo.save(user);
		return "redirect:/user/search";
	}
	
	@GetMapping("/update")
	public String update(@RequestParam("id") int id, Model model) {
		User user = userRepo.getById(id);
		model.addAttribute("user", user);
		return "user/update";
	}
	
	@PostMapping("/update")
	public String update(@ModelAttribute User user) {
		User OldOne = userRepo.getById(user.getId());
		OldOne.setName(user.getName());
		userRepo.save(OldOne);
		return "redirect:/user/search";
	}
	
	@GetMapping("/delete")
	public String delete(@RequestParam("id") int id) {
		userRepo.deleteById(id);
		return "redirect:/user/search";
	}

	@GetMapping("/search")
	public String search(Model model, @RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "size", required = false) Integer size) {

		if (size == null)
			size = 3; // max records per page
		if (page == null)
			page = 0; // trang hien tai

		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

		if (name != null && !name.isEmpty()) {
			Page<User> pageUser = userRepo.searchName("%" + name + "%", pageable);
			model.addAttribute("userlist", pageUser.toList());
			model.addAttribute("totalPage", pageUser.getTotalPages());

//ở đây có 3 page 0,1,2
		} else if (id != null) {

			User user = userRepo.findById(id).orElse(null);
			if (user != null) {
				model.addAttribute("userlist", Arrays.asList(user));
				model.addAttribute("totalPage", 0);
				 } else//
					 model.addAttribute("totalPage", 0);

			} else {

				// if(s==null) {
				Page<User> pageUser = userRepo.findAll(pageable);
				model.addAttribute("userlist", pageUser.toList());
				model.addAttribute("totalPage", pageUser.getTotalPages());
//ở đây có 4 page 0,1,2,3,4
			}
		            //gui department sang view de so xuong phogn ban
		
			model.addAttribute("page", page);
			model.addAttribute("name", name == null ? "" : name);
			model.addAttribute("size", size);
			model.addAttribute("id", id == null ? "" : id);
			return "user/search";

		}
	}

