(ns situated-program-challenge-specgen.fixtures
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as s]
            [mount.core :as mount]
            [situated-program-challenge-specgen.db.db :as db]))

(defn with-rollback [f]
  (binding [db/*rollback-only* true]
    (f)))

(defn with-mount [f]
  (mount/start)
  (f)
  (mount/stop))
