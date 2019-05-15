package br.com.barroso.kafka.avroclient.client.consumer;

import java.time.Duration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;

import br.com.barroso.kafka.avroclient.avro.Order;
import br.com.barroso.kafka.avroclient.avro.Payment;
import br.com.barroso.kafka.avroclient.client.ClientConstants;
import br.com.barroso.kafka.avroclient.client.producer.ProducerKStringVAvroClient;
import br.com.barroso.kafka.avroclient.utils.ProducerUtils;

/**
 * Class responsible to prepare and execute the Avro consumer Order and post in Avro Payment partition.
 * 
 * @author Andre Barroso.
 */
public class RunConsumerOrderClient {
	
	/**
	 * Method responsible for execute the Avro consumer.
	 * @param args Not used.
	 * @throws Exception General exception error.
	 */
	public static void main(String[] args) throws Exception {
		
		// Creating Order consumer.
		Consumer<String, Order> consumerOrder = ConsumerKStringVAvroClient.createConsumer(
				ClientConstants.KAFKA_BROKERS,
				ClientConstants.GROUP_ID_CONSUMERS + "_order",
				ClientConstants.CLIENT_ID_CONSUMER + "_order",
				ClientConstants.MAX_POLL_RECORD,
				ClientConstants.OFFSET_RESET_EARLIEST,
				ClientConstants.TOPIC_NAME_SALES,
				0);
		
		// Creating Payment producer.
		Producer<String, Payment> producerPayment = ProducerKStringVAvroClient.createProducer(
				ClientConstants.KAFKA_BROKERS, 
				ClientConstants.CLIENT_ID_PRODUCER + "_payment", 
				ClientConstants.ACK_ALL);
		
		int noMessageFound = 0;

		// Closes the consumer and producer before exiting execution.
		Runtime.getRuntime().addShutdownHook(new Thread(()-> {
			
			System.out.println("\nClosing consumer order...");
			consumerOrder.close();
			System.out.println("Consumer order closed!");
			
			System.out.println("\nClosing producer payment...");
			producerPayment.flush();
			producerPayment.close();
			System.out.println("Producer payment closed!");
		}));
		
		while(true) {
			ConsumerRecords<String, Order> consumerRecords = consumerOrder.poll(Duration.ofMillis(100));

			if(consumerRecords.count() == 0) {
				noMessageFound++;
				if(noMessageFound > ClientConstants.MAX_NO_RECORDS_FOUND) {
					break;
				} else {
					continue;
				}
			}
			
			System.out.println();
			
			consumerRecords.forEach( record -> {
				
				System.out.println("Consuming Order record key: " + record.key() + " with value: " + record.value()
						+ " with partition: " + record.partition()
						+ " and offset: " + record.offset());
				
				Payment payment = createPayment(record);
				
				System.out.println("\nPosting record " + payment.getCode() + " to partition \"1\" Payment...");
				
				ProducerUtils.executeProducer(producerPayment, ClientConstants.TOPIC_NAME_SALES, 1, 
						payment.getCode(), payment);
				
				System.out.println("Record " + payment.getCode() + " posted!\n");
			});
			consumerOrder.commitAsync();
		}
	}

	/**
	 * Method responsible for creating the Avro Payment.
	 * 
	 * @param record Avro Order data.
	 * @return payment Avro Payment.
	 */
	private static Payment createPayment(ConsumerRecord<String, Order> record) {
		
		// Total number of products with java 8 and lambdas.
		Integer quantity = record.value().getProducts()
				.stream()
				.map(product -> product.getProductAmount())
				.mapToInt(Integer::intValue)
				.sum();
		
		// Total order value with java 8 and lambdas.
		Double total = record.value().getProducts()
				.stream()
				.map(product -> product.getProductPrice())
				.mapToDouble(Double::doubleValue)
				.sum();
		
		return Payment.newBuilder()
				.setCode("pay-" + record.key())
				.setOrder(record.value())
				.setTotalQuantity(quantity)
				.setTotalValue(total)
				.build();
	}
	
}
