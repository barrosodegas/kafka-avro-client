package br.com.barroso.kafka.avroclient.client.consumer;

import java.time.Duration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import br.com.barroso.kafka.avroclient.avro.ConfirmDelivery;
import br.com.barroso.kafka.avroclient.client.ClientConstants;

/**
 * Class responsible to prepare and consumer Avro ConfirmDelivery data and generate log to finished process.
 * 
 * @author Andre Barroso.
 */
public class RunConsumerConfirmDeliveryClient {
	
	/**
	 * Method responsible for execute the Avro consumer.
	 * @param args Not used.
	 * @throws Exception General exception error.
	 */
	public static void main(String[] args) throws Exception {
		
		// Creating ConfirmDelivery consumer.
		Consumer<String, ConfirmDelivery> consumerConfirmDelivery = ConsumerKStringVAvroClient.createConsumer(ClientConstants.KAFKA_BROKERS,
				ClientConstants.GROUP_ID_CONSUMERS + "_confirmDelivery",
				ClientConstants.CLIENT_ID_CONSUMER + "_confirmDelivery",
				ClientConstants.MAX_POLL_RECORD,
				ClientConstants.OFFSET_RESET_EARLIEST,
				ClientConstants.TOPIC_NAME_SALES,
				4);
		
		int noMessageFound = 0;

		// Closes the consumer and producer before exiting execution.
		Runtime.getRuntime().addShutdownHook(new Thread(()-> {
			
			System.out.println("\nClosing consumer delivery confirmation...");
			consumerConfirmDelivery.close();
			System.out.println("Consumer delivery confirmation closed!");
		}));
		
		while(true) {
			ConsumerRecords<String, ConfirmDelivery> consumerRecords = consumerConfirmDelivery.poll(Duration.ofMillis(100));

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
				
				System.out.println("Consuming Delivery Confirmation record key: " + record.key() + " with value: " + record.value()
						+ " with partition: " + record.partition()
						+ " and offset: " + record.offset());
				
				System.out.println("\nSending email of record " + record.key() + " to customer: " 
						+ record.value().getOrder().getCustomer().getCode());
			});
			consumerConfirmDelivery.commitAsync();
		}
	}
	
}
