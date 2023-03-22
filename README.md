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

#### Algorithm to parse required tasks and generate rules.
algorithm String getRules(String requiredTasks) {
* Step  1. for(char ch:requiredTasks.toCharArray()) { // main for loop
* Step  2. if ( char is letter or digit) {
* Step  3. add it to result string
* Step  4. } else if( char is '&' || char is ',') {
* Step  5. push char to stack
* Step  6. missingListBean.add(result);
* Step  7. result = "";
* Step  8. } else if( char is '|')  {
* Step  9. push char to stack
* Step 10. missingSomeListBeans.add(result);
* Step 11. result = "";
* Step 12. } else if( char is '(' ) {
* Step 13. push char to stack
* Step 14. } else if ( char is ')' ) {
* Step 15. while(!stack.isEmpty && stack.peek()!= '(' ) {
* Step 16. char c = stack.pop();
* Step 17. if(c=='&' || c == ',') {
* Step 18. missingListBean.add(result);
* Step 19. result = "";
* Step 20. } else if( c == '|') {
* Step 21. missingSomeListBeans.add(result);
* Step 22. result = ""; }
* Step 23. }
* Step 24. stack.pop(); }
* Step 25. } // end of main for loop
* Step 26. if result is not empty then
* Step 27.  if(stack is empty) {
* Step 28.  missingSomeListBeans.add(result);
* Step 29. }else {
* Step 30.  top of stack is '&' or ',' then add result to missing bean list
* Step 31.  else then add result to missing_some beans list.
* Step 32. }
* Step 33. populate rules json string based on missing bean list and missing_some beans list and return rules string.
* Step 34. end;

### Reference Documentation
For further reference, please consider the following sections:
#### References
* https://stackoverflow.com/questions/74701738/spring-boot-3-springdoc-openapi-ui-doesnt-work
* https://www.youtube.com/watch?v=oynNU7D2w3Y