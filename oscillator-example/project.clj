(defproject de.otto/oscillator-example "0.7.1"
  :description ""
  :url "https://github.com/otto-de/tesla-examples"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/license/LICENSE-2.0.html"}
  :scm {:name "git"
        :url  "https://github.com/otto-de/tesla-examples"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [de.otto/tesla-microservice "0.7.1"]
                 [de.otto/tesla-httpkit "1.0.1"]
                 [de.otto/oscillator "0.2.26"]
                 [de.otto/tesla-basic-logging "0.1.5"]]

  :profiles {:dev {:dependencies [[ring-mock "0.1.5"]]
                   :plugins      [[lein-ancient "0.6.10"]]}}

  :main ^:skip-aot de.otto.tesla.example.oscillator.core)
