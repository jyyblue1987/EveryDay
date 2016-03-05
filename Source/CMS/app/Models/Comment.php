<?php 

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use \DateTime;
use \DateInterval;
use App\Models\OfflineMessage;


class Comment extends Model { 
	protected $table = 'comment';
	//protected $table = 'advertisement';
	public static $error = 0;
	
	protected $fillable = array('fullname', 'username', 'password', 'email', 'phone', 'thumbnail', 'birthday', 'address', 'supportnum', 'pointnum', 'commentnum', 'frinenum', 'token', 'modifydate');
	public $timestamps = false;
	
	/* public function comments()
    {
        return $this->hasMany('App\Models\OfflineMessage', 'username', 'username');
    } */
	
	public static function addComment($hno, $userno, $content)
	{
		
		// create user
		$stage = new Comment();
		$stage->userno = $userno;	
		$stage->hno = $hno;	
		$stage->content = $content;			
		$stage->modifydate = time();		
			
		$id = $stage->save();
		History::updateCommentNum($hno);
		return $id;
	}	
	public static function deleteComment($cno, $userno)
	{
		$stage = Comment::where('id', '=', $cno)-> where('userno', '=', $userno) ->first();
		
		if( empty($stage) )
			return -1;
		$stage->delete();
		History::deleteCommentNum($hno);
		
		return 1;
	}
	public static function getComments($userno, $hno, $pagenum, $limit)
	{
		//limit(30)->offset(30)->get();
		//old take(30)->skip(30)->get();
		//$stage = TempStage::where('hno', '=', $hno) -> where('userno', '=', $userno) ->first();
		
		$stage = Comment::where('userno', '=', $userno) ->where('hno', '=', $hno) -> offset($pagenum)->limit($limit)->get();
		
		
		return $stage;
	}
}