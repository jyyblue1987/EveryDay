<?php

/*
|--------------------------------------------------------------------------
| Routes File
|--------------------------------------------------------------------------
|
| Here is where you will register all of the routes in an application.
| It's a breeze. Simply tell Laravel the URIs it should respond to
| and give it the controller to call when that URI is requested.
|
*/

session_start();
Route::get('/admin', function () {
    return Redirect::to('/login');
});

/*
|--------------------------------------------------------------------------
| Application Routes
|--------------------------------------------------------------------------
|
| This route group applies the "web" middleware group to every route
| it contains. The "web" middleware group is defined in your HTTP
| kernel and includes session state, CSRF protection, and more.
|
*/

Route::group(['middleware' => ['web']], function () {
    //
});

Route::get('/', function()
{
	
	return view('users.advertisement'); // laravel 5 return View('pages.home');
});




//admin panel
Route::any('/ad_totaldelete', 'AdvertisementController@ad_totaldelete'); 
Route::any('/ad_edit/{id?}', 'AdvertisementController@ad_edit'); 
Route::any('/ad_delete/{id?}', 'AdvertisementController@ad_delete'); 
Route::post('/admin/ad_item', 'AdvertisementController@addItem');
Route::post('/admin/update_ad_item/{id?}', 'AdvertisementController@updateItem');
Route::get('/ad_register', 'AdvertisementController@ad_register');

Route::get('/', 'AdvertisementController@index');

Route::get('/login','UserController@login');
Route::post('/admin/postLogin','UserController@postLogin');
Route::get('/logout', 'UserController@logout');

Route::get('/admin/index', array('uses'=>'UserController@index'));
Route::get('/admin/ad_user', 'UserController@ad_user');
Route::any('/user_edit/{id?}', 'UserController@user_edit');
Route::any('/user_delete/{id?}', 'UserController@user_delete');
Route::any('/admin/totaldelete', 'UserController@user_totaldelete');
Route::post('/admin/user_item', 'UserController@user_item');
Route::post('/admin/update_user_item/{id?}', 'UserController@updateItem');
Route::get('/admin/users', function(){
	return Redirect::to('/admin/index');
});
Route::post('/admin/search', 'UserController@search'); 
Route::post('/admin/update', 'UserController@update'); 
Route::get('/admin/search', 'UserController@search'); 
Route::any('/admin/process/{id?}', 'ProcessController@process'); 
Route::any('/process/{id?}', 'ProcessController@process'); 
Route::any('/email', 'UserController@email');
Route::get('/admin/userprofile', 'UserController@userprofile');

