# The Oscar Programming Language

Oscar is a statically typed object-orientation and functional-programming fusioning modern generic programming language.

## Rationale

*Best practices and good design over features*.

The main difference to other contemporary languages is that **Oscar** is *opinionated*. While other languages
concentrate on packing new features into the language irrespective of paradigms, in **Oscar** the
*paradigm* is the main value and only features that serve that paradigm are included.

With that in mind, the goals can be summarized thusly:

 * Maintainability. Only features that promote maintainability are included, features that are easy to
   abuse or misuse are omitted.
 * Familiarity to Java developers. No additional mathematical or theoretical training should be necessary to start.
 * Opinionated. Using best practices should not be left up to the developer, but should flow naturally from the language.

# Differences to Java
 
Following is an incomplete but representative list of differences between Oscar and Java:
 
 * There is no `null`.
 * There are no static methods, no companion objects or similar built-in singletons.
 * There is no inheritance at all, only implementing interfaces is possible.
 * Reduced mutability. There are no mutable local variables or parameters, only instance variables can be mutated, occasionally.
 * There are no blocking operations. It is not possible to sleep, wait, synchronize or otherwise block a (system) thread in Oscar.
 * No primitive values, everything is an object.
 * Objects do not have any methods by default. No `equals()`, `hashCode()`, `toString()`.
 * No `instanceof`, `getClass()`, no reflection.
 * No serialization.
 * No cycles, looping structures.

## Syntax

### Objects

The most important building blocks of an Oscar software are Objects. Objects are analogous to Java Classes, except there
is no inheritance.

```oscar
object Money(amount: Integer, currency: Currency) {
   ...
}
```

There are no visibility modifiers for objects, all objects are "public final" in the Java sense.

The parameters for objects are always immutable variables, essentially "private final" variables in the Java sense.
Mutability is realized through a different mechanism described in later chapters. For now, assume all objects
are immutable.

Objects can not be "abstract", since there is no inheritance.

It is not possible to define additional "statements" to be executed during the construction
of an object. The constructor should only set the initial state of the object.

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
   Money.zeroDollars() = Money(0, Currency.Dollar())
   ...
}
```

### Methods / Functions

Methods in Oscar are actually functions, since objects are immutable. Because they are functions,
they always have a return value and are implemented not by statements, but by an expression.

There are three kinds of methods that can be defined by an object. Normal public methods,
public methods that are the implementations of methods defined in some interface or interfaces,
and private methods.

Public methods are defined using the Java-style `public` keyword. For example:

```oscar
fun order(amount: Integer) = ...
```

Parameters may have default values, similar to constructor parameters:

```oscar
fun order(amount: Integer = 1) = ...
```

Methods that are supposed to be implementations of methods defined in an interface must use the
keyword 'implement', like this:

```oscar
implement fun order(amount: Integer) = ...
```

`Implement`ed methods are always public.

Methods may be "private" in which case they are only visible in the same class or inner classes.

```oscar
private fun order(amount: Integer) = ...
```

### Exception Handling

Methods may declare exceptions, similar to what Java offers.

```oscar
public order(amount: Integer = 1) throws OutOfStockException {
   if (stockAmount < amount) {
      throw OutOfStockException(...);
   }
   ...
}
```

Since there are no "unchecked" exceptions, all exceptions have to be always declared.

It is worth noting, that exceptions should be used sparingly and only to indicate
programming errors and exceptional states.

### Lambda Expressions and Method References

Lambda expressions are similar in syntax to Java, but are a bit different semantically. Lambdas are
written as:

```oscar
(a, b) -> a+b
```

With parameters left, then a `->` symbol, then the expression or code body on the right. However, lambdas are not anonymous
implementations of any interface with a single method. Lambdas are *always* an implementation of the
special `Function` interface.

The `Function` interface is special for three reasons. One, is that instead of this:

```oscar
public addTwoNumbers(operation: Function<Integer, Integer, Integer>): Integer {
   ...
}
```

It can be specified using a function type literal like this:

```oscar
public addTwoNumbers(operation: (Integer, Integer) -> Integer): Integer {
   ...
}
```

Second, it can be called without specifying a method. I.e.:

```oscar
public addTwoNumbers(operation: (Integer, Integer) -> Integer): Integer {
   return operation(5);
}
```

Third, that `Function`s always declare all exceptions thrown in their body at their call site. For example:

```oscar
public addTwoNumbers((a, b) -> throw IllegalArgumentException());
```

will result in a compile error, because `addTwoNumbers` does not declare `IllegalArgumentException` to be thrown,
but the invocation of `operation` does throw `IllegalArgumentException`.

The method can not hard-code the `IllegalArgumentException` because it is only thrown in that specific call, 
not all calls, furthermore any number of exceptions might be thrown in any call. To mitigate this the method
has to declare that it re-throws all potential exceptions of the given function. This is done this way:

```oscar
public addTwoNumbers(operation: (Integer, Integer) -> Integer): Integer
      throws allof operation {
   ...
}
```

This has to be done always when the method itself doesn't handle the exceptions.

TODO: this can not be stored in 'var's, because the the type needs to be statically known

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
   lessThan(T other): Boolean;
   
   equalTo(T other): Boolean;
   
   greaterThan(T other): Boolean;
}
```

