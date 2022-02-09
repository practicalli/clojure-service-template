;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Tools for REPL Driven Development
;;
;; Start repl with aliases from practicalli/clojure-deps-edn
;; :lib/hotload:inspect/portal-cli
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(ns user
  "Tools for REPL Driven Development")


;; Hotload libraries into running REPL
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Start REPL with :lib/hotload alias from practicalli/clojure-deps-edn
(comment
  (require '[clojure.tools.deps.alpha.repl :refer [add-libs]])
  (add-libs '{domain/library-name {:mvn/version "1.0.0"}})
  #_()) ;; End of rich comment block


;; Portal Data Inspector -
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;; Start REPL with :lib/hotload alias from practicalli/clojure-deps-edn
(comment
  (require '[portal.api :as inspect])
  ;; Open a portal inspector in browser window - light theme
  (inspect/open {:portal.colors/theme :portal.colors/solarized-light})
  ;; Open Portal window in browser with dark theme
  (inspect/open {:portal.colors/theme :portal.colors/solarized-dark})

  ;; Add portal as a tap> target
  (inspect/tap)

  ;; Clear all values in the portal inspector window
  (inspect/clear)

  ;; Close the portal window
  (inspect/close)

  #_()) ;; End of rich comment


;; TODO: Component services - Integrant-repl
