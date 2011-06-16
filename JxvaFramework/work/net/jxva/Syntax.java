package net.jxva;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

class Syntax {
	// 创建保存各种关键字的arraylist
	private static ArrayList keyWords = new ArrayList();
	private static ArrayList divSymbol = new ArrayList();
	private static ArrayList arithmeticOperator = new ArrayList();
	private static ArrayList relationOperator = new ArrayList();
	// 输入输出文件名
	private String keywordsFile = new String(), inputFile = new String(),
			outputFile = new String();
	static StringBuffer source = new StringBuffer(); // 读取源码缓冲
	static StringBuffer analyseSource = new StringBuffer(); // 读取分析后的源码缓冲

	private void readKeyWords() throws IOException {
		// 读取各种关键字
		BufferedReader in = new BufferedReader(new FileReader(keywordsFile));
		String s = new String(), type = new String();
		boolean readIn = false;
		while ((s = in.readLine()) != null) {
			// 用空白符分隔各种关键字符集
			if (s.equals(""))
				readIn = false;
			else
				readIn = true;
			// 设定当前读取的是哪种关键字符
			if (s.equals("keywords")) {
				type = "keywords";
				continue;
			} else if (s.equals("divsymbol")) {
				type = "divsymbol";
				continue;
			} else if (s.equals("arithmeticoperator")) {
				type = "arithmeticoperator";
				continue;
			} else if (s.equals("relationoperator")) {
				type = "relationoperator";
				continue;
			}
			// 将相应的关键字放入对应的arraylist中
			if (readIn == true) {
				if (type.equals("keywords"))
					keyWords.add(s);
				else if (type.equals("divsymbol"))
					divSymbol.add(s);
				else if (type.equals("arithmeticoperator"))
					arithmeticOperator.add(s);
				else if (type.equals("relationoperator"))
					relationOperator.add(s);
			}
		}
	}

	private void readSource() throws IOException {
		// 读取源代码并将每行传给分析方法
		BufferedReader in = new BufferedReader(new FileReader("E:/eclipse/workspace/JxvaFramework/work/net/jxva/input.txt"));
		String s = new String();
		while ((s = in.readLine()) != null) {
			source.append(s + "\n");
			analyseLine(s);
		}
	}

	private void analyseLine(String s) {
		// 根据分隔符分析每一行两分隔符之间的词法
		// 先分析第一个，防止因每行开始没有分隔符漏掉词句
		int i = 0;
		for (; i < s.length(); i++) {
			if (divSymbol.contains(new String().valueOf(s.charAt(i)))
					|| arithmeticOperator.contains(new String().valueOf(s
							.charAt(i)))
					|| relationOperator.contains(new String().valueOf(s
							.charAt(i)))) {
				replace(s.substring(0, i));
				break;
			}
		}
		if (i == s.length()) { // 如果一次分析就到末尾，直接存入关键字后结束
			replace(s.substring(0, s.length()));
			return;
		}
		// 如果后面还有语句
		out: for (; i < s.length(); i++) {
			// 从当前分隔符位置开始向后搜索
			replace(s.substring(i, i + 1)); // 先把当前分隔符存入分隔符arraylist
			inner: for (int j = i + 1; j < s.length(); j++) {
				if ((j == i + 1)
						&& (divSymbol.contains(new String()
								.valueOf(s.charAt(j)))
								|| arithmeticOperator.contains(new String()
										.valueOf(s.charAt(j))) || relationOperator
								.contains(new String().valueOf(s.charAt(j))))) { // 如果紧接着下一个还是一个分隔符的话继续外围循环
					i = j - 1; // 将当前分隔符标识放到后分隔符上
					continue out;
				} else if (divSymbol
						.contains(new String().valueOf(s.charAt(j)))
						|| arithmeticOperator.contains(new String().valueOf(s
								.charAt(j)))
						|| relationOperator.contains(new String().valueOf(s
								.charAt(j)))) { // 否则的话将两个分隔符之间的词进行替换分析
					replace(s.substring(i + 1, j));
					i = j - 1; // 将当前分隔符标识放到后分隔符上
					continue out;
				}
			}
		}
	}

