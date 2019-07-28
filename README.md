# The Oscar Programming Language

Oscar is a statically typed object-inspired modern generic programming language.

## Rationale

When Java came out in the 90's it brought a lot of revolutionary ideas to the mainstream development community. However, that was more than 20 years ago and there are a lot of lessons the communiy learned since then. Oscar is the Java syntax + all the experience applied without having to be backwards compatible with anything.

Following idea underlies these changes: **There should be a *single* *correct* way to write things.**

"*Single*" means to not have redundancies. There should not be multiple ways to iterate, multiple ways to instantiate an object, multiple ways to define objects or interfaces. In short, all idioms should be the same for all developers regardless of personal style.

"*Correct*" means that things written should be unambiguous and do what readers would expect. Also, things that often lead to misunderstandings, failures or are often misused are consiered incorrect. Examples include inheritance, addition with overflow, etc.

This idea also means, that some things that are not included in Java but turned out to have a correct way to do become a part of the language. This includes exceptions handling scenarios like rethrowing or handling.
 
 ## Differences to Java
 
 Following is an incomplete but representative list of differences how Oscar differs from Java:
 
  * There is no `null`.
  * There are no static methods. No companion objects or similar built-in singletons.
  * There is no inheritance at all, only implementing interfaces is possible.
  * There are no mutable local variables or parameters, only instance variables can be mutated.
  * There are no blocking operations. It is not possible to block a thread in Oscar.
  * No primitive values, everything is an `Object`.
  * `Object` does not have any methods. No `equals()`, `hashCode()`, `toString()`.
  * No `instanceof`, no reflection.
  * No cycles, looping structures.
  * Built-in dependency injection/management through supplier parameters.
  * All exceptions are checked, including those in lambda expressions.
