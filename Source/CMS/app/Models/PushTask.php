<?php 

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use \DateTime;
use \DateInterval;
use App\Models\OfflineMessage;

class PushTask extends Model { 
	protected $table = 'hc_schedule';
	
	public $timestamps = false;
	
	public function user()
    {
        return $this->belongsTo('App\Models\OfflineMessage', 'lastid', 'messageID');
    }
}