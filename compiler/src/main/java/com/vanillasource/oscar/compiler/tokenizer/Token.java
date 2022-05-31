package com.vanillasource.oscar.compiler.tokenizer;

import org.typemeta.funcj.parser.Parser;
import static org.typemeta.funcj.parser.Combinators.*;
import org.typemeta.funcj.parser.Combinators;
import java.util.function.Predicate;
import com.vanillasource.oscar.compiler.chars.SourceCodePosition;

public final class Token {
   private final SourceCodePosition startPosition;
   private final String content;

   public Token(SourceCodePosition startPosition, String content) {
      this.startPosition = startPosition;
      this.content = content;
   }

   public String printStartPosition() {
      return startPosition.toString();
   }

   public static Parser<Token, String> token(String content) {
      return matchingToken(content, str -> str.equals(content));
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
