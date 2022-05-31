package com.vanillasource.oscar.compiler.chars;

public final class OscarInput extends DelegatingInput<Character> {
   private OscarInput(PositionalInput<Character> delegate) {
      super(delegate);
   }

   public static OscarInput of(String content) {
      return of("<stdin>", content);
   }

   public static OscarInput of(String source, String content) {
      return new OscarInput(
               new BlockCommentInput(
                  new InlineCommentInput(
                     new StringInput(source, content))));
   }
}
