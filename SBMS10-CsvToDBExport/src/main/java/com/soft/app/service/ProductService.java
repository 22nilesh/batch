package com.soft.app.service;

import java.io.File;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import com.soft.app.bean.Product;
import com.soft.app.dao.IProductDAO;

@Service
public class ProductService {

	private IProductDAO dao;

	public ProductService(IProductDAO dao) {
		this.dao = dao;
	}

	public String SaveRecordProduct() throws Exception {

		Scanner sc = new Scanner(new File("product.csv"));

		while (sc.hasNext()) {
			String line = sc.next();

			String[] split = line.split(",");
			String pid = split[0];
			String pname = split[1];
			String price = split[2];

			Product p = new Product();
			p.setProdId(Integer.parseInt(pid));
			p.setProdName(pname);
			p.setProdCost(Double.parseDouble(price));

			dao.saveProduct(p);

		}

		sc.close();
		return "Saved";
	}

}
