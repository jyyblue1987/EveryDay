package com.sin.quian.network;import java.util.HashMap;import java.util.List;import common.library.utils.AndroidUtils;import common.library.utils.DataUtils;import common.network.utils.ResultCallBack;public class ServerManager {		public final static String COMMAND_GET_COUNTRYLIST_ACTION = "getcountrylist";	private static void sendRqeust(HashMap<String, String> params, String command,			ResultCallBack callBack) {		ServerTask task = new ServerTask(command, callBack, params);				if (AndroidUtils.getAPILevel() > 12) {			//task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, "");			task.execute("");		} else {			task.execute("");		}	}		private static void sendRqeust(List<HashMap<String, String>> paramsList, List<String> commandList,			ResultCallBack callBack) {		ServerTask task = new ServerTask(commandList, callBack, paramsList);				if (AndroidUtils.getAPILevel() > 12) {			//task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, "");			task.execute("");		} else {			task.execute("");		}	}		private static void sendRqeustToOtherService(String url, HashMap<String, String> params, ResultCallBack callBack) {		WebServiceTask task = new WebServiceTask(url, callBack, params);		task.execute("");			}		public static void getCountryList(ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();				map.put(ServerTask.OFFLINE_REQUEST_KEY, "1");				sendRqeust(map, "getcountrylist", callback);	}		public static void register(String username, String email, String password, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();		map.put("username", username);		map.put("email", email);		map.put("pwd", password);				sendRqeust(map, "register", callback);	}		public static void verify(String country_code, String mobile, String vcode, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();				map.put("country_code", country_code);		map.put("mobile", mobile);		map.put("vcode", vcode);				sendRqeust(map, "verify", callback);	}		public static void login(String username, String password, String pushkey, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();				map.put("username", username);		map.put("pwd", password);		map.put("device", "1"); 		map.put("pushkey", pushkey);				sendRqeust(map, "login", callback);	}	public static void getCategory(ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();//		map.put(ServerTask.OFFLINE_REQUEST_KEY, "1");				sendRqeust(map, "PCATEGORY", callback);	}		public static void getSubCategory(int parentID, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();		map.put("parent", parentID + "");				sendRqeust(map, "SUBCATEGORY", callback);	}		public static void getRSSFeed(String url, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();				sendRqeustToOtherService(url, map, callback);	}		public static void getEventList(ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();//		map.put(ServerTask.OFFLINE_REQUEST_KEY, "1");				sendRqeust(map, "EVENT", callback);	}	public static  void uploadPhoto(String path, String username, ResultCallBack callBack) {		HashMap<String, String> map = new HashMap<String, String>();				map.put("username", username);				// add security key		map.put(ServerTask.SECURITY_KEY, DataUtils.getPreference(ServerTask.SECURITY_KEY, ""));				UpLoadFileTask task = new UpLoadFileTask(ServerTask.SERVER_URL + "uploadphoto", map, null, callBack);		task.execute(path);	}}