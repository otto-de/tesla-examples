(ns de.otto.tesla.example.end-to-end-test
  (:require [clojure.test :refer :all]
            [hickory.select :as s]
            [hickory.core :as h]
            [de.otto.tesla.util.test-utils :as u]
            [de.otto.tesla.stateful.handler :as hndl]
            [de.otto.tesla.example.core :as core]))

(deftest ^:integration mock-request-tests
  (u/with-started
    [started (core/example-system {:server-port 8088})]
    (let [handler (hndl/handler (:handler started))]
      (testing "GET request"
        (let [response    (handler (u/mock-request :get "/example" {}))
              parsed-html (h/as-hickory (h/parse (:body response)))]
          (is (= 200 (:status response)))
          (is (= {"Content-Type" "text/html; charset=utf-8"} (:headers response)))
          (is (= ["0 calculations so far"]
                 (-> (s/select (s/class "calculations") parsed-html)
                     first
                     :content)))))

      (testing "POST request gets redirected"
        (let [response (handler (u/mock-request :post "/example" {:params {"input" "test"}}))]
          (is (= 302 (:status response)))
          (is (= {"Location" "/example"} (:headers response))))))))
