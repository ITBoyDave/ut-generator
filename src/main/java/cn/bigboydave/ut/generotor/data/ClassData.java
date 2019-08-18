package cn.bigboydave.ut.generotor.data;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/17/19 11:40 PM
 * @srcFile ClassData.java
 */
public class ClassData implements Serializable {

    /**
     * 最终的父类
     */
    private boolean root;
    private Class<? extends Object> clazz;
    private ClassData supperClassData;
    private List<MethodData> methodData;
    private List<Field> fields;
    private List<Annotation> annotations;

    public boolean isRoot() {
        return root;
    }

    public void setRoot(boolean root) {
        this.root = root;
    }

    public ClassData(Class<? extends Object> clazz) {
        this.clazz = clazz;
    }

    public Class<? extends Object> getClazz() {
        return clazz;
    }


    public ClassData getSupperClassData() {
        return supperClassData;
    }

    public void setSupperClassData(ClassData supperClassData) {
        this.supperClassData = supperClassData;
    }

    public List<MethodData> getMethodData() {
        return methodData;
    }

    public void setMethodData(List<MethodData> methodData) {
        this.methodData = methodData;
    }

    public List<Field> getFields() {
        return fields;
    }

    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public void setAnnotations(List<Annotation> annotations) {
        this.annotations = annotations;
    }

}
