(ns mondriandroid.random-test
  (:require [clojure.test.check.clojure-test :refer [defspec]]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test :as t]
            [mondriandroid.random :as mr ]))

;;; Unit tests

(t/deftest default-not-equal
  (t/is (not=
         (doall (repeatedly 10 mr/rand!))
         (doall (repeatedly 10 mr/rand!)))
        "Random numbers form the default generator are random."))


;;; Property tests

(def test-iterations 100)

(defn- fresh-r
  "Create a fresh PRNG."
  ([] (fresh-r 429606))
  ([seed] (new java.util.Random seed)))

(defn seq-eq-with-same-seed [s f]
  (let [r1 (fresh-r s)
        r2 (fresh-r s)]
    (= (binding [mondriandroid.random/*prng* r1]
         (doall (repeatedly 1000 f)))
       (binding [mondriandroid.random/*prng* r2]
         (doall (repeatedly 1000 f))))))

(defspec rand!-equal-seeds
  test-iterations
  (prop/for-all [s gen/int]
                (seq-eq-with-same-seed s mr/rand!)))

(defspec rand-int!-equal-seeds
  test-iterations
  (prop/for-all [s gen/int
                 n gen/nat]
                (seq-eq-with-same-seed s #(mr/rand-int! n))))

(defspec rand-nth!-equal-seeds
  (prop/for-all [s gen/int
                 c (-> gen/int
                       gen/vector
                       gen/not-empty)]
                (seq-eq-with-same-seed s #(mr/rand-nth! c))))

(defspec normal!-equal-seeds
  (prop/for-all [s gen/int
                 mean (gen/double* {:infinite? false :NaN? false})
                 std (gen/double* {:infinite? false :NaN? false})]
                (seq-eq-with-same-seed s #(mr/normal! mean std))))


;; TODO(nknight): properties of individual functions
