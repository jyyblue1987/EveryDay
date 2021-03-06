<?php 

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use \DateTime;
use \DateInterval;
use App\Models\OfflineMessage;


class ReceivePoint extends Model { 
	protected $table = 'receivesupport';
	//protected $table = 'advertisement';
	public static $error = 0;
	
	protected $fillable = array('fullname', 'username', 'password', 'email', 'phone', 'thumbnail', 'birthday', 'address', 'supportnum', 'pointnum', 'commentnum', 'frinenum', 'token', 'modifydate');
	public $timestamps = false;
	
	public function comments()
    {
        return $this->hasMany('App\Models\OfflineMessage', 'username', 'username');
    }
	
	public static function addReceivePoint($userno, $huserno, $amount)
	{
		$stage = ReceivePoint::where('userno', '=', $userno) -> where('huserno', '=', $huserno) ->first();
		
		if( empty($stage) )	// user does not exist
		{
			// create user
			$stage = new ReceivePoint();
			$stage->userno = $userno;
			$stage->huserno = $huserno;	
			$stage->amount = $amount;			
			$stage->modifydate = time();	
		}else{
				
			$stage->amount = $stage->amount + $amount;
		}	
			
		$id = $stage->save();
		return $id;
	}	
	public static function getReceivePoints($userno, $huserno)
	{
		//limit(30)->offset(30)->get();
		//old take(30)->skip(30)->get();
		//$stage = TempStage::where('hno', '=', $hno) -> where('userno', '=', $userno) ->first();
		// -> offset($pagenum)->limit($limit)
		
		$stage = ReceivePoint::where('userno', '=', $userno) ->where('huserno', '=', $huserno)->first();
				
		return $stage;
	}
}