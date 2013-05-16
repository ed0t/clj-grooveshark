(ns clj-grooveshark.core-test
  (:require [clojure.data.json :as json])
  (:use clojure.test
        clj-grooveshark.core))


(defn jsonify [query]
  (json/read-str query)
  )


(deftest should-create-a-basic-query
  (testing "a basic query"
    (is (= {"header" {"wsKey" "devonlinux"}, "method" "pingService"} (jsonify (a-query2 "pingService"))))
    )
  )

(deftest should-create-a-query-with-parameters
  (testing "a query with parameters"
    (is (= {"header" {"wsKey" "devonlinux"}, "method" "getDoesAlbumExist", "parameters" {"albumID" "1234"}} (jsonify (a-query2 "getDoesAlbumExist", {:albumID "1234"}))))
    )
  )

(deftest should-create-a-query-with-extra-headers
  (testing "a query with extra headers"
    (is (= {"method" "logout", "header" {"wsKey" "devonlinux", "sessionID" "1234abcd"}} (jsonify (a-query2 "logout" nil , {:sessionID "1234abcd"}))))
    )
  )


