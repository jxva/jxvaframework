/*
 * Copyright @ 2006-2010 by The Jxva Framework Foundation
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
 */
package com.jxva.cache;

import com.jxva.util.RandomUtil;

/**
 * Contains common LFU policy code for use between the LFUWeakCache, which also
 * uses an LFUPolicy for evictions.
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-01-23 09:53:55 by Jxva
 */
public final class LFUPolicy {

    private static final int DEFAULT_SAMPLE_SIZE = 30;
    private LFUPolicy() {
    }

    /**
     * sampleSize how many samples to take
     * @return the smaller of the map size and the default sample size of 30
     */
    private static int calculateSampleSize(int populationSize) {
        if (populationSize < DEFAULT_SAMPLE_SIZE) {
            return populationSize;
        } else {
            return DEFAULT_SAMPLE_SIZE;
        }

    }


    /**
     * Finds the least hit of the sampled elements provided
     * @param sampledElements this should be a random subset of the population
     * @param justAdded we never want to select the element just added. May be null.
     * @return the least hit
     */
    public static LFUPolicy.Metadata leastHit(LFUPolicy.Metadata[] sampledElements, LFUPolicy.Metadata justAdded) {
        //edge condition when Memory Store configured to size 0
        if (sampledElements.length == 1 && justAdded != null) {
            return justAdded;
        }
        LFUPolicy.Metadata lowestElement = null;
        for (int i = 0; i < sampledElements.length; i++) {
            LFUPolicy.Metadata element = sampledElements[i];
            if (lowestElement == null) {
                if (!element.equals(justAdded)) {
                    lowestElement = element;
                }
            } else {
                if (element.getHitCount() < lowestElement.getHitCount() && !element.equals(justAdded)) {
                    lowestElement = element;
                }
            }
        }
        return lowestElement;
    }

    /**
     * Generates a random sample from a population
     * @param populationSize the size to draw from
     */
    public static int[] generateRandomSample(int populationSize) {
        int sampleSize = LFUPolicy.calculateSampleSize(populationSize);
        int[] offsets = new int[sampleSize];
        int maxOffset = populationSize / sampleSize;
        for (int i = 0; i < sampleSize; i++) {
            offsets[i] = RandomUtil.RANDOM.nextInt(maxOffset);
        }
        return offsets;
    }

    public static interface Metadata {
    	
        Object getKey();

        long getHitCount();

    }
}
