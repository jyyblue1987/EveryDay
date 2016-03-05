<?php

namespace App\Http\Controllers;

use Auth;
use Hash;
use App\User;
use Illuminate\Http\Request;
use App\Models\OfUser;
use App\Models\UserProfile;
use App\Models\AdminInfo;
use App\Http\Requests;
use App\Http\Controllers\Controller;
use Illuminate\Pagination\Paginator;
use Illuminate\Pagination\LengthAwarePaginator;
use \SimpleXMLElement;

use App\Modules\Functions;
//use Illuminate\Pagination;


function convertMobileNumber($number)
{
	return "+" . str_replace("_", " ", $number);
}		
class UserController extends Controller
{
	function login()
	{
		
		 if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return view('login');
		}else{	
		//return redirect()->intended('/admin/advertisement'); // 
			return redirect()->intended('/admin/index');
		}
		return view('login');
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
		
 		$default_email = 'admin@gmail.com';
/*		
		if (AdminInfo::where('email', '=', $default_email)->exists() === false) {
			$user = new AdminInfo();
			$user->email = $default_email;
			$user->password = Hash::make('every');
			$user->save();
		} 
*/		
		
		//echo $request->input('email');
		$email = $request->input('email');
		$password = $request->input('password');
		if (Auth::attempt(['email' => $email, 'password' => $password])) {
			$_SESSION["email"] = $email;
			$_SESSION["login"] = '1';
			return redirect()->intended('/admin/index');
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
		$option = $request->get('select_option');
		echo $option;
		
		if($item == null)
		{
			$item = 10;				
		}
		if($option == null){
			$option = 'Email';	
		}
		
		$users = OfUser::paginate($item);
		
		
		foreach( $users as &$value )	
		{	
			
			$value['phone'] = convertMobileNumber($value['phone']);	
			if( $value['email'] === 'false' )
				$value['email'] = '';
			if( $value['name'] === 'false' )
				$value['name'] = '';
			if( $value['address'] === 'false' )
				$value['address'] = '';
			if( $value['fullname'] === null )
				$value['fullname'] = '';
			if( $value['supportnum'] === null )
				$value['supportnum'] = 0;
			if( $value['pointnum'] === null )
				$value['pointnum'] = 0;
			if( $value['commentnum'] === null )
				$value['commentnum'] = 0;
			if( $value['friendnum'] === null )
				$value['friendnum'] = 0;
		} 
		
									
		return view('users.user')->with('users', $users)->with('item_v',$item)->with('option',$option);
	}
	
	function search(Request $request)
	{	
		if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return redirect()->intended('login');
		}
		$item = 10;	
		$input = $request->get('find');
		$option = $request->get('select_option');
		$orderby = $request->get('orderby_option');
		echo $orderby;
		switch($option){
			case 1:
				$option_id = "name";
				$user = OfUser::where('name', 'like', '%' . $input . '%')->paginate($item);
				
				break;
			case 0:
				$option_id = "Phone";
				$user = OfUser::where('phone', 'like', '%' . $input . '%')->paginate($item);
				break;
			case 2:
				$option_id = "Email";
				$user = OfUser::where('email', 'like', '%' . $input . '%')->paginate($item);
				break;
		}
		echo $user;				
		
		
		//$user = OfUser::where('%'.$option_id.'%', 'like', '%' . $input . '%')->paginate($item);
		foreach( $user as &$value )	
		{
			$value['mobile'] = convertMobileNumber($value['username']);	
			if( $value['email'] === 'false' )
				$value['email'] = '';
			if( $value['name'] === 'false' )
				$value['name'] = '';
		}
		
