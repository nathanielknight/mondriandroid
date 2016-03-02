(ns mondriandroid.rect-test
  (:require [clojure.test :refer :all]
            [mondriandroid.rect :refer :all]))



(deftest test-rect-structure
  
  (testing "Can retrieve points from rects"
    (let [o (point 0 0)
          p (point 1 1)
          r (rect o p)]
      (is (= o (ll-of r)))
      (is (= p (ur-of r)))
      (is (= 0 (x-of (ll-of r)) (y-of (ll-of r))))
      (is (= 1 (x-of (ur-of r)) (y-of (ur-of r)))))))


(deftest test-splitting
  
  (testing "Can split horizintally"
    (let [r (rect (point 0 0)
                  (point 1 1))
          [r1 r2] (split-x r 0.3)]
      (is (= 0.3 (width r1)))
      (is (= 1 (height r1)))
      (is (= 0.7 (width r2)))
      (is (= 1 (height r2)))))
  
  (testing "Can split vertically"
    (let [r (rect (point 0 0)
                  (point 1 1))
          [r1 r2] (split-y r 0.3)]
      (is (= 1 (width r1)))
      (is (= 0.3 (height r1)))
      (is (= 1 (width r2)))
      (is (= 0.7 (height r2))))))


