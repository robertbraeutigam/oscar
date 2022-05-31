package com.vanillasource.oscar.compiler;

public final class StringInput implements PositionalInput<Character> {
   private final SourceCodePosition position;
   private final String content;
   private final int index;

   private StringInput(SourceCodePosition position, String content, int index) {
      this.position = position;
      this.content = content;
      this.index = index;
   }

   public StringInput(String source, String content) {
      this(new SourceCodePosition(source), content, 0);
   }

   @Override
   public boolean isEof() {
      return index >= content.length();
   }

   @Override
   public Character get() {
      return content.charAt(index);
   }

   @Override
   public PositionalInput<Character> next() {
      return new StringInput(position.advanceWith(content.charAt(index)), content, index+1);
   }

   @Override
   public SourceCodePosition position() {
      return position;
   }
}
