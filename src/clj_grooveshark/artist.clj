(ns clj-grooveshark.artist
  (:use clj-grooveshark.core)
  )

(defn get-artist-search-results
  "Search for an artist given a query. A limit and a page number can be specified optionally"
  [api-key secret-key query & [limit page]]
  (let [query-params (into {} (filter second {:query query :limit limit :page page}))
        response (execute (a-query api-key "getArtistSearchResults" query-params) secret-key)]
    (handle response (fn [x] (:artists (:result x))))
    )
  )

(defn get-artist-albums
  "Retrieve albums of an artist, given an artist id"
  [api-key secret-key artist-id]
  (let [response (execute (a-query api-key "getArtistAlbums" {:artistID artist-id}) secret-key)]
    (handle response (fn [x] (:albums (:result x))))
    )
  )

(defn get-artist-popular-songs
  "Retrieves artists most popular songs"
  [api-key secret-key artist-id]
  (let [response (execute (a-query api-key "getArtistPopularSongs", {:artistID artist-id}) secret-key)]
    (handle response (fn [x] (:songs (:result x))))
    )
  )

(defn get-artist-verified-albums
  "Retrieves artist's verified album given an artist id"
  [api-key secret-key artist-id]
  (let [response (execute (a-query api-key "getArtistVerifiedAlbums", {:artistID artist-id}) secret-key)]
    (handle response (fn [x] (:albums (:result x))))
    )
  )

(defn get-does-artist-exists?
  "Checks if a given artist id exists"
  [api-key secret-key artist-id]
  (let [response (execute (a-query api-key "getDoesArtistExist", {:artistID artist-id}) secret-key)]
    (handle response (fn [x] (:result x)))
    )
  )

(defn get-artists-info
  "Returns information for the given list of artist ids."
  [api-key secret-key artist-id & more]
  (let [response (execute (a-query api-key "getArtistsInfo" {:artistIDs (concat (list artist-id) more)}) secret-key)]
    (handle response (fn [x] (:artists (:result x))))
    )
  )
