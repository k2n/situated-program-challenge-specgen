(defproject situated-program-challenge-specgen "0.1.0-SNAPSHOT"
  :description "Generate swagger json file for 
               https://github.com/clj-nakano/situated-program-challenge"
  :url "https://github.com/k2n/situated-program-challenge-specgen"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[clj-time              "0.14.0"]
                 [metosin/compojure-api "2.0.0-alpha12"]
                 [metosin/spec-tools    "0.5.1"]
                 [peridot               "0.5.0"]
                 [org.clojure/clojure   "1.9.0-RC1"]]
  :aliases {"swagger" ["run" "-m" "situated-program-challenge-specgen.core"]}
  :ring {:handler situated-program-challenge-specgen.handler/app
         :nrepl {:start? false}}
  :profiles {:dev {:plugins [[lein-ring "0.12.0"]
                             [com.jakemccrary/lein-test-refresh "0.21.1"]]}})
