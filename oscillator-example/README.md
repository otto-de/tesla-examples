# A simple oscillator example

This is a very simple application to demonstrate how to create an application based on
[otto-de/oscillator](https://github.com/otto-de/oscillator) with the usage of
[tesla-microservice](https://github.com/otto-de/tesla-microservice).

So there are two relevant dependencies:

`[de.otto/oscillator "0.2.4"]`
`[de.otto/tesla-microservice "0.1.18"]`


## Running it
Get [leiningen](http://leiningen.org/#install). Clone the repo. 

Adjust:

* `:base-url` in `pages/page-config` to your graphite server
* targets in `charts`  to point to proper graphite data series
* targets in `annotations` to point to your deployment events (or remove them)

Start the example application with

`$ lein run`

Access the example dashboard under `http://localhost:8080/`

It should look something like this:

![Example Dashboard](doc/oscillator.png)
