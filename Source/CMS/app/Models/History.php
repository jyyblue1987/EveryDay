<?php 

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use \DateTime;
use \DateInterval;
use App\Models\OfflineMessage;


class History extends Model { 
	protected $table = 'history';
	public static $error = 0;
	
	protected $fillable = array('fullname', 'username', 'password', 'email', 'phone', 'thumbnail', 'birthday', 'address', 'supportnum', 'pointnum', 'commentnum', 'frinenum', 'token', 'modifydate');
	public $timestamps = false;
	
	public function comments()
    {
        return $this->hasMany('App\Models\OfflineMessage', 'username', 'username');
    }
	
	public static function addHistory($userno)
	{
		
		// create user
		$history = new History();
		$history->userno = $userno;			
		$history->modifydate = time();		
			
		$id = $history->save();
		return $id;
	}
	public static function getRecentHistory($userno, $pagenum, $limited)
	{
		
		// get History
		$histories = History::orderBy('modifydate','DESC')-> offset($pagenum)->limit($limit)->get();
		$datahistories = array();
		foreach($histories as $history){
			$datahistories[]['id'] = $history->id;
			$datahistories[]['huserno'] = $history->userno;
			$datahistories[]['favonum'] = $history->favonum;
			$datahistories[]['commentnum'] = $history->commentnum;
			$datahistories[]['modifydate'] = $history->modifydate;
			$stage= Stage::getStage($history->id);
			$datahistories[]['recentthumb'] = $stage->thumbnail;
			$datahistories[]['recentcontent'] = $stage->content;
			$user = User::where('no', '=', $history->userno)->first();
			$datahistories[]['userfullname'] = $user->fullname;
			$datahistories[]['username'] = $user->username;
			$datahistories[]['useremail'] = $user->email;
			$datahistories[]['userthumb'] = $user->thumbnail;
			$datahistories[]['useraddr'] = $user->address;
			$datahistories[]['userreceivenum'] = $user->receivenum;
			$datahistories[]['usersendnum'] = $user->sendnum;
			$datahistories[]['userpointnum'] = $user->pointnum;
			$friend = Contacts::checkContact($userno, $history->userno);
			$datahistories[]['checkfriend'] = $friend;
		}
		return $datahistories;
	}
	public static function getHistorybyUser($userno, $huserno, $pagenum, $limited)
	{
		
		// get History
		$histories = History::where('userno', '=', $huserno) -> orderBy('modifydate','DESC')-> offset($pagenum)->limit($limit)->get();
		$datahistories = array();
		foreach($histories as $history){
			$datahistories[]['id'] = $history->id;
			$datahistories[]['huserno'] = $history->userno;
			$datahistories[]['favonum'] = $history->favonum;
			$datahistories[]['commentnum'] = $history->commentnum;
			$datahistories[]['modifydate'] = $history->modifydate;
			$stage= Stage::getStage($history->id);
			if(empty($stage) )	// user does not exist
			{
				$datahistories[]['recentthumb'] = "";
				$datahistories[]['recentcontent'] = "";
			}else{
				$datahistories[]['recentthumb'] = $stage->thumbnail;
				$datahistories[]['recentcontent'] = $stage->content;
				
			}
			$user = User::where('no', '=', $history->userno)->first();
			$datahistories[]['userfullname'] = $user->fullname;
			$datahistories[]['username'] = $user->username;
			$datahistories[]['useremail'] = $user->email;
			$datahistories[]['userthumb'] = $user->thumbnail;
			$datahistories[]['useraddr'] = $user->address;
			$datahistories[]['userreceivenum'] = $user->receivenum;
			$datahistories[]['usersendnum'] = $user->sendnum;
			$datahistories[]['userpointnum'] = $user->pointnum;
			$friend = Contacts::checkContact($userno, $history->userno);
			$datahistories[]['checkfriend'] = $friend;
		}
		return $datahistories;
	}
	public static function getOwnHistory($userno, $pagenum, $limited)
	{
		
		// get History
		$datahistories = array();
			$datahistories[]['id'] = 0;
			$datahistories[]['favonum'] = 0;
			$datahistories[]['commentnum'] = 0;
			$datahistories[]['modifydate'] = 0;
			$stage= TempStage::getStage($userno);
			if(empty($stage) )	// user does not exist
			{
				$datahistories[]['recentthumb'] = "";
				$datahistories[]['recentcontent'] = "";
			}else{
				$datahistories[]['recentthumb'] = $stage->thumbnail;
				$datahistories[]['recentcontent'] = $stage->content;
			}
		$histories = History::where('userno', '=', $userno) -> orderBy('modifydate','DESC')-> offset($pagenum)->limit($limit)->get();
		foreach($histories as $history){
			$datahistories[]['id'] = $history->id;
			$datahistories[]['favonum'] = $history->favonum;
			$datahistories[]['commentnum'] = $history->commentnum;
			$datahistories[]['modifydate'] = $history->modifydate;
			$stage= Stage::getStage($history->id);
			if(empty($stage) )	// user does not exist
			{
				$datahistories[]['recentthumb'] = "";
				$datahistories[]['recentcontent'] = "";
			}else{
				$datahistories[]['recentthumb'] = $stage->thumbnail;
				$datahistories[]['recentcontent'] = $stage->content;
			}
		}
		return $datahistories;
	}
	public static function getHighUsers($userno, $pagenum, $limited)
	{
		
		// get History
		$users = User::orderBy('sortscore','DESC')-> offset($pagenum)->limit($limit)->get();
		$datahistories = array();
		foreach($users as $user){
			$datahistories[]['userno'] = $user->id;
			$datahistories[]['userfullname'] = $user->fullname;
			$datahistories[]['username'] = $user->username;
			$datahistories[]['useremail'] = $user->email;
			$datahistories[]['userthumb'] = $user->thumbnail;
			$datahistories[]['useraddr'] = $user->address;
			$datahistories[]['userreceivenum'] = $user->receivenum;
			$datahistories[]['usersendnum'] = $user->sendnum;
			$datahistories[]['userpointnum'] = $user->pointnum;
			$friend = Contacts::checkContact($userno, $user->id);
			$datahistories[]['checkfriend'] = $friend;
			$history = History::where('userno', '=', $user->id) ->first();
			if(empty($history) )	// user does not exist
			{
				$datahistories[]['recentthumb'] = "";
				$datahistories[]['recentcontent'] = "";				
			}else{
				$stage= Stage::getStage($history->id);
				if(empty($stage) )	// user does not exist
				{
					$datahistories[]['recentthumb'] = "";
					$datahistories[]['recentcontent'] = "";
				}else{
					$datahistories[]['recentthumb'] = $stage->thumbnail;
					$datahistories[]['recentcontent'] = $stage->content;
				}
			}	
		}
		return $datahistories;
	}
	
	public static function updateFavoNum($hno)
	{
		$history = History::where('id', '=', $hno) ->first();
		
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
		
	public static function getHistories($hno, $userno)
	{
		$history = History::where('id', '=', $hno) -> where('userno', '=', $userno) ->first();
		
		if( empty($history) )
			return -1;
		$history -> favonum = $history -> favonum - 1;
		$history->delete();
		
		return 1;
	}
		
	public static function deleteFavoNum($hno)
	{
		$history = History::where('id', '=', $hno) ->first();
		
		if( empty($history) )
			return -1;
		$history -> favonum = $history -> favonum - 1;
		$history->delete();
		
		return 1;
	}
	public static function updateCommentNum($hno)
	{
		$history = History::where('id', '=', $hno) ->first();
		
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
	
		
	public static function deleteCommentNum($hno)
	{
		$history = History::where('id', '=', $hno) ->first();
		
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