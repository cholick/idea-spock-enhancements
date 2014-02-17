package com.cholick.idea.spock.inspections

import org.jetbrains.plugins.groovy.GroovyFileType

class CleanupInspectionTest extends BaseInspectionTest {

    public void testHighlightForExpect() {
        String spockMethod = makeSpecFile('''
            def test() {
                setup:
                def a = 1
                expect:
                a == 1
                cleanup:
                a = newValue
                expect:
                a == null
            }
        ''')
        myFixture.configureByText(GroovyFileType.GROOVY_FILE_TYPE, spockMethod)
        BaseLabelInspection inspection = new CleanupInspection()
        myFixture.enableInspections(inspection)

        assertTrue(hasHighlightingFor('expect', myFixture.doHighlighting(), inspection))
    }

}
