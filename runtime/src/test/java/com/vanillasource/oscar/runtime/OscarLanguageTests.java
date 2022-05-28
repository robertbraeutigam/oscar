package com.vanillasource.oscar.runtime;

import org.testng.annotations.Test;
import org.graalvm.polyglot.Context;

@Test
public final class OscarLanguageTests {
   public void testLanguageRegistered() {
      Context.create().initialize("osc");
   }
}
