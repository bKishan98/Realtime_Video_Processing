package bibhu.kishan.kafka.project;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.opencv.core.Core;

import java.time.Duration;
import java.util.Collections;

public class EnrichmentProcessor {
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        String rawVideoTopic = "rawVideoTopic";
        String enrichedVideoTopic = "enrichedVideoTopic";
        String groupId = "VideoConsumer"; // Provide your desired group ID
        Consumer<String, byte[]> consumer = KafkaConsumerInstance.createConsumer(groupId); // Instantiate your Kafka consumer
        Producer<String, byte[]> producer = KafkaProducerInstance.createProducer(); // Instantiate your Kafka producer

        consumer.subscribe(Collections.singletonList(rawVideoTopic));

        try {
            while (true) {
                ConsumerRecords<String, byte[]> records = consumer.poll(Duration.ofMillis(100));

                for (ConsumerRecord<String, byte[]> record : records) {
                    byte[] rawImageData = record.value();

                    // Apply enrichment using OpenCV
                    byte[] enrichedImageData = EnrichmentUtils.applyEnrichment(rawImageData);

                    // Produce the enriched video data to Kafka
                    ProducerRecord<String, byte[]> enrichedRecord = new ProducerRecord<>(enrichedVideoTopic, "key", enrichedImageData);
                    producer.send(enrichedRecord);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            consumer.close(); // Close the Kafka consumer
            producer.close(); // Close the Kafka producer
        }
    }
}
