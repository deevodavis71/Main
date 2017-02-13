package il.org.spartan.spartanizer.research.nanos.common;

import java.util.*;

import org.eclipse.jdt.core.dom.*;

import il.org.spartan.spartanizer.engine.*;
import il.org.spartan.spartanizer.research.*;

/** List that can contain nano patterns
 * @author orimarco <tt>marcovitch.ori@gmail.com</tt>
 * @since 2017-02-12 */
public class NanoPatternContainer<N extends ASTNode> extends ArrayList<UserDefinedTipper<N>> {
  private static final long serialVersionUID = 1L;

  public NanoPatternContainer<N> patternTipper(final String pattern, final String replacement, final String description) {
    add(TipperFactory.patternTipper(pattern, replacement, description));
    return this;
  }

  public boolean anyTips(final N ¢) {
    return ¢ != null && stream().anyMatch(λ -> λ.canTip(¢));
  }

  public Tip firstTip(final N ¢) {
    return firstTipper(¢).tip(¢);
  }

  public UserDefinedTipper<N> firstTipper(final N ¢) {
    return stream().filter(λ -> λ.canTip(¢)).findFirst().get();
  }
}