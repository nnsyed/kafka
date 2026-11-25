import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.*;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class KafkaDemo {
    private static final String BOOTSTRAP_SERVER = "localhost:9092";
    private static final String TOPIC_NAME = "orders";

    public static void main(String[] args) throws InterruptedException {
        // 1. Run the Producer
        sendKafkaMessage();

        // Pause briefly to let the broker register the message
        Thread.sleep(1000);

        // 2. Run the Consumer
        receiveKafkaMessage();
    }

    private static void sendKafkaMessage() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        System.out.println("\n🚀 Creating Producer...");
        try (Producer<String, String> producer = new KafkaProducer<>(props)) {
            ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, "Hello Kafka from Java!");
            
            System.out.println("Sending message: '" + record.value() + "'...");
            producer.send(record, (metadata, exception) -> {
                if (exception == null) {
                    System.out.println("✅ Message sent successfully to topic: " + metadata.topic());
                } else {
                    System.err.println("❌ Message delivery failed: " + exception.getMessage());
                }
            });
            producer.flush();
        }
    }

    private static void receiveKafkaMessage() {
        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "java-demo-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        System.out.println("\n🎧 Starting Consumer...");
        try (Consumer<String, String> consumer = new KafkaConsumer<>(props)) {
            consumer.subscribe(Collections.singletonList(TOPIC_NAME));

            // Poll for a maximum of 5 seconds to look for our message
            int retryCount = 0;
            while (retryCount < 5) {
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                if (!records.isEmpty()) {
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.println("📥 Received message: '" + record.value() + "'");
                    }
                    break; // Exit once we read the message
                }
                retryCount++;
            }
        }
    }
}

