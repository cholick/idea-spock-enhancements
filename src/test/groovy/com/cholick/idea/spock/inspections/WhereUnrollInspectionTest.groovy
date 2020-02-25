package com.cholick.idea.spock.inspections

import org.jetbrains.plugins.groovy.GroovyFileType

class WhereUnrollInspectionTest extends BaseInspectionTest {

    public void testNoHighlight() {
        String spockMethod = makeSpecFile('''
            @Unroll
            def test() {
                expect:
                a
                where:
                a << [1, 2]
            }
        ''')

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        WhereUnrollInspection inspection = new WhereUnrollInspection()
        myFixture.enableInspections(inspection)

        assertFalse(hasHighlightingFor('where', myFixture.doHighlighting(), inspection))
    }

    public void testHighlight() {
        String spockMethod = makeSpecFile('''
            def test() {
                expect:
                a
                where:
                a << [1, 2]
            }
        ''')

        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        WhereUnrollInspection inspection = new WhereUnrollInspection()
        myFixture.enableInspections(inspection)

        assertTrue(hasHighlightingFor('where', myFixture.doHighlighting(), inspection))
    }

}
