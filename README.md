# rise-api
#### note: the arc need to be linux/amd64 or linux/arm64/v8 (tested on m1) 
## requirement
### .env file
```bash
touch .env
ENV_FILE=".env"
echo "POSTGRES_USER=tomer" > $ENV_FILE
echo "POSTGRES_PASSWORD=123456789" >> $ENV_FILE
echo "POSTGRES_DB=management" >> $ENV_FILE
echo "POSTGRES_PORT=5432" >> $ENV_FILE
echo "POSTGRES_HOST=sql" >> $ENV_FILE
```
## RUN
```bash
docker compose up --build
```
Note:
* in the dockerfile and docker-compose.yaml files, you can remove and add the .m2 folder the purpose is that if a new dependency added not to download all from the start it is a long process in production it can be removed

## how to use
1) run the container
2) For the rest api: go to http://localhost:8080/swagger-ui/index.html
3) go to public-api and test the program by yourself
4) go to internal-api put the username: "user" and password: "password" (on top right there is a green lock press it ):
   * in the actuator/prometheus you can see a lot of metric and extract them to another monitoring service like grafana
   * in the actuator/metrics you can see a list of all the available metrics
   * in the actuator/metrics/{requiredMetricName} you can see choose which one you want to see 
