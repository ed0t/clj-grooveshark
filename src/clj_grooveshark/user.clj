(ns clj-grooveshark.user
  (:require [digest])
  (:use clj-grooveshark.core))

(defn get-user-id-from-username
  "Returns the userId given a valid username. If the username does not exits returns UserID=0"
  [username]
  (execute (a-query2 "getUserIDFromUsername" {:username username} nil))
  )

(defn start-session
  "Start a new session"
  []
  (execute-secure (a-query "startSession"))
  )

(defn logout
  "Logout a user from a session"
  [session-id]
  (execute-secure (a-query2 "logout" nil {:sessionID session-id}))
  )

(defn authenticate
  "Authenticate a user given a username, a password and a previously created sessionId"
  [username password session-id]
  (execute-secure (a-query2 "authenticate" {:login (.toLowerCase username) :password (digest/md5 password)} {:sessionID session-id}))
  )

;(defn addUserLibrarySongs [song-ids album-ids artist-ids session-id]
;  )


(defn get-user-info
  "Returns user information for a given the sessionId. Requires an authenticated session"
  [session-id]
  (execute-secure (a-query2 "getUserInfo" nil {:sessionID session-id}))
  )

(defn get-user-subscription-details
  "Get logged-in user subscription info. Returns type of subscription and either dateEnd or recurring."
  [session-id]
  (execute-secure (a-query2 "getUserSubscriptionDetails" nil {:sessionID session-id}))
  )

;(defn get-user-favourite-songs [session-id & [limit]]
;  "Get user favorite songs. Requires an authenticated session"
;  (if (nil? limit))
;
;  (let [query-params (into {} (filter second {:limit limit}))]
;    (execute-secure (a-query2 "getUserFavoriteSongs" (empty?) {:sessionID session-id}))
;    )
;  )


