# Check virtual maching ID (to set it as DOCKER_HOST_IP)
    docker-machine ip

# For standalone
Check is DOCKER_HOST_IP is set:
   echo $DOCKER_HOST_IP

./gradlew assemble -P eventuateDriver=local
export DOCKER_HOST_IP=192.168.99.100
docker-compose -f docker-compose-eventuate-local.yml build
docker-compose -f docker-compose-eventuate-local.yml up -d

# Check logs
Get ID of container we want to get log for
    docker ps

    docker logs [CONTAINER_ID]


Accessing
http://192.168.99.100:8080/swagger-ui.html
