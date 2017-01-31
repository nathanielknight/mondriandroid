(defproject mondriandroid "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0-alpha14"]
                 [org.clojure/data.xml "0.0.8"]
                 [org.immutant/web "2.1.5"]
                 [ring/ring-core "1.5.0"]
                 [hiccup "1.0.5"]]
  :profiles {:dev {:dependencies [[ring/ring-devel "1.5.0"]
                                  [org.clojure/test.check "0.9.0"]]
                   :plugins [[cider/cider-nrepl "0.15.0-SNAPSHOT"]]}}
  :main mondriandroid.core
  )
