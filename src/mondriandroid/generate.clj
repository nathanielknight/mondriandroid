(ns mondriandroid.generate
  "Generate a Mondrianesque image as a sequence of rects."
  (:require [clojure.spec :as spec]
            [mondriandroid.rect :as rect :refer :all]
            [mondriandroid.random :as mr]))

;; ---------------------------------------------------------------------
;; Utils

(defn random-weights
  "Given a seqence of pairs of values and weights, randomly select one
  of the values weighting by the given weights."
  [weights]
  (mr/rand-nth!
   (apply
    concat
    (for [[val count] weights]
      (repeat count val)))))

(def colours
  {:white "ghostwhite"
   :black "black"
   :blue "mediumblue"
   :yellow "gold"
   :red "red"})

(defn- random-colour
  "Randomly generate a colour (with a bias towards white)."
  []
  (let [colour-key (random-weights
                    {:white 12, :black 1, :red 2, :blue 2, :yellow 2})]
    (get colours colour-key)))


;; ---------------------------------------------------------------------
;; Splitting rects

(defn- split-point [] 0.5) ;; functions ∵ might to randomize
(defn- split-std [] 0.1)

(defn- split-vert
  "Split a rect vertically"
  [r]
  (let [ry (mr/normal! (split-point) (split-std))]
    (split-y r ry)))

(defn- split-horiz
  "Split a rect horizontally"
  [r]
  (let [rx (mr/normal! (split-point) (split-std))]
    (split-x r rx)))


;; ---------------------------------------------------------------------
;; Aspect ratio maintenance

(defn- needs-remediation?
  "This rect is too tall or too wide."
  [r]
  (> (-> r
         aspect-ratio
         java.lang.Math/log
         java.lang.Math/abs)
     0.5))

(defn- remedial-split
  "Split a rect in such a way that its aspect ratio comes back towards 1."
  [rect]
  (if (< 0 (java.lang.Math/log (aspect-ratio rect)))
    (split-horiz rect)
    (split-vert rect)))


;; ---------------------------------------------------------------------
;; Synthesis: splitting

(defn- split
  "Given a rect, split it horizontally or vertically into a few rects
  which tile the original."
  [r]
  (if (needs-remediation? r)
    (remedial-split r)
    (let [split-fn (random-weights {split-vert 1, split-horiz 1})]
      (split-fn r))))


;; ---------------------------------------------------------------------
;; Generation

(defn- terminate
  "Given a rect, return it with an optional colour."
  [r]
  (list {:rect r
         :colour (random-colour)}))

(def generation-schemes
  (list
   (fn g [r n]
     (cond
       (<= n 4) (mapcat #(g % (+ n 1)) (split r))
       (<= n 8) ((random-weights
                  {terminate (+ n 4)
                   (fn [r] (mapcat #(g % (+ n 1)) (split r))) 12})
                 r)
       :else (terminate r)))
   (fn g [r n]
     (cond
       (<= n (mr/rand-int! 4)) (mapcat #(g % (+ n 1)) (split r))
       (<= n (+ 4 (mr/rand-int! 4))) ((random-weights
                   {terminate (/ (* n n) 2)
                    (fn [r] (mapcat #(g % (+ n 1)) (split r))) 12})
                 r)
       :else (terminate r)))
   (fn g [r n]
     (cond
       (<= n 3) (mapcat #(g % (+ n 1)) (split r))
       :else ((random-weights
               {terminate n
                (fn [r] (mapcat #(g % (+ n 1)) (split r))) 8})
              r)))))


;; ---------------------------------------------------------------------
;; Generation interface

(defn- bounding-rect [w h]
  (rect (point 0 0) (point w h)))

(spec/def
  ::generate-args
  (spec/keys :opt-un [::width ::height ::seed]))


(defn generate
  "Given a rect, generate a Mondrianesque which tiles it."
  [{:keys [width height seed]}]
  (let [width (or width 20)
        height (or height 16)
        seed (or seed 4)]
    (binding [mondriandroid.random/*prng* (new java.util.Random seed)]
      ((mr/rand-nth! generation-schemes)
       (bounding-rect width height)
       0))))
