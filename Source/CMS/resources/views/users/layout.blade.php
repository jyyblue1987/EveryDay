<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="language" content="en" />

	<!-- blueprint CSS framework -->
        <link rel="stylesheet" type="text/css" href="/css/screen.css" media="screen, projection" />
	<link rel="stylesheet" type="text/css" href="/css/print.css" media="print" />
	<!--[if lt IE 8]>
	<link rel="stylesheet" type="text/css" href="/css/ie.css" media="screen, projection" />
	<![endif]-->

	<link rel="stylesheet" type="text/css" href="/css/main.css" />
	<link rel="stylesheet" type="text/css" href="/css/form.css" />
        
	<link rel="stylesheet" type="text/css" href="/css/styles_cms.css" />
    <link rel="stylesheet" type="text/css" href="/css/mystyle.css" />
    <link rel="stylesheet" type="text/css" href="/css/token-input.css" />
    <link rel="stylesheet" type="text/css" href="/css/token-input-facebook.css" />
    <link rel="stylesheet" type="text/css" href="/css/jqueryui.css" />
	<link rel="stylesheet" href="/css/uploadfile.css">
	<link rel="stylesheet" href="/css/multi-select.css">

	
	<script type='text/javascript' src='/js/jquery.min.js'></script>
    <script type='text/javascript' src='/js/jquery.ui.js'></script>
    <!--<script type='text/javascript' src='./js/jquery.tokeninput.js'></script>-->
    <script type='text/javascript' src='/js/jquery.browser.min.js'></script>
    <!--<script type='text/javascript' src='./js/jquery-ui.custom.min.js'></script>-->

    <script type='text/javascript' src='/js/jquery.appear-1.1.1.js'></script>
    <script type='text/javascript' src='/js/tooltip.min.js'></script>

    <script type='text/javascript' src='/js/tinymce.editor.js'></script>

    <script type='text/javascript' src='/js/core.js'></script>
    <script type='text/javascript' src='/js/ajax.js'></script>
    <script type='text/javascript' src='/js/funcs.js' type='text/javascript'></script>
	<script src="/js/jquery.uploadfile.js"></script>
        

	<script type='text/javascript'>
        //$(document).ready(function(){
        var changes_warning = 'Y';
        $(function(){
                $.runCart('A');
        });
        //});
    </script>

	<link rel="shortcut icon" href="/images/ico/favicon.ico" />
	<link rel="stylesheet" type="text/css" href="/assets/7b8be64/gridview/styles.css" />
	<link rel="stylesheet" type="text/css" href="/assets/bda30421/pager.css" />
	<script type="text/javascript" src=""></script>
	<script type="text/javascript" src="/assets/89e1c5f0/jquery.ba-bbq.js"></script>
	<title>每天</title>
</head>

<body>

    
<script type='text/javascript'>
    $(document).ready(function() {        
        
	});
</script>

<div id="ajax_loading_box" class="ajax-loading-box">
    <div id="ajax_loading_message" class="ajax-inner-loading-box">Loading...</div>
</div>

<div class="header-wrap">
    <div id="header">
        <div id="logo">
            <a href="/">每天</a>
			<!--<a class="logo" href="/contactus"><img src="/images/logo/logo1.png" alt=""></a>-->
        </div>

        <div id="top_quick_links">
            <div class="nowrap">
                <a id="update_profile" href="/email">
                    <span>admin@meitian.com</span>
                </a>
                <span class="top-signout" title="Sign out">
                    <a href="/logout" class="text-link">&nbsp;</a>
                </span>
            </div>
        </div>
        <div id="top_menu">
            <ul id="alt_menu"></ul>
        </div>
        <ul id="menu">
            <li class="dashboard dashboard-active">
                <a href="/admin/advertisement" title="Home">&nbsp;</a>
            </li>
            <li>
                <a class="drop">Users</a>
                <div class="dropdown-column">
                    <div class="col">
                        <ul>
                            <li class="blank users">
                            	<a href="/admin/index">
                                    <span>Users</span>
                                    <span class="hint">Manage users that are registered at your store.</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
			</li>   

            <li>
                <a class="drop">Histories</a>
                <div class="dropdown-column">
                    <div class="col">
                        <ul>
                            <li class="blank users">
                            	<a href="/">
                                    <span>Histories</span>
                                    <span class="hint">Manage Histories that are registered at your store.</span>
                                </a>
                            </li>
                        </ul>
                    </div>
                </div>
            </li>
            
            
	
        </ul>
			
    </div>
</div>
	@yield('content')
</body>
</html>
