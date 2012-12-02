package com.cholick.idea.spock.config

import com.intellij.testFramework.UsefulTestCase
import com.intellij.openapi.application.ApplicationManager
import com.intellij.mock.MockApplication
import org.picocontainer.MutablePicoContainer
import java.awt.Color

class SpockConfigFormTest extends UsefulTestCase {

    public void setUp() {
        super.setUp()
        ApplicationManager.setApplication(new MockApplication(myTestRootDisposable), myTestRootDisposable)
        MutablePicoContainer picoContainer = ((MutablePicoContainer) ApplicationManager.application.getPicoContainer())

        picoContainer.registerComponentInstance(SpockConfig.class.getName(), new SpockConfig())
    }

    public void testApply() {
        SpockConfigForm spockConfigForm = new SpockConfigForm()

        spockConfigForm.labelBold.setSelected(false)
        spockConfigForm.labelItalics.setSelected(true)
        spockConfigForm.labelColor.setSelectedColor(new Color(64, 0, 128))

        spockConfigForm.apply()

        assertEquals(spockConfigForm.labelBold.selected, SpockConfig.instance.labelBold)
        assertEquals(spockConfigForm.labelItalics.selected, SpockConfig.instance.labelItalics)
        assertEquals(spockConfigForm.getLabelColor().selectedColor, SpockConfig.instance.labelColor)
    }

    public void testDefaultsButton() {
        SpockConfigForm spockConfigForm = new SpockConfigForm()

        SpockConfig.instance.labelBold = false
        SpockConfig.instance.labelItalics = true
        SpockConfig.instance.labelColor = new Color(64, 0, 128)

        spockConfigForm.defaultsButton.doClick()
        spockConfigForm.apply()

        assertEquals(new SpockConfig().labelBold, SpockConfig.instance.labelBold)
        assertEquals(new SpockConfig().labelItalics, SpockConfig.instance.labelItalics)
        assertEquals(new SpockConfig().labelColor, SpockConfig.instance.labelColor)
    }

}
