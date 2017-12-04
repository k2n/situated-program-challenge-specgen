(ns situated-program-challenge-specgen.db.db-test
  (:refer-clojure :exclude [update partition-by])
  (:require [clojure.spec.alpha :as s]
            [orchestra.spec.test :as stest]
            [clojure.test :refer :all]
            [honeysql.core :as sql]
            [honeysql.helpers :refer :all]
            [honeysql-postgres.format :refer :all]
            [honeysql-postgres.helpers :refer :all]
            [mount.core :as mount]
            [situated-program-challenge-specgen.fixtures :refer :all]
            [situated-program-challenge-specgen.db.db :refer :all]))

(stest/instrument)

(use-fixtures :once with-rollback with-mount)

(deftest dbtest
  (testing "query input doen't conform with spec."
    (is (thrown? java.lang.AssertionError (query "foo"))))

  (testing "query with valid arg"
    (is (= 1 (with-conn
               (-> (select :*)
                   (from :groups)
                   sql/format
                   query-first
                   :id)))))

  (testing "insert rows to groups"
    (with-tx
      (is (s/valid? (s/coll-of (s/keys :req-un [::id]))
                    (insert :groups [{:name "test1"} {:name "test2"}]))))))
