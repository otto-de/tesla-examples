(ns de.otto.tesla.example.page-test
  (:require [clojure.test :refer :all]
            [de.otto.tesla.example.example-system :as example-system]
            [de.otto.tesla.stateful.handler :as rts]
            [de.otto.tesla.util.test-utils :as u]
            [de.otto.tesla.example.page :as page]
            [hickory.core :as h]
            [hickory.select :as s]))

(deftest ^:unit form-page-with-calculator-count
  (testing "Should render the form page with the correct calculator count."
    (let [result (page/form-page "" "" 7)]
      (let [parsed-html (h/as-hickory (h/parse result))]
        (is (= ["7 calculations so far"]
               (-> (s/select (s/class "calculations") parsed-html)
                   first
                   :content)))))))

(deftest ^:unit form-page-with-input-param
  (testing "Should render the form page with the correct result of the input."
    (let [result (page/form-page "test" "TEST" 1)]
      (let [parsed-html (h/as-hickory (h/parse result))]
        (is (= ["test to upper case is TEST"]
               (-> (s/select (s/class "resuĺt") parsed-html)
                   first
                   :content)))))))

(deftest ^:integration mock-request-tests
  (u/with-started [started (example-system/example-system {})]
                  (let [all-handler (rts/handler (:handler started))]
                    (testing "GET request"
                      (let [response (all-handler (u/mock-request :get "/example" {}))
                            parsed-html (h/as-hickory (h/parse (:body response)))]
                        (is (= 200 (:status response)))
                        (is (= {"Content-Type" "text/html; charset=utf-8"} (:headers response)))
                        (is (= ["0 calculations so far"]
                               (-> (s/select (s/class "calculations") parsed-html)
                                   first
                                   :content)))))
                    (testing "POST request"
                      (let [response (all-handler (u/mock-request :post "/example" {:params {"input" "test"}}))
                            parsed-html (h/as-hickory (h/parse (:body response)))]
                        (is (= ["test to upper case is TEST"]
                               (-> (s/select (s/class "resuĺt") parsed-html)
                                   first
                                   :content)))
                        (is (= ["1 calculations so far"]
                               (-> (s/select (s/class "calculations") parsed-html)
                                   first
                                   :content))))))))