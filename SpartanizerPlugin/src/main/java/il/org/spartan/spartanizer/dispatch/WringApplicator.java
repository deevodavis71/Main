package il.org.spartan.spartanizer.dispatch;

import java.util.*;

import org.eclipse.core.resources.*;
import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.*;

import il.org.spartan.plugin.*;
import il.org.spartan.spartanizer.engine.*;
import il.org.spartan.spartanizer.wringing.*;

/** An adapter that converts the protocol of a single @{link Tipper} instance
 * into that of {@link GUI$Applicator}. This class must eventually die.
 * @author Yossi Gil
 * @since 2015/07/25 */
public final class WringApplicator extends GUI$Applicator {
  final Tipper<ASTNode> tipper;
  final Class<? extends ASTNode> clazz;

  /** Instantiates this class
   * @param tipper The tipper we wish to convert
   * @param name The title of the refactoring */
  @SuppressWarnings("unchecked") public WringApplicator(final Tipper<? extends ASTNode> w) {
    super(w.name());
    tipper = (Tipper<ASTNode>) w;
    clazz = w.myActualOperandsClass();
    assert clazz != null : "Oops, cannot find kind of operands of " + w.name();
  }

  @Override protected void consolidateSuggestions(final ASTRewrite r, final CompilationUnit u, final IMarker m) {
    u.accept(new ASTVisitor() {
      @Override public void preVisit(final ASTNode ¢) {
        super.preVisit(¢);
        if (¢.getClass() == clazz || tipper.canSuggest(¢) || inRange(m, ¢))
          tipper.suggest(¢).go(r, null);
      }
    });
  }

  @Override protected ASTVisitor makeSuggestionsCollector(final List<Suggestion> $) {
    return new ASTVisitor() {
      @Override public void preVisit(final ASTNode ¢) {
        super.preVisit(¢);
        progressMonitor.worked(1);
        if (¢.getClass() == clazz)
          return;
        progressMonitor.worked(1);
        if (!tipper.canSuggest(¢))
          return;
        progressMonitor.worked(1);
        $.add(tipper.suggest(¢));
      }
    };
  }
}
