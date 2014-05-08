(defproject clj-grooveshark "0.1.1-SNAPSHOT"
  :description "Clojure bindings for Grooveshark API"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [clj-http "0.7.0"]
                 [org.clojure/data.json "0.2.1"]
                 [digest "1.4.3"]
                 [org.clojure/tools.logging "0.2.6"]
                 [log4j/log4j "1.2.16" :exclusions [javax.mail/mail
                                                    javax.jms/jms
                                                    com.sun.jdmk/jmxtools
                                                    com.sun.jmx/jmxri]]]
  :plugins [[codox "0.7.3"]]
  :codox {:src-dir-uri "https://bitbucket.org/ed0t/clj-grooveshark/blob/master/"}
  )
