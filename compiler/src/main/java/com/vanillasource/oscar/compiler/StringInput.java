package com.vanillasource.oscar.compiler;

import org.typemeta.funcj.data.Chr;
import org.typemeta.funcj.parser.Input;

public final class StringInput implements Input<Chr> {
   private final SourceCodePosition position;
   private final String content;
   private int index = 0;

   public StringInput(String source, String content) {
      this.position = new SourceCodePosition(source);
      this.content = content;
   }

   @Override
   public boolean isEof() {
      return index >= content.length();
   }

   @Override
   public Chr get() {
      return Chr.valueOf(content.charAt(index));
   }

   @Override
   public Input<Chr> next() {
      position.advanceWith(content.charAt(index));
      index++;
      return this;
   }

   @Override
   public SourceCodePosition position() {
      return position;
   }
}
