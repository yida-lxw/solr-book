package com.yida.solr.book.examples.utils;

import java.lang.reflect.Type;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * Json工具类
 * 
 * @author Lanxiaowei
 * 
 */
@SuppressWarnings("rawtypes")
public class JSONUtils {
	/** 空的 {@code JSON} 数据 - <code>"{}"</code>。 */
	public static final String EMPTY_JSON = "{}";
	/** 空的 {@code JSON} 数组(集合)数据 - {@code "[]"}。 */
	public static final String EMPTY_JSON_ARRAY = "[]";
	/** 默认的 {@code JSON} 日期/时间字段的格式化模式。 */
	public static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";
	/** {@code Google Gson} 的 <code>@Since</code> 注解常用的版本号常量 - {@code 1.0}。 */
	public static final double SINCE_VERSION_10 = 1.0d;
	/** {@code Google Gson} 的 <code>@Since</code> 注解常用的版本号常量 - {@code 1.1}。 */
	public static final double SINCE_VERSION_11 = 1.1d;
	/** {@code Google Gson} 的 <code>@Since</code> 注解常用的版本号常量 - {@code 1.2}。 */
	public static final double SINCE_VERSION_12 = 1.2d;
	/** {@code Google Gson} 的 <code>@Until</code> 注解常用的版本号常量 - {@code 1.0}。 */
	public static final double UNTIL_VERSION_10 = SINCE_VERSION_10;
	/** {@code Google Gson} 的 <code>@Until</code> 注解常用的版本号常量 - {@code 1.1}。 */
	public static final double UNTIL_VERSION_11 = SINCE_VERSION_11;
	/** {@code Google Gson} 的 <code>@Until</code> 注解常用的版本号常量 - {@code 1.2}。 */
	public static final double UNTIL_VERSION_12 = SINCE_VERSION_12;

	private JSONUtils() {}

	/**
	 * 
	 * 将给定的目标对象根据指定的条件参数转换成 {@code JSON} 格式的字符串。
	 * <strong>该方法转换发生错误时，不会抛出任何异常。若发生错误时，曾通对象返回 <code>"{}"</code>； 集合或数组对象返回
	 * </strong>
	 * @param target
	 *            目标对象
	 * @param targetType
	 *            目标对象的类型
	 * @param isSerializeNulls
	 *            是否序列化 {@code null} 值字段
	 * @param version
	 *            字段的版本号注解
	 * @param datePattern
	 *            日期字段的格式化模式
	 * @param excludesFieldsWithoutExpose
	 *            是否排除未标注 {@literal @Expose} 注解的字段
	 * @return 目标对象的 {@code JSON} 格式的字符串
	 * @since 1.0
	 */
	public static String toJson(Object target, Type targetType,
			boolean isSerializeNulls, Double version,
			String datePattern, boolean excludesFieldsWithoutExpose) {
		if (target == null){
			return EMPTY_JSON;
		}
		GsonBuilder builder = new GsonBuilder();
		if (isSerializeNulls){
			builder.serializeNulls();
		}
		if (version != null){
			builder.setVersion(version.doubleValue());
		}
		if (StringUtils.isBlank(datePattern)){
			datePattern = DEFAULT_DATE_PATTERN;
		}
		builder.setDateFormat(datePattern);
		if (excludesFieldsWithoutExpose){
			builder.excludeFieldsWithoutExposeAnnotation();
		}
		return toJson(target, targetType, builder);
	}

	public static String toJson(Object target, Class clazz) {
		return toJson(target, clazz, false, SINCE_VERSION_10, null, false);
	}

	public static String toJson(Object target) {
		return toJson(target, target.getClass(), false, SINCE_VERSION_10, null,false);
	}

	/**
	 * 
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * </ul>
	 * @param target
	 *            要转换成 {@code JSON} 的目标对象。
	 * @param datePattern  日期字段的格式化模式。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, String datePattern) {
		return toJson(target, null, false, null, datePattern, true);
	}

	/**
	 * 
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * @param target  要转换成 {@code JSON} 的目标对象。
	 * @param version  字段的版本号注解({@literal @Since})。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Double version) {
		return toJson(target, null, false, version, null, true);
	}

	/**
	 * 
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * @param target  要转换成 {@code JSON} 的目标对象。
	 * @param excludesFieldsWithoutExpose  是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串
	 * @since 1.0
	 */
	public static String toJson(Object target,boolean excludesFieldsWithoutExpose) {
		return toJson(target, null, false, null, null,excludesFieldsWithoutExpose);
	}

