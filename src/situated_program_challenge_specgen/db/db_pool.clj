(ns situated-program-challenge-specgen.db.db-pool
  (:require [hikari-cp.core :refer :all]
            [mount.core :refer [defstate] :as mount]))

(def datasource-options {:auto-commit        true
                         :read-only          false
                         :connection-timeout 30000
                         :validation-timeout 5000
                         :idle-timeout       600000
                         :max-lifetime       1800000
                         :minimum-idle       1
                         :maximum-pool-size  10
                         :pool-name          "db-pool"
                         :adapter            "postgresql"
                         :username           "meetup"
                         :password           "password123"
                         :database-name      "meetup"
                         :server-name        "localhost"
                         :port-number        5432
                         :register-mbeans    false})

(defstate datasource
  :start  (make-datasource datasource-options)
  :stop   (close-datasource datasource))
