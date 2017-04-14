# rest-assured-docker
Simple rest assured test suite that works for testing status code and one json value. Optionally runs on a Docker container.

docker run -it -v `` `pwd` ``:/usr/src/app adityai/rest-assured-simpler mvn clean test

To test any REST web service, add a new line in src/test/resources/ExcelTestData.xlsx

TestCaseName: Any unique test case name
ServiceUri:	The exact http uri for the service end-point
JsonPath: The full path of the Json key that needs to be verified
JsonValueExpected: The expected value of the Json key
HttpStatusCode: The http response code expected

Then just run the test suite by executing 'mvn clean test'.
