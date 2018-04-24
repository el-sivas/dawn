package de.elsivas.basic;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class CopyPrimitiveValuesUtils {

	private static final Log LOG = LogFactory.getLog(CopyPrimitiveValuesUtils.class);

	private static List<Class> PRIMITIVE_TYPES = Arrays.asList(Byte.class, Short.class, Integer.class, Long.class,
			Character.class, Boolean.class, Double.class, Float.class);

	private static List<Class> EXTENDED_PRIMITIVE_TYPES = Arrays.asList(Date.class, String.class);

	private static List<Class> SUPPORTED_COMPLEX_TYPES = Arrays.asList(BigDecimal.class);

	public static void copy(Object source, Object target) throws EsLogicException {
		copy(source, target, false);
	}

	public static void copy(Object source, Object target, boolean strict) throws EsLogicException {
		final Class<? extends Object> sourceClass = source.getClass();
		final Class<? extends Object> targetClass = target.getClass();

		if (!sourceClass.equals(targetClass) && strict) {
			throw new EsLogicException("classes not equal: " + sourceClass + ", " + targetClass);
		}

		LOG.info("copy from '" + sourceClass.getName() + "' to '" + targetClass.getName() + "'");

		final Method[] methods = sourceClass.getMethods();
		for (final Method method : methods) {
			final String methodName = method.getName();
			if (!isGetterMethod(methodName)) {
				LOG.debug("no getter method: " + methodName);
				continue;
			}

			final Class<?> returnType = method.getReturnType();
			if (!isSupported(returnType)) {
				final String message = "return type not supported: " + returnType;
				LOG.debug(message);
				continue;
			}

			final String setterMethodNameCandidate = determineSetterMethodNameCandidate(methodName);
			if (setterMethodNameCandidate == null) {
				final String message = "no setter method found for: " + methodName;
				if (strict) {
					throw new EsLogicException(message);
				}
				LOG.warn(message);
				continue;
			}
			final Method setterMethod;
			try {
				setterMethod = targetClass.getMethod(setterMethodNameCandidate,
						boolean.class.isAssignableFrom(returnType) ? Boolean.class : returnType);
			} catch (NoSuchMethodException | SecurityException e) {
				final String message = "setter method not found for: " + methodName;
				if (strict) {
					throw new EsLogicException(message, e);
				}
				LOG.trace("no method found", e);
				LOG.warn(message);
				continue;
			}
			final Object value;
			try {
				value = method.invoke(source);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				final String message = "error invoking getter method: " + methodName;
				if (strict) {
					throw new EsLogicException(message, e);
				}
				LOG.warn(message, e);
				continue;
			}
			if (value == null) {
				LOG.info("value null for getter method: " + methodName);
				continue;
			}
			final Object valueCopy = copyValue(value);
			if (valueCopy == null) {
				final String message = "copy failed for value: " + value;
				if (strict) {
					throw new EsLogicException(message);
				}
				LOG.warn(message);
				continue;
			}
			try {
				setterMethod.invoke(target, valueCopy);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
				final String message = "set of value failed using setter method: " + setterMethodNameCandidate;
				if (strict) {
					throw new EsLogicException(message, e);
				}
				LOG.warn(message, e);
				continue;
			}
			LOG.debug("success setting value copy from " + sourceClass.getName() + "." + methodName + " to ."
					+ setterMethodNameCandidate + ": " + valueCopy);

		}

	}

	private static Object copyValue(Object value) {
		if (value == null) {
			return null;
		}
		final Class<? extends Object> valueType = value.getClass();
		if (valueType.isPrimitive() || PRIMITIVE_TYPES.contains(valueType)) {
			return value;
		}
		if (EXTENDED_PRIMITIVE_TYPES.contains(valueType) || SUPPORTED_COMPLEX_TYPES.contains(valueType)) {
			return copyExtendedValue(value);
		}
		return null;
	}

	private static Object copyExtendedValue(Object value) {
		if (value instanceof String) {
			return value;
		}
		if (value instanceof Date) {
			return new Date(((Date) value).getTime());
		}
		if (value instanceof BigDecimal) {
			return BigDecimal.valueOf(((BigDecimal) value).doubleValue());
		}
		return null;
	}

	private static boolean isSupported(Class<?> returnType) {
		if (returnType.isPrimitive()) {
			return true;
		}
		if (PRIMITIVE_TYPES.contains(returnType)) {
			return true;
		}
		if (EXTENDED_PRIMITIVE_TYPES.contains(returnType)) {
			return true;
		}
		if (SUPPORTED_COMPLEX_TYPES.contains(returnType)) {
			return true;
		}
		return false;

	}

	private static String determineSetterMethodNameCandidate(String getterMethodName) {
		final StringBuilder sb = new StringBuilder("set");
		if (getterMethodName.startsWith("is")) {
			sb.append(getterMethodName.substring(2));
		} else {
			sb.append(getterMethodName.substring(3));
		}
		return sb.toString();
	}

	private static boolean isGetterMethod(String name) {
		if (name.startsWith("get")) {
			return true;
		}
		return name.startsWith("is");
	}
}
