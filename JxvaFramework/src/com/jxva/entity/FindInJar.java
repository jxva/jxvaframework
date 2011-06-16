package com.jxva.entity;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;


/**
 * 在目录中查找类位于哪个jar包中
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-06 13:15:20 by Jxva
 */
public class FindInJar {
	
    private String className;
    private ArrayList<String> jarFiles;

    public FindInJar(String className) {
        this.className = className;
        this.jarFiles = new ArrayList<String>();
    }

    public List<String> findClass(String dir, boolean recurse)throws EntityException {
        try {
            File d = new File(dir);
            if (!d.isDirectory()) {
                return jarFiles;
            }
            File[] files = d.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (recurse && files[i].isDirectory()) {
                    findClass(files[i].getAbsolutePath(), true);
                } else {
                    String filename = files[i].getAbsolutePath();
                    if (filename.endsWith(".jar")||filename.endsWith(".zip")) {
                        ZipFile zip = new ZipFile(filename);
                        Enumeration<? extends ZipEntry> entries = zip.entries();
                        while (entries.hasMoreElements()) {
                            ZipEntry entry = (ZipEntry) entries.nextElement();
                            String thisClassName = entry.getName().replace('/', '.');
                            if (thisClassName.equals(className) || thisClassName.equals(className + ".class")) {
                                jarFiles.add(filename);
                            }
                        }
                    }
                }
            }
            return jarFiles;
        } catch (IOException e) {
            throw new EntityException(e);
        }
    }
}