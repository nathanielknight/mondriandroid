(ns mondriandroid.core
  "Ring handlers and the server entry-point for Mondriandroid."
  (:require [clojure.string]
            [immutant.web :as iweb]
            [mondriandroid.draw :as draw]
            [mondriandroid.generate :as generate]
            [mondriandroid.templates :refer [templates]]))

;; ---------------------------------------------------------------------
;; Handlers
(defn- missing-handler [req]
  {:status 404
   :headers {"Content-Type" "text/plain"}
   :body (str "I can't find " (pr-str (:uri req)))})

(defn- generate-handler [req]
  {:status 200
   :headers {"Content-Type" "image/svg+xml"}
   :body (draw/render (generate/generate))})

(defn- generator-handler [req]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "It's not done yet!"})

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
  ((find-handler req) req))



;; ---------------------------------------------------------------------
;; Server entrypoint

(defn -main
  ([]
   (-main 8080))
  ([port]
   (iweb/run-dmc handler {:port port})))
