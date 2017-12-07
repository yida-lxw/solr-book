package com.yida.solr.book.examples.utils;

import java.lang.annotation.Inherited;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Java反射工具类
 * @author Lanxiaowei
 * @createTime 2013-01-30 下午04:18:45
 */
@SuppressWarnings({"unchecked","rawtypes"})
public class ReflectionUtils {

    /**
     * 调用Getter方法
     */
    public static Object invokeGetterMethod(Object target, String propertyName) {
        String getterMethodName = "get" + StringUtils.upperFirstLetter(propertyName);
        return invokeMethod(target, getterMethodName, new Class[] {}, new Object[] {});
    }

    /**
     * 调用Getter方法
     */
    public static Object invokeGetMethod(Object target, String methodName) {
        return invokeMethod(target, methodName, new Class[] {}, new Object[] {});
    }

    /**
     * 调用Setter方法.使用value的Class来查找Setter方法.
     */
    public static void invokeSetterMethod(Object target, String propertyName, Object value) {
        invokeSetterMethod(target, propertyName, value, null);
    }

    /**
     * 调用Setter方法.
     *
     * @param propertyType
     *            用于查找Setter方法,为空时使用value的Class替代
     */
    public static void invokeSetterMethod(Object target, String propertyName, Object value, Class<?> propertyType) {
        Class<?> type = propertyType != null ? propertyType : value.getClass();
        String setterMethodName = "set" + StringUtils.upperFirstLetter(propertyName);
        invokeMethod(target, setterMethodName, new Class[] { type }, new Object[] { value });
    }

    /**
     * 直接读取对象属性值, 无视private/protected修饰符, 不经过getter函数.
     */
    public static Object getFieldValue(final Object object, final String fieldName) {
        Field field = getDeclaredField(object, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        makeAccessible(field);
        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("直接读取对象属性值出现异常", e);
        }
        return result;
    }

    /**
     * 直接设置对象属性值, 无视private/protected修饰符, 不经过setter函数.
     */
    public static void setFieldValue(final Object object, final String fieldName, final Object value) {
        Field field = getDeclaredField(object, fieldName);
        if (field == null) {
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        makeAccessible(field);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("直接设置对象属性值出现异常", e);
        }
    }

    /**
     * 直接调用对象方法, 无视private/protected修饰符.
     */
    public static Object invokeMethod(final Object object, final String methodName, final Class<?>[] parameterTypes,
                                      final Object[] parameters) {
        Method method = getDeclaredMethod(object.getClass(), methodName, parameterTypes);

        if (method == null) {
            throw new IllegalArgumentException("Could not find method [" + methodName + "] on target [" + object + "]");
        }
        method.setAccessible(true);
        try {
            return method.invoke(object, parameters);
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
    }

    /**
     * 将反射时的checked exception转换为unchecked exception.
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(Exception e) {
        return convertReflectionExceptionToUnchecked(null, e);
    }

    /**
     * 将反射时的checked exception转换为unchecked exception(重载)
     */
    public static RuntimeException convertReflectionExceptionToUnchecked(String desc, Exception e) {
        desc = (desc == null) ? "Unexpected Checked Exception." : desc;
        if (e instanceof IllegalAccessException || e instanceof IllegalArgumentException || e instanceof NoSuchMethodException) {
            return new IllegalArgumentException(desc, e);
        } else if (e instanceof InvocationTargetException) {
            return new RuntimeException(desc, ((InvocationTargetException) e).getTargetException());
        } else if (e instanceof RuntimeException) {
            return (RuntimeException) e;
        }
        return new RuntimeException(desc, e);
    }

    /**
     * 循环向上转型, 获取对象的DeclaredMethod. 若向上转型到Object仍无法找到, 返回null.
     */
    protected static Method getDeclaredMethod(Class target, String methodName, Class<?>[] parameterTypes) {
        if (null == target) {
            return null;
        }
        for (Class<?> superClass = target; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredMethod(methodName, parameterTypes);
            } catch (NoSuchMethodException e) {
                // Method不在当前类定义,继续向上转型
                continue;
            }
        }
        return null;
    }