	private void replace(String s) {
		// 分析传来的关键词
		analyseSource.append(s + "\n");
		if (keyWords.contains(s)) { // 如果词是一个关键字
			System.out.print("t: 1; i: ");
			if (s.equals("BEGIN"))
				System.out.println("0;");
			else if (s.equals("DO"))
				System.out.println("1;");
			else if (s.equals("ELSE"))
				System.out.println("2;");
			else if (s.equals("END"))
				System.out.println("3;");
			else if (s.equals("IF"))
				System.out.println("4;");
			else if (s.equals("THEN"))
				System.out.println("5;");
			else if (s.equals("VAR"))
				System.out.println("6;");
			else if (s.equals("WHILE"))
				System.out.println("7;");
		} else if (divSymbol.contains(s)) { // 如果词是一个分隔符
			System.out.print("t: 2; i: ");
			if (s.equals(","))
				System.out.println("0;");
			else if (s.equals(";"))
				System.out.println("1;");
			else if (s.equals("."))
				System.out.println("2;");
			else if (s.equals(":="))
				System.out.println("3;");
			else if (s.equals("("))
				System.out.println("4;");
			else if (s.equals(")"))
				System.out.println("5;");
		} else if (arithmeticOperator.contains(s)) { // 如果词是一个算术运算符
			System.out.print("t: 3; i: ");
			if (s.equals("+"))
				System.out.println("10H;");
			else if (s.equals("-"))
				System.out.println("11H;");
			else if (s.equals("*"))
				System.out.println("20H;");
			else if (s.equals("/"))
				System.out.println("21H;");
		} else if (relationOperator.contains(s)) { // 如果词是一个关系运算符
			System.out.print("t: 4; i: ");
			if (s.equals("<"))
				System.out.println("00H;");
			else if (s.equals("<="))
				System.out.println("01H;");
			else if (s.equals("="))
				System.out.println("02H;");
			else if (s.equals(">"))
				System.out.println("03H;");
			else if (s.equals(">="))
				System.out.println("04H;");
			else if (s.equals("<>"))
				System.out.println("05H;");
		}
	}

	private void printAnalyseSource() {
		// 打印分析后的源文件
		System.out.println("\n---analyseSourc---\n" + analyseSource.toString());
	}

	private void printAllKeyWords() {
		// 打印所有关键字符集合
		System.out.println("---keywords:");
		for (Iterator it = keyWords.iterator(); it.hasNext();)
			System.out.println(it.next());
		System.out.println("\n---divsymbol:");
		for (Iterator it = divSymbol.iterator(); it.hasNext();)
			System.out.println(it.next());
		System.out.println("\n---arithmeticoperator:");
		for (Iterator it = arithmeticOperator.iterator(); it.hasNext();)
			System.out.println(it.next());
		System.out.println("\n---relationoperator:");
		for (Iterator it = relationOperator.iterator(); it.hasNext();)
			System.out.println(it.next());
	}

	private void printSource() {
		// 打印源代码
		System.out.println("\n---input source:---\n" + source.toString());
	}

	private void outputAnalyseSource() throws IOException {
		// 将所有分析后的代码输出至一个文件
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(
				outputFile)));
		out.print(analyseSource.toString());
		out.close();
	}

	private void checkFile(String keywordsFile, String inputFile,
			String outputFile) throws NullPointerException {
		// 检查目录是否已经存在，如不存在则创建它
		File k = new File(keywordsFile), i = new File(inputFile), o = new File(
				outputFile);
		if (k.exists() == false) {
			try {
				k.createNewFile();
			} catch (IOException e) { /* 忽略 */
			}
		}
		if (i.exists() == false) {
			try {
				i.createNewFile();
			} catch (IOException e) { /* 忽略 */
			}
		}
		if (i.exists() == false) {
			try {
				i.createNewFile();
			} catch (IOException e) { /* 忽略 */
			}
		}
	}

	public Syntax(String keywordsFile, String inputFile, String outputFile) {
		// 构造器设定关键字文件、源文件、输出文件名
		this.keywordsFile = keywordsFile;
		this.inputFile = inputFile;
		this.outputFile = outputFile;
		// 先检查目录文件是否已经创建
		checkFile(keywordsFile, inputFile, outputFile);
	}

	public static void main(String[] args) throws IOException {
		String path="E:/eclipse/workspace/JxvaFramework/work/net/jxva/";
		Syntax syntax = new Syntax(path+"keywords.txt", path+"input.txt", path+"output.txt");
		syntax.readKeyWords();
		syntax.readSource();
		syntax.printSource();
		syntax.printAnalyseSource();
		syntax.outputAnalyseSource();
	}
}