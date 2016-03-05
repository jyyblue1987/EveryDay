<?php 

namespace App\Models;

use Illuminate\Database\Eloquent\Model;
use \DateTime;
use \DateInterval;
use Illuminate\Pagination;

class Advertisement extends Model { 
	protected $table = 'advertisement';
	
	public $timestamps = false;
}
