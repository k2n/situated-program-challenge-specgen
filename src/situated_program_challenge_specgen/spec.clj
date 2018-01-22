(ns situated-program-challenge-specgen.spec
  (:require [clojure.spec.alpha :as s]
            [spec-tools
             [core :as st]
             [data-spec :as ds]
             [spec :as spec]]))

(s/def ::event-id           (st/spec int?      {:description "自動採番ID"}))
(s/def ::title              (st/spec string?   {:description "イベント名"}))
(s/def ::start-at           (st/spec inst?     {:description "開始日時"}))
(s/def ::end-at             (st/spec inst?     {:description "終了日時"}))
(s/def ::prefecture         (st/spec string?   {:description "都道府県"}))
(s/def ::city               (st/spec string?   {:description "市区町村"}))
(s/def ::address1           (st/spec string?   {:description "丁目番地"}))
(s/def ::address2           (st/spec string?   {:description "建物名"}))
(s/def ::postal-code        (st/spec string?   {:description "郵便番号"}))
(s/def ::venue-id           (st/spec int?      {:description "自動採番ID"}))
(s/def ::venue-name         (st/spec string?   {:description "会場名"}))
(s/def ::member-id          (st/spec int?      {:description "自動採番ID"}))
(s/def ::first-name         (st/spec string?   {:description "名"}))
(s/def ::last-name          (st/spec string?   {:description "姓"}))
(s/def ::email              (st/spec string?   {:description "Eメールアドレス"}))
(s/def ::group-id           (st/spec int?      {:description "自動採番ID"}))
(s/def ::group-name         (st/spec string?   {:description "グループ名"}))
(s/def ::admin              (st/spec boolean?  {:description "管理者フラグ"}))
(s/def ::online-venue-id    (st/spec int?      {:description "自動採番ID"}))
(s/def ::venue-url          (st/spec string?   {:description "オンライン会場URL"}))

(def address {:postal-code        ::postal-code
              :prefecture         ::prefecture
              :city               ::city
              :address1           ::address1
              (ds/opt :address2)  ::address2})

(def address-spec
  (ds/spec ::address address
           {:description "住所"}))

(def venue {:venue-id   ::venue-id
            :venue-name ::venue-name
            :address    address-spec})

(def online-venue {:online-venue-id    ::online-venue-id
                   :venue-name         ::venue-name
                   :url                ::venue-url})

(def venue-spec
  (ds/spec ::venue venue
           {:description "会場"}))

(def online-venue-spec
  (ds/spec ::online-venue online-venue
           {:description "オンライン会場"}))

(def venue-request-spec
  (ds/spec ::venue-request (dissoc venue :venue-id) {:description "会場"}))

(def online-venue-request-spec
  (ds/spec ::online-venue-request (dissoc online-venue :online-venue-id) {:description "オンライン会場"}))

(def venues-spec
  (ds/spec ::venues [venue] {:description "会場"}))

(def online-venues-spec
  (ds/spec ::online-venues [online-venue] {:description "オンライン会場"}))

(def member {:member-id   ::member-id
             :first-name  ::first-name
             :last-name   ::last-name
             :email       ::email})

(def member-request-spec
  (ds/spec ::member-request (dissoc member :member-id) {:description "メンバーリクエスト"}))

(def member-spec
  (ds/spec ::member member {:description "メンバーレスポンス"}))

(def members-spec
  (ds/spec ::members [member] {:description "メンバーズレスポンス"}))

(def meetup {:event-id          ::event-id
             :title             ::title
             :start-at          ::start-at
             :end-at            ::end-at
             :venue             venue-spec
             :online-venue      online-venue-spec
             :members           members-spec})

(def meetup-request-spec
  (ds/spec ::meetup-request (-> meetup
                                (dissoc :event-id :members :venue :online-venue)
                                (assoc :venue-id ::venue-id
                                       :online-venue-id ::online-venue-id))
           {:description "ミートアップリクエスト"}))

(def meetup-spec
  (ds/spec ::meetup meetup {:description "ミートアップレスポンス"}))

(def meetups-spec
  (ds/spec ::meetups [meetup] {:description "ミートアップスレスポンス"}))

(def group {:group-id         ::group-id
            :group-name       ::group-name
            :admin            members-spec
            :venues           venues-spec
            :online-venues    online-venues-spec
            :meetups          meetups-spec})

(def group-request-spec
  (ds/spec ::group-request (-> group
                               (dissoc :group-id :venues :online-venues :meetups :admin)
                               (assoc :admin-member-ids [::member-id])) {:description "グループリクエスト"}))

(def group-spec
  (ds/spec ::group group {:description "グループレスポンス"}))

(def new-group-spec
  (ds/spec ::new-group (dissoc group :venues :online-venues :meetups) {:description "新規グループレスポンス"}))

(def groups-spec
  (ds/spec ::groups [group] {:description "グループスレスポンス"}))

(def member-group-join-request-spec
  (ds/spec ::member-group-join-request-spec {:admin     ::admin}
           {:description "メンバーグループジョインリクエスト"}))
