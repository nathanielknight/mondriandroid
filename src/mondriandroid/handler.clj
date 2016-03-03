(ns mondriandroid.handler
  (:require [mondriandroid.generate :as g]
            [mondriandroid.draw :as d])
  )


(defn dev [req]
  {:status 200
   :headers {"Cnotent-Type" "image/svg+xml"}
   :body (d/render (g/generate))})
