(ns situated-program-challenge-specgen.handler
  (:require [compojure.api.sweet :refer [api context GET POST]]
            [muuntaja.core :as muuntaja]
            [situated-program-challenge-specgen
             [meetups :refer [meetups-routes]]
             [members :refer [members-routes]]]))

(def app
  (api
   {:swagger
    {:ui "/"
     :spec "/swagger.json"
     :data {:info {:title "Situated Program Challenge"}
            :tags [{:name "meetups"
                    :description "ミートアップエンティティ"}
                   {:name "members"
                    :description "メンバーエンティティ"}]}}
    :formats (-> muuntaja/default-options
                 (update-in [:formats]
                            dissoc
                            "application/edn"
                            "application/transit+json"
                            "application/transit+msgpack"))}
   meetups-routes
   members-routes))
