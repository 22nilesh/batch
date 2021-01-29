package com.soft.app.process;


import org.springframework.batch.item.ItemProcessor;

import com.soft.app.model.Product;

public class ProductProcessor implements ItemProcessor<Product, Product>{

	@Override
	public Product process(Product item) throws Exception {
		double cost=item.getProdCost();
		double disc=cost*15/100;
		double gst=cost*8/100;
		
		item.setProdDisc(disc);
		item.setProdGst(gst);
		return item;
	}
	

}
