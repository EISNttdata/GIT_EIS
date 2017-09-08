package com.dell.dims.Utils;

/**
 * Created by Manoj_Mehta on 1/4/2017.
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringSupport {
    private static char[] wschars = new char[]{'\t', '\n', '\u000b', '\f', '\r', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', ' ', '?', '?', '?', '?', '\u200b', ' ', '\ufeff'};

    public StringSupport() {
    }

    private static boolean isIn(char c, char[] cs) {
        char[] var2 = cs;
        int var3 = cs.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            char f = var2[var4];
            if(f == c) {
                return true;
            }
        }

        return false;
    }

    public static boolean equals(String s1, String s2) {
        return s1 == null?s2 == null:s1.equals(s2);
    }

    public static String charAltsToRegex(char[] cs) {
        StringBuilder buf = new StringBuilder();
        char[] var2 = cs;
        int var3 = cs.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            char c = var2[var4];
            buf.append("(\\Q" + c + "\\E)|");
        }

        if(buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }

        return buf.toString();
    }

    public static String strAltsToRegex(String[] strs) {
        StringBuilder buf = new StringBuilder();
        String[] var2 = strs;
        int var3 = strs.length;

        for(int var4 = 0; var4 < var3; ++var4) {
            String s = var2[var4];
            buf.append("(\\Q" + s + "\\E)|");
        }

        if(buf.length() > 0) {
            buf.deleteCharAt(buf.length() - 1);
        }

        return buf.toString();
    }

    public static int Compare(String s1, String s2) {
        return Compare(s1, s2, false);
    }

    public static int Compare(String s1, String s2, boolean ignoreCase) {
        return s1 == null?(s2 == null?0:-1):(s2 == null?1:(ignoreCase?s1.compareToIgnoreCase(s2):s1.compareTo(s2)));
    }

    public static String join(Collection<?> s, String delimiter) {
        StringBuilder builder = new StringBuilder();
        Iterator iter = s.iterator();

        while(iter.hasNext()) {
            builder.append(iter.next());
            if(!iter.hasNext()) {
                break;
            }

            builder.append(delimiter);
        }

        return builder.toString();
    }

    public static String join(Object[] s, String delimiter) {
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < s.length; ++i) {
            builder.append(s[i].toString());
            if(i == s.length - 1) {
                break;
            }

            builder.append(delimiter);
        }

        return builder.toString();
    }

    public static String Trim(String in, char[] filters, boolean trimStart, boolean trimEnd) {
        if(in == null) {
            return null;
        } else {
            int firstIdx = 0;
            if(trimStart) {
                while(firstIdx < in.length() && isIn(in.charAt(firstIdx), filters)) {
                    ++firstIdx;
                }
            }

            int lastIdx = in.length();
            if(trimEnd) {
                while(lastIdx > firstIdx && isIn(in.charAt(lastIdx - 1), filters)) {
                    --lastIdx;
                }
            }

            return in.substring(firstIdx, lastIdx);
        }
    }

    public static String Trim(String in) {
        return Trim(in, wschars, true, true);
    }

    public static String Trim(String in, char[] filters) {
        return Trim(in, filters, true, true);
    }

    public static String TrimStart(String in, char[] filters) {
        return Trim(in, filters == null?wschars:filters, true, false);
    }

    public static String TrimEnd(String in, char[] filters) {
        return Trim(in, filters == null?wschars:filters, false, true);
    }

    public static String PadLeft(Object inVal, int totalWidth, char paddingChar) {
        String inValStr = inVal.toString();
        int inValLen = inValStr.length();
        if(inValLen >= totalWidth) {
            return inValStr;
        } else {
            char[] padder = new char[totalWidth - inValLen];
            Arrays.fill(padder, paddingChar);
            return new String(padder) + inValStr;
        }
    }

    public static String PadLeft(Object inVal, int totalWidth) {
        return PadLeft(inVal, totalWidth, ' ');
    }

    public static String PadRight(Object inVal, int totalWidth, char paddingChar) {
        String inValStr = inVal.toString();
        int inValLen = inValStr.length();
        if(inValLen >= totalWidth) {
            return inValStr;
        } else {
            char[] padder = new char[totalWidth - inValLen];
            Arrays.fill(padder, paddingChar);
            return inValStr + new String(padder);
        }
    }

    public static String PadRight(Object inVal, int totalWidth) {
        return PadRight(inVal, totalWidth, ' ');
    }

    public static final String encodeHTML(String str) {
        if(str != null && str != "") {
            StringBuilder sb = new StringBuilder();

            for(int i = 0; i < str.length(); ++i) {
                char c = str.charAt(i);
                switch(c) {
                    case '\"':
                        sb.append("&quot;");
                        break;
                    case '&':
                        sb.append("&amp;");
                        break;
                    case '<':
                        sb.append("&lt;");
                        break;
                    case '>':
                        sb.append("&gt;");
                        break;
                    default:
                        sb.append(c);
                }
            }

            return sb.toString();
        } else {
            return str;
        }
    }

    public static final String[] Split(String str, char c1) {
        String[] rets = str.split("\\Q" + c1 + "\\E");
        if(str.endsWith(c1 + "")) {
            ArrayList ret_al = new ArrayList(Arrays.asList(rets));
            ret_al.add("");
            rets = (String[])ret_al.toArray(new String[ret_al.size()]);
        }

        return rets;
    }

    public static final String[] Split(String str, char c1, char c2) {
        String[] rets = str.split("\\Q" + c1 + "\\E)|(\\Q" + c2 + "\\E");
        if(str.endsWith(c1 + "") || str.endsWith(c2 + "")) {
            ArrayList ret_al = new ArrayList(Arrays.asList(rets));
            ret_al.add("");
            rets = (String[])ret_al.toArray(new String[ret_al.size()]);
        }

        return rets;
    }

    public static final String[] Split(String str, char[] cs, StringSplitOptions options) {
        String[] rets = str.split(charAltsToRegex(cs));
        char[] ret_al = cs;
        int var5 = cs.length;

        int var6;
        for(var6 = 0; var6 < var5; ++var6) {
            char c = ret_al[var6];
            if(options != StringSplitOptions.RemoveEmptyEntries && str.endsWith(c + "")) {
                ArrayList p = new ArrayList(Arrays.asList(rets));
                p.add("");
                rets = (String[])p.toArray(new String[p.size()]);
                break;
            }
        }

        if(options == StringSplitOptions.RemoveEmptyEntries) {
            ArrayList var9 = new ArrayList();
            String[] var10 = rets;
            var6 = rets.length;

            for(int var11 = 0; var11 < var6; ++var11) {
                String var12 = var10[var11];
                if(!var12.equals("")) {
                    var9.add(var12);
                }
            }

            rets = (String[])var9.toArray(new String[var9.size()]);
        }

        return rets;
    }

    public static final String[] Split(String str, String[] strs, StringSplitOptions options) {
        String[] rets = str.split(strAltsToRegex(strs));
        String[] ret_al = strs;
        int var5 = strs.length;

        int var6;
        for(var6 = 0; var6 < var5; ++var6) {
            String s = ret_al[var6];
            if(options != StringSplitOptions.RemoveEmptyEntries && str.endsWith(s)) {
                ArrayList p = new ArrayList(Arrays.asList(rets));
                p.add("");
                rets = (String[])p.toArray(new String[p.size()]);
                break;
            }
        }

        if(options == StringSplitOptions.RemoveEmptyEntries) {
            ArrayList var9 = new ArrayList();
            String[] var10 = rets;
            var6 = rets.length;

            for(int var11 = 0; var11 < var6; ++var11) {
                String var12 = var10[var11];
                if(!var12.equals("")) {
                    var9.add(var12);
                }
            }

            rets = (String[])var9.toArray(new String[var9.size()]);
        }

        return rets;
    }

    public static final boolean isNullOrEmpty(String str) {
        return str == null || "".equals(str);
    }

    public static final boolean IsNullOrEmpty(String str) {
        return isNullOrEmpty(str);
    }

    public static final boolean IsEmptyOrBlank(String str) {
        int firstIdx;
        for(firstIdx = 0; firstIdx < str.length() && isIn(str.charAt(firstIdx), wschars); ++firstIdx) {
        }

        return firstIdx == str.length();
    }

    public static final int lastIndexOfAny(String str, char[] anyOf) {
        int index = -1;
        char[] var3 = anyOf;
        int var4 = anyOf.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            char test = var3[var5];
            index = Math.max(index, str.lastIndexOf(test));
        }

        return index;
    }

    public static String mkString(char c, int count) {
        char[] chars = new char[count];

        for(int i = 0; i < count; ++i) {
            chars[i] = c;
        }

        return new String(chars);
    }

    public static String CSFmtStrToJFmtStr(String fmt) {
        Pattern p = Pattern.compile("\\{(\\d)+\\}");
        Matcher m = p.matcher(fmt);
        StringBuffer sb = new StringBuffer();

        while(m.find()) {
            String replacement = "%" + String.valueOf(Integer.parseInt(m.group(1)) + 1) + "\\$s";
            m.appendReplacement(sb, replacement);
        }

        m.appendTail(sb);
        return sb.toString();
    }

    public static void Testmain(String[] args) {
        System.out.println("**" + Trim("") + "**");
        System.out.println("**" + Trim("kevin") + "**");
        System.out.println("**" + Trim("    ") + "**");
        char[] splitFred = new char[]{'h', 'e', 'l', 'l', 'o'};
        int var2 = splitFred.length;

        for(int var3 = 0; var3 < var2; ++var3) {
            Character o = Character.valueOf(splitFred[var3]);
            char c = o.charValue();
            System.out.print(c);
        }

        System.out.println();
        System.out.println("5L = **" + PadLeft(Integer.valueOf(56), 5, '0') + "**");
        System.out.println("1L = **" + PadLeft(Integer.valueOf(56), 1, '0') + "**");
        System.out.println("2L = **" + PadLeft(Integer.valueOf(56), 2, '0') + "**");
        System.out.println("5L = **" + PadLeft(Integer.valueOf(-56), 5, '0') + "**");
        System.out.println("1L = **" + PadLeft(Integer.valueOf(-56), 1, '0') + "**");
        System.out.println("2L = **" + PadLeft(Integer.valueOf(-56), 2, '0') + "**");
        System.out.println("5R = **" + PadRight(Integer.valueOf(56), 5, '0') + "**");
        System.out.println("1R = **" + PadRight(Integer.valueOf(56), 1, '0') + "**");
        System.out.println("2R = **" + PadRight(Integer.valueOf(56), 2, '0') + "**");
        System.out.println("5R = **" + PadRight(Integer.valueOf(-56), 5, '0') + "**");
        System.out.println("1R = **" + PadRight(Integer.valueOf(-56), 1, '0') + "**");
        System.out.println("2R = **" + PadRight(Integer.valueOf(-56), 2, '0') + "**");
        String[] var6 = Split("fred=", '=');
        System.out.println("Split(\"fred=\", \'=\') = [\"" + var6[0] + "\", \"" + var6[1] + "\"]");
        var6 = Split("fred=5", '=');
        System.out.println("Split(\"fred=5\", \'=\') = [\"" + var6[0] + "\", \"" + var6[1] + "\"]");
        var6 = Split("=fred", '=');
        System.out.println("Split(\"=fred\", \'=\') = [\"" + var6[0] + "\", \"" + var6[1] + "\"]");
        System.out.printf(CSFmtStrToJFmtStr("DECLARATION: {0}={1}") + "\n", "Kevin", "Great");
        System.out.printf(CSFmtStrToJFmtStr("DECLARATION: {0}={1}"), "Kevin", "Great");
    }

   /* public static void main(String[] args) {
        Testmain(args);
    }*/
}

