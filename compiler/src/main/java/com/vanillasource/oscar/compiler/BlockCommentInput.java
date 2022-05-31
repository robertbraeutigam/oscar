package com.vanillasource.oscar.compiler;

public final class BlockCommentInput implements PositionalInput<Character> {
   private final PositionalInput<Character> delegate;

   public BlockCommentInput(PositionalInput<Character> delegate) {
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
         if (!peek.isEof() && peek.get() == '*') {
            PositionalInput<Character> cont = peek.next();
            int found = 0;
            while (!cont.isEof() && found<2) {
               if ((found == 0) && cont.get()=='*' ||
                   (found == 1) && cont.get()=='/') {
                  found++;
               } else {
                  found = 0;
               }
               cont = cont.next();
            }
            return new BlockCommentInput(cont);
         } else {
            return new BlockCommentInput(next);
         }
      } else {
         return new BlockCommentInput(next);
      }
   }

   @Override
   public SourceCodePosition position() {
      return delegate.position();
   }
}
