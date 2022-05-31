package com.vanillasource.oscar.compiler;

public final class BlockCommentInput extends DelegatingInput<Character> {
   public BlockCommentInput(PositionalInput<Character> delegate) {
      super(delegate);
   }

   @Override
   public PositionalInput<Character> next() {
      PositionalInput<Character> next = super.next();
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
}
