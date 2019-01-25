(ns de.otto.tesla.example.calculating
  (:require [com.stuartsierra.component :as c]
            [clojure.string :as cs]
            [clojure.tools.logging :as log]
            [de.otto.tesla.stateful.app-status :as app-status]
            [de.otto.status :as s]))

;; status turns warning after 10 calculations. Because license expired.
(defn- status-fun [results]
  (if (> 10 (count @results))
    (s/status-detail :calculator :ok "less than 10 calculations performed")
    (s/status-detail :calculator :warning "more than 10 calculations performed. Renew license.")))

(defn- do-something [input]
  (cs/upper-case input))

(defn calculate! [{:keys [results]} input]
  (swap! results conj (do-something input)))

(defn results [{:keys [results]}]
  @results)

(defrecord Calculator [app-status]
  c/Lifecycle
  (start [self]
    (log/info "-> starting example calculator.")
    (let [results (atom [])]
      (app-status/register-status-fun app-status (partial status-fun results))
      (assoc self
        :results results)))

  (stop [self]
    (log/info "<- stopping example calculator.")
    self))
