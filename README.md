# FastFF3
JavaFX frontend for Firefly III with aim to insert entries faster.

## How to build and run

```shell
$ mvn clean javafx:run
```

To debug
```shell
$  mvn clean javafx:run@cebug
```
Then, attach a remote debug to the port 8000.

## Issues

- Lombok is in scope `compile`. I didn't succeded to set it `provided` as usual because of java module usage I think.