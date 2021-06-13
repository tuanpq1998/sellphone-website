package com.hust.project3.phonesellingweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.hust.project3.phonesellingweb.entity.Manufacturer;
import com.hust.project3.phonesellingweb.service.ManufacturerService;
import com.hust.project3.phonesellingweb.service.ProductService;
import com.hust.project3.phonesellingweb.utility.StringHandler;

@Controller
public class ManufactureController extends BaseController {

	@Autowired
	private ManufacturerService manufacturerService;

	@Autowired
	private ProductService productService;

	@GetMapping({ "/dtdd", "/dtdd/" })
	public String getAllProduct(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "sx", required = false) String sortType,
			@RequestParam(name = "search", required = false) String searchKey,
			@RequestParam(name = "price", required = false) String priceRange ) {
		if (StringHandler.isEmpty(searchKey)) {
			if (StringHandler.isEmpty(priceRange))
				model.addAttribute("products", productService.findAll(page, sortType));
			else model.addAttribute("products", productService.findAll(page, sortType, priceRange));
		} else {
			if (StringHandler.isEmpty(priceRange))
				model.addAttribute("products", productService.findByNameLike(page, sortType, searchKey));
			else model.addAttribute("products", productService.findByNameLike(page, sortType, searchKey, priceRange));
		}
		model.addAttribute("isAllManuPage", true);
		return "manufacturer";
	}

	@GetMapping({ "/hang-dt/{manufactureSlug}.{manufactureId}", "/hang-dt/{manufactureSlug}.{manufactureId}/" })
	public String showProductList(@PathVariable("manufactureSlug") String manufacturerSlug,
			@PathVariable("manufactureId") int manufacturerId, Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "sx", required = false) String sortType,
			@RequestParam(name = "search", required = false) String searchKey,
			@RequestParam(name = "price", required = false) String priceRange ) {
		Manufacturer manufacturer = manufacturerService.findById(manufacturerId);
		if (manufacturer == null)
			return "";
		String rootManufacturerSlug = manufacturer.getSlug();
		if (rootManufacturerSlug.equals(manufacturerSlug)) {
			if (StringHandler.isEmpty(searchKey)) {
				if (StringHandler.isEmpty(priceRange)) 
					model.addAttribute("products", productService.findAllByManufacturerId(manufacturerId, page, sortType));
				else model.addAttribute("products", productService.findAllByManufacturerId(manufacturerId, page, sortType, priceRange));
			} else {
				if (StringHandler.isEmpty(priceRange)) 
					model.addAttribute("products", 
							productService.findByManufacturerIdAndNameLike(manufacturerId, page, sortType, searchKey));
				else model.addAttribute("products",
							productService.findByManufacturerIdAndNameLike(manufacturerId, page, sortType, searchKey, priceRange));
			}
			model.addAttribute("manufacturer", manufacturer);
			return "manufacturer";
		} else
			return "redirect:/hang-dt/" + rootManufacturerSlug + "." + manufacturerId;
	}

}
