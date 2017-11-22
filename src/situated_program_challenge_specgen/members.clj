(ns situated-program-challenge-specgen.members
  (:require [compojure.api.sweet :refer [context GET POST]]
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
      :return spec/member-spec)))
