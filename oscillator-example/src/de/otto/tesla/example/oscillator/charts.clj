(ns de.otto.tesla.example.oscillator.charts
  (:require [de.otto.oscillator.graphite.dsl :as dsl]))

(defn bot-bounces []
  (dsl/sum-series
    (dsl/non-negative-derivative (str "servers.app-#{env}*.metrics.bot-bounces.count"))))

(defn requests-in []
  (dsl/sum-series
    (dsl/non-negative-derivative (str "varnish.app-#{env}*.metrics.requests-in.count"))))

(defn requests-out []
  (dsl/sum-series
    (dsl/non-negative-derivative (str "servers.app-#{env}*.metrics.requests-out.count"))))

(defn realtime-timing []
  "apps.#{env}.test.realtimeintegration.time")

(defn redis-timing [percentile]
  (dsl/average-series (str "redis.#{env}.realtime.get." percentile)))

(defn mongo-timing [percentile]
  (dsl/average-series (str "mongo.#{env}.read." percentile)))

(defn chart-definitions [env]
  {:request-count  {:targets {:bot-bounces  {:target (bot-bounces)
                                             :color  "rgba(52, 152, 219, 1.0)"}
                              :requests-in  {:target (requests-in)
                                             :color  "rgba(155, 89, 182, 1.0)"}
                              :requests-out {:target (requests-out)
                                             :color  "rgba(241, 196, 15, 1.0)"}}}
   :request-timing {:lineMode "connected"
                    :yMax     4000
                    :targets  {:realtime {:target   (realtime-timing)
                                          :renderer "bar"
                                          :color    "rgba(155, 89, 182, 1.0)"}}}
   :storage-timing {:yMax    50
                    :targets {:redis-mean {:target (redis-timing "mean")
                                           :color  "rgba(241, 196, 15, 1.0)"}
                              :redis-p95  {:target (redis-timing "p95")
                                           :color  "rgba(155, 89, 182, 1.0)"}
                              :mongo-mean {:target (mongo-timing "mean")
                                           :color  "rgba(155, 89, 182, 1.0)"}
                              :mongo-p95  {:target (mongo-timing "p95")
                                           :color  "rgba(155, 89, 182, 1.0)"}}}})

