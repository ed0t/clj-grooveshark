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
      (is (= {:result {:artists [{:ArtistID 1261, :ArtistName "Mogwai", :IsVerified "1"}]}} (get-artists-info2 1261)))
      )
    )
  )



(deftest should-return-albums-for-an-artists
  (let [response (stringify {:header {:hostname "RHL109"}, :result {:pager {:numPages 342, :hasPrevPage false, :hasNextPage true}, :albums [{:AlbumID 1550, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename nil, :IsVerified false, :AlbumName "Unknown Album"}]}})]
    (with-redefs [client/get (fn [url body json] response)]
      (is (= [{:AlbumID 1550, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename nil, :IsVerified false, :AlbumName "Unknown Album"}] (get-artist-albums 1261)))
      )
    )
  )

(deftest should-return-a-list-of-popular-songs-for-an-artist
  (let [response (stringify {:header {:hostname "RHL081"}, :result {:songs [{:AlbumID 8790822, :ArtistID 12, :SongID 38508082, :AlbumName "Disco 2", :ArtistName "Joaquin Sabina", :SongName "Y Sin Embargo", :Flags 0, :IsVerified false, :Popularity "1314002909", :IsLowBitrateAvailable false, :CoverArtFilename nil}]}})]
    (with-redefs [client/get (fn [url body json] response)]
      (println (get-artist-popular-songs 1261))
      (is (= [{:AlbumID 8790822, :ArtistID 12, :SongID 38508082, :AlbumName "Disco 2", :ArtistName "Joaquin Sabina", :SongName "Y Sin Embargo", :Flags 0, :IsVerified false, :Popularity "1314002909", :IsLowBitrateAvailable false, :CoverArtFilename nil}] (get-artist-popular-songs 1261)))
      )
    )
  )

(deftest should-return-an-empty-list-when-artists-id-does-not-exists
  (let [response (stringify {"header" {"hostname" "RHL110"}, "result" {"songs" []}})]
    (with-redefs [client/get (fn [url body json] response)]
      (is (empty? (get-artist-popular-songs "11111")))
      )
    )
  )




