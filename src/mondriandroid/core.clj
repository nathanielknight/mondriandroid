(ns mondriandroid.core
  (:require [mondriandroid.draw :as draw]
            [mondriandroid.generate :as generate]
            [immutant.web :as iweb]))

(defn handler [req]
  {:status 200
   :headers {"Content-Type" "image/svg+xml"}
   :body (draw/render (generate/generate))})

(defn -main
  ([]
   (-main 8080))
  ([port]
   (iweb/run-dmc handler {:port port})))
