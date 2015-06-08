(defproject de.otto/tesla-mongo-example "0.1.15"
            :description "Addon to https://github.com/otto-de/tesla-microservice to read and write to mongodb."
            :url "https://github.com/otto-de/tesla-mongo-connect"
            :license {:name "Apache License 2.0"
                      :url  "http://www.apache.org/license/LICENSE-2.0.html"}
            :scm {:name "git"
                  :url  "https://github.com/otto-de/tesla-mongo-connect"}
            :dependencies [[org.clojure/clojure "1.6.0"]
                           [de.otto/tesla-microservice "0.1.15"]
                           [de.otto/tesla-jetty "0.1.0"]
                           [de.otto/tesla-mongo-connect "0.1.4"]
                           [org.slf4j/slf4j-api "1.7.12"]
                           [ch.qos.logback/logback-core "1.1.3"]
                           [ch.qos.logback/logback-classic "1.1.3"]]

            :plugins [[lein-embongo "0.2.2"][lein-environ "1.0.0"]]

            :embongo {:port     27017
                      :version  "2.6.4"
                      :data-dir "./target/mongo-data-files"}

            :main ^:skip-aot de.otto.tesla.mongo.example.example-system)
