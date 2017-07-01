package il.org.spartan.spartanizer.tippers;

import java.util.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.InfixExpression.*;

import fluent.ly.*;
import il.org.spartan.spartanizer.ast.safety.*;
import il.org.spartan.spartanizer.engine.*;
import il.org.spartan.spartanizer.engine.type.Primitive.*;

/** Evaluate the subtraction of numbers according to the following rules {@code
 * int - int --> int
 * double - double --> double
 * long - long --> long
 * int - double --> double
 * int - long --> long
 * long - double --> double
 * }
 * @author Dor Ma'ayan
 * @since 2016 */
public final class InfixSubtractionEvaluate extends $EvaluateInfixExpression {
  private static final long serialVersionUID = 0x5D0DBA6EE527B4BL;

  @Override double evaluateDouble(final List<Expression> xs) {
    double ret = 0;
    try {
      ret = az.throwing.double¢(the.firstOf(xs)) - az.stream(the.tailOf(xs)).mapToDouble(az.throwing::double¢).sum();
    } catch (final NumberFormatException ¢) {
      note.bug(this, ¢);
    }
    return ret;
  }
  @Override int evaluateInt(final List<Expression> xs) {
    int ret = 0;
    try {
      ret = az.throwing.int¢(the.firstOf(xs));
      for (final Expression ¢ : the.tailOf(xs)) {
        if (type.of(¢) == Certain.DOUBLE || type.of(¢) == Certain.LONG)
          throw new NumberFormatException();
        ret -= az.throwing.int¢(¢);
      }
    } catch (final NumberFormatException ¢) {
      note.bug(this, ¢);
    }
    return ret;
  }
  @Override long evaluateLong(final List<Expression> xs) {
    long ret = 0;
    try {
      ret = az.throwing.long¢(the.firstOf(xs));
      for (final Expression ¢ : the.tailOf(xs)) {
        if (type.of(¢) == Certain.DOUBLE)
          throw new NumberFormatException();
        ret -= az.throwing.long¢(¢);
      }
    } catch (final NumberFormatException ¢) {
      note.bug(this, ¢);
    }
    return ret;
  }
  @Override String operation() {
    return "subtraction";
  }
  @Override Operator operator() {
    return il.org.spartan.spartanizer.ast.navigate.op.MINUS2;
  }
}
