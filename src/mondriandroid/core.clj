(ns mondriandroid.core
  (:require [mondriandroid.draw :as draw]
            [mondriandroid.generate :as generate]
            [immutant.web :as iweb]
            [bidi.ring :as br]))

(defn app-handler [req]
  {:status 200
   :headers {"Content-Type" "image/svg+xml"}
   :body (draw/render (generate/generate))})

(defn missing-handler [req]
  {:status 404
   :headers {"Content-Type" "text/plain"}
   :body (str "I can't find " (:uri req))})

(def app-routes ["/" {"app" app-handler
                      true missing-handler}])

(def app (br/make-handler app-routes))

(defn -main
  ([]
   (-main 8080))
  ([port]
   (iweb/run-dmc handler {:port port})))
