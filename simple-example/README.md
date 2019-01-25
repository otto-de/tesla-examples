# A simple example application

This is a very simple application to demonstrate how to create an application based on 
[tesla-microservice](https://github.com/otto-de/tesla-microservice). 
Jetty is used as an embedded server, so there are two relevant dependencies:

`[de.otto/tesla-microservice "0.13.0"]`
`[de.otto/tesla-jetty "0.2.6"]`


## The application

The application consists of two components. A calculator and an endpoint.
The calculator - for the sake of the example - can be used to convert Strings to upper case. 
The endpoint can render a page that displays a form and the results of our calculations. 
Let's imagine, the calculator was a volume based enterprise software and we paid for 
10 calculations.

While the endpoint has to register a route with the handler component of tesla-microservice, 
the calculator registers a status function with the app-status component of tesla-microservice.

You will notice, that after the uppercasing of 10 Strings, the status of the calculator changes
and consequently the whole application will change its status from `OK` to `WARNING`.


## Running it

Get [leiningen](http://leiningen.org/#install). Clone the repo. 

Start the example application with `lein run`

Access the example service under [localhost:8080/example](http://localhost:8080/example).

Access the status report under [localhost:8080/status](http://localhost:8080/status).