	/**
	 * 
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法只用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * @param target 要转换成 {@code JSON} 的目标对象。
	 * @param version  字段的版本号注解({@literal @Since})。
	 * @param excludesFieldsWithoutExpose  是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Double version,boolean excludesFieldsWithoutExpose) {
		return toJson(target, null, false, version, null,excludesFieldsWithoutExpose);
	}

	/**
	 * 
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSSS}；</li>
	 * </ul>
	 * @param target  要转换成 {@code JSON} 的目标对象。
	 * @param targetType  目标对象的类型。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Type targetType) {
		return toJson(target, targetType, false, null, null, true);
	}

	/**
	 * 
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法只会转换标有 {@literal @Expose} 注解的字段；</li>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSSS}；</li>
	 * </ul>
	 * @param target  要转换成 {@code JSON} 的目标对象。
	 * @param targetType  目标对象的类型。
	 * @param version  字段的版本号注解({@literal @Since})。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Type targetType, Double version) {
		return toJson(target, targetType, false, version, null, true);
	}

	/**
	 * 
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法会转换所有未标注或已标注 {@literal @Since} 的字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * @param target  要转换成 {@code JSON} 的目标对象。
	 * @param targetType  目标对象的类型。
	 * @param excludesFieldsWithoutExpose  是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Type targetType,boolean excludesFieldsWithoutExpose) {
		return toJson(target, targetType, false, null, null,excludesFieldsWithoutExpose);
	}

	/**
	 * 
	 * 将给定的目标对象转换成 {@code JSON} 格式的字符串。<strong>此方法通常用来转换使用泛型的对象。</strong>
	 * <ul>
	 * <li>该方法不会转换 {@code null} 值字段；</li>
	 * <li>该方法转换时使用默认的 日期/时间 格式化模式 - {@code yyyy-MM-dd HH:mm:ss SSS}；</li>
	 * </ul>
	 * @param target  要转换成 {@code JSON} 的目标对象。
	 * @param targetType  目标对象的类型。
	 * @param version  字段的版本号注解({@literal @Since})。
	 * @param excludesFieldsWithoutExpose  是否排除未标注 {@literal @Expose} 注解的字段。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.0
	 */
	public static String toJson(Object target, Type targetType, Double version,boolean excludesFieldsWithoutExpose) {
		return toJson(target, targetType, false, version, null,excludesFieldsWithoutExpose);
	}

	/**
	 * 
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 * @param <T>
	 *            要转换的目标类型。
	 * @param json
	 *            给定的 {@code JSON} 字符串。
	 * @param token
	 *            {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @param datePattern
	 *            日期格式模式。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 * @since 1.0
	 */
	public static <T> T fromJson(String json, TypeToken<T> token,String datePattern) {
		if (StringUtils.isBlank(json)) {
			return null;
		}
		GsonBuilder builder = new GsonBuilder();
		if (StringUtils.isBlank(datePattern)) {
			datePattern = DEFAULT_DATE_PATTERN;
		}
		Gson gson = builder.create();
		try {
			return gson.fromJson(json, token.getType());
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。
	 * @param <T>   要转换的目标类型。
	 * @param json  给定的 {@code JSON} 字符串。
	 * @param token {@code com.google.gson.reflect.TypeToken} 的类型指示类对象。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 * @since 1.0
	 */
	public static <T> T fromJson(String json, TypeToken<T> token) {
		return fromJson(json, token, null);
	}

	/**
	 * 
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * @param <T>  要转换的目标类型。
	 * @param json  给定的 {@code JSON} 字符串。
	 * @param clazz  要转换的目标类。
	 * @param datePattern  日期格式模式。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 * @since 1.0
	 */
	public static <T> T fromJson(String json, Class<T> clazz, String datePattern) {
		if (StringUtils.isBlank(json)) {
			return null;
		}
		GsonBuilder builder = new GsonBuilder();
		if (StringUtils.isBlank(datePattern)) {
			datePattern = DEFAULT_DATE_PATTERN;
		}
		Gson gson = builder.create();
		try {
			return gson.fromJson(json, clazz);
		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 
	 * 将给定的 {@code JSON} 字符串转换成指定的类型对象。<strong>此方法通常用来转换普通的 {@code JavaBean}
	 * 对象。</strong>
	 * @param <T>  要转换的目标类型。
	 * @param json  给定的 {@code JSON} 字符串。
	 * @param clazz  要转换的目标类。
	 * @return 给定的 {@code JSON} 字符串表示的指定的类型对象。
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
		return fromJson(json, clazz, null);
	}

	/**
	 * @Title: fromJsonToMap
	 * @Description: Json字符串转换成Map
	 * @param @param json
	 * @param @return
	 * @return Map
	 * @throws
	 */
	public static Map fromJsonToMap(String json) {
		return fromJson(json, Map.class);
	}
	
	/**
	 * 
	 * 将给定的目标对象根据{@code GsonBuilder} 所指定的条件参数转换成 {@code JSON} 格式的字符串。
	 * <p />
	 * 该方法转换发生错误时，不会抛出任何异常。若发生错误时，{@code JavaBean} 对象返回 <code>"{}"</code>；
	 * 集合或数组对象返回
	 * @param target  目标对象。
	 * @param targetType  目标对象的类型。
	 * @param builder  可定制的{@code Gson} 构建器。
	 * @return 目标对象的 {@code JSON} 格式的字符串。
	 * @since 1.1
	 */
	public static String toJson(Object target, Type targetType,
			GsonBuilder builder) {
		if (target == null)
			return EMPTY_JSON;
		Gson gson = null;
		if (builder == null) {
			gson = new Gson();
		} else {
			gson = builder.create();
		}
		String result = EMPTY_JSON;
		try {
			if (targetType == null) {
				result = gson.toJson(target);
			} else {
				result = gson.toJson(target, targetType);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			if (target instanceof Collection<?>
					|| target instanceof Iterator<?>
					|| target instanceof Enumeration<?>
					|| target.getClass().isArray()) {
				result = EMPTY_JSON_ARRAY;
			}
		}
		return result;
	}
}
