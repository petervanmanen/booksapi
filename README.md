# TOPbooksapi

## Goal
This is the TOPbook api. It allows the user to query the google books api with text and language.

## Documentation
The api is documented according to the OpenAPI specification. Currently the swagger-ui application is deployed with the api to view the documentation and test the api.

## Running bookapi

There are two ways to run the book api
1. mvn run:springboot (or mvn test to run the test)
2. docker build . --tag topbooks && docker run -d -p 8080:8080 topbooks

When its running goto http://localhost:8080/swagger-ui/index.html in a browser 