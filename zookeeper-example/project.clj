(defproject de.otto/zookeeper-example "0.1.15"
            :description "Addon to https://github.com/otto-de/tesla-microservice to observe values in zookeeper."
            :url "https://github.com/otto-de/tesla-zookeeper-observer"
            :license {:name "Apache License 2.0"
                      :url  "http://www.apache.org/license/LICENSE-2.0.html"}
            :scm {:name "git"
                  :url  "https://github.com/otto-de/tesla-zookeeper-observer"}
            :dependencies [[org.clojure/clojure "1.7.0"]
                           [de.otto/tesla-microservice "0.1.18"]
                           [de.otto/tesla-zookeeper-observer "0.1.5"]
                           [de.otto/tesla-jetty "0.1.0"]
                           [zookeeper-clj "0.9.3"]
                           [hiccup "1.0.5"]
                           [org.slf4j/slf4j-api "1.7.12"]
                           [ch.qos.logback/logback-core "1.1.3"]
                           [ch.qos.logback/logback-classic "1.1.3"]]

            :main ^:skip-aot de.otto.tesla.zk.example.system)
