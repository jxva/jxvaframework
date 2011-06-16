package net.jxva;
class   ParserException   extends   Exception  
  {  
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public   ParserException(String   message)  
    {  
      super(message);  
    }  
  }  
  class   Token  
  {  
    public   final   static   int   INVALID   =   -1;  
    public   final   static   int   LEFTPARENTHESIS   =   0;  
    public   final   static   int   RIGHTPARENTHESIS   =   1;  
    public   final   static   int   ADD   =   2;  
    public   final   static   int   SUB   =   3;  
    public   final   static   int   MUL   =   4;  
    public   final   static   int   DIV   =   5;  
    public   final   static   int   NUM   =   6;  
   
    private   String   content;  
    private   int   type;  
   
    public   Token(String   content,   int   type)  
    {  
      this.content   =   content;  
      this.type   =   type;  
    }  
   
    public   String   getContent()  
    {  
      return   content;  
    }  
   
    public   double   getDoubleValue()  
    {  
      return   Double.parseDouble(content);  
    }  
   
    public   int   getType()  
    {  
      return   type;  
    }  
  }  
   
  