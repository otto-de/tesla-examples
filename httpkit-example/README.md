# A simple example application

This is a very simple application based on [tesla-microservice](https://github.com/otto-de/tesla-microservice). [tesla-httpkit](https://github.com/otto-de/tesla-httpkit) is used as an embedded server, so there are two relevant dependencies:

```clojure
[de.otto/tesla-microservice "0.13.0"]
[de.otto/tesla-httpkit "1.0.2"]
```

## Running it

Get [leiningen](http://leiningen.org/#install). Clone the repo. 

Start the example application with `lein run`

Access the example page under [localhost:8080/example](http://localhost:8080/example).
