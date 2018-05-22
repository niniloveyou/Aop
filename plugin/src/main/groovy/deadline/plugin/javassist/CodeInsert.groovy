package deadline.plugin.javassist

import javassist.CtClass
import javassist.CtField
import javassist.CtMethod
import javassist.CtNewMethod

import java.lang.reflect.Field

public class CodeInsert {

    static def tabSpace = "        "
    static def onSaveInstance = "\n" + tabSpace + "protected void onSaveInstanceState(Bundle outState) {\n"
    static def callSuperMethod = tabSpace + "super.onSaveInstanceState(outState);\n }"
    static def restoreAnnotation = "deadline.annotation.javassist.Restore"

    static void insert(InsertCodeModel codeModel, String path) {

        if (codeModel.fields == null || codeModel.fields.isEmpty()) {
            return
        }

        // 解冻
        if (codeModel.clazz.isFrozen()) {
            codeModel.clazz.defrost()
        }

        if (codeModel.onSaveInstance == null) {
            String m = onSaveInstance + generateCodeByModel(codeModel) + callSuperMethod
            codeModel.project.logger.error("generateCodeByModel : "  + m)
            CtMethod mInitEventMethod = CtNewMethod.make(m, codeModel.clazz);
            codeModel.clazz.addMethod(mInitEventMethod)
        } else {
            codeModel.onSaveInstance.insertBefore(generateCodeByModel(codeModel));
        }

        // 写入文件
        codeModel.clazz.writeFile(path)
    }

    static String generateCodeByModel(InsertCodeModel codeModel) {
        StringBuilder stringBuilder = new StringBuilder()
        List<CtField> fieldList = codeModel.fields
        CtMethod onSaveInstance = codeModel.onSaveInstance
        String bundleArgumentName = "outState"
        for (CtField ctField : fieldList) {
            stringBuilder.append(tabSpace + generateCodeByFieldType(bundleArgumentName, codeModel.clazz, ctField) + "\n")
        }
        codeModel.project.logger.error("generateCodeByModel : "  + stringBuilder.toString())
        return stringBuilder.toString();
    }

    static String generateCodeByFieldType(String bundleArgumentName, CtClass ctClass, CtField ctField) {

        // field name as Key
        String name = ctField.getName().toUpperCase()

        switch (ctField.getType()) {
            case CtClass.booleanType:
                Boolean object = (Boolean)ctField.getConstantValue()
                return bundleArgumentName + ".putBoolean(\"" + name + "\", " + object + ");"
            case CtClass.intType:
                int object = 100 //(Integer)ctField.getConstantValue()
                return bundleArgumentName + ".putInt(\"" + name + "\", " + object + ");"
        }
    }
}