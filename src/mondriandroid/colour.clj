(ns mondriandroid.colour)

(def white "ghostwhite")
(def black "black")
(def blue "mediumblue")
(def yellow "gold")
(def red "red")

(defn random-colour
  "Randomly return a color, with a bias towards white."
  []
  (let [x (rand-int 10)]
    (cond
      (< x 7) white
      (< x 8) blue
      (< x 9) yellow
      (< x 10) red
      :else white)))
