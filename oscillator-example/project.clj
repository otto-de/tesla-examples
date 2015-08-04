(defproject de.otto/oscillator-example "0.1.18"
  :description ""
  :url "https://github.com/otto-de/tesla-examples"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/license/LICENSE-2.0.html"}
  :scm {:name "git"
        :url  "https://github.com/otto-de/tesla-examples"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [de.otto/tesla-microservice "0.1.18"]
                 [de.otto/tesla-httpkit "0.1.1"]
                 [de.otto/oscillator "0.2.4"]

                 [org.slf4j/slf4j-api "1.7.12"]
                 [ch.qos.logback/logback-core "1.1.3"]
                 [ch.qos.logback/logback-classic "1.1.3"]]
  :profiles {:dev {:dependencies [[ring-mock "0.1.5"]]}}

  :main ^:skip-aot de.otto.tesla.example.oscillator.core)
