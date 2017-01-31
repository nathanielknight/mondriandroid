(ns mondriandroid.rect
  "Generic constructors for rectangualar objects. "
  (:require [clojure.spec :as spec]))

(spec/def ::point (spec/and vector? #(= (count %) 2)))
(spec/def ::rect (spec/and
                  vector?
                  #(= (count %) 2)))

(defn point [a b]
  [a b])

(defn x-of [point]
  (first point))

(defn y-of [point]
  (second point))

(defn rect
  [ll ur]
  [ll ur])

(defn ll-of [rect]
  (first rect))

(defn ur-of [rect]
  (second rect))

(defn width [rect]
  (Math/abs (- (-> rect ll-of x-of) (-> rect ur-of x-of))))

(defn height [rect]
  (Math/abs (- (-> rect ll-of y-of) (-> rect ur-of y-of))))

(defn aspect-ratio [rect]
  (/ (width rect) (height rect)))

(defn split-x
  "Given a rect `r` and a ratio `rx`, return a seq of two rects with
  widths `rx` and `(1-rx)` times the original's width."
  [r rx]
  (let [dx (* rx (width r))
        c (+ dx (x-of (ll-of r)))]
    (list
     (rect (ll-of r)
           (point c (y-of (ur-of r))))
     (rect (point c (y-of (ll-of r)))
           (ur-of r)))))

(defn split-y
  "Given a rect `r` and a ratio `ry`, return a seq of two rects with
  heights `ry` and `(1-ry)` times the original's height."  
  [r ry]
  (let [dy (* ry (height r))
        c (+ dy (y-of (ll-of r)))]
    (list
     (rect (ll-of r)
           (point (x-of (ur-of r)) c))
     (rect (point (x-of (ll-of r)) c)
           (ur-of r)))))
