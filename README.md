##Code Validator

This is a REST API project that has one service to validate the given code to ensure that it adheres to the business requirements. It validates the length, service indicator code,
checkdigit and the country code. It also calculates the checkdigit using the input serial number to verify its value.

This REST service can be accessed in two ways. By passing the code value as path parameter or by passing it as request param.
``````
http://localhost:8080/validate/{code}
or
http://localhost:8080/validate?code={code}
`````````````
##API-Docs
`````````````
Swagger is configured to create the API documentation for this project.
"codevalidator_api-docs.json" is the api-doc file that is available inside the api-docs directory.
API docs can also be accessed within the app as below.
http://localhost:8080/v2/api-docs

http://localhost:8080/swagger-ui.html
`````````````
##Sample Request
`````````````
Json ->
{
	"messageHeader":{
       "id": "123",
       "sentDateTime": "2021-11-17T04:52:35.694Z",
       "channelName": "API_GATEWAY"
	},
"messageBody":{
 "payId":"123",
 "methodOfPay":"BOOK",
 "transferType":"CREDIT",
 "transactionType":"V2V",
 "transactionDetail": [
    {
	 "rxAcId":"TSIP",
	 "senderAcId": "TVAT",
	 "amount":1.8,
     "serviceCode": "IN1001",
	 "peCode": "10004",
	 "txId": "abc",
	 "mopId": "MC",
	 "eodId": "EURO2020278",
	 "poId":"abc"
	},
	{
	"rxAcId":"TDMNET",
	 "senderAcId": "TREVIN",
	 "amount":1.8,
	 "serviceCode": "BK10",
	 "peCode": "10004",
	 "txId": "abc",
	 "mopId": "MC",
	 "eodId": "EURO2020278",
	 "poId":"abc"
	},
	{
	  "rxAcId":"TREVIN",
	 "senderAcId": "TSIP",
	 "amount":1.8,
	  "serviceCode": "BK10",
	 "peCode": "10004",
	 "txId": "abc",
	 "mopId": "MC",
	 "eodId": "EURO2020278",
	 "poId":"abc"
	},
	{
	  "rxAcId":"TSIP",
	 "senderAcId": "TDMNET",
	 "amount":1.8,
	  "serviceCode": "LC13",
	 "peCode": "10004",
	 "txId": "abc",
	 "mopId": "MC",
	 "eodId": "EURO2020278",
	 "poId":"abc"
	}
	]
 }
}
	 
 

`
