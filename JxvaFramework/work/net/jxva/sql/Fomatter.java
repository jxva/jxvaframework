package net.jxva.sql;
/** *//**
 * SQL整形器类
 * @author 何杨（heyang78@gmail.com）
 *
 * @since 2009-2-5 上午11:45:38
 * @version 1.00
 */
class Fomatter{
    // 需要整形的SQL语句
    private String sql;
    
    // 整形完毕的SQL语句
    private String formatedSql;
    
    // 空格的深度,用于嵌套Sql语句
    private int spaceDepth;
    
    // 在sql逐字读取时当前字符所在的位置
    private int currCharPos;
    
    /** *//**
     * 构造函数，入口点
     * 2009年2月5日11:52:42
     * @param sql
     */
    public Fomatter(String sql){
        this.sql=sql;
                
        format();
    }
        
    /** *//**
     * 取得整形完毕的Sql语句，出口点
     * 2009年2月5日11:52:45
     * @return
     */
    public String getFomattedSql(){
        return formatedSql;
    }
    
    /** *//**
     * 对sql进行整形，整形的结果放在FormatedSql中
     * 2009年2月5日12:16:08
     */
    private void format(){
        formatedSql="";
        
        Token token=getToken();
        
        while(token!=null){
            formatedSql+=token;
            token=getToken();
        }
    }
    
    /** *//**
     * 取得下一个标识符
     * 2009年2月5日12:22:41
     * @return
     */
    private Token getToken(){
        // 初始化
        String tokenText="";
        //tokenType=TokenType_None;
        
        // 结束即终止
        if(currCharPos==sql.length()){
            return null;
        }
        
        // 跳过空白字符
        while(currCharPos<sql.length() && currCharIsWhiteSpace(currCharPos)){
            currCharPos++;
        }
        
        // 结束即终止
        if(currCharPos==sql.length()){
            return null;
        }
        
        // 不是空白字符的话就连续往下取
        while(currCharPos<sql.length() ){
            char currChar=sql.charAt(currCharPos);
                        
            if(currChar=='('){    
                if(tokenText.trim().length()>0){
                    break;
                }
                
                Token token=new Token(currChar,spaceDepth);
                spaceDepth++;    
                currCharPos++;    
                return token;
            }
            else if(currChar==')'){
                if(tokenText.trim().length()>0){
                    break;
                }
                
                spaceDepth--;                
                Token token=new Token(currChar,spaceDepth);
                currCharPos++;    
                return token;
            }
            else if(currChar==','){
                tokenText+=currChar;
                currCharPos++;    
                break;
            }
                        
            if(currCharIsWhiteSpace(currCharPos)==true){
                break;
            }
            
            tokenText+=currChar;
            currCharPos++;            
        }
                
        Token token=new Token(tokenText,spaceDepth);
                
        return token;
    }
    
    /** *//**
     * 判断currentCharIndex在originalSql指向的字符是否空白字符
     * @param currentCharIndex
     * @return
     */
    private boolean currCharIsWhiteSpace(int currentCharIndex){
        return Character.isWhitespace(sql.charAt(currentCharIndex));
    }
}