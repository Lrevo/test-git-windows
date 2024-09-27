<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="style/style.css">
<title>ログイン画面</title>
</head>
<header>
	<h1 class="border">会議室予約</h1>
</header>
<body>
	<h2>ログイン</h2>
	<p class="err">${errr }　 </p>
	<form action="Login" method="post">
		ユーザー名：<input type="text" name="userId" minlength="7" maxlength="7"
			value="" placeholder="7文字で入力"><br> 
			パスワード：<input
			type="password" name="userPw" minlength="6" maxlength="10" value=""
			placeholder="6文字～10文字で入力"><br><br>
			 <input type="submit"
			value="ログイン">
	</form>
	<div class="border"></div>
</body>

</html>