Interfaces may `extend` any number of other interfaces and objects may implement any number of
interfaces, similarly to Java.

### Delegation

Objects may delegate the implementation of a certain interface to another object. For example:

```oscar
object CachingConnection(delegate: Connection) implements Connection by delegate {
   implement send(query: String): String {
      // Cache response
   }
}
```

The `CachingConnection` in this case delegates all `Connection` methods to the constructor
parameter, except `send()` which is explicitly implemented.
Note that the object delegated to needs to implement the interface that is delegated.

An object can of course implement and delegate multiple interfaces if need be. For example:

```oscar
object CachingConnection(connection: Connection, cache: Cache) 
      implements Connection by connection,
      implements Cache by cache {

   implement send(query: String): String {
      return cache.read(name, connection::send(query));
   }
}
```

In this case the `CachingConnection` is both a `Connection` and a `Cache`, and it delegates
both duties to the respective objects. The overridden functionality connects the two parts.

### Instantiating

There is no "new" keyword in Oscar, objects are not instantiated directly. Instead instances of objects can be *acquired*
through *supplier functions* (see next chapter). "Acquired" here means that the user code can not know whether the
object was really instantiated or supplied via other means.

Supplier functions are implicitly created whenever a type is referenced with a (potentially empty) parameter list.
For example:

```oscar
let player = Player("Jack", "Kirby");
```

`Player` must be an object, which might or might not have a declared constructor with
the given parameter list. The code here doesn't instantiate an object, but rather *declares a dependency* to
an object to be acquired with the given parameters!

An object containing this code will implicitly define an additional constructor argument to supply
a method to produce a `Player` instance for the given parameters.

### Supplier Functions

As described above, objects do not instantiate other objects, but use *supplier functions* to acquire instances.
This is normally transparent to the code itself. For example:

```oscar
object Game(firstName: String, lastName: String) {
   let player = Player(firstName, lastName);
}
```

In this example an instance of a `Player` is acquired, but there is no additional definition necessary.
A supplier function with signature `Player(String, String)` will now be an additional constructor
argument to `Game`. Note however, that the object `Player` needs to be accessible, i.e. imported.

If a constructor with that signature *actually* exists in `Player`, than it will be the default value
for the supplier function, if the code acquiring the `Game` does not override it.

Any supplier function can be overridden regardless whether they have default values, like this:

```oscar
let game = Game((firstName, lastName) -> Player("Jane", lastName), firstName, lastName);
```

The supplier functions precede the declared argument list, but are parameters themselves. Here
the overridden supplier function always acquires a `Player` using the first name "Jane". Of course
the object containing this code *also* declares a dependency on `Player(String, String)`.

### Generics



### Threading and Parallelism

In **Oscar** there is no direct way to influence threading and there is no abstraction available for threads,
fibers, continuations or similar constructs. A code sequence is always executed sequentially in time:

```oscar
statement1();
statement2();
statement3();
```

`statement2()` will always execute *after* `statement1()`, and `statement3()` will always execute
after `statement2()`. However, these statements will never *block* a physical (operating system) thread,
even if `statement1()` is `sleep()`.

**Oscar** will automatically manage these calls, take care of asynchronous calls, wait for data to be available
and schedule it to some physical thread. Therefore all code will look like it blocks, but it never will.

To indicate that a statement *shouldn't* wait for a given block of code the keyword `parallel` should be used:

```oscar
statement1();
parallel {
   statement2();
}
statement3();
```

In this case `statement3()` will not wait for `statement2()` to finish. The counterpart of `parallel` is
the `wait` keyword. Statements after the `wait` keyword will only execute if the condition given to wait
is satisfied:

```oscar
statement1();
wait condition2();
statement3();
```

Here `statement3()` will only be executed if the `condition2()` method returns true. The `wait` condition
will be evaluated after every call to the object where the `wait` is in, until it returns true.

## Idioms

