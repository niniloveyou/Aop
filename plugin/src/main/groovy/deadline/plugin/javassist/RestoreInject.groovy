package deadline.plugin.javassist

import javassist.CtField
import org.gradle.api.Project;

import javassist.ClassPool;
import javassist.CtClass;
import javassist.CtMethod

import java.lang.annotation.Annotation;

/**
 * 目前问题：
 * 获取不到annotation
 * 获取不到CtField的值
 * 如何获取CtMethod的参数
 *
 * @author deadline
 * @time 2018/5/8
 */

public class RestoreInject {

    private final static ClassPool pool = ClassPool.getDefault()

    public static void injectDir(String path, String packageName, Project project) {
        pool.appendClassPath(path)
        pool.appendClassPath(project.android.bootClasspath[0].toString())
        pool.importPackage("android.os.Bundle")
        pool.importPackage(CodeInsert.restoreAnnotation)
        File dir = new File(path)

        if (dir.isDirectory()) {
            dir.eachFileRecurse { File file ->
                String filePath = file.absolutePath

                if (filePath.endsWith(".class") && !filePath.contains('R$') && !filePath.contains('$')
                        && !filePath.contains('R.class') && !filePath.contains("BuildConfig.class")) {

                    int index = filePath.indexOf(packageName)
                    boolean isMyPackage = index != -1

                    if (isMyPackage) {
                        String className = getClassName(index, filePath)
                        CtClass c = pool.getCtClass(className)
                        if (c.isFrozen()) c.defrost()

                        if (c.getName().endsWith("Activity")
                                || c.getSuperclass().getName().endsWith("Activity")
                                || c.getName().endsWith("Fragment")
                                || c.getSuperclass().getName().endsWith("Fragment")){

                            //Class<?> activityClass = Class.forName(c.getName())
                            //Class<?> restoreClass = Class.forName(CodeInsert.restoreAnnotation)
                            InsertCodeModel model = new InsertCodeModel()
                            List<CtField> fieldList = null
                            for (CtField ctField : c.getDeclaredFields()) {
                                project.logger.error("the CtField name is : "  + ctField.getName())

                                for (Annotation mAnnotation : ctField.getAnnotations()) {
                                    if (mAnnotation.annotationType().canonicalName.contains("Restore")) {
                                        project.logger.error("catch annotation with name restore!!!!")
                                        if (fieldList == null) {
                                            fieldList = new ArrayList<>()
                                        }
                                        fieldList.add(ctField)
                                    }

                                }
                            }


                            for (CtMethod ctMethod : c.getDeclaredMethods()) {
                                String methodName = getSimpleName(ctMethod)
                                if (CodeInsert.onSaveInstance.contains(methodName)) {
                                    model.onSaveInstance = ctMethod
                                    break
                                }
                            }


                            model.fields = fieldList
                            model.project = project
                            model.clazz = c

                            CodeInsert.insert(model, path)
                        }

                        //用完一定记得要卸载，否则pool里的永远是旧的代码
                        c.detach()
                    }
                }
            }
        }
    }

    static String getClassName(int index, String filePath) {
        int end = filePath.length() - 6 // .class = 6
        return filePath.substring(index, end).replace('\\', '.').replace('/', '.')
    }

    static String getSimpleName(CtMethod ctmethod) {
        def methodName = ctmethod.getName();
        return methodName.substring(
                methodName.lastIndexOf('.') + 1, methodName.length())
    }
}
