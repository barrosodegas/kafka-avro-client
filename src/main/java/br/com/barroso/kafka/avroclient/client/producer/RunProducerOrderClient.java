package br.com.barroso.kafka.avroclient.client.producer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.kafka.clients.producer.Producer;

import br.com.barroso.kafka.avroclient.avro.Customer;
import br.com.barroso.kafka.avroclient.avro.Order;
import br.com.barroso.kafka.avroclient.avro.Product;
import br.com.barroso.kafka.avroclient.client.ClientConstants;
import br.com.barroso.kafka.avroclient.utils.ProducerUtils;

/**
 * Class that executes the producer of orders generate three orders.
 * 
 * @author Andre Barroso
 */
public class RunProducerOrderClient {
	
	/**
	 * Method responsible for executing the order producer.
	 * @param args Not used.
	 */
	public static void main(String[] args) {
		
		Producer<String, Order> producer = ProducerKStringVAvroClient.createProducer(
				ClientConstants.KAFKA_BROKERS, 
				ClientConstants.CLIENT_ID_PRODUCER + "_order", 
				ClientConstants.ACK_ALL);
		
		// Closes the producer before exiting execution.
		Runtime.getRuntime().addShutdownHook(new Thread(()-> {
			
			System.out.println();
			
			producer.flush();
			producer.close();
			
			System.out.println("\nClosing producer order...");
			System.out.println("Producer order closed!");
		}));

		SortedMap<String, Order> orders = createOrders();
		
		/**
		 * Send data to retail sales topic in the partition of "0", Order.
		 */
		ProducerUtils.executeProducer(producer, ClientConstants.TOPIC_NAME_SALES, 0, orders);
	}

	/**
	 * Method responsible for creating the Avro orders.
	 * @return orders Order map.
	 */
	private static SortedMap<String, Order> createOrders() {

		// Customers.
		Customer bill = createCustomer("c-100", "Bill", "Bla", "bill.bla@gmail.com");
		Customer jow = createCustomer("c-200", "Jow", "Blaa", "jow.blaa@gmail.com");
		Customer juca = createCustomer("c-300", "Juca", "Blaaa", "juca.blaaa@gmail.com");
		
		// Products.
		Product tv = createProduct("p-01", "TV", 770.45, 1);
		Product lamp = createProduct("p-10", "Lamp", 11.05, 2);
		Product bean = createProduct("p-30", "Bean", 8.32, 3);
		
		// Order of Bill Bla.
		Order orderOfBill = Order.newBuilder()
				.setCode("order-01")
				.setProducts(Collections.singletonList(tv))
				.setCustomer(bill)
				.build();
		
		// Order of Jow Blaa.
		List<Product> prodcutsOfJow = new ArrayList<>();
		prodcutsOfJow.add(lamp);
		prodcutsOfJow.add(bean);
		
		Order orderOfJow = Order.newBuilder()
				.setCode("order-02")
				.setProducts(prodcutsOfJow)
				.setCustomer(jow)
				.build();
		
		// Order of Juca Blaaa.
		List<Product> prodcutsOfJuca = new ArrayList<>();
		prodcutsOfJuca.add(lamp);
		prodcutsOfJuca.add(bean);
		prodcutsOfJuca.add(tv);
		
		Order orderOfJuca = Order.newBuilder()
				.setCode("order-03")
				.setProducts(prodcutsOfJuca)
				.setCustomer(juca)
				.build();
		
		SortedMap<String, Order> orders = new TreeMap<>();
		
		orders.put(orderOfBill.getCode(), orderOfBill);
		orders.put(orderOfJow.getCode(), orderOfJow);
		orders.put(orderOfJuca.getCode(), orderOfJuca);		
		
		return orders;
	}

	/**
	 * Method responsible for creating the Avro Customer.
	 * @param code Client code.
	 * @param firstName First name.
	 * @param lastName Last name.
	 * @param email Email.
	 * @return customer Customer.
	 */
	private static Customer createCustomer(String code, String firstName, String lastName, String email) {
		
		return Customer.newBuilder()
				.setCode(code)
				.setFirstName(firstName)
				.setLastName(lastName)
				.setEmail(email)
				.build();
	}

	/**
	 * Method responsible for creating the product.
	 * @param code Product code.
	 * @param name Product name.
	 * @param price Product price.
	 * @param amount Product amount.
	 * @return product Product.
	 */
	private static Product createProduct(String code, String name, double price, Integer amount) {
		
		return Product.newBuilder().setCode(code)
		.setProductName(name)
		.setProductPrice(price)
		.setProductAmount(amount)
		.build();
	}
	

}
