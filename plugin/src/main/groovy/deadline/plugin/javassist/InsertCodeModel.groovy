package deadline.plugin.javassist

import javassist.CtClass
import javassist.CtField
import javassist.CtMethod
import org.gradle.api.Project

class InsertCodeModel {

    //保留当前工程的引用
    Project project

    //当前处理的class
    CtClass clazz

    //带有Bus注解的方法列表
    List<CtField> fields

    // onSaveInstance方法
    CtMethod onSaveInstance
}