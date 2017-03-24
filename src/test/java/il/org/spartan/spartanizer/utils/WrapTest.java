package il.org.spartan.spartanizer.utils;

import static il.org.spartan.azzert.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jface.text.*;
import org.jetbrains.annotations.*;
import org.junit.*;

import il.org.spartan.*;
import il.org.spartan.spartanizer.engine.nominal.*;

/** TODO: Yossi Gil {@code Yossi.Gil@GMail.COM} please add a description
 * @author Yossi Gil {@code Yossi.Gil@GMail.COM}
 * @since Oct 7, 2016 */
@SuppressWarnings({ "static-method", "javadoc" })
public final class WrapTest {
  @Test public void dealWithBothKindsOfComment() {
    similar("if (b) {\n /* empty */; \n} { // no else \n throw new Exception();\n}", //
        "if (b) {;} { throw new Exception(); }");
  }

  @Test public void dealWithComment() {
    azzert.that(WrapIntoComilationUnit.find("if (b) {\n /* empty */} else {\n throw new Exception();\n}"), is(WrapIntoComilationUnit.Statement));
  }

  @Test public void essenceTest() {
    azzert.that("if(b){;}throw new Exception();", is(trivia.essence("if (b) {\n /* empty */; \n} // no else \n throw new Exception();\n")));
  }

  @Test public void expression() {
    azzert.that(WrapIntoComilationUnit.Expression.off(WrapIntoComilationUnit.Expression.on("a+b")), is("a+b"));
  }

  @Test public void findAddition() {
    azzert.that(WrapIntoComilationUnit.find("a+b"), is(WrapIntoComilationUnit.Expression));
  }

  @Test public void findDivision() {
    azzert.that(WrapIntoComilationUnit.find("a/b"), is(WrapIntoComilationUnit.Expression));
  }

  @Test public void findDivisionOfExpressions() {
    azzert.that(WrapIntoComilationUnit.find("(a+b)/++b"), is(WrapIntoComilationUnit.Expression));
  }

  @Test public void findEmptyBlock() {
    azzert.that(WrapIntoComilationUnit.find("{}"), is(WrapIntoComilationUnit.Statement));
  }

  @Test(expected = AssertionError.class) public void findError() {
    azzert.that(WrapIntoComilationUnit.find("}} f() { a();} b();}"), is(nullValue()));
  }

  @Test public void findExpression() {
    azzert.that(WrapIntoComilationUnit.find("i++"), is(WrapIntoComilationUnit.Expression));
  }

  @Test public void findMethod() {
    azzert.that(WrapIntoComilationUnit.find("f() { a(); b();}"), is(WrapIntoComilationUnit.Method));
  }

  @Test public void findStatement() {
    azzert.that(WrapIntoComilationUnit.find("for(;;);"), is(WrapIntoComilationUnit.Statement));
  }

  @Test public void findTwoStatements() {
    azzert.that(WrapIntoComilationUnit.find("a(); b();"), is(WrapIntoComilationUnit.Statement));
  }

  @Test public void intMethod() {
    azzert.that(WrapIntoComilationUnit.find("int f() { int s = 0; for (int i = 0; i <10; ++i) s += i; return s;}"),
        is(WrapIntoComilationUnit.Method));
  }

  @Test public void intoCompilationUnit() {
    @NotNull final WrapIntoComilationUnit w = WrapIntoComilationUnit.Expression;
    @NotNull final String codeFragment = "a + b * c";
    @NotNull final CompilationUnit u = w.intoCompilationUnit(codeFragment);
    assert u != null;
    azzert.that(w.off(u + ""), containsString(codeFragment));
  }

  @Test public void intoDocument() {
    @NotNull final WrapIntoComilationUnit w = WrapIntoComilationUnit.Expression;
    @NotNull final String codeFragment = "a + b * c";
    @NotNull final Document d = w.intoDocument(codeFragment);
    assert d != null;
    azzert.that(w.off(d.get()), containsString(codeFragment));
  }

  @Test public void method() {
    azzert.that(WrapIntoComilationUnit.Method.off(WrapIntoComilationUnit.Method.on("int f() { return a; }")), is("int f() { return a; }"));
  }

  @Test public void offDivision() {
    azzert.that("a/b", is(WrapIntoComilationUnit.Expression.off(WrapIntoComilationUnit.Expression.on("a/b"))));
  }

  @Test public void removeComments() {
    similar(trivia.removeComments("if (b) {\n /* empty */} else {\n throw new Exception();\n}"), "if (b) {} else { throw new Exception(); }");
  }

  private void similar(@NotNull final String s1, @NotNull final String s2) {
    azzert.that(trivia.essence(s2), is(trivia.essence(s1)));
  }

  @Test public void statement() {
    azzert.that(WrapIntoComilationUnit.Statement.off(WrapIntoComilationUnit.Statement.on("int a;")), is("int a;"));
  }
}
