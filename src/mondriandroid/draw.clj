(ns mondriandroid.draw
  (:require [clojure.xml :as xml]
            [mondriandroid.rect :refer :all]
            [mondriandroid.colour :as c]))

(defn- cm [x]
  (str x "cm"))

(defn- to-svg-rect [{:keys [rect colour] :as r}]
  (let [[x y] (ll-of rect)
        x (cm x)
        y (cm y)
        w (cm (width rect))
        h (cm (height rect))
        borderstyle "stroke-width:5;stroke:rgb(0,0,0)"]
    {:tag "rect"
     :attrs {:x x, :y y, :width w, :height h,
             :fill colour, :style borderstyle}}))

(defn render [rects]
  (clojure.string/join
   (list
    "<?xml version=\"1.0\" standalone=\"no\"?><!DOCTYPE svg PUBLIC \"-//W3C//DTD SVG 1.1//EN\" \"http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd\">\n"
    (with-out-str
      (xml/emit-element
       {:tag :svg
        :attrs {:width "1000" :height "618"
                :version "1.1" :xmlns "http://www.w3.org/2000/svg"}
        :content (map to-svg-rect rects)})))))
