(ns de.otto.tesla.example.oscillator.core
  (:require [de.otto.tesla.system :as system]
            [de.otto.tesla.example.oscillator.monitor :as monitor]
            [com.stuartsierra.component :as component]
            [de.otto.tesla.serving-with-httpkit :as httpkit]
            )
  (:gen-class))

(defn oscillator-example [runtime-config]
  (-> (system/base-system (merge {:name "example-oscillator"} runtime-config))
      (assoc :oscillator-example
             (component/using (monitor/new-monitor) [:handler :app-status]))))

(defn -main
  "starts up the production system."
  [& args]
  (system/start (httpkit/add-server (oscillator-example {}) :oscillator-example)))

