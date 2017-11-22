(ns situated-program-challenge-specgen.meetups
  (:require [compojure.api.sweet :refer [context GET POST]]
            [situated-program-challenge-specgen.spec :as spec]))

(def meetups-routes
  (context "/meetups" []
    :coercion :spec
    :tags ["meetups"]

    (GET "/" []
      :summary "ミートアップイベント一覧情報の取得"
      :description "現在登録されているミートアップイベントの一覧を取得します。"
      :return spec/meetups-spec)

    (GET "/:event-id" []
      :summary "ミートアップイベント情報の取得"
      :path-params [event-id :- ::spec/event-id]
      :description "IDで指定されたミートアップイベントを取得します。"
      :return spec/meetup-spec)))
