<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<h2>Single File Upload</h2>
<form action="test!upload.jv" method="post" enctype="multipart/form-data">
	<input type="text" name="username" value="中国"/>
		<input type="text" name="email" value="jxva@qq.com"/>
	<input type="file" name="Filedata" />
	<input type="submit" value="Upload"/>
</form>

<h2>Multi File Upload</h2>
<form action="test!uploadMulti.jv" method="post" enctype="multipart/form-data">
	<input type="text" name="username" value="测试"/>
		<input type="text" name="email" value="hi@jxva.com"/>
	<input type="file" name="Filedata1" /><br />
	<input type="file" name="Filedata2" /><br />
	<input type="file" name="Filedata3" /><br />
	<input type="file" name="Filedata4" /><br />
	<input type="submit" value="Upload"/>
</form>