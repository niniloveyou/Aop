package com.processor;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

import deadline.annotation.apt.RealmField;

/**
 * @author deadline
 * @time 2018/5/7
 */
@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_8)
@SupportedAnnotationTypes({"deadline.annotation.apt.RealmField"})
public class RealmFieldProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnv) {

        for (Element element : roundEnv.getElementsAnnotatedWith(RealmField.class)) {

            List<FieldSpec> fieldSpecList = new ArrayList<>();
            FieldSpec fieldSpec = null;
            for (Element field : element.getEnclosedElements()) {
                if (field.getKind() == ElementKind.FIELD) {
                    fieldSpec = FieldSpec.builder(TypeName.get(String.class),
                            field.getSimpleName().toString().toUpperCase(),
                            Modifier.PUBLIC, Modifier.STATIC, Modifier.FINAL)
                            .initializer("\"" + field.getSimpleName().toString().toUpperCase() + "\"")
                            .build();
                    fieldSpecList.add(fieldSpec);
                }
            }

            // 创建类相关信息
            TypeSpec realm = TypeSpec.classBuilder(element.getSimpleName() + "Field")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addJavadoc("\n@author deadline\n@time 2018/5/23\n")
                    .addFields(fieldSpecList)
                    .build();

            // 创建java 文件
            JavaFile javaFile = JavaFile
                    .builder("com.aop", realm)
                    .addFileComment("deadline copyright")
                    .build();

            // System.out.print(javaFile.toString());

            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}