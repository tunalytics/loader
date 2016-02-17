# Loader
ETL process handler
#### Building and execution
Execute the following command inside of the project folder:
```
$ gradle clean build
```
After it you can run the loader locally by executing:
```
$ java -jar build/libs/loader.jar
```
#### Miscellaneous
Signal topology components (ones located inside of `.topology.signals` package) are for system protopyping / testing only and should be removed from any production-ready code.