		return view('users.user')->with('users', $user)->with('item_v',$item);
	}
	
	function process($id = AUTH)	
	{
		if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return redirect()->intended('login');
		}
		//echo $id;
		return view('users.process')->with('action', $id);
	}
	function email(Request $request)
	{
		if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return redirect()->intended('login');
		}
		
		$error = '';
		
		if( $request->isMethod('post') )
		{
			$admin = User::where('email', '=', $_SESSION['email'])->first();
			if( empty($admin) )
				return redirect()->intended('login');
			
			if( $request->get('password1') !== $request->get('password2') )
				$error = Functions::GetMessage ('ACCOUNT_PASS_MISMATCH');
			else if( strlen($request->get('password1')) < 6 || strlen($request->get('password1')) > 32 )
				$error = Functions::GetMessage('ACCOUNT_PASS_CHAR_LIMIT', array(6, 32));
			else
			{
				$admin->email = $request->get('email');
				$admin->name = $request->get('username');
				$admin->password = Hash::make($request->get('password1'));
				$admin->save();
				
				$_SESSION['email'] = $admin->email;	
				$error = 'SUCCESS';
			}
		}
		else
		{
			$email = $_SESSION["email"];
			$admin = User::where('email', '=', $email)->first();
		}
		return view('users.email')->with('admin', $admin)->with('error',$error);		
	}
	
	function update(Request $request)
	{
		if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return redirect()->intended('login');
		}
		if( !isset($_SESSION['email']) ) 
		{
			return redirect()->intended('login');
		}
		
		
		
		return redirect()->intended('/');	
	}
	
	function userprofile(Request $request)
	{
		if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return redirect()->intended('login');
		}
		
		$username = $request->get('pname');
		$userinfo = OfUser::where('id', '=', $username)->first();
		//echo $userinfo;
		if( empty($userinfo) )
		{
			$profile = array();	
			$profile['mobile'] = convertMobileNumber($username);
			return view('users.exceptuserprofile')->with('profile',$profile);
		}
		
		
		/*$xml_parser = xml_parser_create() or die ("XML 파서를 생성하지 못했습니다.");
		xml_parse_into_struct($xml_parser, $userinfo->vcard, $value, $index);  
		xml_parser_free($xml_parser);  
		
		$middle = '';
		$family = '';
		foreach($value as $v){  
			if( $v['tag'] === 'MIDDLE' )
			{
				$middle = $v['value'];
			}
			if( $v['tag'] === 'FAMILY' )
			{
				$family = $v['value'];
			}
		}
	
		$profile = json_decode($middle, true);	
		$profile['mobile'] = convertMobileNumber($username);
		if( $profile['role'] === '0' )
			$profile['role'] = 'Patient';
		else
			$profile['role'] = 'Doctor';*/
	
		return view('users.userprofile')->with('profile',$userinfo);
	}
	function ad_user(Request $request)
	{	
		
		if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return redirect()->intended('login');
		}	

		$adver = new OfUser();
		
		return view('users.user_register')->with('adver',$adver)->with('error', '')->with('mode', 'create');
	}
	
	function user_item(Request $request)
	{	
		
		if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return redirect()->intended('login');
		}	
		
		$data = $request->get('region_data');
		
		$adver = new OfUser();
		
		$adver->phone = $data['phone'];
		$adver->name = $data['name'];
		$adver->email = $data['email'];
		$adver->adress = $data['adress'];
		//$adver->sequence = $data['sequence'];
		
		$adver->save();
		
		$adver = new OfUser(); 
		return view('users.user_register')->with('adver',$adver)->with('error', 'SUCCESS')->with('mode', 'create');
	}
	
	 function user_edit(Request $request, $phone)
	{	
		
		if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return redirect()->intended('login');
		}	

		$page = $request->get('page');
		if( $page === null )
			$page = 1;
		
		$adver = OfUser::where('phone', '=', $phone)->first();
		
		if( $adver == null )
		{
			return redirect()->intended('/admin/index?page='.$page);
		}
									
		return view('users.user_register')->with('adver',$adver)->with('error', '')->with('mode', 'edit');

	}

function updateItem(Request $request, $phone)
    {	
		
		if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return redirect()->intended('login');
		}	
		
		$adver = OfUser::where('phone', '=', $phone)->first();
		//echo $adver;
		if( $adver == null )
		{
			return redirect()->intended('/admin/index');
		}
		
		$data = $request->get('region_data');
		$adver = new OfUser();
		$adver->phone = $data['phone'];
		$adver->name = $data['name'];
		$adver->email = $data['email'];
		
		$adver->adress = $data['adress'];
		//$adver->sequence = $data['sequence'];
		
		
		$adver->save();
		
		return view('users.user_register')->with('adver',$adver)->with('error', 'SUCCESS')->with('mode', 'edit');
	}

function user_delete(Request $request, $phone)
	{	
		
		if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return redirect()->intended('login');
		}	
		
		$page = $request->get('page');
		if( $page === null )
			$page = 1;

		$my_delete = OfUser::where('phone', '=', $phone)->delete();
		//echo $my_delete;
		/*if( $my_delete != null )
			$my_delete->delete();*/

		
		return redirect()->intended('/admin/index?page='.$page);		
	}

function user_totaldelete(Request $request)
	{	
		
		if(empty($_SESSION["login"]) || $_SESSION["login"] !== '1' )
		{
			return redirect()->intended('login');
		}	
		$data = $request->get('cat_ids');
		$j = $data->length();
		echo $j;
		//echo $data[2];
		for($i = 0; $i <= $data.length-1; $i++ ){
			if($data[i] != null){
				OfUser::where('phone', '=', $data[i])->delete();
			}
			else
				return redirect()->intended('/admin/index');
		}
			
		
		
		//OfUser::where('phone', '=', $data[i])->delete();
			
		
		//$deletions = $_POST['cat_ids'];
		//echo $data[1];
		//OfUser::destroy($data[1]);
		
		return redirect()->intended('/admin/index');		
	}	
}
