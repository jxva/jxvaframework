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
package com.jxva.entity;

import java.util.HashMap;
import java.util.Map;


/**
	font-family: Lucida Sans Unicode
	 
	元音(Vowels)
	前元音 [i:] 				[i] 				[e] 			[æ][&#230;] 
	中元音 [ə:][&#601;:] 	[ə][&#601;] 		[ʌ][&#652;]
	后元音 [ɑ:][&#593;:] 		[ɔ:][&#596;:] 		[ɔ][&#596;]  	[u] 			[u:]
	合口双元音 [ei] 			[ai] 				[ɔi][&#596;i] 	[əu][&#601;u]	[au]
	集中双元音 [iə][i&#601;] 	[ɛə][&#603;&#601;]	[uə][u&#601;]
	
	辅音(Consonants)
	清辅音 	[p] [t]  [k] [f] [s] [θ][&#952;] [ts] [tr] [ʃ][&#643;] [tʃ][t&#643;]
	浊辅音 	[b] [d]  [g] [v] [z] [ð][&#240;] [dz] [dr] [ʒ][&#658;] [dʒ][d&#658;]
	其它辅音 [h] [l]  [r] [m] [n] [ŋ][&#331;] [j]  [w]

 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-11-03 08:51:34 by Jxva
 */
public class Phonetic {
	
	private static final Map<Character,String> map=new HashMap<Character,String>(50);
	
	static{
		map.put('æ',"&#230;");
		map.put('ə',"&#601;");
		map.put('ʌ',"&#652;");
		map.put('ɑ',"&#593;");
		map.put('ɔ',"&#596;");
		map.put('ɛ',"&#603;");
		map.put('θ',"&#952;");
		map.put('ð',"&#240;");
		map.put('ʃ',"&#643;");
		map.put('ʒ',"&#658;");
		map.put('ŋ',"&#331;");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
