package com.cholick.idea.spock.highlight

import com.intellij.psi.impl.source.DummyHolderElement
import com.intellij.testFramework.UsefulTestCase
import com.intellij.psi.PsiFile
import com.intellij.openapi.extensions.Extensions
import com.intellij.openapi.project.Project
import com.intellij.mock.MockProjectEx
import com.intellij.mock.MockPsiFile
import com.intellij.mock.MockPsiManager
import com.intellij.codeInsight.daemon.impl.analysis.HighlightInfoHolder
import com.intellij.mock.MockPsiElement
import com.intellij.psi.impl.source.DummyHolderElement
import com.intellij.openapi.application.ApplicationManager
import com.intellij.mock.MockApplication
import org.junit.Ignore
import org.picocontainer.MutablePicoContainer
import com.cholick.idea.spock.config.SpockConfig

@Ignore
class SpockPsiElementVisitorTest extends UsefulTestCase {

    private PsiFile file

    public void setUp() throws Exception {
        super.setUp()
        Extensions.registerAreaClass('IDEA_PROJECT', null)
        Project project = new MockProjectEx(myTestRootDisposable)
        file = new MockPsiFile(new MockPsiManager(project))
    }

    public void testVisitNonSpockElement() {
        HighlightInfoHolder highlightInfoHolder = new HighlightInfoHolder(file)
        SpockPsiElementVisitor elementVisitor = new SpockPsiElementVisitor()
        elementVisitor.highlightInfoHolder = highlightInfoHolder

        elementVisitor.visitElement(new MockPsiElement(myTestRootDisposable))

        assertEquals(0, highlightInfoHolder.size())
    }

    public void testVisitNonSpockLabel() {
        HighlightInfoHolder highlightInfoHolder = new HighlightInfoHolder(file)
        SpockPsiElementVisitor elementVisitor = new SpockPsiElementVisitor()
        elementVisitor.setHighlightInfoHolder(highlightInfoHolder)

        elementVisitor.visitElement(new MockPsiElement(new DummyHolderElement('uncle_bob')))

        assertEquals(0, highlightInfoHolder.size())
    }

    public void testVisitSpockElement() {
        ApplicationManager.setApplication(new MockApplication(myTestRootDisposable), myTestRootDisposable)
        MutablePicoContainer picoContainer = ((MutablePicoContainer) ApplicationManager.getApplication().getPicoContainer())
        picoContainer.registerComponentInstance(SpockConfig.class.getName(), new SpockConfig())

        HighlightInfoHolder highlightInfoHolder = new HighlightInfoHolder(file)
        SpockPsiElementVisitor elementVisitor = new SpockPsiElementVisitor()
        elementVisitor.highlightInfoHolder = highlightInfoHolder

        String spockLabelText = 'given'
        elementVisitor.visitElement(new MockPsiElement(new DummyHolderElement(spockLabelText)))

        assertEquals(1, highlightInfoHolder.size())
        assertEquals(0, highlightInfoHolder.get(0).getStartOffset())
        assertEquals(spockLabelText.length(), highlightInfoHolder.get(0).getEndOffset())
    }

}
