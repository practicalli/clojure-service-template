(ns build
  (:refer-clojure :exclude [test])
  (:require [org.corfield.build :as build-task]))

(def lib 'net.clojars.practicalli/clojure-app-template)
(def version "0.1.0-SNAPSHOT")
(def main 'practicalli.clojure-app-template)

(defn test "Run the tests." [opts]
  (build-task/run-tests opts))

(defn ci "Run the CI pipeline of tests (and build the uberjar)." [opts]
  (-> opts
      (assoc :lib lib :version version :main main)
      (build-task/run-tests)
      (build-task/clean)
      (build-task/uber)))
