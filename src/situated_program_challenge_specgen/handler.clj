(ns situated-program-challenge-specgen.handler
  (:require [compojure.api.sweet :refer [api context GET POST]]
            [muuntaja.core :as muuntaja]
            [situated-program-challenge-specgen
             [members :refer [members-routes]]
             [groups :refer [groups-routes]]]))

(def app
  (api
   {:swagger
    {:ui "/"
     :spec "/swagger.json"
     :data {:info {:title "Situated Program Challenge"}
            :tags [{:name "meetups"
                    :description "ミートアップエンティティ"}
                   {:name "members"
                    :description "メンバーエンティティ"}
                   {:name "venues"
                    :description "会場エンティティ"}
                   {:name "groups"
                    :description "グループエンティティ"}]}}
    :formats (-> muuntaja/default-options
                 (update-in [:formats]
                            dissoc
                            "application/edn"
                            "application/transit+json"
                            "application/transit+msgpack"))}
   groups-routes
   members-routes))
