(ns de.otto.tesla.mongo.example.testcore
  (:require [de.otto.tesla.system :as sys]
            [me.lomin.component-restart :as restart]
            [de.otto.tesla.mongo.example.example-system :as system])
  (:gen-class))

(defn -main [& args]
  (println "Starting the system. With restart watcher.")
  (let [started (sys/start (system/example-system {}))]
    (restart/watch (var -main) started)))
