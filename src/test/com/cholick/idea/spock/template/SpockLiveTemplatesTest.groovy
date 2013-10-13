package com.cholick.idea.spock.template

import com.intellij.codeInsight.lookup.Lookup
import com.intellij.codeInsight.lookup.LookupManager
import com.intellij.codeInsight.lookup.impl.LookupImpl
import com.intellij.codeInsight.template.impl.actions.ListTemplatesAction
import com.intellij.openapi.editor.Editor
import com.intellij.testFramework.PsiTestUtil
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase

/**
 * User: fpape
 * Date: 6/13/13
 */
class SpockLiveTemplatesTest extends LightCodeInsightFixtureTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp()
        PsiTestUtil.addLibrary(myModule, 'spock', './lib', 'spock-core-0.7-groovy-1.8.jar')
    }

    @Override
    protected String getBasePath() {
        return "resources/liveTemplates/";
    }

    public void testExpect() {
        myFixture.configureByText("a.groovy", """
import spock.lang.Specification

class SpockSpec extends Specification {
    spex<caret>
}
""");
        expandTemplate(myFixture.getEditor());
        myFixture.checkResult("""
import spock.lang.Specification

class SpockSpec extends Specification {
    def ""() {
        expect:

    }
}
""", true);
    }

    public void testExpectWhere() {
        myFixture.configureByText("a.groovy", """
import spock.lang.Specification

class SpockSpec extends Specification {
    spew<caret>
}
""");
        expandTemplate(myFixture.getEditor());
        myFixture.checkResult("""
import spock.lang.Specification

class SpockSpec extends Specification {
    @Unroll
    def ""() {
        expect:

        where:
        // TODO implement where
    }
}
""", true);
    }

    public void testWhenThen() {
        myFixture.configureByText("a.groovy", """
import spock.lang.Specification

class SpockSpec extends Specification {
    spwt<caret>
}
""");
        expandTemplate(myFixture.getEditor());
        myFixture.checkResult("""
import spock.lang.Specification

class SpockSpec extends Specification {
    def ""() {
        when:

        then:
        // TODO implement then
    }
}
""", true);
    }

    public void testGivenWhenThen() {
        myFixture.configureByText("a.groovy", """
import spock.lang.Specification

class SpockSpec extends Specification {
    spgwt<caret>
}
""");
        expandTemplate(myFixture.getEditor());
        myFixture.checkResult("""
import spock.lang.Specification

class SpockSpec extends Specification {
    def ""() {
        given:

        when:
        // TODO implement when
        then:
        // TODO implement then
    }
}
""", true);
    }

    public void testGivenWhenThenCleanup() {
        myFixture.configureByText("a.groovy", """
import spock.lang.Specification

class SpockSpec extends Specification {
    spgwtc<caret>
}
""");
        expandTemplate(myFixture.getEditor());
        myFixture.checkResult("""
import spock.lang.Specification

class SpockSpec extends Specification {
    def ""() {
        given:

        when:
        // TODO implement when
        then:
        // TODO implement then
        cleanup:
        // TODO implement cleanup
    }
}
""", true);
    }

    public void testGivenWhenThenWhere() {
        myFixture.configureByText("a.groovy", """
import spock.lang.Specification

class SpockSpec extends Specification {
    spgwtw<caret>
}
""");
        expandTemplate(myFixture.getEditor());
        myFixture.checkResult("""
import spock.lang.Specification

class SpockSpec extends Specification {
    @Unroll
    def ""() {
        given:

        when:
        // TODO implement when
        then:
        // TODO implement then
        where:
        // TODO implement where
    }
}
""", true);
    }

    public void testGivenWhenThenWhereCleanup() {
        myFixture.configureByText("a.groovy", """
import spock.lang.Specification

class SpockSpec extends Specification {
    spgwtwc<caret>
}
""");
        expandTemplate(myFixture.getEditor());
        myFixture.checkResult("""
import spock.lang.Specification

class SpockSpec extends Specification {
    @Unroll
    def ""() {
        given:

        when:
        // TODO implement when
        then:
        // TODO implement then
        where:
        // TODO implement where
        cleanup:
        // TODO implement cleanup
    }
}
""", true);
    }

    public void testWhenThenWhere() {
        myFixture.configureByText("a.groovy", """
import spock.lang.Specification

class SpockSpec extends Specification {
    spwtw<caret>
}
""");
        expandTemplate(myFixture.getEditor());
        myFixture.checkResult("""
import spock.lang.Specification

class SpockSpec extends Specification {
    @Unroll
    def ""() {
        when:

        then:
        // TODO implement then
        where:
        // TODO implement where
    }
}
""", true);
    }

    public static void expandTemplate(final Editor editor) {
        new ListTemplatesAction().actionPerformedImpl(editor.getProject(), editor);
        ((LookupImpl) LookupManager.getActiveLookup(editor)).finishLookup(Lookup.NORMAL_SELECT_CHAR);
    }
}
