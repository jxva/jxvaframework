<?php
require_once('lib/nusoap.php');
$client = new nusoap_client('http://passport.ztemc.com:8081/tomWebService?wsdl',true);
$client->soap_defencoding = 'UTF-8';
$client->decode_utf8 = false;
$err = $client->getError();
if ($err) {
	echo $err;
}
//检测用户信息
$result = $client->call('check',array('username' =>'china'),array('password' =>'fdsa'),'',false,true);
echo $result;
?>

<?php
  echo base64_encode("1?china");
  echo base64_decode("MT9jaGluYQ==");
?>

<?php
ini_set('display_errors', 'on');
require_once('lib/nusoap.php');
$client = new nusoap_client('http://passport.ztemc.com:8081/tomWebService?wsdl',true);
//$client->soap_defencoding = 'UTF-8';
//$client->decode_utf8 = false;
$err = $client->getError();
if ($err) {
	echo $err;
}
echo $_COOKIE["ssoid"]."<br/>";
//检测用户信息
$tomUser = $client->call('getTomUserBySsoId',array('ssoid' =>$_COOKIE["ssoid"]));
echo $tomUser."<br><br><br>";
foreach($tomUser as $data){
  echo $data."<br>";
}
?>

<?php
header('P3P: CP="CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR"');
setcookie("ssoid",$_GET['ssoid'],time()+3600,"/");
?>

print ssoid:<br>
<?php
echo $_COOKIE["ssoid"]
?>


    $ParamArr=array(
        'objTime'=>"2008-03-05",
    );
    $param=array('parameters'=>$ParamArr);
    $result = $client->call('testsoup',$param, '', '', false, true);
    
    
    
    
    
    
    
    
    
    
    
        function escape($str) {
        preg_match_all("/[\x80-\xff].|[\x01-\x7f]+/",$str,$r);
        $ar = $r[0];
         foreach($ar as $k=>$v) {
             if(ord($v[0]) < 128) {
                $ar[$k] = rawurlencode($v);
             } else {
                $ar[$k] = "%u".bin2hex(iconv("GB2312","UCS-2",$v));
             }
         }
         return join("",$ar);
     }

     function unescape($str) {
        $str = rawurldecode($str);
        preg_match_all("/(?:%u.{4})|.+/",$str,$r);
        $ar = $r[0];
         foreach($ar as $k=>$v) {  
             if(substr($v,0,2) == "%u" && strlen($v) == 6) {
                $ar[$k] = iconv("UCS-2","GB2312",pack("H4",substr($v,-4)));
             }
         }
         return join("",$ar);
     }
     
     
//使用方法

接收参数时,需要解码

如:

var $queryString=unescape($_REQUEST('queryString'));