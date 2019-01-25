(ns de.otto.tesla.example.testcore
  (:require [de.otto.tesla.system :as sys]
            [me.lomin.component-restart :as restart]
            [de.otto.tesla.example.core :as system])
  (:gen-class))

(defn -main [& args]
  (println "Starting the system. With restart watcher.")
  (let [started (sys/start (system/example-system {}))]
    (restart/watch (var -main) started)))
