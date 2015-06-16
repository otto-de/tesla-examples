(ns de.otto.tesla.zk.example.system
  (:require [de.otto.tesla.system :as system]
            [de.otto.tesla.serving-with-jetty :as jetty]
            [de.otto.tesla.zk.zk-observer :as observer]
            [de.otto.tesla.zk.example.page :as example-page]
            [com.stuartsierra.component :as c])
  (:gen-class))

(defn example-system [runtime-config]
  (-> (system/base-system (assoc runtime-config :name "example-zk-service"))
      (assoc :zk-observer
             (c/using (observer/new-zkobserver) [:config]))
      (assoc :example-page
             (c/using (example-page/new-example-page) [:handler :zk-observer :app-status]))
      (jetty/add-server :example-page)))

(defn -main
  "starts up the production system."
  [& args]
  (system/start (example-system {})))