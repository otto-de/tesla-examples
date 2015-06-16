(ns de.otto.tesla.zk.example.page
  (:require
    [com.stuartsierra.component :as c]
    [de.otto.tesla.stateful.handler :as handler]
    [de.otto.status :as status]
    [de.otto.tesla.stateful.app-status :as app-status]
    [de.otto.tesla.zk.zk-observer :as observer]
    [compojure.core :as compojure]
    [hiccup.core :as hic]))

(defn usage-page []
  (hic/html [:body [:h1 "ZOOKEEPER OBSERVE"]
             [:div (str "call /example/foo to get the data for key foo from the zookeeper.")]]))

(defn result-page [self input]
  (let [data (observer/observe! (:zk-observer self) (str "/" input))]
    (hic/html [:body [:h1 "ZOOKEEPER OBSERVE"]
               [:div
                (if (nil? data)
                  (str "For the key " input " zookeeper has no data.")
                  (str "For the key " input " zookeeper has the data " data "."))]])))


(defrecord ExamplePage [handler]
  c/Lifecycle
  (start [self]
    (handler/register-handler handler
                              (compojure/routes
                                (compojure/GET "/example" [_] (usage-page))
                                (compojure/GET "/example/:input" [input] (result-page self input))))
    (app-status/register-status-fun (:app-status self)
                                    (fn [] (status/status-detail :example-page :ok "page is always fine")))
    self)
  (stop [self]
    self))

(defn new-example-page [] (map->ExamplePage {}))
