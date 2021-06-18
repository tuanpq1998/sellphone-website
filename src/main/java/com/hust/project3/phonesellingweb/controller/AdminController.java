package com.hust.project3.phonesellingweb.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.hust.project3.phonesellingweb.entity.Manufacturer;
import com.hust.project3.phonesellingweb.entity.Product;
import com.hust.project3.phonesellingweb.service.ManufacturerService;
import com.hust.project3.phonesellingweb.service.ProductService;
import com.hust.project3.phonesellingweb.utility.ConstantVariable;
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
	
	@DeleteMapping("/products/{productId}")
	public ResponseEntity<String> handleDeleteProduct(@PathVariable int productId) {
		productService.deleteById(productId);
		return ResponseEntity.ok("Deleted!");
	}
	
	@GetMapping("/products/new")
	public String showCreateProduct(Model model) {
		model.addAttribute("product", new Product());
		return "/admin/newproduct";
	}
	
	@GetMapping("/manufacturers")
	public String showAllManufacturers(Model model) {
		model.addAttribute("manufacturers", manufacturerService.findAll());
		model.addAttribute("manufacturer", new Manufacturer());
		return "admin/manufacturers";
	}
	
	@PostMapping(value = "/manufacturers")
	public String createManufacturer(Manufacturer manufacturer,
			@RequestPart(name = "fileImage", required = false) MultipartFile multipartFile) throws IOException {
		manufacturer.setSlug(StringHandler.toSlug(manufacturer.getName()));
		if (multipartFile != null && !multipartFile.isEmpty()) {
			String ext = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
	        String newFileName =  manufacturer.getSlug() + "." + ext;
	        FileUploadHandler.save(ConstantVariable.UPLOAD_DIR, newFileName, multipartFile);
	        manufacturer.setImage("/"+ConstantVariable.UPLOAD_DIR + newFileName);
		}
        manufacturerService.save(manufacturer);
		return "redirect:/admin/manufacturers?created";
	}
	
	@GetMapping("/manufacturers/edit")
	public String showEditManufacturer(Model model,
			@RequestParam("id") int manufacturerId) {
		Manufacturer manufacturer = manufacturerService.findById(manufacturerId);
		if (manufacturer == null) 
			return "";
		model.addAttribute("manufacturer", manufacturer);
		return "admin/editmanufacturer";
	}
	
	@PostMapping("/manufacturers/edit")
	public String handleEditManufacturer(Manufacturer manufacturer, Model model,
			@RequestPart(name = "fileImage", required = false) MultipartFile multipartFile) throws IOException {
		manufacturer.setSlug(StringHandler.toSlug(manufacturer.getName()));
		if (multipartFile != null && !multipartFile.isEmpty()) {
			String ext = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
	        String newFileName =  manufacturer.getSlug() + "." + ext;
	        FileUploadHandler.save(ConstantVariable.UPLOAD_DIR, newFileName, multipartFile);
	        manufacturer.setImage("/"+ConstantVariable.UPLOAD_DIR + newFileName);
		}
        manufacturerService.save(manufacturer);
        return "redirect:/admin/manufacturers?edited";  
	}
	
	@DeleteMapping("/manufacturers/{manufacturerId}")
	public ResponseEntity<String> handleDeleteManfacturer(@PathVariable("manufacturerId") int manufacturerId) {
		if (manufacturerId == 0)
			return new ResponseEntity<String>("Failed!", HttpStatus.FORBIDDEN);
		manufacturerService.deleteById(manufacturerId);
		return ResponseEntity.ok("Deleted!");
	}
}
