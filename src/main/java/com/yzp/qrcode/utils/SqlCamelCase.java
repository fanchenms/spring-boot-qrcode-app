package com.yzp.qrcode.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * SQL 驼峰转换
 *
 * @author yzp
 * @version 1.0
 * @date 2022/9/18 - 3:51
 */
public class SqlCamelCase {

    /** 跳过字段，无需处理 */
    private final static List<String> SKIP_LIST = Arrays.asList(
            "SELECT", "FROM", "JOIN", "ON", "WHERE", "GROUP", "BY", "HAVING", "ORDER", "LIMIT",
            "DELETE", "UPDATE", "SET", "INSERT", "INTO", "VALUES"
    );
    /** 数据库表前面的关键字 */
    private final static List<String> KEYWORD_BEFORE_TABLE_LIST = Arrays.asList("FROM", "JOIN", "UPDATE",
            "INSERT", "INTO");
    /** 是否为表的标志 */
    private static boolean isTableFlag = false;
    /** 字段驼峰转换的标识 */
    private final static String FIELD_HUMP_CONVERT_MARK = ".";


    public static void main(String[] args) {
//        String str = "SELECT MAX(qci.codeType),qci.expirationTime, CONCAT( qci.createTime, TRIM(af.fileName),MAX(qci.contentType) )\n" +
//                "FROM qQrCodeInfo qci \n" +
//                "LEFT JOIN qQrCodeInfoAttachFile qciaf ON qci.id = qciaf.qrCodeInfoId \n" +
//                "LEFT JOIN qAttachFile af ON qciaf.attachFileId = af.fileId \n" +
//                "WHERE qci.id = 1";
//        System.out.println(humpConvertUnderscore(str));
//
//        String str2 = "SELECT MAX(qci.code_type),qci.expiration_time, CONCAT( qci.create_time, TRIM(af.file_name),MAX(qci.content_type) ) \n" +
//                "FROM q_qr_code_info qci \n" +
//                "LEFT JOIN q_qr_code_info_attach_file qciaf ON qci.id = qciaf.qr_code_info_id \n" +
//                "LEFT JOIN q_attach_file af ON qciaf.attach_file_id = af.file_id \n" +
//                "WHERE qci.id = 1 ";
//        System.out.println(underscoreConvertHump(str2));

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("请选择转换类型：1-驼峰转下划线 2-下划线转驼峰 exit-退出");
            String type = scanner.next();
            if ("exit".equalsIgnoreCase(type)) {
                break;
            }
            System.out.println("请输入SQL语句【可回车换行输入SQL，写完回车，输入exit退出即可】：");
            StringBuilder inputSqlStr = new StringBuilder();
            while (true) {
                String str = scanner.nextLine();
                if ("exit".equalsIgnoreCase(str)) {
                    break;
                }
                inputSqlStr.append(str).append(" ");
            }

            System.out.println("转换结果：");
            if ("1".equals(type)) {
                System.out.println(humpConvertUnderscore(inputSqlStr.toString()));
            } else if ("2".equals(type)) {
                System.out.println(underscoreConvertHump(inputSqlStr.toString()));
            } else {
                System.out.println("暂不支持此类型：" + type);
            }
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println("是否继续（Y-是，任意键-否）：");
            String exitStr = scanner.next();
            if (!"Y".equalsIgnoreCase(exitStr)) {
                break;
            }
        }

    }

    public static String humpConvertUnderscore(String sqlStr) {
        return convert(sqlStr, 1);
    }

    public static String underscoreConvertHump(String sqlStr) {
        return convert(sqlStr, 2);
    }

    /**
     * 替换
     *
     * @param sqlStr   sql字符串
     * @param type  类型，1-驼峰转下划线 2-下划线转驼峰
     * @return  转换后的sql串
     */
    public static String convert(String sqlStr, int type) {
        if (sqlStr == null || sqlStr.trim().isEmpty()) {
            return null;
        }
        sqlStr = sqlStr.replaceAll("\n", " ");
        sqlStr = sqlStr.replaceAll("\t", " ");
        StringBuilder stringBuilder = new StringBuilder();
        String[] separatedBySpaces = sqlStr.split(" ");
        int i = 0;
        for (String separatedBySpace : separatedBySpaces) {
            if (separatedBySpace.trim().length() == 0) {
                stringBuilder.append(separatedBySpace);
                continue;
            }
            /*String[] separatedByCommas = separatedBySpace.split(",");
            for (int j = 0; j < separatedByCommas.length; j++) {
                stringBuilder.append(replaceHump(separatedByCommas[j]));
                if (j < separatedByCommas.length - 1) {
                    stringBuilder.append(",");
                }
            }*/
            if (type == 1) {
                stringBuilder.append(replaceHumpToUnderscore(separatedBySpace));
            } else if (type == 2) {
                stringBuilder.append(replaceUnderscoreToHump(separatedBySpace));
            }
            i++;
            if (i < separatedBySpaces.length) {
                stringBuilder.append(" ");
            }
        }
        return stringBuilder.toString();
    }

    /**
     * 替换驼峰为下划线
     *
     * @param str   需要转换的字符串
     * @return string
     */
    public static String replaceHumpToUnderscore(String str) {
        if (str.trim().isEmpty() || SKIP_LIST.contains(str)) {
            if (KEYWORD_BEFORE_TABLE_LIST.contains(str)) {
                isTableFlag = true;
            }
            return str;
        }
        char[] chars;
        StringBuilder stringBuilder = new StringBuilder();
        if (str.contains(FIELD_HUMP_CONVERT_MARK) || isTableFlag) {
            chars = str.toCharArray();
            int j = Integer.MAX_VALUE;
            if (isTableFlag) {
                j = 0;
                isTableFlag = false;
            }
            for (int i = 0; i < chars.length; i++) {
                if (chars[i] == '.') {
                    j = i;
                }
                // 遇到 )或,的话，j重置
                if (chars[i] == ')') {
                    j = Integer.MAX_VALUE;
                }
                if (chars[i] == ',') {
                    j = Integer.MAX_VALUE;
                }
                if (Character.isUpperCase(chars[i]) && i > j) {
                    stringBuilder.append('_').append(Character.toLowerCase(chars[i]));
                } else {
                    stringBuilder.append(chars[i]);
                }
            }
        }
        return stringBuilder.length() == 0 ? str : stringBuilder.toString();
    }

    public static String replaceUnderscoreToHump(String str) {
        if (str.trim().isEmpty() || SKIP_LIST.contains(str)) {
            return str;
        }
        char[] chars = str.toCharArray();
        StringBuilder stringBuilder = new StringBuilder();
        // 是否是下划线后的字符
        boolean isUnderscoreAfter = false;
        for (int i = 0; i < chars.length; i++) {
            if (isUnderscoreAfter) {
                isUnderscoreAfter = false;
                continue;
            }
            if (chars[i] == '_') {
                stringBuilder.append(Character.toUpperCase(chars[i + 1]));
                isUnderscoreAfter = true;
            } else {
                stringBuilder.append(chars[i]);
            }
        }
        return stringBuilder.toString();
    }

}
