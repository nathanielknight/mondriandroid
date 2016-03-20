(ns mondriandroid.colour)

(def white "ghostwhite")
(def black "black")
(def blue "mediumblue")
(def yellow "gold")
(def red "red")


(defn random-weights [weights]
  (rand-nth
   (apply
    concat
    (for [[val count] weights]
            (repeat count val)))))

(defn random-colour
  "Randomly return a color, with a bias towards white."
  []
  (random-weights
   {white 12
    black 1
    red 2
    blue 2
    yellow 2}
   )
)
