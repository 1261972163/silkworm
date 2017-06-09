package com.hz.wy.service.feature;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hz.wy.service.product.ProductService;

@Service
public class FeatureServiceImpl implements FeatureService {
	
	@Autowired
	private ProductService productService;
	
//	public ProductService getProductService() {
//		return productService;
//	}
//
//	public void setProductService(ProductService productService) {
//		this.productService = productService;
//	}
	
//	public FeatureServiceImpl(ProductService productService) {
//		this.productService = productService;
//	}

	@Override
	public String get(String s) {
		return productService.get(s);
	}

	
	
}
