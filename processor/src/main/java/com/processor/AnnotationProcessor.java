package com.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"deadline.annotation.javassist.Restore"})
public class AnnotationProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {

        /*for (TypeElement element : set) {

            // 代码块
            ClassName annotationNullable = ClassName.get("java.lang.annotation", "Documented");
            ClassName date = ClassName.get("java.util", "Date");
            CodeBlock processCodeBlock = CodeBlock.builder()
                    .add("\nDate date = null;\n", date)
                    .add("date = new Date(System.currentTimeMillis());\n")
                    .add("return date")
                    .build();

            // 创建方法
            MethodSpec process = MethodSpec.methodBuilder("process")
                    .addModifiers(Modifier.PUBLIC)
                    .addParameter(String.class, "holder", Modifier.FINAL)
                    .addException(RuntimeException.class)
                    .addJavadoc("自动生成代码")
                    .addComment(" todo comment test")
                    .addStatement(processCodeBlock)
                   // .addAnnotation(annotationNullable)
                    .returns(date)
                    .build();

            // 创建类相关信息
            TypeSpec restoreState = TypeSpec.classBuilder("RestoreState")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addJavadoc("\n@author deadline\n@time 2018/5/23\n")
                    .addMethod(process)
                    .build();

            // 创建java 文件
            JavaFile javaFile = JavaFile
                    .builder("com.aop", restoreState)
                    .addFileComment("deadline copyright")
                    .build();
            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }*/
        return true;
    }
}
