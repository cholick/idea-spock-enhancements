package com.cholick.idea.spock;

import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.codeInsight.daemon.impl.HighlightInfoType;
import com.intellij.openapi.components.ServiceManager;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class HighlightInfoFactory {

    public static HighlightInfoFactory getInstance() {
        return ServiceManager.getService(HighlightInfoFactory.class);
    }

    public abstract HighlightInfo createHighlightInfo(@NotNull HighlightInfoType type, @NotNull PsiElement element, @Nullable String message, @Nullable TextAttributes attributes);

}
