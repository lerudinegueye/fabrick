# fabrick application

1-******** External Properties of this application are loaded from a local file tha must be created with this path below:*****
C:\fabrickProperties\fabrick.properties

2 -*****Into the file fabrick.properties, we must add this variables below:*****
fabrick.accountId=14537780
fabrick.baseUrl=https://sandbox.platfr.io
fabrick.contextUrl=/api/gbs/banking/v4.0/accounts/{accountId}
fabrick.url=https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/{accountId}
fabrick.urlBalance=https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/{accountId}/balance
fabrick.urlMoneyTransfer=https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/{accountId}/payments/money-transfers
fabrick.urlTransaction=https://sandbox.platfr.io/api/gbs/banking/v4.0/accounts/{accountId}/transactions

3-*******SWAGGER*******

  3.1 - To get the swagger of this application, after starting, go to this endpoint:http://localhost:8080/v3/api-docs

  3.2 - To see graphically existing endpoints, launch to your browser:http://localhost:8080/swagger-ui/index.html

4-*****Endpoint in localhost for this application***********

   *********Common Headers key value pairs for all calls endpoints:********
   Auth-Schema:S2S
   Api-Key:FXOVVXXHVCPVPBZXIJOBGUGSKHDNFRRQJP
   Content-Type:application/json
   X-Time-Zone:Europe/Rome

  4-1:GET Method - Allows to get the account balance go in postman create and launch this *****endpoint:http://localhost:8080/api/gbs/banking/v4.0/accounts/14537780/balance*****				    
  
  
  4-2 :POST METHOD - Allows to do money transfer 
*****endpoint:http://localhost:8080/api/gbs/banking/v4.0/accounts/14537780/payments/money-transfers*****
  
  
  4-3 : GET Method - Allows to retrieve transaction of a user accountId between a range of two dates
  Here we add offset,limit,pagination for handling list of transactions returned into the response
*****endpoint:http://localhost:8080/api/gbs/banking/v4.0/accounts/14537780/transactions?fromAccountingDate=2019-11-01&toAccountingDate=2019-12-01&pagination=true

****Database H2 in Memory or File in local system****
For H2 web console:http://localhost:8080/h2-console

For persisting database in local file: create this folder that you will pass to h2 console web as last part of url connection:
C:/fabrickdb/fabrick
refer to application.properties file:<--> spring.datasource.url=jdbc:h2:file:C:/fabrickdb/fabrick
So you must create this directory C:/fabrickdb/fabrick
user:sa
password:testFabrick
  