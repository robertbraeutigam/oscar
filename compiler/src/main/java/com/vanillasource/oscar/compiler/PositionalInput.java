package com.vanillasource.oscar.compiler;

import org.typemeta.funcj.parser.Input;

public interface PositionalInput<C> extends Input<C> {
   @Override
   SourceCodePosition position();

   @Override
   PositionalInput<C> next();
}
