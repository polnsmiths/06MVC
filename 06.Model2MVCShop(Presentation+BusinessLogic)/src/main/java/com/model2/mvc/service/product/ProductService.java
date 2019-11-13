package com.model2.mvc.service.product;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.model2.mvc.common.Search;
import com.model2.mvc.service.domain.Product;

@Service
public interface ProductService {
	
	public Product findProduct(int prodNo)throws Exception;
	
	public Map<String, Object> getProductList(Search search)throws Exception;
	
	public void insertProduct(Product product)throws Exception;
	
	public void updateProduct(Product product)throws Exception;
	

}
