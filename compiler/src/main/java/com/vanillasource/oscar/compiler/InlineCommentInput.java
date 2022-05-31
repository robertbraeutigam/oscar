package com.vanillasource.oscar.compiler;

public final class InlineCommentInput extends DelegatingInput<Character> {
   public InlineCommentInput(PositionalInput<Character> delegate) {
      super(delegate);
   }

   @Override
   public PositionalInput<Character> next() {
      PositionalInput<Character> next = super.next();
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
}
