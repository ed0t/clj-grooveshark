(ns clj-grooveshark.album
  (:use clj-grooveshark.core))

(defn get-album-search-results [query & [limit page]]
  "Search for an album name given a query. Optionally a limit and a page number can be specified."
  (let [query-params (into {} (filter second {:query query :limit limit :page page}))]
    (execute (a-query "getAlbumSearchResults" query-params))
    )
  )

(defn get-albums-info [album-id & more]
  "Returns information for the given list of album ids."
  (execute (a-query "getAlbumsInfo" {:albumIDs (concat (list album-id) more)}))
  )

(defn get-album-songs [album-id & [limit]]
  "Returns songs for the given album id."
  (let [query-params (into {} (filter second {:albumID album-id :limit limit }))]
    (execute (a-query "getAlbumSongs" query-params))
    )
  )

(defn get-does-album-exists? [album-id]
  "Checks if a given album id exists"
  (let [response (execute (a-query "getDoesAlbumExist", {:albumID album-id}))]
    (:result response)
    )
  )
