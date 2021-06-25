package com.hust.project3.phonesellingweb.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
import org.springframework.web.multipart.MultipartFile;

import com.hust.project3.phonesellingweb.entity.Bill;
import com.hust.project3.phonesellingweb.entity.Manufacturer;
import com.hust.project3.phonesellingweb.entity.Post;
import com.hust.project3.phonesellingweb.entity.product.Color;
import com.hust.project3.phonesellingweb.entity.product.ColorImg;
import com.hust.project3.phonesellingweb.entity.product.Product;
import com.hust.project3.phonesellingweb.entity.product.ProductImg;
import com.hust.project3.phonesellingweb.entity.product.ProductSpec;
import com.hust.project3.phonesellingweb.model.TempImageUploadItem;
import com.hust.project3.phonesellingweb.service.BillService;
import com.hust.project3.phonesellingweb.service.FeedbackService;
import com.hust.project3.phonesellingweb.service.ManufacturerService;
import com.hust.project3.phonesellingweb.service.PostService;
import com.hust.project3.phonesellingweb.service.ProductService;
import com.hust.project3.phonesellingweb.service.SlideService;
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
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FeedbackService feedbackService;
	
	@Autowired
	private BillService billService;
	
	@Autowired
	private SlideService slideService;
	
	@GetMapping({"", "/"})
	public String showIndex(Model model) {
		
		model.addAttribute("feedbackCount", feedbackService.countAll());
		model.addAttribute("productCount", productService.countAll());
		model.addAttribute("billCount", billService.countAll());
		model.addAttribute("postCount", postService.countAll());
		
		model.addAttribute("newfeedBacks", feedbackService.findTop3Newest());
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
	
	@PostMapping("/products/ajax")
	public ResponseEntity<String> handleFastEdit(@RequestParam("productId") int productId,
			@RequestParam("price") Double price, @RequestParam("available") boolean available) {
		productService.updatePriceAndAvailable(price, available, productId);
		
		return ResponseEntity.ok("Done!");
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
		
		return "admin/newproduct";
	}
	
	@PostMapping("/products/new")
	public String createNewProduct(Product product, @RequestParam(name="tmpImg", required = false) List<String> tmpImgs,
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
				else if (colorImg.getUrl().startsWith("/"+ConstantVariable.UPLOAD_TEMP_DIR)) {
					String ext = StringUtils.getFilenameExtension(colorImg.getUrl());
					String newname = slug + "-" + StringHandler.toSlug(colors.get(i).getName())+j+"."+ext;
					FileHandler.move(colorImg.getUrl().substring(1), ConstantVariable.UPLOAD_DIR, newname);
					colorImg.setUrl("/"+ConstantVariable.UPLOAD_DIR + newname);
				}
			}
			color.setColorImgs(colorImgs);
			colors.set(i, color);
		}
		
		if (tmpImgs != null && tmpImgs.size() > 0) {
			ProductSpec spec = product.getSpec();
			String description = spec.getDescription();
			for (int i = 0; i < tmpImgs.size(); i++) {
				String tempFile = tmpImgs.get(i);
				String ext = StringUtils.getFilenameExtension(tempFile);
				String newname = slug + "-desciption-" + i +  "." + ext;
				FileHandler.move(tempFile, ConstantVariable.UPLOAD_DIR, newname);
				description = description.replaceAll(Pattern.quote(tempFile), ConstantVariable.UPLOAD_DIR + newname);
			}
			spec.setDescription(description);
			product.setSpec(spec);
		}
		productService.save(product);
		return "redirect:/admin/products?created";
	}
	
	@GetMapping("/products/edit")
	public String showEditProduct(@RequestParam("id") int productId, Model model) {
		
		Product product = productService.findById(productId);
		if (product == null)
			return "";
		model.addAttribute("product", product);
		model.addAttribute("manufacturers", manufacturerService.findAll());
		
		return "admin/editproduct";
	}
	
	@PostMapping("/products/edit")
	public String handleEditProduct(Product product, @RequestParam(name="tmpImg", required = false) List<String> tmpImgs,
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
				else if (colorImg.getUrl().startsWith("/"+ConstantVariable.UPLOAD_TEMP_DIR)) {
					String ext = StringUtils.getFilenameExtension(colorImg.getUrl());
					String newname = slug + "-" + StringHandler.toSlug(colors.get(i).getName())+j+"."+ext;
					FileHandler.move(colorImg.getUrl().substring(1), ConstantVariable.UPLOAD_DIR, newname);
					colorImg.setUrl("/"+ConstantVariable.UPLOAD_DIR + newname);
				}
			}
			color.setColorImgs(colorImgs);
			colors.set(i, color);
		}
		
		if (tmpImgs != null && tmpImgs.size() > 0) {
			ProductSpec spec = product.getSpec();
			String description = spec.getDescription();
			for (int i = 0; i < tmpImgs.size(); i++) {
				String tempFile = tmpImgs.get(i);
				String ext = StringUtils.getFilenameExtension(tempFile);
				String newname = slug + "-desciption-" + i +  "." + ext;
				FileHandler.move(tempFile, ConstantVariable.UPLOAD_DIR, newname);
				description = description.replaceAll(Pattern.quote(tempFile), ConstantVariable.UPLOAD_DIR + newname);
			}
			spec.setDescription(description);
			product.setSpec(spec);
		}
		productService.save(product);
		return "redirect:/admin/products?edited";
	}
	
	@GetMapping("/posts")
	public String showAllPosts(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "search", required = false) String searchKey) {
		if (StringHandler.isEmpty(searchKey))
			model.addAttribute("posts", postService.findAll(20, page));
		else model.addAttribute("posts", postService.findAllLike(searchKey, 20, page));
		
		return "admin/posts";
	}
	
	@GetMapping("/posts/new")
	public String showCreatePost(Model model) {
		model.addAttribute("post", new Post());
		
		return "admin/formpost";
	}
	
	@PostMapping("/posts/new")
	public String createNewPost(Post post, @RequestParam(name="tmpImg", required = false) List<String> tmpImgs,
			@RequestPart(name = "file4BannerImg", required = false) MultipartFile bannerImageFile,
			@RequestPart(name="fileImage4Ava", required = false) MultipartFile avaImageFile ) throws IOException {
		String slug = StringHandler.toSlug(post.getTitle());
		post.setSlug(slug);
		if (avaImageFile != null && !avaImageFile.isEmpty()) {
			String ext = StringUtils.getFilenameExtension(avaImageFile.getOriginalFilename());
	        String newFileName =  slug + "-ava-img." + ext;
	        FileHandler.save(ConstantVariable.UPLOAD_DIR, newFileName, avaImageFile);
	        post.setAvaImage("/"+ConstantVariable.UPLOAD_DIR + newFileName);
		}
		
		if (bannerImageFile != null && !bannerImageFile.isEmpty()) {
			String ext = StringUtils.getFilenameExtension(bannerImageFile.getOriginalFilename());
	        String newFileName =  slug + "-banner-img." + ext;
	        FileHandler.save(ConstantVariable.UPLOAD_DIR, newFileName, avaImageFile);
	        post.setBannerImage("/"+ConstantVariable.UPLOAD_DIR + newFileName);
		}
		
		if (tmpImgs != null && tmpImgs.size() > 0) {
			String body = post.getBody();
			for (int i = 0; i < tmpImgs.size(); i++) {
				String tempFile = tmpImgs.get(i);
				String ext = StringUtils.getFilenameExtension(tempFile);
				String newname = slug + "-body-" + i +  "." + ext;
				FileHandler.move(tempFile, ConstantVariable.UPLOAD_DIR, newname);
				body = body.replaceAll(Pattern.quote(tempFile), ConstantVariable.UPLOAD_DIR + newname);
			}
			post.setBody(body);
		}
		postService.save(post);
		return "redirect:/admin/posts?created";
	}
	
	@GetMapping("/posts/edit")
	public String showEditPost(Model model, @RequestParam("id") int postId) {
		Post post = postService.findById(postId);
		if (post == null)
			return "";
		model.addAttribute("post", post);
		return "admin/formpost";
	}
	
	@PostMapping("/posts/edit")
	public String handleEditPost(Post post,  @RequestParam(name="tmpImg", required = false) List<String> tmpImgs,
			@RequestPart(name = "file4BannerImg", required = false) MultipartFile bannerImageFile,
			@RequestPart(name="fileImage4Ava", required = false) MultipartFile avaImageFile ) throws IOException {
		
		String slug = StringHandler.toSlug(post.getTitle());
		post.setSlug(slug);
		if (avaImageFile != null && !avaImageFile.isEmpty()) {
			String ext = StringUtils.getFilenameExtension(avaImageFile.getOriginalFilename());
	        String newFileName =  slug + "-ava-img." + ext;
	        FileHandler.save(ConstantVariable.UPLOAD_DIR, newFileName, avaImageFile);
	        post.setAvaImage("/"+ConstantVariable.UPLOAD_DIR + newFileName);
		}
		
		if (bannerImageFile != null && !bannerImageFile.isEmpty()) {
			String ext = StringUtils.getFilenameExtension(bannerImageFile.getOriginalFilename());
	        String newFileName =  slug + "-banner-img." + ext;
	        FileHandler.save(ConstantVariable.UPLOAD_DIR, newFileName, avaImageFile);
	        post.setBannerImage("/"+ConstantVariable.UPLOAD_DIR + newFileName);
		}
		
		if (tmpImgs != null && tmpImgs.size() > 0) {
			String body = post.getBody();
			for (int i = 0; i < tmpImgs.size(); i++) {
				String tempFile = tmpImgs.get(i);
				String ext = StringUtils.getFilenameExtension(tempFile);
				String newname = slug + "-body-" + i +  "." + ext;
				FileHandler.move(tempFile, ConstantVariable.UPLOAD_DIR, newname);
				body = body.replaceAll(Pattern.quote(tempFile), ConstantVariable.UPLOAD_DIR + newname);
			}
			post.setBody(body);
		}
		postService.save(post);

		return "redirect:/admin/posts?edited";
	}
	
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<String> handleDeletePost(@PathVariable int postId) {
		postService.deleteById(postId);
		return ResponseEntity.ok("Deleted!");
	}
	
	@GetMapping("/feedbacks")
	public String showAllFeedbacks(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page) {
		model.addAttribute("feedbacks", feedbackService.findAll(20, page));
		return "admin/feedbacks";
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
		return new ResponseEntity<List<TempImageUploadItem>>(result, HttpStatus.OK);
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
	
	@GetMapping("/bills")
	public String showAllBills(Model model,
			@RequestParam(name = "page", required = false, defaultValue = "1") Integer page,
			@RequestParam(name = "search", required = false) String billIdStr) {
		if (StringHandler.isEmpty(billIdStr))
			model.addAttribute("bills", billService.findAll(20, page));
		else {
			int billId = Integer.parseInt(billIdStr);
			model.addAttribute("bills", billService.findAllLike(20, page, billId));
		}
		return "admin/bills";
	}
	
	@GetMapping("/bills/{billId}")
	public ResponseEntity<Bill> showBillDetail(@PathVariable("billId") int billId) {
		return new ResponseEntity<Bill>(billService.findById(billId), HttpStatus.OK);
	}
	
	@PostMapping("/bills/{billId}")
	public ResponseEntity<String> handleEditBill(@PathVariable("billId") int billId,
		@RequestParam("note") String note, @RequestParam("address") String address,
		@RequestParam("phone") String phone, @RequestParam("status") String status,
		@RequestParam(name="done", required = false) boolean done) {
			Bill bill = billService.findById(billId);
			if (bill== null)
				return new ResponseEntity<String>("", HttpStatus.NOT_FOUND);
			bill.setCustomAddress(address);
			bill.setCustomPhone(phone);
			bill.setNote(note);
			bill.setStatus(status);
			if (done)
				bill.setDone(done);
			
			billService.update(bill);
			return ResponseEntity.ok("Updated!");
	}
	
	@GetMapping("/customized")
	public String showSetting(Model model) {
		model.addAttribute("slides", slideService.findAll());
		return "admin/customized";
	}
	
	
}
