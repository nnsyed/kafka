import time
from confluent_kafka import Producer, Consumer, KafkaError

# Configuration based on your podman-compose.yaml
BOOTSTRAP_SERVER = 'localhost:9092'
TOPIC_NAME = 'test-topic'

# ==========================================
# 1. PRODUCER: Sending a Message
# ==========================================
def delivery_report(err, msg):
    """ Callback called once message is delivered or failed """
    if err is not None:
        print(print(f"❌ Message delivery failed: {err}"))
    else:
        print(f"✅ Message sent successfully to {msg.topic()} [{msg.partition()}]")

def send_message():
    p_config = {'bootstrap.servers': BOOTSTRAP_SERVER}
    producer = Producer(p_config)

    message_payload = "Hello Kafka from Podman Compose!"
    print(f"\n🚀 Sending message: '{message_payload}'...")
    
    # Produce message asynchronously
    producer.produce(TOPIC_NAME, message_payload.encode('utf-8'), callback=delivery_report)
    
    # Wait for any outstanding messages to be delivered
    producer.flush()

# ==========================================
# 2. CONSUMER: Receiving the Message
# ==========================================
def receive_message():
    c_config = {
        'bootstrap.servers': BOOTSTRAP_SERVER,
        'group.id': 'python-demo-group',
        'auto.offset.reset': 'earliest' # Read from the beginning of the topic
    }
    consumer = Consumer(c_config)
    consumer.subscribe([TOPIC_NAME])

    print("\n🎧 Listening for messages (Press Ctrl+C to stop)...")
    try:
        # Polling loop to fetch the message we just sent
        timeout = 5.0  # seconds
        start_time = time.time()
        
        while time.time() - start_time < timeout:
            msg = consumer.poll(1.0)
            
            if msg is None:
                continue
            if msg.error():
                if msg.error().code() == KafkaError._PARTITION_EOF:
                    continue
                else:
                    print(f"❌ Consumer error: {msg.error()}")
                    break

            # Print the received message
            print(f"📥 Received message: '{msg.value().decode('utf-8')}'")
            break # Exit after receiving our message for demonstration purposes
            
    except KeyboardInterrupt:
        pass
    finally:
        consumer.close()

# ==========================================
# Main Execution
# ==========================================
if __name__ == '__main__':
    send_message()
    time.sleep(3) # Small pause before consuming
    receive_message()

