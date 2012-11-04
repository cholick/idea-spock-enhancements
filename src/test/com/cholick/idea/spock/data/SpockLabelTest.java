package com.cholick.idea.spock.data;

import com.intellij.testFramework.UsefulTestCase;

public class SpockLabelTest extends UsefulTestCase {

    public void testContains() {
        assertTrue(SpockLabel.contains("and"));
        assertTrue(SpockLabel.contains("setup"));
        assertTrue(SpockLabel.contains("given"));
        assertTrue(SpockLabel.contains("expect"));
        assertTrue(SpockLabel.contains("when"));
        assertTrue(SpockLabel.contains("then"));
        assertTrue(SpockLabel.contains("cleanup"));
        assertTrue(SpockLabel.contains("where"));

        assertFalse(SpockLabel.contains("void"));
        assertFalse(SpockLabel.contains("cholick"));
        assertFalse(SpockLabel.contains("main"));
    }

}
