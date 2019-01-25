(ns de.otto.tesla.example.page
  (:require [com.stuartsierra.component :as c]
            [de.otto.tesla.stateful.handler :as handlers]
            [compojure.core :as compojure]
            [hiccup.core :as hiccup]))

(defn simple-page []
  (hiccup/html [:body [:h1 "A simple example"]
                [:div "served with httpkit"]]))

(defrecord Page []
  c/Lifecycle
  (start [self]
    (handlers/register-handler
      (:handler self)
      (compojure/routes (compojure/GET "/example" [_] (simple-page))))
    self)
  (stop [self]
    self))
