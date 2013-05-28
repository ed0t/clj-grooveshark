(ns clj-grooveshark.album
  (:use clj-grooveshark.core))

(defn get-album-search-results [api-key secret-key query & [limit page]]
  "Search for an album name given a query. Optionally a limit and a page number can be specified."
  (let [query-params (into {} (filter second {:query query :limit limit :page page}))
        response (execute (a-query api-key "getAlbumSearchResults" query-params) secret-key)]
    (handle response (fn [x] (:albums (:result x))))
    )
  )

(defn get-albums-info [api-key secret-key album-id & more]
  "Returns information for the given list of album ids."
  (execute (a-query api-key "getAlbumsInfo" {:albumIDs (concat (list album-id) more)}) secret-key)
  )

(defn get-album-songs [api-key secret-key album-id & [limit]]
  "Returns songs for the given album id."
  (let [query-params (into {} (filter second {:albumID album-id :limit limit}))]
    (execute (a-query api-key "getAlbumSongs" query-params) secret-key)
    )
  )

(defn get-does-album-exists? [api-key secret-key album-id]
  "Checks if a given album id exists"
  (let [response (execute (a-query api-key "getDoesAlbumExist", {:albumID album-id}) secret-key)]
    (handle response (fn [x] (:result x)))
    )
  )
