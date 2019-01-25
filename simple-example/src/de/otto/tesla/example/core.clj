(ns de.otto.tesla.example.core
  (:require [com.stuartsierra.component :as c]
            [de.otto.tesla.system :as system]
            [de.otto.tesla.example.calculating :as calculating]
            [de.otto.tesla.serving-with-jetty :as jetty]
            [de.otto.tesla.example.endpoint :as endpoint])
  (:gen-class))

(defn example-system [runtime-config]
  (-> (system/base-system (merge {:name "example-service"} runtime-config))
      (assoc
        :calculator (c/using (calculating/map->Calculator {}) [:metering :app-status])
        :endpoint (c/using (endpoint/map->Endpoint {}) [:handler :calculator :app-status]))
      (jetty/add-server)))

(defn -main
  "starts up the production system."
  [& args]
  (system/start (example-system {})))

