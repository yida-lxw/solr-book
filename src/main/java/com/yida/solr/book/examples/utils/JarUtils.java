package com.yida.solr.book.examples.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Manifest;
/**
 * Created by Lanxiaowei
 * Craated on 2016/11/19 20:01
 */
public class JarUtils {
    private static ArrayList<URL> classPath = new ArrayList<URL>();

    /** Unpack a jar file into a directory. */
    public static void unJar(File jarFile, File toDir) throws IOException {
        JarFile jar = new JarFile(jarFile);
        try {
            Enumeration entries = jar.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = (JarEntry) entries.nextElement();
                if (!entry.isDirectory()) {
                    InputStream in = jar.getInputStream(entry);
                    try {
                        File file = new File(toDir, entry.getName());
                        if (!file.getParentFile().mkdirs()) {
                            if (!file.getParentFile().isDirectory()) {
                                throw new IOException("Mkdirs failed to create "
                                        + file.getParentFile().toString());
                            }
                        }
                        OutputStream out = new FileOutputStream(file);
                        try {
                            byte[] buffer = new byte[8192];
                            int i;
                            while ((i = in.read(buffer)) != -1) {
                                out.write(buffer, 0, i);
                            }
                        } finally {
                            out.close();
                        }
                    } finally {
                        in.close();
                    }
                }
            }
        } finally {
            jar.close();
        }
    }

    /**
     * Run a Hadoop job jar. If the main class is not in the jar's manifest, then
     * it must be provided on the command line.
     */
    public static void runJar(String[] args) throws Throwable {
        String usage = "jarFile [mainClass] args...";

        if (args.length < 1) {
            System.err.println(usage);
            System.exit(-1);
        }

        int firstArg = 0;
        String fileName = args[firstArg++];
        File file = new File(fileName);
        String mainClassName = null;

        JarFile jarFile;
        try {
            jarFile = new JarFile(fileName);
        } catch (IOException io) {
            throw new IOException("Error opening job jar: " + fileName).initCause(io);
        }

        Manifest manifest = jarFile.getManifest();
        if (manifest != null) {
            mainClassName = manifest.getMainAttributes().getValue("Main-Class");
        }
        jarFile.close();

        if (mainClassName == null) {
            if (args.length < 2) {
                System.err.println(usage);
                System.exit(-1);
            }
            mainClassName = args[firstArg++];
        }
        mainClassName = mainClassName.replaceAll("/", ".");

        File tmpDir = new File(System.getProperty("java.io.tmpdir"));
        tmpDir.mkdirs();
        if (!tmpDir.isDirectory()) {
            System.err.println("Mkdirs failed to create " + tmpDir);
            System.exit(-1);
        }
        final File workDir = File.createTempFile("hadoop-unjar", "", tmpDir);
        workDir.delete();
        workDir.mkdirs();
        if (!workDir.isDirectory()) {
            System.err.println("Mkdirs failed to create " + workDir);
            System.exit(-1);
        }

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    fullyDelete(workDir);
                } catch (IOException e) {
                }
            }
        });

        unJar(file, workDir);

        classPath.add(new File(workDir + "/").toURL());
        classPath.add(file.toURL());
        classPath.add(new File(workDir, "classes/").toURL());
        File[] libs = new File(workDir, "lib").listFiles();
        if (libs != null) {
            for (int i = 0; i < libs.length; i++) {
                classPath.add(libs[i].toURL());
            }
        }

        ClassLoader loader = new URLClassLoader(classPath.toArray(new URL[0]));

        Thread.currentThread().setContextClassLoader(loader);
        Class<?> mainClass = Class.forName(mainClassName, true, loader);
        Method main = mainClass.getMethod("main", new Class[] { Array.newInstance(
                String.class, 0).getClass() });
        String[] newArgs = Arrays.asList(args).subList(firstArg, args.length)
                .toArray(new String[0]);
        try {
            main.invoke(null, new Object[] { newArgs });
        } catch (InvocationTargetException e) {
            throw e.getTargetException();
        }
    }

    /**
     * Delete a directory and all its contents. If we return false, the directory
     * may be partially-deleted.
     */
    public static boolean fullyDelete(File dir) throws IOException {
        File contents[] = dir.listFiles();
        if (contents != null) {
            for (int i = 0; i < contents.length; i++) {
                if (contents[i].isFile()) {
                    if (!contents[i].delete()) {
                        return false;
                    }
                } else {
                    // try deleting the directory
                    // this might be a symlink
                    boolean b = false;
                    b = contents[i].delete();
                    if (b) {
                        // this was indeed a symlink or an empty directory
                        continue;
                    }
                    // if not an empty directory or symlink let
                    // fullydelete handle it.
                    if (!fullyDelete(contents[i])) {
                        return false;
                    }
                }
            }
        }
        return dir.delete();
    }

    /**
     * Add a directory or file to classpath.
     *
     * @param component
     */
    public static void addClasspath(String component) {
        if ((component != null) && (component.length() > 0)) {
            try {
                File f = new File(component);
                if (f.exists()) {
                    URL key = f.getCanonicalFile().toURL();
                    if (!classPath.contains(key)) {
                        classPath.add(key);
                    }
                }
            } catch (IOException e) {
            }
        }
    }

    /**
     * Add default classpath listed in bin/hadoop bash.
     *
     * @param hadoopHome
     */
    public static void addDefaultClasspath(String hadoopHome) {
        // Classpath initially contains conf dir.
        addClasspath(hadoopHome + "/conf");

        // For developers, add Hadoop classes to classpath.
        addClasspath(hadoopHome + "/build/classes");
        if (new File(hadoopHome + "/build/webapps").exists()) {
            addClasspath(hadoopHome + "/build");
        }
        addClasspath(hadoopHome + "/build/test/classes");
        addClasspath(hadoopHome + "/build/tools");

        // For releases, add core hadoop jar & webapps to classpath.
        if (new File(hadoopHome + "/webapps").exists()) {
            addClasspath(hadoopHome);
        }
        addJarsInDir(hadoopHome);
        addJarsInDir(hadoopHome + "/build");

        // Add libs to classpath.
        addJarsInDir(hadoopHome + "/lib");
        addJarsInDir(hadoopHome + "/lib/jsp-2.1");
        addJarsInDir(hadoopHome + "/build/ivy/lib/Hadoop/common");
    }

    /**
     * Add all jars in directory to classpath, sub-directory is excluded.
     *
     * @param dirPath
     */
    public static void addJarsInDir(String dirPath) {
        File dir = new File(dirPath);
        if (!dir.exists()) {
            return;
        }
        File[] files = dir.listFiles();
        if (files == null) {
            return;
        }
        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                continue;
            } else {
                addClasspath(files[i].getAbsolutePath());
            }
        }
    }

    /**
     * Create a temp jar file in "java.io.tmpdir".
     *
     * @param root
     * @return
     * @throws IOException
     */
    public static File createTempJar(String root) throws IOException {
        if (!new File(root).exists()) {
            return null;
        }
        Manifest manifest = new Manifest();
        manifest.getMainAttributes().putValue("Manifest-Version", "1.0");
        final File jarFile = File.createTempFile("JarUtils-", ".jar", new File(System
                .getProperty("java.io.tmpdir")));

        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                jarFile.delete();
            }
        });

        JarOutputStream out = new JarOutputStream(new FileOutputStream(jarFile),
                manifest);
        createTempJarInner(out, new File(root), "");
        out.flush();
        out.close();
        return jarFile;
    }

    private static void createTempJarInner(JarOutputStream out, File f,
                                           String base) throws IOException {
        if (f.isDirectory()) {
            File[] fl = f.listFiles();
            if (base.length() > 0) {
                base = base + "/";
            }
            for (int i = 0; i < fl.length; i++) {
                createTempJarInner(out, fl[i], base + fl[i].getName());
            }
        } else {
            // filter MANIFEST created by M2E eclipse plugin
            if(base.equalsIgnoreCase("META-INF/MANIFEST.MF"))
                return;
            out.putNextEntry(new JarEntry(base));
            FileInputStream in = new FileInputStream(f);
            byte[] buffer = new byte[1024];
            int n = in.read(buffer);
            while (n != -1) {
                out.write(buffer, 0, n);
                n = in.read(buffer);
            }
            in.close();
        }
    }

    /**
     * Return a classloader based on user-specified classpath and parent
     * classloader.
     *
     * @return
     */
    public static ClassLoader getClassLoader() {
        ClassLoader parent = Thread.currentThread().getContextClassLoader();
        if (parent == null) {
            parent = JarUtils.class.getClassLoader();
        }
        if (parent == null) {
            parent = ClassLoader.getSystemClassLoader();
        }
        return new URLClassLoader(classPath.toArray(new URL[0]), parent);
    }
}
