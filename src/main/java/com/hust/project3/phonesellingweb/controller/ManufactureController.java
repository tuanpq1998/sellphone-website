package com.hust.project3.phonesellingweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.hust.project3.phonesellingweb.entity.Manufacturer;
import com.hust.project3.phonesellingweb.service.ManufacturerService;
import com.hust.project3.phonesellingweb.service.ProductService;

@Controller
@RequestMapping("/hang-dt")
public class ManufactureController extends BaseController {

	@Autowired
	private ManufacturerService manufacturerService;
	
	@Autowired
	private ProductService productService;
	
	@GetMapping({"/{manufactureSlug}.{manufactureId}","/{manufactureSlug}.{manufactureId}/"})
	public String showProductList(@PathVariable("manufactureSlug") String manufacturerSlug, @PathVariable("manufactureId") int manufacturerId,
			Model model) {
		Manufacturer manufacturer = manufacturerService.findById(manufacturerId);
		if (manufacturer == null)
			return "";
		String rootManufacturerSlug = manufacturer.getSlug();
		if (rootManufacturerSlug.equals(manufacturerSlug)) {

			model.addAttribute("products", productService.findAllByManufacturerId(manufacturerId));
			model.addAttribute("manufacturer", manufacturer);
		} else 
			return "redirect:/hang-dt/" + rootManufacturerSlug + "." + manufacturerId;
		return "manufacturer";
	}
}
