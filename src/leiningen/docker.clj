(ns leiningen.docker
  (:require [leiningen.core.eval :as eval]
            [leiningen.core.main :as main]
            [leiningen.docker.commands :as c]))

(defn- exec [& args]
  (apply main/debug "Exec: docker" args)
  (apply eval/sh "docker" args))

(def valid-command? #{:build :push :remove})

(defn docker
  "Builds and delpoys docker images.
   Commands:
     'build' builds your docker image
     'push' pushes your docker image
     'remove' removes your docker image"
  [project command & [image-name]]

  (let [command (keyword command)]

    (when-not (valid-command? command)
      (main/warn "Invalid command" command)
      (main/exit 1))

    (let [config        (:docker project)
          image-name    (or image-name
                            (:image-name config)
                            (str (:name project)))
          image-version (or (:image-version config)
                            (:version project))
          image         (str image-name ":" image-version)
          build-dir     (or (:build-dir config)
                            (:root project))
          dockerfile    (or (:dockerfile config)
                            "Dockerfile")
          data          {:image       image
                         :build-dir   build-dir
                         :dockerfile  dockerfile}]

      (case command
        :build  (c/build data exec)
        :push   (c/push data exec)
        :remove (c/remove data exec)))))
