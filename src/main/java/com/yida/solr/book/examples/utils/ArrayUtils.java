package com.yida.solr.book.examples.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * 数组工具类
 * @author  Lanxiaowei
 * @created 2013-09-03 16:43:08
 */
@SuppressWarnings({"rawtypes", "unchecked" })
public class ArrayUtils {
    public static final Object[] EMPTY_OBJECT_ARRAY = new Object[0];

    public static final Class<?>[] EMPTY_CLASS_ARRAY = new Class[0];

    public static final String[] EMPTY_STRING_ARRAY = new String[0];

    public static final long[] EMPTY_LONG_ARRAY = new long[0];

    public static final Long[] EMPTY_LONG_OBJECT_ARRAY = new Long[0];

    public static final int[] EMPTY_INT_ARRAY = new int[0];

    public static final Integer[] EMPTY_INTEGER_OBJECT_ARRAY = new Integer[0];

    public static final short[] EMPTY_SHORT_ARRAY = new short[0];

    public static final Short[] EMPTY_SHORT_OBJECT_ARRAY = new Short[0];

    public static final byte[] EMPTY_BYTE_ARRAY = new byte[0];

    public static final Byte[] EMPTY_BYTE_OBJECT_ARRAY = new Byte[0];

    public static final double[] EMPTY_DOUBLE_ARRAY = new double[0];

    public static final Double[] EMPTY_DOUBLE_OBJECT_ARRAY = new Double[0];

    public static final float[] EMPTY_FLOAT_ARRAY = new float[0];

    public static final Float[] EMPTY_FLOAT_OBJECT_ARRAY = new Float[0];

    public static final boolean[] EMPTY_BOOLEAN_ARRAY = new boolean[0];

    public static final Boolean[] EMPTY_BOOLEAN_OBJECT_ARRAY = new Boolean[0];

    public static final char[] EMPTY_CHAR_ARRAY = new char[0];

    public static final Character[] EMPTY_CHARACTER_OBJECT_ARRAY = new Character[0];

    public static final int INDEX_NOT_FOUND = -1;

    /**
     * 判断数组为空
     * @param <T>
     *
     * @param array
     * @return
     */
    public static <T> boolean isEmpty(T[] array) {
        return (array == null) || (array.length == 0);
    }

    /**
     * 判断数组为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(long[] array) {
        return (array == null) || (array.length == 0);
    }

    /**
     * 判断数组为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(int[] array) {
        return (array == null) || (array.length == 0);
    }

    /**
     * 判断数组为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(short[] array) {
        return (array == null) || (array.length == 0);
    }

    /**
     * 判断数组为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(char[] array) {
        return (array == null) || (array.length == 0);
    }

    /**
     * 判断数组为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(byte[] array) {
        return (array == null) || (array.length == 0);
    }

    /**
     * 判断数组为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(double[] array) {
        return (array == null) || (array.length == 0);
    }

    /**
     * 判断数组为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(float[] array) {
        return (array == null) || (array.length == 0);
    }

    /**
     * 判断数组为空
     *
     * @param array
     * @return
     */
    public static boolean isEmpty(boolean[] array) {
        return (array == null) || (array.length == 0);
    }

    /**
     * 判断数组非空
     * @param <T>
     *
     * @param array
     * @return
     */
    public static <T> boolean isNotEmpty(T[] array) {
        return (array != null) && (array.length != 0);
    }

    /**
     * 判断数组非空
     *
     * @param array
     * @return
     */
    public static boolean isNotEmpty(long[] array) {
        return (array != null) && (array.length != 0);
    }

    /**
     * 判断数组非空
     *
     * @param array
     * @return
     */
    public static boolean isNotEmpty(int[] array) {
        return (array != null) && (array.length != 0);
    }

    /**
     * 判断数组非空
     *
     * @param array
     * @return
     */
    public static boolean isNotEmpty(short[] array) {
        return (array != null) && (array.length != 0);
    }

    /**
     * 判断数组非空
     *
     * @param array
     * @return
     */
    public static boolean isNotEmpty(char[] array) {
        return (array != null) && (array.length != 0);
    }

    /**
     * 判断数组非空
     *
     * @param array
     * @return
     */
    public static boolean isNotEmpty(byte[] array) {
        return (array != null) && (array.length != 0);
    }

    /**
     * 判断数组非空
     *
     * @param array
     * @return
     */
    public static boolean isNotEmpty(double[] array) {
        return (array != null) && (array.length != 0);
    }

    /**
     * 判断数组非空
     *
     * @param array
     * @return
     */
    public static boolean isNotEmpty(float[] array) {
        return (array != null) && (array.length != 0);
    }

    /**
     * 判断数组非空
     *
     * @param array
     * @return
     */
    public static boolean isNotEmpty(boolean[] array) {
        return (array != null) && (array.length != 0);
    }

    /**
     * 数组克隆
     * @param <T>
     *
     * @param array
     * @return
     */
    public static <T> T[] clone(T[] array) {
        if (array == null) {
            return null;
        }
        return array.clone();
    }

    /**
     * 数组克隆
     *
     * @param array
     * @return
     */
    public static long[] clone(long[] array) {
        if (array == null) {
            return null;
        }
        return (long[]) array.clone();
    }

    /**
     * 数组克隆
     *
     * @param array
     * @return
     */
    public static int[] clone(int[] array) {
        if (array == null) {
            return null;
        }
        return (int[]) array.clone();
    }

    /**
     * 数组克隆
     *
     * @param array
     * @return
     */
    public static short[] clone(short[] array) {
        if (array == null) {
            return null;
        }
        return (short[]) array.clone();
    }

    /**
     * 数组克隆
     *
     * @param array
     * @return
     */
    public static char[] clone(char[] array) {
        if (array == null) {
            return null;
        }
        return (char[]) array.clone();
    }

    /**
     * 数组克隆
     *
     * @param array
     * @return
     */
    public static byte[] clone(byte[] array) {
        if (array == null) {
            return null;
        }
        return (byte[]) array.clone();
    }

    /**
     * 数组克隆
     *
     * @param array
     * @return
     */
    public static double[] clone(double[] array) {
        if (array == null) {
            return null;
        }
        return (double[]) array.clone();
    }

    /**
     * 数组克隆
     *
     * @param array
     * @return
     */
    public static float[] clone(float[] array) {
        if (array == null) {
            return null;
        }
        return (float[]) array.clone();
    }

    /**
     * 数组克隆
     *
     * @param array
     * @return
     */
    public static boolean[] clone(boolean[] array) {
        if (array == null) {
            return null;
        }
        return (boolean[]) array.clone();
    }

    /**
     * 数组非空检查，若为空则返回空数组，否则直接返回
     * @param <T>
     *
     * @param array
     * @return
     */
    public static <T> T[] emptyCheck(T[] array) {
        if ((array == null) || (array.length == 0)) {
            return (T[]) Array.newInstance(array.getClass().getComponentType(), 0);
        }
        return array;
    }

    /**
     * 数组非空检查，若为空则返回空数组，否则直接返回
     *
     * @param array
     * @return
     */
    public static String[] emptyCheck(String[] array) {
        if ((array == null) || (array.length == 0)) {
            return EMPTY_STRING_ARRAY;
        }
        return array;
    }

    /**
     * 数组非空检查，若为空则返回空数组，否则直接返回
     *
     * @param array
     * @return
     */
    public static long[] emptyCheck(long[] array) {
        if ((array == null) || (array.length == 0)) {
            return EMPTY_LONG_ARRAY;
        }
        return array;
    }

    /**
     * 数组非空检查，若为空则返回空数组，否则直接返回
     *
     * @param array
     * @return
     */
    public static int[] emptyCheck(int[] array) {
        if ((array == null) || (array.length == 0)) {
            return EMPTY_INT_ARRAY;
        }
        return array;
    }

    /**
     * 数组非空检查，若为空则返回空数组，否则直接返回
     *
     * @param array
     * @return
     */
    public static short[] emptyCheck(short[] array) {
        if ((array == null) || (array.length == 0)) {
            return EMPTY_SHORT_ARRAY;
        }
        return array;
    }

    /**
     * 数组非空检查，若为空则返回空数组，否则直接返回
     *
     * @param array
     * @return
     */
    public static char[] emptyCheck(char[] array) {
        if ((array == null) || (array.length == 0)) {
            return EMPTY_CHAR_ARRAY;
        }
        return array;
    }

    /**
     * 数组非空检查，若为空则返回空数组，否则直接返回
     *
     * @param array
     * @return
     */
    public static byte[] emptyCheck(byte[] array) {
        if ((array == null) || (array.length == 0)) {
            return EMPTY_BYTE_ARRAY;
        }
        return array;
    }

    /**
     * 数组非空检查，若为空则返回空数组，否则直接返回
     *
     * @param array
     * @return
     */
    public static double[] emptyCheck(double[] array) {
        if ((array == null) || (array.length == 0)) {
            return EMPTY_DOUBLE_ARRAY;
        }
        return array;
    }

    /**
     * 数组非空检查，若为空则返回空数组，否则直接返回
     *
     * @param array
     * @return
     */
    public static float[] emptyCheck(float[] array) {
        if ((array == null) || (array.length == 0)) {
            return EMPTY_FLOAT_ARRAY;
        }
        return array;
    }

    /**
     * 数组非空检查，若为空则返回空数组，否则直接返回
     *
     * @param array
     * @return
     */
    public static boolean[] emptyCheck(boolean[] array) {
        if ((array == null) || (array.length == 0)) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        return array;
    }

