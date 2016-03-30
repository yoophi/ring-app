(ns ring-app.core
  (:require [ring.adapter.jetty :as jetty]))

(defn handler [request-map]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (str "<html><body>your IP is: "
              (:remote-addr request-map)
              "</body></html>")})

(defn wrap-nocache [handler]
  (fn [request]
    (-> request
        handler
        (assoc-in [:headers "Pragma"] "no-cache"))))

(defn -main []
  (jetty/run-jetty
    (wrap-nocache handler)
    {:port 3000
     :join? false}))
