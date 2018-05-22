package deadline.plugin.javassist

import com.android.annotations.NonNull
import com.android.build.api.transform.*
import com.google.common.collect.Sets
import deadline.plugin.asm.RestoreVisitor
import groovyjarjarasm.asm.ClassReader
import groovyjarjarasm.asm.ClassVisitor
import groovyjarjarasm.asm.ClassWriter
import javassist.ClassPool
import org.apache.commons.io.FileUtils
import org.gradle.api.Project

import java.util.jar.JarEntry
import java.util.jar.JarFile
import java.util.jar.JarOutputStream
import java.util.zip.ZipEntry

/**
 * 使用场景：热修复，用户操作统计
 * @author deadline
 * @time 2018/5/4    
 */

public class JavassistRestoreTransform extends Transform {

    Project project

    def JavassistRestoreTransform(Project project) {
        this.project = project
    }

    @Override
    String getName() {
        return "JavassistRestoreTransform"
    }

    @Override
    Set<QualifiedContent.ContentType> getInputTypes() {
        return Sets.immutableEnumSet(QualifiedContent.DefaultContentType.CLASSES)
    }

    @Override
    Set<? super QualifiedContent.Scope> getScopes() {
        return Sets.immutableEnumSet(QualifiedContent.Scope.PROJECT, QualifiedContent.Scope.PROJECT_LOCAL_DEPS,
                QualifiedContent.Scope.SUB_PROJECTS, QualifiedContent.Scope.SUB_PROJECTS_LOCAL_DEPS,
                QualifiedContent.Scope.EXTERNAL_LIBRARIES)
    }

    @Override
    boolean isIncremental() {
        return false
    }

    @Override
    public void transform(@NonNull TransformInvocation transformInvocation) throws TransformException, InterruptedException, IOException {

        // Transform的inputs有两种类型，一种是目录，一种是jar包，要分开遍历
        transformInvocation.getInputs().each { TransformInput input ->
            try {
                input.jarInputs.each {
                    // todo 代码获取包名
                    RestoreInject.injectDir(it.file.absolutePath, "deadline", project)
                    String outputFileName = it.name.replace(".jar", "") + '-' + it.file.path.hashCode()
                    def output = transformInvocation.getOutputProvider().getContentLocation(outputFileName, it.contentTypes, it.scopes, Format.JAR)
                    FileUtils.copyFile(it.file, output)
                }
            } catch (Exception e) {
                project.logger.err e.getMessage()
            }

            //对文件遍历
            input.directoryInputs.each { DirectoryInput directoryInput ->
                //文件夹里面包含的是我们手写的类以及R.class、BuildConfig.class以及R$XXX.class等
                RestoreInject.injectDir(directoryInput.file.absolutePath, "deadline", project)
                def dest = transformInvocation.getOutputProvider().getContentLocation(directoryInput.name,
                        directoryInput.contentTypes, directoryInput.scopes,
                        Format.DIRECTORY)
                FileUtils.copyDirectory(directoryInput.file, dest)
            }
        }
        ClassPool.getDefault().clearImportedPackages()
    }
}
