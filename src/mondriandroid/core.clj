(ns mondriandroid.core
  "Ring handlers and the server entry-point for Mondriandroid."
  (:require [clojure.string]
            [clojure.spec :as s]
            [immutant.web :as iweb]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [mondriandroid.draw :as draw]
            [mondriandroid.generate :as generate]
            [mondriandroid.random :as mr]
            [mondriandroid.templates :refer [templates]]))

;; ---------------------------------------------------------------------
;; Utils


;; ---------------------------------------------------------------------
;; Handlers

(defn- missing-handler [req]
  {:status 404
   :headers {"Content-Type" "text/html"}
   :body (mondriandroid.templates/four-oh-four (:uri req))})

(defn- generate-handler [req]
  (let [query-params (get req :params {})]
    {:status 200
     :headers {"Content-Type" "image/svg+xml"}
     :body (binding [mondriandroid.random/*prng*  (new java.util.Random 3)]
             (draw/render (generate/generate {})))}))

(defn- generator-handler [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (get templates :generator)})

(defn- index-handler [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (get templates :index)})

(defn- about-handler [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (get templates :about)})



;; ---------------------------------------------------------------------
;; Application ring-handler

;; TODO(nknight): standardize routes with a `clojure.spec`?
;; TODO(nknight): Remove the regex from route matching (to avoid the
;;   possibility of catastrophic matching performance).

(defn- find-handler [req]
  (let [path (->> (:uri req)
                  clojure.string/lower-case
                  (re-find #"/[a-z_]*$"))]
    (case path
      "/" index-handler
      "/generate" generate-handler
      "/generator" generator-handler
      "/about" about-handler
      missing-handler)))

(defn- handler [req]
  (println req)
  ((find-handler req) req))



;; ---------------------------------------------------------------------
;; Server entrypoint

(defn -main
  ([]
   (-main 8080))
  ([port]
   (iweb/run-dmc
    (-> handler
        wrap-keyword-params
        wrap-params)
    {:port port})))
