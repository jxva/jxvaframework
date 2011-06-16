<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
	<title>@{#request.title}</title>
	<style type="text/css">
		td,body,input{font-size:12px;font-family:arial,sans-serif;line-height:180%;}
	</style>
</head>
<body>
	@{#request.content},哈哈<br/><br/>
	
	
	<table border="1" width="100%" bgcolor="#FFFFFF" cellpadding="1" cellspacing="1">
		<tr>
			<th>Index</th><th>BookName</th><th>Press</th><th>PressType</th><th>Category</th><th>Description</th><th>Quantity</th><th>Price</th><th>Total</th><th>Hits</th><th>IssuerDate</th><th>CreateTime</th><th>IsCommend</th><th>IsAvailable</th><th>Edit</th><th>Del</th>
		</tr>
		@foreach{item:#request.list} 
	    <tr>
	    	<td>@{item.bookId}</td>
	        <td><a href='book!showBook.jv?bookId=@{item.bookId}'>@{item.name}</a></td>
	        <td><a href="press!showPress.jv?pressId=@{item.pressId}">@{item.press.name}</a></td>
	        <td><a href="pressType!showPressType.jv?pressTypeId=@{item.press.pressTypeId}">@{item.press.pressType.name}</a></td>
	        <td><a href="category!showCategory.jv?categoryId=@{item.categoryId}">@{item.category.name}</a></td>
	        <td>@{item.description}</td>
	        <td>@{item.quantity}</td>
	        <td>@{item.price}</td>
	        <td>@{item.quantity*item.price}</td>
	        <td>@{item.hitCount+4}</td>
	        <td>@{item.issuerDate}</td>
	        <td>@{item.createTime}</td>
	        <td>
	        	@{item.isCommend==0?'否':'是'}	  
	        	</td>
	     	<td>	@{item.isAvailable==0?'否':'是'}</td>
	        <td><a href='mtag!form.jv?bookId=@{item.bookId}'>Edit</a></td>
	        <td><a href='book!delBook.jv?bookId=@{item.bookId}'>Del</a></td>
	    </tr>
	    @end{}
	</table>
</body>
</html>