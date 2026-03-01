package springboot_crud;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/products")
public class ProductController 
{
	@Autowired
    private ProductService productService;
    @GetMapping
    	public String getAllProducts(Model model) 
		{
        model.addAttribute("products", productService.getAllProducts());
        return "product-list";
    	}

    	@GetMapping("/create")
    	public String showCreateForm(Model model) 
    	{
        model.addAttribute("product", new Product());
        return "product-form";
    	}
    	
    	@PostMapping("/create")
    	public String createProduct(@ModelAttribute Product product) 
	{
        productService.saveProduct(product);
        return "redirect:/products";
    	}

    	@GetMapping("/edit/{id}")
    	public String showEditForm(@PathVariable("id") Long id, Model model) 
	{
        Product product = productService.getProductById(id).orElseThrow(() -> new IllegalArgumentException("Invalid product ID"));
        model.addAttribute("product", product);
        return "product-form";
    	}

    	@PostMapping("/edit/{id}")
    	public String updateProduct(@PathVariable("id") Long id, @ModelAttribute Product product)
	 {
        product.setId(id);
        productService.saveProduct(product);
        return "redirect:/products";
    }

    	@GetMapping("/delete/{id}")
    	public String deleteProduct(@PathVariable("id") Long id) 
	{
        productService.deleteProduct(id);
        return "redirect:/products";
    	}
}
