package com.cholick.idea.spock.actions;

import com.cholick.idea.spock.template.SpockTemplates;
import com.cholick.idea.spock.template.SpockTemplatesFactory;
import com.cholick.idea.spock.util.Icons;
import com.cholick.idea.spock.util.SpockConstants;
import com.intellij.ide.IdeView;
import com.intellij.ide.actions.CreateFileFromTemplateDialog;
import com.intellij.ide.actions.JavaCreateTemplateInPackageAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.actionSystem.Presentation;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.project.DumbService;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectFileIndex;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.JarFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.JavaPsiFacade;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiDirectory;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.groovy.GroovyBundle;
import org.jetbrains.plugins.groovy.actions.GroovySourceFolderDetector;
import org.jetbrains.plugins.groovy.actions.NewGroovyActionBase;
import org.jetbrains.plugins.groovy.lang.psi.GroovyFile;
import org.jetbrains.plugins.groovy.lang.psi.api.statements.typedef.GrTypeDefinition;
import org.jetbrains.plugins.groovy.util.LibrariesUtil;

public class NewSpockSpecAction extends JavaCreateTemplateInPackageAction<GrTypeDefinition> implements DumbAware {

    public NewSpockSpecAction() {
        super("Spock Specification", "Create new Spock Specification", Icons.getGroovyIcon(), true);
    }

    @Override
    protected void buildDialog(Project project, PsiDirectory directory, CreateFileFromTemplateDialog.Builder builder) {
        builder
                .setTitle(GroovyBundle.message("newclass.dlg.title"))
                .addKind("Class", Icons.getGroovyIcon(), SpockTemplates.SPOCK_SPEC);
    }

    @Override
    protected boolean isAvailable(DataContext dataContext) {
        Module module = LangDataKeys.MODULE.getData(dataContext);
        return super.isAvailable(dataContext)
                && module != null
                && LibrariesUtil.hasGroovySdk(module)
                && hasSpockOnClasspath(module);
    }

    protected boolean hasSpockOnClasspath(Module module) {
        return !DumbService.getInstance(module.getProject()).isDumb()
                && findJarWithClass(module, SpockConstants.SPOCK_BASE_CLASS) != null;
    }

    //todo: taken from 12 implementation LibrariesUtil.findJarWithClass, remove when retire 11.1 compatibility
    @Nullable
    public static VirtualFile findJarWithClass(@NotNull Module module, final String classQName) {
        GlobalSearchScope scope = GlobalSearchScope.moduleWithDependenciesAndLibrariesScope(module);
        for (PsiClass psiClass : JavaPsiFacade.getInstance(module.getProject()).findClasses(classQName, scope)) {
            final VirtualFile local = JarFileSystem.getInstance().getVirtualFileForJar(psiClass.getContainingFile().getVirtualFile());
            if (local != null) {
                return local;
            }
        }
        return null;
    }

    @Override
    protected String getActionName(PsiDirectory directory, String newName, String templateName) {
        return "Spock Specification";
    }

    @Override
    protected PsiElement getNavigationElement(@NotNull GrTypeDefinition createdElement) {
        return createdElement.getLBrace();
    }

    @Override
    public void update(AnActionEvent e) {
        super.update(e);
        Presentation presentation = e.getPresentation();
        if (!presentation.isVisible()) {
            return;
        }

        IdeView view = LangDataKeys.IDE_VIEW.getData(e.getDataContext());
        if (view == null) {
            return;
        }
        Project project = PlatformDataKeys.PROJECT.getData(e.getDataContext());
        if (project == null) {
            return;
        }

        ProjectFileIndex projectFileIndex = ProjectRootManager.getInstance(project).getFileIndex();
        for (PsiDirectory dir : view.getDirectories()) {
            if (projectFileIndex.isInSourceContent(dir.getVirtualFile()) && checkPackageExists(dir)) {
                for (GroovySourceFolderDetector detector : GroovySourceFolderDetector.EP_NAME.getExtensions()) {
                    if (detector.isGroovySourceFolder(dir)) {
                        presentation.setWeight(Presentation.HIGHER_WEIGHT);
                        break;
                    }
                }
                return;
            }
        }
    }

    protected final GrTypeDefinition doCreate(PsiDirectory dir, String className, String templateName) throws IncorrectOperationException {
        final String fileName = className + NewGroovyActionBase.GROOVY_EXTENSION;
        final PsiFile fromTemplate = SpockTemplatesFactory.createFromTemplate(dir, className, fileName, templateName);
        if (fromTemplate instanceof GroovyFile) {
            CodeStyleManager.getInstance(fromTemplate.getManager()).reformat(fromTemplate);
            return ((GroovyFile) fromTemplate).getTypeDefinitions()[0];
        }
        final String description = fromTemplate.getFileType().getDescription();
        throw new IncorrectOperationException(GroovyBundle.message("groovy.file.extension.is.not.mapped.to.groovy.file.type", description));
    }

}
