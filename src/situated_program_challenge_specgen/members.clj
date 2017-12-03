(ns situated-program-challenge-specgen.members
  (:require [compojure.api.sweet :refer [context GET POST]]
            [spec-tools.swagger.core :as swagger]
            [situated-program-challenge-specgen.spec :as spec]))

(def members-routes
  (context "/members" []
    :coercion :spec
    :tags ["members"]

    (GET "/" []
      :summary "メンバー一覧情報の取得"
      :description "現在登録されているメンバーの一覧を取得します。"
      :return spec/members-spec)

    (GET "/:member-id" []
      :summary "メンバー情報の取得"
      :path-params [member-id :- ::spec/member-id]
      :description "IDで指定されたメンバー情報を取得します。"
      :return spec/member-spec)

    (POST "/" []
      :summary "メンバーの登録"
      :description "メンバーを新規登録します。"
      :body [member-request spec/member-request-spec]
      :return spec/member-spec)

    (POST "/:member-id/meetups/:meetup-id" []
      :summary "ミートアップへの参加"
      :description  "メンバーがミートアップに参加申し込みをします。"
      :return spec/meetup-spec)

    (POST "/:member-id/groups/:group-id" []
      :summary "グループへの参加"
      :description "メンバーがグループに参加申し込みをします。"
      :body [member-group-join spec/member-group-join-request-spec]
      :return spec/group-spec
      ;:responses {400 {:description "bad request???"}}
      )))
