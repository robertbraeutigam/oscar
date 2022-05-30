package com.vanillasource.oscar.compiler;

import org.testng.annotations.Test;
import org.typemeta.funcj.parser.Input;
import static org.testng.Assert.*;

@Test
public final class OscarMethodTests {
   public void testMethodWithoutWSParses() {
      OscarMethod method = OscarMethod.PARSER.parse(Input.of("def m()=123")).getOrThrow();

      assertEquals(method, new OscarMethod("m", 123));
   }

   @Test(expectedExceptions = Exception.class)
   public void testMethodWithoutNameFails() {
      OscarMethod.PARSER.parse(Input.of("def ()=123")).getOrThrow();
   }
}
