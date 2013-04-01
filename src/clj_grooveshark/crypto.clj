(ns clj-grooveshark.crypto
  (:import (javax.crypto Mac)
           (javax.crypto.spec SecretKeySpec)))

(defn secretKeyInst [key mac]
  (SecretKeySpec. (.getBytes key) (.getAlgorithm mac)))

(defn sign
  "Returns the signature of a string with a given
    key, using a MD5 HMAC."
  [key string]
  (let [mac (Mac/getInstance "HMacMD5")
        secretKey (secretKeyInst key mac)]
    (-> (doto mac
          (.init secretKey)
          (.update (.getBytes string)))
      .doFinal)))



(defn toString [byte]
  (let [character (format "%x" byte)]
    (if (= (count character) 1)
      (str 0 character)
      character
      )
    )
  )

(defn toHexString
  "Convert bytes to a String"
  [bytes]
  (apply str (map toString bytes)))

(defn sign-as-string [key string]
  (toHexString (sign key string))
  )