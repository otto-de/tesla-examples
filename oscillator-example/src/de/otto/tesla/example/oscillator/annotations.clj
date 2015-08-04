(ns de.otto.tesla.example.oscillator.annotations
  (:require [de.otto.oscillator.graphite.dsl :as dsl]))

(defn annotation-events []
  [(dsl/aliaz "deployments.#{env}.count" "Deployment")
   (dsl/aliaz "monkey.#{env}.count" "Chaos Monkey")])