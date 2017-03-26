package il.org.spartan.bloater.bloaters;

import static il.org.spartan.Utils.*;

import static il.org.spartan.spartanizer.ast.navigate.step.*;

import java.util.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.*;
import org.eclipse.text.edits.*;
import org.jetbrains.annotations.*;

import il.org.spartan.spartanizer.ast.factory.*;
import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.ast.safety.*;
import il.org.spartan.spartanizer.dispatch.*;
import il.org.spartan.spartanizer.engine.*;
import il.org.spartan.spartanizer.java.namespace.*;
import il.org.spartan.spartanizer.tipping.*;
import il.org.spartan.zoomer.zoomin.expanders.*;

/** An expander to rename short or unnecessarily understandable variable names
 * in a method dec to more common or intuitive names (s.e i for an integer
 * variable and ret for a return variable) : Important - the $ will always
 * change to ret by convention for more naming conventions information -
 * {@link https://github.com/SpartanRefactoring/Spartanizer/wiki/Naming-Rules}
 * {@code int f(int ¢) { int $ = ¢; x($); return $; } } ==> {@code int f(int i)
 * { int res = i; x(res); return res; } }
 * @author Raviv Rachmiel {@code  raviv.rachmiel@gmail.com }
 * @since 2017-01-10 Issue #979, {@link Issue0979} */
// TODO: Raviv Rachmiel take care of single var declaration, tests
public class MethodDeclarationNameExpander extends EagerTipper<MethodDeclaration>//
    implements TipperCategory.Bloater {
  private static final long serialVersionUID = -3829131163900046060L;

  @Override  public String description( final MethodDeclaration ¢) {
    return ¢.getName() + "";
  }

  @Override @SuppressWarnings("unused") @Nullable public Tip tip( final MethodDeclaration d, final ExclusionManager __) {
    assert d != null;
    if (d.isConstructor() || iz.abstract¢(d))
      return null;
     final List<SimpleName> prev = new ArrayList<>(), after = new ArrayList<>();
    for ( final SingleVariableDeclaration parameter : parameters(d)) {
      final SimpleName $ = parameter.getName();
      assert $ != null;
      if (in($.getIdentifier(), "$")) {
        prev.add($);
        after.add(make.from(d).identifier("result"));
        continue;
      }
      final SimpleName ¢ = make.from(d).identifier(scope.newName(body(d), step.type(parameter)));
      prev.add($);
      after.add(¢);
    }
    return prev.isEmpty() ? null : new Tip("Rename paraemters", d, getClass()) {
      @Override public void go(final ASTRewrite r, final TextEditGroup g) {
        int counter = 0;
        for (final SimpleName ¢ : prev) {
          Tricks.rename(¢, after.get(counter), d, r, g);
          ++counter;
        }
      }
    };
  }
}
