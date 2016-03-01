(ns mondriandroid.generate
  "Functions to generate a Mondrianesque image as a sequence of rects."
  (:require [mondriandroid.colour :as colour]
            [mondriandroid.rect :as rect :refer :all])
  (:import [org.apache.commons.math3.distribution NormalDistribution]))

(defn- randomly
  "Given a list of forms, randomly execute one of them."
  [& options]
  (let [f (rand-int (count options))]
    (eval f)))

(defn- normal [mean std]
  (.sample (NormalDistribution. mean std)))

(defn- split-across [r]
  (TODO))
(defn- split-vert [r]
  (TODO))
(defn- split-horiz [r]
  (TODO))

(defn- split
  "Given a rect, split it horizontally or vertically into a few rects
  which tile the original."
  [r]
  (randomly
   (split-across r)
   (split-vert r (rand-int 1 5))
   (split-horiz r (rand-int 1 5))))

(defn- terminate "Given a rect, return it with an optional color."
  [r]
  {:rect r
   :color (colour/random-colour)})

(defn generate
  "Given a rect, generate a Mondrianesque which tiles it."
  [r]
  (randomly
   (terminate rect)
   (apply concat (map generate (split rect)))))
