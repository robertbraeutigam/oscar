package com.vanillasource.oscar.compiler.lexer;

import org.typemeta.funcj.data.Chr;

public final class OscarInput extends DelegatingInput<Chr> {
   private OscarInput(PositionalInput<Chr> delegate) {
      super(delegate);
   }

   public static OscarInput of(String content) {
      return of("<stdin>", content);
   }

   public static OscarInput of(String source, String content) {
      return new OscarInput(new MappedInput<>(Chr::valueOf,
               new BlockCommentInput(
                  new InlineCommentInput(
                     new StringInput(source, content)))));
   }
}
