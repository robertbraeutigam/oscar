package com.vanillasource.oscar.compiler.parser;

import org.testng.annotations.Test;
import static org.testng.Assert.*;
import com.vanillasource.oscar.compiler.chars.OscarInput;

@Test
public final class OscarMethodTests {
   public void testMethodWithoutWSParses() {
      OscarMethod method = OscarMethod.PARSER.parse(OscarInput.of("def m()=123")).getOrThrow();

      assertEquals(method, new OscarMethod("m", 123));
   }

   public void testWhitespacesParse() {
      OscarMethod method = OscarMethod.PARSER.parse(OscarInput.of("def m  (   )   =   \n\n   123")).getOrThrow();

      assertEquals(method, new OscarMethod("m", 123));
   }

   public void testInlineCommentsGetRemoved() {
      OscarMethod method = OscarMethod.PARSER.parse(OscarInput.of("def m  (   )   = // This is an inline comment  \n\n   123")).getOrThrow();

      assertEquals(method, new OscarMethod("m", 123));
   }

   public void testBlockCommentsGetRemoved() {
      OscarMethod method = OscarMethod.PARSER.parse(OscarInput.of("def m  (   )   = /* Block comment */   123")).getOrThrow();

      assertEquals(method, new OscarMethod("m", 123));
   }

   public void testBlockCommentsCanBeMultiline() {
      OscarMethod method = OscarMethod.PARSER.parse(OscarInput.of("def m  (   )   = /* Block \n comment \n */   123")).getOrThrow();

      assertEquals(method, new OscarMethod("m", 123));
   }

   @Test(expectedExceptions = Exception.class)
   public void testMethodWithoutNameFails() {
      OscarMethod.PARSER.parse(OscarInput.of("def ()=123")).getOrThrow();
   }
}
