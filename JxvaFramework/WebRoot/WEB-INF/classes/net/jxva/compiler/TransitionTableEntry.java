package net.jxva.compiler;
public class TransitionTableEntry {
    public static enum EntryType {
        Normal,
        Call,
        Return
    }
    public NFAState nextState;    //下一个状态
    public EntryType entryType = EntryType.Normal;//表项类型:普通、调用和返回
    public State returnPoint; //返回调用方的状态,只在类型为调用时有效
}

 