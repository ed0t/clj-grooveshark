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
      (is (= [{:ArtistID 1261, :ArtistName "Mogwai", :IsVerified "1"}] (get-artists-info 1261)))
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

(deftest should-check-if-an-artist-exists
  (let [response (stringify {"header" {"hostname" "RHL073"}, "result" "true"})]
    (with-redefs [client/get (fn [url body json] response)])
      (is (true? (get-does-artist-exists? 1261)))
    )
  )

(deftest should-search-for-an-artist
  (let [response (stringify {:header {:hostname "RHL061"}, :result {:pager {:numPages 5, :hasPrevPage false, :hasNextPage false}, :artists [{:ArtistID 1261, :ArtistName "Mogwai", :IsVerified true} {:ArtistID 1410559, :ArtistName "Mogwai", :IsVerified false} {:ArtistID 3437, :ArtistName "Bloc Party", :IsVerified true} {:ArtistID 1086380, :ArtistName "Mogwai & Bardo Pond", :IsVerified false} {:ArtistID 357876, :ArtistName "Fuck Buttons", :IsVerified false}]}})]
    (with-redefs [client/get (fn [url body json] response)])
      (is (= [{:ArtistID 1261, :ArtistName "Mogwai", :IsVerified true} {:ArtistID 1410559, :ArtistName "Mogwai", :IsVerified false} {:ArtistID 3437, :ArtistName "Bloc Party", :IsVerified true} {:ArtistID 1086380, :ArtistName "Mogwai & Bardo Pond", :IsVerified false} {:ArtistID 357876, :ArtistName "Fuck Buttons", :IsVerified false}] (get-artist-search-results "Mogwai")))
    )
  )

