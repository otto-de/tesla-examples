(defproject de.otto/tesla-simple-example "0.1.18"
            :description "a simple example of an application build with tesla-microservice."
            :url "https://github.com/otto-de/tesla-examples"
            :license {:name "Apache License 2.0"
                      :url  "http://www.apache.org/license/LICENSE-2.0.html"}
            :scm {:name "git"
                  :url  "https://github.com/otto-de/tesla-microservice"}
            :dependencies [[org.clojure/clojure "1.8.0"]
                           [de.otto/tesla-microservice "0.1.28"]
                           [de.otto/tesla-jetty "0.1.1"]
                           [hiccup "1.0.5"]
                           [org.slf4j/slf4j-api "1.7.16"]
                           [ch.qos.logback/logback-core "1.1.5"]
                           [ch.qos.logback/logback-classic "1.1.5"]]
            :profiles {:dev {:main         de.otto.tesla.example.testcore
                             :source-paths ["src" "test"]
                             :dependencies [[ring-mock "0.1.5"]
                                            [me.lomin/component-restart "0.1.0"]
                                            [hickory "0.6.0"]]}}

            :main ^:skip-aot de.otto.tesla.example.example-system)
