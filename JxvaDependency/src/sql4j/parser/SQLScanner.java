//Author: Jianguo Lu jglu@cs.toronto.edu
//Feb 2001.
package sql4j.parser;
import java.util.*;
import java.lang.*;
import java_cup.runtime.*;
import java.io.*;
import org.apache.log4j.Category;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.BasicConfigurator;


class SQLScanner {
	private final int YY_BUFFER_SIZE = 512;
	private final int YY_F = -1;
	private final int YY_NO_STATE = -1;
	private final int YY_NOT_ACCEPT = 0;
	private final int YY_START = 1;
	private final int YY_END = 2;
	private final int YY_NO_ANCHOR = 4;
	private final int YY_BOL = 256;
	private final int YY_EOF = 257;

static Category cat = Category.getInstance(SQLScanner.class.getName());
static SQLScanner foo;
public static void init(InputStream is) {
  BasicConfigurator.configure();
  cat.setPriority(org.apache.log4j.Priority.ERROR);
  foo = new SQLScanner(is);
}
public static Symbol next_token() throws java.io.IOException {
  Symbol s= foo.yylex();
  String prompt="";
  if (s.value !=null && s.value instanceof String){
	  prompt=prompt+" "+ s.value;
  }
  cat.debug(prompt);
  return s;
}
public class IntStack {
   Stack stack; 
   public IntStack(){stack=new Stack(); }
   public IntStack(int i)
	 { stack=new Stack(); stack.push(new Integer(i));}
   public void push(int i){ 
	 stack.push(new Integer(i)); 
	 cat.debug("States:"+stack.toString());
   }
   public int peek(){ 
	   Object o= stack.peek();
	   if (o instanceof Integer){
		   return ((Integer)o).intValue();
	   }else {
		   cat.error("Int expected.");
		   return -1;
	   }
   }
   public int  popPeek(){
	 cat.debug("state stack "+stack.toString());
	   cat.debug("current state: "+this.peek()+"   ==> ");
	   int result= -1;
	   stack.pop();
	   Object o=stack.peek();
	   if (o instanceof Integer){ 
		   result=((Integer)o).intValue();
		   cat.debug("next state:"+result);
	   } else
		   cat.error("Int expected;");
	   return result;
   }
   public int  pop(){
	 Object o=stack.pop();
	 if (o instanceof Integer){ 
	   return ((Integer)o).intValue();
	 } else 
	   cat.error("Int expected;");
	 return -1;
   }
 }
 IntStack states=new IntStack();
	private java.io.BufferedReader yy_reader;
	private int yy_buffer_index;
	private int yy_buffer_read;
	private int yy_buffer_start;
	private int yy_buffer_end;
	private char yy_buffer[];
	private int yychar;
	private int yyline;
	private boolean yy_at_bol;
	private int yy_lexical_state;

	SQLScanner (java.io.Reader reader) {
		this ();
		if (null == reader) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(reader);
	}

	SQLScanner (java.io.InputStream instream) {
		this ();
		if (null == instream) {
			throw (new Error("Error: Bad input stream initializer."));
		}
		yy_reader = new java.io.BufferedReader(new java.io.InputStreamReader(instream));
	}

	private SQLScanner () {
		yy_buffer = new char[YY_BUFFER_SIZE];
		yy_buffer_read = 0;
		yy_buffer_index = 0;
		yy_buffer_start = 0;
		yy_buffer_end = 0;
		yychar = 0;
		yyline = 0;
		yy_at_bol = true;
		yy_lexical_state = YYINITIAL;

	  states.push(YYINITIAL);
	}

