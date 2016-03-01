(ns mondriandroid.draw
  (:require [clojure.data.xml :as dx]))

;; Base constructors
;; TODO rect 



;; Conversion
;; TODO: Render SVG to file
;; TODO: hack in a doctype
(defn render [elements]
  (dx/emit-str
   (dx/sexp-as-element
    elements)))




