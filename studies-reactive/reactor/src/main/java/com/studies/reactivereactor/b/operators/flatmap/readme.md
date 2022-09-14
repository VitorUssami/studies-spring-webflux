# Operator: Flatmap

* Transforms one source elemento to a Flux of 1 to N elements

  * "ABC"-> Flux.just("A","B","C")

* Use it when the transformation returns a Reactive Type (flux/mono)
* FlatMap **always** returns a Flux<T> !!!



# Operator: Map Vs Flatmap

| Map  | Flatmap |
| ------------- | ------------- |
| one to one transformation   | one to N transformations |
| simple transformation  | Subscribes to a Flux/Mono that's part of transformation and the flattens it and sends it downstream  |
| used for simple synchronous transformations | used for asynchronous transformations |
| does not support transformations that returns publisher | used it with transformations that returns publisher |