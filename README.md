# clj-grooveshark

`clj-grooveshark` provides Clojure binding for Grooveshark API.

Currently the binding is incomplete.


## Usage

```
  (ns hello-world
    (:use clj-grooveshark.artist))

  (defn search-artist [name]
    (get-artist-search-results "grooveshark-api-key" "grooveshark-secret-key" name))

```


## Installation



`clj-grooveshark` is not yet available as a Maven artifact from [Clojars](http://clojars.org/clj-grooveshark), but it will soon:

    :dependencies
      [["clj-grooveshark" "0.1.1-SNAPSHOT"]
       ...]

in the meantime just clone the code and build it locally.

## Development

    $ git clone git@bitbucket.org:ed0t/clj-grooveshark.git
    $ lein deps
    $ lein test
    $ lein install


## License

Copyright © 2013 Edoardo Tosca

Distributed under the Eclipse Public License, the same as Clojure.
