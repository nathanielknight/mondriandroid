(ns mondriandroid.random
  "Versions of random-number functions that accept a random number
  generator (so that the seed can be set).")

(defn rand!
  ([r]
   (. r (nextDouble)))
  ([r n] (* n (rand! r))))

(defn rand-int!
  [r n]
  (int (rand! r n)))

(defn rand-nth!
  [r coll]
  (nth coll (rand-int! r (count coll))))
