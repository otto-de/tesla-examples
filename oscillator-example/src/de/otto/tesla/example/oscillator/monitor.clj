(ns de.otto.tesla.example.oscillator.monitor
  (:require
    [de.otto.tesla.example.oscillator.pages :as pages]
    [de.otto.tesla.example.oscillator.charts :as charts]
    [de.otto.tesla.example.oscillator.annotations :as annotations]
    [de.otto.oscillator.routes :as osci-routes]
    [de.otto.tesla.stateful.app-status :as app-status]
    [de.otto.tesla.stateful.handler :as handlers]
    [de.otto.status :as status]
    [com.stuartsierra.component :as c]))

(defrecord Monitor [handler app-status]
  c/Lifecycle
  (start [self]
    (handlers/register-handler handler
                               (osci-routes/oscillator-routes :page-config pages/page-config
                                                              :chart-def-fetch-fun charts/chart-definitions
                                                              :annotation-event-targets (annotations/annotation-events)))
    (app-status/register-status-fun app-status
                                    (fn [] (status/status-detail :example-page :ok "page is always fine")))
    self)
  (stop [self]
    self))

(defn new-monitor [] (map->Monitor {}))