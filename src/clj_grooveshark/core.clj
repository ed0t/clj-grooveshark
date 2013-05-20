(ns clj-grooveshark.core
  (:require [clj-http.client :as client])
  (:require [clojure.data.json :as json])
  (:use clj-grooveshark.crypto)
  (:use clj-grooveshark.configuration)
  )

;(org.apache.log4j.BasicConfigurator/configure)

(defn a-query
  ([method] (json/write-str {:method method :header {:wsKey (:key configuration)}}))
  ([method parameters] (json/write-str {:method method :parameters parameters :header {:wsKey (:key configuration)}}))
  ([method session-id parameters] (json/write-str {:method method :parameters parameters :header {:wsKey (:key configuration) :sessionID session-id}}))
  )

(defn a-query2
  "Creates a query with a given method name. Optionally can receive a map of body parameters and a map of header parameters."
  [method & [parameters header]]
  (let [query-params (into {} (filter second {:parameters parameters :header header}))]
    (json/write-str (assoc query-params :method method :header (assoc (get query-params :header ) :wsKey (:key configuration))))
    )
  )


;TO REFACTOR!
(defn execute-basic [query]
  (client/get (:url configuration) {:body query} {:as :json})
  )

;(defn execute [query]
;  ;  (json/read-str (:body (client/get (str (:url configuration) "?sig=" (toHexString (create-signature (:secret configuration) query))) {:body query} {:as :json})) :key-fn keyword )
;  (json/read-str (:body (client/get (str (:url configuration) "?sig=" (sign (:secret configuration) query)) {:body query} {:as :json})) :key-fn keyword)
;  )

(defn execute-secure [query]
  ;  (json/read-str (:body (client/get (str (:secure-url configuration) "?sig=" (toHexString (create-signature (:secret configuration) query))) {:body query} {:as :json})) :key-fn keyword )
  (json/read-str (:body (client/get (str (:secure-url configuration) "?sig=" (sign (:secret configuration) query)) {:body query} {:as :json})) :key-fn keyword)
  )


(def secure
  (:secure-url configuration)
  )

(def plain
  (:url configuration)
  )


(defn- do-execute
  [url query]
  (json/read-str (:body (client/get (str url "?sig=" (sign (:secret configuration) query)) {:body query} {:as :json})) :key-fn keyword)
  )

(defn execute
  ([query] (do-execute plain query))
  ([url query] (do-execute url query))
  ;  (json/read-str (:body (client/get (str url "?sig=" (sign (:secret configuration) query)) {:body query} {:as :json})) :key-fn keyword )
  )









