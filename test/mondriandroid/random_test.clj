(ns mondriandroid.random-test
  (:require [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [mondriandroid.random :as mr ]))

(def test-iterations 100)

(defn- fresh-r
  "Create a fresh PRNG."
  ([] (fresh-r 429606))
  ([seed] (new java.util.Random seed)))

(defn seq-eq-with-same-seed [s f]
  (let [r1 (fresh-r s)
        r2 (fresh-r s)]
    (= (repeatedly 1000 #(f r1))
       (repeatedly 1000 #(f r2)))))


(defspec rand!-equal-seeds
  test-iterations
  (prop/for-all [s gen/int]
                (seq-eq-with-same-seed s mr/rand!)))

(defspec rand-int!-equal-seeds
  test-iterations
  (prop/for-all [s gen/int
                 n gen/nat]
                (seq-eq-with-same-seed s #(mr/rand-int! % n))))

(defspec rand-nth!-equal-seeds
  (prop/for-all [s gen/int
                 c (-> gen/int
                       gen/vector
                       gen/not-empty)]
                (seq-eq-with-same-seed s #(mr/rand-nth! % c))))

;; TODO(nknight): properties of individual functions
