package il.org.spartan.spartanizer.research.nanos;

import static il.org.spartan.spartanizer.tippers.TrimmerTestsUtils.*;

import org.eclipse.jdt.core.dom.*;
import org.junit.*;
import org.junit.runners.*;
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
/** TODO: orimarco <tt>marcovitch.ori@gmail.com</tt> please add a description
 * @author orimarco <tt>marcovitch.ori@gmail.com</tt>
 * @since 2017-01-18 */
@SuppressWarnings("static-method")
public class CollectTest {
  @Test public void a() {
    trimmingOf("L<SimpleName> $ = new A<>();  for (VariableDeclarationFragment ¢ : fs)   $.add(¢.getName());")//
        .using(EnhancedForStatement.class, new ForEach(), new ForEachSuchThat(), new Collect())//
        .gives("L<SimpleName>$=(fs).stream().map(¢->¢.getName()).collect(Collectors.toList());")//
    ;
  }

  @Test public void b() {
    trimmingOf("L<SimpleName> $ = new A<>();  for (VariableDeclarationFragment ¢ : fs) if(iLikeTo(a))  $.add(¢.getName());")//
        .using(EnhancedForStatement.class, new ForEachSuchThat(), new ForEach(), new Collect())//
        .gives("L<SimpleName>$=(fs).stream().filter(¢->iLikeTo(a)).map(¢->¢.getName()).collect(Collectors.toList());")//
    ;
  }

  @Test public void c() {
    trimmingOf("L<SimpleName> $ = new A<>();  for (VariableDeclarationFragment ¢ : fs) if(iLikeTo(a))  $.add(¢);")//
        .using(EnhancedForStatement.class, new ForEach(), new ForEachSuchThat(), new Collect())//
        .gives("L<SimpleName>$=(fs).stream().filter(λ->iLikeTo(a)).collect(Collectors.toList());")//
        .stays();
  }

  @Test public void d() {
    trimmingOf("L<SimpleName> $ = new A<>();  for (VariableDeclarationFragment ¢ : fs) $.add(¢);")//
        .using(EnhancedForStatement.class, new ForEach(), new ForEachSuchThat(), new Collect())//
        .gives("L<SimpleName>$=(fs).stream().collect(Collectors.toList());")//
        .stays();
  }

  @Test public void e() {
    trimmingOf("for (L<M> sentence : allSentences()) for (M ¢ : sentence)  if (disabling.specificallyDisabled(¢))   $.add(x(¢));")//
        .using(EnhancedForStatement.class, new ForEach(), new ForEachSuchThat(), new Collect())//
        .gives(
            "for(L<M>sentence:allSentences())$.addAll((sentence).stream().filter(¢->disabling.specificallyDisabled(¢)).map(¢->x(¢)).collect(Collectors.toList()));")//
        .using(EnhancedForStatement.class, new ForEach(), new ForEachSuchThat(), new Collect())//
        .stays();
  }

  @Test public void e0() {
    trimmingOf("for (S s : as()) for (M ¢ : s) if (a.b(¢)) $.add(x(¢));")//
        .using(EnhancedForStatement.class, new ForEach(), new ForEachSuchThat(), new Collect())//
        .gives("for(S s:as())$.addAll((s).stream().filter(¢->a.b(¢)).map(¢->x(¢)).collect(Collectors.toList()));")//
        .stays();
  }

  @Test public void f() {
    trimmingOf("Set<Modifier> $ = new H<>(); for (IExtendedModifier ¢ : ms)  if (test(¢, ps))   $.add((Modifier) ¢);")//
        .using(EnhancedForStatement.class, new ForEach(), new ForEachSuchThat(), new Collect())//
        .gives("Set<Modifier>$=(ms).stream().filter(¢->test(¢,ps)).map(¢->(Modifier)¢).collect(Collectors.toList());")//
        .stays();
  }

  @Test public void g() {
    trimmingOf("L<S> $ = new A<>(); for (E e : J.parse(d.getHtml()).select(\"dS\").first().children())  $.add(e.tagName()); return $;")//
        .using(EnhancedForStatement.class, new ForEach(), new ForEachSuchThat(), new Collect())//
        .gives("L<S>$=(J.parse(d.getHtml()).select(\"dS\").first().children()).stream().map(e->e.tagName()).collect(Collectors.toList());return $;")//
        .gives("return(J.parse(d.getHtml()).select(\"dS\").first().children()).stream().map(e->e.tagName()).collect(Collectors.toList());")//
        .stays();
  }
}