    /**
     * 数组非空检查，若为空则返回空数组，否则直接返回
     *
     * @param array
     * @return
     */
    public static Long[] emptyCheck(Long[] array) {
        if ((array == null) || (array.length == 0)) {
            return EMPTY_LONG_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * 数组非空检查，若为空则返回空数组，否则直接返回
     *
     * @param array
     * @return
     */
    public static Integer[] emptyCheck(Integer[] array) {
        if ((array == null) || (array.length == 0)) {
            return EMPTY_INTEGER_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * 数组非空检查，若为空则返回空数组，否则直接返回
     *
     * @param array
     * @return
     */
    public static Short[] emptyCheck(Short[] array) {
        if ((array == null) || (array.length == 0)) {
            return EMPTY_SHORT_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * 数组非空检查，若为空则返回空数组，否则直接返回
     *
     * @param array
     * @return
     */
    public static Character[] emptyCheck(Character[] array) {
        if ((array == null) || (array.length == 0)) {
            return EMPTY_CHARACTER_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * 数组非空检查，若为空则返回空数组，否则直接返回
     *
     * @param array
     * @return
     */
    public static Byte[] emptyCheck(Byte[] array) {
        if ((array == null) || (array.length == 0)) {
            return EMPTY_BYTE_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * 数组非空检查，若为空则返回空数组，否则直接返回
     *
     * @param array
     * @return
     */
    public static Double[] emptyCheck(Double[] array) {
        if ((array == null) || (array.length == 0)) {
            return EMPTY_DOUBLE_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * 数组非空检查，若为空则返回空数组，否则直接返回
     *
     * @param array
     * @return
     */
    public static Float[] emptyCheck(Float[] array) {
        if ((array == null) || (array.length == 0)) {
            return EMPTY_FLOAT_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * 数组非空检查，若为空则返回空数组，否则直接返回
     *
     * @param array
     * @return
     */
    public static Boolean[] emptyCheck(Boolean[] array) {
        if ((array == null) || (array.length == 0)) {
            return EMPTY_BOOLEAN_OBJECT_ARRAY;
        }
        return array;
    }

    /**
     * 数组截取
     * @param <T>
     *
     * @param <T>
     * @param array
     *            待截取的目标数组
     * @param startIndexInclusive
     *            截取起始索引，包含起始索引(从零开始计算)
     * @param endIndexExclusive
     *            截取结束索引，但不包含结束索引(从零开始计算)
     * @return
     */
    public static <T> T[] subarray(T[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        Class type = array.getClass().getComponentType();
        if (newSize <= 0) {
            return (T[]) Array.newInstance(type, 0);
        }
        T[] subarray = (T[]) Array.newInstance(type, newSize);
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * 数组截取
     *
     * @param array
     *            待截取的目标数组
     * @param startIndexInclusive
     *            截取起始索引，包含起始索引(从零开始计算)
     * @param endIndexExclusive
     *            截取结束索引，但不包含结束索引(从零开始计算)
     * @return
     */
    public static long[] subarray(long[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_LONG_ARRAY;
        }
        long[] subarray = new long[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * 数组截取
     *
     * @param array
     *            待截取的目标数组
     * @param startIndexInclusive
     *            截取起始索引，包含起始索引(从零开始计算)
     * @param endIndexExclusive
     *            截取结束索引，但不包含结束索引(从零开始计算)
     * @return
     */
    public static int[] subarray(int[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_INT_ARRAY;
        }
        int[] subarray = new int[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * 数组截取
     *
     * @param array
     *            待截取的目标数组
     * @param startIndexInclusive
     *            截取起始索引，包含起始索引(从零开始计算)
     * @param endIndexExclusive
     *            截取结束索引，但不包含结束索引(从零开始计算)
     * @return
     */
    public static short[] subarray(short[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_SHORT_ARRAY;
        }
        short[] subarray = new short[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * 数组截取
     *
     * @param array
     *            待截取的目标数组
     * @param startIndexInclusive
     *            截取起始索引，包含起始索引(从零开始计算)
     * @param endIndexExclusive
     *            截取结束索引，但不包含结束索引(从零开始计算)
     * @return
     */
    public static char[] subarray(char[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_CHAR_ARRAY;
        }
        char[] subarray = new char[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * 数组截取
     *
     * @param array
     *            待截取的目标数组
     * @param startIndexInclusive
     *            截取起始索引，包含起始索引(从零开始计算)
     * @param endIndexExclusive
     *            截取结束索引，但不包含结束索引(从零开始计算)
     * @return
     */
    public static byte[] subarray(byte[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] subarray = new byte[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * 数组截取
     *
     * @param array
     *            待截取的目标数组
     * @param startIndexInclusive
     *            截取起始索引，包含起始索引(从零开始计算)
     * @param endIndexExclusive
     *            截取结束索引，但不包含结束索引(从零开始计算)
     * @return
     */
    public static double[] subarray(double[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        double[] subarray = new double[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * 数组截取
     *
     * @param array
     *            待截取的目标数组
     * @param startIndexInclusive
     *            截取起始索引，包含起始索引(从零开始计算)
     * @param endIndexExclusive
     *            截取结束索引，但不包含结束索引(从零开始计算)
     * @return
     */
    public static float[] subarray(float[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        float[] subarray = new float[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * 数组截取
     *
     * @param array
     *            待截取的目标数组
     * @param startIndexInclusive
     *            截取起始索引，包含起始索引(从零开始计算)
     * @param endIndexExclusive
     *            截取结束索引，但不包含结束索引(从零开始计算)
     * @return
     */
    public static boolean[] subarray(boolean[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        boolean[] subarray = new boolean[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * 数组截取
     *
     * @param array
     *            待截取的目标数组
     * @param startIndexInclusive
     *            截取起始索引，包含起始索引(从零开始计算)
     * @param endIndexExclusive
     *            截取结束索引，但不包含结束索引(从零开始计算)
     * @return
     */
    public static String[] subarray(String[] array, int startIndexInclusive, int endIndexExclusive) {
        if (array == null) {
            return null;
        }
        if (startIndexInclusive < 0) {
            startIndexInclusive = 0;
        }
        if (endIndexExclusive > array.length) {
            endIndexExclusive = array.length;
        }
        int newSize = endIndexExclusive - startIndexInclusive;
        if (newSize <= 0) {
            return EMPTY_STRING_ARRAY;
        }
        String[] subarray = new String[newSize];
        System.arraycopy(array, startIndexInclusive, subarray, 0, newSize);
        return subarray;
    }

    /**
     * 判断两个数组的长度是否相同
     *
     * @param array1
     * @param array2
     * @return
     */
    public static boolean isSameLength(Object[] array1, Object[] array2) {
        if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
            return false;
        }
        return true;
    }

    /**
     * 判断两个数组的长度是否相同
     *
     * @param array1
     * @param array2
     * @return
     */
    public static boolean isSameLength(long[] array1, long[] array2) {
        if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
            return false;
        }
        return true;
    }

    /**
     * 判断两个数组的长度是否相同
     *
     * @param array1
     * @param array2
     * @return
     */
    public static boolean isSameLength(int[] array1, int[] array2) {
        if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
            return false;
        }
        return true;
    }

    /**
     * 判断两个数组的长度是否相同
     *
     * @param array1
     * @param array2
     * @return
     */
    public static boolean isSameLength(short[] array1, short[] array2) {
        if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
            return false;
        }
        return true;
    }

    /**
     * 判断两个数组的长度是否相同
     *
     * @param array1
     * @param array2
     * @return
     */
    public static boolean isSameLength(char[] array1, char[] array2) {
        if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
            return false;
        }
        return true;
    }

    /**
     * 判断两个数组的长度是否相同
     *
     * @param array1
     * @param array2
     * @return
     */
    public static boolean isSameLength(byte[] array1, byte[] array2) {
        if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
            return false;
        }
        return true;
    }

    /**
     * 判断两个数组的长度是否相同
     *
     * @param array1
     * @param array2
     * @return
     */
    public static boolean isSameLength(double[] array1, double[] array2) {
        if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
            return false;
        }
        return true;
    }

    /**
     * 判断两个数组的长度是否相同
     *
     * @param array1
     * @param array2
     * @return
     */
    public static boolean isSameLength(float[] array1, float[] array2) {
        if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
            return false;
        }
        return true;
    }

    /**
     * 判断两个数组的长度是否相同
     *
     * @param array1
     * @param array2
     * @return
     */
    public static boolean isSameLength(boolean[] array1, boolean[] array2) {
        if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
            return false;
        }
        return true;
    }

    /**
     * 判断两个数组的长度是否相同
     *
     * @param array1
     * @param array2
     * @return
     */
    public static boolean isSameLength(String[] array1, String[] array2) {
        if (((array1 == null) && (array2 != null) && (array2.length > 0)) || ((array2 == null) && (array1 != null) && (array1.length > 0)) || ((array1 != null) && (array2 != null) && (array1.length != array2.length))) {
            return false;
        }
        return true;
    }

    /**
     * 返回数组包含的元素个数,若数组为空，返回0
     *
     * @param array
     * @return
     */
    public static int getLength(Object array) {
        if (array == null) {
            return 0;
        }
        return Array.getLength(array);
    }

    /**
     * 判断两个数组的类型是否一致
     *
     * @param array1
     * @param array2
     * @return
     */
    public static boolean isSameType(Object array1, Object array2) {
        if ((array1 == null) || (array2 == null)) {
            throw new IllegalArgumentException("The Array must not be null");
        }
        return array1.getClass().getName().equals(array2.getClass().getName());
    }

    /**
     * 数组反转[每个元素的顺序颠倒]，比如：<br/>
     * 0,1,2,3,4,5,6,7,8,9 反转后 9,8,7,6,5,4,3,2,1,0
     * @param <T>
     *
     * @param array
     */
    public static <T> T[] reverse(T[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        int i = 0;
        int j = array.length - 1;
        while (j > i) {
            T tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 数组反转[每个元素的顺序颠倒]，比如：<br/>
     * 0,1,2,3,4,5,6,7,8,9 反转后 9,8,7,6,5,4,3,2,1,0
     *
     * @param array
     */
    public static long[] reverse(long[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        int i = 0;
        int j = array.length - 1;

        while (j > i) {
            long tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 数组反转[每个元素的顺序颠倒]，比如：<br/>
     * 0,1,2,3,4,5,6,7,8,9 反转后 9,8,7,6,5,4,3,2,1,0
     *
     * @param array
     */
    public static int[] reverse(int[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        int i = 0;
        int j = array.length - 1;

        while (j > i) {
            int tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 数组反转[每个元素的顺序颠倒]，比如：<br/>
     * 0,1,2,3,4,5,6,7,8,9 反转后 9,8,7,6,5,4,3,2,1,0
     *
     * @param array
     */
    public static short[] reverse(short[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        int i = 0;
        int j = array.length - 1;

        while (j > i) {
            short tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 数组反转[每个元素的顺序颠倒]，比如：<br/>
     * 0,1,2,3,4,5,6,7,8,9 反转后 9,8,7,6,5,4,3,2,1,0
     *
     * @param array
     */
    public static char[] reverse(char[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        int i = 0;
        int j = array.length - 1;

        while (j > i) {
            char tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 数组反转[每个元素的顺序颠倒]，比如：<br/>
     * 0,1,2,3,4,5,6,7,8,9 反转后 9,8,7,6,5,4,3,2,1,0
     *
     * @param array
     */
    public static byte[] reverse(byte[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        int i = 0;
        int j = array.length - 1;

        while (j > i) {
            byte tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 数组反转[每个元素的顺序颠倒]，比如：<br/>
     * 0,1,2,3,4,5,6,7,8,9 反转后 9,8,7,6,5,4,3,2,1,0
     *
     * @param array
     */
    public static double[] reverse(double[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        int i = 0;
        int j = array.length - 1;

        while (j > i) {
            double tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 数组反转[每个元素的顺序颠倒]，比如：<br/>
     * 0,1,2,3,4,5,6,7,8,9 反转后 9,8,7,6,5,4,3,2,1,0
     *
     * @param array
     */
    public static float[] reverse(float[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        int i = 0;
        int j = array.length - 1;

        while (j > i) {
            float tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 数组反转[每个元素的顺序颠倒]，比如：<br/>
     * 0,1,2,3,4,5,6,7,8,9 反转后 9,8,7,6,5,4,3,2,1,0
     *
     * @param array
     */
    public static boolean[] reverse(boolean[] array) {
        if (array == null || array.length == 0) {
            return array;
        }
        int i = 0;
        int j = array.length - 1;

        while (j > i) {
            boolean tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
        return array;
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     * @param <T>
     *
     * @param array
     *            目标数组
     * @param objectToFind
     *            待查找的元素
     * @return
     */
    public static <T> int indexOf(T[] array, T objectToFind) {
        return indexOf(array, objectToFind, 0);
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     * @param <T>
     *
     * @param array
     *            目标数组
     * @param objectToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @return
     */
    public static <T> int indexOf(T[] array, T objectToFind, int startIndex) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        if (objectToFind == null) {
            for (int i = startIndex; i < array.length; i++) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else if (array.getClass().getComponentType().isInstance(objectToFind)) {
            for (int i = startIndex; i < array.length; i++) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static int indexOf(long[] array, long valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @return
     */
    public static int indexOf(long[] array, long valueToFind, int startIndex) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static int indexOf(int[] array, int valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @return
     */
    public static int indexOf(int[] array, int valueToFind, int startIndex) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static int indexOf(short[] array, short valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @return
     */
    public static int indexOf(short[] array, short valueToFind, int startIndex) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static int indexOf(char[] array, char valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @return
     */
    public static int indexOf(char[] array, char valueToFind, int startIndex) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static int indexOf(byte[] array, byte valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @return
     */
    public static int indexOf(byte[] array, byte valueToFind, int startIndex) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static int indexOf(double[] array, double valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param tolerance
     *            容差
     * @return
     */
    public static int indexOf(double[] array, double valueToFind, double tolerance) {
        return indexOf(array, valueToFind, 0, tolerance);
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @return
     */
    public static int indexOf(double[] array, double valueToFind, int startIndex) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @param tolerance
     *            容差
     * @return
     */
    public static int indexOf(double[] array, double valueToFind, int startIndex, double tolerance) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        double min = valueToFind - tolerance;
        double max = valueToFind + tolerance;
        for (int i = startIndex; i < array.length; i++) {
            if ((array[i] >= min) && (array[i] <= max)) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static int indexOf(float[] array, float valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @return
     */
    public static int indexOf(float[] array, float valueToFind, int startIndex) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static int indexOf(boolean[] array, boolean valueToFind) {
        return indexOf(array, valueToFind, 0);
    }

    /**
     * 查找数组中某个元素第一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @return
     */
    public static int indexOf(boolean[] array, boolean valueToFind, int startIndex) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            startIndex = 0;
        }
        for (int i = startIndex; i < array.length; i++) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     * @param <T>
     *
     * @param array
     *            目标数组
     * @param objectToFind
     *            待查找的元素
     * @return
     */
    public static <T> int lastIndexOf(T[] array, T objectToFind) {
        return lastIndexOf(array, objectToFind, 2147483647);
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     * @param <T>
     *
     * @param array
     *            目标数组
     * @param objectToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @return
     */
    public static <T> int lastIndexOf(T[] array, T objectToFind, int startIndex) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        if (objectToFind == null) {
            for (int i = startIndex; i >= 0; i--) {
                if (array[i] == null) {
                    return i;
                }
            }
        } else if (array.getClass().getComponentType().isInstance(objectToFind)) {
            for (int i = startIndex; i >= 0; i--) {
                if (objectToFind.equals(array[i])) {
                    return i;
                }
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static int lastIndexOf(long[] array, long valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @return
     */
    public static int lastIndexOf(long[] array, long valueToFind, int startIndex) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static int lastIndexOf(int[] array, int valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @return
     */
    public static int lastIndexOf(int[] array, int valueToFind, int startIndex) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static int lastIndexOf(short[] array, short valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @return
     */
    public static int lastIndexOf(short[] array, short valueToFind, int startIndex) {
        if (array == null) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static int lastIndexOf(char[] array, char valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @return
     */
    public static int lastIndexOf(char[] array, char valueToFind, int startIndex) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static int lastIndexOf(byte[] array, byte valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @return
     */
    public static int lastIndexOf(byte[] array, byte valueToFind, int startIndex) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static int lastIndexOf(double[] array, double valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param tolerance
     *            容差
     * @return
     */
    public static int lastIndexOf(double[] array, double valueToFind, double tolerance) {
        return lastIndexOf(array, valueToFind, 2147483647, tolerance);
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @return
     */
    public static int lastIndexOf(double[] array, double valueToFind, int startIndex) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @param tolerance
     *            容差
     * @return
     */
    public static int lastIndexOf(double[] array, double valueToFind, int startIndex, double tolerance) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        double min = valueToFind - tolerance;
        double max = valueToFind + tolerance;
        for (int i = startIndex; i >= 0; i--) {
            if ((array[i] >= min) && (array[i] <= max)) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static int lastIndexOf(float[] array, float valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @return
     */
    public static int lastIndexOf(float[] array, float valueToFind, int startIndex) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static int lastIndexOf(boolean[] array, boolean valueToFind) {
        return lastIndexOf(array, valueToFind, 2147483647);
    }

    /**
     * 查找数组中某个元素最后一次出现的索引位置(索引从零开始计算，若找不到则返回INDEX_NOT_FOUND)
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param startIndex
     *            查找的起始索引
     * @return
     */
    public static int lastIndexOf(boolean[] array, boolean valueToFind, int startIndex) {
        if (array == null || array.length == 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex < 0) {
            return INDEX_NOT_FOUND;
        }
        if (startIndex >= array.length) {
            startIndex = array.length - 1;
        }
        for (int i = startIndex; i >= 0; i--) {
            if (valueToFind == array[i]) {
                return i;
            }
        }
        return INDEX_NOT_FOUND;
    }

    /**
     * 判断数组内是否包含指定元素
     * @param <T>
     *
     * @param array
     *            目标数组
     * @param objectToFind
     *            待查找的元素
     * @return
     */
    public static <T> boolean contains(T[] array, T objectToFind) {
        return indexOf(array, objectToFind) != INDEX_NOT_FOUND;
    }

    /**
     * 判断数组内是否包含指定元素
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static boolean contains(long[] array, long valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    /**
     * 判断数组内是否包含指定元素
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static boolean contains(int[] array, int valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    /**
     * 判断数组内是否包含指定元素
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static boolean contains(short[] array, short valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    /**
     * 判断数组内是否包含指定元素
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static boolean contains(char[] array, char valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    /**
     * 判断数组内是否包含指定元素
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static boolean contains(byte[] array, byte valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    /**
     * 判断数组内是否包含指定元素
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static boolean contains(double[] array, double valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    /**
     * 判断数组内是否包含指定元素
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @param tolerance
     *            容差
     * @return
     */
    public static boolean contains(double[] array, double valueToFind, double tolerance) {
        return indexOf(array, valueToFind, 0, tolerance) != INDEX_NOT_FOUND;
    }

    /**
     * 判断数组内是否包含指定元素
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static boolean contains(float[] array, float valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    /**
     * 判断数组内是否包含指定元素
     *
     * @param array
     *            目标数组
     * @param valueToFind
     *            待查找的元素
     * @return
     */
    public static boolean contains(boolean[] array, boolean valueToFind) {
        return indexOf(array, valueToFind) != INDEX_NOT_FOUND;
    }

    /**
     * 对象数组转换成基本数据类型数组
     *
     * @param array
     * @return
     */
    public static char[] toPrimitive(Character[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].charValue();
        }
        return result;
    }

    /**
     * 对象数组转换成基本数据类型数组
     *
     * @param array
     *            目标数组
     * @param valueForNull
     *            null元素替代值
     * @return
     */
    public static char[] toPrimitive(Character[] array, char valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_CHAR_ARRAY;
        }
        char[] result = new char[array.length];
        for (int i = 0; i < array.length; i++) {
            Character b = array[i];
            result[i] = (b == null ? valueForNull : b.charValue());
        }
        return result;
    }

    /**
     * 对象数组转换成基本数据类型数组
     *
     * @param array
     *            目标数组
     * @return
     */
    public static long[] toPrimitive(Long[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_LONG_ARRAY;
        }
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].longValue();
        }
        return result;
    }

    /**
     * 对象数组转换成基本数据类型数组
     *
     * @param array
     *            目标数组
     * @param valueForNull
     *            null元素替代值
     * @return
     */
    public static long[] toPrimitive(Long[] array, long valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_LONG_ARRAY;
        }
        long[] result = new long[array.length];
        for (int i = 0; i < array.length; i++) {
            Long b = array[i];
            result[i] = (b == null ? valueForNull : b.longValue());
        }
        return result;
    }

    /**
     * 对象数组转换成基本数据类型数组
     *
     * @param array
     * @return
     */
    public static int[] toPrimitive(Integer[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].intValue();
        }
        return result;
    }

    /**
     * 对象数组转换成基本数据类型数组
     *
     * @param array
     * @return
     */
    public static int[] toPrimitive(Integer[] array, int valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_INT_ARRAY;
        }
        int[] result = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            Integer b = array[i];
            result[i] = (b == null ? valueForNull : b.intValue());
        }
        return result;
    }

    /**
     * 对象数组转换成基本数据类型数组
     *
     * @param array
     * @return
     */
    public static short[] toPrimitive(Short[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].shortValue();
        }
        return result;
    }

    /**
     * 对象数组转换成基本数据类型数组
     *
     * @param array
     * @return
     */
    public static short[] toPrimitive(Short[] array, short valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_SHORT_ARRAY;
        }
        short[] result = new short[array.length];
        for (int i = 0; i < array.length; i++) {
            Short b = array[i];
            result[i] = (b == null ? valueForNull : b.shortValue());
        }
        return result;
    }

    /**
     * 对象数组转换成基本数据类型数组
     *
     * @param array
     * @return
     */
    public static byte[] toPrimitive(Byte[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].byteValue();
        }
        return result;
    }

    /**
     * 对象数组转换成基本数据类型数组
     *
     * @param array
     * @return
     */
    public static byte[] toPrimitive(Byte[] array, byte valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BYTE_ARRAY;
        }
        byte[] result = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            Byte b = array[i];
            result[i] = (b == null ? valueForNull : b.byteValue());
        }
        return result;
    }

    /**
     * 对象数组转换成基本数据类型数组
     *
     * @param array
     * @return
     */
    public static double[] toPrimitive(Double[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].doubleValue();
        }
        return result;
    }

    /**
     * 对象数组转换成基本数据类型数组
     *
     * @param array
     * @return
     */
    public static double[] toPrimitive(Double[] array, double valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_DOUBLE_ARRAY;
        }
        double[] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            Double b = array[i];
            result[i] = (b == null ? valueForNull : b.doubleValue());
        }
        return result;
    }

    /**
     * 对象数组转换成基本数据类型数组
     *
     * @param array
     * @return
     */
    public static float[] toPrimitive(Float[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].floatValue();
        }
        return result;
    }

    /**
     * 对象数组转换成基本数据类型数组
     *
     * @param array
     * @return
     */
    public static float[] toPrimitive(Float[] array, float valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_FLOAT_ARRAY;
        }
        float[] result = new float[array.length];
        for (int i = 0; i < array.length; i++) {
            Float b = array[i];
            result[i] = (b == null ? valueForNull : b.floatValue());
        }
        return result;
    }

    /**
     * 对象数组转换成基本数据类型数组
     *
     * @param array
     * @return
     */
    public static boolean[] toPrimitive(Boolean[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i].booleanValue();
        }
        return result;
    }

    /**
     * 对象数组转换成基本数据类型数组
     *
     * @param array
     * @return
     */
    public static boolean[] toPrimitive(Boolean[] array, boolean valueForNull) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BOOLEAN_ARRAY;
        }
        boolean[] result = new boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            Boolean b = array[i];
            result[i] = (b == null ? valueForNull : b.booleanValue());
        }
        return result;
    }

    /**
     * 基本数据类型数组转换成对象数组
     *
     * @param array
     * @return
     */
    public static Integer[] toObject(int[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_INTEGER_OBJECT_ARRAY;
        }
        Integer[] result = new Integer[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Integer.valueOf(array[i]);
        }
        return result;
    }

    /**
     * 基本数据类型数组转换成对象数组
     *
     * @param array
     * @return
     */
    public static Short[] toObject(short[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_SHORT_OBJECT_ARRAY;
        }
        Short[] result = new Short[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Short.valueOf(array[i]);
        }
        return result;
    }

    /**
     * 基本数据类型数组转换成对象数组
     *
     * @param array
     * @return
     */
    public static Byte[] toObject(byte[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BYTE_OBJECT_ARRAY;
        }
        Byte[] result = new Byte[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Byte.valueOf(array[i]);
        }
        return result;
    }

    /**
     * 基本数据类型数组转换成对象数组
     *
     * @param array
     * @return
     */
    public static Double[] toObject(double[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_DOUBLE_OBJECT_ARRAY;
        }
        Double[] result = new Double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Double.valueOf(array[i]);
        }
        return result;
    }

    /**
     * 基本数据类型数组转换成对象数组
     *
     * @param array
     * @return
     */
    public static Float[] toObject(float[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_FLOAT_OBJECT_ARRAY;
        }
        Float[] result = new Float[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Float.valueOf(array[i]);
        }
        return result;
    }

    /**
     * 基本数据类型数组转换成对象数组
     *
     * @param array
     * @return
     */
    public static Boolean[] toObject(boolean[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_BOOLEAN_OBJECT_ARRAY;
        }
        Boolean[] result = new Boolean[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = (array[i] ? Boolean.TRUE : Boolean.FALSE);
        }
        return result;
    }

    /**
     * 基本数据类型数组转换成对象数组
     *
     * @param array
     * @return
     */
    public static Long[] toObject(long[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_LONG_OBJECT_ARRAY;
        }
        Long[] result = new Long[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Long.valueOf(array[i]);
        }
        return result;
    }

    /**
     * 基本数据类型数组转换成对象数组
     *
     * @param array
     * @return
     */
    public static Character[] toObject(char[] array) {
        if (array == null) {
            return null;
        }
        if (array.length == 0) {
            return EMPTY_CHARACTER_OBJECT_ARRAY;
        }
        Character[] result = new Character[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = Character.valueOf(array[i]);
        }
        return result;
    }

    /**
     * 两个数组合并
     * @param <T>
     *
     * @param array1
     * @param array2
     * @return
     */
    public static <T> T[] mergeArray(T[] array1, T[] array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        Class type1 = array1.getClass().getComponentType();

        T[] joinedArray = (T[]) Array.newInstance(type1, array1.length + array2.length);
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * 两个数组合并
     *
     * @param array1
     * @param array2
     * @return
     */
    public static boolean[] mergeArray(boolean[] array1, boolean[] array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        boolean[] joinedArray = new boolean[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * 两个数组合并
     *
     * @param array1
     * @param array2
     * @return
     */
    public static char[] mergeArray(char[] array1, char[] array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        char[] joinedArray = new char[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * 两个数组合并
     *
     * @param array1
     * @param array2
     * @return
     */
    public static byte[] mergeArray(byte[] array1, byte[] array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        byte[] joinedArray = new byte[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * 两个数组合并
     *
     * @param array1
     * @param array2
     * @return
     */
    public static short[] mergeArray(short[] array1, short[] array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        short[] joinedArray = new short[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * 两个数组合并
     *
     * @param array1
     * @param array2
     * @return
     */
    public static int[] mergeArray(int[] array1, int[] array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        int[] joinedArray = new int[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * 两个数组合并
     *
     * @param array1
     * @param array2
     * @return
     */
    public static long[] mergeArray(long[] array1, long[] array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        long[] joinedArray = new long[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * 两个数组合并
     *
     * @param array1
     * @param array2
     * @return
     */
    public static float[] mergeArray(float[] array1, float[] array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        float[] joinedArray = new float[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * 两个数组合并
     *
     * @param array1
     * @param array2
     * @return
     */
    public static double[] mergeArray(double[] array1, double[] array2) {
        if (array1 == null) {
            return clone(array2);
        }
        if (array2 == null) {
            return clone(array1);
        }
        double[] joinedArray = new double[array1.length + array2.length];
        System.arraycopy(array1, 0, joinedArray, 0, array1.length);
        System.arraycopy(array2, 0, joinedArray, array1.length, array2.length);
        return joinedArray;
    }

    /**
     * 往数组末尾追加一个元素<br/>
     * 注意：不会修改原始数组，会产生新的新组，先数组复制并扩容一个位置然后添加
     * @param <T>
     *
     * @param array
     * @param element
     * @return
     */
    public static <T> T[] add(T[] array, T element) {
        Class type = null;
        if (array != null) {
            type = array.getClass();
        } else {
            if (element != null) {
                type = element.getClass();
            } else {
                throw new IllegalArgumentException("Arguments cannot both be null");
            }
        }
        T[] newArray = (T[])copyArrayGrow1(array, type);
        newArray[(newArray.length - 1)] = element;
        return newArray;
    }

    /**
     * 往数组末尾追加一个元素<br/>
     * 注意：不会修改原始数组，会产生新的新组，先数组复制并扩容一个位置然后添加
     *
     * @param array
     * @param element
     * @return
     */
    public static boolean[] add(boolean[] array, boolean element) {
        boolean[] newArray = (boolean[]) copyArrayGrow1(array, Boolean.TYPE);
        newArray[(newArray.length - 1)] = element;
        return newArray;
    }

    /**
     * 往数组末尾追加一个元素<br/>
     * 注意：不会修改原始数组，会产生新的新组，先数组复制并扩容一个位置然后添加
     *
     * @param array
     * @param element
     * @return
     */
    public static byte[] add(byte[] array, byte element) {
        byte[] newArray = (byte[]) copyArrayGrow1(array, Byte.TYPE);
        newArray[(newArray.length - 1)] = element;
        return newArray;
    }

    /**
     * 往数组末尾追加一个元素<br/>
     * 注意：不会修改原始数组，会产生新的新组，先数组复制并扩容一个位置然后添加
     *
     * @param array
     * @param element
     * @return
     */
    public static char[] add(char[] array, char element) {
        char[] newArray = (char[]) copyArrayGrow1(array, Character.TYPE);
        newArray[(newArray.length - 1)] = element;
        return newArray;
    }

    /**
     * 往数组末尾追加一个元素<br/>
     * 注意：不会修改原始数组，会产生新的新组，先数组复制并扩容一个位置然后添加
     *
     * @param array
     * @param element
     * @return
     */
    public static double[] add(double[] array, double element) {
        double[] newArray = (double[]) copyArrayGrow1(array, Double.TYPE);
        newArray[(newArray.length - 1)] = element;
        return newArray;
    }

    /**
     * 往数组末尾追加一个元素<br/>
     * 注意：不会修改原始数组，会产生新的新组，先数组复制并扩容一个位置然后添加
     *
     * @param array
     * @param element
     * @return
     */
    public static float[] add(float[] array, float element) {
        float[] newArray = (float[]) copyArrayGrow1(array, Float.TYPE);
        newArray[(newArray.length - 1)] = element;
        return newArray;
    }

    /**
     * 往数组末尾追加一个元素<br/>
     * 注意：不会修改原始数组，会产生新的新组，先数组复制并扩容一个位置然后添加
     *
     * @param array
     * @param element
     * @return
     */
    public static int[] add(int[] array, int element) {
        int[] newArray = (int[]) copyArrayGrow1(array, Integer.TYPE);
        newArray[(newArray.length - 1)] = element;
        return newArray;
    }

    /**
     * 往数组末尾追加一个元素<br/>
     * 注意：不会修改原始数组，会产生新的新组，先数组复制并扩容一个位置然后添加
     *
     * @param array
     * @param element
     * @return
     */
    public static long[] add(long[] array, long element) {
        long[] newArray = (long[]) copyArrayGrow1(array, Long.TYPE);
        newArray[(newArray.length - 1)] = element;
        return newArray;
    }

    /**
     * 往数组末尾追加一个元素<br/>
     * 注意：不会修改原始数组，会产生新的新组，先数组复制并扩容一个位置然后添加
     *
     * @param array
     * @param element
     * @return
     */
    public static short[] add(short[] array, short element) {
        short[] newArray = (short[]) copyArrayGrow1(array, Short.TYPE);
        newArray[(newArray.length - 1)] = element;
        return newArray;
    }

    /**
     * 往数组指定索引位置添加一个元素
     * @param <T>
     *
     * @param array
     *            目标数组
     * @param index
     *            指定添加元素的索引位置
     * @param element
     *            待添加的元素
     * @return
     */
    public static <T> T[] add(T[] array, int index, Object element) {
        Class clss = null;
        if (array != null) {
            clss = array.getClass().getComponentType();
        } else if (element != null) {
            clss = element.getClass();
        } else {
            throw new IllegalArgumentException("Array and element cannot both be null");
        }

        T[] newArray = (T[]) add(array, index, element, clss);
        return newArray;
    }

    /**
     * 往数组指定索引位置添加一个元素
     *
     * @param array
     *            目标数组
     * @param index
     *            指定添加元素的索引位置
     * @param element
     *            待添加的元素
     * @return
     */
    public static boolean[] add(boolean[] array, int index, boolean element) {
        return (boolean[]) add(array, index, Boolean.valueOf(element), Boolean.TYPE);
    }

    /**
     * 往数组指定索引位置添加一个元素
     *
     * @param array
     *            目标数组
     * @param index
     *            指定添加元素的索引位置
     * @param element
     *            待添加的元素
     * @return
     */
    public static char[] add(char[] array, int index, char element) {
        return (char[]) add(array, index, Character.valueOf(element), Character.TYPE);
    }

    /**
     * 往数组指定索引位置添加一个元素
     *
     * @param array
     *            目标数组
     * @param index
     *            指定添加元素的索引位置
     * @param element
     *            待添加的元素
     * @return
     */
    public static byte[] add(byte[] array, int index, byte element) {
        return (byte[]) add(array, index, Byte.valueOf(element), Byte.TYPE);
    }

    /**
     * 往数组指定索引位置添加一个元素
     *
     * @param array
     *            目标数组
     * @param index
     *            指定添加元素的索引位置
     * @param element
     *            待添加的元素
     * @return
     */
    public static short[] add(short[] array, int index, short element) {
        return (short[]) add(array, index, Short.valueOf(element), Short.TYPE);
    }

    /**
     * 往数组指定索引位置添加一个元素
     *
     * @param array
     *            目标数组
     * @param index
     *            指定添加元素的索引位置
     * @param element
     *            待添加的元素
     * @return
     */
    public static int[] add(int[] array, int index, int element) {
        return (int[]) add(array, index, Integer.valueOf(element), Integer.TYPE);
    }

    /**
     * 往数组指定索引位置添加一个元素
     *
     * @param array
     *            目标数组
     * @param index
     *            指定添加元素的索引位置
     * @param element
     *            待添加的元素
     * @return
     */
    public static long[] add(long[] array, int index, long element) {
        return (long[]) add(array, index, Long.valueOf(element), Long.TYPE);
    }

    /**
     * 往数组指定索引位置添加一个元素
     *
     * @param array
     *            目标数组
     * @param index
     *            指定添加元素的索引位置
     * @param element
     *            待添加的元素
     * @return
     */
    public static float[] add(float[] array, int index, float element) {
        return (float[]) add(array, index, Float.valueOf(element), Float.TYPE);
    }

    /**
     * 往数组指定索引位置添加一个元素
     *
     * @param array
     *            目标数组
     * @param index
     *            指定添加元素的索引位置
     * @param element
     *            待添加的元素
     * @return
     */
    public static double[] add(double[] array, int index, double element) {
        return (double[]) add(array, index, Double.valueOf(element), Double.TYPE);
    }

    /**
     * 往数组指定索引位置添加一个元素
     * @param <T>
     *
     * @param array
     *            目标数组
     * @param index
     *            指定添加元素的索引位置
     * @param element
     *            待添加的元素
     * @return
     */
    public static <T> T[] add(T[] array, int index, T element, Class<?> clss) {
        if (array == null) {
            if (index != 0) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: 0");
            }
            T[] joinedArray = (T[])Array.newInstance(clss, 1);
            Array.set(joinedArray, 0, element);
            return joinedArray;
        }
        int length = Array.getLength(array);
        if ((index > length) || (index < 0)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }
        T[] result = (T[])Array.newInstance(clss, length + 1);
        System.arraycopy(array, 0, result, 0, index);
        Array.set(result, index, element);
        if (index < length) {
            System.arraycopy(array, index, result, index + 1, length - index);
        }
        return result;
    }

    /**
     * 删除数组指定索引位置的元素
     *
     * @param array
     * @param index
     * @return
     */
    public static boolean[] remove(boolean[] array, int index) {
        return (boolean[]) removeByIndex(array, index);
    }

    /**
     * 删除数组指定索引位置的元素
     *
     * @param array
     * @param index
     * @return
     */
    public static byte[] remove(byte[] array, int index) {
        return (byte[]) removeByIndex(array, index);
    }

    /**
     * 删除数组指定索引位置的元素
     *
     * @param array
     * @param index
     * @return
     */
    public static char[] remove(char[] array, int index) {
        return (char[]) removeByIndex(array, index);
    }

    /**
     * 删除数组指定索引位置的元素
     *
     * @param array
     * @param index
     * @return
     */
    public static double[] remove(double[] array, int index) {
        return (double[]) removeByIndex(array, index);
    }

    /**
     * 删除数组指定索引位置的元素
     *
     * @param array
     * @param index
     * @return
     */
    public static float[] remove(float[] array, int index) {
        return (float[]) removeByIndex(array, index);
    }

    /**
     * 删除数组指定索引位置的元素
     *
     * @param array
     * @param index
     * @return
     */
    public static int[] remove(int[] array, int index) {
        return (int[]) removeByIndex(array, index);
    }

    /**
     * 删除数组指定索引位置的元素
     *
     * @param array
     * @param index
     * @return
     */
    public static long[] remove(long[] array, int index) {
        return (long[]) removeByIndex(array, index);
    }

    /**
     * 删除数组指定索引位置的元素
     *
     * @param array
     * @param index
     * @return
     */
    public static short[] remove(short[] array, int index) {
        return (short[]) removeByIndex(array, index);
    }

    /**
     * 删除数组中某元素<br/>
     * 若数组中包含该元素，则删除，否则直接返回
     *
     * @param array
     * @param element
     * @return
     */
    public static boolean[] removeElement(boolean[] array, boolean element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return array;
        }
        return remove(array, index);
    }

    /**
     * 删除数组中某元素<br/>
     * 若数组中包含该元素，则删除，否则直接返回
     *
     * @param array
     * @param element
     * @return
     */
    public static byte[] removeElement(byte[] array, byte element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return array;
        }
        return remove(array, index);
    }

    /**
     * 删除数组中某元素<br/>
     * 若数组中包含该元素，则删除，否则直接返回
     *
     * @param array
     * @param element
     * @return
     */
    public static char[] removeElement(char[] array, char element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return array;
        }
        return remove(array, index);
    }

    /**
     * 删除数组中某元素<br/>
     * 若数组中包含该元素，则删除，否则直接返回
     *
     * @param array
     * @param element
     * @return
     */
    public static double[] removeElement(double[] array, double element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return array;
        }
        return remove(array, index);
    }

    /**
     * 删除数组中某元素<br/>
     * 若数组中包含该元素，则删除，否则直接返回
     *
     * @param array
     * @param element
     * @return
     */
    public static float[] removeElement(float[] array, float element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return array;
        }
        return remove(array, index);
    }

    /**
     * 删除数组中某元素<br/>
     * 若数组中包含该元素，则删除，否则直接返回
     *
     * @param array
     * @param element
     * @return
     */
    public static int[] removeElement(int[] array, int element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return array;
        }
        return remove(array, index);
    }

    /**
     * 删除数组中某元素<br/>
     * 若数组中包含该元素，则删除，否则直接返回
     *
     * @param array
     * @param element
     * @return
     */
    public static long[] removeElement(long[] array, long element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return array;
        }
        return remove(array, index);
    }

    /**
     * 删除数组中某元素<br/>
     * 若数组中包含该元素，则删除，否则直接返回
     *
     * @param array
     * @param element
     * @return
     */
    public static short[] removeElement(short[] array, short element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return array;
        }
        return remove(array, index);
    }

    /**
     * 删除数组中某元素<br/>
     * 若数组中包含该元素，则删除，否则直接返回
     * @param <T>
     *
     * @param array
     * @param element
     * @return
     */
    public static <T> T[] removeElement(T[] array, T element) {
        int index = indexOf(array, element);
        if (index == INDEX_NOT_FOUND) {
            return array;
        }
        return remove(array, index);
    }

    /**
     * 两个数组相减
     * <br/>比如：
     * {1,2,3,4,5,6,7,8,9,10}  中 去掉 {4,6,8,9,11}<br/>
     * 结果是[1,2,3,5,7,10]
     * @param <T>
     * @param array           目标数组1
     * @param array2                           目标数组2
     * @return
     */
    public static <T> T[] removeArray(T[] array1,T[] array2) {
        if(null == array1 || null == array2) {
            throw new IllegalArgumentException("array1 and array2 can't be both of null.");
        }
        Set<T> set = new HashSet<T>();
        for(T t : array2) {
            set.add(t);
        }
        int[] indices = new int[array1.length];
        int index = 0;
        for (int i = 0; i < array1.length; i++) {
            if(set.contains(array1[i])) {
                indices[index++] = i;
            }
        }
        int[] temp = (int[])Array.newInstance(indices.getClass().getComponentType(), index);
        System.arraycopy(indices, 0, temp, 0, index);
        indices = temp;
        temp = null;
        return removeAll(array1, indices);
    }

    /**
     * 两个数组相减
     * <br/>比如：
     * {1,2,3,4,5,6,7,8,9,10}  中 去掉 {4,6,8,9,11}<br/>
     * 结果是[1,2,3,5,7,10]
     * @param array           目标数组1
     * @param array2                           目标数组2
     * @return
     */
    public static int[] removeArray(int[] array1,int[] array2) {
        if(null == array1 || null == array2) {
            throw new IllegalArgumentException("array1 and array2 can't be both of null.");
        }
        Set<Integer> set = new HashSet<Integer>();
        for(int t : array2) {
            set.add(t);
        }
        int[] indices = new int[array1.length];
        int index = 0;
        for (int i = 0; i < array1.length; i++) {
            if(set.contains(array1[i])) {
                indices[index++] = i;
            }
        }
        int[] temp = (int[])Array.newInstance(indices.getClass().getComponentType(), index);
        System.arraycopy(indices, 0, temp, 0, index);
        indices = temp;
        temp = null;
        return removeAll(array1, indices);
    }

    /**
     * 两个数组相减
     * <br/>比如：
     * {1,2,3,4,5,6,7,8,9,10}  中 去掉 {4,6,8,9,11}<br/>
     * 结果是[1,2,3,5,7,10]
     * @param array           目标数组1
     * @param array2                           目标数组2
     * @return
     */
    public static short[] removeArray(short[] array1,short[] array2) {
        if(null == array1 || null == array2) {
            throw new IllegalArgumentException("array1 and array2 can't be both of null.");
        }
        Set<Short> set = new HashSet<Short>();
        for(short t : array2) {
            set.add(t);
        }
        int[] indices = new int[array1.length];
        int index = 0;
        for (int i = 0; i < array1.length; i++) {
            if(set.contains(array1[i])) {
                indices[index++] = i;
            }
        }
        int[] temp = (int[])Array.newInstance(indices.getClass().getComponentType(), index);
        System.arraycopy(indices, 0, temp, 0, index);
        indices = temp;
        temp = null;
        return removeAll(array1, indices);
    }

    /**
     * 两个数组相减
     * <br/>比如：
     * {1,2,3,4,5,6,7,8,9,10}  中 去掉 {4,6,8,9,11}<br/>
     * 结果是[1,2,3,5,7,10]
     * @param array           目标数组1
     * @param array2                           目标数组2
     * @return
     */
    public static long[] removeArray(long[] array1,long[] array2) {
        if(null == array1 || null == array2) {
            throw new IllegalArgumentException("array1 and array2 can't be both of null.");
        }
        Set<Long> set = new HashSet<Long>();
        for(long t : array2) {
            set.add(t);
        }
        int[] indices = new int[array1.length];
        int index = 0;
        for (int i = 0; i < array1.length; i++) {
            if(set.contains(array1[i])) {
                indices[index++] = i;
            }
        }
        int[] temp = (int[])Array.newInstance(indices.getClass().getComponentType(), index);
        System.arraycopy(indices, 0, temp, 0, index);
        indices = temp;
        temp = null;
        return removeAll(array1, indices);
    }

    /**
     * 两个数组相减
     * <br/>比如：
     * {1,2,3,4,5,6,7,8,9,10}  中 去掉 {4,6,8,9,11}<br/>
     * 结果是[1,2,3,5,7,10]
     * @param array           目标数组1
     * @param array2                           目标数组2
     * @return
     */
    public static float[] removeArray(float[] array1,float[] array2) {
        if(null == array1 || null == array2) {
            throw new IllegalArgumentException("array1 and array2 can't be both of null.");
        }
        Set<Float> set = new HashSet<Float>();
        for(float t : array2) {
            set.add(t);
        }
        int[] indices = new int[array1.length];
        int index = 0;
        for (int i = 0; i < array1.length; i++) {
            if(set.contains(array1[i])) {
                indices[index++] = i;
            }
        }
        int[] temp = (int[])Array.newInstance(indices.getClass().getComponentType(), index);
        System.arraycopy(indices, 0, temp, 0, index);
        indices = temp;
        temp = null;
        return removeAll(array1, indices);
    }

    /**
     * 两个数组相减
     * <br/>比如：
     * {1,2,3,4,5,6,7,8,9,10}  中 去掉 {4,6,8,9,11}<br/>
     * 结果是[1,2,3,5,7,10]
     * @param array           目标数组1
     * @param array2                           目标数组2
     * @return
     */
    public static double[] removeArray(double[] array1,double[] array2) {
        if(null == array1 || null == array2) {
            throw new IllegalArgumentException("array1 and array2 can't be both of null.");
        }
        Set<Double> set = new HashSet<Double>();
        for(double t : array2) {
            set.add(t);
        }
        int[] indices = new int[array1.length];
        int index = 0;
        for (int i = 0; i < array1.length; i++) {
            if(set.contains(array1[i])) {
                indices[index++] = i;
            }
        }
        int[] temp = (int[])Array.newInstance(indices.getClass().getComponentType(), index);
        System.arraycopy(indices, 0, temp, 0, index);
        indices = temp;
        temp = null;
        return removeAll(array1, indices);
    }

    /**
     * 两个数组相减
     * <br/>比如：
     * {1,2,3,4,5,6,7,8,9,10}  中 去掉 {4,6,8,9,11}<br/>
     * 结果是[1,2,3,5,7,10]
     * @param array           目标数组1
     * @param array2                           目标数组2
     * @return
     */
    public static byte[] removeArray(byte[] array1,byte[] array2) {
        if(null == array1 || null == array2) {
            throw new IllegalArgumentException("array1 and array2 can't be both of null.");
        }
        Set<Byte> set = new HashSet<Byte>();
        for(byte t : array2) {
            set.add(t);
        }
        int[] indices = new int[array1.length];
        int index = 0;
        for (int i = 0; i < array1.length; i++) {
            if(set.contains(array1[i])) {
                indices[index++] = i;
            }
        }
        int[] temp = (int[])Array.newInstance(indices.getClass().getComponentType(), index);
        System.arraycopy(indices, 0, temp, 0, index);
        indices = temp;
        temp = null;
        return removeAll(array1, indices);
    }

    /**
     * 两个数组相减
     * <br/>比如：
     * {1,2,3,4,5,6,7,8,9,10}  中 去掉 {4,6,8,9,11}<br/>
     * 结果是[1,2,3,5,7,10]
     * @param array           目标数组1
     * @param array2                           目标数组2
     * @return
     */
    public static char[] removeArray(char[] array1,char[] array2) {
        if(null == array1 || null == array2) {
            throw new IllegalArgumentException("array1 and array2 can't be both of null.");
        }
        Set<Character> set = new HashSet<Character>();
        for(char t : array2) {
            set.add(t);
        }
        int[] indices = new int[array1.length];
        int index = 0;
        for (int i = 0; i < array1.length; i++) {
            if(set.contains(array1[i])) {
                indices[index++] = i;
            }
        }
        int[] temp = (int[])Array.newInstance(indices.getClass().getComponentType(), index);
        System.arraycopy(indices, 0, temp, 0, index);
        indices = temp;
        temp = null;
        return removeAll(array1, indices);
    }

    /**
     * 两个数组相减
     * <br/>比如：
     * {1,2,3,4,5,6,7,8,9,10}  中 去掉 {4,6,8,9,11}<br/>
     * 结果是[1,2,3,5,7,10]
     * @param array           目标数组1
     * @param array2                           目标数组2
     * @return
     */
    public static boolean[] removeArray(boolean[] array1,boolean[] array2) {
        if(null == array1 || null == array2) {
            throw new IllegalArgumentException("array1 and array2 can't be both of null.");
        }
        Set<Boolean> set = new HashSet<Boolean>();
        for(boolean t : array2) {
            set.add(t);
        }
        int[] indices = new int[array1.length];
        int index = 0;
        for (int i = 0; i < array1.length; i++) {
            if(set.contains(array1[i])) {
                indices[index++] = i;
            }
        }
        int[] temp = (int[])Array.newInstance(indices.getClass().getComponentType(), index);
        System.arraycopy(indices, 0, temp, 0, index);
        indices = temp;
        temp = null;
        return removeAll(array1, indices);
    }

    /**
     * 删除索引数组中指定索引位置对应原始数组中的所有元素
     * @param <T>
     * @param array
     * @param indices  索引数组
     * @return
     */
    public static <T> T[] removeAll(T[] array, int[] indices) {
        int length = getLength(array);
        int diff = 0;

        if (isNotEmpty(indices)) {
            Arrays.sort(indices);

            int i = indices.length;
            int prevIndex = length;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                int index = indices[i];
                if ((index < 0) || (index >= length)) {
                    throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
                }
                if (index < prevIndex) {
                    diff++;
                    prevIndex = index;
                }
            }
        }
        T[] result = (T[])(Array.newInstance(array.getClass().getComponentType(), length - diff));
        if (diff < length) {
            int end = length;
            int dest = length - diff;
            for (int i = indices.length - 1; i >= 0; i--) {
                int index = indices[i];
                if (end - index > 1) {
                    int cp = end - index - 1;
                    dest -= cp;
                    System.arraycopy(array, index + 1, result, dest, cp);
                }
                end = index;
            }
            if (end > 0) {
                System.arraycopy(array, 0, result, 0, end);
            }
        }
        return result;
    }

    /**
     * 删除索引数组中指定索引位置对应原始数组中的所有元素
     * @param array    目标数组
     * @param indices  索引数组
     * @return
     */
    public static int[] removeAll(int[] array, int[] indices) {
        int length = getLength(array);
        int diff = 0;

        if (isNotEmpty(indices)) {
            Arrays.sort(indices);

            int i = indices.length;
            int prevIndex = length;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                int index = indices[i];
                if ((index < 0) || (index >= length)) {
                    throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
                }
                if (index < prevIndex) {
                    diff++;
                    prevIndex = index;
                }
            }
        }
        int[] result = (int[])(Array.newInstance(array.getClass().getComponentType(), length - diff));
        if (diff < length) {
            int end = length;
            int dest = length - diff;
            for (int i = indices.length - 1; i >= 0; i--) {
                int index = indices[i];
                if (end - index > 1) {
                    int cp = end - index - 1;
                    dest -= cp;
                    System.arraycopy(array, index + 1, result, dest, cp);
                }
                end = index;
            }
            if (end > 0) {
                System.arraycopy(array, 0, result, 0, end);
            }
        }
        return result;
    }

    /**
     * 删除索引数组中指定索引位置对应原始数组中的所有元素
     * @param array    目标数组
     * @param indices  索引数组
     * @return
     */
    public static short[] removeAll(short[] array, int[] indices) {
        int length = getLength(array);
        int diff = 0;

        if (isNotEmpty(indices)) {
            Arrays.sort(indices);

            int i = indices.length;
            int prevIndex = length;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                int index = indices[i];
                if ((index < 0) || (index >= length)) {
                    throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
                }
                if (index < prevIndex) {
                    diff++;
                    prevIndex = index;
                }
            }
        }
        short[] result = (short[])(Array.newInstance(array.getClass().getComponentType(), length - diff));
        if (diff < length) {
            int end = length;
            int dest = length - diff;
            for (int i = indices.length - 1; i >= 0; i--) {
                int index = indices[i];
                if (end - index > 1) {
                    int cp = end - index - 1;
                    dest -= cp;
                    System.arraycopy(array, index + 1, result, dest, cp);
                }
                end = index;
            }
            if (end > 0) {
                System.arraycopy(array, 0, result, 0, end);
            }
        }
        return result;
    }

    /**
     * 删除索引数组中指定索引位置对应原始数组中的所有元素
     * @param array    目标数组
     * @param indices  索引数组
     * @return
     */
    public static long[] removeAll(long[] array, int[] indices) {
        int length = getLength(array);
        int diff = 0;

        if (isNotEmpty(indices)) {
            Arrays.sort(indices);

            int i = indices.length;
            int prevIndex = length;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                int index = indices[i];
                if ((index < 0) || (index >= length)) {
                    throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
                }
                if (index < prevIndex) {
                    diff++;
                    prevIndex = index;
                }
            }
        }
        long[] result = (long[])(Array.newInstance(array.getClass().getComponentType(), length - diff));
        if (diff < length) {
            int end = length;
            int dest = length - diff;
            for (int i = indices.length - 1; i >= 0; i--) {
                int index = indices[i];
                if (end - index > 1) {
                    int cp = end - index - 1;
                    dest -= cp;
                    System.arraycopy(array, index + 1, result, dest, cp);
                }
                end = index;
            }
            if (end > 0) {
                System.arraycopy(array, 0, result, 0, end);
            }
        }
        return result;
    }

    /**
     * 删除索引数组中指定索引位置对应原始数组中的所有元素
     * @param array    目标数组
     * @param indices  索引数组
     * @return
     */
    public static float[] removeAll(float[] array, int[] indices) {
        int length = getLength(array);
        int diff = 0;

        if (isNotEmpty(indices)) {
            Arrays.sort(indices);

            int i = indices.length;
            int prevIndex = length;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                int index = indices[i];
                if ((index < 0) || (index >= length)) {
                    throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
                }
                if (index < prevIndex) {
                    diff++;
                    prevIndex = index;
                }
            }
        }
        float[] result = (float[])(Array.newInstance(array.getClass().getComponentType(), length - diff));
        if (diff < length) {
            int end = length;
            int dest = length - diff;
            for (int i = indices.length - 1; i >= 0; i--) {
                int index = indices[i];
                if (end - index > 1) {
                    int cp = end - index - 1;
                    dest -= cp;
                    System.arraycopy(array, index + 1, result, dest, cp);
                }
                end = index;
            }
            if (end > 0) {
                System.arraycopy(array, 0, result, 0, end);
            }
        }
        return result;
    }

    /**
     * 删除索引数组中指定索引位置对应原始数组中的所有元素
     * @param array    目标数组
     * @param indices  索引数组
     * @return
     */
    public static double[] removeAll(double[] array, int[] indices) {
        int length = getLength(array);
        int diff = 0;

        if (isNotEmpty(indices)) {
            Arrays.sort(indices);

            int i = indices.length;
            int prevIndex = length;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                int index = indices[i];
                if ((index < 0) || (index >= length)) {
                    throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
                }
                if (index < prevIndex) {
                    diff++;
                    prevIndex = index;
                }
            }
        }
        double[] result = (double[])(Array.newInstance(array.getClass().getComponentType(), length - diff));
        if (diff < length) {
            int end = length;
            int dest = length - diff;
            for (int i = indices.length - 1; i >= 0; i--) {
                int index = indices[i];
                if (end - index > 1) {
                    int cp = end - index - 1;
                    dest -= cp;
                    System.arraycopy(array, index + 1, result, dest, cp);
                }
                end = index;
            }
            if (end > 0) {
                System.arraycopy(array, 0, result, 0, end);
            }
        }
        return result;
    }

    /**
     * 删除索引数组中指定索引位置对应原始数组中的所有元素
     * @param array    目标数组
     * @param indices  索引数组
     * @return
     */
    public static byte[] removeAll(byte[] array, int[] indices) {
        int length = getLength(array);
        int diff = 0;

        if (isNotEmpty(indices)) {
            Arrays.sort(indices);

            int i = indices.length;
            int prevIndex = length;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                int index = indices[i];
                if ((index < 0) || (index >= length)) {
                    throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
                }
                if (index < prevIndex) {
                    diff++;
                    prevIndex = index;
                }
            }
        }
        byte[] result = (byte[])(Array.newInstance(array.getClass().getComponentType(), length - diff));
        if (diff < length) {
            int end = length;
            int dest = length - diff;
            for (int i = indices.length - 1; i >= 0; i--) {
                int index = indices[i];
                if (end - index > 1) {
                    int cp = end - index - 1;
                    dest -= cp;
                    System.arraycopy(array, index + 1, result, dest, cp);
                }
                end = index;
            }
            if (end > 0) {
                System.arraycopy(array, 0, result, 0, end);
            }
        }
        return result;
    }

    /**
     * 删除索引数组中指定索引位置对应原始数组中的所有元素
     * @param array    目标数组
     * @param indices  索引数组
     * @return
     */
    public static char[] removeAll(char[] array, int[] indices) {
        int length = getLength(array);
        int diff = 0;

        if (isNotEmpty(indices)) {
            Arrays.sort(indices);

            int i = indices.length;
            int prevIndex = length;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                int index = indices[i];
                if ((index < 0) || (index >= length)) {
                    throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
                }
                if (index < prevIndex) {
                    diff++;
                    prevIndex = index;
                }
            }
        }
        char[] result = (char[])(Array.newInstance(array.getClass().getComponentType(), length - diff));
        if (diff < length) {
            int end = length;
            int dest = length - diff;
            for (int i = indices.length - 1; i >= 0; i--) {
                int index = indices[i];
                if (end - index > 1) {
                    int cp = end - index - 1;
                    dest -= cp;
                    System.arraycopy(array, index + 1, result, dest, cp);
                }
                end = index;
            }
            if (end > 0) {
                System.arraycopy(array, 0, result, 0, end);
            }
        }
        return result;
    }

    /**
     * 删除索引数组中指定索引位置对应原始数组中的所有元素
     * @param array    目标数组
     * @param indices  索引数组
     * @return
     */
    public static boolean[] removeAll(boolean[] array, int[] indices) {
        int length = getLength(array);
        int diff = 0;

        if (isNotEmpty(indices)) {
            Arrays.sort(indices);

            int i = indices.length;
            int prevIndex = length;
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                int index = indices[i];
                if ((index < 0) || (index >= length)) {
                    throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
                }
                if (index < prevIndex) {
                    diff++;
                    prevIndex = index;
                }
            }
        }
        boolean[] result = (boolean[])(Array.newInstance(array.getClass().getComponentType(), length - diff));
        if (diff < length) {
            int end = length;
            int dest = length - diff;
            for (int i = indices.length - 1; i >= 0; i--) {
                int index = indices[i];
                if (end - index > 1) {
                    int cp = end - index - 1;
                    dest -= cp;
                    System.arraycopy(array, index + 1, result, dest, cp);
                }
                end = index;
            }
            if (end > 0) {
                System.arraycopy(array, 0, result, 0, end);
            }
        }
        return result;
    }

    /**
     * 数组N等分，最后余数作为一组
     * @param <T>
     * @param arr             目标数组
     * @param subArrayLength  子数组元素个数
     * @return
     */
    public static <T> T[][] splitArray(T[] arr, int subArrayLength) {
        if(null == arr || arr.length == 0) {
            return null;
        }
        if (subArrayLength > getLength(arr)) {
            throw new IllegalArgumentException("subArrayLength: " + subArrayLength + ",array length:" + getLength(arr) + ",subArray length can't be largger than array length.");
        }
        int start = 0;
        int totalLen = getLength(arr);
        int N = (totalLen % subArrayLength == 0)? totalLen / subArrayLength : (totalLen / subArrayLength) + 1;
        T[][] result = (T[][])(Array.newInstance(arr.getClass().getComponentType(), new int[]{N,0}));
        int index = 0;
        while (start <= arr.length - 1) {
            int n = arr.length - start;
            if (n > subArrayLength) {
                T[] dest = (T[])(Array.newInstance(arr.getClass().getComponentType(), subArrayLength));
                System.arraycopy(arr, start, dest, 0, subArrayLength);
                result[index++] = dest;
            } else {
                T[] dest = (T[])(Array.newInstance(arr.getClass().getComponentType(), n));
                System.arraycopy(arr, start, dest, 0, n);
                result[index++] = dest;
            }
            start += subArrayLength;
        }
        return result;
    }

    /**
     * 数组N等分，最后余数作为一组
     * @param arr             目标数组
     * @param subArrayLength  子数组元素个数
     * @return
     */
    public static int[][] splitArray(int[] arr, int subArrayLength) {
        if(null == arr || arr.length == 0) {
            return null;
        }
        if (subArrayLength > getLength(arr)) {
            throw new IllegalArgumentException("subArrayLength: " + subArrayLength + ",array length:" + getLength(arr) + ",subArray length can't be largger than array length.");
        }
        int start = 0;
        int totalLen = getLength(arr);
        int N = (totalLen % subArrayLength == 0)? totalLen / subArrayLength : (totalLen / subArrayLength) + 1;
        int[][] result = new int[N][0];
        int index = 0;
        while (start <= arr.length - 1) {
            int n = arr.length - start;
            if (n > subArrayLength) {
                int[] dest = new int[subArrayLength];
                System.arraycopy(arr, start, dest, 0, subArrayLength);
                result[index++] = dest;
            } else {
                int[] dest = new int[n];
                System.arraycopy(arr, start, dest, 0, n);
                result[index++] = dest;
            }
            start += subArrayLength;
        }
        return result;
    }

    /**
     * 数组N等分，最后余数作为一组
     * @param arr             目标数组
     * @param subArrayLength  子数组元素个数
     * @return
     */
    public static short[][] splitArray(short[] arr, int subArrayLength) {
        if(null == arr || arr.length == 0) {
            return null;
        }
        if (subArrayLength > getLength(arr)) {
            throw new IllegalArgumentException("subArrayLength: " + subArrayLength + ",array length:" + getLength(arr) + ",subArray length can't be largger than array length.");
        }
        int start = 0;
        int totalLen = getLength(arr);
        int N = (totalLen % subArrayLength == 0)? totalLen / subArrayLength : (totalLen / subArrayLength) + 1;
        short[][] result = new short[N][0];
        int index = 0;
        while (start <= arr.length - 1) {
            int n = arr.length - start;
            if (n > subArrayLength) {
                short[] dest = new short[subArrayLength];
                System.arraycopy(arr, start, dest, 0, subArrayLength);
                result[index++] = dest;
            } else {
                short[] dest = new short[n];
                System.arraycopy(arr, start, dest, 0, n);
                result[index++] = dest;
            }
            start += subArrayLength;
        }
        return result;
    }

    /**
     * 数组N等分，最后余数作为一组
     * @param arr             目标数组
     * @param subArrayLength  子数组元素个数
     * @return
     */
    public static long[][] splitArray(long[] arr, int subArrayLength) {
        if(null == arr || arr.length == 0) {
            return null;
        }
        if (subArrayLength > getLength(arr)) {
            throw new IllegalArgumentException("subArrayLength: " + subArrayLength + ",array length:" + getLength(arr) + ",subArray length can't be largger than array length.");
        }
        int start = 0;
        int totalLen = getLength(arr);
        int N = (totalLen % subArrayLength == 0)? totalLen / subArrayLength : (totalLen / subArrayLength) + 1;
        long[][] result = new long[N][0];
        int index = 0;
        while (start <= arr.length - 1) {
            int n = arr.length - start;
            if (n > subArrayLength) {
                long[] dest = new long[subArrayLength];
                System.arraycopy(arr, start, dest, 0, subArrayLength);
                result[index++] = dest;
            } else {
                long[] dest = new long[n];
                System.arraycopy(arr, start, dest, 0, n);
                result[index++] = dest;
            }
            start += subArrayLength;
        }
        return result;
    }

    /**
     * 数组N等分，最后余数作为一组
     * @param arr             目标数组
     * @param subArrayLength  子数组元素个数
     * @return
     */
    public static double[][] splitArray(double[] arr, int subArrayLength) {
        if(null == arr || arr.length == 0) {
            return null;
        }
        if (subArrayLength > getLength(arr)) {
            throw new IllegalArgumentException("subArrayLength: " + subArrayLength + ",array length:" + getLength(arr) + ",subArray length can't be largger than array length.");
        }
        int start = 0;
        int totalLen = getLength(arr);
        int N = (totalLen % subArrayLength == 0)? totalLen / subArrayLength : (totalLen / subArrayLength) + 1;
        double[][] result = new double[N][0];
        int index = 0;
        while (start <= arr.length - 1) {
            int n = arr.length - start;
            if (n > subArrayLength) {
                double[] dest = new double[subArrayLength];
                System.arraycopy(arr, start, dest, 0, subArrayLength);
                result[index++] = dest;
            } else {
                double[] dest = new double[n];
                System.arraycopy(arr, start, dest, 0, n);
                result[index++] = dest;
            }
            start += subArrayLength;
        }
        return result;
    }

    /**
     * 数组N等分，最后余数作为一组
     * @param arr             目标数组
     * @param subArrayLength  子数组元素个数
     * @return
     */
    public static float[][] splitArray(float[] arr, int subArrayLength) {
        if(null == arr || arr.length == 0) {
            return null;
        }
        if (subArrayLength > getLength(arr)) {
            throw new IllegalArgumentException("subArrayLength: " + subArrayLength + ",array length:" + getLength(arr) + ",subArray length can't be largger than array length.");
        }
        int start = 0;
        int totalLen = getLength(arr);
        int N = (totalLen % subArrayLength == 0)? totalLen / subArrayLength : (totalLen / subArrayLength) + 1;
        float[][] result = new float[N][0];
        int index = 0;
        while (start <= arr.length - 1) {
            int n = arr.length - start;
            if (n > subArrayLength) {
                float[] dest = new float[subArrayLength];
                System.arraycopy(arr, start, dest, 0, subArrayLength);
                result[index++] = dest;
            } else {
                float[] dest = new float[n];
                System.arraycopy(arr, start, dest, 0, n);
                result[index++] = dest;
            }
            start += subArrayLength;
        }
        return result;
    }

    /**
     * 数组N等分，最后余数作为一组
     * @param arr             目标数组
     * @param subArrayLength  子数组元素个数
     * @return
     */
    public static byte[][] splitArray(byte[] arr, int subArrayLength) {
        if(null == arr || arr.length == 0) {
            return null;
        }
        if (subArrayLength > getLength(arr)) {
            throw new IllegalArgumentException("subArrayLength: " + subArrayLength + ",array length:" + getLength(arr) + ",subArray length can't be largger than array length.");
        }
        int start = 0;
        int totalLen = getLength(arr);
        int N = (totalLen % subArrayLength == 0)? totalLen / subArrayLength : (totalLen / subArrayLength) + 1;
        byte[][] result = new byte[N][0];
        int index = 0;
        while (start <= arr.length - 1) {
            int n = arr.length - start;
            if (n > subArrayLength) {
                byte[] dest = new byte[subArrayLength];
                System.arraycopy(arr, start, dest, 0, subArrayLength);
                result[index++] = dest;
            } else {
                byte[] dest = new byte[n];
                System.arraycopy(arr, start, dest, 0, n);
                result[index++] = dest;
            }
            start += subArrayLength;
        }
        return result;
    }

    /**
     * 数组N等分，最后余数作为一组
     * @param arr             目标数组
     * @param subArrayLength  子数组元素个数
     * @return
     */
    public static char[][] splitArray(char[] arr, int subArrayLength) {
        if(null == arr || arr.length == 0) {
            return null;
        }
        if (subArrayLength > getLength(arr)) {
            throw new IllegalArgumentException("subArrayLength: " + subArrayLength + ",array length:" + getLength(arr) + ",subArray length can't be largger than array length.");
        }
        int start = 0;
        int totalLen = getLength(arr);
        int N = (totalLen % subArrayLength == 0)? totalLen / subArrayLength : (totalLen / subArrayLength) + 1;
        char[][] result = new char[N][0];
        int index = 0;
        while (start <= arr.length - 1) {
            int n = arr.length - start;
            if (n > subArrayLength) {
                char[] dest = new char[subArrayLength];
                System.arraycopy(arr, start, dest, 0, subArrayLength);
                result[index++] = dest;
            } else {
                char[] dest = new char[n];
                System.arraycopy(arr, start, dest, 0, n);
                result[index++] = dest;
            }
            start += subArrayLength;
        }
        return result;
    }

    /**
     * 数组N等分，最后余数作为一组
     * @param arr             目标数组
     * @param subArrayLength  子数组元素个数
     * @return
     */
    public static boolean[][] splitArray(boolean[] arr, int subArrayLength) {
        if(null == arr || arr.length == 0) {
            return null;
        }
        if (subArrayLength > getLength(arr)) {
            throw new IllegalArgumentException("subArrayLength: " + subArrayLength + ",array length:" + getLength(arr) + ",subArray length can't be largger than array length.");
        }
        int start = 0;
        int totalLen = getLength(arr);
        int N = (totalLen % subArrayLength == 0)? totalLen / subArrayLength : (totalLen / subArrayLength) + 1;
        boolean[][] result = new boolean[N][0];
        int index = 0;
        while (start <= arr.length - 1) {
            int n = arr.length - start;
            if (n > subArrayLength) {
                boolean[] dest = new boolean[subArrayLength];
                System.arraycopy(arr, start, dest, 0, subArrayLength);
                result[index++] = dest;
            } else {
                boolean[] dest = new boolean[n];
                System.arraycopy(arr, start, dest, 0, n);
                result[index++] = dest;
            }
            start += subArrayLength;
        }
        return result;
    }

    /**
     * 数组N等分，最后余数作为一组
     * @param <T>
     * @param arr   目标数组
     * @param N     分成几份，N表示总份数
     * @return
     */
    public static <T> T[][] splitArrayN(T[] arr,int N) {
        if(null == arr || arr.length == 0) {
            return null;
        }
        if (N > getLength(arr)) {
            throw new IllegalArgumentException("N: " + N + ",array length:" + getLength(arr) + ",N can't be largger than array length.");
        }
        int totalLen = getLength(arr);
        int subArrayLen = (totalLen % N == 0)? totalLen / N : (totalLen / N) + 1;
        return splitArray(arr, subArrayLen);
    }

    /**
     * 数组N等分，最后余数作为一组
     * @param <T>
     * @param arr   目标数组
     * @param N     分成几份，N表示总份数
     * @return
     */
    public static int[][] splitArrayN(int[] arr,int N) {
        if(null == arr || arr.length == 0) {
            return null;
        }
        if (N > getLength(arr)) {
            throw new IllegalArgumentException("N: " + N + ",array length:" + getLength(arr) + ",N can't be largger than array length.");
        }
        int totalLen = getLength(arr);
        int subArrayLen = (totalLen % N == 0)? totalLen / N : (totalLen / N) + 1;
        return splitArray(arr, subArrayLen);
    }

    /**
     * 数组N等分，最后余数作为一组
     * @param <T>
     * @param arr   目标数组
     * @param N     分成几份，N表示总份数
     * @return
     */
    public static short[][] splitArrayN(short[] arr,int N) {
        if(null == arr || arr.length == 0) {
            return null;
        }
        if (N > getLength(arr)) {
            throw new IllegalArgumentException("N: " + N + ",array length:" + getLength(arr) + ",N can't be largger than array length.");
        }
        int totalLen = getLength(arr);
        int subArrayLen = (totalLen % N == 0)? totalLen / N : (totalLen / N) + 1;
        return splitArray(arr, subArrayLen);
    }

    /**
     * 数组N等分，最后余数作为一组
     * @param <T>
     * @param arr   目标数组
     * @param N     分成几份，N表示总份数
     * @return
     */
    public static long[][] splitArrayN(long[] arr,int N) {
        if(null == arr || arr.length == 0) {
            return null;
        }
        if (N > getLength(arr)) {
            throw new IllegalArgumentException("N: " + N + ",array length:" + getLength(arr) + ",N can't be largger than array length.");
        }
        int totalLen = getLength(arr);
        int subArrayLen = (totalLen % N == 0)? totalLen / N : (totalLen / N) + 1;
        return splitArray(arr, subArrayLen);
    }

    /**
     * 数组N等分，最后余数作为一组
     * @param <T>
     * @param arr   目标数组
     * @param N     分成几份，N表示总份数
     * @return
     */
    public static float[][] splitArrayN(float[] arr,int N) {
        if(null == arr || arr.length == 0) {
            return null;
        }
        if (N > getLength(arr)) {
            throw new IllegalArgumentException("N: " + N + ",array length:" + getLength(arr) + ",N can't be largger than array length.");
        }
        int totalLen = getLength(arr);
        int subArrayLen = (totalLen % N == 0)? totalLen / N : (totalLen / N) + 1;
        return splitArray(arr, subArrayLen);
    }

    /**
     * 数组N等分，最后余数作为一组
     * @param <T>
     * @param arr   目标数组
     * @param N     分成几份，N表示总份数
     * @return
     */
    public static double[][] splitArrayN(double[] arr,int N) {
        if(null == arr || arr.length == 0) {
            return null;
        }
        if (N > getLength(arr)) {
            throw new IllegalArgumentException("N: " + N + ",array length:" + getLength(arr) + ",N can't be largger than array length.");
        }
        int totalLen = getLength(arr);
        int subArrayLen = (totalLen % N == 0)? totalLen / N : (totalLen / N) + 1;
        return splitArray(arr, subArrayLen);
    }

    /**
     * 数组N等分，最后余数作为一组
     * @param <T>
     * @param arr   目标数组
     * @param N     分成几份，N表示总份数
     * @return
     */
    public static byte[][] splitArrayN(byte[] arr,int N) {
        if(null == arr || arr.length == 0) {
            return null;
        }
        if (N > getLength(arr)) {
            throw new IllegalArgumentException("N: " + N + ",array length:" + getLength(arr) + ",N can't be largger than array length.");
        }
        int totalLen = getLength(arr);
        int subArrayLen = (totalLen % N == 0)? totalLen / N : (totalLen / N) + 1;
        return splitArray(arr, subArrayLen);
    }

    /**
     * 数组N等分，最后余数作为一组
     * @param <T>
     * @param arr   目标数组
     * @param N     分成几份，N表示总份数
     * @return
     */
    public static char[][] splitArrayN(char[] arr,int N) {
        if(null == arr || arr.length == 0) {
            return null;
        }
        if (N > getLength(arr)) {
            throw new IllegalArgumentException("N: " + N + ",array length:" + getLength(arr) + ",N can't be largger than array length.");
        }
        int totalLen = getLength(arr);
        int subArrayLen = (totalLen % N == 0)? totalLen / N : (totalLen / N) + 1;
        return splitArray(arr, subArrayLen);
    }

    /**
     * 数组N等分，最后余数作为一组
     * @param <T>
     * @param arr   目标数组
     * @param N     分成几份，N表示总份数
     * @return
     */
    public static boolean[][] splitArrayN(boolean[] arr,int N) {
        if(null == arr || arr.length == 0) {
            return null;
        }
        if (N > getLength(arr)) {
            throw new IllegalArgumentException("N: " + N + ",array length:" + getLength(arr) + ",N can't be largger than array length.");
        }
        int totalLen = getLength(arr);
        int subArrayLen = (totalLen % N == 0)? totalLen / N : (totalLen / N) + 1;
        return splitArray(arr, subArrayLen);
    }

    /**
     * 数组复制并且长度加N
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    public static int[] copyArrayGrowN(int[] array, Class<?> newArrayComponentType,int N) {
        return (int[])copyArrayAndGrowN(array, newArrayComponentType, N);
    }

    /**
     * 数组复制并且长度加N
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    public static short[] copyArrayGrowN(short[] array, Class<?> newArrayComponentType,int N) {
        return (short[])copyArrayAndGrowN(array, newArrayComponentType, N);
    }

    /**
     * 数组复制并且长度加N
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    public static long[] copyArrayGrowN(long[] array, Class<?> newArrayComponentType,int N) {
        return (long[])copyArrayAndGrowN(array, newArrayComponentType, N);
    }

    /**
     * 数组复制并且长度加N
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    public static float[] copyArrayGrowN(float[] array, Class<?> newArrayComponentType,int N) {
        return (float[])copyArrayAndGrowN(array, newArrayComponentType, N);
    }

    /**
     * 数组复制并且长度加N
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    public static double[] copyArrayGrowN(double[] array, Class<?> newArrayComponentType,int N) {
        return (double[])copyArrayAndGrowN(array, newArrayComponentType, N);
    }

    /**
     * 数组复制并且长度加N
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    public static byte[] copyArrayGrowN(byte[] array, Class<?> newArrayComponentType,int N) {
        return (byte[])copyArrayAndGrowN(array, newArrayComponentType, N);
    }

    /**
     * 数组复制并且长度加N
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    public static char[] copyArrayGrowN(char[] array, Class<?> newArrayComponentType,int N) {
        return (char[])copyArrayAndGrowN(array, newArrayComponentType, N);
    }

    /**
     * 数组复制并且长度加N
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    public static boolean[] copyArrayGrowN(boolean[] array, Class<?> newArrayComponentType,int N) {
        return (boolean[])copyArrayAndGrowN(array, newArrayComponentType, N);
    }

    /**
     * 数组复制并且长度加N
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    public static int[] copyArrayGrowN(int[] array,int N) {
        return (int[])copyArrayAndGrowN(array, null, N);
    }

    /**
     * 数组复制并且长度加N
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    public static short[] copyArrayGrowN(short[] array,int N) {
        return (short[])copyArrayAndGrowN(array,null, N);
    }

    /**
     * 数组复制并且长度加N
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    public static long[] copyArrayGrowN(long[] array,int N) {
        return (long[])copyArrayAndGrowN(array, null, N);
    }

    /**
     * 数组复制并且长度加N
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    public static float[] copyArrayGrowN(float[] array,int N) {
        return (float[])copyArrayAndGrowN(array, null, N);
    }

    /**
     * 数组复制并且长度加N
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    public static double[] copyArrayGrowN(double[] array,int N) {
        return (double[])copyArrayAndGrowN(array, null, N);
    }

    /**
     * 数组复制并且长度加N
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    public static byte[] copyArrayGrowN(byte[] array,int N) {
        return (byte[])copyArrayAndGrowN(array, null, N);
    }

    /**
     * 数组复制并且长度加N
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    public static char[] copyArrayGrowN(char[] array,int N) {
        return (char[])copyArrayAndGrowN(array, null, N);
    }

    /**
     * 数组复制并且长度加N
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    public static boolean[] copyArrayGrowN(boolean[] array,int N) {
        return (boolean[])copyArrayAndGrowN(array, null, N);
    }

    /**
     * 数组复制并且长度加N
     * @param <T>
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    public static <T> T[] copyArrayGrowN(T[] array, Class<?> newArrayComponentType,int N) {
        if (array != null) {
            int arrayLength = Array.getLength(array);
            T[] newArray = (T[])Array.newInstance(array.getClass().getComponentType(), arrayLength + N);
            System.arraycopy(array, 0, newArray, 0, arrayLength);
            return newArray;
        }
        return (T[])Array.newInstance(newArrayComponentType, N);
    }

    /**
     * 数组复制并且长度加N
     * @param <T>
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    public static <T> T[] copyArrayGrowN(T[] array,int N) {
        return copyArrayGrowN(array, null, N);
    }

    /**
     * 数组打印
     * @param <T>
     * @param array
     */
    public static <T> void printArray(T[] array) {
        if(null == array || array.length == 0) {
            return;
        }
        System.out.print("[");
        int index = 0;
        for (T t : array) {
            if(index == array.length - 1) {
                System.out.print(t);
            } else {
                System.out.print(t + ",");
            }
            index++;
        }
        System.out.print("]");
    }

    /**
     * 数组打印
     * @param <T>
     * @param array
     */
    public static void printArray(int[] array) {
        if(null == array || array.length == 0) {
            return;
        }
        System.out.print("[");
        int index = 0;
        for (int t : array) {
            if(index == array.length - 1) {
                System.out.print(t);
            } else {
                System.out.print(t + ",");
            }
            index++;
        }
        System.out.print("]");
    }

    /**
     * 数组打印
     * @param <T>
     * @param array
     */
    public static void printArray(short[] array) {
        if(null == array || array.length == 0) {
            return;
        }
        System.out.print("[");
        int index = 0;
        for (short t : array) {
            if(index == array.length - 1) {
                System.out.print(t);
            } else {
                System.out.print(t + ",");
            }
            index++;
        }
        System.out.print("]");
    }

    /**
     * 数组打印
     * @param <T>
     * @param array
     */
    public static void printArray(long[] array) {
        if(null == array || array.length == 0) {
            return;
        }
        System.out.print("[");
        int index = 0;
        for (long t : array) {
            if(index == array.length - 1) {
                System.out.print(t);
            } else {
                System.out.print(t + ",");
            }
            index++;
        }
        System.out.print("]");
    }

    /**
     * 数组打印
     * @param <T>
     * @param array
     */
    public static void printArray(float[] array) {
        if(null == array || array.length == 0) {
            return;
        }
        System.out.print("[");
        int index = 0;
        for (float t : array) {
            if(index == array.length - 1) {
                System.out.print(t);
            } else {
                System.out.print(t + ",");
            }
            index++;
        }
        System.out.print("]");
    }

    /**
     * 数组打印
     * @param <T>
     * @param array
     */
    public static void printArray(double[] array) {
        if(null == array || array.length == 0) {
            return;
        }
        System.out.print("[");
        int index = 0;
        for (double t : array) {
            if(index == array.length - 1) {
                System.out.print(t);
            } else {
                System.out.print(t + ",");
            }
            index++;
        }
        System.out.print("]");
    }

    /**
     * 数组打印
     * @param <T>
     * @param array
     */
    public static void printArray(byte[] array) {
        if(null == array || array.length == 0) {
            return;
        }
        System.out.print("[");
        int index = 0;
        for (byte t : array) {
            if(index == array.length - 1) {
                System.out.print(t);
            } else {
                System.out.print(t + ",");
            }
            index++;
        }
        System.out.print("]");
    }

    /**
     * 数组打印
     * @param <T>
     * @param array
     */
    public static void printArray(char[] array) {
        if(null == array || array.length == 0) {
            return;
        }
        System.out.print("[");
        int index = 0;
        for (char t : array) {
            if(index == array.length - 1) {
                System.out.print(t);
            } else {
                System.out.print(t + ",");
            }
            index++;
        }
        System.out.print("]");
    }

    /**
     * 数组打印
     * @param <T>
     * @param array
     */
    public static void printArray(boolean[] array) {
        if(null == array || array.length == 0) {
            return;
        }
        System.out.print("[");
        int index = 0;
        for (boolean t : array) {
            if(index == array.length - 1) {
                System.out.print(t);
            } else {
                System.out.print(t + ",");
            }
            index++;
        }
        System.out.print("]");
    }

    /**
     * Map.Entry/Array转换成Map的Key-Value结构
     *
     * @param array
     * @return
     */
    public static Map<Object, Object> toMap(Object[] array) {
        if (array == null || array.length == 0) {
            return null;
        }
        Map<Object, Object> map = new HashMap<Object, Object>((int) (array.length * 1.5D));
        for (int i = 0; i < array.length; i++) {
            Object object = array[i];
            if ((object instanceof Map.Entry)) {
                Map.Entry entry = (Map.Entry) object;
                map.put(entry.getKey(), entry.getValue());
            } else if ((object instanceof Object[])) {
                Object[] entry = (Object[]) object;
                if (entry.length < 2) {
                    throw new IllegalArgumentException("Array element " + i + ", '" + object + "', has a length less than 2");
                }
                map.put(entry[0], entry[1]);
            } else {
                throw new IllegalArgumentException("Array element " + i + ", '" + object + "', is neither of type Map.Entry nor an Array");
            }
        }
        return map;
    }

    /**
     * 数组转换成List
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static <T> List<T> toList(T[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        return Arrays.asList(array);
    }

    /**
     * 数组转换成List
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static List<Integer> toList(int[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        List<Integer> list = new ArrayList<Integer>();
        for(int i=0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }

    /**
     * 数组转换成List
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static List<Short> toList(short[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        List<Short> list = new ArrayList<Short>();
        for(int i=0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }

    /**
     * 数组转换成List
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static List<Long> toList(long[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        List<Long> list = new ArrayList<Long>();
        for(int i=0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }

    /**
     * 数组转换成List
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static List<Float> toList(float[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        List<Float> list = new ArrayList<Float>();
        for(int i=0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }

    /**
     * 数组转换成List
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static List<Double> toList(double[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        List<Double> list = new ArrayList<Double>();
        for(int i=0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }

    /**
     * 数组转换成List
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static List<Byte> toList(byte[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        List<Byte> list = new ArrayList<Byte>();
        for(int i=0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }

    /**
     * 数组转换成List
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static List<Character> toList(char[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        List<Character> list = new ArrayList<Character>();
        for(int i=0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }

    /**
     * 数组转换成List
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static List<Boolean> toList(boolean[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        List<Boolean> list = new ArrayList<Boolean>();
        for(int i=0; i < array.length; i++) {
            list.add(array[i]);
        }
        return list;
    }

    /**
     * 数组转换成HashSet
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static <T> Set<T> toHashSet(T[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        return new HashSet<T>(Arrays.asList(array));
    }

    /**
     * 数组转换成HashSet
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static Set<Integer> toHashSet(int[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        Set<Integer> set = new HashSet<Integer>();
        for(int i=0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set;
    }

    /**
     * 数组转换成HashSet
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static Set<Short> toHashSet(short[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        Set<Short> set = new HashSet<Short>();
        for(int i=0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set;
    }

    /**
     * 数组转换成HashSet
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static Set<Long> toHashSet(long[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        Set<Long> set = new HashSet<Long>();
        for(int i=0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set;
    }

    /**
     * 数组转换成HashSet
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static Set<Float> toHashSet(float[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        Set<Float> set = new HashSet<Float>();
        for(int i=0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set;
    }

    /**
     * 数组转换成HashSet
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static Set<Double> toHashSet(double[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        Set<Double> set = new HashSet<Double>();
        for(int i=0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set;
    }

    /**
     * 数组转换成HashSet
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static Set<Byte> toHashSet(byte[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        Set<Byte> set = new HashSet<Byte>();
        for(int i=0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set;
    }

    /**
     * 数组转换成HashSet
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static Set<Character> toHashSet(char[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        Set<Character> set = new HashSet<Character>();
        for(int i=0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set;
    }

    /**
     * 数组转换成HashSet
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static Set<Boolean> toHashSet(boolean[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        Set<Boolean> set = new HashSet<Boolean>();
        for(int i=0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set;
    }

    /**
     * 数组转换成LinkedHashSet
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static <T> Set<T> toLinkedSet(T[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        return new LinkedHashSet<T>(Arrays.asList(array));
    }

    /**
     * 数组转换成LinkedHashSet
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static Set<Integer> toLinkedSet(int[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        Set<Integer> set = new LinkedHashSet<Integer>();
        for(int i=0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set;
    }

    /**
     * 数组转换成LinkedHashSet
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static Set<Short> toLinkedSet(short[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        Set<Short> set = new LinkedHashSet<Short>();
        for(int i=0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set;
    }

    /**
     * 数组转换成LinkedHashSet
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static Set<Long> toLinkedSet(long[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        Set<Long> set = new LinkedHashSet<Long>();
        for(int i=0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set;
    }

    /**
     * 数组转换成LinkedHashSet
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static Set<Float> toLinkedSet(float[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        Set<Float> set = new LinkedHashSet<Float>();
        for(int i=0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set;
    }

    /**
     * 数组转换成LinkedHashSet
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static Set<Double> toLinkedSet(double[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        Set<Double> set = new LinkedHashSet<Double>();
        for(int i=0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set;
    }

    /**
     * 数组转换成LinkedHashSet
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static Set<Byte> toLinkedSet(byte[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        Set<Byte> set = new LinkedHashSet<Byte>();
        for(int i=0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set;
    }

    /**
     * 数组转换成LinkedHashSet
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static Set<Character> toLinkedSet(char[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        Set<Character> set = new LinkedHashSet<Character>();
        for(int i=0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set;
    }

    /**
     * 数组转换成LinkedHashSet
     * @param <T>
     * @param array    目标数组
     * @return
     */
    public static Set<Boolean> toLinkedSet(boolean[] array) {
        if(null == array || array.length == 0) {
            return null;
        }
        Set<Boolean> set = new LinkedHashSet<Boolean>();
        for(int i=0; i < array.length; i++) {
            set.add(array[i]);
        }
        return set;
    }

    /**
     * 去除数组中重复元素
     * @param array
     * @return
     */
    public static Object[] arrayDelRepeat(Object[] array) {
        List<Object> list = new LinkedList<Object>();
        for(int i = 0; i < array.length; i++) {
            if(!list.contains(array[i])) {
                list.add(array[i]);
            }
        }
        return list.toArray();
    }

    /**
     * 将数组中所有元素按照指定的连接符拼接成一个字符串
     * 如果不指定连接符，默认设置为英文逗号,
     * @param array      数组对象
     * @param delimiter  连接符
     * @return
     */
    public static String joinArray(Object[] array,String delimiter) {
        if(isEmpty(array)) {
            return null;
        }
        if(null == delimiter || delimiter.length() == 0){
            delimiter = ",";
        }
        StringBuffer text = new StringBuffer();
        for (Object element : array) {
            String str = "";
            if(null != element) {
                str = element.toString();
            }
            text.append(str).append(delimiter);
        }
        return text.toString().replaceAll(delimiter + "$", "");
    }

    /**
     * 将数组中所有元素按照指定的连接符拼接成一个字符串(重载)
     * 如果不指定连接符，默认设置为英文逗号,
     * @param array      数组对象
     * @param delimiter  连接符
     * @return
     */
    public static String joinArray(Object[] array) {
        return joinArray(array, null);
    }

    /**
     * 往数组指定索引位置添加一个元素
     *
     * @param array
     *            目标数组
     * @param index
     *            指定添加元素的索引位置
     * @param element
     *            待添加的元素
     * @return
     */
    private static Object add(Object array, int index, Object element, Class<?> clss) {
        if (array == null) {
            if (index != 0) {
                throw new IndexOutOfBoundsException("Index: " + index + ", Length: 0");
            }
            Object joinedArray = Array.newInstance(clss, 1);
            Array.set(joinedArray, 0, element);
            return joinedArray;
        }
        int length = Array.getLength(array);
        if ((index > length) || (index < 0)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }
        Object result = Array.newInstance(clss, length + 1);
        System.arraycopy(array, 0, result, 0, index);
        Array.set(result, index, element);
        if (index < length) {
            System.arraycopy(array, index, result, index + 1, length - index);
        }
        return result;
    }

    /**
     * 删除数组指定索引位置的元素 <br/>
     * 注意：不会修改原始数组，而是先copy一个数组副本，从副本中删除，最后返回该数组副本
     * @param <T>
     *
     * @param array
     * @param index
     * @return
     */
    private static <T> T[] remove(T[] array, int index) {
        int length = getLength(array);
        if ((index < 0) || (index >= length)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }

        T[] result = (T[])Array.newInstance(array.getClass().getComponentType(), length - 1);
        System.arraycopy(array, 0, result, 0, index);
        if (index < length - 1) {
            System.arraycopy(array, index + 1, result, index, length - index - 1);
        }
        return result;
    }

    /**
     * 删除数组指定索引位置的元素 <br/>
     * 注意：不会修改原始数组，而是先copy一个数组副本，从副本中删除，最后返回该数组副本
     * @param <T>
     *
     * @param array
     * @param index
     * @return
     */
    private static Object removeByIndex(Object array, int index) {
        int length = getLength(array);
        if ((index < 0) || (index >= length)) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Length: " + length);
        }

        Object result = Array.newInstance(array.getClass().getComponentType(), length - 1);
        System.arraycopy(array, 0, result, 0, index);
        if (index < length - 1) {
            System.arraycopy(array, index + 1, result, index, length - index - 1);
        }
        return result;
    }

    /**
     * 数组复制并且长度加1
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @return
     */
    private static Object copyArrayGrow1(Object array, Class<?> newArrayComponentType) {
        if (array != null) {
            int arrayLength = Array.getLength(array);
            Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + 1);
            System.arraycopy(array, 0, newArray, 0, arrayLength);
            return newArray;
        }
        return Array.newInstance(newArrayComponentType, 1);
    }

    /**
     * 数组复制并且长度加N
     *
     * @param array
     *            原始数组
     * @param newArrayComponentType
     *            新数组类型
     * @param N
     *            数组长度扩容大小
     * @return
     */
    private static Object copyArrayAndGrowN(Object array, Class<?> newArrayComponentType,int N) {
        if (array != null) {
            int arrayLength = Array.getLength(array);
            Object newArray = Array.newInstance(array.getClass().getComponentType(), arrayLength + N);
            System.arraycopy(array, 0, newArray, 0, arrayLength);
            return newArray;
        }
        return Array.newInstance(newArrayComponentType, N);
    }
}