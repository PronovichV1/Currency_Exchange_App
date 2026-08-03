# Currency Exchange App

## About the project

Pet project for educational purpose.

REST API for describing currencies and exchange rates. It allows users to browse and modify exchange rate and currency
lists, as well as calculate conversions for arbitrary amounts from one currency to another.

## Tech Stack

- Java 21
- Maven
- Jakarta Servlet API 6.1
- SQLite
- JDBC
- Jackson
- Apache Tomcat
- slf4j
- MapStruct

## Features

The REST API implements standard data management operations on the database, allowing you to create, read, and update
records:

### Currency Management

- View all available currencies.
- Retrieve a specific currency by its code.
- Add a new currency to the system.

### Exchange Rate Management

- View all available exchange rates.
- Fetch a specific exchange for a currency pair.
- Add a new exchange rate to the database.
- Update an existing exchange rate value.

### Conversion Engine

- Calculate the conversion of the specific amount from a base currency to a target currency.

## Prerequisites & Installation

### Prerequisites

- **JDK 21**
- **Maven**
- **Apache Tomcat 9+ or Tomcat 10**

### Local Setup

1. **Clone the repository**

   _Enter these commands into your terminal:_
   ```
   git clone https://github.com/PronovichV1/Currency_Exchange_App.git
   cd Currency_Exchange_App
   ```
2. **Change the host path:**
   - Navigate to `Currency_Exchange_App/src/main/webapp/js/app.js`.
   - On line 2, change `host` to `http://localhost:8080/currency-exchange`
   
3. To run the project locally, update the database file path in `ConnectionManager.java` to match your local project folder:
   
   ``` java
   if (dbPath == null) {
   // Replace with your local path to currency_exchange.db
   dbPath = "C:/your/local/path/to/currency_exchange.db";
   }
   ```
   
3. **Build the project using Maven**

   _Enter this command into your terminal_
    ``` bash
   mvn clean package
   ```
4. **Deploy the application to Tomcat:** 
   - Navigate to `Currency_Exchange_App/target/` and copy `currency-exchange.war`.
   - Paste it into your Tomcat `webapps/` directory.
5. **Start Tomcat:**
  - Execute the startup script from the Tomcat `bin/` directory:
  - **Linux / macOS:** `./bin/startup.sh`
  - **Windows:** `bin/startup.bat`
6. **Access the application** - the app will be available at: http://localhost:8080/currency-exchange/

### Remote Deployment

1. **Clone the repository**

   _Enter these commands into your terminal:_
   ``` bash
   git clone https://github.com/PronovichV1/Currency_Exchange_App.git
   cd Currency_Exchange_App
   ```
2. **Change the host path:**
   - Navigate to `Currency_Exchange_App/src/main/js/app.js`.
   - On line 2, change `host` to `http://<your-server-ip>:8080/currency-exchange`.

3. **Build the project using Maven**

   _Enter this command into your terminal_
    ``` bash
   mvn clean package
   ```
4. **Prepare the server:**
   Be sure your server has `JDK 21`and `Tomcat 10/11`.
5. **Configure the Database Path Environment Variable:**
   Configure Tomcat to locate the SQLite database file by creating a systemd override:

   ``` bash
   sudo systemctl edit tomcat
   ```
   **Add the environment configuration:**
   Paste the following block between the comment lines (or at the top of the file):
   ```
   [Service]
   Environment="DB_PATH=/var/lib/tomcat/currency_exchange.db"
   ```
   **Note:** You can replace `/var/lib/tomcat/currency_exchange.db` with any valid absolute path on your server where Tomcat has read/write permissions.
   
   **Save, reload, and restart Tomcat:**
   Apply this systemd chnages and restart the server:
   ``` bash
   sudo systemctl daemon-reload
   sudo systemctl restart tomcat
   ```
   
6. **Download `.war` file on server:**

   Upload the compiled file to the webapps folder of your Tomcat (via password or SSH key):
    ``` bash
   scp target/currency-exchange.war <username>@<your-server-ip>:/opt/tomcat/webapps/
   ``` 
