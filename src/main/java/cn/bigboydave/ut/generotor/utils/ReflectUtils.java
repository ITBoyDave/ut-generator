package cn.bigboydave.ut.generotor.utils;

import cn.bigboydave.ut.generotor.data.MethodData;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.Sets;

import java.lang.reflect.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author bigboydave
 * @description
 * @email 15839622863@163.com
 * @date 8/28/19 1:14 AM
 * @srcFile ReflectUtils.java
 */
public class ReflectUtils {

    static final LoadingCache<Class<?>, Set<Method>> LOADING_CACHE = CacheBuilder.newBuilder().expireAfterAccess(300,
            TimeUnit.SECONDS).maximumSize(10000).build(new CacheLoader<Class<?>, Set<Method>>() {
        @Override
        public Set<Method> load(Class<?> clazz) {
            return Sets.newHashSet(clazz.getDeclaredMethods());
        }
    });

    /**
     * @param field
     * @param clazz
     * @return
     */
    public static boolean assertFieldAutoInit(Field field, Class clazz) {
        try {
            field.setAccessible(true);
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            if (field.get(constructor.newInstance()) != null) {
                return true;
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * @param clazz
     * @return
     */
    public static boolean assertBaseDataType(Class clazz) {
        if (boolean.class.equals(clazz)) {
            return true;
        } else if (int.class.equals(clazz)) {
            return true;
        } else if (byte.class.equals(clazz)) {
            return true;
        } else if (short.class.equals(clazz)) {
            return true;
        } else if (long.class.equals(clazz)) {
            return true;
        } else if (double.class.equals(clazz)) {
            return true;
        } else if (float.class.equals(clazz)) {
            return true;
        } else if (char.class.equals(clazz)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * @param clazz
     * @return
     */
    public static Set<Method> getMethods(Class<?> clazz) {
        try {
            return LOADING_CACHE.get(clazz);
        } catch (ExecutionException e) {
            e.printStackTrace();
            return Sets.newHashSet();
        }
    }

    /**
     * @param clazz
     * @return
     */
    public static Set<Method> getSuperSupportExtendsMethod(Class<?> clazz) {
        try {
            Set<Method> methods = LOADING_CACHE.get(clazz);
            return methods.stream().filter(
                    method -> Modifier.isProtected(method.getModifiers()) || Modifier.isPublic(method.getModifiers()))
                    .collect(Collectors.toSet());
        } catch (ExecutionException e) {
            e.printStackTrace();
            return Sets.newHashSet(clazz.getMethods());
        }
    }

    /**
     * @param method
     * @param superMethods
     * @return
     */
    public static boolean isOverride(Method method, Collection<Method> superMethods) {
        final int parameterCount = method.getParameterCount();
        final String methodName = method.getName();
        final int modifiers = method.getModifiers();
        final Class<?>[] parameterTypes = method.getParameterTypes();
        final Class<?> returnType = method.getReturnType();
        for (Method m : superMethods) {
            int count = m.getParameterCount();
            String mName = m.getName();
            Class<?>[] mParameterTypes = m.getParameterTypes();
            Class<?> mReturnType = m.getReturnType();
            if (!methodName.equals(mName)) {
                continue;
            } else if (parameterCount != count) {
                continue;
            } else if (modifiers != m.getModifiers()) {
                continue;
            } else if (!Arrays.deepEquals(parameterTypes, mParameterTypes)) {
                continue;
            } else if (!returnType.equals(mReturnType)) {
                continue;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * lambda method
     *
     * @param method
     * @return
     */
    public static boolean isLambdaMethod(Method method) {
        String methodName = method.getName();
        int modifiers = method.getModifiers();
        return methodName.startsWith("lambda$") || methodName.contains("$");
    }

    public static boolean isStaticAllMethod(List<MethodData> methodDataList) {
        for (MethodData md : methodDataList) {
            if (!md.isSignature()) {
                continue;
            }
            if (!Modifier.isStatic(md.getMethod().getModifiers())) {
                return false;
            }
        }
        return true;
    }

}
