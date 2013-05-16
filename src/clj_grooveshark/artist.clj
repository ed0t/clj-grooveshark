(ns clj-grooveshark.artist
  (:use clj-grooveshark.core)
  )

(defn get-artist-search-results
  "Search for an artist given a query. A limit and a page number can be specified optionally"
  [query & [limit page]]
  (let [query-params (into {} (filter second {:query query :limit limit :page page}))]
    (execute (a-query "getArtistSearchResults" query-params))
    )
  )

(defn get-artist-albums
  "Retrieve albums of an artist, given an artist id"
  [artist-id]
  (let [response (execute (a-query "getArtistAlbums" {:artistID artist-id}))]
    (if (contains? response :errors)
      (:errors response)
      (:albums (:result response))
      )
    )
  )

(defn get-artist-popular-songs
  "Retrieves artists most popular songs"
  [artist-id]
  (execute (a-query "getArtistPopularSongs", {:artistID artist-id}))
  )

(defn get-artist-verified-albums
  "Retrieves artist's verified album given an artist id"
  [artist-id]
  (execute (a-query "getArtistVerifiedAlbums", {:artistID artist-id}))
  )

(defn get-does-artist-exists?
  "Checks if a given artist id exists"
  [artist-id]
  (let [response (execute (a-query "getDoesArtistExist", {:artistID artist-id}))]
    (:result response)
    )
  )

(defn get-artists-info
  "Returns information for the given list of artist ids."
  [artist-id & more]
  (execute (a-query "getArtistsInfo" {:artistIDs  (concat (list artist-id) more)}))
  )

(defn get-artists-info2
  "Returns information for the given list of artist ids."
  [artist-id & more]
  (exec plain (a-query2 "getArtistsInfo" {:artistIDs  (concat (list artist-id) more)}))
  )




