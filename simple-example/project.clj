(defproject de.otto/tesla-simple-example "0.7.1"
            :description "a simple example of an application build with tesla-microservice."
            :url "https://github.com/otto-de/tesla-examples"
            :license {:name "Apache License 2.0"
                      :url  "http://www.apache.org/license/LICENSE-2.0.html"}
            :scm {:name "git"
                  :url  "https://github.com/otto-de/tesla-microservice"}
            :dependencies [[org.clojure/clojure "1.8.0"]
                           [de.otto/tesla-microservice "0.7.1"]
                           [de.otto/tesla-jetty "0.1.3"]
                           [hiccup "1.0.5"]
                           [org.slf4j/slf4j-api "1.7.22"]
                           [ch.qos.logback/logback-core "1.1.8"]
                           [ch.qos.logback/logback-classic "1.1.8"]]
            :profiles {:dev {:main         de.otto.tesla.example.testcore
                             :source-paths ["src" "test"]
                             :dependencies [[ring-mock "0.1.5"]
                                            [me.lomin/component-restart "0.1.1"]
                                            [hickory "0.7.0"]]
                             :plugins      [[lein-ancient "0.6.10"]]}}

            :main ^:skip-aot de.otto.tesla.example.core)
