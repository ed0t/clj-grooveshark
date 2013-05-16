(ns clj-grooveshark.artist-test
  (:require [clojure.data.json :as json])
  (:require [clj-http.client :as client])
  (:use clojure.test
        clj-grooveshark.artist))



(defn stringify [query]
  {:body (json/write-str query)}
  )

(deftest should-get-artists-info
  (let [query (stringify {"result" {"artists" [{"ArtistID" 1261,"ArtistName" "Mogwai","IsVerified" "1"}]}})]
    (with-redefs [client/get (fn [url body json] query)]
      (is (= {:result {:artists [{:ArtistID 1261, :ArtistName "Mogwai", :IsVerified "1"}]}} (get-artists-info 1261)))
      )
    )
  )



