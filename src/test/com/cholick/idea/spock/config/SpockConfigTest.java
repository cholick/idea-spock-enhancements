package com.cholick.idea.spock.config;

import com.intellij.testFramework.UsefulTestCase;
import org.jdom.Element;

import java.awt.*;

public class SpockConfigTest extends UsefulTestCase {

    public void testSerialization() {
        SpockConfig saved = new SpockConfig();
        saved.labelBold = false;
        saved.labelItalics = true;
        saved.labelColor = new Color(0, 128, 64);
        Element savedState = saved.getState();

        SpockConfig loaded = new SpockConfig();
        //ensure non-defaults were chosen
        assert saved.labelBold != loaded.labelBold;
        assert saved.labelItalics != loaded.labelItalics;
        assert !saved.labelColor.equals(loaded.labelColor);

        loaded.loadState(savedState);
        assertEquals(saved.labelBold, loaded.labelBold);
        assertEquals(saved.labelItalics, loaded.labelItalics);
        assertEquals(saved.labelColor, loaded.labelColor);
    }

}
