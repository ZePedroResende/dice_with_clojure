(ns dice.core
(:require [iota                  :as iota]
          [clojure.core.reducers :as r]
          [clojure.string        :as str])
 (:gen-class))

(defn parse-int [s]
   (Integer. (re-find  #"\d+" s )))

(defn roll
  [nroll nfaces]
  (->> (repeat nroll nfaces)
       (r/map (fn [x](+ (rand-int (- x 1)) 1)))
       (r/fold +)))

(defn tokenizer
  [s]
  (map parse-int (str/split s #"d")))

(defn result
  [x]
  (apply roll (tokenizer x)))

(defn parsefile
  [filename]
  (let [result
        (->> (iota/seq filename)
          (r/filter identity)
          (r/map result)
          (r/reduce #(+ %1 %2) 0))]
    (println "resultados" result )))

(defn -main [& args]
(prn (format "args=%s" args))
  (if (not (empty? args))
    (do
      (time(parsefile (first args))))))

