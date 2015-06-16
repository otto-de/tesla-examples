# zookeeper example

This simple example demonstrates how to use the [zookeeper addon](https://github.com/otto-de/tesla-zookeeper-observer) to [tesla-microservice](https://github.com/otto-de/tesla-microservice).

## Running zookeeper

For testing you can run zookeeper locally.

1. Download from [zookeeper.apache.org](https://zookeeper.apache.org/) and unpack
2. start with ```bin/zkServer.sh```
3. connect with ```bin/zkCli.sh```
4. create some data under the path ```/foo``` with ```create /foo bar```.

## Running the example

1. Clone the repo.
2. If you are running zookeeper locally, `resources/default.properties` already has a valid connection string: ```zookeeper.connect=localhost:2181``` you can override it with an environment variable: ```ZOOKEEPER_CONNECT=localhost:2182```.
3. Start the application with `lein run`.
4. Access the example service under `http://localhost:8080/example` and `http://localhost:8080/example/foo`. The latter will report which data, if any, zookeeper has for the path ```/foo```.
5. In ```bin/zkCli.sh``` try changing the data with ```set /foo baz``` and reload the page.

## License

Apache License
