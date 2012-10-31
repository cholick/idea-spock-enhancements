package com.cholick.idea.spock.config;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

@State(
        name = "SpockEnhancements",
        storages = @Storage(id = "other", file = "$APP_CONFIG$/spockEnhancements.xml")
)
public class SpockConfig implements PersistentStateComponent<org.jdom.Element> {

    public boolean labelBold;
    public boolean labelItalics;
    public Color labelColor;

    /** Initialize default settings */
    public SpockConfig() {
        labelBold = true;
        labelItalics = false;
        labelColor = Color.BLUE;
    }

    @Override
    @NotNull
    public Element getState() {
        Element element = new Element("SpockEnhancements");
        element.setAttribute("labelBold", Boolean.toString(labelBold));
        element.setAttribute("labelItalics", Boolean.toString(labelItalics));
        element.setAttribute("labelColor", Integer.toString(labelColor.getRGB()));
        return element;
    }

    @Override
    public void loadState(@NotNull Element element) {
        labelBold = Boolean.parseBoolean(element.getAttributeValue("labelBold"));
        labelItalics = Boolean.parseBoolean(element.getAttributeValue("labelItalics"));
        labelColor = new Color(Integer.parseInt(element.getAttributeValue("labelColor")));
    }

    public static SpockConfig getInstance() {
        return ServiceManager.getService(SpockConfig.class);
    }

}
