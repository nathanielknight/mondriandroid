(ns mondriandroid.generate
  "Functions to generate a Mondrianesque image as a sequence of rects."
  (:require [mondriandroid.colour :as colour]
            [mondriandroid.rect :as rect :refer :all])
  (:import [org.apache.commons.math3.distribution NormalDistribution]))

(defn- randomly
  "Given a list of forms, randomly execute one of them."
  [& options]
  (let [n (rand-int (count options))
        f (nth options n)]
    f))

(defn- normal [mean std]
  (.sample (NormalDistribution. mean std)))

(defn- split-across [r]
  (let [rx (normal 0.5 0.16)
        ry (normal 0.5 0.16)]
    (mapcat
     #(split-y % ry)
     (split-x r rx))))

(defn- split-vert [r]
  (let [ry (normal 0.5 0.6)]
    (split-y r ry)))

(defn- split-horiz [r]
  (let [rx (normal 0.5 0.16)]
    (split-x r rx)))

(defn- split
  "Given a rect, split it horizontally or vertically into a few rects
  which tile the original."
  [r]
  (randomly
   (split-across r)
   (split-vert r)
   (split-horiz r)))

(defn- terminate "Given a rect, return it with an optional colour."
  [r]
  {:rect r
   :colour (colour/random-colour)})

(defn generate
  "Given a rect, generate a Mondrianesque which tiles it."
  ([r]
   (generate r 4))
  ([r n]
   (cond
     (> n 2) (mapcat #(generate % (- n 1)) (split r))
     (<= n 0) (list (terminate r))
     :else (randomly
            (list (terminate r))
            (mapcat #(generate % (- n 1)) (split r))))))
