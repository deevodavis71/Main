package il.org.spartan.plugin;

import java.lang.reflect.*;

import javax.swing.*;

import org.eclipse.core.commands.*;
import org.eclipse.jdt.core.*;
import org.eclipse.ui.*;
import org.eclipse.ui.progress.*;

import il.org.spartan.spartanizer.dispatch.*;
import il.org.spartan.spartanizer.utils.*;

/** A handler for {@link Spartanizations} This handler executes all safe
 * spartanizations on all Java files in the current project, while exposing
 * static methods to spartanize only specific compilation units.
 * @author Ofir Elmakias <code><elmakias [at] outlook.com></code>
 * @since 2015/08/01 */
public final class SpartanizeCurrent extends BaseHandler {
  private final int MAX_PASSES = 20;

  @Override public Void execute(final ExecutionEvent e) throws ExecutionException {
    final ICompilationUnit currentCompilationUnit = eclipse.currentCompilationUnit();
    final StringBuilder status = new StringBuilder("Spartanizing " + currentCompilationUnit.getElementName());
    new JOptionPane(status, JOptionPane.INFORMATION_MESSAGE, JOptionPane.NO_OPTION, eclipse.icon, null, Integer.valueOf(0));
    final IWorkbench wb = PlatformUI.getWorkbench();
    final GUI$Applicator applicator = new Trimmer();
    applicator.setICompilationUnit(currentCompilationUnit);
    int total = 0;
    for (int i = 0; i < MAX_PASSES; ++i) {
      final Int n = new Int();
      final IProgressService ps = wb.getProgressService();
      try {
        ps.busyCursorWhile(pm -> {
          applicator.setProgressMonitor(pm);
          pm.setTaskName(status + "");
          applicator.parse();
          applicator.scan();
          n.inner = applicator.suggestionsCount();
          applicator.apply();
        });
      } catch (final InvocationTargetException x) {
        Plugin.logEvaluationError(this, x);
      } catch (final InterruptedException x) {
        Plugin.logCancellationRequest(this, x);
        return null;
      }
      if (n.inner <= 0) {
        status.append("\n Applied a total of " + total + " suggestions in " + i + " rounds");
        return eclipse.announce(status);
      }
      status.append("\n Round " + (i + 1) + ": " + n.inner + " suggestions (previous rounds: " + total + " suggestions");
      total += n.inner;
    }
    status.append("\n too many passes; aborting");
    throw new ExecutionException(status + "");
  }
}
