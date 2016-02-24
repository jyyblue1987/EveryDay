package com.sin.quian.network;import java.util.HashMap;import java.util.List;import common.library.utils.AndroidUtils;import common.library.utils.DataUtils;import common.network.utils.ResultCallBack;public class ServerManager {		public final static String COMMAND_GET_COUNTRYLIST_ACTION = "getcountrylist";	private static void sendRqeust(HashMap<String, String> params, String command,			ResultCallBack callBack) {		ServerTask task = new ServerTask(command, callBack, params);				if (AndroidUtils.getAPILevel() > 12) {			//task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, "");			task.execute("");		} else {			task.execute("");		}	}		private static void sendRqeust(List<HashMap<String, String>> paramsList, List<String> commandList,			ResultCallBack callBack) {		ServerTask task = new ServerTask(commandList, callBack, paramsList);				if (AndroidUtils.getAPILevel() > 12) {			//task.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, "");			task.execute("");		} else {			task.execute("");		}	}		private static void sendRqeustToOtherService(String url, HashMap<String, String> params, ResultCallBack callBack) {		WebServiceTask task = new WebServiceTask(url, callBack, params);		task.execute("");			}		public static void getCountryList(ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();				map.put(ServerTask.OFFLINE_REQUEST_KEY, "1");				sendRqeust(map, "getcountrylist", callback);	}		public static void register(String username, String email, String password, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();		map.put("username", username);		map.put("email", email);		map.put("pwd", password);				sendRqeust(map, "register", callback);	}		public static void login(String username, String password, String pushkey, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();				map.put("username", username);		map.put("pwd", password);		map.put("device", "1"); 		map.put("pushkey", pushkey);				sendRqeust(map, "login", callback);	}		public static void loginWithFacebook(String id, String name, String email, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();				map.put("fullname", name);		map.put("pwd", id);		map.put("email", email); 				sendRqeust(map, "fbregister", callback);	}		public static void changePassword(String userid, String oldpwd, String newpwd, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();				map.put("userno", userid);		map.put("oldpwd", oldpwd);		map.put("newpwd", newpwd);				sendRqeust(map, "changepassword", callback);	}		public static void forgotPassword(String username, String email, String newpwd, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();				map.put("username", username);		map.put("email", email);		map.put("newpwd", newpwd);				sendRqeust(map, "forgotpassword", callback);	}		public static void updateProfile(String userno, String username, String fullname, String email, String phone, String birth, String addr, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();				map.put("userno", userno);		map.put("username", username);		map.put("fullname", fullname);		map.put("email", email);		map.put("phone", phone);		map.put("birth", birth);		map.put("addr", addr);				sendRqeust(map, "update", callback);	}		public static void addContact(String userno, String contactno, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();				map.put("userno", userno);		map.put("contactno", contactno);				sendRqeust(map, "addcontact", callback);	}		public static void deleteContact(String userno, String contactno, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();				map.put("userno", userno);		map.put("contactno", contactno);				sendRqeust(map, "delcontact", callback);	}		public static void getContactList(String userno, int pagenum, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();				map.put("userno", userno);		map.put("pagenum", pagenum + "");				sendRqeust(map, "getcontacts", callback);	}		public static void getTempStages(String userno, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();		map.put("userno", userno);						sendRqeust(map, "gettempstage", callback);			}		public static void deleteTempStages(String userno, String stageNo, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();		map.put("userno", userno);				map.put("sno", stageNo);								sendRqeust(map, "deletetempstage", callback);			}		public static void getStages(String userno, String hno, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();		map.put("userno", userno);		map.put("hno", hno);				sendRqeust(map, "getstage", callback);			}			public static void addHistory(String userno, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();		map.put("userno", userno);						sendRqeust(map, "addhistory", callback);			}		public static void getOwnHistory(String userno, int pageno, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();		map.put("userno", userno);		map.put("pagenum", pageno + "");						sendRqeust(map, "getownhistory", callback);			}		public static void getUserHistory(String userno, String huserno, int pageno, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();		map.put("userno", userno);		map.put("huserno", huserno);			map.put("pagenum", pageno + "");						sendRqeust(map, "gethistorybyuser", callback);			}		public static void deleteHistory(String userno, String hno, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();		map.put("userno", userno);			map.put("hno", hno);					sendRqeust(map, "addhistory", callback);			}		public static void getRecentHistory(String userno, int pageno, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();		map.put("userno", userno);				map.put("pagenum", pageno + "");						sendRqeust(map, "getrecenthistory", callback);			}		public static void getHighUserList(String userno, int pageno, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();		map.put("userno", userno);				map.put("pagenum", pageno + "");						sendRqeust(map, "gethighpointusers", callback);			}			public static void addComment(String userno, String hno, String content, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();		map.put("userno", userno);				map.put("hno", hno);				map.put("content", content);						sendRqeust(map, "addcomment", callback);			}		public static void deleteComment(String userno, String hno, String cno, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();		map.put("userno", userno);				map.put("hno", hno);		map.put("cno", cno);				sendRqeust(map, "deletecomment", callback);			}		public static void getCommentList(String userno, String hno, String pagenum, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();		map.put("userno", userno);				map.put("hno", hno);				map.put("pagenum", pagenum);						sendRqeust(map, "getcomment", callback);			}		public static void sendPoint(String userno, String huserno, String ammount, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();		map.put("userno", userno);				map.put("huserno", huserno);				map.put("amount", ammount);						sendRqeust(map, "sendpoint", callback);			}			public static void addPoint(String userno, String ammount, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();		map.put("userno", userno);				map.put("amount", ammount);						sendRqeust(map, "addpoint", callback);			}		public static void addLike(String userno, String hno, ResultCallBack callback)	{		HashMap<String, String> map = new HashMap<String, String>();		map.put("userno", userno);				map.put("hno", hno);						sendRqeust(map, "addfavorite", callback);			}				public static  void uploadPhoto(String userno, String path, ResultCallBack callBack) {		HashMap<String, String> map = new HashMap<String, String>();				map.put("userno", userno);				// add security key		map.put(ServerTask.SECURITY_KEY, DataUtils.getPreference(ServerTask.SECURITY_KEY, ""));				UpLoadFileTask task = new UpLoadFileTask(ServerTask.SERVER_URL + "userphoto", map, null, callBack);		task.execute(path);	}		public static  void uploadStage(String path, String username, String content, ResultCallBack callBack) {		HashMap<String, String> map = new HashMap<String, String>();				map.put("userno", username);		map.put("content", content);				// add security key		map.put(ServerTask.SECURITY_KEY, DataUtils.getPreference(ServerTask.SECURITY_KEY, ""));				UpLoadFileTask task = new UpLoadFileTask(ServerTask.SERVER_URL + "addtempstage", map, null, callBack);		task.execute(path);	}		public static  void uploadThumbnail(String path, String userno, ResultCallBack callBack) {		HashMap<String, String> map = new HashMap<String, String>();				map.put("userno", userno);				// add security key		map.put(ServerTask.SECURITY_KEY, DataUtils.getPreference(ServerTask.SECURITY_KEY, ""));				UpLoadFileTask task = new UpLoadFileTask(ServerTask.SERVER_URL + "stagethumb", map, null, callBack);		task.execute(path);	}}