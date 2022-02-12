package com.ibm.telco.som;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.resource.PathResourceResolver;


import java.io.IOException;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class SomPersistenceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SomPersistenceApplication.class, args);
	}

}

@Document(collection = "somorders")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class Order {
	@Id
	private String id;
	private String key;
	private String order;

	public Order(String key, String order) {
		this.key = key;
		this.order = order;
	}

	@Override
	public String toString() {
		return order;
	}
}

interface OrdersRepository extends MongoRepository<Order, String>{}

@Configuration
@EnableConfigurationProperties(OrderProperties.class)
class OrderConfig implements WebMvcConfigurer {
	private Logger log = LoggerFactory.getLogger(OrderConfig.class);

	@Autowired
	private OrderProperties properties;

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/uploads/**")
				.addResourceLocations("file:" + properties.getUploadDir())
				.setCachePeriod(3600)
				.resourceChain(true)
				.addResolver(new PathResourceResolver());
	}
}

@ConfigurationProperties(prefix = "order")
class OrderProperties {
	@Value("${uploadDir:/tmp/uploads/}")
	private String uploadDir;

	public String getUploadDir() {
		return uploadDir;
	}
}

@Controller
class OrderController {
	private Logger log = LoggerFactory.getLogger(OrderController.class);

	@Autowired
	private OrdersRepository ordersRepository;
	//private Parser parser = Parser.builder().build();
	//private HtmlRenderer renderer = HtmlRenderer.builder().build();

	@GetMapping("/")
	public String index(Model model) {
		log.info("...in index(model)");
		getAllOrders(model);
		return "index";
	}

	private void getAllOrders(Model model) {
		log.info("...in getAllOrders(model)");
		List<Order> orders = ordersRepository.findAll();
		log.info("...found "+orders.size() +" orders");
		Collections.reverse(orders);
		model.addAttribute("orders", orders);
	}

	private void saveOrder(String key, String order, Model model) {
		log.info("...in saveOrder("+key+", "+order +", model");
		if (key != null && !key.trim().isEmpty()) {
			ordersRepository.save(new Order(key.trim(), order.trim()));
//			Node document = parser.parse(order.trim());
//			String html = renderer.render(document);
//			ordersRepository.save(new Order(key, order, html));

			//After publish you need to clean up the textarea
			model.addAttribute("key", "");
			model.addAttribute("order", "");
		}
	}

	@PostMapping("/order")
	public String saveOrders(@RequestParam String key,
							@RequestParam String order,
							@RequestParam(required = false) String save,
							Model model) throws IOException {
		log.info("...in POST saveOrders()");
		if (save != null && save.equals("Save")) {
			saveOrder(key, order, model);
			getAllOrders(model);
			return "redirect:/";
		}
		// After save fetch all orders again
		return "index";
	}

}

