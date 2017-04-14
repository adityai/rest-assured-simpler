# rest-assured-docker
[![Build Status](http://circleci-badges-max.herokuapp.com/img/adityai/rest-assured-simpler?token=c521012b8c872719c418db3077b7763a3f9cc836)](https://circleci.com/gh/adityai/rest-assured-simpler)

Simple rest assured test suite that works for testing status code and one json value. Optionally runs on a Docker container.

docker run -it -v `` `pwd` ``:/usr/src/app adityai/rest-assured-simpler mvn clean test

To test any REST web service, add a new line in src/test/resources/ExcelTestData.xlsx

TestCaseName: Any unique test case name
ServiceUri:	The exact http uri for the service end-point
JsonPath: The full path of the Json key that needs to be verified
JsonValueExpected: The expected value of the Json key
HttpStatusCode: The http response code expected

Then just run the test suite by executing 'mvn clean test'.
