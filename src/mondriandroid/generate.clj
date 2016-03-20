(ns mondriandroid.generate
  "Functions to generate a Mondrianesque image as a sequence of rects."
  (:require [mondriandroid.colour :as colour]
            [mondriandroid.rect :as rect :refer :all])
  (:import [org.apache.commons.math3.distribution NormalDistribution]))



(defn random-weights [weights]
  (rand-nth
   (apply
    concat
    (for [[val count] weights]
            (repeat count val)))))

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

(defn- split-across
  "Split a rect into four rects in a cross shape"
  [r]
  (let [rx (normal (split-point) (split-std))
        ry (normal (split-point) (split-std))]
    (mapcat
     #(split-y % ry)
     (split-x r rx))))

(defn- split-vert
  "Split a rect vertically"
  [r]
  (let [ry (normal (split-point) (split-std))]
    (split-y r ry)))

(defn- split-horiz
  "Split a rect horizontally"
  [r]
  (let [rx (normal (split-point) (split-std))]
    (split-x r rx)))

(defn- needs-remediation? [r]
  (> (-> r
         aspect-ratio
         java.lang.Math/log
         java.lang.Math/abs)
     0.5))

(defn- remedial-split
  "Split a rect in such a way that its aspect ratio comes back towards 1"
  [rect]
  (if (< 0 (java.lang.Math/log (aspect-ratio rect)))
    (split-horiz rect)
    (split-vert rect)))

(defn- split
  "Given a rect, split it horizontally or vertically into a few rects
  which tile the original."
  [r]
  (if (needs-remediation? r)
    (remedial-split r)
    ((random-weights
      {
;       split-across 1
       split-vert 3
       split-horiz 3
       }
      ) r)))

(defn- terminate
  "Given a rect, return it with an optional colour."
  [r]
  (list {:rect r
         :colour (colour/random-colour)}))

(defn generate
  "Given a rect, generate a Mondrianesque which tiles it."
  ([]
   (generate (rect (point 0 0) (point 20 12.4))))
  ([r]
   (generate r 0))
  ([r n]
   (cond
     (<= n 2) (mapcat #(generate % (+ n 1)) (split r))     
     (<= n 8)    ((random-weights
                   {terminate n
                    (fn [r] (mapcat #(generate % (+ n 1)) (split r))) 12})
                  r)
     :else (terminate r))
   ))


