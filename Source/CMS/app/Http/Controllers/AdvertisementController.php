<?php
namespace App\Http\Controllers;


use App\User;
use Illuminate\Http\Request;
use App\Models\Advertisement;
use App\Models\UserProfile;

use App\Http\Requests;
use App\Http\Controllers\Controller;


		
class AdvertisementController extends Controller
{
	function login()
	{
		
		return view('login'); //
	}
	
	function logout()
	{
		$_SESSION["login"] = '0';
		$_SESSION["email"] = '';
		unset($_SESSION['login']); 
			
		return redirect()->intended('login');
	}
	
	public function postLogin(Request $request)
	{
 		/* $default_email = 'admin@gmail.com';
		if (User::where('email', '=', $default_email)->exists() === false) {
			$user = new User();
			$user->email = $default_email;
			$user->password = Hash::make('hoyaadmin');
			$user->save();
		}  */
		$email = $request->input('email');
		$password = $request->input('password');
		if (Auth::attempt(['email' => $email, 'password' => $password])) {
			$_SESSION["email"] = $email;
			$_SESSION["login"] = '1';
			return redirect()->intended('/admin');
        }
		else
		{
			$_SESSION["login"] = '0';
			$_SESSION["email"] = '';
			return redirect()->intended('login');
		}	
	}
	
	function index(Request $request)
	{	
		
		if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return redirect()->intended('login');
		}	
		$item = $request->get('pageSize');
		
		if($item == null)
		{
			$item = 10;				
		}
		
		$users = Advertisement::paginate($item);	
		
									
		return view('users.advertisement')->with('users', $users)->with('item_v',$item);
	}

	function ad_register(Request $request)
	{	
		if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return redirect()->intended('login');
		}	

		$adver = new Advertisement();
									
		return view('users.ad_register')->with('adver',$adver)->with('error', '')->with('mode', 'create');
	}
	
	function addItem(Request $request)
	{
		if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return redirect()->intended('login');
		}	
		
		$data = $request->get('region_data');
		
		$adver = new Advertisement();
		
		$adver->title = $data['title'];
		$adver->thumbpath = $data['thumb_url'];
		$adver->start = $data['start'];
		$adver->end = $data['end'];
		$adver->sequence = $data['sequence'];
		
		$adver->save();
		
		$adver = new Advertisement(); 
		return view('users.ad_register')->with('adver',$adver)->with('error', 'SUCCESS')->with('mode', 'create');
	}
	
    function ad_edit(Request $request, $id)
	{	
		if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return redirect()->intended('login');
		}	

		$page = $request->get('page');
		if( $page === null )
			$page = 1;
		
		$adver = Advertisement::find($id);
		if( $adver == null )
		{
			return redirect()->intended('admin/advertisement?page='.$page);
		}
									
		return view('users.ad_register')->with('adver',$adver)->with('error', '')->with('mode', 'edit');

	}		

    function updateItem(Request $request, $id)
    {	
		if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return redirect()->intended('login');
		}	
		
		$adver = Advertisement::find($id);
		if( $adver == null )
		{
			return redirect()->intended('admin/advertisement');
		}
		
		$data = $request->get('region_data');
		
		$adver->title = $data['title'];
		$adver->thumbpath = $data['thumb_url'];
		$adver->start = $data['start'];
		
		$adver->end = $data['end'];
		$adver->sequence = $data['sequence'];
		
		
		$adver->save();
		
		return view('users.ad_register')->with('adver',$adver)->with('error', 'SUCCESS')->with('mode', 'edit');
	}		

    function ad_delete(Request $request, $id)
	{		
		if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return redirect()->intended('login');
		}	
		
		$page = $request->get('page');
		if( $page === null )
			$page = 1;

		$my_delete = Advertisement::find($id);

		if( $my_delete != null )
			$my_delete->delete();

		
		return redirect()->intended('admin/advertisement?page='.$page);		
	}			
	
	function ad_totaldelete(Request $request)
	{		
		if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return redirect()->intended('login');
		}	
		
		$deletions = $_POST['cat_ids'];
		Advertisement::destroy($deletions);
		
		return redirect()->intended('admin/advertisement');		
	}
	
}
