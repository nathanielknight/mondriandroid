(ns mondriandroid.generate
  "Functions to generate a Mondrianesque image as a sequence of rects."
  (:require [mondriandroid.colour :as colour]
            [mondriandroid.rect :as rect :refer :all])
  (:import [org.apache.commons.math3.distribution NormalDistribution]))


(defn split-point [] 0.5)
(defn split-std [] 0.1)

(defn- randomly
  "Given a list of forms, randomly execute one of them."
  [& options]
  (let [n (rand-int (count options))
        f (nth options n)]
    f))

(defn- normal [mean std]
  (.sample (NormalDistribution. mean std)))

(defn- split-across [r]
  (let [rx (normal (split-point) (split-std))
        ry (normal (split-point) (split-std))]
    (mapcat
     #(split-y % ry)
     (split-x r rx))))

(defn- split-vert [r]
  (let [ry (normal (split-point) (split-std))]
    (split-y r ry)))

(defn- split-horiz [r]
  (let [rx (normal (split-point) (split-std))]
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
  (list {:rect r
         :colour (colour/random-colour)}))

(defn generate
  "Given a rect, generate a Mondrianesque which tiles it."
  ([]
   (generate (rect (point 0 0) (point 10 6.2))))
  ([r]
   (generate r 0))
  ([r n]
   (cond
     (<= n 0) (mapcat #(generate % (+ n 1)) (split-across r))
     (<= n 1) (mapcat #(generate % (+ n 1)) (split-horiz r))
     (<= n 2) (randomly
               (terminate r)
               (mapcat #(generate % (+ n 1) ) (split r)))
     :else (terminate r))))
