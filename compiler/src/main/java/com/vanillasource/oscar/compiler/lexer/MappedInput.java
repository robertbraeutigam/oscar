package com.vanillasource.oscar.compiler.lexer;

import java.util.function.Function;

public final class MappedInput<C, R> implements PositionalInput<R> {
   private final PositionalInput<C> delegate;
   private final Function<C, R> mapper;

   public MappedInput(Function<C, R> mapper, PositionalInput<C> delegate) {
      this.delegate = delegate;
      this.mapper = mapper;
   }

   @Override
   public boolean isEof() {
      return delegate.isEof();
   }

   @Override
   public R get() {
      return mapper.apply(delegate.get());
   }

   @Override
   public PositionalInput<R> next() {
      return new MappedInput<>(mapper, delegate.next());
   }

   @Override
   public SourceCodePosition position() {
      return delegate.position();
   }
}
