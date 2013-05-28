(ns clj-grooveshark.core
  (:require [clj-http.client :as client])
  (:require [clojure.data.json :as json])
  (:use clj-grooveshark.crypto)
  (:use clj-grooveshark.configuration)
  (:use [clojure.tools.logging :only (error debug)])
  )

(org.apache.log4j.BasicConfigurator/configure)


(defn a-query
  "Creates a query with a given method name. Optionally can receive a map of body parameters and a map of header parameters."
  [api-key method & [parameters header]]
  (let [query-params (into {} (filter second {:parameters parameters :header header}))]
    (json/write-str (assoc query-params :method method :header (assoc (get query-params :header ) :wsKey api-key)))
    )
  )


(defn handle
  "Handles the respose and applies a callback function in case no errors are returned."
  [response callback]
  (if (contains? response :errors )
    (:errors response)
    (do
      (debug response)
      (callback response)
      )
    )
  )


;TO REFACTOR!
(defn execute-basic [query]
  (client/get (:url configuration) {:body query} {:as :json})
  )

(def secure
  (:secure-url configuration)
  )

(def plain
  (:url configuration)
  )


(defn- do-execute
  [url query secret-key]
  (json/read-str (:body (client/get (str url "?sig=" (sign secret-key query)) {:body query} {:as :json})) :key-fn keyword)
  )

(defn execute
  "executes a query. It can be a standard query or a secure query (HTTPS). For the latter pass secure as first parameter."
  ([query secret-key] (do-execute plain query secret-key))
  ([url query secret-key] (do-execute url query secret-key))
  )









