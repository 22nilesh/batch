package com.soft.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import com.soft.app.service.ProductService;

@SpringBootApplication
public class Sbms10CsvToDbExportApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(Sbms10CsvToDbExportApplication.class, args);

		ProductService bean = context.getBean(ProductService.class);

		try {
			bean.SaveRecordProduct();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
