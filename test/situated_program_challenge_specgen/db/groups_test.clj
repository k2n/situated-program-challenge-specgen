(ns situated-program-challenge-specgen.db.groups-test
  (:require [clojure.test :refer :all]
            [clojure.spec.alpha :as s]
            [orchestra.spec.test :as stest]
            [situated-program-challenge-specgen.fixtures :refer :all]
            [situated-program-challenge-specgen.db.db :as db]
            [situated-program-challenge-specgen.db.groups :refer :all]))

(stest/instrument)

(use-fixtures :once with-mount with-rollback)

(s/def ::member-id integer?)

(deftest groups-test
  (testing "insert"
    (is (s/valid? (s/coll-of (s/keys :req-un [::member-id]))
                  (db/with-tx (insert [{:name "clj-nakano"
                                        :admin-member-ids [0]}
                                       {:name "clj-ebisu"
                                        :admin-member-ids [1 2]}]))))))
