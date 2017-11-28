(ns situated-program-challenge-specgen.groups
  (:require [compojure.api.sweet :refer [context GET POST]]
            [situated-program-challenge-specgen.spec :as spec]))

(def groups-routes
  (context "/groups" []
    :coercion :spec
    :tags ["groups"]

    (GET "/" []
      :summary "グループ一覧の取得"
      :description "現在登録されているグループ一覧を取得します。"
      :return spec/groups-spec)

    (POST "/" []
      :summary "グループの登録"
      :description "新しいグループを登録します。"
      :body [group-request spec/group-request-spec]
      :return spec/group-spec)

    (context "/:group-id/meetups" []
      :coercion :spec
      :tags ["meetups"]

      (GET "/" []
        :summary "ミートアップイベント一覧情報の取得"
        :description "現在登録されているミートアップイベントの一覧を取得します。"
        :return spec/meetups-spec)

      (POST "/" []
        :summary "ミートアップイベントの登録"
        :description "新しいミートアップを登録します。"
        :body [meetup-request spec/meetup-request-spec]
        :return spec/meetup-spec)

      (GET "/:event-id" []
        :summary "ミートアップイベント情報の取得"
        :path-params [event-id :- ::spec/event-id]
        :description "IDで指定されたミートアップイベントを取得します。"
        :return spec/meetup-spec))

    (context "/:group-id/venues" []
      :coercion :spec
      :tags ["venues"]

      (GET "/" []
        :summary "会場一覧の取得"
        :description "登録されている会場の一覧を取得します。"
        :return spec/venues-spec)

      (POST "/" []
        :summary "会場の登録"
        :description "会場を登録します。"
        :body [venue-request spec/venue-request-spec]
        :return spec/venue-spec))))
