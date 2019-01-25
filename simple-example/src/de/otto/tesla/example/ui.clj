(ns de.otto.tesla.example.ui
  (:require [hiccup.page :as page]
            [hiccup.form :as form]))

(defn head [title]
  [:head
   [:title title]
   [:meta {:http-equiv "Content-Type" :content "text/html" :charset "utf-8"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]])

(defn basic-template [title & content]
  (page/html5
    {:lang "en"}
    (head (str title))
    [:body content]))

(defn input-form []
  (form/form-to
    [:post "/example"]
    [:div
     (form/text-field "input")
     (form/submit-button "send")]))

(defn page [results]
  (basic-template
    "Simple Example Usage Page"
    [:h1 "TO UPPER CASE"]
    [:span "last results are:"]
    (into [:ul.results] (map (fn [r] [:li r]) results))
    [:div "fill out the form below, to get a new uppercase calculation:"]
    (input-form)
    [:div.calculations (count results) " calculations so far"]))
