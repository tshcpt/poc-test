#Barcode Validator

This is a REST API project that has one service to validate the given barcode to ensure that it adheres to the business requirements. It validates the length, service indicator code,
checkdigit and the country code. It also calculates the checkdigit using the input serial number to verify its value.

This REST service can be accessed in two ways. By passing the barcode value as path parameter or by passing it as request param.

http://localhost:8080/validate/{barcode}
or
http://localhost:8080/validate?barcode={barcode}

#API-Docs
Swagger is configured to create the API documentation for this project.
"barcodevalidator_api-docs.json" is the api-doc file that is available inside the api-docs directory.
API docs can also be accessed within the app as below.
http://localhost:8080/v2/api-docs
http://localhost:8080/swagger-ui.html


