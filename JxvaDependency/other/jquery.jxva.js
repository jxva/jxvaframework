String.prototype.trim   =function(){return this.replace(/^\s+|\s+$/g, "");}
String.prototype.ltrim  =function(){return this.replace(/(^\s*)/g, "");}
String.prototype.rtrim  =function(){return this.replace(/(\s*$)/g, "");}
String.prototype.len    =function(){return this.replace(/[^\x00-\xff]/g,"rr").length;}


jQuery.extend({
    isNull: function(s){return s==null;},
    isEmpty: function(s){var t= jQuery.trim(s);return t.length==0;},
    isChinese: function(s){return /^[\u4E00-\u9FA5]{0,25}$/.test(s);},
    isEmail: function(s){return /^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/.test(s);},
    isUrl: function(s){return /^(file|http|https|ftp|mms|telnet|news|wais|mailto):\/\/(.+)$/.test(s);},
    isIp: function(s){return /^(\d{1}|\d{2}|[0-1]\d{2}|2[0-4]\d|25[0-5])\.(\d{1}|\d{2}|[0-1]\d{2}|2[0-4]\d|25[0-5])\.(\d{1}|\d{2}|[0-1]\d{2}|2[0-4]\d|25[0-5])\.(\d{1}|\d{2}|[0-1]\d{2}|2[0-4]\d|25[0-5])$/.test(s);},
    isPostcode: function(s){return /^(\d){6}$/.test(s);},
    isPhone: function(s){return /(^[0-9]{3,4}\-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^[0-9]{3,4}[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/.test(s);},
    isMobile: function(s){return /^0{0,1}1(3|5)[0-9]{9}$/.test(s);},
    isUsername: function(s){return /^[a-z]\w{4,15}$/i.test(s);},
    isPassword: function(s){return /^[\x00-\xff]{6,16}$/.test(s);},
    
    
//15位身份证转换为18位,如果参数字符串中有非数字字符,则返回"#"表示参数不正确
idCardUpdate: function(_str)
{ 
    var idCard18;
    var regIDCard15 = /^(\d){15}$/;
    if(regIDCard15.test(_str))
    {
        var nTemp = 0;
        var ArrInt = new Array(7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2);
        var ArrCh = new Array('1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2');
        _str = _str.substr(0,6) + '1' + '9' + _str.substr(6,_str.length-6);
        for(var i=0;i<_str.length;i++)
        {
            nTemp += parseInt(_str.substr(i,1)) * ArrInt[i];
        }
        _str += ArrCh[nTemp % 11];
        idCard18 = _str;        
    }
    else
    {
        idCard18 = "#";
    }
    return idCard18;
},


//是否是有效中国身份证
isIDCard: function(_str)
{
    var iSum=0;
    var info="";
    var sId;
    var aCity={11:"北京",12:"天津",13:"河北",14:"山西",15:"内蒙古",21:"辽宁",22:"吉林",23:"黑龙江",31:"上海",32:"江苏",33:"浙江",34:"安徽",35:"福建",36:"江西",37:"山东",41:"河南",42:"湖北",43:"湖南",44:"广东",45:"广西",46:"海南",50:"重庆",51:"四川",52:"贵州",53:"云南",54:"西藏",61:"陕西",62:"甘肃",63:"青海",64:"宁夏",65:"新疆",71:"台湾",81:"香港",82:"澳门",91:"国外"};
    //如果输入的为15位数字,则先转换为18位身份证号
    if(_str.length == 15)    
        sId = jQuery.idCardUpdate(_str);    
    else
        sId = _str;
    
    if(!/^\d{17}(\d|x)$/i.test(sId))
    {
        return false;
    }
    sId=sId.replace(/x$/i,"a");
    //非法地区
    if(aCity[parseInt(sId.substr(0,2))]==null)
    {
        return false;
    }
    var sBirthday=sId.substr(6,4)+"-"+Number(sId.substr(10,2))+"-"+Number(sId.substr(12,2));
    var d=new Date(sBirthday.replace(/-/g,"/"))    
    //非法生日
    if(sBirthday!=(d.getFullYear()+"-"+ (d.getMonth()+1) + "-" + d.getDate()))
    {
        return false;
    }
    for(var i = 17;i>=0;i--) 
    {
        iSum += (Math.pow(2,i) % 11) * parseInt(sId.charAt(17 - i),11);
    }
    if(iSum%11!=1)
    {
        return false;
    }
    return true;    
}

});