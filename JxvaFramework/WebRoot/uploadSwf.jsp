<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.jxva.com/tag" prefix="j"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<title>View Page</title>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
</head>
<body>
	<j:form action="" method="post" id="form" bean="#request.ads">
			<span style="float:left;_margin:3px;">
				<j:input name="picUrl" readonly="true" id="picUrl"/>
			</span>    
			<span class="browser" id="upload"></span>
	</j:form>
<script type="text/javascript" src="res/js/jquery.js"></script>
<script type="text/javascript" src="res/js/jquery.flash.js"></script>
<script type="text/javascript">
$(function(){
    $('#upload').flash({ 
        src: 'res/swf/upload',
        id: 'demo',
        name:'demo',
        flashvars: {
    		'uploadUrl':(document.all?'':'../../')+'test!upload.jv',
    		'callback':'uploadCallback',
        	'fileCount':100,
        	'fileSize':3000,
        	'fileType':'All Images (*.jpg,*.jpeg,*.gif,*.png)'
        },
        width: 80,
        height:20,
        wmode:"opaque",
        allowScriptAccess: "always"
    });
});
function uploadCallback(result){
	alert(result);
	//$('#picUrl').val(result);
}
</script>
</body>
</html>