package com.cholick.idea.spock.inspections

import org.jetbrains.plugins.groovy.GroovyFileType

class WhereInspectionTest extends BaseInspectionTest {

    public void testNoHighlight() {
        String spockMethod = '''
            def test() {
                expect:
                a < 5
                where:
                a << [1, 2]
                and:
                a << [3, 4]
            }
        '''

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        BaseLabelInspection inspection = new WhereInspection(true)
        myFixture.enableInspections(inspection)

        assertFalse(hasHighlightingFor('and', myFixture.doHighlighting(), inspection))
    }

    public void testHighlightForCleanup() {
        String spockMethod = '''
            def test() {
                expect:
                a < 5
                where:
                a << [1, 2]
                cleanup:
                a = null
            }
        '''

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        BaseLabelInspection inspection = new WhereInspection(true)
        myFixture.enableInspections(inspection)

        assertTrue(hasHighlightingFor('cleanup', myFixture.doHighlighting(), inspection))
    }

}
