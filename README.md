# Reactive-Programming
Java Reactive Programming

![reactor](./img/reactor.png) {: height="100px" width="100px} <br>
[reactor tutorial]("https://tech.io/playgrounds/929/reactive-programming-with-reactor-3/Intro") <br> 
[project reactor]("https://projectreactor.io/")

### Flux


```java
static <T> Flux<T> empty()
```
- Create a Flux that completes without emitting any item.
 
```java
static <T> Flux<T> just(T... data)
```
- Create a new Flux that emits the specified item(s) and then complete.

```java
static <T> Flux<T> fromIterable(Iterable<? extends T> it)
```
- Create a Flux that emits the items contained in the provided Iterable.
```java
static <T> Flux<T> error(Throwable error)
```
- Create a Flux that completes with the specified error.
```java
static Flux<Long> interval(Duration period)
```
- Create a new Flux that emits an ever incrementing long starting with 0 every period on the global timer.

```java
Flux.fromIterable(List.of("foo","bar"))
                .doOnNext(System.out::println)
                .map(String::toUpperCase)
                .subscribe(System.out::println);
```

```java
Flux.interval(Duration.ofMillis(1000))
                .take(10)
                .subscribe(System.out::println);
        System.out.println("thread test");
        Thread.sleep(1000*10);

```
