;; ---------------------------------------------------------
;; Build Script
;;
;; Build project and package for deployment
;; - `jar` for library
;; - `uberjar` for service
;; - `clean` remove all build assets and jar files
;;
;; All funcitons are passed command line arguments
;; - `nil` is passed if there are no arguments
;;
;;
;; tools.build API commands
;; - `create-basis` create a project basis
;; - `copy-dir` copy Clojure source and resources into a working dir
;; - `compile-clj` compile Clojure source code to classes
;; - `delete` - remove path from file space
;; - `write-pom` - write pom.xml and pom.properties files
;; - `jar` - to jar up the working dir into a jar file
;;
;; ---------------------------------------------------------



(ns build
  (:require
   [clojure.tools.build.api :as build-api]
   [clojure.pprint :as pprint]))


;; ---------------------------------------------------------
;; Project configuration

(def project-config
  "Project configuration to support all tasks"
  (let [library-name 'practicalli/clojure-app-template
        version (format "1.0.%s" (build-api/git-count-revs nil))]
    {:library-name    library-name
     :main-namespace  library-name
     :project-version version
     :class-directory "target/classes"
     :project-basis   (build-api/create-basis)
     :jar-file        (format "target/%s-%s.jar" (name library-name) version)
     :uberjar-file    (format "target/%s-%s-standalone.jar" (name library-name) version)}))

;; End of Build configuration
;; ---------------------------------------------------------


(defn config
  "Display build configuration"
  [config]
  ;; (pprint/pprint project-config)

  (pprint/pprint (or config project-config)))

;; ---------------------------------------------------------
;; Testing tasks
;; - optionally include a test runner
;; End of Testing tasks
;; ---------------------------------------------------------


;; ---------------------------------------------------------
;; Build tasks

(defn clean
  "Remove a directory
  - `:path '\"directory-name\"'` for a specific directory
  - `nil` (or no command line arguments) to delete `target` directory
  `target` is the default directory for build artefacts

  Ensures `.` and `/` directories are not deleted"
  [directory]
  (when
   (not (contains? #{"." "/"} directory))
    (build-api/delete {:path (or (:path directory) "target")})))


(defn jar
  "Create a build of the project, cleaning existing build assets first"
  [options]
  (let [config (merge project-config options)
        {:keys [class-directory jar-file library-name project-basis project-version]} config]
    (clean "target")
    (pprint/pprint project-config)
    (build-api/write-pom {:class-dir class-directory
                          :lib       library-name
                          :version   project-version
                          :basis     project-basis
                          :src-dirs  ["src"]})
    (build-api/copy-dir {:src-dirs   ["src" "resources"]
                         :target-dir class-directory})
    (build-api/jar {:class-dir class-directory
                    :jar-file  jar-file})))


(defn uberjar
  "Create an archive containing Clojure and the build of the project"
  [options]
  (let [config (merge project-config options)
        {:keys [class-directory main-namespace project-basis uberjar-file]} config]
    (clean "target")
    (build-api/copy-dir {:src-dirs   ["src" "resources"]
                         :target-dir class-directory})

    (build-api/compile-clj {:basis     project-basis
                            :src-dirs  ["src"]
                            :class-dir class-directory})

    (build-api/uber {:class-dir class-directory
                     :uber-file uberjar-file
                     :basis     project-basis
                     :main      main-namespace})))

;; End of Build tasks
;; ---------------------------------------------------------


;; ---------------------------------------------------------
;; Deployment tasks
;; - optional deployment tasks for services or libraries

;; End of Deployment tasks
;; ---------------------------------------------------------
