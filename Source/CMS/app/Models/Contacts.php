<?php 

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use \DateTime;
use \DateInterval;
use App\Models\OfflineMessage;

class Contacts extends Model { 
	protected $table = 'contacts';
	public static $error = 0;
	
	protected $fillable = array('id', 'userno', 'contactno');
	public $timestamps = false;
	
	public function comments()
    {
        return $this->hasMany('App\Models\OfflineMessage', 'username', 'username');
    }
	
	public static function registerContact($userno, $contactno)
	{
		$contact = Contacts::where('userno', '=', $userno)->where('contactno', '=', $contactno)->first();
		
		if( empty($contact) )	// user does not exist
		{
			// create user
			$contact = new Contacts();
			$contact->userno = $userno;		
			$contact->contactno = $contactno;	
		}else{
			return 0;
		}		
		$contact->save();
		return 1;
	}
		
	public static function deleteContact($userno, $contactno)
	{   
		$contact = Contacts::where('userno', '=', $userno)->where('contactno', '=', $contactno)->first();
		
		if( empty($contact) )
			return 0;
		
		$contact->delete();
		
		return 1;
	}
	public static function checkContact($userno, $contactno)
	{   
		$contacts = Contacts::where('userno', '=', $userno)->where('contactno', '=', $contactno)->first();
		if( empty($contact) )	// user does not exist
		{
			return 0;
		}
		
		return 1;
	}
	public static function getContacts($userno, $pagenum, $limited)
	{   
		$contacts = Contacts::where('userno', '=', $userno)->orderBy('pointnum','DESC')-> offset($pagenum)->limit($limit)->get();
		$users = array();
		foreach($contacts as $contact){
			$users[]= User::where('no', '=', $contact->contactno)->first();
		}
		
		return $users;
	}
}