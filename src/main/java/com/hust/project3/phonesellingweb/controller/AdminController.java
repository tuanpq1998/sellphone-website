package com.hust.project3.phonesellingweb.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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

import com.hust.project3.phonesellingweb.entity.Color;
import com.hust.project3.phonesellingweb.entity.ColorImg;
import com.hust.project3.phonesellingweb.entity.Manufacturer;
import com.hust.project3.phonesellingweb.entity.Product;
import com.hust.project3.phonesellingweb.entity.ProductImg;
import com.hust.project3.phonesellingweb.model.TempImageUploadItem;
import com.hust.project3.phonesellingweb.service.ManufacturerService;
import com.hust.project3.phonesellingweb.service.ProductService;
import com.hust.project3.phonesellingweb.utility.ConstantVariable;
import com.hust.project3.phonesellingweb.utility.FileHandler;
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
		model.addAttribute("manufacturers", manufacturerService.findAll());
		
		return "/admin/newproduct";
	}
	
	@PostMapping("/products/new")
	public String createNewProduct(Product product, 
			@RequestPart(name = "file4ProductImg", required = false) MultipartFile[] files,
			@RequestPart(name="fileImage4Ava", required = false) MultipartFile avaImageFile ) throws IOException {
		String slug = StringHandler.toSlug(product.getName());
		product.setSlug(slug);
		
		if (avaImageFile != null && !avaImageFile.isEmpty()) {
			String ext = StringUtils.getFilenameExtension(avaImageFile.getOriginalFilename());
	        String newFileName =  slug + "." + ext;
	        FileHandler.save(ConstantVariable.UPLOAD_DIR, newFileName, avaImageFile);

	        product.setAvaImage("/"+ConstantVariable.UPLOAD_DIR + newFileName);
		}
		
		List<ProductImg> imgs = product.getProductImgs();
		
		for (int i = imgs.size() - 1; i >= 0; i--) {
			if (StringHandler.isEmpty(imgs.get(i).getUrl()))
				imgs.remove(i);
		}
		
		if (files != null && !(files.length == 0)) {
			for (int i = 0; i < files.length; i++) {
				MultipartFile file = files[i];
				if (file != null && !file.isEmpty()) {
					String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
					String newFileName = slug + "-" + (i+1) + "." + ext;
			        FileHandler.save(ConstantVariable.UPLOAD_DIR, newFileName, file);
			        String newImg = "/"+ConstantVariable.UPLOAD_DIR + newFileName;
			        ProductImg productImg = new ProductImg();
			        productImg.setUrl(newImg);
			        imgs.add(productImg);
				}
			}
			product.setProductImgs(imgs);
		}

		System.out.println(product);
		List<Color> colors = product.getColors();
		for (int i = colors.size() - 1; i >= 0; i--) {
			if (StringHandler.isEmpty(colors.get(i).getName())) {
				colors.remove(i);
				continue;
			}
			Color color = colors.get(i);
			List<ColorImg> colorImgs = color.getColorImgs();
			for (int j = colorImgs.size() -1; j>= 0; j--) {
				ColorImg colorImg = colorImgs.get(j);
				if (StringHandler.isEmpty(colorImg.getUrl()))
					colorImgs.remove(j);
				else if (colorImg.getUrl().startsWith("upload/tmp/")) {
					String ext = StringUtils.getFilenameExtension(colorImg.getUrl());
					String newname = slug + "_" + StringHandler.toSlug(colors.get(i).getName())+j+"."+ext;
					FileHandler.move(colorImg.getUrl(), ConstantVariable.UPLOAD_DIR, newname);
					colorImg.setUrl("/"+ConstantVariable.UPLOAD_DIR + newname);
				}
			}
			color.setColorImgs(colorImgs);
			colors.set(i, color);
		}
		System.out.println(product);
		return "";
	}
	
	@PostMapping("/tmpUpload")
	public ResponseEntity<List<TempImageUploadItem>> uploadTempImage(
			@RequestPart(name = "fileImage", required = false) MultipartFile[] files) throws IOException {
		List<TempImageUploadItem> result = null;
		if (files != null && !(files.length == 0)) {
			result = new ArrayList<>();
			for (MultipartFile file : files) {
				if (file != null && !file.isEmpty()) {
					TempImageUploadItem item = new TempImageUploadItem();
					item.setOriginName(file.getOriginalFilename());
					String ext = StringUtils.getFilenameExtension(file.getOriginalFilename());
					String newFileName = StringHandler.generateRandomString(10) + "." + ext;
			        FileHandler.save(ConstantVariable.UPLOAD_TEMP_DIR, newFileName, file);
			        item.setTempPath(ConstantVariable.UPLOAD_TEMP_DIR + newFileName);
			        
			        result.add(item);
				}
			}
		}
		return new ResponseEntity<List<TempImageUploadItem>>(result, HttpStatus.CREATED);
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
	        FileHandler.save(ConstantVariable.UPLOAD_DIR, newFileName, multipartFile);
	        manufacturer.setImage("/"+ConstantVariable.UPLOAD_DIR + newFileName);
		}
        manufacturerService.save(manufacturer);
		return "redirect:/admin/manufacturers?created";
	}
	
	@PostMapping(value = "/manufacturers/ajax")
	public ResponseEntity<List<Manufacturer>> createManufacturerAjax(Manufacturer manufacturer,
			@RequestPart(name = "fileImage", required = false) MultipartFile multipartFile) throws IOException {
		createManufacturer(manufacturer, multipartFile);
		return new ResponseEntity<List<Manufacturer>>(manufacturerService.findAll(), HttpStatus.OK);
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
	        FileHandler.save(ConstantVariable.UPLOAD_DIR, newFileName, multipartFile);
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
