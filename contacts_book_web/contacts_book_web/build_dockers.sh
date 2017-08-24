#!/bin/bash
sudo mvn docker:build
sudo docker network create --subnet=172.18.0.0/16 skynet
sudo mvn docker:stop
service postgresql stop
sudo mvn docker:start
OUTPUT="$(sudo docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' bubna_docker_postgresql)"
echo "ip of postgresql container is ${OUTPUT}"
OUTPUT="$(sudo docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' bubna_docker_tomcat)"
echo "ip of tomcat container is ${OUTPUT}"
echo "change pom.xml, context.xml, maven's settings.xml (i don't know where is it on your pc, sorry)"
