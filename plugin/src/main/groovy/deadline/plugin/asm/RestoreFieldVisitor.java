package deadline.plugin.asm;

import groovyjarjarasm.asm.AnnotationVisitor;
import groovyjarjarasm.asm.Attribute;
import groovyjarjarasm.asm.FieldVisitor;
import groovyjarjarasm.asm.TypePath;

/**
 * @author deadline
 * @time 2018/5/8
 */

public class RestoreFieldVisitor extends FieldVisitor {

    public RestoreFieldVisitor(int i) {
        super(i);
    }

    public RestoreFieldVisitor(int i, FieldVisitor fieldVisitor) {
        super(i, fieldVisitor);
    }

    @Override
    public AnnotationVisitor visitAnnotation(String s, boolean b) {
        return super.visitAnnotation(s, b);
    }

    @Override
    public void visitAttribute(Attribute attribute) {
        super.visitAttribute(attribute);
    }

    @Override
    public AnnotationVisitor visitTypeAnnotation(int i, TypePath typePath, String s, boolean b) {
        return super.visitTypeAnnotation(i, typePath, s, b);
    }
}
