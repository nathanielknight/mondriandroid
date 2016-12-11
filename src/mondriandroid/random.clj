(ns mondriandroid.random
  "Versions of random-number functions that accept a random number
  generator (so that the seed can be set).")

(def ^:dynamic *prng* (new java.util.Random))

(defn rand!
  ([]
   (. *prng* (nextDouble)))
  ([n] (* n (rand!))))

(defn rand-int!
  [n]
  (int (rand! n)))

(defn rand-nth!
  [coll]
  (nth coll (rand-int! (count coll))))

;; TODO(nknight): verify that the distribution approximately follows mean and std
(defn normal!
  [mean std]
  (let [kernel (. *prng* nextGaussian)]
    (+ mean (* std kernel))))
