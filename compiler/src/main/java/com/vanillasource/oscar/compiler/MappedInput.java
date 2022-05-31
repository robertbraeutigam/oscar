package com.vanillasource.oscar.compiler;

import java.util.function.Function;

public final class MappedInput<C, R> implements PositionalInput<R> {
   private final PositionalInput<C> delegate;
   private final Function<C, R> mapper;

   public MappedInput(PositionalInput<C> delegate, Function<C, R> mapper) {
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
      return new MappedInput<>(delegate.next(), mapper);
   }

   @Override
   public SourceCodePosition position() {
      return delegate.position();
   }
}
