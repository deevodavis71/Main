package il.org.spartan.spartanizer.tippers;

import static il.org.spartan.idiomatic.*;
import static org.eclipse.jdt.core.dom.InfixExpression.Operator.*;

import org.eclipse.jdt.core.dom.*;

import static il.org.spartan.spartanizer.ast.navigate.step.*;

import il.org.spartan.spartanizer.ast.factory.*;
import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.dispatch.*;
import il.org.spartan.spartanizer.engine.*;
import il.org.spartan.spartanizer.tipping.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/** Replace {@code (long)X} by {@code 1L*X}
 * @author Alex Kopzon
 * @author Dan Greenstein
 * @since 2016 */
public final class CastToLong2Multiply1L extends ReplaceCurrentNode<CastExpression>//
    implements TipperCategory.NOP {
  @NotNull private static NumberLiteral literal(@NotNull final Expression ¢) {
    final NumberLiteral $ = ¢.getAST().newNumberLiteral();
    $.setToken("1L");
    return $;
  }

  @NotNull private static InfixExpression replacement(@NotNull final Expression $) {
    return subject.pair(literal($), $).to(TIMES);
  }

  @Override @Nullable public String description(final CastExpression ¢) {
    return "Use 1L*" + expression(¢) + " instead of (long)" + expression(¢);
  }

  @Override public ASTNode replacement(final CastExpression ¢) {
    return eval(() -> replacement(step.expression(¢)))
        .when(step.type(¢).isPrimitiveType() && "long".equals(step.type(¢) + "") && type.of(step.expression(¢)).isIntegral());
  }
}
