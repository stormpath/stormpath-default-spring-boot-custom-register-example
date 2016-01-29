## Custom Register Validation

This example demonstrates how you can override the default validation behavior in Stormpath's standard Register
Controller.

The example takes advantage of Stormpath's pluggable architecture in conjunction with Spring Boot's bean override
capabilities.

The key elements are:

* a `CustomRegisterController` that extends Stormpath's default `RegisterController`. It implements the new
validation logic and then calls the existing validation logic from the parent class.

* a Spring Boot Configuration that extends Stormpath's `AbstractStormpathWebMvcConfiguration` and
exposes a `stormpathRegisterController` bean, which overrides the default. Internally, the `stormpathRegisterController`
method instantiates and returns the `CustomRegisterController`.

The custom validation logic, in this case, ensures that the password the user enters does not contain an email address.

This is a very simple, bare bones Spring Security, Spring Boot WebMVC application. All paths are locked down and require
authentication. From the login page, you can click the `Create Account`.

### Stormpath Setup

Create a Stormpath Account and setup a Stormpath Application as outlined
[here](https://docs.stormpath.com/rest/product-guide/latest/setup.html)

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