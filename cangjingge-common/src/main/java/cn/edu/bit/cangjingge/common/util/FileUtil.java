package cn.edu.bit.cangjingge.common.util;

public class FileUtil {

    public static String getExtensionName(final String filename) {
        String[] strings = filename.split("\\.");
        return strings.length > 1 ? strings[strings.length - 1] : "";
    }

}
