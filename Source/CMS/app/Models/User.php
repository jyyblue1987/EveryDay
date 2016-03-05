<?php 

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use \DateTime;
use \DateInterval;
use App\Models\OfflineMessage;

//@ Thanks to - http://phpsec.org
function generateHash($plainText, $salt = null)
{
	if ($salt === null)
	{
		$salt = substr(md5(uniqid(rand(), true)), 0, 25);
	}
	else
	{
		$salt = substr($salt, 0, 25);
	}

	$aaa = sha1($salt . $plainText);
	return $salt . sha1($salt . $plainText);
}
function generateRandomNumber($length = 10) {
    //$characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
	$characters = '0123456789';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
}

function generateRandomString($length = 10) {
    $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';	
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
}

class User extends Model { 
	protected $table = 'user';
	public static $error = 0;
	
	protected $fillable = array('fullname', 'username', 'password', 'email', 'phone', 'thumbnail', 'birthday', 'address', 'supportnum', 'pointnum', 'commentnum', 'frinenum', 'token', 'modifydate');
	public $timestamps = false;
	
	public function comments()
    {
        return $this->hasMany('App\Models\OfflineMessage', 'username', 'username');
    }
	
	public static function registerUser($name, $pwd, $email)
	{
		$user = User::where('username', '=', $name)->first();
		
		if( empty($user) )	// user does not exist
		{
			// create user
			$user = new User();
			$user->username = $name;
			$user->pointnum = 1000;
			$send = 0;$receive = 0; $point = 1000;
			$user->sortscore = User:: calSort($send, $receive, $point);
			$user->password = generateHash($pwd);	
			$user->email = $email;		
		}else{
			return ;
		}		
		$user->token = generateRandomString(20);		
		$user->modifydate = time();		
			
		$user->save();
		$user -> error = 0;
		return $user;
	}
	
	public static function updateUser($userno, $fullname, $name, $email, $phone, $birth, $addr, $token)
	{
		$user = User::where('id', '=', $userno)->first();
		
		if( empty($user) )	// user does not exist
		{
			return ;
		}else{
			if($user->token != $token){
				return;
			}
			// update user
			$user->username = $name;		
			$user->fullname = $fullname;		
			$user->email = $email;	
			$user->phone = $phone;	
			$user->birthday = $birth;	
			$user->address = $addr;	
		}				
		$user->modifydate = time();		
			
		$user->save();
		return $user;
	}
	public static function updatethumb($userno, $thumb)
	{
		$user = User::where('id', '=', $userno)->first();
		
		if( empty($user) )	// user does not exist
		{
			return -1;
		}else{
			// update user	
			$user->thumbnail = $fullname;	
		}				
		$user->modifydate = time();		
			
		$user->save();
		return 1;
	}
	
		
	public static function deleteUser($userno)
	{
		$user = User::where('id', '=', $userno)->first();
		
		if( empty($user) )
			return 0;
		
		$id = $user->delete();
		
		return $id;
	}
	public static function confirmUser($userno, $token)
	{
		$user = User::where('id', '=', $userno)->first();
		
		if( empty($user) )
			return -1;
		if($user->token != $token){
			return -2;
		}		
				
		return 1;
	}
	
	public static function login($username, $pwd)
	{
		$password = generateHash($pwd);
		$user = User::
		where('username', '=', $username) -> first();
		
		if( empty($user) ){
			return ;
		}
		$oldpwd = $user->password;
		$entered_pass = generateHash($pwd, $oldpwd);
		if($entered_pass == $oldpwd){
			$user->token = generateRandomString(20);
				
			$user->save();
		}else{
			return;
		}		
		return $user;
	}
	public static function addcontact($userno, $contactno, $token)
	{
		$user = User::
		where('id', '=', $userno) -> first();
		$contact = User::
		where('id', '=', $contactno) -> first();
		if( empty($user) ){
			return -1;
		}
		if( empty($contact) ){
			return -1;
		}
		if($user->token != $token){
			return -2;
		}
		$id = Contacts::registerContact($userno, $contactno);
		User::sumFriendNum($userno);
		if($id == 0){
			return 0;
		}		
		return $id;
	}
	public static function delcontact($userno, $contactno, $token)
	{
		$user = User::
		where('id', '=', $userno) -> first();
		$contact = User::
		where('id', '=', $contactno) -> first();
		if( empty($user) ){
			return -1;
		}
		if( empty($contact) ){
			return -1;
		}
		if($user->token != $token){
			return -2;
		}
		
		$id = Contacts::deleteContact($userno, $contactno);
		User::minusFriendNum($userno);
		if($id == 0){
			return 0;
		}		
		return $id;
	}
	public static function changewd($userno, $oldpwd, $newpwd, $token)
	{
		$oldpassword = generateHash($oldpwd);
		$newpassword = generateHash($newpwd);
		$user = User::where('id', '=', $userno) -> first();
		
		if( empty($user) ){
			return ;
		}
		if($user->token != $token){
			return;
		}
		$cryptpwd = $user->password;
		$entered_pass = generateHash($oldpwd, $cryptpwd);
		
		if($entered_pass == $cryptpwd){
			$user -> password = $newpassword;
			$user->save();
		}else{
			return;
		}		
		return $user;
	}
	public static function forgotpassword($username, $email, $newpwd)
	{
		$newpassword = generateHash($newpwd);
		$user = User::where('username', '=', $username) -> where('email', '=', $email) ->first();
		
		if( empty($user) ){
			return ;
		}
		$user -> password = $newpassword;
		$user->save();
		return $user;
	}
	public static function calSort($send = 0, $receive = 0, $point = 0)
	{
		$f1 = 0.5; $f2 = 0.3; $f3 = 0.2;
		$cal = floatval($f1) * floatval($receive) + floatval($f2) * floatval($send) + floatval($f3) * floatval($point);
		return $cal;
	}
	public static function sumSendNum($userno, $amount)
	{
		$user = User::where('id', '=', $userno) ->first();
		
		$user -> sendnum = $user -> sendnum + $amount;
		$user -> pointnum = $user -> pointnum - $amount;
		$user->sortscore = User:: calSort($user -> sendnum, $user -> receivenum, $user -> pointnum);
		$id = $user->save();
		return $id;
	}
	public static function sumReceiveNum($userno, $amount)
	{
		$user = User::where('id', '=', $userno) ->first();
		
		$user -> receivenum = $user -> receivenum + $amount;
		$user -> pointnum = $user -> pointnum + $amount;
		$user->sortscore = User:: calSort($user -> sendnum, $user -> receivenum, $user -> pointnum);
		$id = $user->save();
		return $id;
	}
	public static function sumPointNum($userno, $amount)
	{
		$user = User::where('id', '=', $userno) ->first();
		
		$user -> pointnum = $user -> pointnum + $amount;
		$user->sortscore = User:: calSort($user -> sendnum, $user -> receivenum, $user -> pointnum);
		$id = $user->save();
		return $id;
	}
	public static function sumFriendNum($userno)
	{
		$user = User::where('id', '=', $userno) ->first();
		
		$user -> friendnum = $user -> friendnum + 1;
		$id = $user->save();
		return $id;
	}
	public static function minusFriendNum($userno)
	{
		$user = User::where('id', '=', $userno) ->first();
		
		$user -> friendnum = $user -> friendnum - 1;
		$id = $user->save();
		return $id;
	}
}