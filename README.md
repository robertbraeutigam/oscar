# The Oscar Programming Language

Oscar is a statically typed object-orientation and functional-programming fusioning modern generic programming language.

## Rationale

*Good design should be idiomatic, bad design should smell.*

## State and Process

*In-Development*

The development of the language follows a strongly incremental model. It starts
with a language that can only consists of a single integer, and eventually (if ever)
develops into a usable langauge.

# Differences to Java
 
Following is an incomplete but representative list of differences between Oscar and Java:
 
 * There is no `null`. At all.
 * There is no inheritance at all, only implementing interfaces is allowed.
 * Reduced mutability. There are no mutable local variables or parameters, only instance variables can be mutated, occasionally.
 * There are no blocking operations. It is not possible to sleep, wait, synchronize or otherwise block a (system) thread in Oscar.
 * There is no explicit threading / coroutines / etc. Everything defaults to asynchronous, including all method calls.
 * No primitive values, everything is an object.
 * Objects do not have any methods by default. No `equals()`, `hashCode()`, `toString()`.
 * No `instanceof`, `getClass()`, no reflection.
 * No serialization.
 * No cycles, looping structures.

## Syntax

The currently allowed syntax is one integer as text. When compiled and executed, this integer will be
returned as exit value.


