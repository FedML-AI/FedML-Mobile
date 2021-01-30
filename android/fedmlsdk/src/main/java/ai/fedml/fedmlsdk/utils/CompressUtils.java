package ai.fedml.fedmlsdk.utils;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;

public class CompressUtils {
    public static void unzip(final String zipFilePath, final String destDir) throws ZipException {
        ZipFile zipFile = new ZipFile(zipFilePath);
        zipFile.extractAll(destDir);
    }
}
