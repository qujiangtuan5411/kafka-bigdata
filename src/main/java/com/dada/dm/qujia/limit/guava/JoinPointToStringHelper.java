//package com.dada.dm.qujia.limit.guava;
//
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.Signature;
//import org.aspectj.lang.reflect.MethodSignature;
//import org.aspectj.lang.reflect.SourceLocation;
//
//import java.util.Optional;
//
///**
// * @author jt.Qu
// * @description
// * @program: kafka-demo
// * @date 2023-03-22 10:21
// */
//public class JoinPointToStringHelper {
//
//    public static String toString(JoinPoint jp) {
//        StringBuilder sb = new StringBuilder();
//        appendType(sb, getType(jp));
//        Signature signature = jp.getSignature();
//        if (signature instanceof MethodSignature) {
//            MethodSignature ms = (MethodSignature) signature;
//            sb.append("#");
//            sb.append(ms.getMethod().getName());
//            sb.append("(");
//            appendTypes(sb, ms.getMethod().getParameterTypes());
//            sb.append(")");
//        }
//        return sb.toString();
//    }
//
//    private static Class<?> getType(JoinPoint jp) {
//        return Optional.ofNullable(jp.getSourceLocation())
//                .map(SourceLocation::getWithinType)
//                .orElse(jp.getSignature().getDeclaringType());
//    }
//
//    private static void appendTypes(StringBuilder sb, Class<?>[] types) {
//        for (int size = types.length, i = 0; i < size; i++) {
//            appendType(sb, types[i]);
//            if (i < size - 1) {
//                sb.append(",");
//            }
//        }
//    }
//
//    private static void appendType(StringBuilder sb, Class<?> type) {
//        if (type.isArray()) {
//            appendType(sb, type.getComponentType());
//            sb.append("[]");
//        } else {
//            sb.append(type.getName());
//        }
//    }
//}
