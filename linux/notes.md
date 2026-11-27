systemctl --user enable --now podman.socket
export DOCKER_HOST=unix://$XDG_RUNTIME_DIR/podman/podman.sock
curl --unix-socket $XDG_RUNTIME_DIR/podman/podman.sock  http://localhost/_ping

podman-compose.yaml
[nnsyed@youthrcclub-srv kafka]$ podman compose up -d
>>>> Executing external compose provider "/usr/local/bin/podman-compose". Please see podman-compose(1) for how to disable this message. <<<<

c3b059ede60e711c09b925e52991cc0a9c0c1630169ff47ae39840f255f490b2
15b7334b03fcc6b4b40ee28b26ce0f40816f01a078846867d1a6c3e69077eb49
02ec15e8045dd93b7a06bd96dd398170f9375b72c16a81812fb28aafbff1c420
kafka_zookeeper_1
kafka_kafka_1
[nnsyed@youthrcclub-srv kafka]$ podman ps
CONTAINER ID  IMAGE                                    COMMAND               CREATED             STATUS             PORTS                                                 NAMES
26421c69b12d  docker.io/library/zookeeper:3.6.3        zkServer.sh start...  About a minute ago  Up About a minute  0.0.0.0:2181->2181/tcp, 2888/tcp, 3888/tcp, 8080/tcp  kafka_zookeeper_1
02ec15e8045d  docker.io/wurstmeister/kafka:2.13-2.7.0  start-kafka.sh        10 seconds ago      Up 10 seconds      0.0.0.0:9092->9092/tcp                                kafka_kafka_1
[nnsyed@youthrcclub-srv kafka]



nnsyed@youthrcclub-srv kafka]$ podman ps
CONTAINER ID  IMAGE                                    COMMAND               CREATED         STATUS         PORTS                                                 NAMES
26421c69b12d  docker.io/library/zookeeper:3.6.3        zkServer.sh start...  12 minutes ago  Up 12 minutes  0.0.0.0:2181->2181/tcp, 2888/tcp, 3888/tcp, 8080/tcp  kafka_zookeeper_1
02ec15e8045d  docker.io/wurstmeister/kafka:2.13-2.7.0  start-kafka.sh        11 minutes ago  Up 11 minutes  0.0.0.0:9092->9092/tcp                                kafka_kafka_1
[nnsyed@youthrcclub-srv kafka]$ podman compose down
>>>> Executing external compose provider "/usr/local/bin/podman-compose". Please see podman-compose(1) for how to disable this message. <<<<

kafka_zookeeper_1
WARN[0010] StopSignal SIGTERM failed to stop container kafka_kafka_1 in 10 seconds, resorting to SIGKILL 
kafka_kafka_1
kafka_kafka_1
kafka_zookeeper_1
e881cab7477c5c16b33da3c2686ba83102d1908b240b6c724fc6fc404848e8d6
kafka_default
[nnsyed@youthrcclub-srv kafka]$ podman compose down

****************************************************************
#Access Kafka container:
docker exec -it <kafka_container_id> /bin/bash

# Locate Kafka binaries:
cd /opt/kafka/bin

# create topic
./kafka-topics.sh --create --topic test-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1

# Run Kafka Producer
./kafka-console-producer.sh --topic test-topic --bootstrap-server localhost:9092

# Run Kafka Consumer:
docker exec -it <kafka_container_id> /bin/bash
cd /opt/kafka/bin
./kafka-console-consumer.sh --topic test-topic --bootstrap-server localhost:9092 --from-beginning


## Creating a Topic with Multiple Partitions:
docker exec -it <kafka_container_id> /bin/bash
cd /kafka/bin
./kafka-topics.sh --create --topic test-topic --bootstrap-server localhost:9092 --replication-factor 1 --partitions


-----------------------------------------------
# list topics
bash-5.1# ./kafka-topics.sh --bootstrap-server localhost:9092 --list

# create topic
bash-5.1# ./kafka-topics.sh --create --topic orders --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1
Created topic orders.

# list topics
bash-5.1# ./kafka-topics.sh --bootstrap-server localhost:9092 --list
orders

# Run Kafka Producer
./kafka-console-producer.sh --topic orders --bootstrap-server localhost:9092


./kafka-console-consumer.sh --topic orders --bootstrap-server localhost:9092 --from-beginning

# deleting topics
/opt/kafka/bin/kafka-topics.sh --delete --topic orders --bootstrap-server localhost:9092

#
bash-5.1# ./kafka-topics.sh --create --topic orders --bootstrap-server localhost:9092 --partitions 3 --replication-factor 1
Created topic orders.

bash-5.1# ./kafka-consumer-groups.sh --list --bootstrap-server localhost:9092
payment-group
notification-group
inventory-group



