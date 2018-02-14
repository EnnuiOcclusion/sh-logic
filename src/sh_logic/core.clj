(ns sh-logic.core
  (:gen-class)
  (:refer-clojure :exclude [==])
  (:use [clojure.core.logic])
  (:require [clojure.core.logic.fd :as fd]))

(def puzzle-text
  "The first is larger than the second;
  the second twice the third;
  the third smaller than the fourth;
  the fourth is half the first.

  Four of the numbers
  are not repeated
  Three are not in the top row
  Two are not in the right row
  One of the numbers is the final key")

(defn tester []
  (run* [q]
        (fresh [a]
               (== a 1)
               (conda
                ((== a 1) (== q 2))
                (s# (== q 3))))))
(defn sorto [l]
  (conda
   [(nilo l)]
   [(emptyo (rest l))]
   [(fd/< (first l) (first (rest l))) (sorto (rest l))]))

(defn solve-puzzle []
  (run* [q]
    (fresh [a b c d]
      (== [a b c d] q)
      (everyg #(fd/in % (fd/domain 1 2 3 4 5 6 7 8 9)) [a b c d])
      (fd/> a b)
      (fd/* c 2 b)
      (fd/< c d)
      (fd/* d 2 a)

      (fd/distinct [a b c d])

      ;; Old and busted
      ;; ----------
      ;; (fresh [l1 l2 l3 l4]
      ;; (everyg #(membero % q) [l1 l2 l3])
      ;; (fd/distinct [l1 l2 l3])
      ;; (everyg #(fd/in % (fd/domain 4 5 6 7 8 9)) [l1 l2 l3]))

      ;; The New Hotness
      ;; ----------
      (fresh [l1 l2 l3 l4]
        (everyg #(membero % q) [l1 l2 l3 l4])
        (fd/distinct [l1 l2 l3 l4])
        (sorto [l1 l2 l3 l4])
        (fd/> l2 3)))))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (solve-puzzle))
