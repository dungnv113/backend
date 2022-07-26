package dung.spring.webbanhang.controller;

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
import dung.spring.webbanhang.entity.BillItems;
import dung.spring.webbanhang.entity.Product;
import dung.spring.webbanhang.repository.BillItemsRepo;
import dung.spring.webbanhang.repository.BillRepo;
import dung.spring.webbanhang.repository.ProductRepo;

@Controller
@RequestMapping("/billitems")
public class BillItemsController {
	@Autowired
	BillRepo billRepo;
	@Autowired
	BillItemsRepo billItemsRepo;
	@Autowired
	ProductRepo productRepo;

	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("billItems", new BillItems());
		model.addAttribute("billList", billRepo.findAll());
		model.addAttribute("productList", productRepo.findAll());
		return "billitems/create";
	}

	@PostMapping("/create")
	public String create(@ModelAttribute("billitems") @Valid BillItems billItems, BindingResult bindingresult,
			@RequestParam("billId") int billId, @RequestParam("productId") int productId) {

		billItems.setBill(billRepo.getById(billId));
		billItems.setProduct(productRepo.getById(productId));
		billItemsRepo.save(billItems);
		return "redirect:/billitems/search";
	}

	@GetMapping("/update")
	public String update(@RequestParam("id") int id, Model model) {
		BillItems billItems = billItemsRepo.getById(id);
		model.addAttribute("billItems", billItems);
		model.addAttribute("products", productRepo.findAll());
		model.addAttribute("bills", billItemsRepo.findAll());
		return "billitems/update";
	}

	@PostMapping("/update")
	public String update(@ModelAttribute BillItems billItems,@RequestParam("billId") int billId,@RequestParam("productId") int productId) {
		billItems.setBill(billRepo.getById(billId));
		billItems.setProduct(productRepo.getById(productId));
		billItemsRepo.save(billItems);
		return "redirect:/billitems/search";
	}

	@GetMapping("/delete")
	public String delete(@RequestParam("id") int id) {
		billItemsRepo.delete(billItemsRepo.getById(id));
		return "redirect:/billitems/search";
	}

	@GetMapping("/search")
	public String search(Model model, @RequestParam(name = "name", required = false) String name,
			@RequestParam(name = "id", required = false) Integer id,
			@RequestParam(name = "productId", required = false) Integer productId,
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

//		if (name != null && !name.isEmpty()) {
//			Page<BillItems> pageBillItems = billItemsRepo.searchByBill(billId, pageable);
//
//			model.addAttribute("list", pageBillItems.toList());
//			model.addAttribute("totalPage", pageBillItems.getTotalPages());

		 if (id != null) {
			Page<BillItems> pageBillItems = billItemsRepo.searchByBill(id, pageable);
			model.addAttribute("list", pageBillItems.toList());
			model.addAttribute("totalPage", pageBillItems.getTotalPages());
		}

		else {

			// if(s==null) {
			Page<BillItems> pageBillItems = billItemsRepo.findAll(pageable);
			model.addAttribute("list", pageBillItems.toList());
			model.addAttribute("totalPage", pageBillItems.getTotalPages());
		}

		List<Bill> bills = billRepo.findAll(); 
		List<Product> products = productRepo.findAll(); 
		model.addAttribute("billId", id == null ? "" : id);
		model.addAttribute("productId", productId == null ? "" : productId);
		model.addAttribute("bills", bills);
		model.addAttribute("products", products);
		System.out.println(bills);
		model.addAttribute("size", size);
		model.addAttribute("page", page);
		model.addAttribute("name", name == null ? "" : name);
		model.addAttribute("sortBy", sortBy == null ? "" : sortBy);
		return "billitems/search";

	}
}
