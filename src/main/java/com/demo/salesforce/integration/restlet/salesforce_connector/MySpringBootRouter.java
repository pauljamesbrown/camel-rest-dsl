package com.demo.salesforce.integration.restlet.salesforce_connector;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;


import org.apache.camel.component.servlet.CamelHttpTransportServlet;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

import org.springframework.boot.web.servlet.ServletRegistrationBean;


@SpringBootApplication
public class MySpringBootRouter extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(MySpringBootRouter.class, args);
    }

    @Bean
    ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servlet = new ServletRegistrationBean(
            new CamelHttpTransportServlet(), "/camel-rest-salesforce/*");
        servlet.setName("CamelServlet");
        return servlet;
    }
    
	@Component
	class RestApi extends RouteBuilder {
	    @Override
		public void configure() {
		    
		    restConfiguration().contextPath("/camel-rest-salesforce").apiContextPath("/api-doc")
		        .apiProperty("api.title", "Camel REST API")
		        .apiProperty("api.version", "1.0")
		        .apiProperty("cors", "true")
		        .apiContextRouteId("doc-api")
		    .bindingMode(RestBindingMode.json)
		    .dataFormatProperty("prettyPrint", "true");
		
		    rest("/leads").description("Leads REST service")
		    .get("/").description("The list of all the leads")
		        .route().routeId("leads-api")
		        .setBody().simple("Completed")
		        .endRest()
		    .get("/lead/{id}").description("Details of a lead by id")
		        .route().routeId("lead-api")
		        .setBody().simple("Completed")
		        .endRest()   
			.post("/lead").description("Details of a lead by id")
		        .route().routeId("create-lead-api")
		        .setBody().simple("Completed")
		        .endRest();
		}
	}
}
