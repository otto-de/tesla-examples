(ns de.otto.tesla.example.example-system
  (:require [de.otto.tesla.system :as system]
            [de.otto.tesla.example.calculating :as calculating]
            [de.otto.tesla.serving-with-jetty :as serving]
            [de.otto.tesla.example.page :as page]
            [com.stuartsierra.component :as component])
  (:gen-class))

(defn example-calculation-function [input]
  (.toUpperCase input))

(defn example-system [runtime-config]
  (-> (system/base-system (merge {:name "example-service"} runtime-config))
      (assoc :calculator
             (component/using (calculating/new-calculator example-calculation-function) [:metering :app-status]))
      (assoc :example-page
             (component/using (page/new-page) [:handler :calculator :app-status]))))

(defn -main
  "starts up the production system."
  [& args]
  (system/start (serving/add-server (example-system {}) :example-page)))

