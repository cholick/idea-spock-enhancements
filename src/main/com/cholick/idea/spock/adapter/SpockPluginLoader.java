package com.cholick.idea.spock.adapter;

import com.cholick.idea.spock.GroovyIcons;
import com.cholick.idea.spock.GroovyIcons11;
import com.cholick.idea.spock.GroovyIcons12;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.impl.ComponentManagerImpl;
import org.jetbrains.annotations.NotNull;
import org.picocontainer.MutablePicoContainer;

public class SpockPluginLoader implements ApplicationComponent {

    ComponentManagerImpl componentManager;

    SpockPluginLoader(@NotNull ComponentManagerImpl componentManager) {
        this.componentManager = componentManager;
    }

    @Override
    public void initComponent() {
        MutablePicoContainer picoContainer = componentManager.getPicoContainer();
        switch (getVersion()) {
            case V11:
                picoContainer.registerComponentInstance(GroovyIcons.class.getName(), new GroovyIcons11());
                break;
            case V12:
            default:
                picoContainer.registerComponentInstance(GroovyIcons.class.getName(), new GroovyIcons12());
        }
    }

    @Override
    public void disposeComponent() {
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "Spock Framework Enhancements";
    }

    private IntelliJVersion getVersion() {
        if(ApplicationInfo.getInstance().getBuild().getBaselineVersion() < 123) {
            return IntelliJVersion.V11;
        }
        return IntelliJVersion.V12;
    }

    enum IntelliJVersion {
        V11, V12
    }

}
