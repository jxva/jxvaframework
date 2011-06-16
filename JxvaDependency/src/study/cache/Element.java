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
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;


/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:14:50 by Jxva
 */
public final class Element implements Serializable, Cloneable {

    private static final long serialVersionUID = 3343087714201120157L;

    private final Object key;

    private Object value;

    private long version;

    private long creationTime;

    private long lastAccessTime;

    private long nextToLastAccessTime;

    private long hitCount;

    private int timeToLive;

    private int timeToIdle;

    private long lastUpdateTime;

    private boolean eternal;

    private boolean lifespanSet;

    public Element(Serializable key, Serializable value, long version) {
        this((Object) key, (Object) value, version);

    }
    
    public Element(Object key, Object value, long version) {
        this.key = key;
        this.value = value;
        this.version = version;
        creationTime = System.currentTimeMillis();
        hitCount = 0;
    }

    public Element(Serializable key, Serializable value) {
        this((Object) key, (Object) value, 1L);
    }

    public Element(Object key, Object value) {
        this(key, value, 1L);
    }

    public final Serializable getKey() {
        Serializable keyAsSerializable=null;
        try {
            keyAsSerializable = (Serializable) key;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyAsSerializable;
    }

    public final Object getObjectKey() {
        return key;
    }

    public final Serializable getValue() {
        Serializable valueAsSerializable=null;
        try {
            valueAsSerializable = (Serializable) value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return valueAsSerializable;
    }

    public final Object getObjectValue() {
        return value;
    }


    public final boolean equals(Object object) {
        if (object == null) {
            return false;
        }

        Element element = (Element) object;
        if (key == null || element.getObjectKey() == null) {
            return false;
        }

        return key.equals(element.getObjectKey());
    }


    public void setTimeToLive(int timeToLiveSeconds) {
        this.timeToLive = timeToLiveSeconds;
        lifespanSet = true;
    }

    public void setTimeToIdle(int timeToIdleSeconds) {
        this.timeToIdle = timeToIdleSeconds;
        lifespanSet = true;
    }

    public final int hashCode() {
        return key.hashCode();
    }

    public final void setVersion(long version) {
        this.version = version;
    }

    public final long getCreationTime() {
        return creationTime;
    }

    public final void setCreateTime() {
        creationTime = System.currentTimeMillis();
    }

    public final long getVersion() {
        return version;
    }

    public final long getLastAccessTime() {
        return lastAccessTime;
    }

    final long getNextToLastAccessTime() {
        return nextToLastAccessTime;
    }

    public final long getHitCount() {
        return hitCount;
    }

    public final void resetAccessStatistics() {
        lastAccessTime = 0;
        nextToLastAccessTime = 0;
        hitCount = 0;
    }

    public final void updateAccessStatistics() {
        nextToLastAccessTime = lastAccessTime;
        lastAccessTime = System.currentTimeMillis();
        hitCount++;
    }

    public final void updateUpdateStatistics() {
        lastUpdateTime = System.currentTimeMillis();
        version = lastUpdateTime;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[ key = ").append(key)
                .append(", value=").append(value)
                .append(", version=").append(version)
                .append(", hitCount=").append(hitCount)
                .append(", creationTime = ").append(this.getCreationTime())
                .append(", lastAccessTime = ").append(this.getLastAccessTime())
                .append(" ]");

        return sb.toString();
    }

    public final Object clone() throws CloneNotSupportedException {
        super.clone();
        Element element = new Element(deepCopy(key), deepCopy(value), version);
        element.creationTime = creationTime;
        element.lastAccessTime = lastAccessTime;
        element.nextToLastAccessTime = nextToLastAccessTime;
        element.hitCount = hitCount;
        return element;
    }

    private Object deepCopy(Object oldValue) {
        Serializable newValue = null;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            oos = new ObjectOutputStream(bout);
            oos.writeObject(oldValue);
            ByteArrayInputStream bin = new ByteArrayInputStream(bout.toByteArray());
            ois = new ObjectInputStream(bin);
            newValue = (Serializable) ois.readObject();
        } catch (Exception e) {
        	System.out.println("Error cloning Element with key " + key    + " during serialization and deserialization of value");
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (ois != null) {
                    ois.close();
                }
            } catch (Exception e) {
            	System.out.println("Error closing Stream");
            }
        }
        return newValue;
    }

    public final long getSerializedSize() {
        if (!isSerializable()) {
            return 0;
        }
        long size = 0;
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bout);
            oos.writeObject(this);
            size = bout.size();
            return size;
        } catch (IOException e) {
            System.out.println("Error measuring element size for element with key " + key + ". Cause was: " + e.getMessage());
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
            } catch (Exception e) {
            	System.out.println("Error closing ObjectOutputStream");
            }
        }

        return size;
    }

    public final boolean isSerializable() {
        return key instanceof Serializable && value instanceof Serializable;
    }

    public final boolean isKeySerializable() {
        return key instanceof Serializable;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public boolean isExpired() {
        if (!lifespanSet) {
            return false;
        }

        long now = System.currentTimeMillis();
        long expirationTime = getExpirationTime();

        return now > expirationTime;
    }

    public long getExpirationTime() {
        if (!lifespanSet || eternal || (timeToLive == 0 && timeToIdle == 0)) {
            return Long.MAX_VALUE;
        }

        long expirationTime = 0;
        final long ONE_SECOND = 1000L;
        long ttlExpiry = creationTime + timeToLive * ONE_SECOND;

        long mostRecentTime = Math.max(creationTime, nextToLastAccessTime);
        long ttiExpiry = mostRecentTime + timeToIdle * ONE_SECOND;

        if (timeToLive != 0 && (timeToIdle == 0 || lastAccessTime == 0)) {
            expirationTime = ttlExpiry;
        } else if (timeToLive == 0) {
            expirationTime = ttiExpiry;
        } else {
            expirationTime = Math.min(ttlExpiry, ttiExpiry);
        }
        return expirationTime;
    }

    public boolean isEternal() {
        return eternal;
    }

    public void setEternal(boolean eternal) {
        this.eternal = eternal;
        lifespanSet = true;
    }

    public boolean isLifespanSet() {
        return lifespanSet;
    }


    public int getTimeToLive() {
        return timeToLive;
    }

    public int getTimeToIdle() {
        return timeToIdle;
    }
}