package net.jxva.sql;
import java.util.HashSet;
import java.util.Set;

/** *//**
 * Token代表SQL语句中一个不可再分割的子单元
 * @author 何杨（heyang78@gmail.com）
 *
 * @since 2009-2-5 上午11:46:35
 * @version 1.00
 */
class Token{
    // Token的类型
    private int type;
    
    // 表示这个Token是关键字
    public static final int Type_Keyword=0;
    public static final int Type_LeftKeyword=2;
    public static final int Type_RightKeyword=4;
    
    // 表示这个Token是分隔符
    public static final int Type_Seperate=6;
    
    // 表示这个Token是表达式
    public static final int Type_Expression=8;
    
    // 表示这个Token是左括号
    public static final int Type_LeftBraket=10;
    
    // 表示这个Token是右括号
    public static final int Type_RightBraket=12;
        
    // Token的文本内容
    private String text;
    
    // 这个标记所处的深度
    private int depth;
    
    private static final Set<String> keywords;
    private static final Set<String> leftKeywords;
    private static final Set<String> rightKeywords;
    
    static{
        keywords=new HashSet<String>();
        
        keywords.add("select");
        keywords.add("from");
        keywords.add("where");
        keywords.add("on");
        keywords.add("having");
        keywords.add("values");
        keywords.add("update");
        keywords.add("set");        
        
        leftKeywords=new HashSet<String>();
        leftKeywords.add("order");
        leftKeywords.add("group");
        leftKeywords.add("delete");
        leftKeywords.add("insert");
        leftKeywords.add("left");
        leftKeywords.add("right");
        leftKeywords.add("inner");
        
        rightKeywords=new HashSet<String>();
        rightKeywords.add("by");
        rightKeywords.add("into");
        rightKeywords.add("join");
    }    
    
    private static boolean isKeyWords(String str){
        return keywords.contains(str);
    }
    
    private static boolean isLeftKeyWords(String str){
        return leftKeywords.contains(str);
    }
    
    private static boolean isRightKeyWords(String str){
        return rightKeywords.contains(str);
    }
    
    public Token(String text,int depth){
        if("(".equals(text)){
            this.type=Type_LeftBraket;
        }
        else if(")".equals(text)){
            this.type=Type_RightBraket;
        }
        else if(isKeyWords(text.toLowerCase())){
            this.type=Type_Keyword;
        }
        else if(isLeftKeyWords(text.toLowerCase())){
            this.type=Type_LeftKeyword;
        }
        else if(isRightKeyWords(text.toLowerCase())){
            this.type=Type_RightKeyword;
        }
        else{
            this.type=Type_Expression;
        }
        
        this.text=text;
        this.depth=depth;
    }
    
    
    
    public Token(char c,int depth){
        this(String.valueOf(c),depth);
    }
    
    public String toString(){
        StringBuffer sb=new StringBuffer();
        
        sb.append(getDepthSpace());        
        sb.append(getPreTokenSpace());
        sb.append(text);
        sb.append(getAfterTokenWhiteSpace());
        
        return sb.toString();
    }
    
    /** *//**
     * 取得代表深度的前置空白
     * @return
     */
    private String getDepthSpace(){
        StringBuffer sb=new StringBuffer("");
        
        for(int i=0;i<depth;i++){
            sb.append(Consts.FourSpace);
        }
        
        return sb.toString();
    }
    
    /** *//**
     * 取得标识符前的空白
     * @return
     */
    private String getPreTokenSpace(){
        if(type==Type_Expression){
            return Consts.FourSpace;
        }
        else if(type==Type_LeftBraket){
            return Consts.FourSpace;
        }
        else if(type==Type_RightBraket){
            return Consts.FourSpace;
        }
        else if(type==Type_RightKeyword){
            return Consts.Space;
        }
        
        return "";
    }
    
    /** *//**
     * 取得标识符后的白字符
     * @return
     */
    private String getAfterTokenWhiteSpace(){
        if(type==Type_LeftKeyword){
            return "";
        }
        
        return Consts.NewLine;
    }
    
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }    
}