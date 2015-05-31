(defproject de.otto/tesla-simple-example "0.1.0"
            :description "a simple example of an application build with tesla-microservice."
            :url "https://github.com/otto-de/tesla-examples"
            :license { :name "Apache License 2.0" 
                       :url "http://www.apache.org/license/LICENSE-2.0.html"}
            :scm { :name "git"
 	           :url "https://github.com/otto-de/tesla-microservice"}
            :dependencies [[org.clojure/clojure "1.6.0"]
                           [de.otto/tesla-microservice "0.1.12"]]
            :main ^:skip-aot de.otto.tesla.example.example-system)