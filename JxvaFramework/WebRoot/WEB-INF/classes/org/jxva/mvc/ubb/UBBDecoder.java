/*
 * Copyright @ 2006-2010 by The Jxva Framework Foundation
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package org.jxva.mvc.ubb;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2009-01-12 13:07:33 by Jxva
 */
public class UBBDecoder {
    
    public static final int MODE_IGNORE = 0;
    public static final int MODE_CLOSE = 1;
    
    private static final int SEARCH_LEN = 200;

    /**
     * 进行UBB标签的转换
     * @param s 需要转换的包含UBB标签的文本
     * @param th 用户自定义的UBB标签的处理器的实例
     * @param mode 容错模式,可以是忽略模式(MODE_IGNORE)或关闭模式(MODE_CLOSE)
     * @return 转换后的包含HTML标签的文本
     */
    public static final String decode(String s, UBBTagHandler th, int mode) {
        return decode(s, th, mode, false);
    }
    
    /**
     * 进行UBB标签的转换
     * @param s 需要转换的包含UBB标签的文本
     * @param th 用户自定义的UBB标签的处理器的实例
     * @param mode 容错模式,可以是忽略模式(MODE_IGNORE)或关闭模式(MODE_CLOSE)
     * @param convBr 是否把'\n'字符也转换为'<br>'
     * @return 转换后的包含HTML标签的文本
     */
    public static final String decode(String s, UBBTagHandler th, int mode, boolean convBr) {
        StringBuffer buf = new StringBuffer(); // 当前文本
        char[] cc = s.toCharArray(); // 把输入转换为字符数组以提高处理性能
        int len = cc.length, pos = 0;
        UBBNode root = new UBBNode(null, "", null, "", false); // 根节点
        UBBNode node = root; // 当前节点
        Stack<UBBNode> stk = new Stack<UBBNode>(); // 使用堆栈处理节点的嵌套
        stk.push(node);
        while (pos < len) { // 只要未到文件末尾就循环
            char cur = cc[pos]; // 当前字符
            if (convBr && cur == '\n') { // 如果当前字符是换行
                buf.append("<br>");
                pos++;
                continue;
            }
            if (cur != '[') { // 只要当前字符不是'['就扩展到当前文本
                buf.append(cur);
                pos++;
                continue;
            }
            // 如果当前字符是'[',那么查找下一个']'
            int ii = indexOf(cc, ']', pos + 1, SEARCH_LEN);
            if (ii == -1) { // 未找到,把当前'['作为一般字符处理,扩展到当前文本
                buf.append(cur);
                pos++;
                continue;
            }
            if (cc[pos + 1] == '/') { // 标签以'/'起始,可能是结束标签
                if (cc[pos + 2] == ']') { // 修正littlebat发现的bug,处理"[/]"的情况
                    buf.append("[/]");
                    pos += 3;
                    continue;
                }
                // 得到结束标签的文本
                String tmp = new String(cc, pos + 2, ii - pos - 2).trim().toLowerCase();
                int cnt = 1;
                boolean find = false;
                // 在节点树上向上查找和本结束标签对应的标签
                for (UBBNode nd = node; nd != null; nd = nd.parent, cnt++) {
                    if (nd.tag.equals(tmp)) {
                        find = true;
                        break;
                    }
                }
                if (find) { // 如果在节点树上找到了和本结束标签对应的标签
                    // 先把当前文本扩展到当前节点
                    addTextChild(node, buf);
                    // 从堆栈中弹出节点,直到找到的标签成为当前节点
                    while (cnt-- > 0) {
                        // 对于CLOSE容错模式,把当前节点和找到的标签节点之间的标签全部关闭
                        if (mode == MODE_CLOSE) {
                            node.closed = true;
                        }
                        node = (UBBNode) stk.pop();
                    }
                    // 关闭当前标签节点,当前节点上移一层
                    node.closed = true;
                    node = node.parent;
                    pos = ii + 1;
                    continue;
                } else { // 未找到对应起始标签,作为普通文本处理
                    buf.append("[/");
                    pos += 2;
                    continue;
                }
            } else if (cc[ii - 1] == '/') { // 标签以'/'结尾,可能是空标签
                String tmp = new String(cc, pos + 1, ii - pos - 2).trim();
                // 由UBBTagHandler决定是否是一个合法空标签
                String[] ss = th.parseTag(tmp, true);
                if (ss != null && ss.length == 2) { // 处理空标签
                    // 先把当前文本扩展到当前节点
                    addTextChild(node, buf);
                    UBBNode nd = new UBBNode(node, ss[0].toLowerCase(), ss[1], 
                            new String(cc, pos, ii + 1 - pos), true);
                    node.addChild(nd);
                    pos = ii + 1;
                    continue;
                }
            }
            // 可能是普通起始标签
            // 得到标签文本
            String tmp = new String(cc, pos + 1, ii - pos - 1).trim();
            // 由UBBTagHandler决定是否是合法标签
            String[] ss = th.parseTag(tmp, false);
            if (ss != null && ss.length == 2) { // 是合法标签
                // 先把当前文本扩展到当前节点
                addTextChild(node, buf);
                // 创建新的节点,扩展到当前节点,然后当前节点下移一层
                UBBNode nd = new UBBNode(node, ss[0], ss[1], 
                        new String(cc, pos, ii + 1 - pos), false);
                node.addChild(nd);
                pos = ii + 1;
                stk.push(nd);
                node = nd;
            } else { // 不是标签,当作普通文本处理
                buf.append('[');
                pos++;
            }
        }
        // 把当前文本中剩余的内容扩展到当前节点
        addTextChild(node, buf);
//        System.out.println("=========================\n" + root.toString(0));
        // 使用节点树构造输出文本
        return decodeNode(th, root);
    }
    
