(ns clj-grooveshark.basic
  (:use clj-grooveshark.core)
  (:require [clojure.data.json :as json])
  )


(defn ping
  "Ping Grooveshark"
  []
  (let [response (execute-basic (a-query "pingService")) ]
    (if (contains? (json/read-str (:body response)) "errors")
      (println "errors")
      (println "ok")
      )
    )
  )


(defn get-service-description []
  (execute (a-query "getServiceDescription"))
  )

(defn get-country  []
  (execute (a-query "getCountry"))
  )


