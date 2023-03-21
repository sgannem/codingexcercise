# clarity identity services inc. codeassignment

### how to run the project

#### pre-requisites
* jdk 17
* maven 3.0.5 and above
#### how to run project
```
git clone <project-git-hub-url>
cd <cloned-project>
mvn clean install
java -jar target/codeassignment-0.0.1-SNAPSHOT.jar
```

### swagger endpoint.
* http://localhost:9081/swagger-ui/index.html

### Sample curl command(s)

#### ping api request
```
curl -X 'GET' \
  'http://localhost:9081/ping' \
  -H 'accept: */*'
```
#### ping api response
```
pong
```

#### upload api request
```
curl -X 'POST' \
  'http://localhost:9081/upload' \
  -H 'accept: */*' \
  -H 'Content-Type: multipart/form-data' \
  -F 'file=@EWNworkstreamAutomationInput.xlsx;type=application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'                     

```
#### upload api response
```
File processed successfully...
```

### Reference Documentation
For further reference, please consider the following sections:
#### References
* https://stackoverflow.com/questions/74701738/spring-boot-3-springdoc-openapi-ui-doesnt-work