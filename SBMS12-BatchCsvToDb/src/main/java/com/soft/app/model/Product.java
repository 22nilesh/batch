package com.soft.app.model;

import lombok.Data;

@Data
public class Product {

	private Integer prodId;
	private String prodName;
	private Double prodCost;
	private Double prodDisc;
	private Double prodGst;
}
