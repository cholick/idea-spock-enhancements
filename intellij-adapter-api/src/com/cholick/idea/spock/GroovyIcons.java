package com.cholick.idea.spock;

import com.intellij.openapi.components.ServiceManager;

import javax.swing.*;

public abstract class GroovyIcons {

    public static GroovyIcons getInstance() {
        return ServiceManager.getService(GroovyIcons.class);
    }

    public abstract Icon getGroovy16Icon();

    public abstract Icon getClassIcon();

}
