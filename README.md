# lein-docker

A Leiningen plugin to build and deploy [Docker](https://www.docker.com/) images.

[![Clojars Project](https://img.shields.io/clojars/v/macluck/lein-docker.svg)](https://clojars.org/macluck/lein-docker)

## Usage

Available commands:

    $ lein docker build
    $ lein docker push
    $ lein docker remove

## Configuration

You can add the following configuration options at the root of your `project.clj`:

```clojure
:docker {:image-name "myregistry.example.org/myimage"
         :dockerfile "target/dist/Dockerfile"
         :build-dir  "target"}
```

Defaults:

* `:image-name` is your project's name (without the group ID)
* `:dockerfile` points to `Dockerfile`
* `:build-dir` points to the project's root

## Releasing your docker images

You can use Leiningen to handle your technical release process. In order to do that with your Docker image instead of
your plain jar file, configure your release tasks similar to that:

```clojure
:release-tasks [["vcs" "assert-committed"]
                ["change" "version" "leiningen.release/bump-version" "release"]
                ["vcs" "commit"]
                ["vcs" "tag"]
                ["clean"]
                ["uberjar"]
                ["docker" "build"]
                ["docker" "push"]
                ["change" "version" "leiningen.release/bump-version"]
                ["vcs" "commit"]
                ["vcs" "push"]]
```

## License

Copyright (c) 2015, Tobias Sarnowski

Permission to use, copy, modify, and/or distribute this software for any purpose with or without fee is hereby granted,
provided that the above copyright notice and this permission notice appear in all copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL
IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT,
INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR PERFORMANCE OF
THIS SOFTWARE.
