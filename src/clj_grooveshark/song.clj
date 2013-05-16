(ns clj-grooveshark.song
  (:use clj-grooveshark.core))

;(defn get-song-search-results)

(defn get-does-song-exists?
  "Checks if a given song id exists"
  [song-id]
  (let [response (execute (a-query "getDoesSongExist", {:songID song-id}))]
    (:result response)
    )
  )

(defn get-songs-info
  "Returns information for the given list of song ids."
  [song-id & more]
  (execute (a-query "getSongsInfo" {:artistIDs  (concat (list song-id) more)}))
  )

(defn get-popular-songs-today
  "Get a subset of today's popular songs, from the Grooveshark popular billboard."
  ([] (execute (a-query2 "getPopularSongsToday" nil nil)))
  ([limit] (execute (a-query2 "getPopularSongsToday" {:limit  limit} nil)))

  )
