package br.com.barroso.kafka.avroclient.client.consumer;

import java.time.Duration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;

import br.com.barroso.kafka.avroclient.avro.ConfirmDelivery;
import br.com.barroso.kafka.avroclient.avro.StockSeparation;
import br.com.barroso.kafka.avroclient.client.ClientConstants;
import br.com.barroso.kafka.avroclient.client.producer.ProducerKStringVAvroClient;
import br.com.barroso.kafka.avroclient.utils.ProducerUtils;

/**
 * Class responsible to prepare and consumer Avro StockSeparation data and post in confirmDelivery partition.
 * 
 * @author Andre Barroso.
 */
public class RunConsumerStockSeparationClient {
	
	/**
	 * Method responsible for execute the Avro consumer.
	 * @param args Not used.
	 * @throws Exception General exception error.
	 */
	public static void main(String[] args) throws Exception {
		
		// Creating StockSeparation consumer.
		Consumer<String, StockSeparation> consumerStockSeparation = ConsumerKStringVAvroClient.createConsumer(
				ClientConstants.KAFKA_BROKERS,
				ClientConstants.GROUP_ID_CONSUMERS + "_stockSeparation",
				ClientConstants.CLIENT_ID_CONSUMER + "_stockSeparation",
				ClientConstants.MAX_POLL_RECORD,
				ClientConstants.OFFSET_RESET_EARLIEST,
				ClientConstants.TOPIC_NAME_SALES,
				3);
		
		// Creating ConfirmDelivery producer.
		Producer<String, ConfirmDelivery> producerConfirmDelivery = ProducerKStringVAvroClient.createProducer(
				ClientConstants.KAFKA_BROKERS, 
				ClientConstants.CLIENT_ID_PRODUCER + "_confirmDelivery", 
				ClientConstants.ACK_ALL);
		
		int noMessageFound = 0;

		// Closes the consumer and producer before exiting execution.
		Runtime.getRuntime().addShutdownHook(new Thread(()-> {
			
			System.out.println("\nClosing consumer stock separation...");
			consumerStockSeparation.close();
			System.out.println("Consumer stock separation closed!");
			
			System.out.println("\nClosing producer delivery...");
			producerConfirmDelivery.flush();
			producerConfirmDelivery.close();
			System.out.println("Producer delivery closed!");
		}));
		
		while(true) {
			ConsumerRecords<String, StockSeparation> consumerRecords = consumerStockSeparation.poll(Duration.ofMillis(100));

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
				
				System.out.println("Consuming Stock Separation record key: " + record.key() + " with value: " + record.value()
						+ " with partition: " + record.partition()
						+ " and offset: " + record.offset());
				
				ConfirmDelivery confirmDelivery = createConfirmDelivery(record);
				
				System.out.println("\nPosting record " + confirmDelivery.getCode() + " to partition \"4\" Confirmation Delivery...");
				
				ProducerUtils.executeProducer(producerConfirmDelivery, ClientConstants.TOPIC_NAME_SALES, 4, 
						confirmDelivery.getCode(), confirmDelivery);
				
				System.out.println("Record " + confirmDelivery.getCode() + " posted!\n");
			});
			consumerStockSeparation.commitAsync();
		}
	}

	/**
	 * Method responsible for create the Avro ConfirmDelivery.
	 * 
	 * @param record Avro StockSeparation data.
	 * @return confirmDelivery Avro ConfirmDelivery.
	 */
	private static ConfirmDelivery createConfirmDelivery(ConsumerRecord<String, StockSeparation> record) {
		
		return ConfirmDelivery.newBuilder()
				.setCode("cd-" + record.key())
				.setOrder(record.value().getOrder())
				.setInvoiceCode(record.value().getInvoiceCode())
				.build();
	}
	
}
