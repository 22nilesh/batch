package com.soft.app.dao.impl;

import java.sql.SQLException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.soft.app.bean.Product;
import com.soft.app.dao.IProductDAO;

@Repository
public class ProductDAOImpl implements IProductDAO {

	private final static String INSERT_QUERY = "INSERT INTO PRODUCT(PRODID,PRODNAME,PRODPRICE)VALUES(?,?,?)";

	private JdbcTemplate template;

	public ProductDAOImpl(JdbcTemplate template) {
		this.template = template;
	}

	@Override
	public void saveProduct(Product product) throws SQLException {
		template.update(INSERT_QUERY, product.getProdId(), product.getProdName(), product.getProdCost());
	}

}
