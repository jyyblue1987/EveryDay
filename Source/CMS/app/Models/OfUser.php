<?php 

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use \DateTime;
use \DateInterval;
use Illuminate\Pagination;

class User extends Model { 
	protected $table = 'user';
	
	public $timestamps = false;
	function static generateRandomNumber($length = 10) {
    //$characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
	$characters = '0123456789';
    $charactersLength = strlen($characters);
    $randomString = '';
    for ($i = 0; $i < $length; $i++) {
        $randomString .= $characters[rand(0, $charactersLength - 1)];
    }
    return $randomString;
}

	function static generateRandomString($length = 10) {
		$characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';	
		$charactersLength = strlen($characters);
		$randomString = '';
		for ($i = 0; $i < $length; $i++) {
			$randomString .= $characters[rand(0, $charactersLength - 1)];
		}
		return $randomString;
	}
	public static function registerUser($fullname, $name, $pwd, $email, $thumb, $birth, $addr, $supportnum, $pointnum, $commentnum, $friendnum)
	{
		$user = OfUser::where('username', '=', $name)->first();
		
		if( empty($user) )	// user does not exist
		{
			// create user
			$user = new User();
			$user->username = $name;			
			$user->active = 0;
		}
		else
		{			
			if( $user->active === 1 ) // user is activated
			{
				// set device and pushkey, token
				$user->device = $device;
				
				if( !empty($pushkey) )
					$user->pushkey = $pushkey;

				$user->token = generateRandomString(20);
			
				$user->save();
				
				return $user;			
			}
			$user->active = 0;	
		}
		
		$user->vcode = generateRandomNumber(6);		
		$user->expire = time() + 600;		
			
		$user->save();
		
		return $user;
	}
	
}
