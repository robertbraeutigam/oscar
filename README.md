# The Oscar Programming Language

Oscar is a statically typed object-inspired modern generic programming language.

## Rationale

When Java came out in the 90's it brought a lot of revolutionary ideas to the mainstream development community. However, that was more than 20 years ago and there are a lot of lessons the communiy learned since then. Oscar is the result of applying all this experience, starting from the Java language, but withou the need to be backwards compatible.

 ## Differences to Java
 
 Following is an incomplete but representative list of differences between Oscar and Java:
 
  * There is no `null`.
  * There are no static methods. No companion objects or similar built-in singletons.
  * There is no inheritance at all, only implementing interfaces is possible.
  * There are no mutable local variables or parameters, only instance variables can be mutated.
  * There are no blocking operations. It is not possible to block a thread in Oscar.
  * No primitive values, everything is an object.
  * Objects by default do not have any methods. No `equals()`, `hashCode()`, `toString()`.
  * No `instanceof`, `getClass()`, no reflection.
  * No cycles, looping structures.
  * Built-in dependency injection/management through supplier parameters.
  * All exceptions are checked, including those in lambda expressions.

## Syntax

### Objects

The most important building blocks of an Oscar software are Objects. Objects are analogous to Java Classes, except there is no inheritance.

```oscar
object Money(val amount: Integer, val currency: Currency) {
   ...
}
```

By default all `object`s are immutable, if that's not the case the object needs to be marked `mutable object...`. There are no visibility modifiers for objects, all objects are "public final" in the Java sense.

Objects can not be "abstract", since there is no inheritance.

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
      return this(0, Currency.Dollar());
   }
   ...
}
```

### Instance variables

All parameters declared in the primary constructor are automatically instance variables too. Additional instance variables can be declared in the body of the `object` with the keywords `val` or `var`.

Variables declared `val` are immutable, equivalent to "final" variables in Java. Note however that the referenced object itself may be mutable. Variables declared `var` are mutable variables, conceptually equal to "non-final" instance variables.

```oscar
object Money(val amount: Integer = 0, val currency: Currency) {
   val cents: Integer = amount * 100;

   def Money.zeroDollars() {
      return this(0, Currency.Dollar());
   }
   ...
}
```

Note that objects are immutable by default. Only `mutable object`s may use `var` declarations. Note also that immutable object may reference mutable objects, that does not make them mutable too.

### Interfaces

Interfaces are largerly the same as in Java. Interfaces may not have any instance variables, but they can have implemented methods. Implemented methods do not need the "default" keyword like in Java.

```oscar
interface Ordering<T> {
   def lessThan(T other): Boolean;
   
   def equalTo(T other): Boolean;
   
   def greaterThan(T other): Boolean {
      return !equalTo(other) && !lessThan(other);
   }
}
```

### Delegation

Objects may delegate the implementation of a certain interface to another object. For example:

```oscar
object Score(val player: Player, val points: Integer) implements Ordering by points {
   ...
}
```

The `Ordering` interface is similar to the `Comparable` interface in Java, as it compares two objects of the same type. As `Integer implements an `Ordering` itself, all the ordering of the `Score` is delegated to the `points` variable. An `object` can implement and delegate multiple interfaces, just as in Java:

```oscar
object Score(val player: Player, val points: Integer) implements
      Ordering by points
      Identity by player {
   ...
}
```

An `Identity` defines the `identicalTo()` method that is similar to Java's "equals()". The delegation does not have to go to a constructor parameter, it can be a custom object created just for this purpose.

```oscar
object Player(val firstName: String, val lastName: String) implements
      Identity by AggregateIdentity(firstName, lastName)
```

In this case however there is no way to refer to the delegate itself from the body of the object. If that is needed, it has to be explicitly put into a variable:

```oscar
object Player(val firstName: String, val lastName: String) implements
      Identity by identityDelegate
   val identityDelegate: Identity = AggregateIdentity(firstName, lastName)
```

## Idioms

Objects may not inherit from other objects. This also means there is no "superclass", no `Object` class that is the parent for all objects. The preferred method to compose different capabilities is to *delegate*.


