<?php 

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use \DateTime;
use \DateInterval;
use App\Models\OfflineMessage;


class Stage extends Model { 
	protected $table = 'stage';
	//protected $table = 'advertisement';
	public static $error = 0;
	
	protected $fillable = array('fullname', 'username', 'password', 'email', 'phone', 'thumbnail', 'birthday', 'address', 'supportnum', 'pointnum', 'commentnum', 'frinenum', 'token', 'modifydate');
	public $timestamps = false;
	
	public function comments()
    {
        return $this->hasMany('App\Models\OfflineMessage', 'username', 'username');
    }
	
	public static function addStage($hno, $userno, $thumb, $content, $mdate)
	{
		
		// create user
		$stage = new Stage();
		$stage->userno = $userno;	
		$stage->hno = $hno;	
		$stage->thumbnail = $thumb;	
		$stage->content = $content;			
		$stage->modifydate = $mdate;		
			
		$id = $stage->save();
		return $id;
	}	
	public static function deleteStage($sno, $userno)
	{
		$stage = Stage::where('id', '=', $sno)-> where('userno', '=', $userno) ->first();
		
		if( empty($stage) )
			return -1;
		$stage->delete();
		
		return 1;
	}
	public static function getStages($userno, $hno)
	{
		//limit(30)->offset(30)->get();
		//old take(30)->skip(30)->get();
		//$stage = TempStage::where('hno', '=', $hno) -> where('userno', '=', $userno) ->first();
		// -> offset($pagenum)->limit($limit)
		
		$stage = Stage::where('userno', '=', $userno) ->where('hno', '=', $hno)->get();
		
		
		return $stage;
	}
	public static function getStage($hno)
	{
		//limit(30)->offset(30)->get();
		//old take(30)->skip(30)->get();
		//$stage = TempStage::where('hno', '=', $hno) -> where('userno', '=', $userno) ->first();
		// -> offset($pagenum)->limit($limit)
		
		$stage = Stage::where('hno', '=', $hno)->orderBy('modifydate','DESC')->first();
		
		
		return $stage;
	}
	public static function updateFavoNum($hno, $userno)
	{
		$history = History::where('id', '=', $hno) -> where('userno', '=', $userno) ->first();
		
		if( empty($history) )	// user does not exist
		{
			return -1;
		}else{
			// update user	
			$history->favonum = $history->favonum + 1;
		}				
			
		$history->save();
		return 1;
	}
	
	
	public static function updateCommentNum($hno, $userno)
	{
		$history = History::where('id', '=', $hno) -> where('userno', '=', $userno) ->first();
		
		if( empty($history) )	// user does not exist
		{
			return -1;
		}else{
			// update user	
			$history->commentnum = $history->commentnum + 1;
		}				
			
		$history->save();
		return 1;
	}
	
		
	public static function deleteCommentNum($hno, $userno)
	{
		$history = History::where('id', '=', $hno) -> where('userno', '=', $userno) ->first();
		
		if( empty($history) )
			return -1;
		$history -> commentnum = $history -> commentnum - 1;
		$history->delete();
		
		return 1;
	}
	public static function deleteHistory($hno, $userno)
	{
		$history = History::where('id', '=', $hno) -> where('userno', '=', $userno) ->first();
		
		if( empty($history) )
			return -1;
		$history->delete();
		
		return 1;
	}
}