<?php
  ini_set("soap.wsdl_cache_enabled", "0"); // disabling WSDL cache 
  
  //WSDL 模式
  //$client = new SoapClient("http://127.0.0.1:8081/lakooWebService?wsdl");
 
  //Non-WSDL 模式
  $client=new SoapClient(null, array('location' => "http://127.0.0.1:8081/lakooWebService",'uri'=> "http://passport.ztemc.com/lakoo"));
  $result = $client->check("admin","dd");
  echo $result;
?>