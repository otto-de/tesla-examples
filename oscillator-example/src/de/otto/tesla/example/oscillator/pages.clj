(ns de.otto.tesla.example.oscillator.pages)

(def pages
  [{:name    "INDEX"
    :heading "Overview"
    :url     "/"
    :type    :dashboard
    :tiles   [{:type   :chart
               :params {:chart-name :request-count}}
              {:type   :chart
               :params {:chart-name :request-timing}}
              {:type   :chart
               :params {:chart-name :storage-timing}}
              {:type   :plain-html
               :params [:div {:class "col wide"}
                        [:h2 "CUSTOM HTML"]
                        [:br]
                        [:span "Hiccup can do some nice things here"]]}
              {:type   :image
               :params {:src     "nikola-tesla.png"
                        :heading "NIKOLA TESLA"}}
              {:type   :number
               :params {:heading "Awesomeness"
                        :descr   "OVER"
                        :num     9000}}
              {:type   :image
               :params {:src     "nikola-tesla.png"
                        :heading "NIKOLA TESLA"}}]}
   {:name    "JVM"
    :heading "JVM Stats"
    :url     "/"
    :type    :dashboard
    :tiles   [{:type   :image
               :params {:src     "nikola-tesla.png"
                        :heading "NIKOLA TESLA"}}]}
   ])

(def page-config
  {:base-url       "http://graphite.example.com/"
   :pages          pages
   :environments   [{:key "dev" :name "DEV"}
                    {:key "pre" :name "PRE"}
                    {:key "prod" :name "PROD"}]
   :default-params {:env        "prod"
                    :from       "-24h"
                    :until      "-1min"
                    :resolution "10min"
                    :ymax-mode  "fix"}
   :replace-rules  {:env         (fn [env] env)
                    :logging-env (fn [env] ((keyword env) {:prod "live"} "dev"))}
   :add-js-files   []
   :add-css-files  ["/stylesheets/tiles.css"]})