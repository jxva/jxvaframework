package demo.sql;


import java.util.Stack;
import java.util.regex.*;

/**
 * 编写Java程序时,我们可能会碰到解释数学表达式的问题,例如,输入字符串“(5+4)*10”,求出其算术结果,对于这个问题,可以通过后缀表达式来解决。

        首先,让我们看看后缀表达式的算法:
1) 将中缀表达式转换为后缀表达式:
　　•当读到数字直接送至输出队列中；
　　•当读到运算符t时:
            a.将栈中所有优先级高于或等于t的运算符弹出,送到输出队列中；
　　　b.t进栈；
　　•读到左括号时总是将它压入栈中；
　　•读到右括号时,将靠近栈顶的第一个左括号上面的运算符全部依次弹出,送至输出队列后,再丢弃左括号；
        •中缀表达式全部读完后,若栈中仍有运算符,将其送到输出队列中。
2) 运用后缀表达式进行计算:
　　•建立一个栈S；
　　•从左到右读后缀表达式,读到数字就将它转换为数值压入栈S中,读到运算符则从栈中依次弹出两个数分别到Y和X,然后以“X 运算符 Y”的形式计算机出结果,再压加栈S中；
　　•如果后缀表达式未读完,就重复上面过程,最后输出栈顶的数值则为结束。

         以下是代码实现:
 * @author Administrator
 *
 */

public class StringToArithmetic {
	
    public static void main(String args[]){
    	System.out.println(stringToArithmetic("(8+32*2.34313)-10+12/3"));
    }

    /** *//**
     * 给出一个算术表达式,返回结果。 例如 (5+8+10)*1,返回23
     * 
     * @param string
     */
    public static double stringToArithmetic(String string) {
        return postfixExprToArithmetic(prefixExprToPostfixExpr(string));
    }

    /** *//**
     * 中缀表达式转后缀表达式
     * 
     * @param String prefix
     * @return String
     */
    private static String prefixExprToPostfixExpr(String prefix) {
        Stack<Character> stack = new Stack<Character>();
        StringBuilder postfix = new StringBuilder("");
        int length = prefix.length();
        for (int i = 0; i < length; i++) {
            Character temp;
            char c = prefix.charAt(i);
            switch (c) {
            // 忽略空格
            case ' ':
                break;
            // 碰到'(',push到栈
            case '(':
                stack.push(c);
                break;
            // 碰到'+''-',将栈中所有运算符弹出,送到输出队列中
            case '+':
            case '-':
                while (stack.size() != 0) {
                    temp = stack.pop();
                    if (temp == '(') {
                        stack.push('(');
                        break;
                    }
                    postfix.append(" ").append(temp);
                }
                stack.push(c);
                postfix.append(" ");
                break;
            // 碰到'*''/',将栈中所有乘除运算符弹出,送到输出队列中
            case '*':
            case '/':
                while (stack.size() != 0) {
                    temp = stack.pop();
                    if (temp == '(' || temp == '+' || temp == '-') {
                        stack.push(temp);
                        break;
                    } else {
                    	postfix.append(" ").append(temp);
                    }
                }
                stack.push(c);
                postfix.append(" ");
                break;
            // 碰到右括号,将靠近栈顶的第一个左括号上面的运算符全部依次弹出,送至输出队列后,再丢弃左括号
            case ')':
                while (stack.size() != 0) {
                    temp = stack.pop();
                    if (temp == '(')
                        break;
                    else
                    	postfix.append(" ").append(temp);
                }
                postfix.append(" ");
                break;
            default:
            	postfix.append(c);
            }
        }
        while (stack.size() != 0)
        	postfix.append(" ").append(stack.pop());
        return postfix.toString();
    }

    /** *//**
     * 通过后缀表达式求出算术结果
     * 
     * @param String
     *            postfix
     * @return double
     */
    private static double postfixExprToArithmetic(String postfix) {
        Pattern pattern = Pattern.compile("\\d+||\\d+\\.\\d*"); // 匹配数字
        String strings[] = postfix.split(" ");
        for (int i = 0; i < strings.length; i++)
            strings[i].trim();
        Stack<Double> stack = new Stack<Double>();
        for (int i = 0; i < strings.length; i++) {
            if (strings[i].equals(""))
                continue;
            if ((pattern.matcher(strings[i])).matches()) {
                stack.push(Double.parseDouble(strings[i]));
            } else {
                double y = stack.pop();
                double x = stack.pop();
                stack.push(caculate(x, y, strings[i]));
            }
        }
        return stack.pop();
    }

    private static double caculate(double x, double y, String simble) {
        if (simble.trim().equals("+"))
            return x + y;
        if (simble.trim().equals("-"))
            return x - y;
        if (simble.trim().equals("*"))
            return x * y;
        if (simble.trim().equals("/"))
            return x / y;
        return 0;
    }
}

