# Operator: zip | zipWith

* `zip()` - static method in Flux 
  * can be used to merge up-to 2 to 8 publishers in to one 
  
  
* `zipWith()` - instance method in Flux and Mono 
 * used to merge two publishers in to one 

 
* publishers are subscribed eagerly
* waits for all publishers involved in the transformation to emit one element
