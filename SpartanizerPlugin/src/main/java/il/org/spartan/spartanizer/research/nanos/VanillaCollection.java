package il.org.spartan.spartanizer.research.nanos;

import static il.org.spartan.lisp.*;

import static il.org.spartan.spartanizer.ast.navigate.step.*;

import java.util.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.*;
import org.eclipse.text.edits.*;
import org.jetbrains.annotations.*;

import il.org.spartan.spartanizer.ast.safety.*;
import il.org.spartan.spartanizer.engine.*;
import il.org.spartan.spartanizer.research.nanos.common.*;

/** Nano matches fields
 * @author orimarco {@code marcovitch.ori@gmail.com}
 * @since 2017-03-07 */
public final class VanillaCollection extends NanoPatternTipper<FieldDeclaration> {
  private static final long serialVersionUID = 0x69F91FBBFC879D62L;
  private static final List<String> abstractTypes = Arrays.asList("List", "Set", "Map");
  private static final List<String> specificTypes = Arrays.asList("ArrayList", "HashSet", "TreeSet", "HashMap");

  @Override public boolean canTip(final FieldDeclaration $) {
    return abstractTypes.contains(type(az.parameterizedType(type($))) + "")
        && specificTypes.contains(type(az.parameterizedType(type(az.classInstanceCreation(initializer(onlyOne(fragments($))))))) + "");
  }

  @Override @NotNull public Tip pattern(@NotNull final FieldDeclaration ¢) {
    return new Tip(description(), ¢, getClass()) {
      @Override public void go(@NotNull final ASTRewrite r, final TextEditGroup g) {
        r.remove(¢, g);
      }
    };
  }
}
