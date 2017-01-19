(defproject de.otto/tesla-mongo-example "0.7.1"
  :description "Addon to https://github.com/otto-de/tesla-microservice to read and write to mongodb."
  :url "https://github.com/otto-de/tesla-mongo-connect"
  :license {:name "Apache License 2.0"
            :url  "http://www.apache.org/license/LICENSE-2.0.html"}
  :scm {:name "git"
        :url  "https://github.com/otto-de/tesla-mongo-connect"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [de.otto/tesla-microservice "0.7.1"]
                 [de.otto/tesla-jetty "0.1.3"]
                 [de.otto/tesla-mongo-connect "0.2.1"]
                 [hiccup "1.0.5"]
                 [org.slf4j/slf4j-api "1.7.22"]
                 [ch.qos.logback/logback-core "1.1.8"]
                 [ch.qos.logback/logback-classic "1.1.8"]]

  :plugins [[lein-embongo "0.2.2"]
            [lein-environ "1.1.0"]]

  :aliases {"test" ["do" "embongo" "test"]}

  :embongo {:port     27017
            :version  "2.6.4"
            :data-dir "./target/mongo-data-files"}
  :profiles {:dev {:main         de.otto.tesla.mongo.example.testcore
                   :source-paths ["src" "test"]
                   :dependencies [[ring-mock "0.1.5"]
                                  [hickory "0.7.0"]
                                  [me.lomin/component-restart "0.1.1"]]
                   :plugins      [[lein-ancient "0.6.10"]]}}

  :main ^:skip-aot de.otto.tesla.mongo.example.example-system)
