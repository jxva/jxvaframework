package net.jxva.compiler;

import java.util.Vector;

public class NFA {

    public Vector<NFAState> finalStateSet;      //所有状态的集合
    public NFAState startState;                 //初时状态
    public TransitionTable transitionTable;     //转换矩阵
    
    public boolean getStartState(){
    	return true;
    }

    public void setstartState(NFAState s){}
    
    public void setfinalStateSet(Vector<NFAState> v){}
    
    public void settransitionTable(TransitionTable s){}
}

