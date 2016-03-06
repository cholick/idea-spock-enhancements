package com.cholick.idea.spock.adapter;

import com.cholick.idea.spock.GrLabeledStatementAdapter;
import com.cholick.idea.spock.GrLabeledStatementAdapter11;
import com.cholick.idea.spock.GrLabeledStatementAdapter13;
import com.cholick.idea.spock.HighlightInfoFactory;
import com.cholick.idea.spock.HighlightInfoFactory11;
import com.cholick.idea.spock.HighlightInfoFactory13;
import com.cholick.idea.spock.LanguageLookup;
import com.cholick.idea.spock.LanguageLookup11;
import com.cholick.idea.spock.LanguageLookup14;
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
        registerHighlightInfoFactory(picoContainer);
        registerGrLabeledStatementAdapter(picoContainer);
        registerLanguageLookup(picoContainer);
    }

    @Override
    public void disposeComponent() {
    }

    @Override
    @NotNull
    public String getComponentName() {
        return "Spock Framework Enhancements";
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

    private void registerLanguageLookup(MutablePicoContainer picoContainer) {
        if (isAtLeast14()) {
            picoContainer.registerComponentInstance(LanguageLookup.class.getName(), new LanguageLookup14());
        } else {
            picoContainer.registerComponentInstance(LanguageLookup.class.getName(), new LanguageLookup11());
        }
    }

    private IntelliJVersion getVersion() {
        int version = ApplicationInfo.getInstance().getBuild().getBaselineVersion();
        if (version >= 145) {
            return IntelliJVersion.V16;
        } else if (version >= 143) {
            return IntelliJVersion.V15;
        } else if (version >= 138) {
            return IntelliJVersion.V14;
        } else if (version >= 130) {
            return IntelliJVersion.V13;
        } else if (version >= 120) {
            return IntelliJVersion.V12;
        }
        return IntelliJVersion.V11;
    }

    private boolean isAtLeast13() {
        return getVersion().compareTo(IntelliJVersion.V13) >= 0;
    }

    private boolean isAtLeast14() {
        return getVersion().compareTo(IntelliJVersion.V14) >= 0;
    }

    enum IntelliJVersion {
        V11, V12, V13, V14, V15, V16
    }

}
