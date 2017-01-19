(ns de.otto.tesla.mongo.example.page-test
  (:require [clojure.test :refer :all]
            [de.otto.tesla.util.test-utils :as utils]
            [de.otto.tesla.mongo.example.example-system :as system]
            [hickory.core :as hickory]
            [hickory.select :as select]
            [de.otto.tesla.stateful.handler :as rts]
            [de.otto.tesla.mongo.mongo :as mongo]))

(defn insert-dummy-data-into-mongo [mongodb]
  (some->> (mongo/find-checked! mongodb "collection" {})
           (map :_id)
           (run! #(mongo/remove-by-id! mongodb "collection" %)))

  (mongo/update-upserting! mongodb "collection" {:_id "myId"}
                           {:_id  "myId"
                            :foo  "bar was upserted into mongo"
                            :info "This was upserted into mongo"}))

(deftest ^:unit mock-request-tests
  (utils/with-started [started (system/example-system {})]
                      (let [all-handler (rts/handler (:handler started))
                            mongodb (:mongo started)]
                        (insert-dummy-data-into-mongo mongodb)
                        (testing "GET request - Should display the landing page with the db result.")
                        (let [response (all-handler (utils/mock-request :get "/example" {}))
                              parsed-html (hickory/as-hickory (hickory/parse (:body response)))]
                          (is (= 200 (:status response)))
                          (is (= {"Content-Type" "text/html; charset=utf-8"} (:headers response)))
                          (is (= ["myId"]
                                 (some-> (select/select (select/class "d-id") parsed-html)
                                         first
                                         :content)))
                          (is (= "foo: "
                                 (some-> (select/select (select/class "key") parsed-html)
                                         first
                                         :content
                                         first)))
                          (is (= ["bar was upserted into mongo"]
                                 (some-> (select/select (select/class "value") parsed-html)
                                         first
                                         :content)))
                          (is (= ["This was upserted into mongo"]
                                 (some-> (select/select (select/class "value") parsed-html)
                                         last
                                         :content)))
                          (is (= "info: "
                                 (some-> (select/select (select/class "key") parsed-html)
                                         last
                                         :content
                                         first))))

                        (testing "POST request - Should display new data.")
                        (let [response (all-handler (utils/mock-request :post "/example" {:params {:col "collection" :key "k1" :value "test"}}))
                              parsed-html (hickory/as-hickory (hickory/parse (:body response)))]
                          (is (= 200 (:status response)))
                          (is (= {"Content-Type" "text/html; charset=utf-8"} (:headers response)))
                          (is (= ["myId"]
                                 (some-> (select/select (select/class "d-id") parsed-html)
                                         first
                                         :content)))
                          (is (= "foo: "
                                 (some-> (select/select (select/class "key") parsed-html)
                                         first
                                         :content
                                         first)))
                          (is (= ["bar was upserted into mongo"]
                                 (some-> (select/select (select/class "value") parsed-html)
                                         first
                                         :content)))
                          (is (= "k1: "
                                 (some-> (select/select (select/class "key") parsed-html)
                                         last
                                         :content
                                         first)))
                          (is (= ["[\"test\"]"]
                                 (some-> (select/select (select/class "value") parsed-html)
                                         last
                                         :content)))))))