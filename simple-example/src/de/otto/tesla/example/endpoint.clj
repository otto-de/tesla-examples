(ns de.otto.tesla.example.endpoint
  (:require [com.stuartsierra.component :as c]
            [compojure.core :as compojure]
            [compojure.route :as route]
            [ring.util.response :as resp]
            [ring.middleware.params :as params]
            [de.otto.tesla.stateful.handler :as handlers]
            [de.otto.tesla.stateful.app-status :as app-status]
            [de.otto.status :as status]
            [de.otto.tesla.example.calculating :as calculating]
            [de.otto.tesla.example.ui :as ui]))

(defn myroutes [calculator]
  (compojure/routes
    (compojure/GET "/example" [_]
      (ui/page (calculating/results calculator)))

    (params/wrap-params
      (compojure/POST "/example" request
        (when-let [input (get-in request [:params "input"])]
          (calculating/calculate! calculator input)
          (resp/redirect "/example"))))

    (route/not-found "Not Found")))

(defn status []
  (status/status-detail :example-page :ok "page is always fine"))

(defrecord Endpoint [app-status calculator]
  c/Lifecycle
  (start [self]
    (handlers/register-handler (:handler self) (myroutes calculator))
    (app-status/register-status-fun app-status status)
    self)

  (stop [self]
    self))
