package com.vanillasource.oscar.compiler.tokenizer;

import org.typemeta.funcj.parser.Parser;
import static org.typemeta.funcj.parser.Combinators.*;
import org.typemeta.funcj.parser.Combinators;
import java.util.function.Predicate;

public final class Token {
   private final String content;

   public Token(String content) {
      this.content = content;
   }

   @Override
   public String toString() {
      return "'"+content+"'";
   }

   @Override
   public boolean equals(Object o) {
      if ((o==null)||(!(o instanceof Token))) {
         return false;
      }
      return ((Token)o).content.equals(content);
   }

   public static Parser<Token, String> token(String content) {
      return value(new Token(content))
         .map(token -> token.content);
   }

   public static Parser<Token, String> anyToken() {
      return Combinators.<Token>any()
         .map(token -> token.content);
   }

   public static Parser<Token, String> matchingToken(String name, Predicate<String> condition) {
      return Combinators.<Token>satisfy(name, token -> condition.test(token.content))
         .map(token -> token.content);
   }
}
