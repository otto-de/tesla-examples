(ns de.otto.tesla.mongo.example.page
  (:require
    [com.stuartsierra.component :as c]
    [de.otto.tesla.stateful.handler :as handler]
    [de.otto.status :as status]
    [de.otto.tesla.stateful.app-status :as app-status]
    [compojure.core :as compojure]
    [compojure.route :as route]
    [de.otto.tesla.mongo.mongo :as mongo]
    [monger.operators :refer :all]
    [ring.middleware.params :as rmp]
    [ring.middleware.keyword-params :as kparams]
    [clojure.string :as string]
    [hiccup.page :as page]
    [hiccup.form :as form]))

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

(defn item [[k v]]
  [:li {:class "key"}
   (name k)
   (str ": ")
   [:span {:class "value"} (str v)]])

(defn row [document]
  [:li [:h3 {:class "d-id"} (:_id document)]
   [:ul
    (map item (dissoc document :_id))]])

(defn database-content [collection]
  [:div {:class "database-content"}
   [:h3 "Database content"]
   [:pre
    [:ul
     (map row collection)]]])

(defn input-form-html []
  (form/form-to [:post "/example"]
                [:div {:class "row"}
                 [:div {:class "col-xs-2"}
                  (form/label :col "Document ID")
                  (form/text-field {:class "form-control"} :col)]
                 [:div {:class "col-xs-4"}
                  (form/label :key "Key")
                  (form/text-field {:class "form-control"} :key)]
                 [:div {:class "col-xs-4"}
                  (form/label :value "Value")
                  (form/text-field {:class "form-control"} :value)]
                 [:div {:class "col-xs-2"}
                  (form/label :send "Action")
                  (form/submit-button {:class "btn btn-default form-control"} :add)]
                 ]))

(defn usage-html []
  [:div [:h1 "MongoDB Example"]
   [:p {:class "lead"} "Fill out the form below to add something to the database.
   <br>The database content section shows you everything that is in the database right now."]])

(defn error-html [error]
  (when (not
          (or (instance? com.mongodb.WriteResult error) (string/blank? error)))
    [:div {:class "alert alert-danger"} error]))


(defn render-page [self error]
  (let [collection (mongo/find-checked! (:mongo self) "collection" {})
        _ (println collection)]
    (basic-template "Database Example Page" (merge
                                              (usage-html)
                                              (input-form-html)
                                              (error-html error)
                                              (database-content collection)))))

(defn insert-form-values [mongo {:keys [col value key]}]
  (try
    (mongo/update-upserting! mongo "collection" {:_id col} {$push {(keyword key) value}})
    (catch Exception e (str "caught exception: " (.getMessage e)))))


(defrecord ExamplePage [mongo handler app-status]
  c/Lifecycle
  (start [self]
    (handler/register-handler handler
                              (compojure/routes (compojure/GET "/example" [_] (render-page self ""))
                                                (rmp/wrap-params
                                                  (kparams/wrap-keyword-params
                                                    (compojure/POST "/example" request
                                                      (->>
                                                        (insert-form-values mongo (:params request))
                                                        (render-page self)))))
                                                (route/not-found "Not Found")))
    (app-status/register-status-fun app-status
                                    (fn [] (status/status-detail :example-page :ok "page is always fine")))
    self)
  (stop [self]
    self))

(defn new-example-page [] (map->ExamplePage {}))