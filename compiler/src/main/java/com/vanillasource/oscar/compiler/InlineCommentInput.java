package com.vanillasource.oscar.compiler;

public final class InlineCommentInput implements PositionalInput<Character> {
   private final PositionalInput<Character> delegate;

   public InlineCommentInput(PositionalInput<Character> delegate) {
      this.delegate = delegate;
   }

   @Override
   public boolean isEof() {
      return delegate.isEof();
   }

   @Override
   public Character get() {
      return delegate.get();
   }

   @Override
   public PositionalInput<Character> next() {
      PositionalInput<Character> next = delegate.next();
      if (!next.isEof() && next.get()=='/') {
         PositionalInput<Character> peek = next.next();
         if (!peek.isEof() && peek.get() == '/') {
            PositionalInput<Character> cont = peek.next();
            while (!cont.isEof() && cont.position().sameLineAs(peek.position())) {
               cont = cont.next();
            }
            return new InlineCommentInput(cont);
         } else {
            return new InlineCommentInput(next);
         }
      } else {
         return new InlineCommentInput(next);
      }
   }

   @Override
   public SourceCodePosition position() {
      return delegate.position();
   }
}
