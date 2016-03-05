<?php 

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use \DateTime;
use \DateInterval;
use App\Models\User;

class OfflineMessage extends Model { 
	protected $table = 'ofoffline';
	
	public $timestamps = false;
	
	public function user()
    {
        return $this->belongsTo('App\Models\User', 'username', 'username');
    }
}