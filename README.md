# Customers (US/CAN)

## Tutorial
A test assignment application.

### Technologies used:
* Java 8
* Spring Framework (Boot, MVC, Data, Security)
* PostgreSQL
* Hibernate (via Spring Data JPA)
* Thymeleaf
* Gradle
* JUnit

### Steps to setup

**1. Clone the repository (alternatively you can just download it) using 
the following command:**

```bash
git clone https://github.com/AlexBobryshev93/American-Customers
```

**2. Install and configure PostgreSQL**

First, install PostgreSQL and create a database named `customers`. 
Then, open `src/main/resources/application.properties` file and change 
the spring datasource username and password to yours if you 
didn't use the default values during the installation. The default username is
`user1` and password - `pass` .

**3. Run the application**

Use the following command from the root directory of the project to run it:

```bash
mvn spring-boot:run
```

Also you may do the same from your IDE.

**4. Use the application as you wish**

Now you can access the application at http://localhost:8080/ in your browser
(port number can be changed in `src/main/resources/application.properties`).

The application will require authentication. Use username
`user1` or `user2` and password `pass` as the credentials. These are 
default accounts. Your own can be created  and updated in the app
in order to take an opportunity to test the full functionality.

Enjoy!
