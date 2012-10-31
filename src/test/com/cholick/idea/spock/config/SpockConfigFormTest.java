package com.cholick.idea.spock.config;

import com.intellij.mock.MockApplication;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.testFramework.UsefulTestCase;
import org.picocontainer.MutablePicoContainer;

import java.awt.*;

public class SpockConfigFormTest extends UsefulTestCase {


    public void setUp() throws Exception {
        super.setUp();
        ApplicationManager.setApplication(new MockApplication(myTestRootDisposable), myTestRootDisposable);
        MutablePicoContainer picoContainer = ((MutablePicoContainer) ApplicationManager.getApplication().getPicoContainer());

        picoContainer.registerComponentInstance(SpockConfig.class.getName(), new SpockConfig());
    }

    public void testApply() throws Exception {
        SpockConfigForm spockConfigForm = new SpockConfigForm();

        spockConfigForm.getLabelBold().setSelected(false);
        spockConfigForm.getLabelItalics().setSelected(true);
        spockConfigForm.getLabelColor().setSelectedColor(new Color(64, 0, 128));

        spockConfigForm.apply();

        assertEquals(spockConfigForm.getLabelBold().isSelected(), SpockConfig.getInstance().labelBold);
        assertEquals(spockConfigForm.getLabelItalics().isSelected(), SpockConfig.getInstance().labelItalics);
        assertEquals(spockConfigForm.getLabelColor().getSelectedColor(), SpockConfig.getInstance().labelColor);
    }

    public void testDefaultsButton() throws Exception {
        SpockConfigForm spockConfigForm = new SpockConfigForm();

        SpockConfig.getInstance().labelBold = false;
        SpockConfig.getInstance().labelItalics = true;
        SpockConfig.getInstance().labelColor = new Color(64, 0, 128);

        spockConfigForm.getDefaultsButton().doClick();
        spockConfigForm.apply();

        assertEquals(new SpockConfig().labelBold, SpockConfig.getInstance().labelBold);
        assertEquals(new SpockConfig().labelItalics, SpockConfig.getInstance().labelItalics);
        assertEquals(new SpockConfig().labelColor, SpockConfig.getInstance().labelColor);
    }

}
