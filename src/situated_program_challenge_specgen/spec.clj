(ns situated-program-challenge-specgen.spec
  (:require [clojure.spec.alpha :as s]
            [spec-tools.spec :as spec]
            [spec-tools.core :as st]))

(s/def ::event-id (st/spec int? {:description "DBが自動採番したID"}))
(s/def ::title (st/spec string? {:description "イベント名"}))
(s/def ::email (st/spec string? {:description "Eメールアドレス"}))
(s/def ::prefecture (st/spec string? {:description "都道府県"}))
(s/def ::city (st/spec string? {:description "市区町村"}))
(s/def ::address1 (st/spec string? {:description "丁目番地"}))
(s/def ::address2 (st/spec string? {:description "建物名"}))
(s/def ::postal-code (st/spec string? {:description "郵便番号"}))
(s/def ::venue-id (st/spec int? {:description "DBが自動採番したID"}))
(s/def ::venue-name (st/spec string? {:description "開催場所名"}))

(s/def ::address (st/spec (s/keys :req-un [::postal-code
                                           ::prefecture
                                           ::city
                                           ::address1]
                                  :opt-un [::address2])
                          {:description "住所"}))
(s/def ::venue (st/spec (s/keys :req-un [::venue-id
                                         ::venue-name
                                         ::address])
                        {:description "開催場所"}))
(s/def ::meetup (st/spec (s/keys :req-un [::title
                                          ::venue])
                         {:description "ミートアップ"}))
(s/def ::meetups (st/spec (s/* (s/keys :req-un [::event-id
                                                ::title
                                                ::venue]))
                          {:description "ミートアップス"}))
