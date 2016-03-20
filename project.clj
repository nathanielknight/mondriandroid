(defproject mondriandroid "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.7.0"]
                 [org.clojure/data.xml "0.0.8"]
                 [org.apache.commons/commons-math3 "3.6"]]
  :profiles {:dev {:dependencies [[ring/ring-core "1.4.0"]
                                  [ring/ring-jetty-adapter "1.4.0"]]
                   :plugins [[cider/cider-nrepl "0.8.2"]
                             [lein-ring "0.9.7"]]
                   :ring {:handler mondriandroid.handler/dev}}})
