(ns practicalli.clojure-app-template
  (:gen-class)
  (:require
    [com.brunobonacci.mulog :as mulog]))

(defn greet
  "Callable entry point to the application."
  [moniker]
  (str "Welcome to Clojure " (or (:name moniker) "everyone")))

(defn -main
  "Entry point into the application via clojure.main -M"
  [& args]
  (mulog/log ::service-starup :user :practicalli)
  (greet {:name (first args)}))



;; Rich comment block with redefined vars ignored
#_{:clj-kondo/ignore [:redefined-var]}
(comment

  (-main)
  (-main "Jenny")

  #_()) ;; End of rich comment block
