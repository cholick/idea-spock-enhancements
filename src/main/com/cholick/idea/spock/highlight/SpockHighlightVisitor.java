package com.cholick.idea.spock.highlight;

import com.intellij.codeInsight.daemon.impl.HighlightVisitor;
import com.intellij.codeInsight.daemon.impl.analysis.HighlightInfoHolder;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.groovy.lang.psi.GroovyFile;

public class SpockHighlightVisitor implements HighlightVisitor {

    private SpockPsiElementVisitor spockPsiElementVisitor;

    public SpockHighlightVisitor() {
        spockPsiElementVisitor = new SpockPsiElementVisitor();
    }

    @Override
    public boolean suitableForFile(@NotNull PsiFile file) {
        return file instanceof GroovyFile;
    }

    @Override
    public void visit(@NotNull PsiElement element) {
        element.accept(spockPsiElementVisitor);
    }

    @Override
    public boolean analyze(@NotNull PsiFile file, boolean updateWholeFile, @NotNull HighlightInfoHolder holder, @NotNull Runnable action) {
        spockPsiElementVisitor.setHighlightInfoHolder(holder);
        try {
            action.run();
        } finally {
            spockPsiElementVisitor.clear();
        }
        return true;
    }

    @NotNull
    @Override
    public HighlightVisitor clone() {
        return new SpockHighlightVisitor();
    }

    @Override
    public int order() {
        return 0;
    }

}
