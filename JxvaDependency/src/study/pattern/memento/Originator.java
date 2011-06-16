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
 */
package study.pattern.memento;


/**
 *
 * @author  The Jxva Framework Foundation
 * @since   1.0
 * @version 2009-03-06 10:57:19 by Jxva
 */
public class Originator {
	
    private String state;
    public Memento createMemento(){
        return new Memento(state);
    }
    public void restoreMemento(Memento memento){
        this.state = memento.getState();
    }
    public String getState(){
       return this.state;
   }
   public void setState(String state){
       this.state=state;
       System.out.println("Current state = " + this.state);
   }
	
}
