# Getting Started


## Cassandra
docker run --name my-cassandra -p 9042:9042 -d cassandra

docker exec -it my-cassandra cqlsh

CREATE KEYSPACE spring_cassandra WITH replication = {'class' : 'SimpleStrategy', 'replication_factor' : 1};


## Kafka
docker-compose -f docker-compose.yml up -d

