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

/**
 * 
 * @author  The Jxva Framework
 * @since   1.0
 * @version 2009-01-12 13:07:17 by Jxva
 */
public class SimpleTagHandler implements UBBTagHandler {
        
//    [b]文字加粗体效果[/b]
//    [i]文字加倾斜效果[/i]
//    [u]文字加下划线效果[/u]
//    [size=4]改变文字大小[/size]
//    [color=red]改变文字颜色[/color]
//    [quote]这个标签是用来做为引用所设置的,如果你有什么内容是引用自别的地方,请加上这个标签！[/quote]  
//    [url]http://www.cnjm.net[/url]
//    [url=http://www.cnjm.net]JAVA手机网[/url]
//    [email=webmaster@cnjm.net]写信给我[/email] 
//    [email]webmaster@cnjm.net[/email]
//    [img]http://www.cnjm.net/myimages/mainlogo.gif[/img]    
    
    public SimpleTagHandler() { }

    public String[] parseTag(String s, boolean isEmpty) {
        if (isEmpty) { // 本处理器不支持空标签
            return null;
        }
        // 如果标签中有'='号就把标签分为UBB标记和属性两部分,否则属性为null
        String tag = s, attr = null;
        int idx = s.indexOf('=');
        if (idx >= 0) {
            tag = s.substring(0, idx);
            attr = s.substring(idx + 1);
        }
        String tmp = tag.toLowerCase(); // 大小写不敏感
        // 只有下面的标记是本处理器支持的
        if ("b".equals(tmp) || 
            "i".equals(tmp) ||
            "u".equals(tmp) ||
            "size".equals(tmp) ||
            "color".equals(tmp) ||
            "quote".equals(tmp) ||
            "url".equals(tmp) ||
            "email".equals(tmp) ||
            "img".equals(tmp)) { 
            return new String[] { tag, attr };
        }
        // 不是一个合法的UBB标签,作为普通文本处理
        return null;
    }

    public String compose(String tag, String attr, String data, boolean isEmpty) {
        // 针对不同标记进行组合工作
        String tmp = tag;
        if ("b".equals(tmp) ||
            "i".equals(tmp) ||
            "u".equals(tmp)) {
            return "<" + tag + ">" + data + "</" + tag + ">";
        } else if ("size".equals(tmp) ||
            "color".equals(tmp)) {
            return "<font " + tag + "='" + attr + "'>" + data + "</font>";
        } else if ("quote".equals(tmp)) {
            return "<table cellpadding=0 cellspacing=0 width=94% bgcolor=#000000 align=center style='table-layout:fixed'><tr><td><table width=100% cellpadding=5 cellspacing=1 style='table-layout:fixed'><tr><td bgcolor=#FFFFFF style='left: 0px; width: 100%; word-wrap: break-word'>"
                    + data + "</td></tr></table></td></tr></table>";
        } else if ("url".equals(tmp)) {
            String url = attr != null ? attr : data;
            return "<a href='" + url + "' target=_blank>" + data + "</a>";
        } else if ("email".equals(tmp)) {
            String email = attr != null ? attr : data;
            return "<a href='mailto:" + email + "'>" + data + "</a>";
        } else if ("img".equals(tmp)) {
            return "<img src='" + data + "' border=0>";
        }
        return data;
    }
    
    // 测试代码,可以运行这个类,并把包含UBB标签的文本作为参数传入来测试
    // 比如java util.SimpleTagHandler "[color=red]你[color=blue]好[/color]啊[/color]"
    public static void main(String[] args) throws Exception {
    	String s="[color=red]你[color=blue]好[/color]啊[/color]";
        System.out.println(">>>>" +s);
        // 下面采用了忽略模式来容错,你也可以用MODE_CLOSE试验一下关闭模式的容错效果
        System.out.println("=========================\n" 
                + UBBDecoder.decode(s, new SimpleTagHandler(), UBBDecoder.MODE_IGNORE));
    }

}
