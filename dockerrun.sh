docker run --name nacos-quick -e MODE=standalone -e NACOS_AUTH_TOKEN=SecretKey012345678901234567890123456789012345678901234567890123456789 -e NACOS_AUTH_ENABLE=true -e NACOS_AUTH_IDENTITY_KEY=serverIdentity -e NACOS_AUTH_IDENTITY_VALUE=security -p 8848:8848 -d nacos/nacos-server:v2.2.3
docker run -p 7000:9000 -p 7001:9001 -e MINIO_ACCESS_KEY=admin -e MINIO_SECRET_KEY=admin123 -d minio/minio server /data --console-address ":9001"
docker run --name ssyx-mysql -e MYSQL_ROOT_PASSWORD="root" -d -p 3306:3306 mysql:5.7
docker network create es-network
docker run -d --name elasticsearch --net es-network -p 9200:9200 -p 9300:9300 -e "discovery.type=single-node" elasticsearch:7.8.0
docker run -d --name kibana --net es-network -p 5601:5601 kibana:7.8.0