package com.cholick.idea.spock.template

import com.intellij.codeInsight.lookup.Lookup
import com.intellij.codeInsight.lookup.LookupManager
import com.intellij.codeInsight.lookup.impl.LookupImpl
import com.intellij.codeInsight.template.impl.actions.ListTemplatesAction
import com.intellij.openapi.editor.Editor
import com.intellij.testFramework.fixtures.LightCodeInsightFixtureTestCase

/**
 * Created with IntelliJ IDEA.
 * User: fpape
 * Date: 6/11/13
 * Time: 8:58 PM
 * To change this template use File | Settings | File Templates.
 */
class SpockLiveTemplatesTest extends LightCodeInsightFixtureTestCase {
    @Override
    protected String getBasePath() {
        return "resources/liveTemplates/";
    }

    public void testExpect() {
        myFixture.configureByText("a.groovy", "spe<caret>");
        expandTemplate(myFixture.getEditor());
        myFixture.checkResult("""@Unroll
def ""() {
    expect:

}""", true);
    }

    public void testExpectWhere() {
        myFixture.configureByText("a.groovy", "spew<caret>");
        expandTemplate(myFixture.getEditor());
        myFixture.checkResult("""@Unroll
def ""() {
    expect:

    where:
    // TODO implement where
}""", true);
    }

    public void testWhenThen() {
        myFixture.configureByText("a.groovy", "spwt<caret>");
        expandTemplate(myFixture.getEditor());
        myFixture.checkResult("""def ""() {
    when:

    then:
    // TODO implement then
}""", true);
    }

    public void testGivenWhenThen() {
        myFixture.configureByText("a.groovy", "spgwt<caret>");
        expandTemplate(myFixture.getEditor());
        myFixture.checkResult("""def ""() {
    given:

    when:
    // TODO implement when
    then:
    // TODO implement then
}""", true);
    }

    public void testGivenWhenThenCleanup() {
        myFixture.configureByText("a.groovy", "spgwtc<caret>");
        expandTemplate(myFixture.getEditor());
        myFixture.checkResult("""def ""() {
    given:

    when:
    // TODO implement when
    then:
    // TODO implement then
    cleanup:
    // TODO implement cleanup
}""", true);
    }

    public void testGivenWhenThenWhere() {
        myFixture.configureByText("a.groovy", "spgwtw<caret>");
        expandTemplate(myFixture.getEditor());
        myFixture.checkResult("""@Unroll
def ""() {
    given:

    when:
    // TODO implement when
    then:
    // TODO implement then
    where:
    // TODO implement where
}""", true);
    }

    public void testGivenWhenThenWhereCleanup() {
        myFixture.configureByText("a.groovy", "spgwtwc<caret>");
        expandTemplate(myFixture.getEditor());
        myFixture.checkResult("""@Unroll
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
}""", true);
    }

    public static void expandTemplate(final Editor editor) {
        new ListTemplatesAction().actionPerformedImpl(editor.getProject(), editor);
        ((LookupImpl) LookupManager.getActiveLookup(editor)).finishLookup(Lookup.NORMAL_SELECT_CHAR);
    }
}
