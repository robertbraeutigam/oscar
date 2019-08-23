# The Oscar Programming Language

Oscar is a statically typed object-inspired modern generic programming language.

## Rationale

*Best practices and good design over features*.

The main difference to other contemporary languages is that **Oscar** is *opinionated*. While other languages
concentrate on packing new features into the language irrespective of paradigms, in **Oscar** the
*paradigm* is the main value and only features that serve that paradigm are included.

With that in mind, the goals can be summarized thusly:

 * Opinionated. Using best practices should not be left up to the developer, but should flow naturally from the language.
 * Maintainability. Only features that promote maintainability are included, features that are easy to
   abuse or misuse are omitted.
 * Familiarity to Java developers. No additional mathematical or theoretical training should be necessary to start.

# Differences to Java
 
Following is an incomplete but representative list of differences between Oscar and Java:
 
 * There is no `null`.
 * There are no static methods, no companion objects or similar built-in singletons.
 * There is no inheritance at all, only implementing interfaces is possible.
 * There are no mutable local variables or parameters, only instance variables can be mutated.
 * There are no blocking operations. It is not possible to sleep, wait, synchronize or otherwise block a thread in Oscar.
 * No primitive values, everything is an object.
 * Objects do not have any methods by default. No `equals()`, `hashCode()`, `toString()`.
 * No `instanceof`, `getClass()`, no reflection.
 * No serialization.
 * No cycles, looping structures.
 * All exceptions are checked, including those in lambda expressions.

## Syntax

### Objects

The most important building blocks of an Oscar software are Objects. Objects are analogous to Java Classes, except there
is no inheritance.

```oscar
object Money(amount: Integer, currency: Currency) {
   ...
}
```

By default all `object`s are immutable, meaning they can only contain immutable variables.
If that's not the case the object needs to be marked `mutable object...`. The `mutable` modifier is
also the only modifier possible for objects. There
are no visibility modifiers for objects, all objects are "public final" in the Java sense.

The parameters for objects are always immutable variables, essentially "private final" variables in the Java sense.

Objects can not be "abstract", since there is no inheritance.

All `object`s need a primary constructor that all other constructors need to refer to. If there are more to be done in
the construction phase, the object may define a code block for the default constructor with:

```oscar
object Money(amount: Integer, currency: Currency) {
   def Money() {
      ...here the object parameters are already set...
   }
   ...
}
```

There can be optional values in all parameter lists:

```oscar
object Money(amount: Integer = 0, currency: Currency) {
   ...
}
```

There can be secondary constructors with different signatures, note however, that because optional parameters are
possible, signatures that just omit optional parameters are not possible. It is however possible to define "named"
constructors, like this:

```oscar
object Money(amount: Integer = 0, currency: Currency) {
   def Money.zeroDollars() {
      return this(0, Currency.Dollar());
   }
   ...
}
```

### Methods

TODO

### Lambda Expressions and Method References

### Instance variables

All parameters declared in the primary constructor are automatically immutable instance "variables". Additional instance
variables can be declared in the body of the `object` with the keywords `let` or `var`.

Variables declared `let` are immutable, equivalent to "final" variables in Java. Note however that the referenced object
itself may be mutable. Variables declared `var` are mutable variables, conceptually equal to normal "non-final" instance
variables.

```oscar
object Money(amount: Integer = 0, currency: Currency) {
   let cents: Integer = amount * 100;

   ...
}
```

Note that objects are immutable by default. Only `mutable object`s may use `var` declarations. Note also that immutable
objects may still reference mutable objects, that does not make them mutable.

### Interfaces

The purpose of interfaces is to describe a contract between the implementation and user code. For this
they only need to contain the signatures of methods supported. Specifically, they can not contain
default arguments nor default method implementations (use delegation for that).

```oscar
interface Ordering<T> {
   def lessThan(T other): Boolean;
   
   def equalTo(T other): Boolean;
   
   def greaterThan(T other): Boolean;
}
```

Interfaces may `extend` any number of other interfaces and objects may implement any number of
interfaces, similarly to Java.

### Delegation

Objects may delegate the implementation of a certain interface to another object. For example:

```oscar
object CachingConnection(delegate: Connection) implements Connection by delegate {
   override def send(query: String): String {
      // Cache response
   }
}
```

The `CachingConnection` in this case delegates all `Connection` methods to the constructor
parameter. Note that the object delegated to needs to implement the interface that is delegated.

An object can of course implement and delegate multiple interfaces if need be. For example:

```oscar
object CachingConnection(connection: Connection, cache: Cache) 
      implements Connection by connection,
      implements Cache by cache {

   override def send(query: String): String {
      return cache.read(name, connection::send(query));
   }
}
```

In this case the `CachingConnection` is both a `Connection` and a `Cache`, and it delegates
both duties to the respective objects.

### Instantiating

There is no "new" keyword in Oscar, objects are not instantiated directly. Instead instances of objects can be *acquired*
through *supplier methods* (see next chapter). "Acquired" here means that the user code can not know whether the
object was really instantiated or supplied via other means.

Supplier methods are implicitly created whenever a type is referenced with a (potentially empty) parameter list.
For example:

```oscar
let player = Player("Jack", "Kirby");
```

`Player` might be either an interface or an object, and might or might not have a declared constructor with
the given parameter list. The code here doesn't instantiate an object, but rather *declares a dependency* to
acquiring an object with the given parameters!

An object containing this code will essentially implicitly define an additional constructor argument to supply
a method to produce a `Player` instance for the given parameters.

Note that interfaces can't have default arguments nor default method implementations, therefore can not
specify dependencies.

### Supplier Methods

As described above, objects do not instantiate other objects, but use *supplier methods* to acquire instances.
This is normally transparent to the code itself. For example:

```oscar
object Game(firstName: String, lastName: String) {
   let player = Player(firstName, lastName);
}
```

In this example an instance of a `Player` is acquired, but there is no additional definition necessary.
A supplier method with signature `Player(String, String)` will now be essentially an additional constructor
argument to `Game`.

If a constructor with that signature *actually* exists in `Player`, than it will be the default value
for the supplier method, if the code acquiring the `Game` does not override it.

Any supplier method can be overridden regardless whether they have default values, like this:

```oscar
let game = Game((firstName, lastName) -> Player("Jane", lastName), firstName, lastName);
```

The supplier methods precede the declared argument list, but are essentially parameters themselves. Here
the overridden supplier method always acquires a `Player` using the first name "Jane". Of course
the object containing this code also declares a dependency on `Player(String, String)`.

### Scratchpad

 * Factories
 * State machine (change object)?

## Idioms

