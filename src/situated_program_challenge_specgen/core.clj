(ns situated-program-challenge-specgen.core
  (:gen-class)
  (:require [cheshire.core :as json]
            [situated-program-challenge-specgen.handler :refer [app]]
            [peridot.core :refer :all]))

(defn -main [& args]
  (-> (session app)
      (request "/swagger.json")
      :response
      :body
      slurp
      ((partial spit "target/swagger.json"))))
