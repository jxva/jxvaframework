/*
 * Copyright @ 2006-2009 by The Jxva Framework Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package study.cache;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.io.Serializable;
import java.io.StreamCorruptedException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * A disk cache implementation.
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:14:40 by Jxva
 */
@SuppressWarnings("unchecked")
public class DiskStore implements Store {
	
    private static final int MS_PER_SECOND = 1000;

    private final String name;
    private boolean active;
    private RandomAccessFile randomAccessFile;

    private HashMap<Serializable, DiskElement> diskElements;
    private ArrayList<DiskElement> freeSpace;
    private final Map<Serializable, Element> spool;

    private Thread spoolThread;
    private Thread expiryThread;
    private long expiryThreadInterval;

    private final CacheEntity cache;


    /**
     * If persistent, the disk file will be kept
     * and reused on next startup. In addition the
     * memory store will flush all contents to spool,
     * and spool will flush all to disk.
     */
    private final boolean persistent;

    private final String diskPath;

    private File dataFile;

    private File indexFile;

    private int status;

    private long totalSize;

    public DiskStore(CacheEntity cache, String diskPath) {
        status = Store.STATUS_UNINITIALISED;
        this.cache = cache;
        name = cache.getName();
        this.diskPath = diskPath;
        diskElements = new HashMap<Serializable, DiskElement>();
        freeSpace = new ArrayList<DiskElement>();
        spool = new HashMap<Serializable, Element>();
        this.expiryThreadInterval = cache.getDiskExpiryThreadIntervalSeconds();
        this.persistent = cache.isDiskPersistent();


        try {
            iniatialiseFiles();

            active = true;

            spoolThread = new SpoolThread();
            spoolThread.start();

            if (!cache.isEternal()) {
                expiryThread = new ExpiryThread();
                expiryThread.start();
            }

            status = Store.STATUS_ALIVE;
        } catch (final Exception e) {
            dispose();
            System.out.println(name + "Cache: Could not create disk store"+e.toString());
        }
    }


    private void iniatialiseFiles() throws Exception {
        // Make sure the cache directory exists
        final File diskDir = new File(diskPath);
        if (diskDir.exists() && !diskDir.isDirectory()) {
            throw new Exception("Store directory \"" + diskDir.getCanonicalPath() + "\" exists and is not a directory.");
        }
        if (!diskDir.exists() && !diskDir.mkdirs()) {
            throw new Exception("Could not create cache directory \"" + diskDir.getCanonicalPath() + "\".");
        }

        dataFile = new File(diskDir, getDataFileName());

        if (persistent) {
            indexFile = new File(diskDir, getIndexFileName());
            readIndex();
            if (diskElements == null) {
                dataFile.delete();
            }
        } else {
            dataFile.delete();
        }

        // Open the data file as random access. The dataFile is created if necessary.
        randomAccessFile = new RandomAccessFile(dataFile, "rw");
    }

    /**
     * Asserts that the store is active.
     */
    private void checkActive() throws CacheException {
        if (!active) {
            throw new CacheException(name + " Cache: The Disk store is not active.");
        }
    }

    public int getCacheType() {
        return Store.DISK_CACHE;
    }

    public String getName() {
        return name;
    }

    public synchronized int getStatus() {
        return Store.STATUS_ALIVE;
    }

    /**
     * Gets an {@link Element} from the Disk Store.
     * @return The element
     */
    public synchronized Element get(final Serializable key) throws IOException {
        try {
            checkActive();

            // Check in the spool.  Remove if present
            Element element = spool.remove(key);
            if (element != null) {
                element.updateAccessStatistics();
                return element;
            }

            // Check if the element is on disk
            final DiskElement diskElement = diskElements.get(key);
            if (diskElement == null) {
                // Not on disk
                return null;
            }

            // Load the element
            randomAccessFile.seek(diskElement.position);
            final byte[] buffer = new byte[diskElement.payloadSize];
            randomAccessFile.readFully(buffer);
            final ByteArrayInputStream instr = new ByteArrayInputStream(buffer);
            final ObjectInputStream objstr = new ObjectInputStream(instr);
            element = (Element) objstr.readObject();
            element.updateAccessStatistics();
            return element;
        } catch (Exception e) {
            System.out.println(name + "Cache: Could not read disk store element for key " + key+e.toString());
        }
        return null;
    }

