package com.flipkart;

import com.flipkart.dao.AdminDaoImplementation;
import com.flipkart.dao.ProfessorDaoImplementation;
import com.flipkart.dao.StudentDaoImplementation;

import com.flipkart.db.ConnectionUtil;
import com.flipkart.restApi.AdminRestApi;
import com.flipkart.restApi.ProfessorRestApi;
import com.flipkart.restApi.StudentRestApi;

import io.dropwizard.Application;
import io.dropwizard.setup.Environment;

public class App extends Application<AppConfiguration>{

	public static void main(String[] args) throws Exception {
		new App().run(args);
	}
	
	@Override
	public void run(AppConfiguration configuration, Environment environment) throws Exception {
		
		ConnectionUtil connectionUtil = new ConnectionUtil(configuration.getUrl(), configuration.getUsername(), configuration.getPassword());
		
		AdminDaoImplementation adm = new AdminDaoImplementation(connectionUtil);
		
		AdminRestApi admapi = new AdminRestApi(adm);
		
		environment.jersey().register(admapi);
		
		StudentDaoImplementation stud = new StudentDaoImplementation(connectionUtil);
		
		StudentRestApi studapi = new StudentRestApi(stud);
		
		environment.jersey().register(studapi);
		
		ProfessorDaoImplementation prof = new ProfessorDaoImplementation(connectionUtil);
		
		ProfessorRestApi profapi = new ProfessorRestApi(prof);
		
		environment.jersey().register(profapi);
		
		
		
//		ProductDAOJdbcImpl dao = new ProductDAOJdbcImpl(connectionUtil);
	
//		ProductResource resource = new ProductResource(dao);
		
//		environment.jersey().register(resource);
		
	}

}
