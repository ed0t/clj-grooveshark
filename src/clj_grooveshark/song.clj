(ns clj-grooveshark.song
  (:use clj-grooveshark.core))

;(defn get-song-search-results)

(defn get-does-song-exists?
  "Checks if a given song id exists"
  [api-key secret-key song-id]
  (let [response (execute (a-query api-key "getDoesSongExist", {:songID song-id}) secret-key)]
    (:result response)
    )
  )

(defn get-songs-info
  "Returns information for the given list of song ids."
  [api-key secret-key song-id & more]
  (execute (a-query api-key "getSongsInfo" {:artistIDs (concat (list song-id) more)}) secret-key)
  )

(defn get-popular-songs-today
  "Get a subset of today's popular songs, from the Grooveshark popular billboard."
  ([api-key secret-key] (execute (a-query api-key "getPopularSongsToday" nil nil)) secret-key)
  ([api-key secret-key limit] (execute (a-query api-key "getPopularSongsToday" {:limit limit} nil) secret-key))

  )
