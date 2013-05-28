(ns clj-grooveshark.user
  (:require [digest])
  (:use clj-grooveshark.core))

(defn get-user-id-from-username
  "Returns the userId given a valid username. If the username does not exits returns UserID=0"
  [api-key secret-key username]
  (execute (a-query api-key "getUserIDFromUsername" {:username username} nil) secret-key)
  )

(defn start-session
  "Start a new session"
  [api-key secret-key]
  (execute secure (a-query api-key "startSession") secret-key)
  )

(defn logout
  "Logout a user from a session"
  [api-key secret-key session-id]
  (execute secure (a-query api-key "logout" nil {:sessionID session-id}) secret-key)
  )

(defn authenticate
  "Authenticate a user given a username, a password and a previously created sessionId"
  [api-key secret-key username password session-id]
  (execute secure (a-query api-key "authenticate" {:login (.toLowerCase username) :password (digest/md5 password)} {:sessionID session-id}) secret-key)
  )

;(defn addUserLibrarySongs [song-ids album-ids artist-ids session-id]
;  )


(defn get-user-info
  "Returns user information for a given the sessionId. Requires an authenticated session"
  [api-key secret-key session-id]
  (execute secure (a-query api-key "getUserInfo" nil {:sessionID session-id}) secret-key)
  )

(defn get-user-subscription-details
  "Get logged-in user subscription info. Returns type of subscription and either dateEnd or recurring."
  [api-key secret-key session-id]
  (execute secure (a-query api-key "getUserSubscriptionDetails" nil {:sessionID session-id}) secret-key)
  )

;(defn get-user-favourite-songs [session-id & [limit]]
;  "Get user favorite songs. Requires an authenticated session"
;  (if (nil? limit))
;
;  (let [query-params (into {} (filter second {:limit limit}))]
;    (execute-secure (a-query2 "getUserFavoriteSongs" (empty?) {:sessionID session-id}))
;    )
;  )


