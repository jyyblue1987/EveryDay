<?php 

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use \DateTime;
use \DateInterval;
use Illuminate\Pagination;

class AdminInfo extends Model { 
	protected $table = 'siteuser';
	
	public $timestamps = false;
}
