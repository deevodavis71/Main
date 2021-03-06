package il.org.spartan.Leonidas.plugin.leonidas.BasicBlocks;

import com.intellij.psi.PsiElement;
import il.org.spartan.Leonidas.PsiTypeHelper;

import java.util.HashMap;


/**
 * @author Amir Sagiv
 * @since 03/05/2017
 */
public class StatementTest extends PsiTypeHelper {

    PsiElement psiElement;
    Statement statement;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        psiElement = createTestStatementFromString("int x;");
        statement = new Statement(psiElement);
    }

    @Override
    protected void tearDown() throws Exception {
        clearFields(this);
        psiElement = null;
        statement = null;
        super.tearDown();
    }

    public void testGeneralizes() throws Exception {
        assert statement.generalizes(Encapsulator.buildTreeFromPsi(createTestStatementFromString("x + 1;")), new HashMap<>()).matches();
        assert !statement.generalizes(Encapsulator.buildTreeFromPsi(createTestExpressionFromString("x+1")), new HashMap<>()).matches();
        assert !statement.generalizes(Encapsulator.buildTreeFromPsi(createTestStatementFromString("{int x; int y;}")), new HashMap<>()).matches();
        assert statement.generalizes(Encapsulator.buildTreeFromPsi(createTestStatementFromString("for(;;){}")), new HashMap<>()).matches();
    }


}