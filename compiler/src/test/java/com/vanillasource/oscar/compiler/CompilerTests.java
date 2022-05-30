package com.vanillasource.oscar.compiler;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import static org.mockito.Mockito.*;
import java.io.DataOutput;

@Test
public final class CompilerTests {
   private CompilerMessages messages;
   private Compiler compiler;
   private DataOutput output;

   public void testErrorMessageIncludesLineAndColumn() {
      compile("object O {\n   def ()=123\n}");

      verify(messages).addError(eq("<stdin>:2:8"), anyString(), anyString());
   }
   
   private void compile(String source) {
      compiler.compile(source, output, messages);
   }

   @BeforeMethod
   protected void setUp() {
      compiler = new Compiler();
      output = mock(DataOutput.class);
      messages = mock(CompilerMessages.class);
   }
}
