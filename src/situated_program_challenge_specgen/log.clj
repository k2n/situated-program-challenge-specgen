(ns situated-program-challenge-specgen.log
  (:require [taoensso.timbre :refer :all]))

(set-config! {:level :info
              :ns-whitelist ["situated-program-challenge.*"]})
