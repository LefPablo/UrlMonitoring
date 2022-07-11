# UrlMonitoring
URL Monitoring Service with Email Reports
> Run all following commands from project root

## Build

Run command `mvn clean install`

## Configure
Setup environment variables in `env-vars` file (it will be used by container during run) <br>
Also you can rewrite parameters in `application.properties` and `project.properties`,<br>
for now they contain default values and environment variables that you should pass to docker container.

## Deploy
Prepare docker image with command `docker build -t <image name>`.<br>
Then run docker image with command <br>
`docker run --name <container name> --env-file ./env-vars -d -p <hostPort>:8080 <image name>`
> Host port should be free to be able to connect it with docker
 
http://localhost:8080/swagger-ui/index.html

