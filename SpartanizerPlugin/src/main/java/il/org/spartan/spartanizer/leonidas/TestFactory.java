package il.org.spartan.spartanizer.leonidas;

import java.util.*;

import org.eclipse.jdt.core.dom.*;
import org.eclipse.jdt.core.dom.rewrite.*;
import org.eclipse.jface.text.*;
import org.eclipse.text.edits.*;

import il.org.spartan.*;
import il.org.spartan.spartanizer.ast.navigate.*;
import il.org.spartan.spartanizer.dispatch.*;

/** @author Ori Marcovitch
 * @since 2016 */
public class TestFactory {
  public static String testcase(String raw) {
    return shortenIdentifiers(eliminateSpaces(raw));
  }

  private static String eliminateSpaces(String s) {
    return s;
  }

  private static String shortenIdentifiers(String s) {
    Map<String, String> renaming = new HashMap<>();
    final Wrapper<String> id = new Wrapper<>();
    id.set("");
    final Document document = new Document(ASTutils.wrapCode(s));
    final ASTParser parser = ASTParser.newParser(AST.JLS8);
    parser.setSource(document.get().toCharArray());
    final CompilationUnit cu = (CompilationUnit) parser.createAST(null);
    final AST ast = cu.getAST();
    final ASTNode n = ASTutils.extractASTNode(s, cu);
    final ASTRewrite r = ASTRewrite.create(ast);
    n.accept(new ASTVisitor() {
      @Override public boolean preVisit2(final ASTNode ¢) {
        if (iz.simpleName(¢)) {
          String name = ((SimpleName) ¢).getFullyQualifiedName();
          if (!renaming.containsKey(name)) {
            id.set(renderIdentifier(id.get()));
            renaming.put(name, id.get());
          }
          Tippers.rename((SimpleName) ¢, ast.newSimpleName(renaming.get(name)), n, r, null);
        }
        return true;
      }
    });
    try {
      r.rewriteAST(document, null).apply(document);
    } catch (MalformedTreeException | IllegalArgumentException | BadLocationException e) {
      e.printStackTrace();
    }
    return ASTutils.extractCode(s, document);
  }

  /** Renders the Strings a,b,c, ..., z, X1, X2, ... */
  static String renderIdentifier(String old) {
    if (old.length() == 0)
      return "a";
    if (old.equals("z"))
      return "X1";
    if (old.length() == 1)
      return String.valueOf((char)(old.charAt(0) + 1));
    return "X" + String.valueOf(old.charAt(1) + 1);
  }

  private static String format(String s) {
    return s;
  }

  private static String toTest(String s) {
    return s;
  }
}
