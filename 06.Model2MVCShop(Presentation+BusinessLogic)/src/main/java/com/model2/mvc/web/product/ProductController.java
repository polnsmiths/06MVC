package com.model2.mvc.web.product;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.model2.mvc.common.Page;
import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;
import com.model2.mvc.service.product.ProductService;

@Controller
public class ProductController {

	///field
	@Autowired
	@Qualifier("productServiceImpl")
	private ProductService productService;
	
	///consructor
	public ProductController() {
		System.out.println(this.getClass());

	}
	
	//==> classpath:config/common.properties  ,  classpath:config/commonservice.xml ���� �Ұ�
	//==> �Ʒ��� �ΰ��� �ּ��� Ǯ�� �ǹ̸� Ȯ�� �Ұ�
	@Value("#{commonProperties['pageUnit']}")
	//@Value("#{commonProperties['pageUnit'] ?: 3}") //pageUnit�� NULL�̸� ����Ʈ�� 3 ����.
	int pageUnit;
		
	@Value("#{commonProperties['pageSize']}")
	//@Value("#{commonProperties['pageSize'] ?: 2}")
	int pageSize;
	
	@RequestMapping("/addProduct.do")
	public String addProduct( @ModelAttribute("product") Product product)throws Exception{
		
		System.out.println("/addProduct.do");
		
		productService.insertProduct(product);
		return "forward:/product/addProduct.jsp";
	}
	
	@RequestMapping("/getProduct.do")
	public String getProduct( @RequestParam("prodNo") int prodNo, Model model)throws Exception{
		
		System.out.println("/getProduct.do");
		
		Product product = productService.findProduct(prodNo);
		
		// Model �� View ����
		model.addAttribute("product", product);
		return "forward:/product/getProduct.jsp";
	}
	
	@RequestMapping("/updateProduct.do")
	public String updateProduct( @ModelAttribute("product") Product product, Model model, HttpSession session)throws Exception{
		
		System.out.println("/updateProduct.do");
		
		productService.updateProduct(product);
		
		int sessionId = ((Product)session.getAttribute("product")).getProdNo();
		
		if(sessionId == product.getProdNo() ) {
			session.setAttribute("product", product);
		}
		
        return "forward:/getProduct.do?prodNo="+product.getProdNo();
	}
	
	@RequestMapping("/updateProductView.do")
	public String updateProductView( @RequestParam("prodNo") int prodNo, Model model)throws Exception{
		
		System.out.println("/updateProductView.do");
		
		Product product = productService.findProduct(prodNo);
		
		model.addAttribute("product", product);
		return "forward:/product/updateProductView.jsp";
	}
	
	@RequestMapping("/listProduct.do")
	public String listProduct( @ModelAttribute("search") Search search, Model model, HttpServletRequest request)throws Exception{
		
		System.out.println("/listProduct.dp");
		
		if(search.getCurrentPage() == 0) {
			search.setCurrentPage(1);
		}
		search.setPageSize(pageSize);
		
		Map<String, Object> map = productService.getProductList(search);
		
		Page resultPage = new Page(search.getCurrentPage(), ((Integer)map.get("totalCount")).intValue(), pageUnit, pageSize);
		System.out.println("resultPage:"+resultPage);
		
		// Model �� View ����
		model.addAttribute("list", map.get("list"));
		model.addAttribute("resultPage", resultPage);
		model.addAttribute("search", search);
		
		String menu = (String)request.getParameter("menu");
		System.out.println("menu : "+menu);
		if(menu.equals("search")) {
		  	map.put("title", "��ǰ �����ȸ");
		   	map.put("page", "/getProduct.do?");
		}else {
		   	map.put("title", "��ǰ����");
		   	map.put("page", "/updateProductView.do?");
		}
		request.setAttribute("menu", map.get("title") );
		request.setAttribute("page", map.get("page"));
		
		return "forward:/product/listProduct.jsp";
	}
		
		
	
	

}
