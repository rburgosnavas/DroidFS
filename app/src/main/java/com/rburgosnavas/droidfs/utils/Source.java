package com.rburgosnavas.droidfs.utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * File IO helper.
 */
public class Source {
    private static final String TAG = Source.class.getSimpleName();
    private static final String SEP = File.separator;
    public static final String DROIDFS_DL_FOLDER = Environment.getExternalStorageDirectory() +
            SEP + "DroidFS";

    /**
     * Makes directory if it doesn't exist.
     *
     * @param name The directory name.
     */
    private static void makeDirectory(String name) {
        File dir = new File(name);

        if (!dir.exists()) {
            dir.mkdir();
            Log.i(TAG, "Folder " + dir.getName() + " created.");
        } else {
            Log.i(TAG, "Folder " + dir.getName() + " exists.");
        }
    }

    /**
     * Makes main app "DroidFS" directory.
     */
    private static void makeDownloadDirectory() {
        makeDirectory(DROIDFS_DL_FOLDER);
    }

    /**
     * Writes file.
     *
     * @param in The InputSteam to read from.
     * @param file The File to write.
     */
    private static void writeFile(InputStream in, File file) {
        OutputStream out;

        try {
            out = new FileOutputStream(file);
            byte[] bytes = new byte[1024];
            int read;
            while ((read = in.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }

            in.close();
            out.flush();
            out.close();
            Log.i(TAG, "File " + file.getName() + " created.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Writes a file to disk.
     *
     * @param in The InputStream to read from bytes from, typically from
     *           {@code retrofit.client.Response.getBody().in()}. A file stored in the following
     *           hierarchy:<br><br>
     *           <pre>sdcard/DroidFS/UserName/PackName/filename.ext</pre><br>
     *           ... or if the file is not part of a pack...<br><br>
     *           <pre>sdcard/DroidFS/UserName/filename.ext</pre>
     *
     * @param userName The user name that owns this file. This is used to create a directory with
     *                 user name under the {@code DroidFS} directory if it doesn't exist. This field
     *                 cannot and should never be {@code null} or empty.
     * @param packName The pack name that the sound belongs to. This is used to create a directory
     *                 with the pack name under the {@code userName} directory if it doesn't exist.
     *                 If {@code null} or empty, then the directory will not be created.
     * @param fileName The name of the file to save. This field cannot and should never
     *                 be {@code null} or empty.
     *
     * @throws java.lang.NullPointerException if userName or fileName are null or empty
     */
    public static void writeSound(InputStream in, String userName, String packName,
                                  String fileName) {
        makeDownloadDirectory();

        if (userName == null || "".equals(userName)) {
            throw new NullPointerException("User name is null or empty empty");
        }

        if (fileName == null || "".equals(fileName)) {
            throw new NullPointerException("File name is null or empty.");
        }

        makeDirectory(DROIDFS_DL_FOLDER + SEP + userName);

        if (packName != null && !"".equals(packName)) {
            makeDirectory(DROIDFS_DL_FOLDER + SEP + userName + SEP + packName);
        }

        File file = new File(DROIDFS_DL_FOLDER + SEP + userName +
                (packName != null && !"".equals(packName) ? SEP + packName : ""),
                fileName);

        writeFile(in, file);
    }

    // TODO Unzip pack file helper
    public static void unzipPack(String userName, String packName, String fileName) {
        makeDownloadDirectory();

        if (userName == null || "".equals(userName)) {
            throw new NullPointerException("User name is null or empty empty");
        }

        if (fileName == null || "".equals(fileName)) {
            throw new NullPointerException("File name is null or empty.");
        }

        makeDirectory(DROIDFS_DL_FOLDER + SEP + userName);

        if (packName != null && !"".equals(packName)) {
            makeDirectory(DROIDFS_DL_FOLDER + SEP + userName + SEP + packName);
        }

        byte[] buffer = new byte[1024];

        File file = new File(DROIDFS_DL_FOLDER + SEP + userName +
                (packName != null && !"".equals(packName) ? SEP + packName : ""),
                fileName);

        try {
            ZipInputStream zis = new ZipInputStream(new FileInputStream(file));

            ZipEntry zipEntry = zis.getNextEntry();

            while (zipEntry != null) {
                String zipEntryFileName = zipEntry.getName();
                File zipEntryFile = new File(file.getParent(), zipEntryFileName);

                FileOutputStream fos = new FileOutputStream(zipEntryFile);
                int len;

                while ((len = zis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }

                fos.close();

                zipEntry = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // TODO delete pack file helper

}
