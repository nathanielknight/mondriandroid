(ns mondriandroid.templates
  (:require [hiccup.core :as h]))

;; TODO(nknight): Get the copy checked

(def nav
  [:nav
   [:a {"href" "/" } "Home"]
   [:a {"href" "/generator"} "Generator"]
   [:a {"href" "/about"} "About"]])

(def copyright
  [:footer
   [:p "© 2016 Nathaniel Knight. Shared under a Createive Commons Attribution Share-Alike License"]])

(defn- boilerplate [body]
  [:html
   [:head [:title "Mondriandroid"]]
   [:body
    nav
    (vec (cons :main (vec body)))
    copyright]])

(defn render [body]
  (h/html (boilerplate body)))



(def about
  [[:h1 "About"]
   [:p
    "Mondriandroid is a generative art project by"
    [:a {:href "http://nathanielknight.ca"} "Nathaniel Knight"] ". "
    "It's meant to simulate the distinctive grid-based paintings of "
    [:a {:href "https://en.wikipedia.org/wiki/Piet_Mondrian"} "Piet Mondrian"] ". "
    "It was enthusiastically built with "
    [:a {:href "http://clojure.org/"} "Clojure"] ". "
    "You can see the code on "
    [:a {:href "https://github.com/neganp/mondriandroid"} "Github"] ". "]
   [:img {"src" "/generate"}]
   ])

(def index
  [[:h1 "Mondriandroid"]
   [:p
    "A generative art project by"
    [:a {:href "http://nathanielknight.ca"} "Nathaniel Knight"]
    "."]
   [:img {"src" "/generate"}]
   ])

(defn four-oh-four [path]
  (render
    [[:h1 "Not a thing"]
     [:p "As far as I can tell , "
      [:code (pr-str path)]
      " isn't a thing."]]))


;; TODO(nknight): Form-based generator to set seed, width, height
(def generator
  [[:p "It's not done yet"]])


(def templates
  {:index (render index)
   :about (render about)
   :generator (render generator)})
