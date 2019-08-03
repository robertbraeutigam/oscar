# The Oscar Programming Language

Oscar is a statically typed object-inspired modern generic programming language.

## Rationale

When Java came out in the 90's it brought a lot of revolutionary ideas to the mainstream development community. However, that was more than 20 years ago and there are a lot of lessons the communiy learned since then. Oscar is the Java syntax + all the experience applied without having to be backwards compatible with anything.

Following idea underlies these changes: **There should be a *single* *correct* way to write things.**

"*Single*" means to not have redundancies. There should not be multiple ways to iterate, multiple ways to instantiate an object, multiple ways to define objects or interfaces. In short, all idioms should be the same for all developers regardless of personal style.

"*Correct*" means that things written should be unambiguous and do what readers would expect. Also, things that often lead to misunderstandings, failures or are often misused are consiered incorrect. Examples include inheritance, addition with overflow, etc.

This idea also means, that some things that are not actually included in Java, but turned out to have a single correct way to do, become a part of Oscar. This includes exceptions handling scenarios like rethrowing or handling.
 
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

## Syntax

### Objects

The most important building blocks of an Oscar software are Objects. Objects are analogous to Java Classes, except there is no inheritance, so calling them classes would be confusing.

```oscar
object Money(val amount: Integer, val currency: Currency) {
   ...
}
```

By default all `object`s are immutable, if that's not the case the object needs to be marked `mutable object...`. There are no visibility modifiers for objects, all objects are "public final" in the Java sense.

All `object`s need a primary constructor that all other constructors need to refer to. If there are more to be done in the construction phase, the object may define a code block for the default constructor with:

```oscar
object Money(val amount: Integer, val currency: Currency) {
   def Money() {
      ...here the object values are already set...
   }
   ...
}
```

There can be optional values in all parameter lists:

```oscar
object Money(val amount: Integer = 0, val currency: Currency) {
   ...
}
```

There can be secondary constructors with different signatures, note however, that because optional parameters are possible, signatures that just omit optional parameters are not possible. It is however possible to define "named" constructors, like this:

```oscar
object Money(val amount: Integer = 0, val currency: Currency) {
   def Money.zeroDollars() {
      return this(0, Dollar);
   }
   ...
}
```
