<?php 

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use \DateTime;
use \DateInterval;
use App\Models\OfflineMessage;


class Favorite extends Model { 
	protected $table = 'favorite';
	//protected $table = 'advertisement';
	public static $error = 0;
	
	protected $fillable = array('fullname', 'username', 'password', 'email', 'phone', 'thumbnail', 'birthday', 'address', 'supportnum', 'pointnum', 'commentnum', 'frinenum', 'token', 'modifydate');
	public $timestamps = false;
	
	public function comments()
    {
        return $this->hasMany('App\Models\OfflineMessage', 'username', 'username');
    }
	
	public static function addFavorite($hno, $userno)
	{
		$stage = Favorite::where('hno', '=', $hno)-> where('userno', '=', $userno) ->first();
		if( empty($stage) ){			
			// create user
			$stage = new Favorite();
			$stage->userno = $userno;	
			$stage->hno = $hno;		
			$stage->favorited = 1;			
			$stage->modifydate = time();
			$stage->save();	
			History::updateFavoNum($hno);
		}else{
			if($stage->favorited == 1){
				$stage->favorited = 0;	
				$stage->save();
				History::deleteFavoNum($hno);
			}else {
				$stage->favorited = 1;	
				$stage->save();
				History::updateFavoNum($hno);
			}
		}
			
		
		return $stage->favorited;
	}	
	public static function getOneFavorite($userno, $hno)
	{
		//limit(30)->offset(30)->get();
		//old take(30)->skip(30)->get();
		//$stage = TempStage::where('hno', '=', $hno) -> where('userno', '=', $userno) ->first();
		// -> offset($pagenum)->limit($limit)
		
		$stage = Favorite::where('userno', '=', $userno) ->where('hno', '=', $hno)->first();
		if( empty($stage) ){
			return 0;
		}else{
			return 1;
		}
		
	}	
	public static function getFavoriteCount($hno)
	{
		//limit(30)->offset(30)->get();
		//old take(30)->skip(30)->get();
		//$stage = TempStage::where('hno', '=', $hno) -> where('userno', '=', $userno) ->first();
		// -> offset($pagenum)->limit($limit)
		
		$favos = Favorite::where('hno', '=', $hno)->get();
		if( empty($favos) ){
			return 0;
		}else{
			$num = 0;
			foreach($favos as $favo){
				$num = $num + $favo -> favorited;
			}
			return $num;
		}
		
	}
}