(ns de.otto.tesla.example.calculating-test
  (:require [clojure.test :refer :all]
            [de.otto.tesla.example.calculating :as calculating]
            [com.stuartsierra.component :as c]
            [de.otto.tesla.stateful.app-status :as app-status]))

(deftest ^:unit calculating
  (testing "it stores the result of calculations"
    (with-redefs [app-status/register-status-fun (constantly nil)]
      (let [calculator (c/start (calculating/map->Calculator {}))]
        (calculating/calculate! calculator "foo")
        (calculating/calculate! calculator "bar")

        (is (= ["FOO" "BAR"]
               (calculating/results calculator)))))))