    /**
     * 把文本生成一个纯文本节点并扩展到给定的节点
     * @param node
     * @param buf
     */
    private static void addTextChild(UBBNode node, StringBuffer buf) {
        if (buf.length() > 0) {
            node.addChild(new UBBNode(node, buf.toString()));
            buf.setLength(0);
        }
    }

    /**
     * 从标签节点树来递归的构造输出文本
     * @param th
     * @param node
     * @return
     */
    private static String decodeNode(UBBTagHandler th, UBBNode node) {
        StringBuffer buf = new StringBuffer(); // 输出文本
        if (UBBNode.TEXT == node.tag) {
            // 处理纯文本节点
            buf.append(node.img);
        } else if (!node.closed) {
            // 处理未正常关闭的节点,节点本身当作纯文本处理,对其子节点进行递归处理
            buf.append(node.img);
            List<UBBNode> lst = node.children;
            if (lst != null && lst.size() > 0) {
                for (int i = 0, n = lst.size(); i < n; i++) {
                    buf.append(decodeNode(th, (UBBNode) lst.get(i)));
                }
            }
        } else {
            // 处理正常节点,使用UBBTagHandler来组合输出,并递归处理其子节点
            List<UBBNode> lst = node.children;
            StringBuffer tmp = new StringBuffer();
            if (lst != null && lst.size() > 0) {
                for (int i = 0, n = lst.size(); i < n; i++) {
                    tmp.append(decodeNode(th, (UBBNode) lst.get(i)));
                }
            }
            buf.append(th.compose(node.tag, node.attr, tmp.toString(), node.isEmpty));
        }
        return buf.toString();
    }

    private static final int indexOf(char[] cc, char c, int idx, int len) {
        int end = idx + len;
        if (end > cc.length) end = cc.length;
        for (int i = idx; i < end; i++) {
            if (cc[i] == c) {
                return i;
            }
        }
        return -1;
    }

}

class UBBNode {
    // 纯文本标签的标签文本
    static final String TEXT = "<text>";

    String tag = null;
    String attr = null;
    String img = null;
    UBBNode parent = null;
    List<UBBNode> children = null;
    boolean closed = false;
    boolean isEmpty = false;
    
    UBBNode(UBBNode parent, String tag, String attr, String img, boolean isEmpty) {
        this.parent = parent;
        this.tag = tag.toLowerCase();
        this.attr = attr;
        this.img = img;
        this.isEmpty = isEmpty;
        this.closed = isEmpty;
        this.children = isEmpty ? null : new ArrayList<UBBNode>();
    }
    
    UBBNode(UBBNode parent, String text) {
        this.parent = parent;
        this.tag = TEXT;
        this.attr = text;
        this.img = text;
        this.closed = true;
        this.isEmpty = true;
    }
    
    final void addChild(UBBNode child) {
        children.add(child);
    }
    
    /**
     * 仅供测试用
     */
    public final String toString() {
        return "[tag=\""+tag+"\",attr=\""+attr+"\",closed="+closed
            +",children="+(children != null ? ""+children.size() : "null")+"]";
    }
    
    /**
     * 仅供测试用,打印节点树
     * @param i
     * @return
     */
    final String toString(int i) {
        StringBuffer buf = new StringBuffer();
        for (int j = i; --j >= 0;) {
            buf.append(' ');
        }
        buf.append(toString() + "\n");
        if (children != null && children.size() > 0) {
            for (int j = 0, n = children.size(); j < n; j++) {
                buf.append(((UBBNode) children.get(j)).toString(i + 2));
            }
        }
        return buf.toString();
    }
    
}