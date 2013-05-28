(ns clj-grooveshark.playlist
  (:use clj-grooveshark.core))

(defn get-playlist
  "Get playlist info and songs"
  [api-key secret-key playlist-id & [limit]]
  (let [query-params (into {} (filter second {:playlistID playlist-id :limit limit}))]
    (execute (a-query api-key "getPlaylist" query-params nil) secret-key)
    )
  )

(defn get-playlist-info
  "Get playlist information. To get songs use get-playlist"
  [api-key secret-key playlist-id]
  (execute (a-query api-key "getPlaylistInfo" {:playlistID playlist-id} nil) secret-key)
  )

(defn get-playlist-songs
  "Get playlist songs. Use get-playlist"
  [api-key secret-key playlist-id & [limit]]
  (let [query-params (into {} (filter second {:playlistID playlist-id :limit limit}))]
    (execute (a-query api-key "getPlaylistSongs" query-params nil) secret-key)
    )
  )

(defn create-playlist
  "Create a new playlist, optionally adding songs to it"
  ([api-key secret-key playlist-name session-id song-id & more]
    (execute secure (a-query api-key "createPlaylist" {:name playlist-name :songIDs (concat (list song-id) more)} {:sessionID session-id}) secret-key))
  ([api-key secret-key playlist-name session-id song-ids]
    (execute secure (a-query api-key "createPlaylist" {:name playlist-name :songIDs song-ids} {:sessionID session-id}) secret-key))
  )

(defn set-playlist-songs
  "Set playlist songs, overwrites any already saved"
  [api-key secret-key playlist-id session-id song-id & more]
  (execute secure (a-query api-key "setPlaylistSongs" {:playlistID playlist-id :songIDs (concat (list song-id) more)} {:sessionID session-id}) secret-key)
  )

(defn rename-playlist
  "Renames a playlist"
  [api-key secret-key playlist-id playlist-name session-id]
  (execute secure (a-query api-key "renamePlaylist" {:playlistID playlist-id :name playlist-name} {:sessionID session-id}) secret-key)
  )

(defn delete-playlist
  "Deletes a playlist"
  [api-key secret-key playlist-id session-id]
  (execute secure (a-query api-key "deletePlaylist" {:playlistID playlist-id} {:sessionID session-id}) secret-key)
  )

;DOES NOT WORK. ALWAYS RETURN FALSE
(defn undelete-playlist
  "Undeletes a playlist. Does not work!"
  [api-key secret-key playlist-id session-id]
  (execute secure (a-query api-key "undeletePlaylist" {:playlistID playlist-id} {:sessionID session-id}) secret-key)
  )

