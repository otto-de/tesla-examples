(ns de.otto.tesla.mongo.example.example-system
  (:require [de.otto.tesla.system :as system]
            [de.otto.tesla.mongo.mongo :as mongo]
            [de.otto.tesla.mongo.example.page :as example-page]
            [com.stuartsierra.component :as c])
  (:gen-class))

(defn example-system [runtime-config]
  (-> (system/empty-system (merge {:name "mongo-example-service"} runtime-config))
      (assoc :mongo
             (c/using (mongo/new-mongo "test-db") [:config :metering :app-status]))
      (assoc :example-page
             (c/using (example-page/new-example-page) [:handler :mongo :app-status]))
      (c/system-using {:server [:example-page]})))

(defn -main
  "starts up the production system."
  [& args]
  (let [started (c/start-system (example-system {}))
        _ (println started)
        mongodb (:mongo started)
        _ (println mongodb)]
    (mongo/update-upserting! (:mongo started) "testcol" {:_id "myId"}
                             {:_id  "myId"
                              :foo  "bar"
                              :info "This was upserted into mongo in example-system.clj"})))
