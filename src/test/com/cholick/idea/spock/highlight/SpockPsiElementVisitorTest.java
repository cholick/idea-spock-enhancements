package com.cholick.idea.spock.highlight;

import com.cholick.idea.spock.config.SpockConfig;
import com.intellij.codeInsight.daemon.impl.analysis.HighlightInfoHolder;
import com.intellij.mock.MockApplication;
import com.intellij.mock.MockProjectEx;
import com.intellij.mock.MockPsiElement;
import com.intellij.mock.MockPsiFile;
import com.intellij.mock.MockPsiManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.extensions.Extensions;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiFile;
import com.intellij.psi.impl.source.DummyHolderElement;
import com.intellij.testFramework.UsefulTestCase;
import org.jetbrains.plugins.groovy.lang.psi.impl.auxiliary.GrLabelImpl;
import org.picocontainer.MutablePicoContainer;

public class SpockPsiElementVisitorTest extends UsefulTestCase {

    private PsiFile file;

    public void setUp() throws Exception {
        super.setUp();
        Extensions.registerAreaClass("IDEA_PROJECT", null);
        Project project = new MockProjectEx(myTestRootDisposable);
        file = new MockPsiFile(new MockPsiManager(project));
    }

    public void testVisitNonSpockElement() {
        HighlightInfoHolder highlightInfoHolder = new HighlightInfoHolder(file);
        SpockPsiElementVisitor elementVisitor = new SpockPsiElementVisitor();
        elementVisitor.setHighlightInfoHolder(highlightInfoHolder);

        elementVisitor.visitElement(new MockPsiElement(myTestRootDisposable));

        assertEquals(0, highlightInfoHolder.size());
    }

    public void testVisitNonSpockLabel() {
        HighlightInfoHolder highlightInfoHolder = new HighlightInfoHolder(file);
        SpockPsiElementVisitor elementVisitor = new SpockPsiElementVisitor();
        elementVisitor.setHighlightInfoHolder(highlightInfoHolder);

        elementVisitor.visitElement(new GrLabelImpl(new DummyHolderElement("uncle_bob")));

        assertEquals(0, highlightInfoHolder.size());
    }

    public void testVisitSpockElement() {
        ApplicationManager.setApplication(new MockApplication(myTestRootDisposable), myTestRootDisposable);
        MutablePicoContainer picoContainer = ((MutablePicoContainer) ApplicationManager.getApplication().getPicoContainer());
        picoContainer.registerComponentInstance(SpockConfig.class.getName(), new SpockConfig());

        String label = "given";

        HighlightInfoHolder highlightInfoHolder = new HighlightInfoHolder(file);
        SpockPsiElementVisitor elementVisitor = new SpockPsiElementVisitor();
        elementVisitor.setHighlightInfoHolder(highlightInfoHolder);

        elementVisitor.visitElement(new GrLabelImpl(new DummyHolderElement(label)));

        assertEquals(1, highlightInfoHolder.size());
        assertEquals(0, highlightInfoHolder.get(0).getStartOffset());
        assertEquals(label.length(), highlightInfoHolder.get(0).getEndOffset());
    }

}
