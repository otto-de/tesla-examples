(ns de.otto.tesla.example.page
  (:require
    [com.stuartsierra.component :as c]
    [de.otto.tesla.stateful.handler :as handlers]
    [de.otto.status :as status]
    [de.otto.tesla.stateful.app-status :as app-status]
    [ring.middleware.params :as rmp]
    [compojure.core :as compojure]
    [compojure.route :as route]
    [hiccup.page :as page]
    [hiccup.form :as form]
    [de.otto.tesla.example.calculating :as calculating]))

(defn head [title]
  [:head
   [:title title]
   [:meta {:http-equiv "Content-Type" :content "text/html" :charset "utf-8"}]
   [:meta {:name "viewport" :content "width=device-width, initial-scale=1"}]
   (page/include-css "https://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css")])

(defn basic-template [title content]
  (page/html5 {:lang "en"}
              (head (str title))
              [:body
               [:div {:class "container"}
                [:div {:class "jumbotron"} content]]]))

(defn input-form []
  (form/form-to [:post "/example"]
                [:div {:class "input-group"}
                 (form/text-field {:class "form-control"} "input")
                 [:span {:class "input-group-btn"} (form/submit-button {:class "btn btn-default"} "send")]]))

(defn usage-html []
  [:div
   [:h1 "TO UPPER CASE"]
   [:div (str "call /example/foo to get FOO or fill out the form below")]])

(defn total-calculations-html [x]
  [:div {:class "calculations"} (str x " calculations so far")])

(defn to-upper-case-html [input result]
  [:div {:class "resuĺt alert alert-info"} (str input " to upper case is " result)])

(defn form-page [input result calculations-number]
  (basic-template "Simple Example Usage Page"
                  [:div
                   (usage-html)
                   (when-not (and input (empty? input))
                     (to-upper-case-html input result))
                   (input-form)
                   (total-calculations-html calculations-number)]))

(defrecord Page []
  c/Lifecycle
  (start [self]
    (handlers/register-handler (:handler self)
                               (compojure/routes (compojure/GET "/example" [_]
                                                   (let [x (calculating/calculations (:calculator self))]
                                                     (form-page "" "" x)))
                                                 (rmp/wrap-params
                                                   (compojure/POST "/example" request
                                                     (if-let [input (get (:params request) "input")]
                                                       (let [result (calculating/calculate! (:calculator self) input)
                                                             calculations-number (calculating/calculations (:calculator self))]
                                                         (form-page input result calculations-number)))))
                                                 (route/not-found "Not Found")))
    (app-status/register-status-fun (:app-status self)
                                    (fn [] (status/status-detail :example-page :ok "page is always fine")))
    self)
  (stop [self]
    self))

(defn new-page [] (map->Page {}))