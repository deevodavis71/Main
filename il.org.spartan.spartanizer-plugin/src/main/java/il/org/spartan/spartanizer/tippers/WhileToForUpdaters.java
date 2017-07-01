package il.org.spartan.spartanizer.tippers;

import static il.org.spartan.spartanizer.ast.navigate.step.*;

import org.eclipse.jdt.core.dom.*;

import il.org.spartan.spartanizer.ast.factory.*;
import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.ast.safety.*;
import il.org.spartan.spartanizer.tipping.*;
import il.org.spartan.spartanizer.tipping.categories.*;

/** Convert a while to a traditional for(;;)
 * @author Alex Kopzon
 * @since 2016-09-23 */
public class WhileToForUpdaters extends ReplaceCurrentNode<WhileStatement>//
    implements Category.Loops {
  private static final long serialVersionUID = -0x550143DCF3BD3B9L;

  private static ForStatement buildForWithoutLastStatement(final WhileStatement ¢) {
    final ForStatement ret = ¢.getAST().newForStatement();
    ret.setExpression(copy.of(¢.getExpression()));
    updaters(ret).add(copy.of(az.expressionStatement(hop.lastStatement(body(¢))).getExpression()));
    ret.setBody(minus.lastStatement(copy.of(body(¢))));
    return ret;
  }
  private static boolean hasFittingUpdater(final WhileStatement ¢) {
    return az.block(body(¢)) != null && iz.updating(hop.lastStatement(body(¢))) && statements(az.block(body(¢))).size() >= 2
        && !ForToForUpdaters.bodyDeclaresElementsOf(hop.lastStatement(body(¢)));
  }
  @Override public String description() {
    return "Convert a while to a traditional for(;;)";
  }
  @Override public String description(final WhileStatement ¢) {
    return "Convert the while about '(" + ¢.getExpression() + ")' to a traditional for(;;)";
  }
  @Override public boolean prerequisite(final WhileStatement ¢) {
    return ¢ != null //
        && !iz.containsContinueStatement(body(¢)) //
        && hasFittingUpdater(¢) //
        && cantTip.declarationInitializerStatementTerminatingScope(¢) //
        && cantTip.declarationRedundantInitializer(¢) //
        && cantTip.remvoeRedundantIf(¢);
  }
  @Override public ASTNode replacement(final WhileStatement ¢) {
    return ¢ == null || iz.containsContinueStatement(body(¢)) || !hasFittingUpdater(¢) || !cantTip.declarationInitializerStatementTerminatingScope(¢)
        || !cantTip.declarationRedundantInitializer(¢) || !cantTip.remvoeRedundantIf(¢) ? null : buildForWithoutLastStatement(¢);
  }
}
