<?php
//require_once('openfire_api.php');
	//echo $out = getUsers();
	
	switch($action){
		case "ADLIST":
			$out = userlist();
			break;
	}
	echo $out;
	
	function userlist()
	{		
		$data = Array();
		
		$output = getUsers();
		$data['code'] = '200';
		
		$userlist = json_decode($output, true);
		
		$data['users'] = $userlist['user'];
		//$users = $userlist['user'];
		return json_encode($data);
		
		
	}
	//echo $action;
?>