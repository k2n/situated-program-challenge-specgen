(ns situated-program-challenge-specgen.db.db
  (:refer-clojure :exclude [update partition-by])
  (:require [camel-snake-kebab.core :refer :all]
            [camel-snake-kebab.extras :refer [transform-keys]]
            [clojure.spec.alpha :as s]
            [clojure.java.jdbc :as jdbc]
            [honeysql.core :as sql]
            [honeysql.helpers :refer :all]
            [honeysql-postgres.format :refer :all]
            [honeysql-postgres.helpers :refer :all]
            [situated-program-challenge-specgen.db.db-pool :refer [datasource]]
            [taoensso.timbre :as log]))

(def ^:dynamic *rollback-only* false)

(def ^:dynamic *conn* nil)

(defmacro with-tx
  [& f]
  `(jdbc/with-db-transaction [tx# {:datasource datasource}]
     (let [result# (binding [*conn* tx#] ~@f)]
       (when (and *rollback-only*
                  (some? (:rollback tx#)))
         (jdbc/db-set-rollback-only! tx#))
       result#)))

(defmacro with-rollback-tx
  [& f]
  `(jdbc/with-db-transaction [tx# {:datasource datasource}]
     (let [result# (binding [*conn* tx#] ~@f)]
       (when (some? (:rollback tx#))
         (jdbc/db-set-rollback-only! tx#))
       result#)))

(defmacro with-conn
  [& f]
  `(jdbc/with-db-connection [conn# {:datasource datasource}]
     (binding [*conn* conn#] ~@f)))

(defn query
  [v]
  {:pre [(s/valid? (s/coll-of any? :kind vector?) v)]
   :post [(s/valid? (s/coll-of (s/map-of keyword? any?)) %)]}
  (->> (jdbc/query *conn* v)
       (transform-keys ->kebab-case-keyword)))

(defn query-first [v]
  (-> (query v) first))

(s/def ::id integer?)

(defn insert
  [table kvs & {:keys [created-at returning-col]
                :or {created-at :%now returning-col :id}}]
  {:pre [(s/valid? keyword? table)
         (s/valid? (s/coll-of (s/map-of keyword? any?) :kind vector?) kvs)]
   :post [(s/valid? (s/coll-of (s/map-of keyword? any?)) %)]}
  (-> (insert-into table)
      (values (if created-at (map #(assoc % :created-at :%now) kvs) kvs))
      ((fn [v] (if returning-col (returning v returning-col) v)))
      sql/format
      query))
