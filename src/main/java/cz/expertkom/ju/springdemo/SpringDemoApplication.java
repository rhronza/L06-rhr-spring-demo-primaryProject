package cz.expertkom.ju.springdemo;

import java.util.Arrays;

import org.apache.cxf.Bus;
import org.apache.cxf.endpoint.Server;
import org.apache.cxf.jaxrs.JAXRSServerFactoryBean;
import org.apache.cxf.jaxrs.provider.json.JSONProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

import cz.expertkom.ju.springdemo.api.TestApi;
import cz.expertkom.ju.springdemo.api.impl.TestApiImpl;


/* @Configuration
@ComponentScan({
	"cz.expertkom.ju.springdemo","cz.expertkom.ju.springdemo.entity",
	"cz.expertkom.ju.springdemo.interfaces","cz.expertkom.ju.springdemo.service.impl", 
	"cz.expertkom.ju.interfaces.entity","cz.expertkom.ju.interfaces" , "cz.expertkom.ju.repository"
})

*/

@SpringBootApplication
/* domnívám se, že by měl prohledávat i všechny "sub-packages" */
@ComponentScan (basePackages="cz.expertkom.ju")
public class SpringDemoApplication {

	@Autowired
	private Bus bus;
	
	public static void main(String[] args) {
		SpringApplication.run(SpringDemoApplication.class, args);

	}
	
	@Bean
	public TestApi testApi5555() {
		return new TestApiImpl();
	}

	@Bean
	public JSONProvider<?> jsonProvider() {
		JSONProvider<?> jsonProvider = new JSONProvider<>();
		jsonProvider.setDropRootElement(true);
		jsonProvider.setSupportUnwrapped(true);
		return jsonProvider;
	}

	@Bean
	public Server rsServer() {
		final JAXRSServerFactoryBean endpoint = new JAXRSServerFactoryBean();

		//endpoint.setProvider(jsonProvider());
		endpoint.setBus(bus);
		endpoint.setAddress("/experkom/v1.0");
		endpoint.setServiceBeans(Arrays.<Object>asList(testApi5555()));
		return endpoint.create();
	}
	

}
