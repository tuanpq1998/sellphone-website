package com.hust.project3.phonesellingweb.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.hust.project3.phonesellingweb.entity.Manufacturer;
import com.hust.project3.phonesellingweb.service.ManufacturerService;
import com.hust.project3.phonesellingweb.service.ProductService;
import com.hust.project3.phonesellingweb.utility.FileUploadHandler;
import com.hust.project3.phonesellingweb.utility.StringHandler;

@Controller
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	private ProductService productService;
	
	@Autowired
	private ManufacturerService manufacturerService;
	
	@GetMapping({"", "/"})
	public String showIndex() {
		return "admin/index.html";
	}
	
	@GetMapping("/products")
	public String showAllProducts(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "search", required = false) String searchKey) {
		if (StringHandler.isEmpty(searchKey))
			model.addAttribute("products", productService.findAllForAdmin(20, page));
		else model.addAttribute("products", productService.findAllForAdminLike(searchKey, 20, page));
		return "admin/products.html";
	}
	
	@GetMapping("/manufacturers")
	public String showAllManufacturers(Model model) {
		model.addAttribute("manufacturers", manufacturerService.findAll());
		model.addAttribute("manufacturer", new Manufacturer());
		return "admin/manufacturers";
	}
	
	@PostMapping("/manufacturers")
	public String createManufacturer(Manufacturer manufacturer,
			@RequestParam("fileImage") MultipartFile multipartFile, Model model) throws IOException {
		String ext = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
        String uploadDir = "src/main/resources/static/upload/images/";
        String newFileName =  manufacturer.getSlug() + "." + ext;
        FileUploadHandler.save(uploadDir, newFileName, multipartFile);
        
        manufacturer.setImage(newFileName);
        manufacturerService.save(manufacturer);
        
		return showAllManufacturers(model);
	}
}
