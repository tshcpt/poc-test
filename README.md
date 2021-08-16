#Code Validator

This is a REST API project that has one service to validate the given code to ensure that it adheres to the business requirements. It validates the length, service indicator code,
checkdigit and the country code. It also calculates the checkdigit using the input serial number to verify its value.

This REST service can be accessed in two ways. By passing the code value as path parameter or by passing it as request param.

http://localhost:8080/validate/{code}
or
http://localhost:8080/validate?code={code}

#API-Docs

Swagger is configured to create the API documentation for this project.
"codevalidator_api-docs.json" is the api-doc file that is available inside the api-docs directory.
API docs can also be accessed within the app as below.
http://localhost:8080/v2/api-docs

http://localhost:8080/swagger-ui.html


