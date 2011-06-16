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

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Iterator;

/**
 * An implementation of a Map which has a maximum size and uses a Least Recently Used
 * algorithm to remove items from the Map when the maximum size is reached and new items are added.
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2008-11-27 09:15:10 by Jxva
 */
@SuppressWarnings("unchecked")
public class LRUMap<K,V> extends SequencedHashMap<K,V> implements Externalizable {
    
    private static final long serialVersionUID = 2197433140769957051L;
	
    private int maximumSize = 0;

    public LRUMap() {
        this( 100 );
    }

    public LRUMap(int i) {
        super( i );
        maximumSize = i;
    }
    
	public V get(Object key) {
        if(!containsKey(key)) return null;

        V value = remove(key);
        super.put((K)key,value);
        return value;
    }
  
    public V put(K key,V value ) {
        int mapSize = size();
        V retval = null;
        if ( mapSize >= maximumSize ) {
            if (!containsKey(key)) {
                removeLRU();
            }
        }
        retval = super.put(key,value);
        return retval;
    }

    protected void removeLRU() {
        Object key = getFirstKey();
        Object value = super.get(key);
        remove(key);
        processRemovedLRU(key,value);
    }

    protected void processRemovedLRU(Object key, Object value) {
    }
 
    // Externalizable interface
    //-------------------------------------------------------------------------        
    public void readExternal( ObjectInput in )  throws IOException, ClassNotFoundException {
        maximumSize = in.readInt();
        int size = in.readInt();
        
        for( int i = 0; i < size; i++ )  {
            K key = (K)in.readObject();
            V value = (V)in.readObject();
            put(key,value);
        }
    }

    public void writeExternal( ObjectOutput out ) throws IOException {
        out.writeInt( maximumSize );
        out.writeInt( size() );
        for( Iterator<?> iterator = keySet().iterator(); iterator.hasNext(); ) {
            Object key = iterator.next();
            out.writeObject( key );
            Object value = super.get( key );
            out.writeObject( value );
        }
    }
    
    

    public int getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(int maximumSize) {
        this.maximumSize = maximumSize;
        while (size() > maximumSize) {
            removeLRU();
        }
    }
}
