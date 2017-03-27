package il.org.spartan.spartanizer.tippers;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.*;
import org.eclipse.text.edits.*;

import il.org.spartan.spartanizer.ast.factory.*;
import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.ast.safety.*;
import il.org.spartan.spartanizer.dispatch.*;
import il.org.spartan.spartanizer.engine.*;
import il.org.spartan.spartanizer.tipping.*;

/** A {@link Tipper} to eliminate degenerate if sideEffects such as {@code
 * if (x)
 *   ;
 * else
 *   ;
 * }
 * @author Yossi Gil {@code Yossi.Gil@GMail.COM}
 * @since 2015-08-26 */
public final class IfEmptyThenEmptyElse extends CarefulTipper<IfStatement>//
    implements TipperCategory.NOP {
  private static final long serialVersionUID = 0x5956B839ADCAD9C5L;

  @Override public String description(@SuppressWarnings("unused") final IfStatement __) {
    return "Remove 'if' statement with vacous 'then' and 'else' parts";
  }

  @Override public boolean prerequisite(final IfStatement ¢) {
    return iz.block(¢.getParent()) && iz.vacuousThen(¢) && iz.vacuousElse(¢);
  }

  @Override public Tip tip(final IfStatement s) {
    return new Tip(description(s), s, getClass()) {
      @Override public void go(final ASTRewrite r, final TextEditGroup g) {
        final Block b = az.block(s.getParent());
        final ListRewrite l = r.getListRewrite(b, Block.STATEMENTS_PROPERTY);
        for (final Statement x : wizard.decompose(s.getExpression()))
          l.insertBefore(copy.of(x), s, g);
        l.remove(s, g);
      }
    };
  }
}
