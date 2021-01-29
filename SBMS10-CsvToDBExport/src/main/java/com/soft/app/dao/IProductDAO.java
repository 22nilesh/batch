package com.soft.app.dao;

import java.sql.SQLException;

import com.soft.app.bean.Product;

public interface IProductDAO {

	public void saveProduct(Product product)throws SQLException;
}
