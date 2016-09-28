package il.org.spartan.spartanizer.tippers;

import static il.org.spartan.spartanizer.tippers.TrimmerTestsUtils.*;

import org.junit.*;
import org.junit.runners.*;

/** @author Dor Ma'ayan & Alex Kopzon
 * @since 2016-09-23 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING) @SuppressWarnings({ "static-method", "javadoc" }) public class Issue144 {
  @Test public void t01() {
    trimmingOf("public boolean check(final ASTNode n) {" + "ASTNode p = n;" + "while (p != null) {" + "if (dns.contains(p))" + "return true;"
        + "p = p.getParent();" + "}" + "return false;" + "}")
            .gives("public boolean check(final ASTNode n) {" + "ASTNode p = n; for (; p != null; p = p.getParent()) {" + "if (dns.contains(p))"
                + "return true;" + "}" + "return false;" + "}");
  }

  @Test public void t01a() {
    trimmingOf("public boolean check(final ASTNode n) {" + "ASTNode p = n;" + "while (p != null) {" + "if (dns.contains(p))" + "return true;"
        + "if (ens.contains(p))" + "return true;" + "p = p.getParent();" + "}" + "return false;" + "}")
            .gives("public boolean check(final ASTNode n) {" + "ASTNode p = n;for (;p != null;p = p.getParent()) {" + "if (dns.contains(p))"
                + "return true;" + "if (ens.contains(p))" + "return true;" + "}" + "return false;" + "}");
  }

  @Test public void t01b() {
    trimmingOf("public boolean check(final ASTNode n) {" + "ASTNode p = n;" + "while (p != null) {" + "if (dns.contains(p))" + "return true;"
        + "f();" + "}" + "return false;" + "}")
            .gives("public boolean check(final ASTNode n) {" + "ASTNode p = n; for (; p != null; f()) {" + "if (dns.contains(p))"
                + "return true;" + "}" + "return false;" + "}");
  }
  
  @Test public void t01c() {
    trimmingOf("public boolean check(final ASTNode n) {" + "ASTNode p = n;" + "while (p != null) {" + "if (dns.contains(p))" + "return true;"
        + "++i;" + "}" + "return false;" + "}")
            .gives("public boolean check(final ASTNode n) {" + "ASTNode p = n; for (; p != null; ++i) {" + "if (dns.contains(p))"
                + "return true;" + "}" + "return false;" + "}");
  }

  @Test public void t02() {
    trimmingOf("public boolean check(final ASTNode n) {" + "ASTNode p = n;" + "for (;p != null;) {" + "if (dns.contains(p))" + "return true;"
        + "p = p.getParent();" + "}" + "return false;" + "}")
            .gives("public boolean check(final ASTNode n) {" + "ASTNode p = n; for (; p != null; p = p.getParent()) {" + "if (dns.contains(p))"
                + "return true;" + "}" + "return false;" + "}");
  }

  @Test public void t02a() {
    trimmingOf("public boolean check(final ASTNode n) {" + "ASTNode p = n;" + "for (;p != null;) {" + "if (dns.contains(p))" + "return true;"
        + "if (ens.contains(p))" + "return true;" + "p = p.getParent();" + "}" + "return false;" + "}")
            .gives("public boolean check(final ASTNode n) {" + "ASTNode p = n;for (;p != null;p = p.getParent()) {" + "if (dns.contains(p))"
                + "return true;" + "if (ens.contains(p))" + "return true;" + "}" + "return false;" + "}");
  }
  
  @Test public void t02b() {
    trimmingOf("public boolean check(final ASTNode n) {" + "ASTNode p = n;" + "for(;p != null;) {" + "if (dns.contains(p))" + "return true;"
        + "f();" + "}" + "return false;" + "}")
            .gives("public boolean check(final ASTNode n) {" + "ASTNode p = n; for (; p != null; f()) {" + "if (dns.contains(p))"
                + "return true;" + "}" + "return false;" + "}");
  }
  
  @Test public void t02c() {
    trimmingOf("public boolean check(final ASTNode n) {" + "ASTNode p = n;" + "for(;p != null;) {" + "if (dns.contains(p))" + "return true;"
        + "++i;" + "}" + "return false;" + "}")
            .gives("public boolean check(final ASTNode n) {" + "ASTNode p = n; for (; p != null; ++i) {" + "if (dns.contains(p))"
                + "return true;" + "}" + "return false;" + "}");
  }


  @Ignore @Test public void t03a() {
    trimmingOf("private static String toPath(String groupId) {" + "final StringBuilder sb = new StringBuilder(groupId);" + "int length = sb.length();"
        + "for (int i = 0; i < length; ++i)" + "if (sb.charAt(i) == '.')" + "sb.setCharAt(i, '/');" + "return sb + \"\";")
            .gives("private static String toPath(String groupId) {" + "final StringBuilder sb = new StringBuilder(groupId);"
                + "for (int i = 0, length = sb.length(); i < length; ++i)" + "if (sb.charAt(i) == '.')" + "sb.setCharAt(i, '/');"
                + "return sb + \"\";")
            .stays();
  }
  
  @Ignore @Test public void t03b() {
    trimmingOf("private static String toPath(String groupId) {" + "int $ = 0, one = 1;"
        + "for (; $ < one;){" + "if ($ == 0)" + "$ = 7; ++$;}" + "return $;}")
            .gives("private static String toPath(String groupId) {"
                + "for (int $ = 0, one = 1; $ < one; ++$)" + "if ($ == 0)" + "$ = 7;" + "return $;}")
            .stays();
  }
  
  @Ignore @Test public void t03c() {
    trimmingOf("private static String toPath(String groupId) {" + "int $ = 0, one = 1;"
        + "while ($ < one){" + "if ($ == 0)" + "$ = 7; ++$;}" + "return $;}")
            .gives("private static String toPath(String groupId) {"
                + "for (int $ = 0, one = 1; $ < one; ++$)" + "if ($ == 0)" + "$ = 7;" + "return $;}")
            .stays();
  }

  @Ignore @Test public void t04() {
    trimmingOf("public boolean check(final ASTNode n) {" + "ASTNode p = n;" + "while (p != null) {" + "if (dns.contains(p))" + "continue;"
        + "p = p.getParent();" + "}" + "return false;" + "}")
            .gives("public boolean check(final ASTNode n) {" + "for (ASTNode p = n;p != null;p = p.getParent()) {" + "if (dns.contains(p))"
                + "continue;" + "}" + "return false;" + "}")
            .gives("public boolean check(final ASTNode n) {" + "for (ASTNode p = n;p != null;p = p.getParent())" + "if (dns.contains(p))"
                + "continue;" + "return false;" + "}")
            .stays();
  }

  @Ignore @Test public void t05() {
    trimmingOf("static Statement recursiveElze(final IfStatement ¢) {" + "Statement $ = ¢.getElseStatement();" + "while ($ instanceof IfStatement)"
        + "$ = ((IfStatement) $).getElseStatement();" + "return $;" + "}")
            .gives("static Statement recursiveElze(final IfStatement ¢) {"
                + "for (Statement $ = ¢.getElseStatement();$ instanceof IfStatement;$ = ((IfStatement) $).getElseStatement());" + "return $;" + "}")
            .stays();
  }

  @Ignore @Test public void t06() {
    trimmingOf("public boolean check(final ASTNode n){ASTNode p=n;while(p!=null)p=p.getParent();return false;}")
        .gives("public boolean check(final ASTNode n) {for(ASTNode p = n; p != null;p = p.getParent());return false;}").stays();
  }

  @Ignore @Test public void t06a() {
    trimmingOf("public boolean check(final ASTNode n){ASTNode p=n;while(p!=null)f();return false;}")
        .gives("public boolean check(final ASTNode n) {for(ASTNode p = n; p != null;f());return false;}").stays();
  }

  @Ignore @Test public void t06b() {
    trimmingOf("public boolean check(final ASTNode n){ASTNode p=n;while(p!=null)if(dns.contains(p))return true;return false;}")
        .gives("public boolean check(final ASTNode n) {for(ASTNode p = n; p != null;)if(dns.contains(p))return true;return false;}").stays();
  }

  @Ignore @Test public void t07() {
    trimmingOf(
        "public boolean check(final ASTNode n){ASTNode p=n;while(p!=null){if(dns.contains(p))return true;if(ens.contains(p))break;p=p.getParent();}return false;}")
            .gives("public boolean check(final ASTNode n) {for  ( ASTNode p = n; p != null;p = p.getParent()) {"
                + "if (dns.contains(p)) return true; if (ens.contains(p)) break;}return false;}")
            .stays();
  }

  @Ignore @Test public void t08() {
    trimmingOf("static Statement recursiveElze(final IfStatement ¢) { Statement $ = ¢.getElseStatement();"
        + "while ($ instanceof IfStatement) $ = ((IfStatement) $).getElseStatement(); return $;}")
            .gives("static Statement recursiveElze(final IfStatement ¢) { for(Statement $ = ¢.getElseStatement();"
                + "$ instanceof IfStatement; $ = ((IfStatement) $).getElseStatement()); return $;}")
            .stays();
  }
}
