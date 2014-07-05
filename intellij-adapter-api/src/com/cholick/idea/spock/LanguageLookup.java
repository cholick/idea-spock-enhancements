package com.cholick.idea.spock;

import com.intellij.lang.Language;
import com.intellij.openapi.components.ServiceManager;

public abstract class LanguageLookup {

    public static LanguageLookup getInstance() {
        return ServiceManager.getService(LanguageLookup.class);
    }

    public abstract Language groovy();

}
