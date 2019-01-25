(ns de.otto.tesla.example.core
  (:require [de.otto.tesla.system :as system]
            [de.otto.tesla.serving-with-httpkit :as serving]
            [de.otto.tesla.example.page :as page]
            [com.stuartsierra.component :as component])
  (:gen-class))

(defn example-system [runtime-config]
  (-> (system/base-system (merge {:name "httpkit-example"} runtime-config))
      (assoc :page
             (component/using (page/map->Page {}) [:handler :app-status]))))

(defn -main
  "starts up the production system."
  [& args]
  (system/start (serving/add-server (example-system {}) :page)))

