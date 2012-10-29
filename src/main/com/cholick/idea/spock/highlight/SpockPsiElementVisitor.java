package com.cholick.idea.spock.highlight;

import com.intellij.codeInsight.daemon.impl.HighlightInfo;
import com.intellij.codeInsight.daemon.impl.analysis.HighlightInfoHolder;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.openapi.editor.markup.EffectType;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiElementVisitor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.lang.psi.api.auxiliary.GrLabel;

import java.awt.Color;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

public class SpockPsiElementVisitor extends PsiElementVisitor {

    private static final List<String> LABELS = Arrays.asList("given", "setup", "when", "then", "and", "expect");

    private HighlightInfoHolder highlightInfoHolder;

    @Override
    public void visitElement(PsiElement element) {
        if (!highlightInfoHolder.hasErrorResults()) {
            if (element instanceof GrLabel && LABELS.contains(element.getText())) {
                highlightInfoHolder.add(createHighlightInfo(element));
            }
        }
    }

    private static HighlightInfo createHighlightInfo(@NotNull PsiElement element) {
        TextRange range = element.getTextRange();
        int start = range.getStartOffset();
        int end = range.getEndOffset();

        TextAttributes textAttributes = new TextAttributes(Color.BLUE, null, null, EffectType.BOXED, Font.BOLD);
        return new HighlightInfo(textAttributes, SpockLabelHighlightInfoTypes.SPOCK_LABEL, start, end, null, null, HighlightSeverity.INFORMATION, false, null, false);
    }


    public void clearHighlightInfoHolder() {
        this.highlightInfoHolder = null;
    }

    public void setHighlightInfoHolder(HighlightInfoHolder highlightInfoHolder) {
        this.highlightInfoHolder = highlightInfoHolder;
    }

}
