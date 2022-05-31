package com.vanillasource.oscar.compiler;

public final class SourceCodePosition {
   private final String sourceName;
   private final int line;
   private final int col;
   private final boolean previousCharacterWasCarriageReturn;

   private SourceCodePosition(String sourceName, int line, int col, boolean previousCharacterWasCarriageReturn) {
      this.sourceName = sourceName;
      this.line = line;
      this.col = col;
      this.previousCharacterWasCarriageReturn = previousCharacterWasCarriageReturn;
   }

   public SourceCodePosition(String sourceName) {
      this(sourceName, 1, 1, false);
   }

   public boolean sameLineAs(SourceCodePosition other) {
      return line == other.line;
   }

   public SourceCodePosition advanceWith(char c) {
      if (c=='\n' && !previousCharacterWasCarriageReturn ||
          c=='\r' || c=='\u0085' || c=='\u2028' || c=='\u2029') {
         return new SourceCodePosition(sourceName, line+1, 1, c=='\r');
      } else {
         return new SourceCodePosition(sourceName, line, col+1, false);
      }
   }

   @Override
   public String toString() {
      return sourceName+":"+line+":"+col;
   }
}