    /**
     * 循环向上转型, 获取对象的DeclaredField. 若向上转型到Object仍无法找到, 返回null.
     */
    protected static Field getDeclaredField(final Object object, final String fieldName) {
        if (null == object || null == fieldName || fieldName.equals("")) {
            return null;
        }
        for (Class<?> superClass = object.getClass(); superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义,继续向上转型
                continue;
            }
        }
        return null;
    }

    /**
     * 强行设置Field可访问
     */
    protected static void makeAccessible(final Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * 通过反射, 获得定义Class时声明的父类的泛型参数的类型,若无法找到, 返回Object.class 如public UserDao extends HibernateDao<User,Long>
     *
     * @param clazz
     * @param index
     *            父类泛型参数的索引，从0开始计算
     * @return Class 返回父类index位置的泛型参数的class
     */
    public static Class getSuperClassGenricType(final Class clazz, final int index) {
        Type genType = clazz.getGenericSuperclass();
        if (!(genType instanceof ParameterizedType)) {
            return Object.class;
        }
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        if (index >= params.length || index < 0) {
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            return Object.class;
        }
        return (Class) params[index];
    }

    /**
     * 通过反射, 获得定义Class时声明的父类的泛型参数的类型， 默认index等于0,若无法找到, 返回Object.class
     *
     * @param clazz
     * @return Class 返回父类index位置的泛型参数的class
     */
    public static <T> Class<T> getSuperClassGenricType(final Class clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 提取集合中的对象的某属性的属性值(通过getter函数), 组合成List.
     *
     * @param collection
     *            数据集合.
     * @param propertyName
     *            要提取的属性名.
     */
    public static List convertElementPropertyToList(final Collection collection, final String propertyName) {
        List list = new ArrayList();
        try {
            for (Object obj : collection) {
                list.add(getFieldValue(obj, propertyName));
            }
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
        return list;
    }

    /**
     * 提取集合中的对象的某属性的属性值(通过getter函数), 组合成数组.
     *
     * @param collection
     *            数据集合.
     * @param propertyName
     *            要提取的属性名.
     */
    public static Object[] convertElementPropertyToArray(final Collection collection, final String propertyName) {
        Object[] arrays = new Object[collection.size()];
        try {
            int index = 0;
            for (Object obj : collection) {
                arrays[index] = getFieldValue(obj, propertyName);
                index++;
            }
        } catch (Exception e) {
            throw convertReflectionExceptionToUnchecked(e);
        }
        return arrays;
    }

    /**
     * 提取集合中的对象的某属性的属性值(通过getter函数), 用指定的分割符分隔组成字符串.
     *
     * @param collection
     *            数据集合.
     * @param propertyName
     *            要提取的属性名.
     * @param separator
     *            分隔符.
     */
    public static String convertElementPropertyToString(final Collection collection, final String propertyName,
                                                        final String separator) {
        List list = convertElementPropertyToList(collection, propertyName);
        return GerneralUtils.joinCollection(list, separator);
    }

    /**
     * 提取集合中的对象的某属性的属性值(通过getter函数),
     * 用指定的分割符分隔组成字符串，默认分隔符是逗号
     *
     * @param collection
     *            数据集合.
     * @param propertyName
     *            要提取的属性名.
     */
    public static String convertElementPropertyToString(final Collection collection, final String propertyName) {
        return convertElementPropertyToString(collection,propertyName,",");
    }

    /**
     *方法摘要：检测某类自身是否包含某属性
     *@param propertyName  属性名称
     *@return boolean 是否包含
     */
    public static boolean hasThisFieldOfSelf(Class target,String propertyName){
        Field[] fields = target.getDeclaredFields();
        if(GerneralUtils.isEmptyArray(fields)) {
            return false;
        }
        for (Field field : fields) {
            if(field.getName().equals(propertyName)) {
                return true;
            }
        }
        return false;
    }



    /**
     *方法摘要：检测某类是否包含某属性
     *@param propertyName  属性名称
     *@return boolean 是否包含
     */
    public static boolean hasThisField(Class target,String propertyName){
        List<Field> fieldList = new ArrayList<Field>();
        getFields(fieldList, target, true);
        return true;
    }

    /**
     * 判断Child类型是否是parent类型的子接口或实现类
     * @param parent
     * @param child
     * @return
     */
    public static boolean isAssignableFrom(Class parent,Class child) {
        return parent.isAssignableFrom(child);
    }

    /**
     * 判断某类或接口是否实现指定接口
     * @param target
     * @param interfaceClazz
     * @return
     */
    public static boolean isImplementInterface(Class target,Class interfaceClazz) {
        if(null == target || null == interfaceClazz) {
            return false;
        }
        Class[] faces = target.getInterfaces();
        if(GerneralUtils.isEmptyArray(faces) || !Modifier.isInterface(interfaceClazz.getModifiers())) {
            return false;
        }
        //递归判断父接口是否实现指定接口
        for (Class face : faces) {
            if(face.getName().equals(interfaceClazz.getName())) {
                return true;
            }
            Class[] parentFaces = face.getInterfaces();
            if(GerneralUtils.isEmptyArray(parentFaces)) {
                return false;
            }
            for (Class parentFace : parentFaces) {
                if(face.getName().equals(interfaceClazz.getName())) {
                    return true;
                } else if(isImplementInterface(parentFace, interfaceClazz)) {
                    return true;
                }
            }
        }
        //判断父类是否实现指定接口
        Class parentClazz = target.getSuperclass();
        if(null != parentClazz) {
            return isImplementInterface(parentClazz, interfaceClazz);
        }
        return false;
    }

    /**
     * 判断某类是否包含指定方法
     * @param methodName       方法名称
     * @param clazz            待检测的类型
     * @param parameterTypes   方法参数类型
     * @return
     */
    public static boolean hasThisMethod(String methodName,Class clazz,Class<?>[] parameterTypes) {
        Method method = getDeclaredMethod(clazz, methodName, parameterTypes);
        return null != method;
    }

    /**
     * 判断某类自身是否包含指定方法(不包括继承的方法)
     * @param methodName       方法名称
     * @param clazz            待检测的类型
     * @param parameterTypes   方法参数类型
     * @return
     */
    public static boolean hasThisMethodOfSelf(String methodName,Class clazz,Class<?>[] parameterTypes) {
        Method method = getDeclaredMethod(clazz, methodName, parameterTypes);
        return null != method;
    }

    /**
     * 获取当前类的父类的所有非Private非static属性
     * @param clazz            待检测类型
     * @return
     */
    public static void getParentFields(List<Field> fieldList,Class clazz) {
        if(null == clazz) {
            return;
        }
        Field[] fields = clazz.getDeclaredFields();
        if(GerneralUtils.isNotEmptyArray(fields)) {
            for(Field field : fields){
                //只添加非Private非static属性
                if(!Modifier.isPrivate(field.getModifiers()) &&
                        !Modifier.isStatic(field.getModifiers())) {
                    fieldList.add(field);
                }
            }
        }
        if(clazz.getSuperclass() == Object.class ) {
            return;
        }
        getParentFields(fieldList, clazz.getSuperclass());
    }

    /**
     * 获取类的所有属性
     * @param fieldList            属性目标存储集合
     * @param clazz                 待检测类型
     * @param includeParent         是否包含从父类继承的属性
     */
    public static void getFields(List<Field> fieldList,Class clazz,boolean includeParent){
        if(null == fieldList) {
            fieldList = new ArrayList<Field>();
        }
        Field[] fields = clazz.getDeclaredFields();
        if(GerneralUtils.isNotEmptyArray(fields)) {
            for (Field method : fields) {
                fieldList.add (method);
            }
        }
        fieldList.addAll(Arrays.asList(fields));
        if (includeParent) {
            getParentFields(fieldList, clazz.getSuperclass());
        }
    }

    /**
     * 获取父类的所有非私有非抽象且非静态方法
     * @param clazz     待检测类型
     * @return
     */
    public static void getParentMethods(List<Method> methodList,Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        if(GerneralUtils.isNotEmptyArray(methods)) {
            for(Method method : methods){
                //只添加非Private非abstract非static方法
                if(!Modifier.isPrivate(method.getModifiers()) &&
                        !Modifier.isAbstract(method.getModifiers()) && !Modifier.isStatic(method.getModifiers())) {
                    methodList.add(method);
                }
            }
        }
        if(clazz.getSuperclass() == Object.class) {
            return;
        }
        getParentMethods(methodList, clazz.getSuperclass());
    }

    /**
     * 获取类的所有方法
     * @param methodList            属性目标存储集合
     * @param clazz                 待检测类型
     * @param includeParent         是否包含从父类继承的方法
     */
    public static void getMothds(List<Method> methodList,Class clazz,boolean includeParent){
        if(null == methodList) {
            methodList = new ArrayList<Method>();
        }
        Method[] methods = clazz.getDeclaredMethods();
        if(GerneralUtils.isNotEmptyArray(methods)) {
            for (Method method : methods) {
                methodList.add (method);
            }
        }
        if (includeParent) {
            getParentMethods(methodList, clazz.getSuperclass());
        }
    }

    /**
     * 获取类中定义私有属性(不包括继承的属性)
     * @param clazz    待检测类型
     * @return
     */
    public static void getSelfPrivateField(List<Field> fieldList,Class clazz) {
        if(null == clazz) {
            return;
        }
        if(null == fieldList) {
            fieldList = new ArrayList<Field>();
        }
        getFields(fieldList, clazz, false);
        if(GerneralUtils.isNotEmptyCollection(fieldList)) {
            for (Iterator<Field> it = fieldList.iterator(); it.hasNext();) {
                Field field = it.next();
                //若不是private属性,从集合中删除它
                if(!Modifier.isPrivate(field.getModifiers())) {
                    it.remove();
                }
            }
        }
    }

    /**
     * 判断属性上是否有指定类型的注解
     * @param field              待检测属性
     * @param annotationClass    注解类型
     * @return
     */
    public static boolean hasThisAnnotationOfField(Field field,Class annotationClass) {
        if(null == field || null == annotationClass) {
            return false;
        }
        return field.isAnnotationPresent(annotationClass);
    }

    /**
     * 判断方法上是否有指定类型的注解
     * @param method              待检测方法
     * @param annotationClass     注解类型
     * @return
     */
    public static boolean hasThisAnnotationOfMethod(Method method,Class annotationClass) {
        if(null == method || null == annotationClass) {
            return false;
        }
        return method.isAnnotationPresent(annotationClass);
    }

    /**
     * 判断类上是否有指定类型的注解
     * @param target              待检测类型
     * @param annotationClass     注解类型
     * @return
     */
    public static boolean hasThisAnnotationOfClass(Class target,Class annotationClass) {
        if(null == target || null == annotationClass) {
            return false;
        }
        if(target.isAnnotationPresent(annotationClass)) {
            return true;
        }
        //若指定注解没有添加@Inherited注解，即该注解不可被继承
        if(!annotationClass.isAnnotationPresent(Inherited.class)) {
            return false;
        }
        //递归判断是否有从父类型继承了该注解，前提是该注解是可被继承的注解，即该注解被@Inherited注解(注意：只有类上定义的注解才可以被继承)
        Class parentClass = target.getSuperclass();
        return hasThisAnnotationOfClass(parentClass,annotationClass);
    }

    /**
     * @Author: Lanxiaowei(736031305@qq.com)
     * @Title: printClassInfo
     * @Description: 打印Java对象
     * @param @param object
     * @return void
     * @throws
     */
    public static void printClassInfo(Object object) {
        Field[] fields = object.getClass().getDeclaredFields();
        for (int i = 0; i < fields.length; i++) {
            Field field = fields[i];
            // static,final,transient修饰的属性不打印
            if(Modifier.isStatic(field.getModifiers()) ||
                    Modifier.isFinal(field.getModifiers()) ||
                    Modifier.isTransient(field.getModifiers())) {
                continue;
            }
            try {
                boolean accessFlag = field.isAccessible();
                field.setAccessible(true);
                String varName = field.getName();
                Object varValue = field.get(object);
                System.out.println(varName + "-->" + varValue);
                field.setAccessible(accessFlag);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
		/*String s = "01/31/2013 15:28:09";
		Date date = convertStringToObject(s, Date.class);
		String t = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss").format(date);
		System.out.println(t);*/
        int[] a = new int[]{1,2,3};
        System.out.println(a.getClass().getName());
    }
}