    /**
     * Gets an {@link Element} from the Disk Store, without updating statistics
     * @return The element
     */
    public synchronized Element getQuiet(final Serializable key) throws IOException {
        try {
            checkActive();

            // Check in the spool.  Remove if present
            Element element = spool.remove(key);
            if (element != null) {
                //element.updateAccessStatistics(); Don't update statistics
                return element;
            }

            // Check if the element is on disk
            final DiskElement diskElement = diskElements.get(key);
            if (diskElement == null) {
                // Not on disk
                return null;
            }

            // Load the element
            randomAccessFile.seek(diskElement.position);
            final byte[] buffer = new byte[diskElement.payloadSize];
            randomAccessFile.readFully(buffer);
            final ByteArrayInputStream instr = new ByteArrayInputStream(buffer);
            final ObjectInputStream objstr = new ObjectInputStream(instr);
            element = (Element) objstr.readObject();
            //element.updateAccessStatistics(); Don't update statistics
            return element;
        } catch (Exception e) {
        	System.out.println(name + "Cache: Could not read disk store element for key " + key+e.toString());
        }
        return null;
    }


    /**
     * Gets an Array of the keys for all elements in the disk store.
     *
     * @return An Object[] of {@link Serializable} keys
     */
    public synchronized Object[] getKeyArray() {
        Set<Serializable> elementKeySet = diskElements.keySet();
        Set<Serializable> spoolKeySet = spool.keySet();
        Set<Serializable> allKeysSet = new HashSet<Serializable>(elementKeySet.size() + spoolKeySet.size());
        allKeysSet.addAll(elementKeySet);
        allKeysSet.addAll(spoolKeySet);
        return allKeysSet.toArray();
    }

    /**
     * Returns the current store size.
     */
    public synchronized int getSize() {
        try {
            checkActive();
            return diskElements.size() + spool.size();
        } catch (Exception e) {
        	System.out.println(name + "Cache: Could not determine size of disk store."+e.toString());
            return 0;
        }
    }

    /**
     * Puts an item into the cache.
     */
    public synchronized void put(final Element entry) throws IOException {
        try {
            checkActive();

            // Spool the entry
            spool.put(entry.getKey(), entry);
            notifyAll();
        } catch (Exception e) {
        	System.out.println(name + "Cache: Could not write disk store element for " + entry.getKey()+e.toString());
        }
    }

    /**
     * Removes an item from the cache.
     */
    public synchronized boolean remove(final Serializable key) throws IOException {
        try {
            checkActive();

            // Remove the entry from the spool
            final Object spoolValue = spool.remove(key);
            if (spoolValue != null) {
                return true;
            }

            // Remove the entry from the file
            final DiskElement element = diskElements.remove(key);
            if (element != null) {
                freeBlock(element);
                return true;
            }
        } catch (Exception e) {
        	System.out.println(name + "Cache: Could not remove disk store entry for " + key+e.toString());
        }
        return false;
    }

    /**
     * Marks a block as free.
     */
    private void freeBlock(final DiskElement element) {
        totalSize -= element.payloadSize;
        element.payloadSize = 0;
        freeSpace.add(element);
    }

    /**
     * Removes all cached items from the cache.
     * <p/>
     */
    public synchronized void removeAll() throws IOException {
        try {
            checkActive();

            // Ditch all the elements, and truncate the file
            spool.clear();
            diskElements.clear();
            freeSpace.clear();
            totalSize = 0;
            randomAccessFile.setLength(0);
            if (persistent) {
                indexFile.delete();
                indexFile.createNewFile();
            }
        } catch (Exception e) {
            // Clean up
        	System.out.println(name + " Cache: Could not rebuild disk store"+e.toString());
            dispose();
        }
    }

