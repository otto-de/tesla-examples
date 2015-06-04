(ns de.otto.tesla.mongo.example.page
  (:require
    [com.stuartsierra.component :as c]
    [de.otto.tesla.stateful.handler :as handler]
    [de.otto.status :as status]
    [de.otto.tesla.stateful.app-status :as app-status]
    [compojure.core :as compojure]
    [de.otto.tesla.mongo.mongo :as mongo]
    [hiccup.core :as hiccup]))

(defn content [self]
  (let [x (mongo/find-one-checked! (:mongo self) "testcol" {})
        _ (println x)]
    (hiccup/html [:body [:h1 "One Thing that is in the mongo"]
                  [:pre (str x)]])))

(defrecord ExamplePage [mongo handler app-status]
  c/Lifecycle
  (start [self]
    (handler/register-handler handler
                            (compojure/routes (compojure/GET "/example" [_] (content self))))
    (app-status/register-status-fun app-status
                                    (fn [] (status/status-detail :example-page :ok "page is always fine")))
    self)
  (stop [self]
    self))

(defn new-example-page [] (map->ExamplePage {}))