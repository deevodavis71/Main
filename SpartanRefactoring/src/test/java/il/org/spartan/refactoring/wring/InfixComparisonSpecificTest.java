package il.org.spartan.refactoring.wring;

import static il.org.spartan.azzert.*;
import static il.org.spartan.refactoring.spartanizations.TESTUtils.*;
import static il.org.spartan.refactoring.utils.Funcs.*;
import static il.org.spartan.refactoring.utils.Into.*;
import static il.org.spartan.refactoring.utils.Restructure.*;
import il.org.spartan.*;
import il.org.spartan.refactoring.spartanizations.*;
import il.org.spartan.refactoring.suggestions.*;
import il.org.spartan.refactoring.utils.*;
import il.org.spartan.refactoring.wring.AbstractWringTest.Noneligible;
import il.org.spartan.refactoring.wring.AbstractWringTest.OutOfScope;

import java.util.*;

import org.eclipse.jdt.core.dom.*;
import org.junit.*;
import org.junit.runner.*;
import org.junit.runners.*;
import org.junit.runners.Parameterized.Parameters;

/**
 * Unit tests for {@link Wrings#ADDITION_SORTER}.
 *
 * @author Yossi Gil
 * @since 2014-07-13
 */
@SuppressWarnings({ "javadoc", "static-method" })//
@FixMethodOrder(MethodSorters.NAME_ASCENDING)//
public class InfixComparisonSpecificTest extends AbstractWringTest<InfixExpression> {
  static final InfixComparisonSpecific WRING = new InfixComparisonSpecific();

  /** Instantiates this class */
  public InfixComparisonSpecificTest() {
    super(WRING);
  }
  @Test public void comparisonWithSpecific0z0() {
    assertWithinScope("this != a");
  }
  @Test public void comparisonWithSpecific0z1() {
    assertLegible("this != a");
  }
  @Test public void comparisonWithSpecificNoChange() {
    assertSimilar("a != this", Wrap.Expression.off(apply(new Context(), Wrap.Expression.on("a != this"))));
    assertSimilar("a != null", Wrap.Expression.off(apply(new Context(), Wrap.Expression.on("a != null"))));
    assertSimilar("a == this", Wrap.Expression.off(apply(new Context(), Wrap.Expression.on("a == this"))));
    assertSimilar("a == null", Wrap.Expression.off(apply(new Context(), Wrap.Expression.on("a == null"))));
    assertSimilar("a <= this", Wrap.Expression.off(apply(new Context(), Wrap.Expression.on("a <= this"))));
    assertSimilar("a <= null", Wrap.Expression.off(apply(new Context(), Wrap.Expression.on("a <= null"))));
    assertSimilar("a >= this", Wrap.Expression.off(apply(new Context(), Wrap.Expression.on("a >= this"))));
    assertSimilar("a >= null", Wrap.Expression.off(apply(new Context(), Wrap.Expression.on("a >= null"))));
  }
  @Test public void comparisonWithSpecificNoChangeWithLongEpxressions() {
    assertSimilar("very(complicate,func,-ction,call) != this",
        Wrap.Expression.off(apply(new Context(), Wrap.Expression.on("very(complicate,func,-ction,call) != this"))));
    assertSimilar("very(complicate,func,-ction,call) != null",
        Wrap.Expression.off(apply(new Context(), Wrap.Expression.on("very(complicate,func,-ction,call) != null"))));
    assertSimilar("very(complicate,func,-ction,call) == this",
        Wrap.Expression.off(apply(new Context(), Wrap.Expression.on("very(complicate,func,-ction,call) == this"))));
    assertSimilar("very(complicate,func,-ction,call) == null",
        Wrap.Expression.off(apply(new Context(), Wrap.Expression.on("very(complicate,func,-ction,call) == null"))));
    assertSimilar("very(complicate,func,-ction,call) <= this",
        Wrap.Expression.off(apply(new Context(), Wrap.Expression.on("very(complicate,func,-ction,call) <= this"))));
    assertSimilar("very(complicate,func,-ction,call) <= null",
        Wrap.Expression.off(apply(new Context(), Wrap.Expression.on("very(complicate,func,-ction,call) <= null"))));
    assertSimilar("very(complicate,func,-ction,call) >= this",
        Wrap.Expression.off(apply(new Context(), Wrap.Expression.on("very(complicate,func,-ction,call) >= this"))));
    assertSimilar("very(complicate,func,-ction,call) >= null",
        Wrap.Expression.off(apply(new Context(), Wrap.Expression.on("very(complicate,func,-ction,call) >= null"))));
  }
  @Test public void comparisonWithSpecificWithinScope() {
    azzert.that(Is.constant(left(i("this != a"))), is(true));
    final ASTNode n = ast.EXPRESSION.from("a != this");
    that(n, notNullValue());
    assertWithinScope(Funcs.asExpression(n));
    correctScopeExpression(n);
  }
  @Test public void comparisonWithSpecificWithinScope1() {
    final InfixExpression e = i("this != a");
    azzert.that(Is.constant(left(e)), is(true));
    assert inner != null;
    azzert.that(inner.scopeIncludes(e), is(true));
    assertLegible(e.toString());
  }
  @Test public void comparisonWithSpecificWithinScope2() {
    assertWithinScope("this != a");
  }
  @Test public void scopeIncludesFalse1() {
    that(WRING.scopeIncludes(i("13455643294 * 22")), is(false));
  }
  @Test public void scopeIncludesFalse1expanded() {
    final InfixExpression e = i("13455643294 * 22");
    azzert.that(!e.hasExtendedOperands(), is(true));
    that(Is.comparison(e), is(false));
  }
  @Test public void scopeIncludesFalse2() {
    that(WRING.scopeIncludes(i("1 + 2 < 3 & 7 + 4 > 2 + 1 || 6 - 7 < 2 + 1")), is(false));
  }
  @Test public void scopeIncludesFalse3() {
    that(WRING.scopeIncludes(i("1 + 2 < 3 & 7 + 4 > 2 + 1")), is(false));
  }
  @Test public void scopeIncludesFalse4() {
    that(WRING.scopeIncludes(i(" 6 - 7 < 2 + 1   ")), is(false));
  }
  @Test public void scopeIncludesFalse6() {
    azzert.that(WRING.scopeIncludes(i("1 < 102333")), is(true));
  }
  @Test public void scopeIncludesFalse7() {
    that(WRING.scopeIncludes(i("1 + 2 < 3 & 7 + 4 > 2 + 1 || 6 - 7 < 2 + 1")), is(false));
  }
  @Test public void scopeIncludesFalse8() {
    that(WRING.scopeIncludes(i("1 + 2 < 3 & 7 + 4 > 2 + 1")), is(false));
  }
  @Test public void scopeIncludesFalse9() {
    that(WRING.scopeIncludes(i(" 6 - 7 < 2 + 1   ")), is(false));
  }
  @Test public void scopeIncludesTrue1() {
    azzert.that(WRING.scopeIncludes(i("a == this")), is(true));
  }
  @Test public void scopeIncludesTrue2() {
    azzert.that(WRING.scopeIncludes(i("this == null")), is(true));
  }
  @Test public void scopeIncludesTrue3() {
    azzert.that(WRING.scopeIncludes(i("12 == this")), is(true));
  }
  @Test public void scopeIncludesTrue4() {
    azzert.that(WRING.scopeIncludes(i("a == 11")), is(true));
  }
  @Test public void scopeIncludesTrue5() {
    azzert.that(WRING.scopeIncludes(i("13455643294 < 22")), is(true));
  }
  @Test public void scopeIncludesTrue7() {
    azzert.that(WRING.scopeIncludes(i("1 < 102333")), is(true));
  }
  @Test public void scopeIncludesTrue8() {
    azzert.that(WRING.scopeIncludes(i("13455643294 < 22")), is(true));
  }

