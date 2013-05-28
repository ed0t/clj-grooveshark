(ns clj-grooveshark.basic
  (:use clj-grooveshark.core)
  (:require [clojure.data.json :as json])
  )


(defn ping
  "Ping Grooveshark"
  [api-key]
  (let [response (execute-basic (a-query api-key "pingService"))]
    (if (contains? (json/read-str (:body response)) "errors")
      (println "errors")
      (println "ok")
      )
    )
  )


(defn get-service-description [api-key secret-key]
  (execute (a-query api-key "getServiceDescription") secret-key)
  )

(defn get-country [api-key secret-key]
  (execute (a-query api-key "getCountry") secret-key)
  )