7. **Start Tomcat:**

   If Tomcat is not already running, run on the server:
    ``` bash
   /opt/tomcat/bin/startup.sh
   ```
   
8. **Access the application** - the app will be available at: `http://<your-server-ip>:8080/currency-exchange`.


## Database Schema

This application uses SQLite database

**Main database name**:
`currency_exchange.db` - has two tables

`currency` (`id`, `full_name`, `code`, `sign`)

`exchange_rates` (`id`, `base_currency_id`, `target_currency_id`, `rate`)

### Limitations:

- the code in `currency` is unique;
- the `base_currency_id` + `target_currency_id` pair in `exchange_rates` is unique;
  `exchange_rates` is linked to `currency` via foreign keys.

## Endpoints examples:

1. **GET** `/currencies`

Retrieving the list of currencies. Response example:

```
[
    {
        "id": 0,
        "full_name": "United States dollar",
        "code": "USD",
        "sign": "$"
},   
    {
        "id": 0,
        "full_name": "Euro",
        "code": "EUR",
        "sign": "€"
    }
]
```

2. **GET** `/currency/EUR`

Retrieving a specific currency. Response example:

```
{
    "id": 0,
    "full_name": "Euro",
    "code": "EUR",
    "sign": "€"

```

3. **GET** `/exchangeRates`

Retrieving the list of all exchange rates. Response example:

```
[
    {
        "id": 0,
        "baseCurrency": {
            "id": 0,
            "full_name": "United States dollar",
            "code": "USD",
            "sign": "$"
        },
        "targetCurrency": {
            "id": 1,
            "full_name": "Euro",
            "code": "EUR",
            "sign": "€"
        },
        "rate": 0.99
    }
]
```

4. **GET** `/exchangeRate/USDRUB`

Retrieving a specific exchange rate. Response example:

```
{
    "id": 0,
    "baseCurrency": {
        "id": 0,
        "full_name": "United States dollar",
        "code": "USD",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 2,
        "full_name": "Russian Ruble",
        "code": "RUB",
        "sign": "₽"
    },
    "rate": 80
}
```

5. **POST** `/exchangeRates`

Adding a new exchange rate to the database. The data is sent in the request body as form fields (x-www-form-urlencoded).
Form fields - baseCurrencyCode, targetCurrencyCode, rate. Example form fields:

- `baseCurrencyCode` - USD
- `targetCurrencyCode` - EUR
- `rate` - 0.99

Response example - JSON representation of the record inserted into the database, including its ID:

```
{
    "id": 0,
    "baseCurrency": {
        "id": 0,
        "full_name": "United States dollar",
        "code": "USD",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 1,
        "full_name": "Euro",
        "code": "EUR",
        "sign": "€"
    },
    "rate": 0.99
}
```

6. **PATCH** `/exchangeRate/USDRUB`

Updating an existing exchange rate in the database. The currency pair is specified by consecutive currency codes in the
request URL. The data is sent in the request body as form fields (`x-www-form-urlencoded`). The only form field is
`rate`.

Response example - JSON representation of the updated record in the database, including its ID:

```
{
    "id": 0,
    "baseCurrency": {
        "id": 0,
        "full_name": "United States dollar",
        "code": "USD",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 2,
        "full_name": "Russian Ruble",
        "code": "RUB",
        "sign": "₽"
    },
    "rate": 80
}
```

7. **GET** `/exchange?from=BASE_CURRENCY_CODE&to=TARGET_CURRENCY_CODE&amount=$AMOUNT`

Calculating the conversion of a specific amount of funds from one currency to another.

Example request - GET`/exchange?from=USD&to=AUD&amount=10`.

Response example:

```
{
    "baseCurrency": {
        "id": 0,
        "full_name": "United States dollar",
        "code": "USD",
        "sign": "$"
    },
    "targetCurrency": {
        "id": 1,
        "full_name": "Australian dollar",
        "code": "AUD",
        "sign": "A$"
    },
    "rate": 1.45,
    "amount": 10.00,
    "convertedAmount": 14.50
}
```
