(ns de.otto.tesla.mongo.example.example-system
  (:require [de.otto.tesla.system :as system]
            [de.otto.tesla.serving-with-jetty :as jetty]
            [de.otto.tesla.mongo.mongo :as mongo]
            [de.otto.tesla.mongo.example.page :as example-page]
            [com.stuartsierra.component :as c])
  (:gen-class))

(defn example-system [runtime-config]
  (-> (system/base-system (merge {:name "mongo-example-service"} runtime-config))
      (assoc :mongo
             (c/using (mongo/new-mongo "test-db") [:config :metering :app-status]))
      (assoc :example-page
             (c/using (example-page/new-example-page) [:handler :mongo :app-status]))
      (jetty/add-server :example-page)))

(defn -main
  "starts up the production system."
  [& args]
  (let [started (c/start-system (example-system {}))
        mongodb (:mongo started)]
    (mongo/update-upserting! (:mongo started) "testcol" {:_id "myId"}
                             {:_id  "myId"
                              :foo  "bar"
                              :info "This was upserted into mongo in example-system.clj"})))
