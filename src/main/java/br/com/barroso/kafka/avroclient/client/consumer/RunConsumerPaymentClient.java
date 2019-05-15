package br.com.barroso.kafka.avroclient.client.consumer;

import java.time.Duration;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;

import br.com.barroso.kafka.avroclient.avro.Invoice;
import br.com.barroso.kafka.avroclient.avro.Payment;
import br.com.barroso.kafka.avroclient.client.ClientConstants;
import br.com.barroso.kafka.avroclient.client.producer.ProducerKStringVAvroClient;
import br.com.barroso.kafka.avroclient.utils.ProducerUtils;

/**
 * Class responsible to prepare and consumer Avro Payment data and post in Invoice partition.
 * 
 * @author Andre Barroso.
 */
public class RunConsumerPaymentClient {
	
	
	/**
	 * Method responsible for execute the Avro consumer.
	 * @param args Not used.
	 * @throws Exception General exception error.
	 */
	public static void main(String[] args) throws Exception {
		
		// Creating Payment consumer.
		Consumer<String, Payment> consumerPayment = ConsumerKStringVAvroClient.createConsumer(
				ClientConstants.KAFKA_BROKERS,
				ClientConstants.GROUP_ID_CONSUMERS + "_payment",
				ClientConstants.CLIENT_ID_CONSUMER + "_payment",
				ClientConstants.MAX_POLL_RECORD,
				ClientConstants.OFFSET_RESET_EARLIEST,
				ClientConstants.TOPIC_NAME_SALES,
				1);
		
		// Creating Invoice producer.
		Producer<String, Invoice> producerInvoice = ProducerKStringVAvroClient.createProducer(
				ClientConstants.KAFKA_BROKERS, 
				ClientConstants.CLIENT_ID_PRODUCER + "_invoice", 
				ClientConstants.ACK_ALL);
		
		int noMessageFound = 0;

		// Closes the consumer and producer before exiting execution.
		Runtime.getRuntime().addShutdownHook(new Thread(()-> {
			
			System.out.println("\nClosing consumer payment...");
			consumerPayment.close();
			System.out.println("Consumer payment closed!");
			
			System.out.println("\nClosing producer invoice...");
			producerInvoice.flush();
			producerInvoice.close();
			System.out.println("Producer invoice closed!");
		}));
		
		while(true) {
			ConsumerRecords<String, Payment> consumerRecords = consumerPayment.poll(Duration.ofMillis(100));

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
				
				System.out.println("Consuming Payment record key: " + record.key() + " with value: " + record.value()
						+ " with partition: " + record.partition()
						+ " and offset: " + record.offset());
				
				Invoice invoice = createInvoice(record);
				
				System.out.println("\nPosting record " + invoice.getCode() + " to partition \"2\" Invoice...");
				
				ProducerUtils.executeProducer(producerInvoice, ClientConstants.TOPIC_NAME_SALES, 2, 
						invoice.getCode(), invoice);
				
				System.out.println("Record " + invoice.getCode() + " posted!\n");
			});
			consumerPayment.commitAsync();
		}
	}

	/**
	 * Method responsible for create the Avro Invoice.
	 * 
	 * @param record Avro Payment data.
	 * @return invoice Avro Invoice.
	 */
	private static Invoice createInvoice(ConsumerRecord<String, Payment> record) {
		
		return Invoice.newBuilder()
				.setCode("inv-" + record.key())
				.setPayment(record.value())
				.build();
	}
	
}
