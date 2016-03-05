<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" >
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="en" />

	<!-- blueprint CSS framework -->
	<link rel="stylesheet" type="text/css" href="css/screen.css" media="screen, projection" />
	<link rel="stylesheet" type="text/css" href="css/print.css" media="print" />
	<!--[if lt IE 8]>
	<link rel="stylesheet" type="text/css" href="css/ie.css" media="screen, projection" />
	<![endif]-->

	<link rel="stylesheet" type="text/css" href="css/main.css" />
        <link rel="stylesheet" type="text/css" href="css/form.css" />

        <link rel="stylesheet" type="text/css" href="css/jqueryui.css" />
        <link rel="stylesheet" type="text/css" href="css/styles.css" />

        <script type='text/javascript' src='js/jquery.min.js'></script>

        <script type='text/javascript' src='js/jquery.appear-1.1.1.js'></script>
        <script type='text/javascript' src='js/tooltip.min.js'></script>

        <script type='text/javascript' src='js/tinymce.editor.js'></script>

        <script type='text/javascript' src='js/core.js'></script>
        <script type='text/javascript' src='js/ajax.js'></script>
        <script type='text/javascript' src='js/funcs.js' type='text/javascript'></script>

        <script type='text/javascript'>
            var changes_warning = 'Y';

            $(function() {
                $.runCart('A');
            });
        </script>

        <link rel="shortcut icon" href="/phonebook/css/images/smartaxa.ico" />
	<title>每天 - Login Site</title>
</head>

<body>

<table cellpadding="0" cellspacing="0" border="0" class="content-table">
    <tr valign="top">
        <td width="1px" class="side-menu">
            <div id="right_column">
            </div>
        </td>
        <td class="login-page">

            <div id="main_column_login" class="clear">
                <div class="login-wrap">
                    <h1 class="clear">
                        <a href="index.php" class="float-left">每天</a>
                        <span>login panel</span>
                    </h1>
                    <form action="/admin/postLogin" method="post" name="loginform" class="cm-form-highlight cm-skip-check-items">
                        <div class="login-content">
                            <div class="clear-form-field">
                                <p><label for="email" class="cm-required cm-email">Email:</label></p>
                                <input id="email" type="text" name="email" size="20" class="input-text cm-focus" tabindex="1" value="" ></input>
                            </div>
                            <div class="clear-form-field">
                                <p><label for="password" class="cm-required">Password:</label></p>
                                <input type="password" id="password" name="password" size="20" class="input-text" tabindex="2" value="" ></input>
                            </div>
                            <div class="buttons-container nowrap">
                                <div class="float-left">
                                    <span  class="submit-button cm-button-main ">
                                        <input type="submit" name="dispatch[auth.login]" value="Login" tabindex="3" />
                                    </span>
                                </div>
                                
                            </div>
                        </div>
                    </form>
                </div>
                <!--main_column_login-->
            </div>
        </td>
    </tr>
</table>

<div class="cm-notification-container cm-notification-container-top">
<?php
	if(isset($_SESSION['login']) && $_SESSION["login"] === '0' )
	{
		echo "<div class='notification-e'>
						 <img id='close_notification_f7c717ded4d80bebcb6f777f80e6840f7b48' class='cm-notification-close hand' src='/css/images/icons/icon_close.gif' width='13' height='13' border='0' alt='Close' title='Close'>
						 <div class='notification-header-e'>Error</div>
						 <div>";

		echo 'Email or password is invalid.'    . "&nbsp;&nbsp;&nbsp;";
		
		echo "</div></div>";	
		$_SESSION["login"] = 2;		
	}
?>
</div>
</body>

</html>
