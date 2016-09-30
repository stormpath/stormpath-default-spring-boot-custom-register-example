## Custom Register Validation

This example demonstrates how you can easily add a `registerPreHandler` to add additional validation logic when a user registers an account. 

The example takes advantage of Stormpath's pluggable architecture in conjunction with Spring Boot's bean override
capabilities.

The custom validation logic, in this case, ensures that the password the user enters does not contain an email address.

The `RegisterHandleConfig` class overrides the default `registerPreHandler` method that returns a `WebHandler`.

This is a very simple, bare bones Spring Security, Spring Boot WebMVC application. All paths are locked down and require
authentication. From the login page, you can click the `Create Account`.

### Stormpath Setup

Create a Stormpath Account and setup a Stormpath Application as outlined
[here](https://docs.stormpath.com/rest/product-guide/latest/quickstart.html)

### Build

```
mvn clean package
```

### Run

```
STORMPATH_API_KEY_FILE=<path to your api key file> \
STORMPATH_APPLICATION_HREF=<your application href> \
java -jar target/*.jar
```
