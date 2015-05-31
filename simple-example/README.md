# A simple example application

This is a very simple application to demonstrate how to create an application based on [tesla-microservice](https://github.com/otto-de/tesla-microservice).
The app has a calculating component, which - for the sake of the example - can be used to convert Strings to upper case and a page that displays the result to the user.

While the page has to register a route with the routes component of tesla-microservice, the calculator registers a status function with the
app-status component of tesla-microservice.

The calculator used for uppercasing is a volume-licensed enterprise software.
So you will notice, that after the uppercasing of 10 Strings, the status of the calculator and consequently the whole application will change from *OK* to *WARNING*.

## Running it
Get [leiningen](http://leiningen.org/#install). Clone the repo. Start the example application with

`$ lein run`

Access the example service under `http://localhost:8080/example` and `http://localhost:8080/example/foo`.

Access the status report under `http://localhost:8080/status`.


