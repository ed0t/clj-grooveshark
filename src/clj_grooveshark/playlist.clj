(ns clj-grooveshark.playlist
  (:use clj-grooveshark.core))

(defn get-playlist
  "Get playlist info and songs"
  [playlist-id & [limit]]
  (let [query-params (into {} (filter second {:playlistID playlist-id :limit limit}))]
    (execute (a-query2 "getPlaylist" query-params nil))
    )
  )

(defn get-playlist-info
  "Get playlist information. To get songs use get-playlist"
  [playlist-id]
  (execute (a-query2 "getPlaylistInfo" {:playlistID playlist-id} nil))
  )

(defn get-playlist-songs
  "Get playlist songs. Use get-playlist"
  [playlist-id & [limit]]
  (let [query-params (into {} (filter second {:playlistID playlist-id :limit limit}))]
    (execute (a-query2 "getPlaylistSongs" query-params nil))
    )
  )

(defn create-playlist
  "Create a new playlist, optionally adding songs to it"
  ([playlist-name session-id song-id & more]
  (execute-secure (a-query2 "createPlaylist" {:name playlist-name :songIDs (concat (list song-id) more)} {:sessionID session-id})))
  ([playlist-name session-id song-ids]
  (execute-secure (a-query2 "createPlaylist" {:name playlist-name :songIDs song-ids} {:sessionID session-id})))
  )

(defn set-playlist-songs
  "Set playlist songs, overwrites any already saved"
  [playlist-id session-id song-id & more]
  (execute-secure (a-query2 "setPlaylistSongs" {:playlistID playlist-id :songIDs (concat (list song-id) more)} {:sessionID session-id}))
  )

(defn rename-playlist
  "Renames a playlist"
  [playlist-id playlist-name session-id]
  (execute-secure (a-query2 "renamePlaylist" {:playlistID playlist-id :name playlist-name} {:sessionID session-id}))
  )

(defn delete-playlist
  "Deletes a playlist"
  [playlist-id session-id]
  (execute-secure (a-query2 "deletePlaylist" {:playlistID playlist-id} {:sessionID session-id}))
  )

;DOES NOT WORK. ALWAYS RETURN FALSE
(defn undelete-playlist
  "Undeletes a playlist. Does not work!"
  [playlist-id session-id]
  (execute-secure (a-query2 "undeletePlaylist" {:playlistID playlist-id} {:sessionID session-id}))
  )

