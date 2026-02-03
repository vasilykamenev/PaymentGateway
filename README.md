# Instructions for candidates

This is the Java version of the Payment Gateway challenge. If you haven't already read this [README.md](https://github.com/cko-recruitment/) on the details of this exercise, please do so now.

## Requirements
- JDK 17
- Docker

## Template structure

src/ - A skeleton SpringBoot Application

test/ - Some simple JUnit tests

imposters/ - contains the bank simulator configuration. Don't change this

.editorconfig - don't change this. It ensures a consistent set of rules for submissions when reformatting code

docker-compose.yml - configures the bank simulator


# Payment gateway Documentation
A payment gateway is an API-based application that allows a merchant to offer their customers a payment method based on the user's bank card details.
![pipe](.img/card_payment_overview.png)
The application does not contain authorization data from the user, this service must be provided by the seller themselves.
The payment gateway only checks credit card data, allowing requests to be sent to the bank only with data that has been verified to match the values.

### Bank card data validation
During the first stage of value verification, all values entered by the Shopper and provided by the Merchant are verified.
If the values fail the first stage, the Merchant transaction will be Rejected.

### Bank card transaction
When processing a transaction on the bank's side, the transaction may be Authorized or Decline, in both cases a notification will be sent to the merchant.

### API Documentation
For documentation openAPI is included, and it can be found under the following url: **http://localhost:8090/swagger-ui/index.html**

