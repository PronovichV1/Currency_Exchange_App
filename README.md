# Currency Exchange App

## About the project

---
REST API for exchange check and change currencies and exchange rates, make conversion of arbitrary amounts from one to
another and back.

## Tech Stack

---

- Java 21
- Maven 21
- Jakarta Servlet API 6.1
- SQLite
- JDBC
- Jackson
- Apache Tomcat
- Lombok
- slf4j
- Map Struct

---

## Features

REST API methods which is implements CRUD interface on database - allows to create, read, and update.

- can show all available currencies in database
- receiving a specific currency
- adding new currency

---

- receiving list of all exchange rates
- receiving specific exchange Rate
- adding new exchange rate in database
- updating available exchange rate amount

---

- calculation of the transfer amount of a certain amount of funds from one to another

---

## Prerequisites

- JDK 21
- Maven
- Apache Tomcat9+ or Tomcat 10

## Instalation/Setup

### Local Setup

1. **Clone the repository**

   _Enter these commands into your terminal:_
   ````
   git clone https://github.com/PronovichV1/Currency_Exchange_App.git
    cd Currency_Exchange_App
   ````
2. **Build the project using Maven**

   _Enter these command into your terminal_
    ````
   mvn clean package
   ````
3. **Start Tomcat** - run the startup script from the Tomcat `bin/` directory:
   (`bin/startup.sh` in Linux/macOS or `bin/startup.bat` in Windows).
4. **Access the application** - the app will be available at: http://localhost:8080/currency-exchange/

---

## Database Scheme

This application use SQLite database

**Main database name**:
`currency_exchange.db` - has two tables

`currency` (id, full_name, code, sign)

`exchange_currencies` (id, base_currecny_id, target_currecny_id, rate)

### Limitations:

- the code in `currency` is unique;
- the `base_currecny_id` + `target_currecny_id` pair in `exchange_rates` is unique;
  `exchange_rates` is linked to `currency` via foreign keys.

---

## Endpoints examples:

1. **GET**`/currencies`

Retrieving the list of currencies. Response example:

````
[
    {
        "id": 0,
        "name": "United States dollar",
        "code": "USD",
        "sign": "$"
},   
    {
        "id": 0,
        "name": "Euro",
        "code": "EUR",
        "sign": "€"
    }
]
````

2. **GET**`/currency/EUR`

Retrieving a specific currency. Response example:

````
{
    "id": 0,
    "name": "Euro",
    "code": "EUR",
    "sign": "€"
}
````

3. **GET**`/exchangeRates`

Retrieving the list of all exchange rates. Response example:

````
[
    {
        "id": 0,
        "baseCurrency": {
            "id": 0,
            "name": "United States dollar",
            "code": "USD",
            "sign": "$"
        },
        "targetCurrency": {
            "id": 1,
            "name": "Euro",
            "code": "EUR",
            "sign": "€"
        },
        "rate": 0.99
    }
]
````

4. **GET**`/exchangeRate/USDRUB`

```
{
    "id": 0,
    "baseCurrency": {
        "id": 0,
        "name": "United States dollar",
        "code": "USD",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 2,
        "name": "Russian Ruble",
        "code": "RUB",
        "sign": "₽"
    },
    "rate": 80
}
```

5. **POST**`/exchangeRates`

Adding a new exchange rate to the database. The data is sent in the request body as form fields (x-www-form-urlencoded).
Form fields - baseCurrencyCode, targetCurrencyCode, rate. Example form fields:

- `baseCurrencyCode` - USD
- `targetCurrencyCode` - EUR
- `rate` - 0.99

Response example - JSON representation of the record inserted into the database, including its ID:

````
{
    "id": 0,
    "baseCurrency": {
        "id": 0,
        "name": "United States dollar",
        "code": "USD",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 1,
        "name": "Euro",
        "code": "EUR",
        "sign": "€"
    },
    "rate": 0.99
}
````

6. **PATCH**`/exchangeRate/USDRUB`

Updating an existing exchange rate in the database. The currency pair is specified by consecutive currency codes in the
request URL. The data is sent in the request body as form fields (`x-www-form-urlencoded`). The only form field is
`rate`.

Response example - JSON representation of the updated record in the database, including its ID:

````
{
    "id": 0,
    "baseCurrency": {
        "id": 0,
        "name": "United States dollar",
        "code": "USD",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 2,
        "name": "Russian Ruble",
        "code": "RUB",
        "sign": "₽"
    },
    "rate": 80
}
````

7. **GET**`/exchange?from=BASE_CURRENCY_CODE&to=TARGET_CURRENCY_CODE&amount=$AMOUNT`

Calculating the conversion of a specific amount of funds from one currency to another.

Example request - GET`/exchange?from=USD&to=AUD&amount=10`.

Response example:

````
{
    "baseCurrency": {
        "id": 0,
        "name": "United States dollar",
        "code": "USD",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 1,
        "name": "Australian dollar",
        "code": "AUD",
        "sign": "A$"
    },
    "rate": 1.45,
    "amount": 10.00,
    "convertedAmount": 14.50
}
````
