package br.com.barroso.kafka.avroclient.client.consumer;

import java.time.Duration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;

import br.com.barroso.kafka.avroclient.avro.Invoice;
import br.com.barroso.kafka.avroclient.avro.StockSeparation;
import br.com.barroso.kafka.avroclient.client.ClientConstants;
import br.com.barroso.kafka.avroclient.client.producer.ProducerKStringVAvroClient;
import br.com.barroso.kafka.avroclient.utils.ProducerUtils;

/**
 * Class responsible to prepare and consumer Avro Invoice data and post in StockSeparation partition.
 * 
 * @author Andre Barroso.
 */
public class RunConsumerInvoiceClient {
	
	/**
	 * Method responsible for execute the Avro consumer.
	 * @param args Not used.
	 * @throws Exception General exception error.
	 */
	public static void main(String[] args) throws Exception {
		
		// Creating Invoice consumer.
		Consumer<String, Invoice> consumerInvoice = ConsumerKStringVAvroClient.createConsumer(
				ClientConstants.KAFKA_BROKERS,
				ClientConstants.GROUP_ID_CONSUMERS + "_invoice",
				ClientConstants.CLIENT_ID_CONSUMER + "_invoice",
				ClientConstants.MAX_POLL_RECORD,
				ClientConstants.OFFSET_RESET_EARLIEST,
				ClientConstants.TOPIC_NAME_SALES,
				2);
		
		// Creating StockSeparation consumer.
		Producer<String, StockSeparation> producerStockSeparation = ProducerKStringVAvroClient.createProducer(
				ClientConstants.KAFKA_BROKERS, 
				ClientConstants.CLIENT_ID_PRODUCER + "_stockSeparation", 
				ClientConstants.ACK_ALL);
		
		int noMessageFound = 0;

		// Closes the consumer and producer before exiting execution.
		Runtime.getRuntime().addShutdownHook(new Thread(()-> {
			
			System.out.println("\nClosing consumer invoice...");
			consumerInvoice.close();
			System.out.println("Consumer invoice closed!");
			
			System.out.println("\nClosing producer stock separation...");
			producerStockSeparation.flush();
			producerStockSeparation.close();
			System.out.println("Producer stocke separation closed!");
		}));
		
		while(true) {
			ConsumerRecords<String, Invoice> consumerRecords = consumerInvoice.poll(Duration.ofMillis(100));

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
				
				System.out.println("Consuming Invoice record key: " + record.key() + " with value: " + record.value()
						+ " with partition: " + record.partition()
						+ " and offset: " + record.offset());
				
				StockSeparation stockSeparation = createStockSeparation(record);
				
				System.out.println("\nPosting record " + stockSeparation.getCode() + " to partition \"3\" Stock Separation...");
				
				ProducerUtils.executeProducer(producerStockSeparation, ClientConstants.TOPIC_NAME_SALES, 3, 
						stockSeparation.getCode(), stockSeparation);
				
				System.out.println("Record " + stockSeparation.getCode() + " posted!\n");
			});
			consumerInvoice.commitAsync();
		}
	}


	/**
	 * Method responsible for create the Avro StockSeparation.
	 * 
	 * @param record Avro SotkcSeparatation data.
	 * @return stockSeparation Avro StockSeparation.
	 */
	private static StockSeparation createStockSeparation(ConsumerRecord<String, Invoice> record) {
		
		return StockSeparation.newBuilder()
				.setCode("stock-" + record.key())
				.setOrder(record.value().getPayment().getOrder())
				.setInvoiceCode(record.value().getCode())
				.build();
	}
	
}
