(ns de.otto.tesla.example.ui-test
  (:require [clojure.test :refer :all]
            [de.otto.tesla.example.ui :as ui]
            [hickory.core :as h]
            [hickory.select :as s]))

(deftest ^:unit page-test
  (testing "it renders the page showing the correct calculation count"
    (let [result      (ui/page ["1" "2" "3"])
          parsed-html (h/as-hickory (h/parse result))]
      (is (= ["3 calculations so far"]
             (-> (s/select (s/class "calculations") parsed-html)
                 first
                 :content)))))

  (testing "it renders the page showing the last results"
    (let [result      (ui/page ["1" "2" "3"])
          parsed-html (h/as-hickory (h/parse result))]
      (is (= ["1" "2" "3"]
             (->> (s/select (s/and (s/class "results")
                                   (s/tag :ul))
                            parsed-html)
                  first
                  :content
                  (mapcat :content)))))))
