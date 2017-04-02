package il.org.spartan.spartanizer.tippers;

import static org.eclipse.jdt.core.dom.ASTNode.*;
import static org.eclipse.jdt.core.dom.Assignment.Operator.*;

import static il.org.spartan.spartanizer.ast.navigate.step.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.*;
import org.eclipse.text.edits.*;

import il.org.spartan.spartanizer.ast.factory.*;
import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.ast.safety.*;
import il.org.spartan.spartanizer.dispatch.*;
import il.org.spartan.spartanizer.java.*;
import il.org.spartan.spartanizer.tipping.*;
import il.org.spartan.utils.*;

/** See {@link #examples()} for documentation
 * @author Yossi Gil {@code Yossi.Gil@GMail.COM}
 * @since 2015-08-28 */
public final class AssignmentAndAssignmentOfSameValue extends GoToNextStatement<Assignment>//
    implements TipperCategory.CommnonFactoring {
  private static final long serialVersionUID = 0x69CDEE55CA481121L;

  @Override public Examples examples() {
    return convert("a=3;b=3;").to("b=a=3;") //
        .convert("a=c;b=c;").to("b=a=c;") //
    ;
  }

  private static Expression extractRight(final Assignment ¢) {
    final Expression $ = from(¢);
    return !iz.assignment($) || operator(az.assignment($)) != ASSIGN ? $ : extractRight(az.assignment($));
  }

  private static Expression getRight(final Assignment ¢) {
    return operator(¢) != ASSIGN ? null : extractRight(¢);
  }

  @Override public String description(final Assignment ¢) {
    return "Consolidate assignment to " + to(¢) + " with subsequent similar assignment";
  }

  @Override protected ASTRewrite go(final ASTRewrite $, final Assignment a, final Statement nextStatement, final TextEditGroup g) {
    final ASTNode parent = parent(a);
    if (!iz.statement(parent))
      return null;
    final Expression right = getRight(a);
    if (right == null || nodeType(right) == NULL_LITERAL)
      return null;
    final Assignment a1 = extract.assignment(nextStatement);
    if (a1 == null)
      return null;
    final Expression right1 = getRight(a1);
    if (right1 == null || !wizard.eq(right, right1) || !sideEffects.deterministic(right))
      return null;
    $.remove(parent, g);
    $.replace(right1, copy.of(a), g);
    return $;
  }
}