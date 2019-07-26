# The Oscar Programming Language

Oscar is a Java-like statically typed object-inspired modern generic programming language.

## Rationale

Newer langages like *Scala* and more recently *Kotlin* are trying to fix some of the shortcomings of the Java language, but by supporting many paradigms and ideas in parallel, some of which are contradictory or known to be mostly used for anti-patterns. Oscar is an opinionated languages with following goals:

 * There should be one (syntactic) way of doin things. Options should be avoided if possible.
 * The way things should be written should reflect best-practices.
 * Be minimal. Features should be only added if there is a large, direct benefit to be had.
 
 ## Features
 
 Following is an incomplete list of things that set Oscar apart from other similar languages:
 
  * There is no `null`. It is not just explicit, like in Kotlin, but isn't available at all.
  * There are no static methods. No constructors that can be directly invoked, no companion objects or similar tricks.
  * There is no inheritance at all, only implementing interfaces is possible.
  * There are no mutable local variables or parameters, only instance variables can be mutated, although those are also immutable by default.
  * Objects know whether they are mutable or not.
  * There are no blocking operations. At all. It is not possible to block a thread in Oscar, therefore it is not necessary to manage threads at all.
  * Everything is an `Object`, but `Object` does not have any methods. No `equals()`, `hashCode()`, `toString()`.
  * No `instanceof`, no reflection.
  * No cycle or looping structures at all. These are not langauge constructs but library functions.
  * Built-in dependency injection/management through supplier parameters.
  * No primitive types.
  * All exceptions are checked, including those in lambda expressions.
