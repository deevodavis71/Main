package il.org.spartan.refactoring.annotations;

import java.util.*;
import java.util.Map.*;

import org.eclipse.jdt.core.dom.*;

import il.org.spartan.*;
import il.org.spartan.refactoring.ast.*;
import il.org.spartan.refactoring.engine.*;
import il.org.spartan.refactoring.java.Environment.*;

/* Implements the handler of flatEnv outer annotation. */
public class EnvFlatHandler extends ENVTestEngineAbstract {
  public EnvFlatHandler(final ASTNode $) {
    n = $;
    testSet = generateSet();
    azzert.nay(testSet == null);
    runTest();
  }

  public EnvFlatHandler(final String ¢) {
    n = getCompilationUnit(¢);
    testSet = generateSet();
    azzert.nay(testSet == null);
    runTest();
  }

  /* TODO Update EnvironmentCodeExamples - currently NestedENV does not
   * represent the expected results of neither Environment.uses or
   * Environment.declares. Should be the expected result of
   * Environment.declares.
   *
   * @see
   * il.org.spartan.refactoring.engine.ENVTestEngineAbstract#buildEnvironmentSet
   * (org.eclipse.jdt.core.dom.BodyDeclaration) */
  @Override protected LinkedHashSet<Entry<String, Information>> buildEnvironmentSet(final BodyDeclaration $) {
    return null;
  }

  @Override protected void handler(final Annotation ¢) {
    handler(az.singleMemberAnnotation(¢));
  }

  /** Parse the outer annotation to get the inner ones. Add to the flat Set.
   * Compare uses() and declares() output to the flat Set.
   * @param $ JD */
  void handler(final SingleMemberAnnotation a) {
    assert a != null && !"@OutOfOrderflatENV".equals(a.getTypeName() + "");
    if (!"OutOfOrderFlatENV".equals(a.getTypeName() + ""))
      return;
    foundTestedAnnotation = true;
    a.accept(new ASTVisitor() {
      @SuppressWarnings("unchecked") List<MemberValuePair> values(final NormalAnnotation ¢) {
        return ¢.values();
      }

      // runs on the Ids
      @Override public boolean visit(final NormalAnnotation ¢) {
        if (isNameId(¢.getTypeName()))
          addTestSet(values(¢));
        return true;
      }
    });
  }
}
