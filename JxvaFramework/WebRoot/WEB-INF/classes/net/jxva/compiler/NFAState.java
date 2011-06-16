package net.jxva.compiler;
public class NFAState {

    public NFA nfa;//当前状态隶属于哪一个NFA
    private static int index = 1000;

    public static final int INVALID_STATE_NUMBER = -1;
    
    protected int stateNumber = INVALID_STATE_NUMBER;//状态编号,保证唯一！
    
    protected boolean acceptState ;//产生式对应的中止状态
    
    public void setAcceptState(boolean b){
    	
    }
    
    public boolean isAcceptState(){
    	return true;
    }
}

