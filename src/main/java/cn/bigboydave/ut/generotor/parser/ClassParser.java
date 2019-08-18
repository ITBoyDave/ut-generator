package cn.bigboydave.ut.generotor.parser;

import cn.bigboydave.ut.generotor.data.ClassData;
import cn.bigboydave.ut.generotor.data.MethodData;
import cn.bigboydave.ut.generotor.interfaces.Parser;
import cn.bigboydave.ut.generotor.utils.ReflectUtils;
import com.google.common.collect.Lists;

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/17/19 10:43 PM
 * @srcFile ClassParser.java
 */
public class ClassParser implements Parser<Class<? extends Object>, ClassData> {

    private final Field override;

    public ClassParser() {
        {
            try {
                this.override = AccessibleObject.class.getDeclaredField("override");
                this.override.setAccessible(true);
            } catch (NoSuchFieldException e) {
                throw new RuntimeException(e.getCause());
            }
        }
    }

    @Override
    public boolean isEffect(Class<?> clazz) {
        return clazz != null;
    }

    @Override
    public ClassData parse(Class<?> clazz) {
        return this.parse(clazz, new ClassData(clazz));
    }

    @Override
    public ClassData parse(Class<?> clazz, ClassData classData) {

        if (clazz == null) {
            return classData;
        }

        if (classData == null) {
            classData = new ClassData(clazz);
        }

        setSuperClassData(clazz, classData);
        setMethodData(clazz, classData);
        setFields(clazz, classData);
        setAnnotations(clazz, classData);
        return classData;
    }

    private ClassData setMethodData(Class<?> clazz, ClassData classData) {
        Set<Method> declaredMethods = ReflectUtils.getMethods(clazz);
        try {
            List<MethodData> methodData = new ArrayList<>(declaredMethods.size());
            if (classData.isRoot()) {
                methodData.addAll(Lists.newArrayList(declaredMethods).stream().map(method -> new MethodData(method, true)).collect(Collectors.toList()));
            } else {
                Set<Method> superMethods = ReflectUtils.getSuperSupportExtendsMethod(clazz.getSuperclass());
                final Set<String> superMethodNames = superMethods.stream().map(Method::getName).collect(Collectors.toSet());
                for (Method method : declaredMethods) {
                    if (!this.override.getBoolean(method) && superMethodNames.contains(method.getName())) {
                        if (ReflectUtils.isOverride(method, superMethods)) {
                            methodData.add(new MethodData(method, true));
                        } else {
                            methodData.add(new MethodData(method, false));
                        }
                    } else if (ReflectUtils.isLambdaMethod(method)){
                        methodData.add(new MethodData(method, false));
                    } else {
                        methodData.add(new MethodData(method, true));
                    }
                }
            }
            classData.setMethodData(methodData);
            return classData;
        } catch (Exception e) {
            throw new RuntimeException(e.getCause());
        }
    }

    private ClassData setFields(Class<?> clazz, ClassData classData) {
        classData.setFields(Lists.newArrayList(clazz.getDeclaredFields()));
        return classData;
    }

    private ClassData setAnnotations(Class<?> clazz, ClassData classData) {
        classData.setAnnotations(Lists.newArrayList(clazz.getDeclaredAnnotations()));
        return classData;
    }

    private ClassData setSuperClassData(Class<?> clazz, ClassData classData) {
        Class<?> superclass = clazz.getSuperclass();
        if (null == superclass) {
            classData.setRoot(true);
            classData.setSupperClassData(null);
            return classData;
        }
        ClassData superClassData = this.parse(superclass);
        classData.setSupperClassData(superClassData);
        return classData;
    }

}
