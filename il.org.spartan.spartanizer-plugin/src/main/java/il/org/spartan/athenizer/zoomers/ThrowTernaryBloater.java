package il.org.spartan.athenizer.zoomers;

import static il.org.spartan.spartanizer.ast.navigate.step.*;

import org.eclipse.jdt.core.dom.*;

import il.org.spartan.spartanizer.ast.factory.*;
import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.ast.safety.*;
import il.org.spartan.spartanizer.tipping.*;
import il.org.spartan.spartanizer.tipping.categories.*;

/** Same as ReturnTernaryExpander just for "throw" @link {Issue0998}
 * @author Doron Meshulam {@code doronmmm@hotmail.com}
 * @since 2016-12-26 */
public class ThrowTernaryBloater extends ReplaceCurrentNode<ThrowStatement>//
    implements Category.Bloater {
  private static final long serialVersionUID = 0x6F38EBD99BF2A49EL;

  private static ASTNode innerThrowReplacement(final Expression x, final Statement s) {
    final ConditionalExpression ¢ = az.conditionalExpression(extract.core(x));
    if (¢ == null)
      return null;
    final IfStatement ret = s.getAST().newIfStatement();
    ret.setExpression(copy.of(expression(¢)));
    final ThrowStatement then = ¢.getAST().newThrowStatement();
    then.setExpression(copy.of(¢.getThenExpression()));
    ret.setThenStatement(copy.of(az.statement(then)));
    final ThrowStatement elze = ¢.getAST().newThrowStatement();
    elze.setExpression(copy.of(¢.getElseExpression()));
    ret.setElseStatement(copy.of(az.statement(elze)));
    return ret;
  }
  private static ASTNode replaceReturn(final Statement ¢) {
    final ThrowStatement ret = az.throwStatement(¢);
    return ret == null || !(expression(ret) instanceof ConditionalExpression) && !(expression(ret) instanceof ParenthesizedExpression) ? null
        : innerThrowReplacement(expression(ret), ¢);
  }
  @Override public ASTNode replacement(final ThrowStatement ¢) {
    return replaceReturn(¢);
  }
  @Override public String description(@SuppressWarnings("unused") final ThrowStatement __) {
    return "expanding a ternary operator to a full if-else statement";
  }
}
