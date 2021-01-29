package com.soft.app.config;



import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import com.soft.app.model.Product;
import com.soft.app.process.ProductProcessor;

@EnableBatchProcessing
@Configuration
public class BatchConfig {

	
	
	@Autowired
	private DataSource datasource;
	
	//1. Item Reader from CSV file
		@Bean
		public ItemReader<Product> reader(){
			FlatFileItemReader<Product> reader=new FlatFileItemReader<>();
			//loading file
			reader.setResource(new ClassPathResource("product.csv"));
			
			reader.setLineMapper(new DefaultLineMapper<Product>() {{
				setLineTokenizer(new DelimitedLineTokenizer() {{
						//setDelimiter(",");
						setNames("prodId","prodName","prodCost");
				}});
				setFieldSetMapper(new BeanWrapperFieldSetMapper<Product>() {{
					//convert to object format
					setTargetType(Product.class);
				}});
			}});
			
			
			return reader;
		}
		
		//2. Item Processor
		@Bean
		public ItemProcessor<Product, Product> processor(){
			return new ProductProcessor();
		}
		
		
		//#. Item Writer
		@Bean
		public ItemWriter<Product> writer(){
			JdbcBatchItemWriter<Product> writer=new JdbcBatchItemWriter<>();
			writer.setDataSource(datasource);
			writer.setSql("INSERT INTO PRODUCT VALUES(:prodId,:prodName,:prodCost,:prodDisc,:prodGst)");
			writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<Product>());
			
			
			return writer;
		}
		
		
		
		



		//STEP
		@Autowired
		private StepBuilderFactory sf;
		
		//Step Bean
		@Bean
		public Step stepA() {
			return sf.get("stepA")
					.<Product,Product>chunk(1)
					.reader(reader())
					.processor(processor())
					.writer(writer()).build(); 
		}
		
		
		//JOB
		@Autowired		
		private JobBuilderFactory jf;
		
		//JobBean
		@Bean
		public Job jobA() {
			return jf.get("jobA")
					.incrementer(new RunIdIncrementer())
					.start(stepA())
					.build();
		}
}