	private boolean yy_eof_done = false;
	private final int COMMENT_BLK = 1;
	private final int YYINITIAL = 0;
	private final int yy_state_dtrans[] = {
		0,
		96
	};
	private void yybegin (int state) {
		yy_lexical_state = state;
	}
	private int yy_advance ()
		throws java.io.IOException {
		int next_read;
		int i;
		int j;

		if (yy_buffer_index < yy_buffer_read) {
			return yy_buffer[yy_buffer_index++];
		}

		if (0 != yy_buffer_start) {
			i = yy_buffer_start;
			j = 0;
			while (i < yy_buffer_read) {
				yy_buffer[j] = yy_buffer[i];
				++i;
				++j;
			}
			yy_buffer_end = yy_buffer_end - yy_buffer_start;
			yy_buffer_start = 0;
			yy_buffer_read = j;
			yy_buffer_index = j;
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}

		while (yy_buffer_index >= yy_buffer_read) {
			if (yy_buffer_index >= yy_buffer.length) {
				yy_buffer = yy_double(yy_buffer);
			}
			next_read = yy_reader.read(yy_buffer,
					yy_buffer_read,
					yy_buffer.length - yy_buffer_read);
			if (-1 == next_read) {
				return YY_EOF;
			}
			yy_buffer_read = yy_buffer_read + next_read;
		}
		return yy_buffer[yy_buffer_index++];
	}
	private void yy_move_end () {
		if (yy_buffer_end > yy_buffer_start &&
		    '\n' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
		if (yy_buffer_end > yy_buffer_start &&
		    '\r' == yy_buffer[yy_buffer_end-1])
			yy_buffer_end--;
	}
	private boolean yy_last_was_cr=false;
	private void yy_mark_start () {
		int i;
		for (i = yy_buffer_start; i < yy_buffer_index; ++i) {
			if ('\n' == yy_buffer[i] && !yy_last_was_cr) {
				++yyline;
			}
			if ('\r' == yy_buffer[i]) {
				++yyline;
				yy_last_was_cr=true;
			} else yy_last_was_cr=false;
		}
		yychar = yychar
			+ yy_buffer_index - yy_buffer_start;
		yy_buffer_start = yy_buffer_index;
	}
	private void yy_mark_end () {
		yy_buffer_end = yy_buffer_index;
	}
	private void yy_to_mark () {
		yy_buffer_index = yy_buffer_end;
		yy_at_bol = (yy_buffer_end > yy_buffer_start) &&
		            ('\r' == yy_buffer[yy_buffer_end-1] ||
		             '\n' == yy_buffer[yy_buffer_end-1] ||
		             2028/*LS*/ == yy_buffer[yy_buffer_end-1] ||
		             2029/*PS*/ == yy_buffer[yy_buffer_end-1]);
	}
	private java.lang.String yytext () {
		return (new java.lang.String(yy_buffer,
			yy_buffer_start,
			yy_buffer_end - yy_buffer_start));
	}
	private int yylength () {
		return yy_buffer_end - yy_buffer_start;
	}
	private char[] yy_double (char buf[]) {
		int i;
		char newbuf[];
		newbuf = new char[2*buf.length];
		for (i = 0; i < buf.length; ++i) {
			newbuf[i] = buf[i];
		}
		return newbuf;
	}
	private final int YY_E_INTERNAL = 0;
	private final int YY_E_MATCH = 1;
	private java.lang.String yy_error_string[] = {
		"Error: Internal error.\n",
		"Error: Unmatched input.\n"
	};
	private void yy_error (int code,boolean fatal) {
		java.lang.System.out.print(yy_error_string[code]);
		java.lang.System.out.flush();
		if (fatal) {
			throw new Error("Fatal Error.\n");
		}
	}
	private int[][] unpackFromString(int size1, int size2, String st) {
		int colonIndex = -1;
		String lengthString;
		int sequenceLength = 0;
		int sequenceInteger = 0;

		int commaIndex;
		String workString;

		int res[][] = new int[size1][size2];
		for (int i= 0; i < size1; i++) {
			for (int j= 0; j < size2; j++) {
				if (sequenceLength != 0) {
					res[i][j] = sequenceInteger;
					sequenceLength--;
					continue;
				}
				commaIndex = st.indexOf(',');
				workString = (commaIndex==-1) ? st :
					st.substring(0, commaIndex);
				st = st.substring(commaIndex+1);
				colonIndex = workString.indexOf(':');
				if (colonIndex == -1) {
					res[i][j]=Integer.parseInt(workString);
					continue;
				}
				lengthString =
					workString.substring(colonIndex+1);
				sequenceLength=Integer.parseInt(lengthString);
				workString=workString.substring(0,colonIndex);
				sequenceInteger=Integer.parseInt(workString);
				res[i][j] = sequenceInteger;
				sequenceLength--;
			}
		}
		return res;
	}
	private int yy_acpt[] = {
		/* 0 */ YY_NO_ANCHOR,
		/* 1 */ YY_NO_ANCHOR,
		/* 2 */ YY_NO_ANCHOR,
		/* 3 */ YY_NO_ANCHOR,
		/* 4 */ YY_NO_ANCHOR,
		/* 5 */ YY_NO_ANCHOR,
		/* 6 */ YY_NO_ANCHOR,
		/* 7 */ YY_NO_ANCHOR,
		/* 8 */ YY_NO_ANCHOR,
		/* 9 */ YY_NO_ANCHOR,
		/* 10 */ YY_NO_ANCHOR,
		/* 11 */ YY_NO_ANCHOR,
		/* 12 */ YY_NO_ANCHOR,
		/* 13 */ YY_NO_ANCHOR,
		/* 14 */ YY_NO_ANCHOR,
		/* 15 */ YY_NO_ANCHOR,
		/* 16 */ YY_NO_ANCHOR,
		/* 17 */ YY_NOT_ACCEPT,
		/* 18 */ YY_NO_ANCHOR,
		/* 19 */ YY_NO_ANCHOR,
		/* 20 */ YY_NO_ANCHOR,
		/* 21 */ YY_NO_ANCHOR,
		/* 22 */ YY_NO_ANCHOR,
		/* 23 */ YY_NO_ANCHOR,
		/* 24 */ YY_NO_ANCHOR,
		/* 25 */ YY_NO_ANCHOR,
		/* 26 */ YY_NO_ANCHOR,
		/* 27 */ YY_NO_ANCHOR,
		/* 28 */ YY_NO_ANCHOR,
		/* 29 */ YY_NO_ANCHOR,
		/* 30 */ YY_NO_ANCHOR,
		/* 31 */ YY_NO_ANCHOR,
		/* 32 */ YY_NO_ANCHOR,
		/* 33 */ YY_NO_ANCHOR,
		/* 34 */ YY_NO_ANCHOR,
		/* 35 */ YY_NO_ANCHOR,
		/* 36 */ YY_NO_ANCHOR,
		/* 37 */ YY_NO_ANCHOR,
		/* 38 */ YY_NO_ANCHOR,
		/* 39 */ YY_NO_ANCHOR,
		/* 40 */ YY_NO_ANCHOR,
		/* 41 */ YY_NO_ANCHOR,
		/* 42 */ YY_NO_ANCHOR,
		/* 43 */ YY_NO_ANCHOR,
		/* 44 */ YY_NO_ANCHOR,
		/* 45 */ YY_NO_ANCHOR,
		/* 46 */ YY_NO_ANCHOR,
		/* 47 */ YY_NO_ANCHOR,
		/* 48 */ YY_NO_ANCHOR,
		/* 49 */ YY_NO_ANCHOR,
		/* 50 */ YY_NO_ANCHOR,
		/* 51 */ YY_NO_ANCHOR,
		/* 52 */ YY_NO_ANCHOR,
		/* 53 */ YY_NO_ANCHOR,
		/* 54 */ YY_NO_ANCHOR,
		/* 55 */ YY_NO_ANCHOR,
		/* 56 */ YY_NO_ANCHOR,
		/* 57 */ YY_NO_ANCHOR,
		/* 58 */ YY_NO_ANCHOR,
		/* 59 */ YY_NO_ANCHOR,
		/* 60 */ YY_NO_ANCHOR,
		/* 61 */ YY_NO_ANCHOR,
		/* 62 */ YY_NO_ANCHOR,
		/* 63 */ YY_NO_ANCHOR,
		/* 64 */ YY_NO_ANCHOR,
		/* 65 */ YY_NO_ANCHOR,
		/* 66 */ YY_NO_ANCHOR,
		/* 67 */ YY_NO_ANCHOR,
		/* 68 */ YY_NO_ANCHOR,
		/* 69 */ YY_NO_ANCHOR,
		/* 70 */ YY_NO_ANCHOR,
		/* 71 */ YY_NO_ANCHOR,
		/* 72 */ YY_NO_ANCHOR,
		/* 73 */ YY_NO_ANCHOR,
		/* 74 */ YY_NO_ANCHOR,
		/* 75 */ YY_NO_ANCHOR,
		/* 76 */ YY_NO_ANCHOR,
		/* 77 */ YY_NOT_ACCEPT,
		/* 78 */ YY_NO_ANCHOR,
		/* 79 */ YY_NO_ANCHOR,
		/* 80 */ YY_NO_ANCHOR,
		/* 81 */ YY_NO_ANCHOR,
		/* 82 */ YY_NO_ANCHOR,
		/* 83 */ YY_NO_ANCHOR,
		/* 84 */ YY_NO_ANCHOR,
		/* 85 */ YY_NOT_ACCEPT,
		/* 86 */ YY_NO_ANCHOR,
		/* 87 */ YY_NO_ANCHOR,
		/* 88 */ YY_NOT_ACCEPT,
		/* 89 */ YY_NO_ANCHOR,
		/* 90 */ YY_NOT_ACCEPT,
		/* 91 */ YY_NO_ANCHOR,
		/* 92 */ YY_NOT_ACCEPT,
		/* 93 */ YY_NO_ANCHOR,
		/* 94 */ YY_NOT_ACCEPT,
		/* 95 */ YY_NO_ANCHOR,
		/* 96 */ YY_NOT_ACCEPT,
		/* 97 */ YY_NO_ANCHOR,
		/* 98 */ YY_NO_ANCHOR,
		/* 99 */ YY_NO_ANCHOR,
		/* 100 */ YY_NO_ANCHOR,
		/* 101 */ YY_NO_ANCHOR,
		/* 102 */ YY_NO_ANCHOR,
		/* 103 */ YY_NO_ANCHOR,
		/* 104 */ YY_NO_ANCHOR,
		/* 105 */ YY_NO_ANCHOR,
		/* 106 */ YY_NO_ANCHOR,
		/* 107 */ YY_NO_ANCHOR,
		/* 108 */ YY_NO_ANCHOR,
		/* 109 */ YY_NO_ANCHOR,
		/* 110 */ YY_NO_ANCHOR,
		/* 111 */ YY_NO_ANCHOR,
		/* 112 */ YY_NO_ANCHOR,
		/* 113 */ YY_NO_ANCHOR,
		/* 114 */ YY_NO_ANCHOR,
		/* 115 */ YY_NO_ANCHOR,
		/* 116 */ YY_NO_ANCHOR,
		/* 117 */ YY_NO_ANCHOR,
		/* 118 */ YY_NO_ANCHOR,
		/* 119 */ YY_NO_ANCHOR,
		/* 120 */ YY_NO_ANCHOR,
		/* 121 */ YY_NO_ANCHOR,
		/* 122 */ YY_NO_ANCHOR,
		/* 123 */ YY_NO_ANCHOR,
		/* 124 */ YY_NO_ANCHOR,
		/* 125 */ YY_NO_ANCHOR,
		/* 126 */ YY_NO_ANCHOR,
		/* 127 */ YY_NO_ANCHOR,
		/* 128 */ YY_NO_ANCHOR,
		/* 129 */ YY_NO_ANCHOR,
		/* 130 */ YY_NO_ANCHOR,
		/* 131 */ YY_NO_ANCHOR,
		/* 132 */ YY_NO_ANCHOR,
		/* 133 */ YY_NO_ANCHOR,
		/* 134 */ YY_NO_ANCHOR,
		/* 135 */ YY_NO_ANCHOR,
		/* 136 */ YY_NO_ANCHOR,
		/* 137 */ YY_NO_ANCHOR,
		/* 138 */ YY_NO_ANCHOR,
		/* 139 */ YY_NO_ANCHOR,
		/* 140 */ YY_NO_ANCHOR,
		/* 141 */ YY_NO_ANCHOR,
		/* 142 */ YY_NO_ANCHOR,
		/* 143 */ YY_NO_ANCHOR,
		/* 144 */ YY_NO_ANCHOR,
		/* 145 */ YY_NO_ANCHOR,
		/* 146 */ YY_NO_ANCHOR,
		/* 147 */ YY_NO_ANCHOR,
		/* 148 */ YY_NO_ANCHOR,
		/* 149 */ YY_NO_ANCHOR,
		/* 150 */ YY_NO_ANCHOR,
		/* 151 */ YY_NO_ANCHOR,
		/* 152 */ YY_NO_ANCHOR,
		/* 153 */ YY_NO_ANCHOR,
		/* 154 */ YY_NO_ANCHOR,
		/* 155 */ YY_NO_ANCHOR,
		/* 156 */ YY_NO_ANCHOR,
		/* 157 */ YY_NO_ANCHOR,
		/* 158 */ YY_NO_ANCHOR,
		/* 159 */ YY_NO_ANCHOR,
		/* 160 */ YY_NO_ANCHOR,
		/* 161 */ YY_NO_ANCHOR,
		/* 162 */ YY_NO_ANCHOR,
		/* 163 */ YY_NO_ANCHOR,
		/* 164 */ YY_NO_ANCHOR,
		/* 165 */ YY_NO_ANCHOR,
		/* 166 */ YY_NO_ANCHOR,
		/* 167 */ YY_NO_ANCHOR,
		/* 168 */ YY_NO_ANCHOR,
		/* 169 */ YY_NO_ANCHOR,
		/* 170 */ YY_NO_ANCHOR,
		/* 171 */ YY_NO_ANCHOR,
		/* 172 */ YY_NO_ANCHOR,
		/* 173 */ YY_NO_ANCHOR,
		/* 174 */ YY_NO_ANCHOR,
		/* 175 */ YY_NO_ANCHOR,
		/* 176 */ YY_NO_ANCHOR,
		/* 177 */ YY_NO_ANCHOR,
		/* 178 */ YY_NO_ANCHOR,
		/* 179 */ YY_NO_ANCHOR,
		/* 180 */ YY_NO_ANCHOR,
		/* 181 */ YY_NO_ANCHOR,
		/* 182 */ YY_NO_ANCHOR,
		/* 183 */ YY_NO_ANCHOR,
		/* 184 */ YY_NO_ANCHOR,
		/* 185 */ YY_NO_ANCHOR,
		/* 186 */ YY_NO_ANCHOR,
		/* 187 */ YY_NO_ANCHOR,
		/* 188 */ YY_NO_ANCHOR,
		/* 189 */ YY_NO_ANCHOR,
		/* 190 */ YY_NO_ANCHOR,
		/* 191 */ YY_NO_ANCHOR,
		/* 192 */ YY_NO_ANCHOR,
		/* 193 */ YY_NO_ANCHOR,
		/* 194 */ YY_NO_ANCHOR,
		/* 195 */ YY_NO_ANCHOR,
		/* 196 */ YY_NO_ANCHOR,
		/* 197 */ YY_NO_ANCHOR,
		/* 198 */ YY_NO_ANCHOR,
		/* 199 */ YY_NO_ANCHOR,
		/* 200 */ YY_NO_ANCHOR,
		/* 201 */ YY_NO_ANCHOR,
		/* 202 */ YY_NO_ANCHOR,
		/* 203 */ YY_NO_ANCHOR,
		/* 204 */ YY_NO_ANCHOR,
		/* 205 */ YY_NO_ANCHOR,
		/* 206 */ YY_NO_ANCHOR,
		/* 207 */ YY_NO_ANCHOR,
		/* 208 */ YY_NO_ANCHOR,
		/* 209 */ YY_NO_ANCHOR,
		/* 210 */ YY_NO_ANCHOR,
		/* 211 */ YY_NO_ANCHOR,
		/* 212 */ YY_NO_ANCHOR,
		/* 213 */ YY_NO_ANCHOR,
		/* 214 */ YY_NO_ANCHOR,
		/* 215 */ YY_NO_ANCHOR,
		/* 216 */ YY_NO_ANCHOR,
		/* 217 */ YY_NO_ANCHOR,
		/* 218 */ YY_NO_ANCHOR,
		/* 219 */ YY_NO_ANCHOR,
		/* 220 */ YY_NO_ANCHOR,
		/* 221 */ YY_NO_ANCHOR,
		/* 222 */ YY_NO_ANCHOR,
		/* 223 */ YY_NO_ANCHOR,
		/* 224 */ YY_NO_ANCHOR,
		/* 225 */ YY_NO_ANCHOR,
		/* 226 */ YY_NO_ANCHOR,
		/* 227 */ YY_NO_ANCHOR,
		/* 228 */ YY_NO_ANCHOR,
		/* 229 */ YY_NO_ANCHOR,
		/* 230 */ YY_NO_ANCHOR,
		/* 231 */ YY_NO_ANCHOR,
		/* 232 */ YY_NO_ANCHOR,
		/* 233 */ YY_NO_ANCHOR,
		/* 234 */ YY_NO_ANCHOR,
		/* 235 */ YY_NO_ANCHOR,
		/* 236 */ YY_NO_ANCHOR,
		/* 237 */ YY_NO_ANCHOR,
		/* 238 */ YY_NO_ANCHOR,
		/* 239 */ YY_NO_ANCHOR,
		/* 240 */ YY_NO_ANCHOR,
		/* 241 */ YY_NO_ANCHOR,
		/* 242 */ YY_NO_ANCHOR,
		/* 243 */ YY_NO_ANCHOR,
		/* 244 */ YY_NO_ANCHOR
	};
	private int yy_cmap[] = unpackFromString(1,258,
"4:9,57,1,4:2,5,4:18,57,53,6,4,56,4:2,3,10,11,14,54,8,54,9,54,15:10,4,7,51,5" +
"0,52,2,4,16,32,30,25,21,39,40,42,36,45,46,18,35,26,33,38,47,23,29,19,34,44," +
"48,49,28,55,4:4,55,4,17,32,31,25,22,39,41,43,37,45,46,18,35,27,33,38,47,24," +
"29,20,34,44,48,49,28,55,12,4,13,4:130,0:2")[0];

	private int yy_rmap[] = unpackFromString(1,245,
"0,1:2,2,1:9,3,4,1:2,5,1,6,7:2,8,9,7:9,10,11,7,1,7:6,12,7:6,1,7:22,13,1,14,1" +
"5,16,17,18,19,7,20,21,20,22,23,24,25,26,27,28,17,29,30,31,32,33,34,35,36,37" +
",38,39,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,57,58,59,60,61,62" +
",63,64,65,66,67,68,69,70,71,72,73,74,75,76,77,78,79,80,81,82,83,84,85,86,87" +
",88,89,90,91,92,93,94,95,96,97,98,99,100,101,102,103,104,105,106,107,108,10" +
"9,110,111,112,113,114,115,116,117,118,119,120,121,122,123,124,125,126,127,1" +
"28,129,130,131,132,133,134,135,136,137,138,139,140,141,142,143,144,145,146," +
"147,148,149,150,151,152,7:3,153,154,155,156,157,158,159,160,161,162,163,164" +
",165,166,167,168,169,170,171,172,173,174,175,176,177")[0];

	private int yy_nxt[][] = unpackFromString(178,58,
"1,73,2,3,74,73,4,5,6,7,8,9,10,11,12,13,14:2,75,83:2,219:2,228:2,233,147:2,2" +
"19,148,149,150,87,89,235,151,91,93,219,152,237:2,239:2,241,242,219:2,243,21" +
"9,15,76,84,82,16,219,86,73,-1:60,17,18,17:2,77,17:51,-1:15,13,-1:57,219:3,9" +
"5,219:6,97,98:2,219,19,219:14,99,219:5,-1:5,219,-1:4,17,18,17:54,-1:15,219:" +
"15,29:2,219:18,-1:5,219,-1:17,219:35,-1:5,219,-1:17,219:10,184,219:24,-1:5," +
"219,-1:17,219:4,34,115,219:8,186,219:20,-1:5,219,-1:17,219:19,198,219:15,-1" +
":5,219,-1:17,219:6,202,219:11,44,219:16,-1:5,219,-1:17,219,210,219:33,-1:5," +
"219,-1:3,73,-1:3,73,-1:51,73,-1:15,219:15,244:2,219:4,153:2,219:12,-1:5,219" +
",-1:52,15,-1,15,-1:6,88,77,78,77:2,17,77:51,-1,88,92:4,88,92:51,-1:15,219:4" +
",115:2,219:8,186,219:20,-1:5,219,-1:17,219:14,67,219:20,-1:5,219,-1:52,15,-" +
"1:22,219,154:2,219:15,20,219:2,155:2,219:12,-1:5,219,-1:18,90:34,-1:5,90,-1" +
":12,85,-1:62,219:13,21,219:21,-1:5,219,-1:5,36,-1:69,219:8,22:2,219:25,-1:5" +
",219,-1:13,50,-1:3,90:35,-1:5,90,-1,94,-1:15,219:11,23,79,219,24,219:20,-1:" +
"5,219,-1:17,219:11,79:2,219,24,219:20,-1:5,219,-1:13,50,-1:45,94,-1:15,219:" +
"3,25,177:2,219:29,-1:5,219,-1:2,1,-1:72,219:10,26,219:24,-1:5,219,-1:17,219" +
":10,27,219:2,28,219:21,-1:5,219,-1:17,219:25,30:2,219:8,-1:5,219,-1:17,219:" +
"4,31:2,219:29,-1:5,219,-1:17,219:3,238,32:2,219:29,-1:5,219,-1:17,219:20,30" +
",219:14,-1:5,219,-1:17,219:3,33,219:15,182,183,219:14,-1:5,219,-1:17,219:34" +
",30,-1:5,219,-1:17,219:11,30:2,219:22,-1:5,219,-1:17,219:8,35:2,219:25,-1:5" +
",219,-1:17,219:6,37:2,219:27,-1:5,219,-1:17,219:6,38:2,219:27,-1:5,219,-1:1" +
"7,219:14,30,219:20,-1:5,219,-1:17,219:15,39:2,219:18,-1:5,219,-1:17,219:23," +
"40,219:11,-1:5,219,-1:17,219:3,41,219:31,-1:5,219,-1:17,219:6,42:2,219:27,-" +
"1:5,219,-1:17,219:8,43,219:26,-1:5,219,-1:17,219:18,44,219:16,-1:5,219,-1:1" +
"7,219:20,45,219:14,-1:5,219,-1:17,219:33,46,219,-1:5,219,-1:17,219:11,47:2," +
"219:22,-1:5,219,-1:17,219:31,48,219:3,-1:5,219,-1:17,219:27,49:2,219:6,-1:5" +
",219,-1:17,219:8,51:2,219:25,-1:5,219,-1:17,219:6,30:2,219:27,-1:5,219,-1:1" +
"7,219:6,52:2,219:27,-1:5,219,-1:17,219:4,30:2,219:29,-1:5,219,-1:17,219:8,5" +
"3:2,219:25,-1:5,219,-1:17,219:11,54:2,219:22,-1:5,219,-1:17,219:23,55,219:1" +
"1,-1:5,219,-1:17,219:6,80:2,219:27,-1:5,219,-1:17,219:6,56:2,219:27,-1:5,21" +
"9,-1:17,219:6,57:2,219:27,-1:5,219,-1:17,219:6,58:2,219:27,-1:5,219,-1:17,2" +
"19:4,59:2,219:29,-1:5,219,-1:17,219,60:2,219:32,-1:5,219,-1:17,219:6,61:2,2" +
"19:27,-1:5,219,-1:17,219:11,217:2,219:22,-1:5,219,-1:17,219:4,62:2,219:29,-" +
"1:5,219,-1:17,219:6,63:2,219:27,-1:5,219,-1:17,219:6,64:2,219:27,-1:5,219,-" +
"1:17,219:4,65:2,219:29,-1:5,219,-1:17,219:25,66:2,219:8,-1:5,219,-1:17,219:" +
"15,68:2,219:18,-1:5,219,-1:17,219:4,69:2,219:29,-1:5,219,-1:17,219:8,81,219" +
":26,-1:5,219,-1:17,219:31,70,219:3,-1:5,219,-1:17,219:4,71:2,219:29,-1:5,21" +
"9,-1:17,219:23,72,219:11,-1:5,219,-1:17,219:18,100,162,219:15,-1:5,219,-1:1" +
"7,219:6,101:2,219:7,163:2,219,164,102,219:15,-1:5,219,-1:17,219:8,165:2,219" +
":8,103,166,219:7,167,219:7,-1:5,219,-1:17,219:8,165:2,219:8,103,166,219:15," +
"-1:5,219,-1:17,219,104:2,219:18,105:2,219:12,-1:5,219,-1:17,219:8,170:2,219" +
":8,106,219:16,-1:5,219,-1:17,219:31,107,219:3,-1:5,219,-1:17,219:17,179,219" +
":17,-1:5,219,-1:17,219:20,230,219:14,-1:5,219,-1:17,219:11,180:2,219:22,-1:" +
"5,219,-1:17,219:3,229,219:31,-1:5,219,-1:17,219:4,108:2,219:7,109,219:21,-1" +
":5,219,-1:17,219:3,234,219:10,110,219:20,-1:5,219,-1:17,219:18,111,219:16,-" +
"1:5,219,-1:17,219:14,181,219:20,-1:5,219,-1:17,219:3,112,219:16,236,219:14," +
"-1:5,219,-1:17,219:27,240:2,219:6,-1:5,219,-1:17,219:20,113,219:14,-1:5,219" +
",-1:17,219:6,224:2,219:27,-1:5,219,-1:17,219:8,223:2,219:25,-1:5,219,-1:17," +
"219,114,219:33,-1:5,219,-1:17,219:21,185:2,219:12,-1:5,219,-1:17,219:10,232" +
",219:24,-1:5,219,-1:17,219:18,116,219:16,-1:5,219,-1:17,219:18,187,219:16,-" +
"1:5,219,-1:17,219:29,188,219:5,-1:5,219,-1:17,219:6,117:2,219:27,-1:5,219,-" +
"1:17,219:21,118:2,219:12,-1:5,219,-1:17,219:8,119:2,219:25,-1:5,219,-1:17,2" +
"19:4,120:2,219:29,-1:5,219,-1:17,219:6,121:2,219:27,-1:5,219,-1:17,219:14,1" +
"22,219:20,-1:5,219,-1:17,219:3,123,219:31,-1:5,219,-1:17,219,192:2,219:32,-" +
"1:5,219,-1:17,219:4,226:2,219:29,-1:5,219,-1:17,219:11,124:2,219:22,-1:5,21" +
"9,-1:17,219:20,199,219:14,-1:5,219,-1:17,219:6,125:2,219:27,-1:5,219,-1:17," +
"219:18,126,219:13,200,219:2,-1:5,219,-1:17,219:6,203:2,219:27,-1:5,219,-1:1" +
"7,219:19,127,219:15,-1:5,219,-1:17,219:21,204:2,219:12,-1:5,219,-1:17,219:1" +
"9,128,219:15,-1:5,219,-1:17,219:8,129:2,219:25,-1:5,219,-1:17,219:14,205,21" +
"9:20,-1:5,219,-1:17,219:20,130,219:14,-1:5,219,-1:17,219:17,206,219:17,-1:5" +
",219,-1:17,219:4,131:2,219:29,-1:5,219,-1:17,219:15,132:2,219:18,-1:5,219,-" +
"1:17,219:20,133,219:14,-1:5,219,-1:17,219:4,134:2,219:29,-1:5,219,-1:17,219" +
":20,135,219:14,-1:5,219,-1:17,219:21,136:2,219:12,-1:5,219,-1:17,219:19,137" +
",219:15,-1:5,219,-1:17,219:4,138:2,219:29,-1:5,219,-1:17,219:25,211,219:9,-" +
"1:5,219,-1:17,219:8,139:2,219:25,-1:5,219,-1:17,219:11,140:2,219:22,-1:5,21" +
"9,-1:17,219:4,227:2,219:29,-1:5,219,-1:17,219,212:2,219:32,-1:5,219,-1:17,2" +
"19:11,213:2,219:22,-1:5,219,-1:17,219:21,141:2,219:12,-1:5,219,-1:17,219:11" +
",142:2,219:22,-1:5,219,-1:17,219:15,214,219:19,-1:5,219,-1:17,219:6,143,219" +
":28,-1:5,219,-1:17,219:15,144:2,219:18,-1:5,219,-1:17,219:15,145:2,219:18,-" +
"1:5,219,-1:17,219:4,216,219:30,-1:5,219,-1:17,219:20,146,219:14,-1:5,219,-1" +
":17,219:6,220,219:28,-1:5,219,-1:17,219:8,218,219:26,-1:5,219,-1:17,219:3,1" +
"89,219:31,-1:5,219,-1:17,219:6,190:2,219:27,-1:5,219,-1:17,219:8,225:2,219:" +
"25,-1:5,219,-1:17,219,197:2,219:32,-1:5,219,-1:17,219:6,209:2,219:27,-1:5,2" +
"19,-1:17,219:21,207:2,219:12,-1:5,219,-1:17,219,215:2,219:32,-1:5,219,-1:17" +
",219:6,156:2,219:10,157,219:16,-1:5,219,-1:17,219:3,193,219:31,-1:5,219,-1:" +
"17,219:6,191:2,219:27,-1:5,219,-1:17,219:8,208:2,219:25,-1:5,219,-1:17,219," +
"201:2,219:32,-1:5,219,-1:17,219,158:2,219:3,159:2,160:2,219:11,161:2,219:12" +
",-1:5,219,-1:17,219:6,194:2,219:27,-1:5,219,-1:17,219:11,168:2,219:10,169,2" +
"19:11,-1:5,219,-1:17,219:6,231:2,219:27,-1:5,219,-1:17,219:8,171:2,219:25,-" +
"1:5,219,-1:17,219:6,195:2,219:27,-1:5,219,-1:17,219,172:2,219:32,-1:5,219,-" +
"1:17,219:6,196:2,219:27,-1:5,219,-1:17,219,221:2,219:18,173:2,219:12,-1:5,2" +
"19,-1:17,219:18,174,219:16,-1:5,219,-1:17,219:18,175,219:2,176:2,219:4,222:" +
"2,219:6,-1:5,219,-1:17,219,178:2,219:32,-1:5,219,-1:2");

	public Symbol yylex ()
		throws java.io.IOException {
		int yy_lookahead;
		int yy_anchor = YY_NO_ANCHOR;
		int yy_state = yy_state_dtrans[yy_lexical_state];
		int yy_next_state = YY_NO_STATE;
		int yy_last_accept_state = YY_NO_STATE;
		boolean yy_initial = true;
		int yy_this_accept;

		yy_mark_start();
		yy_this_accept = yy_acpt[yy_state];
		if (YY_NOT_ACCEPT != yy_this_accept) {
			yy_last_accept_state = yy_state;
			yy_mark_end();
		}
		while (true) {
			if (yy_initial && yy_at_bol) yy_lookahead = YY_BOL;
			else yy_lookahead = yy_advance();
			yy_next_state = YY_F;
			yy_next_state = yy_nxt[yy_rmap[yy_state]][yy_cmap[yy_lookahead]];
			if (YY_EOF == yy_lookahead && true == yy_initial) {

return (new Symbol(sym.EOF));
			}
			if (YY_F != yy_next_state) {
				yy_state = yy_next_state;
				yy_initial = false;
				yy_this_accept = yy_acpt[yy_state];
				if (YY_NOT_ACCEPT != yy_this_accept) {
					yy_last_accept_state = yy_state;
					yy_mark_end();
				}
			}
			else {
				if (YY_NO_STATE == yy_last_accept_state) {
					throw (new Error("Lexical Error: Unmatched Input."));
				}
				else {
					yy_anchor = yy_acpt[yy_last_accept_state];
					if (0 != (YY_END & yy_anchor)) {
						yy_move_end();
					}
					yy_to_mark();
					switch (yy_last_accept_state) {
					case 0:
						{ }
					case -2:
						break;
					case 1:
						
					case -3:
						break;
					case 2:
						{return new Symbol(sym.QUESTION,"?");}
					case -4:
						break;
					case 3:
						{  
  cat.error("Invalid symbol: <" + yytext() + "> ");
	return new Symbol(sym.ERR,yytext());
}
					case -5:
						break;
					case 4:
						{ 
  return new Symbol(sym.QUOTE, yychar+1,yychar+1+yytext().length(),yytext()); }
					case -6:
						break;
					case 5:
						{ return new Symbol(sym.SEMI,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -7:
						break;
					case 6:
						{ 
  return new Symbol(sym.COMMA,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -8:
						break;
					case 7:
						{ 
  return new Symbol(sym.PERIOD, yychar+1,yychar+1+yytext().length(),yytext()); }
					case -9:
						break;
					case 8:
						{ 
  return new Symbol(sym.LPAREN, yychar+1,yychar+1+yytext().length(),yytext()); }
					case -10:
						break;
					case 9:
						{ 
  return new Symbol(sym.RPAREN,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -11:
						break;
					case 10:
						{ 
  return new Symbol(sym.LBRACE,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -12:
						break;
					case 11:
						{ 
  return new Symbol(sym.RBRACE,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -13:
						break;
					case 12:
						{ 
  return new Symbol(sym.TIMES,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -14:
						break;
					case 13:
						{
  return new Symbol(sym.NUMBER,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -15:
						break;
					case 14:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -16:
						break;
					case 15:
						{ return new Symbol(sym.COMPARISON,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -17:
						break;
					case 16:
						{ return new Symbol(sym.ARITHMETIC,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -18:
						break;
					case 18:
						{ 
  return new Symbol(sym.STRING, yychar+1,yychar+1+yytext().length(),yytext()); }
					case -19:
						break;
					case 19:
						{
  return new Symbol(sym.AS,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -20:
						break;
					case 20:
						{ return new Symbol(sym.TO,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -21:
						break;
					case 21:
						{
  return new Symbol(sym.BY,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -22:
						break;
					case 22:
						{return new Symbol(sym.OR,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -23:
						break;
					case 23:
						{
  return new Symbol(sym.IN,yychar+1,yychar+1+yytext().length(),yytext());  }
					case -24:
						break;
					case 24:
						{return new Symbol(sym.IS,yychar+1,yychar+1+yytext().length(),yytext());}
					case -25:
						break;
					case 25:
						{
  return new Symbol(sym.ALL,yychar+1,yychar+1+yytext().length(),yytext());}
					case -26:
						break;
					case 26:
						{ 
  return new Symbol(sym.ADD,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -27:
						break;
					case 27:
						{
  return new Symbol(sym.AND,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -28:
						break;
					case 28:
						{
  return new Symbol(sym.ANY,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -29:
						break;
					case 29:
						{
  return new Symbol(sym.ASC,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -30:
						break;
					case 30:
						{ return new Symbol(sym.AMMSC,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -31:
						break;
					case 31:
						{return new Symbol(sym.NOT,yychar+1,yychar+1+yytext().length(),yytext());}
					case -32:
						break;
					case 32:
						{ return new Symbol(sym.SET,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -33:
						break;
					case 33:
						{
  return new Symbol(sym.COLUMN,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -34:
						break;
					case 34:
						{ return new Symbol(sym.INTEGER,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -35:
						break;
					case 35:
						{
  return new Symbol(sym.FOR,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -36:
						break;
					case 36:
						{ return new Symbol(sym.STRING,  yychar+1,yychar+1+yytext().length(),yytext().substring(1, yytext().length() - 1)) ; }
					case -37:
						break;
					case 37:
						{return new Symbol(sym.LIKE,yychar+1,yychar+1+yytext().length(),yytext());}
					case -38:
						break;
					case 38:
						{ 
  return new Symbol(sym.DATE,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -39:
						break;
					case 39:
						{
  return new Symbol(sym.DESC,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -40:
						break;
					case 40:
						{ 
  return new Symbol(sym.DROP,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -41:
						break;
					case 41:
						{return new Symbol(sym.NULLX,yychar+1,yychar+1+yytext().length(),yytext());}
					case -42:
						break;
					case 42:
						{ return new Symbol(sym.SOME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -43:
						break;
					case 43:
						{ return new Symbol(sym.CHARACTER,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -44:
						break;
					case 44:
						{ return new Symbol(sym.INTO,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -45:
						break;
					case 45:
						{
  return new Symbol(sym.FROM,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -46:
						break;
					case 46:
						{ return new Symbol(sym.VIEW,  yychar+1,yychar+1+yytext().length(),yytext()); 
}
					case -47:
						break;
					case 47:
						{return new Symbol(sym.JOIN,yychar+1,yychar+1+yytext().length(),yytext());}
					case -48:
						break;
					case 48:
						{ return new Symbol(sym.WORK,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -49:
						break;
					case 49:
						{ return new Symbol(sym.WITH,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -50:
						break;
					case 50:
						{  return new Symbol(sym.VARREF,yytext());}
					case -51:
						break;
					case 51:
						{ 
  return new Symbol(sym.ALTER,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -52:
						break;
					case 52:
						{ return new Symbol(sym.TABLE,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -53:
						break;
					case 53:
						{return new Symbol(sym.ORDER,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -54:
						break;
					case 54:
						{ return new Symbol(sym.UNION,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -55:
						break;
					case 55:
						{
  return new Symbol(sym.GROUP,yychar+1,yychar+1+yytext().length(),yytext());}
					case -56:
						break;
					case 56:
						{ return new Symbol(sym.WHERE,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -57:
						break;
					case 57:
						{ return new Symbol(sym.RENAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -58:
						break;
					case 58:
						{ 
  return new Symbol(sym.DELETE,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -59:
						break;
					case 59:
						{return new Symbol(sym.SELECT,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -60:
						break;
					case 60:
						{ return new Symbol(sym.SCHEMA,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -61:
						break;
					case 61:
						{
  return new Symbol(sym.CREATE,yychar+1,yychar+1+yytext().length(),yytext());
}
					case -62:
						break;
					case 62:
						{ 
  return new Symbol(sym.COMMIT,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -63:
						break;
					case 63:
						{ return new Symbol(sym.UNIQUE,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -64:
						break;
					case 64:
						{ return new Symbol(sym.UPDATE,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -65:
						break;
					case 65:
						{ 
  return new Symbol(sym.INSERT,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -66:
						break;
					case 66:
						{
  return new Symbol(sym.HAVING,yychar+1,yychar+1+yytext().length(),yytext());}
					case -67:
						break;
					case 67:
						{ return new Symbol(sym.VALUES,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -68:
						break;
					case 68:
						{ return new Symbol(sym.NUMERIC,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -69:
						break;
					case 69:
						{
  return new Symbol(sym.CURRENT,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -70:
						break;
					case 70:
						{ return new Symbol(sym.ROLLBACK,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -71:
						break;
					case 71:
						{
  return new Symbol(sym.DISTINCT,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -72:
						break;
					case 72:
						{ return new Symbol(sym.TIMESTAMP,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -73:
						break;
					case 73:
						{ }
					case -74:
						break;
					case 74:
						{  
  cat.error("Invalid symbol: <" + yytext() + "> ");
	return new Symbol(sym.ERR,yytext());
}
					case -75:
						break;
					case 75:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -76:
						break;
					case 76:
						{ return new Symbol(sym.COMPARISON,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -77:
						break;
					case 78:
						{ 
  return new Symbol(sym.STRING, yychar+1,yychar+1+yytext().length(),yytext()); }
					case -78:
						break;
					case 79:
						{
  return new Symbol(sym.IN,yychar+1,yychar+1+yytext().length(),yytext());  }
					case -79:
						break;
					case 80:
						{ return new Symbol(sym.AMMSC,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -80:
						break;
					case 81:
						{ return new Symbol(sym.INTEGER,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -81:
						break;
					case 82:
						{  
  cat.error("Invalid symbol: <" + yytext() + "> ");
	return new Symbol(sym.ERR,yytext());
}
					case -82:
						break;
					case 83:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -83:
						break;
					case 84:
						{ return new Symbol(sym.COMPARISON,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -84:
						break;
					case 86:
						{  
  cat.error("Invalid symbol: <" + yytext() + "> ");
	return new Symbol(sym.ERR,yytext());
}
					case -85:
						break;
					case 87:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -86:
						break;
					case 89:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -87:
						break;
					case 91:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -88:
						break;
					case 93:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -89:
						break;
					case 95:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -90:
						break;
					case 97:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -91:
						break;
					case 98:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -92:
						break;
					case 99:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -93:
						break;
					case 100:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -94:
						break;
					case 101:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -95:
						break;
					case 102:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -96:
						break;
					case 103:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -97:
						break;
					case 104:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -98:
						break;
					case 105:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -99:
						break;
					case 106:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -100:
						break;
					case 107:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -101:
						break;
					case 108:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -102:
						break;
					case 109:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -103:
						break;
					case 110:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -104:
						break;
					case 111:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -105:
						break;
					case 112:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -106:
						break;
					case 113:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -107:
						break;
					case 114:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -108:
						break;
					case 115:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -109:
						break;
					case 116:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -110:
						break;
					case 117:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -111:
						break;
					case 118:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -112:
						break;
					case 119:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -113:
						break;
					case 120:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -114:
						break;
					case 121:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -115:
						break;
					case 122:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -116:
						break;
					case 123:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -117:
						break;
					case 124:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -118:
						break;
					case 125:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -119:
						break;
					case 126:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -120:
						break;
					case 127:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -121:
						break;
					case 128:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -122:
						break;
					case 129:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -123:
						break;
					case 130:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -124:
						break;
					case 131:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -125:
						break;
					case 132:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -126:
						break;
					case 133:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -127:
						break;
					case 134:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -128:
						break;
					case 135:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -129:
						break;
					case 136:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -130:
						break;
					case 137:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -131:
						break;
					case 138:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -132:
						break;
					case 139:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -133:
						break;
					case 140:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -134:
						break;
					case 141:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -135:
						break;
					case 142:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -136:
						break;
					case 143:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -137:
						break;
					case 144:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -138:
						break;
					case 145:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -139:
						break;
					case 146:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -140:
						break;
					case 147:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -141:
						break;
					case 148:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -142:
						break;
					case 149:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -143:
						break;
					case 150:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -144:
						break;
					case 151:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -145:
						break;
					case 152:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -146:
						break;
					case 153:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -147:
						break;
					case 154:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -148:
						break;
					case 155:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -149:
						break;
					case 156:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -150:
						break;
					case 157:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -151:
						break;
					case 158:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -152:
						break;
					case 159:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -153:
						break;
					case 160:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -154:
						break;
					case 161:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -155:
						break;
					case 162:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -156:
						break;
					case 163:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -157:
						break;
					case 164:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -158:
						break;
					case 165:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -159:
						break;
					case 166:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -160:
						break;
					case 167:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -161:
						break;
					case 168:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -162:
						break;
					case 169:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -163:
						break;
					case 170:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -164:
						break;
					case 171:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -165:
						break;
					case 172:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -166:
						break;
					case 173:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -167:
						break;
					case 174:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -168:
						break;
					case 175:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -169:
						break;
					case 176:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -170:
						break;
					case 177:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -171:
						break;
					case 178:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -172:
						break;
					case 179:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -173:
						break;
					case 180:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -174:
						break;
					case 181:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -175:
						break;
					case 182:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -176:
						break;
					case 183:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -177:
						break;
					case 184:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -178:
						break;
					case 185:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -179:
						break;
					case 186:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -180:
						break;
					case 187:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -181:
						break;
					case 188:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -182:
						break;
					case 189:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -183:
						break;
					case 190:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -184:
						break;
					case 191:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -185:
						break;
					case 192:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -186:
						break;
					case 193:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -187:
						break;
					case 194:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -188:
						break;
					case 195:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -189:
						break;
					case 196:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -190:
						break;
					case 197:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -191:
						break;
					case 198:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -192:
						break;
					case 199:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -193:
						break;
					case 200:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -194:
						break;
					case 201:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -195:
						break;
					case 202:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -196:
						break;
					case 203:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -197:
						break;
					case 204:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -198:
						break;
					case 205:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -199:
						break;
					case 206:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -200:
						break;
					case 207:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -201:
						break;
					case 208:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -202:
						break;
					case 209:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -203:
						break;
					case 210:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -204:
						break;
					case 211:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -205:
						break;
					case 212:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -206:
						break;
					case 213:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -207:
						break;
					case 214:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -208:
						break;
					case 215:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -209:
						break;
					case 216:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -210:
						break;
					case 217:
						{
  return new Symbol(sym.COLUMN,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -211:
						break;
					case 218:
						{ return new Symbol(sym.CHARACTER,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -212:
						break;
					case 219:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -213:
						break;
					case 220:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -214:
						break;
					case 221:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -215:
						break;
					case 222:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -216:
						break;
					case 223:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -217:
						break;
					case 224:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -218:
						break;
					case 225:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -219:
						break;
					case 226:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -220:
						break;
					case 227:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -221:
						break;
					case 228:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -222:
						break;
					case 229:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -223:
						break;
					case 230:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -224:
						break;
					case 231:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -225:
						break;
					case 232:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -226:
						break;
					case 233:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -227:
						break;
					case 234:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -228:
						break;
					case 235:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -229:
						break;
					case 236:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -230:
						break;
					case 237:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -231:
						break;
					case 238:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -232:
						break;
					case 239:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -233:
						break;
					case 240:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -234:
						break;
					case 241:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -235:
						break;
					case 242:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -236:
						break;
					case 243:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -237:
						break;
					case 244:
						{ return new Symbol(sym.NAME,yychar+1,yychar+1+yytext().length(),yytext()); }
					case -238:
						break;
					default:
						yy_error(YY_E_INTERNAL,false);
					case -1:
					}
					yy_initial = true;
					yy_state = yy_state_dtrans[yy_lexical_state];
					yy_next_state = YY_NO_STATE;
					yy_last_accept_state = YY_NO_STATE;
					yy_mark_start();
					yy_this_accept = yy_acpt[yy_state];
					if (YY_NOT_ACCEPT != yy_this_accept) {
						yy_last_accept_state = yy_state;
						yy_mark_end();
					}
				}
			}
		}
	}
}