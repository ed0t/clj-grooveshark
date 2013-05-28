(ns clj-grooveshark.artist
  (:use clj-grooveshark.core)
  )

(defn get-artist-search-results
  "Search for an artist given a query. A limit and a page number can be specified optionally"
  [query & [limit page]]
  (let [query-params (into {} (filter second {:query query :limit limit :page page}))]
    (let [response (execute (a-query2 "getArtistSearchResults" query-params))]
      (handle response (fn [x] (:artists (:result x))))
      )
    )
  )

(defn get-artist-albums
  "Retrieve albums of an artist, given an artist id"
  [artist-id]
  (let [response (execute (a-query2 "getArtistAlbums" {:artistID artist-id}))]
    (handle response (fn [x] (:albums (:result x))))
    )
  )

(defn get-artist-popular-songs
  "Retrieves artists most popular songs"
  [artist-id]
  (let [response (execute (a-query2 "getArtistPopularSongs", {:artistID artist-id}))]
    (handle response (fn [x] (:songs (:result x))))
    )
  )

(defn get-artist-verified-albums
  "Retrieves artist's verified album given an artist id"
  [artist-id]
  (let [response (execute (a-query2 "getArtistVerifiedAlbums", {:artistID artist-id}))]
    (handle response (fn [x] (:albums (:result x))))
    )
  )

(defn get-does-artist-exists?
  "Checks if a given artist id exists"
  [artist-id]
  (let [response (execute (a-query2 "getDoesArtistExist", {:artistID artist-id}))]
    (handle response (fn [x] (:result x)))
    )
  )

(defn get-artists-info
  "Returns information for the given list of artist ids."
  [artist-id & more]
  (let [response (execute (a-query2 "getArtistsInfo" {:artistIDs (concat (list artist-id) more)}))]
    (handle response (fn [x] (:artists (:result x))))
    )
  )
