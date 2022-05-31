package com.vanillasource.oscar.compiler;

import org.typemeta.funcj.data.Chr;

public final class OscarInput implements PositionalInput<Chr> {
   private final PositionalInput<Chr> delegate;

   private OscarInput(PositionalInput<Chr> delegate) {
      this.delegate = delegate;
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

   @Override
   public boolean isEof() {
      return delegate.isEof();
   }

   @Override
   public Chr get() {
      return delegate.get();
   }

   @Override
   public PositionalInput<Chr> next() {
      return delegate.next();
   }

   @Override
   public SourceCodePosition position() {
      return delegate.position();
   }
}