    /**
     * Shuts down the disk store in preparation for cache shutdown
     *
     * If a VM crash happens, the shutdown hook will not run. The data file and the index file
     * will be out of synchronisation. At initialisation we always delete the index file
     * after we have read the elements, so that it has a zero length. On a dirty restart, it still will have
     * and the data file will automatically be deleted, thus preserving safety.
     */
    public synchronized void dispose() {

        if (!active) {
            return;
        }

        // Close the cache
        try {
            if (expiryThread != null) {
                expiryThread.interrupt();
            }

            //Flush the spool if persistent, so we don't lose any data.
            if (persistent) {
                flushSpool();
                writeIndex();
            }

            //Clear in-memory data structures
            spool.clear();
            diskElements.clear();
            freeSpace.clear();
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
            if (!persistent) {
                //jlog.Debug("Deleting file " + dataFile.getName());
                dataFile.delete();
            }
        } catch (Exception e) {
        	System.out.println(name + "Cache: Could not shut down disk cache"+e.toString());
        } finally {
            active = false;
            randomAccessFile = null;
            notifyAll();
        }
    }

    /**
     * Whether there are any elements waiting to be spooled to disk.
     *
     * @return false if there are elements waiting, otherwise true
     */
    public synchronized boolean isSpoolEmpty() {
        return (!active || spool.size() == 0);
    }

    /**
     * Main method for the spool thread.
     * <p/>
     * Note that the spool thread locks the cache for the entire time it is writing elements to the disk.
     * 
     */
    private synchronized void spoolThreadMain() {
        while (true) {
            // Wait for elements in the spool
            while (active && spool.size() == 0) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    // Bail
                    return;
                }
            }
            if (!active) {
                return;
            }