  @RunWith(Parameterized.class)//
  public static class Noneligible extends AbstractWringTest.Noneligible.Infix {
    static String[][] cases = as.array(//
        // Literal
        new String[] { "LT/literal", "a<2" }, //
        new String[] { "LE/literal", "a<=2" }, //
        new String[] { "GT/literal", "a>2" }, //
        new String[] { "GE/literal", "a>=2" }, //
        new String[] { "EQ/literal", "a==2" }, //
        new String[] { "NE/literal", "a!=2" }, //
        // This
        new String[] { "LT/this", "a<this" }, //
        new String[] { "LE/this", "a<=this" }, //
        new String[] { "GT/this", "a>this" }, //
        new String[] { "GE/this", "a>=this" }, //
        new String[] { "EQ/this", "a==this" }, //
        new String[] { "NE/this", "a!=this" }, //
        // Null
        new String[] { "LT/null", "a<null" }, //
        new String[] { "LE/null", "a<=null" }, //
        new String[] { "GT/null", "a>null" }, //
        new String[] { "GE/null", "a>=null" }, //
        new String[] { "EQ/null", "a==null" }, //
        new String[] { "NE/null", "a!=null" }, //
        // Character literal
        new String[] { "LT/character literal", "a<'a'" }, //
        new String[] { "LE/character literal", "a<='a'" }, //
        new String[] { "GT/character literal", "a>'a'" }, //
        new String[] { "GE/character literal", "a>='a'" }, //
        new String[] { "EQ/character literal", "a=='a'" }, //
        new String[] { "NE/character literal", "a!='a'" }, //
        // Misc
        new String[] { "Correct order", "1 + 2 < 3 " }, //
        null);

    /**
     * Generate test cases for this parameterized class.
     *
     * @return a collection of cases, where each case is an array of three
     *         objects, the test case name, the input, and the file.
     */
    @Parameters(name = DESCRIPTION)//
    public static Collection<Object[]> cases() {
      return collect(cases);
    }
    /** Instantiates the enclosing class ({@link Noneligible}) */
    public Noneligible() {
      super(WRING);
    }
    @Override @Test public void flattenIsIdempotentt() {
      final InfixExpression flatten = flatten(asInfixExpression());
      that(flatten(flatten).toString(), is(flatten.toString()));
    }
    @Override @Test public void inputIsInfixExpression() {
      that(asInfixExpression(), notNullValue());
    }
    @Test public void twoOrMoreArguments() {
      that(extract.operands(asInfixExpression()).size(), greaterThanOrEqualTo(2));
    }
  }

