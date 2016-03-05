<?php 

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use \DateTime;
use \DateInterval;
use App\Models\OfflineMessage;


class TempStage extends Model { 
	protected $table = 'temp_stage';
	//protected $table = 'advertisement';
	public static $error = 0;
	
	protected $fillable = array('fullname', 'username', 'password', 'email', 'phone', 'thumbnail', 'birthday', 'address', 'supportnum', 'pointnum', 'commentnum', 'frinenum', 'token', 'modifydate');
	public $timestamps = false;
	
	public function comments()
    {
        return $this->hasMany('App\Models\OfflineMessage', 'username', 'username');
    }
	
	public static function addStage($userno, $thumb, $content)
	{
		
		// create user
		$stage = new TempStage();
		$stage->userno = $userno;	
		$stage->thumbnail = $thumb;	
		$stage->content = $content;			
		$stage->modifydate = time();		
			
		$id = $stage->save();
		return $id;
	}	
	public static function deleteStage($sno, $userno)
	{
		$stage = TempStage::where('id', '=', $sno)-> where('userno', '=', $userno) ->first();
		
		if( empty($stage) )
			return -1;
		$stage->delete();
		
		return 1;
	}
	public static function getStages($userno)
	{
		//limit(30)->offset(30)->get();
		//old take(30)->skip(30)->get();
		//$stage = TempStage::where('hno', '=', $hno) -> where('userno', '=', $userno) ->first();
		// -> offset($pagenum)->limit($limit)
		
		$stage = TempStage::where('userno', '=', $userno)->get();
		
		
		return $stage;
	}
	public static function getStage($userno)
	{
		//limit(30)->offset(30)->get();
		//old take(30)->skip(30)->get();
		//$stage = TempStage::where('hno', '=', $hno) -> where('userno', '=', $userno) ->first();
		// -> offset($pagenum)->limit($limit)
		
		$stage = TempStage::where('userno', '=', $userno)->orderBy('modifydate','DESC')->first();
		
		
		return $stage;
	}
	public static function addHistory($userno)
	{
		//limit(30)->offset(30)->get();
		//old take(30)->skip(30)->get();
		//$stage = TempStage::where('hno', '=', $hno) -> where('userno', '=', $userno) ->first();
		//->orderBy('modifydate','ASC')
		$history = History::addHistory($userno);
		$stages = TempStage::where('userno', '=', $userno)->get();
		foreach($stage as $stage){
			Stage::addStage($history->id, $userno, $stage->thumbnail, $stage->content, $stage->modifydate);		
			TempStage::deleteStage($stage -> id, $userno);			
		}		
		
		return 1;
	}
}