            // Write elements to disk
            try {
                flushSpool();
            } catch (IOException e) {
            	System.out.println(name + "Cache: Could not write elements to disk cache"+e.toString());
            }
        }
    }

    /**
     * Flushes all spooled elements to disk.
     * Note that the cache is locked for the entire time that the spool is being flushed.
     */
    private synchronized void flushSpool() throws IOException {
        try {
            // Write elements to the DB
            for (Iterator<Element> iterator = spool.values().iterator(); iterator.hasNext();) {
                final Element element = iterator.next();
                final Serializable key = element.getKey();

                // Remove the old entry, if any
                final DiskElement oldBlock = diskElements.remove(key);
                if (oldBlock != null) {
                    freeBlock(oldBlock);
                }

                // Serialise the entry
                final ByteArrayOutputStream outstr = new ByteArrayOutputStream();
                final ObjectOutputStream objstr = new ObjectOutputStream(outstr);
                objstr.writeObject(element);
                objstr.close();
                final byte[] buffer = outstr.toByteArray();

                // Check for a free block
                DiskElement diskElement = findFreeBlock(buffer.length);
                if (diskElement == null) {
                    diskElement = new DiskElement();
                    diskElement.position = randomAccessFile.length();
                    diskElement.blockSize = buffer.length;
                }

                // Write the record
                randomAccessFile.seek(diskElement.position);
                //todo the free block algorithm will gradually leak disk space, due to
                //payload size being less than block size
                //this will be a problem for the persistent cache
                randomAccessFile.write(buffer);

                if (cache.isEternal()) {
                    // Never expires
                    diskElement.expiryTime = Long.MAX_VALUE;
                } else {
                    // Calculate expiry time
                    long timeToLive = element.getCreationTime() + cache.getTimeToLiveSeconds() * MS_PER_SECOND;
                    long timeToIdle = element.getLastAccessTime() + cache.getTimeToIdleSeconds() * MS_PER_SECOND;
                    diskElement.expiryTime = Math.max(timeToLive, timeToIdle);
                }

                // Add to index, update stats
                diskElement.payloadSize = buffer.length;
                totalSize += buffer.length;
                diskElements.put(key, diskElement);
            }
        } finally {
            // Clear the spool.  Do this regardless of whether the writes failed - just ditch the elements
            spool.clear();
        }
    }

    /**
     * Writes the Index to disk on shutdown
     * <p/>
     * The index consists of the elements Map and the freeSpace List
     * <p/>
     * Note that the cache is locked for the entire time that the index is being written
     */
    private synchronized void writeIndex() throws IOException {

        ObjectOutputStream objectOutputStream = null;
        try {
            FileOutputStream fout = new FileOutputStream(indexFile);
            objectOutputStream = new ObjectOutputStream(fout);
            objectOutputStream.writeObject(diskElements);
            objectOutputStream.writeObject(freeSpace);
        } finally {
            objectOutputStream.close();
        }
    }

    /**
     * Reads Index to disk on startup.
     * <p/>
     * if the index file does not exist, it creates a new one.
     * <p/>
     * Note that the cache is locked for the entire time that the index is being written
     */
    private synchronized void readIndex() throws IOException {
        ObjectInputStream objectInputStream = null;
        if (indexFile.exists()) {
            try {
                FileInputStream fin = new FileInputStream(indexFile);
                objectInputStream = new ObjectInputStream(fin);
                diskElements = (HashMap<Serializable, DiskStore.DiskElement>) objectInputStream.readObject();
                freeSpace = (ArrayList<DiskStore.DiskElement>) objectInputStream.readObject();
            } catch (StreamCorruptedException e) {
            	System.out.println("Corrupt index file. Creating new index. ");
                createNewIndexFile();
            } catch (IOException e) {
            	System.out.println("IOException reading index. Creating new index.");
                createNewIndexFile();
            } catch (ClassNotFoundException e) {
            	System.out.println("Class loading problem reading index. Creating new index."+e.toString());
                createNewIndexFile();
            } finally {
                if (objectInputStream != null) {
                    objectInputStream.close();
                }
                //Zero out file. If there is a dirty shutdown, the file will be empty.
                createNewIndexFile();
            }
        } else {
            createNewIndexFile();
        }
    }

    private void createNewIndexFile() throws IOException {
        if (indexFile.exists()) {
            indexFile.delete();
            //jlog.Debug("Index file " + indexFile + " deleted.");
        }
        if (indexFile.createNewFile()) {
        	System.out.println("Index file " + indexFile + " created successfully");
        } else {
            throw new IOException("Index file " + indexFile + " could not created.");
        }
    }

    /**
     * The main method for the expiry thread.
     * <p/>
     * Will run while the cache is active. After the cache shuts down
     * it will take the expiryThreadInterval to wake up and complete.
     */
    private void expiryThreadMain() {
        long expiryThreadIntervalMillis = expiryThreadInterval * MS_PER_SECOND;
        try {
            while (active) {
                Thread.sleep(expiryThreadIntervalMillis);

                //Expire the elements
                expireElements();
            }
        } catch (InterruptedException e) {
            // Bail on interruption
            //jlog.Debug(name + "Cache: Expiry thread interrupted on Disk Store.");
            
        }
    }

    /**
     * Removes expired elements.
     * Note that the cache is locked for the entire time that elements are being expired.
     */
    private synchronized void expireElements() {
        final long now = System.currentTimeMillis();

        // Clean up the spool
        for (Iterator<Element> iterator = spool.values().iterator(); iterator.hasNext();) {
            final Element element = iterator.next();
            if (cache.isExpired(element)) {
                // An expired element
               //jlog.Debug(name + "Cache: Removing expired spool element " + element.getKey() + " from Disk Store");
                
                iterator.remove();
            }
        }

        // Clean up disk elements
        for (Iterator<?> iterator = diskElements.entrySet().iterator(); iterator.hasNext();) {
            final Map.Entry<Object,Object> entry = (Map.Entry<Object,Object>) iterator.next();
            final DiskElement element = (DiskElement) entry.getValue();
            if (now >= element.expiryTime) {
                // An expired element
               //jlog.Debug(name + "Cache: Removing expired spool element " + entry.getKey() + " from Disk Store");
                
                iterator.remove();
                freeBlock(element);
            }
        }
    }

    /**
     * Allocates a free block.
     */
    private DiskElement findFreeBlock(final int length) {
        for (int i = 0; i < freeSpace.size(); i++) {
            final DiskElement element = freeSpace.get(i);
            if (element.blockSize >= length) {
                freeSpace.remove(i);
                return element;
            }
        }
        return null;
    }

    /**
     * Returns a {@link String} representation of the {@link DiskStore}
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(256);
        sb.append("[ dataFile = ").append(dataFile.getAbsolutePath())
                .append(", active=").append(active)
                .append(", totalSize=").append(totalSize)
                .append(", status=").append(status)
                .append(", expiryThreadInterval = ").append(expiryThreadInterval)
                .append(" ]");
        return sb.toString();
    }

    /**
     * A reference to an on-disk elements.
     */
    private static class DiskElement implements Serializable {
        /**
		 * 
		 */
		private static final long serialVersionUID = -7264548730236858561L;

		/**
         * the file pointer
         */
        private long position;

        /**
         * The size used for data.
         */
        private int payloadSize;

        /**
         * the size of this element.
         */
        private int blockSize;

        /**
         * The expiry time in milliseconds
         */
        private long expiryTime;

    }

    /**
     * A background thread that writes objects to the file.
     */
    private class SpoolThread extends Thread {
        public SpoolThread() {
            super("Store " + name + " Spool Thread");
            setDaemon(true);
        }

        /**
         * Main thread method.
         */
        public void run() {
            spoolThreadMain();
        }
    }

    /**
     * A background thread that removes expired objects.
     */
    private class ExpiryThread extends Thread {
        public ExpiryThread() {
            super("Store " + name + " Expiry Thread");
            setDaemon(true);
        }

        /**
         * Main thread method.
         */
        public void run() {
            expiryThreadMain();
        }
    }

    /**
     * @return the total size of the data file and the index file, in bytes.
     */
    public long getTotalFileSize() {
        return getDataFileSize() + getIndexFileSize();
    }

    /**
     * @return the size of the data file in bytes.
     */
    public long getDataFileSize() {
        return dataFile.length();
    }

    /**
     * The design of the layout on the data file means that there will be small gaps created when DiskElements
     * are reused.
     * @return the sparseness, measured as the percentage of space in the Data File not used for holding data
     */
    public float calculateDataFileSparseness() {
        return 1 - ((float) getUsedDataSize() / (float) getDataFileSize());
    }

    /**
     * When elements are deleted, spaces are left in the file. These spaces are tracked and are reused
     * when new elements need to be written.
     * <p/>
     * This method indicates the actual size used for data, excluding holes. It can be compared with
     * {@link #getDataFileSize()} as a measure of fragmentation.
     */
    public long getUsedDataSize() {
        return totalSize;
    }

    /**
     * @return the size of the index file, in bytes.
     */
    public long getIndexFileSize() {
        if (indexFile == null) {
            return 0;
        } else {
            return indexFile.length();
        }
    }

    /**
     * @return the file name of the data file where the disk store stores data, without any path information.
     */
    public String getDataFileName() {
        return name + ".data";
    }

    /**
     * @return the disk path, which will be dependent on the operating system
     */
    public String getDataFilePath() {
        return diskPath;
    }

    /**
     * @return the file name of the index file, which maintains a record of elements and their addresses
     *         on the data file, without any path information.
     */
    public String getIndexFileName() {
        return name + ".index";
    }


    /**
     * The expiry thread is started provided the cache is not eternal
     * <p/>
     * If started it will continue to run until the {@link #dispose()} method is called,
     * at which time it should be interrupted and then die.
     *
     * @return true if an expiryThread was created and is still alive.
     */
    public boolean isExpiryThreadAlive() {
        if (expiryThread == null) {
            return false;
        } else {
            return expiryThread.isAlive();
        }
    }


}
