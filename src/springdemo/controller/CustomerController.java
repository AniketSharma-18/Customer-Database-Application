package springdemo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import springdemo.dao.CustomerDAO;
import springdemo.entity.Customer;
import springdemo.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
	
	//@Autowired
	//private CustomerDAO customerDAO;
	//need to inject customer service
	@Autowired
	private CustomerService customerservice;

	//@RequestMapping("/list")
	@GetMapping("/list")
	public String listCustomer(Model theModel) {
		
		//get customer from service
		List<Customer> theCustomers=customerservice.getCustomers();
		theModel.addAttribute("customers",theCustomers);
		return "list-customers";
	}
	@RequestMapping(value="/showFormForAdd",method=RequestMethod.GET)
	public String showFormForAdd(Model theModel) {
		Customer theCustomer =new Customer();
		theModel.addAttribute("customer",theCustomer);
		return "customer-form";
	}
	@PostMapping("/saveCustomer")
	public String saveCustomer(@ModelAttribute("customer")Customer theCustomer) {
		customerservice.saveCustomer(theCustomer);
		return "redirect:/customer/list";
	}
	@GetMapping("/showFormForUpdate")
	public String showFormForUpdate(@RequestParam("customerId") int theId, Model theModel) {
		//get customer form service
		Customer theCustomer=customerservice.getCustomers(theId);
		//set customer as model attribute
		theModel.addAttribute("customer",theCustomer);
		
		return "customer-form";
	}
	@GetMapping("/delete")
	public String deleteCustomer(@RequestParam("customerId") int theId) {
	customerservice.deleteCustomer(theId);
	return "redirect:/customer/list";
	}
	@GetMapping("/search")
	public String searchCustomers(@RequestParam("theSearchName")String theSearchName,Model theModel) {
		List<Customer> theCustomers=
				customerservice.searchCustomers(theSearchName);
		theModel.addAttribute("customers",theCustomers);
		return "list-customers";
		}
	
}
