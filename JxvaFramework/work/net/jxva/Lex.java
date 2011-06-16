package net.jxva;
public   class   Lex  
  {  
    private   String   buffer;  
    private   int   colNum   =   0;  
    private   char   curChar;  
   
    public   Lex(String   input)  
    {  
      this.buffer   =   input;  
      curChar   =   getChar();  
    }  
   
    private   char   getChar()  
    {  
      char   ch   =   '#';  
      while(buffer!=null   &&   colNum<buffer.length())  
      {  
        ch   =   buffer.charAt(colNum);  
        colNum++;  
        break;  
      }  
   
      return   ch;  
    }  
   
    private   void   skipBlank()  
    {  
      while(curChar   ==   ' ')  
        curChar   =   getChar();  
   
    }  
   
    public   Token   getToken()   throws   ParserException  
    {  
      Token   tk   =   null;  
      if(curChar   ==   ' ')  
        skipBlank();  
   
      switch(curChar)  
      {  
        case   '(':  
          tk   =   new   Token("(",Token.LEFTPARENTHESIS);  
          curChar   =   getChar();  
          break;  
        case   ')':  
          tk   =   new   Token(")",Token.RIGHTPARENTHESIS);  
          curChar   =   getChar();  
          break;  
        case   '+':  
          tk   =   new   Token("+",Token.ADD);  
          curChar   =   getChar();  
          break;  
        case   '-':  
          tk   =   new   Token("-",Token.SUB);  
          curChar   =   getChar();  
          break;  
        case   '*':  
          tk   =   new   Token("*",Token.MUL);  
          curChar   =   getChar();  
          break;  
        case   '/':  
          tk   =   new   Token("/",Token.DIV);  
          curChar   =   getChar();  
          break;  
        case   '0':  
        case   '1':  
        case   '2':  
        case   '3':  
        case   '4':  
        case   '5':  
        case   '6':  
        case   '7':  
        case   '8':  
        case   '9':  
        case   '.':  
          tk   =   parseNumber();  
          break;  
        case   '#':  
        case   '=':  
          tk   =   null;  
          break;  
        default:  
          tk   =   new   Token("Invalid   character",Token.INVALID);  
          curChar   =   getChar();  
          break;  
      }  
   
      return   tk;  
    }  
   
    private   Token   parseNumber()   throws   ParserException  
    {  
      int   dotNum   =   0;  
      boolean   key   =   true;  
      StringBuffer   buf   =   new   StringBuffer();  
      buf.append(curChar);  
      if(curChar   ==   '.')   dotNum++;  
   
      while(key)  
      {  
        curChar   =   getChar();  
        switch(curChar)  
        {  
        case   '0':  
        case   '1':  
        case   '2':  
        case   '3':  
        case   '4':  
        case   '5':  
        case   '6':  
        case   '7':  
        case   '8':  
        case   '9':  
          buf.append(curChar);  
          continue;  
        case   '.':  
          dotNum++;  
          if(dotNum   >   1)  
            throw   new   ParserException("the   string   inputed   error   at   column:"   +   colNum);  
          buf.append('.');  
          continue;  
        default:  
          key   =   false;  
          continue;  
        }  
      }  
      return   new   Token(buf.toString(),Token.NUM);  
    }  
     
    public   static   void   main(String[]   args)   {  
      try   {  
    	  String s="8+3-9*4/4";
  Lex   lex   =   new   Lex(s);  
        while(true)   {  
          Token   tk   =   lex.getToken();  
          if(tk   ==   null)   {  
            break;  
          }  
          else  
            System.out.println(tk.getContent());  
        }  
      }  
      catch(Exception   e)   {  
        e.printStackTrace();  
      }  
    }  
  } 