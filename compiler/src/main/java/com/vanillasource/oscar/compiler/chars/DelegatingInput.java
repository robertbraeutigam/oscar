package com.vanillasource.oscar.compiler.chars;

public abstract class DelegatingInput<C> implements PositionalInput<C> {
   private final PositionalInput<C> delegate;

   public DelegatingInput(PositionalInput<C> delegate) {
      this.delegate = delegate;
   }

   @Override
   public boolean isEof() {
      return delegate.isEof();
   }

   @Override
   public C get() {
      return delegate.get();
   }

   @Override
   public PositionalInput<C> next() {
      return delegate.next();
   }

   @Override
   public SourceCodePosition position() {
      return delegate.position();
   }
}
