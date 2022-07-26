package dung.spring.webbanhang.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

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

import dung.spring.webbanhang.entity.Bill;
import dung.spring.webbanhang.entity.User;
import dung.spring.webbanhang.repository.BillRepo;
import dung.spring.webbanhang.repository.UserRepo;
import dung.spring.webbanhang.service.MailService;

@Controller
@RequestMapping("/bill")
public class BillController {
	@Autowired
	BillRepo billRepo;
	@Autowired
	UserRepo userRepo;
	@Autowired
	MailService mailService;

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("bill", new Bill());
		model.addAttribute("userList", userRepo.findAll());

		return "bill/create";
	}

	@PostMapping("/create")
	public String create(Model model,@ModelAttribute("bill") @Valid Bill bill, BindingResult bindingresult,
			@RequestParam("userId") int userId,
			@RequestParam("buy_date") String buy_date) {
		if (bindingresult.hasErrors()) {
			return "bill/create";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try {
			bill.setBuyDate(dateFormat.parse(buy_date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		// bill.setUser(userRepo.getById(userId));
//		System.out.println(couponRepo.findByCouponCode(couponCode));
		if(userId==1) {
			model.addAttribute("select",1);
			return "bill/create";
		}
		bill.setUser(userRepo.getById(userId));
		billRepo.save(bill);
		 User user = userRepo.getById(bill.getUser().getId());
		mailService.sendEmail(user.getEmail(),"dung", "test");
		return "redirect:/bill/search";
	}

	@GetMapping("/update")
	public String update(@RequestParam("id") int id, Model model) {
		Bill bill = billRepo.getById(id);
		model.addAttribute("bill", bill);
		return "bill/update";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute Bill bill) {
		Bill OldOne = billRepo.getById(bill.getId());
		OldOne.setBuyDate(bill.getBuyDate());
		billRepo.save(OldOne);
		return "redirect:/bill/search";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("id") int id) {
		billRepo.deleteById(id);
		return "redirect:/bill/search";
	}

	@GetMapping("/search")
	public String search(Model model, @RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "userId", required = false) Integer userId,
			@RequestParam(name = "page", required = false) Integer page,
			@RequestParam(name = "size", required = false) Integer size,
			@RequestParam(name = "sortBy", required = false) String sortBy) {

		if (size == null)
			size = 3; // max records per page
		if (page == null)
			page = 0; // trang hien tai
//
		Sort sort = Sort.by("id").ascending();
//		if(sortBy != null && sortBy.equals("departmentname")) {
//			sort = Sort.by("workingDay").ascending();
//		}else if(sortBy !=null && sortBy.equals("salaryy")) {
//			sort = Sort.by("salary").ascending();
//		}
//		
		Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

		if (name != null && !name.isEmpty()) {
			Page<Bill> pageBill = billRepo.searchByUser(userId, pageable);

			model.addAttribute("list", pageBill.toList());
			model.addAttribute("totalPage", pageBill.getTotalPages());

		} else if (userId != null) {
			Page<Bill> pageBill = billRepo.searchByUser(userId, pageable);
			model.addAttribute("list", pageBill.toList());
			model.addAttribute("totalPage", pageBill.getTotalPages());
		}

		else {

			// if(s==null) {
			Page<Bill> pageBill = billRepo.findAll(pageable);
			model.addAttribute("list", pageBill.toList());
			model.addAttribute("totalPage", pageBill.getTotalPages());
		}

		List<User> users = userRepo.findAll(); // gui department sang view de so xuong phogn ban
		model.addAttribute("userId", userId == null ? "" : userId);
		model.addAttribute("users", users);
//		System.out.println(users);
		model.addAttribute("size", size);
		model.addAttribute("page", page);
		model.addAttribute("name", name == null ? "" : name);
		model.addAttribute("sortBy", sortBy == null ? "" : sortBy);
		return "bill/search";

	}
}