  @RunWith(Parameterized.class)//
  public static class OutOfScope extends AbstractWringTest.OutOfScope.Exprezzion.Infix {
    static String[][] cases = as.array(//
        new String[] { "Expression vs. Expression", " 6 - 7 < 2 + 1   " }, //
        new String[] { "Literal vs. Literal", "1 < 102333" }, //
        null);

    /**
     * Generate test cases for this parameterized class.
     *
     * @return a collection of cases, where each case is an array of three
     *         objects, the test case name, the input, and the file.
     */
    @Parameters(name = DESCRIPTION)//
    public static Collection<Object[]> cases() {
      return collect(cases);
    }
    /** Instantiates the enclosing class ({@link OutOfScope}) */
    public OutOfScope() {
      super(WRING);
    }
  }

  @RunWith(Parameterized.class)//
  @FixMethodOrder(MethodSorters.NAME_ASCENDING)//
  public static class Wringed extends AbstractWringTest.WringedExpression.Infix {
    private static String[][] cases = as.array(//
        // Literal
        new String[] { "LT/literal", "2<a", "a>2" }, //
        new String[] { "LE/literal", "2<=a", "a>=2" }, //
        new String[] { "GT/literal", "2>a", "a<2" }, //
        new String[] { "GE/literal", "2>=a", "a<=2" }, //
        new String[] { "EQ/literal", "2==a", "a==2" }, //
        new String[] { "NE/literal", "2!=a", "a!=2" }, //
        // This
        new String[] { "LT/this", "this<a", "a>this" }, //
        new String[] { "LE/this", "this<=a", "a>=this" }, //
        new String[] { "GT/this", "this>a", "a<this" }, //
        new String[] { "GE/this", "this>=a", "a<=this" }, //
        new String[] { "EQ/this", "this==a", "a==this" }, //
        new String[] { "NE/this", "this!=a", "a!=this" }, //
        // Null
        new String[] { "LT/null", "null<a", "a>null" }, //
        new String[] { "LE/null", "null<=a", "a>=null" }, //
        new String[] { "GT/null", "null>a", "a<null" }, //
        new String[] { "GE/null", "null>=a", "a<=null" }, //
        new String[] { "EQ/null", "null==a", "a==null" }, //
        new String[] { "NE/null", "null!=a", "a!=null" }, //
        // Character literal
        new String[] { "LT/character literal", "'b'<a", "a>'b'" }, //
        new String[] { "LE/character literal", "'b'<=a", "a>='b'" }, //
        new String[] { "GT/character literal", "'b'>a", "a<'b'" }, //
        new String[] { "GE/character literal", "'b'>=a", "a<='b'" }, //
        new String[] { "EQ/character literal", "'b'==a", "a=='b'" }, //
        new String[] { "NE/character literal", "'b'!=a", "a!='b'" }, //
        // Misc
        new String[] { "Crazy comparison", "null == this", "this == null" }, //
        new String[] { "Crazy comparison", "null == 1", "1 == null" }, //
        new String[] { "Negative number", "-1 == a", "a == -1" }, //
        null);

    /**
     * Generate test cases for this parameterized class.
     *
     * @return a collection of cases, where each case is an array of three
     *         objects, the test case name, the input, and the file.
     */
    @Parameters(name = DESCRIPTION)//
    public static Collection<Object[]> cases() {
      return collect(cases);
    }
    /**
     * Instantiates the enclosing class ({@link WringedExpression})
     */
    public Wringed() {
      super(WRING);
    }
    @Override @Test public void flattenIsIdempotentt() {
      final InfixExpression flatten = flatten(asInfixExpression());
      that(flatten(flatten).toString(), is(flatten.toString()));
    }
    @Test public void flipIsNotNull() {
      that(flip(asInfixExpression()), notNullValue());
    }
    @Override @Test public void inputIsInfixExpression() {
      that(asInfixExpression(), notNullValue());
    }
    @Test public void sortTwiceADDITION() {
      final InfixExpression e = asInfixExpression();
      final List<Expression> operands = extract.operands(flatten(e));
      ExpressionComparator.ADDITION.sort(operands);
      that(ExpressionComparator.ADDITION.sort(operands), is(false));
    }
    @Test public void sortTwiceMULTIPLICATION() {
      final List<Expression> operands = extract.operands(flatten(asInfixExpression()));
      ExpressionComparator.MULTIPLICATION.sort(operands);
      that(ExpressionComparator.MULTIPLICATION.sort(operands), is(false));
    }
    @Test public void twoOrMoreArguments() {
      that(extract.operands(asInfixExpression()).size(), greaterThanOrEqualTo(2));
    }
  }
}