(deftest should-return-artist-verified-albums
  (let [response (stringify {:header {:hostname "RHL061"}, :result {:pager {:numPages 27, :hasPrevPage false, :hasNextPage false}, :albums [{:AlbumID 121379, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "121379.jpg", :IsVerified true, :AlbumName "Come On Die Young"} {:AlbumID 121381, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "121381.jpg", :IsVerified true, :AlbumName "Happy Songs for Happy People"} {:AlbumID 138795, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "138795.jpg", :IsVerified true, :AlbumName "Zidane: A 21st Century Portrait"} {:AlbumID 177201, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "177201.jpg", :IsVerified true, :AlbumName "Young Team"} {:AlbumID 182451, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "182451.jpg", :IsVerified true, :AlbumName "Rock Action"} {:AlbumID 182453, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "182453.jpg", :IsVerified true, :AlbumName "Ten Rapid: Collected Recordings 1996-1997"} {:AlbumID 189945, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "189945.jpg", :IsVerified true, :AlbumName "4 Satin EP"} {:AlbumID 391834, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "391834.jpg", :IsVerified true, :AlbumName "EP+6"} {:AlbumID 494059, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "494059.jpg", :IsVerified true, :AlbumName "Kicking a Dead Pig: Mogwai Songs Remixed"} {:AlbumID 606939, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "606939.jpg", :IsVerified true, :AlbumName "5 Track Tour Single"} {:AlbumID 663218, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "663218.jpg", :IsVerified true, :AlbumName "Travel Is Dangerous"} {:AlbumID 1382953, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "1382953.jpg", :IsVerified true, :AlbumName "No Education = No Future (Fuck the Curfew)"} {:AlbumID 1382955, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "1382955.jpg", :IsVerified true, :AlbumName "Travels in Constants, Volume 12"} {:AlbumID 1536356, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "1536356.jpg", :IsVerified true, :AlbumName "Mr. Beast"} {:AlbumID 1951386, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "1951386.jpg", :IsVerified true, :AlbumName "The Hawk Is Howling"} {:AlbumID 2557534, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "2557534.jpg", :IsVerified true, :AlbumName "My Father My King"} {:AlbumID 2863750, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "2863750.jpg", :IsVerified true, :AlbumName "Batcat"} {:AlbumID 3259718, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "3259718.jpg", :IsVerified true, :AlbumName "EP"} {:AlbumID 3588758, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "3588758.jpg", :IsVerified true, :AlbumName "Friend of the Night"} {:AlbumID 3992417, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "3992417.jpg", :IsVerified true, :AlbumName "Government Commissions: BBC Sessions 1996-2003"} {:AlbumID 4514590, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "4514590.jpg", :IsVerified true, :AlbumName "Special Moves"} {:AlbumID 4955954, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "4955954.jpg", :IsVerified true, :AlbumName "EP + 2"} {:AlbumID 5263098, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "5263098.jpg", :IsVerified true, :AlbumName "Hardcore Will Never Die, but You Will"} {:AlbumID 6141751, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "6141751.jpg", :IsVerified true, :AlbumName "Mexican Grand Prix"} {:AlbumID 6680038, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename nil, :IsVerified true, :AlbumName "Earth Division EP"} {:AlbumID 7165413, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "7165413.jpg", :IsVerified true, :AlbumName "Rano Pano"} {:AlbumID 8593636, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename nil, :IsVerified true, :AlbumName "A Wrenched Virile Lore"}]}}
    )]
    (with-redefs [client/get (fn [url body json] response)])
      (is (= [{:AlbumID 121379, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "121379.jpg", :IsVerified true, :AlbumName "Come On Die Young"} {:AlbumID 121381, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "121381.jpg", :IsVerified true, :AlbumName "Happy Songs for Happy People"} {:AlbumID 138795, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "138795.jpg", :IsVerified true, :AlbumName "Zidane: A 21st Century Portrait"} {:AlbumID 177201, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "177201.jpg", :IsVerified true, :AlbumName "Young Team"} {:AlbumID 182451, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "182451.jpg", :IsVerified true, :AlbumName "Rock Action"} {:AlbumID 182453, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "182453.jpg", :IsVerified true, :AlbumName "Ten Rapid: Collected Recordings 1996-1997"} {:AlbumID 189945, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "189945.jpg", :IsVerified true, :AlbumName "4 Satin EP"} {:AlbumID 391834, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "391834.jpg", :IsVerified true, :AlbumName "EP+6"} {:AlbumID 494059, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "494059.jpg", :IsVerified true, :AlbumName "Kicking a Dead Pig: Mogwai Songs Remixed"} {:AlbumID 606939, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "606939.jpg", :IsVerified true, :AlbumName "5 Track Tour Single"} {:AlbumID 663218, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "663218.jpg", :IsVerified true, :AlbumName "Travel Is Dangerous"} {:AlbumID 1382953, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "1382953.jpg", :IsVerified true, :AlbumName "No Education = No Future (Fuck the Curfew)"} {:AlbumID 1382955, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "1382955.jpg", :IsVerified true, :AlbumName "Travels in Constants, Volume 12"} {:AlbumID 1536356, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "1536356.jpg", :IsVerified true, :AlbumName "Mr. Beast"} {:AlbumID 1951386, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "1951386.jpg", :IsVerified true, :AlbumName "The Hawk Is Howling"} {:AlbumID 2557534, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "2557534.jpg", :IsVerified true, :AlbumName "My Father My King"} {:AlbumID 2863750, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "2863750.jpg", :IsVerified true, :AlbumName "Batcat"} {:AlbumID 3259718, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "3259718.jpg", :IsVerified true, :AlbumName "EP"} {:AlbumID 3588758, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "3588758.jpg", :IsVerified true, :AlbumName "Friend of the Night"} {:AlbumID 3992417, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "3992417.jpg", :IsVerified true, :AlbumName "Government Commissions: BBC Sessions 1996-2003"} {:AlbumID 4514590, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "4514590.jpg", :IsVerified true, :AlbumName "Special Moves"} {:AlbumID 4955954, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "4955954.jpg", :IsVerified true, :AlbumName "EP + 2"} {:AlbumID 5263098, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "5263098.jpg", :IsVerified true, :AlbumName "Hardcore Will Never Die, but You Will"} {:AlbumID 6141751, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "6141751.jpg", :IsVerified true, :AlbumName "Mexican Grand Prix"} {:AlbumID 6680038, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename nil, :IsVerified true, :AlbumName "Earth Division EP"} {:AlbumID 7165413, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename "7165413.jpg", :IsVerified true, :AlbumName "Rano Pano"} {:AlbumID 8593636, :ArtistID 1261, :ArtistName "Mogwai", :CoverArtFilename nil, :IsVerified true, :AlbumName "A Wrenched Virile Lore"}] (get-artist-verified-albums 1261)))
    )
  )






