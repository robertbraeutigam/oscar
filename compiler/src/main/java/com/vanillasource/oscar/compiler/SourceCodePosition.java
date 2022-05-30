package com.vanillasource.oscar.compiler;

public final class SourceCodePosition {
   private final String sourceName;
   private boolean previousCharacterWasCarriageReturn = false;
   private int line = 1;
   private int col = 1;

   public SourceCodePosition(String sourceName) {
      this.sourceName = sourceName;
   }

   public void advanceWith(char c) {
      if (c=='\n' && !previousCharacterWasCarriageReturn ||
          c=='\r' || c=='\u0085' || c=='\u2028' || c=='\u2029') {
         line++;
         col = 1;
         previousCharacterWasCarriageReturn = c=='\r';
      } else {
         col++;
      }
   }

   @Override
   public String toString() {
      return sourceName+":"+line+":"+col;
   }
}
