package com.vanillasource.oscar.compiler.tokenizer;

import com.vanillasource.oscar.compiler.chars.PositionalInput;
import com.vanillasource.oscar.compiler.chars.SourceCodePosition;
import static java.lang.Character.*;

public final class OscarTokenizer implements PositionalInput<Token> {
   private final PositionalInput<Character> input;
   private final Token currentToken;

   private OscarTokenizer(PositionalInput<Character> input, Token currentToken) {
      this.input = input;
      this.currentToken = currentToken;
   }

   public static OscarTokenizer of(PositionalInput<Character> input) {
      if (input.isEof()) {
         return new OscarTokenizer(input, null);
      } else {
         return new OscarTokenizer(input, null).next();
      }
   }

   @Override
   public boolean isEof() {
      return currentToken==null && input.isEof();
   }

   @Override
   public Token get() {
      return currentToken;
   }

   @Override
   public OscarTokenizer next() {
      if (input.isEof()) {
         return new OscarTokenizer(input, null);
      } else {
         PositionalInput<Character> currentInput = input;
         // Skip all whitespace
         while (!currentInput.isEof() && isWhitespace(currentInput.get())) {
            currentInput = currentInput.next();
         }
         // Choose category and read it
         return TokenCategory.chooseCategory(currentInput.get()).read(currentInput);
      }
   }

   @Override
   public SourceCodePosition position() {
      return input.position();
   }

   private enum TokenCategory {
      IDENTIFIER {
         @Override
         public boolean startsWith(char c) {
            return isLetter(c) || c=='_';
         }

         @Override
         public OscarTokenizer read(PositionalInput<Character> initialInput) {
            PositionalInput<Character> currentInput = initialInput;
            StringBuilder builder = new StringBuilder();
            while (!currentInput.isEof() && isLegal(currentInput.get())) {
               builder.append(currentInput.get());
               currentInput = currentInput.next();
            }
            return new OscarTokenizer(currentInput, new Token(builder.toString()));
         }

         private boolean isLegal(char c) {
            return isLetter(c) || c=='_' || isDigit(c);
         }
      },
      SINGLE_SYMBOLS {
         @Override
         public boolean startsWith(char c) {
            return c=='{' || c=='}' || c=='=' || c=='(' || c==')';
         }

         @Override
         public OscarTokenizer read(PositionalInput<Character> input) {
            return new OscarTokenizer(input.next(), new Token(""+input.get()));
         }
      },
      NUMBER {
         @Override
         public boolean startsWith(char c) {
            return isDigit(c);
         }

         @Override
         public OscarTokenizer read(PositionalInput<Character> initialInput) {
            PositionalInput<Character> currentInput = initialInput;
            StringBuilder builder = new StringBuilder();
            while (!currentInput.isEof() && isDigit(currentInput.get())) {
               builder.append(currentInput.get());
               currentInput = currentInput.next();
            }
            return new OscarTokenizer(currentInput, new Token(builder.toString()));
         }
      },
      OTHER {
         @Override
         public boolean startsWith(char c) {
            return true;
         }

         @Override
         public OscarTokenizer read(PositionalInput<Character> input) {
            return new OscarTokenizer(input.next(), new Token(""+input.get()));
         }
      }
      ;

      public abstract boolean startsWith(char c);

      public abstract OscarTokenizer read(PositionalInput<Character> input);

      public static TokenCategory chooseCategory(char c) {
         for (TokenCategory potentialCategory: values()) {
            if (potentialCategory.startsWith(c)) {
               return potentialCategory;
            }
         }
         throw new IllegalStateException("can't reach this point");
      }
   };
}
