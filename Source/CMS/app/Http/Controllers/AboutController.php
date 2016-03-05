<?php

namespace App\Http\Controllers;


use Illuminate\Http\Request;

use App\Http\Requests;
use App\Http\Controllers\Controller;
//use Request;



class AboutController extends Controller {
	
	public function aboutus()
	{
	
		return view('pages.aboutus');
	}
	
	public function message(Request $request)
	{

		$datas['email'] =  '2636970547@qq.com';
		$datas['firstname'] = 'Admin';
		//$target_user = User::where('role','admin')->first();
		//$datas['email']= $target_user->email;
		//$datas['firstname'] = $target_user->firstname;
		
		
		$data['email'] = $request->get('email');
		$data['content'] = $request->get('userMsg');
		$data['phoneno'] = $request->get('phoneno');
		$data['name'] = $request->get('name');

		echo $data['email'];
		
		Mail::send('pages.about', $data, function($message)
		{
			$message->from($data['email'], $data['name']);

			$message->to('2905368844@qq.com', 'John Smith')->subject($data['content']);
		});		
		
		
		return view('pages.aboutus');
	}
	
}

