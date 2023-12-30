# spring-boot-cloud-code

### Run mysql as docker container
> docker run --detach --env MYSQL_ROOT_PASSWORD=dev123 --env MYSQL_USER=dev --env MYSQL_PASSWORD=dev123 --env MYSQL_DATABASE=dev --name mysql --publish 3306:3306 mysql:8-oracle