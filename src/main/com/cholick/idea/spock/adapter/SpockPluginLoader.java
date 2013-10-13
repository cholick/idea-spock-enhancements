package com.cholick.idea.spock.adapter;

import com.cholick.idea.spock.GrLabeledStatementAdapter;
import com.cholick.idea.spock.GrLabeledStatementAdapter11;
import com.cholick.idea.spock.GrLabeledStatementAdapter13;
import com.cholick.idea.spock.GroovyIcons;
import com.cholick.idea.spock.GroovyIcons11;
import com.cholick.idea.spock.GroovyIcons12;
import com.cholick.idea.spock.HighlightInfoFactory;
import com.cholick.idea.spock.HighlightInfoFactory11;
import com.cholick.idea.spock.HighlightInfoFactory13;
import com.intellij.openapi.application.ApplicationInfo;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.components.impl.ComponentManagerImpl;
import org.jetbrains.annotations.NotNull;
import org.picocontainer.MutablePicoContainer;

public class SpockPluginLoader implements ApplicationComponent {

    private ComponentManagerImpl componentManager;

    SpockPluginLoader(@NotNull ComponentManagerImpl componentManager) {
        this.componentManager = componentManager;
    }

    @Override
    public void initComponent() {
        MutablePicoContainer picoContainer = componentManager.getPicoContainer();
        registerGroovyIcons(picoContainer);
        registerHighlightInfoFactory(picoContainer);
        registerGrLabeledStatementAdapter(picoContainer);
    }

    @Override
    public void disposeComponent() {
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "Spock Framework Enhancements";
    }

    private void registerGroovyIcons(MutablePicoContainer picoContainer) {
        if (isAtLeast12()) {
            picoContainer.registerComponentInstance(GroovyIcons.class.getName(), new GroovyIcons12());
        } else {
            picoContainer.registerComponentInstance(GroovyIcons.class.getName(), new GroovyIcons11());
        }
    }

    private void registerHighlightInfoFactory(MutablePicoContainer picoContainer) {
        if (isAtLeast13()) {
            picoContainer.registerComponentInstance(HighlightInfoFactory.class.getName(), new HighlightInfoFactory13());
        } else {
            picoContainer.registerComponentInstance(HighlightInfoFactory.class.getName(), new HighlightInfoFactory11());
        }
    }

    private void registerGrLabeledStatementAdapter(MutablePicoContainer picoContainer) {
        if (isAtLeast13()) {
            picoContainer.registerComponentInstance(GrLabeledStatementAdapter.class.getName(), new GrLabeledStatementAdapter13());
        } else {
            picoContainer.registerComponentInstance(GrLabeledStatementAdapter.class.getName(), new GrLabeledStatementAdapter11());
        }
    }

    private IntelliJVersion getVersion() {
        int version = ApplicationInfo.getInstance().getBuild().getBaselineVersion();
        if (version >= 130) {
            return IntelliJVersion.V13;
        } else if (version >= 120) {
            return IntelliJVersion.V12;
        }
        return IntelliJVersion.V11;
    }

    private boolean isAtLeast12() {
        return getVersion().compareTo(IntelliJVersion.V12) >= 0;
    }

    private boolean isAtLeast13() {
        return getVersion().compareTo(IntelliJVersion.V13) >= 0;
    }

    enum IntelliJVersion {
        V11, V12, V13
    }

}
