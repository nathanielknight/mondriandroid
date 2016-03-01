(ns mondriandroid.rect
  "Generic constructors for rectangualar objects.")

(defn point [a b]
  [a b])

(defn x-of [point]
  (first point))

(defn y-of [point]
  (second point))

(defn transl-point-x [v1 x]
  (point (+ (x-of v1) x)
         (y-of v1)))

(defn transl-point-y [v1 y]
  (point (x-of v1)
         (+ (y-of v1) y)))

(defn rect
  [ll ur]
  [ll ur])

(defn ll-of [rect]
  (first rect))

(defn ur-of [rect]
  (second rect))

(defn width [rect]
  (Math/abs (- (-> rect ll-of x-of) (-> rect ur-of x-of))))

(defn height [rect]
  (Math/abs (- (-> rect ll-of y-of) (-> rect ur-of y-of))))

(defn TODO [& args]
  (throw "I haven't been defined yet"))

(defn split-x [r rx]
  (TODO))

(defn split-y [r ry]
  (TODO))
