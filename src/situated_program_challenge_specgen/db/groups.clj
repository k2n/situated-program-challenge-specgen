(ns situated-program-challenge-specgen.db.groups
  (:require [clojure.spec.alpha :as s]
            [situated-program-challenge-specgen.db.db :as db]))

(s/def ::id integer?)
(s/def ::name string?)
(s/def ::admin-member-id integer?)
(s/def ::admin-member-ids (s/coll-of ::admin-member-id :kind vector?))

(defn insert [kvs]
  {:pre [(s/valid? (s/coll-of (s/keys :req-un [::name ::admin-member-ids]
                                      :opt-un [])
                              :kind vector?) kvs)]}
  (let [ids (db/insert :groups
                       (vec (for [kv kvs]
                              (dissoc kv :admin-member-ids))))
        idskvs (->> (zipmap ids kvs)
                    (map #(into {} %)))]
    (db/insert :groups-members
               (vec (for [idkv idskvs
                          id   (:admin-member-ids idkv)]
                      {:member-id id
                       :group-id (:id idkv)
                       :admin true}))
               :created-at nil
               :returning-col :member-id)))
