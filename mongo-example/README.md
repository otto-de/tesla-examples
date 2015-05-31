# Example for using tesla-mongo-connect

This is a very simple example for how to use mongo-db using the [tesla-mongo-connect](https://github.com/otto-de/tesla-microservice) addon to [tesla-microservice](https://github.com/otto-de/tesla-microservice).

## Running it
To start the application you can either use
* a mongo-db running on localhost (or whatever you change the config to in [default.properties](ls resources/default.properties)). Then you can start the application with ```lein run```

* an embedded mongodb provided by embongo. Start the application with ```lein embongo run```.

## What is happening?
First some data is written into the collection ```testcol``` during  starting up the system in [example_system.clj](./src/de/otto/tesla/mongo/example/example_system.clj):

```clojure
(mongo/update-upserting! (:mongo started) "testcol" {:_id "myId"}
                             {:_id  "myId"
                              :foo  "bar"
                              :info "This was upserted into mongo in example-system.clj"})```


The project contains a very simple page, which is dependent on ```:mongo``` in _tesla-mongo-connect_ If you call ```http://localhost:8080/example``` the data is read out of mongo in [page.clj](./src/de/otto/tesla/mongo/example/page.clj):

```clojure
(mongo/find-one-checked! (:mongo self) "testcol" {})
```

