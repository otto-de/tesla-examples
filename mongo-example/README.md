# Example for using tesla-mongo-connect

This is a very simple example for how to use mongo-db using the [tesla-mongo-connect](https://github.com/otto-de/tesla-microservice) addon to [tesla-microservice](https://github.com/otto-de/tesla-microservice).

## Running it
To start the application you can either use
* a mongo-db running on localhost (or whatever you change the config to in [default.edn](resources/default.edn)). Then you can start the application with ```lein run```

* an embedded mongodb provided by embongo. Start the application with ```lein embongo run```.

## What is happening?
The project contains a page, which is dependent on ```:mongo``` in _tesla-mongo-connect_. The page displays the database content and a form to add a document to the collection in the database.
If you call ```http://localhost:8080/example``` the data is read out of mongo and rendered into the html-response in [page.clj](./src/de/otto/tesla/mongo/example/page.clj):

```clojure
(mongo/find-checked! (:mongo self) "collection" {})
```

The form data is processed by the [page.clj](./src/de/otto/tesla/mongo/example/page.clj) via POST:

```clojure
(mongo/update-upserting! mongo "collection" {:_id col}
                             {$push 
                                {(keyword key) value}})
```