(defproject situated-program-challenge-specgen "0.1.0-SNAPSHOT"
  :description "Generate swagger json file for 
               https://github.com/clj-nakano/situated-program-challenge"
  :url "https://github.com/k2n/situated-program-challenge-specgen"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[camel-snake-kebab             "0.4.0"]
                 [clj-time                      "0.14.0"]
                 [com.fzakaria/slf4j-timbre     "0.3.2"]
                 [com.taoensso/timbre           "4.10.0"]
                 [hikari-cp                     "1.8.3"]
                 [honeysql                      "0.9.1"]
                 [metosin/compojure-api         "2.0.0-alpha12"]
                 [metosin/spec-tools            "0.5.1"]
                 [mount                         "0.1.11"]
                 [nilenso/honeysql-postgres     "0.2.3"]
                 [orchestra                     "2017.11.12-1"]
                 [org.clojure/clojure           "1.9.0-RC2"]
                 [org.clojure/java.jdbc         "0.7.3"]
                 [org.postgresql/postgresql     "9.4-1206-jdbc42"] 
                 [peridot                       "0.5.0"]]
  :aliases {"swagger" ["run" "-m" "situated-program-challenge-specgen.core"]}
  :ring {:handler situated-program-challenge-specgen.handler/app
         :init    mount.core/start
         :destroy mount.core/stop
         :nrepl {:start? false}}
  :profiles {:dev {:source-paths ["dev"]
                   :timbre-level "INFO"
                   :plugins [[lein-ring "0.12.0"]
                             [com.jakemccrary/lein-test-refresh "0.21.1"]]}})
