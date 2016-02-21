package com.sin.quian;

import java.util.List;
import java.util.Map;

import android.graphics.Color;

public class Const {
	public static boolean		ANALYTICS = false;
	public static final int APP_BACK_COLOR = Color.rgb(4, 153, 168);
	
	public static final int 	DEFAULT_SCREEN_X = 313;
	public static final int 	DEFAULT_SCREEN_Y = 424;

	public static final String		LOGIN_OK_KEY	= "login_ok";	
	public static final	int			NOT_LOGIN 	= 1;
	public static final	int			NOT_VERIFY 		= 2;
	public static final	int			LOGIN_SUCESS	= 3;
	
	public static final String		COUNTRY_CODE_KEY	= "country_code";
	public static final String		COUNTRY_ID_KEY		= "country_id_key";
	public static final String		PHONR_NUMBER_KEY	= "phone_number_key";
	public static final String		PASSWORD_KEY		= "password_key";
	public static final String		VERIFY_CODE_KEY = "vCode";
	
	public static final String		NEW_LOGIN_PAGE_FLAG	= "new_login_page_flag";
	
	public static final String		CONTACT_ID			= "contact_id";
	public static final String		SELF_FLAG			= "self_flag";
	
	public static final String		PREV_USER_ID		= "prev_userid";
	
	
	public static final	int		CALL_OUT_OK = 0;
	public static final	int		CALL_OUT_FAIL = 1;
	public static final	int		CALL_IN_OK = 2;
	public static final	int		CALL_IN_MISSED = 3;

	// Log Tag
	public static final String SMACK = "contact2w_smack";
	
	// Broadcast message
	public static final String RECEIVE_MESSAGE_ACTION = "receive_message";
	public static final String EXTRA_MESSAGE = "extra_message";
	public static final String ROSTER_CHANGE_ACTION = "roster_change";
	public static final String NOTIFICATON_RECEIVE_ACTION = "notification_receive";
	public static final String UPDATE_MESSAGE_ACTION = "update_message";
	
	public static final String ROSTER_INFO_CHANGE_ACTION = "roster_info_change";
	
	// Connect State
	public static final int NOT_CONNECTED = 0;
	public static final int CONNECTING = 1;
	public static final int CONNECTED = 2;
	public static final int AUTHENTICATING = 3;
	public static final int AUTHENTICATED = 4;
	
	// preference key
	public static final String GCM_PUSH_KEY = "gcm_push_key";	
	public static final String COUNTRY_CODE = "country_code";
	public static final String MOBILE = "mobile";
	public static final String VCODE = "vcode";
	
	public static final String LOGIN_OK = "login_ok";
	
	
	// Table field name
	
	// Contact table
	public static final String FULLNAME = "fullname";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";
	public static final String NICKNAME = "nickname";
	public static final String EMAIL = "email";
	public static final String FAVORITE = "favorite";
	public static final String PHONENUMBER= "phonenumber";
	public static final String BIRTHDAY = "birthday";
	public static final String ROLE = "role";
	public static final String DOMAIN = "domain";
	public static final String CREATOR = "creator";
	public static final String COMPANY = "company";
	public static final String ADDRESS = "address";
	public static final String POSTAL_CODE = "postal";
	public static final String AVASTAR = "avastar";
	public static final String REALNAME = "name";
	
	// Chat history table
	public static final String ID = "id";
	public static final String FROM = "from_id";
	public static final String TO = "to_id";
	public static final String BODY = "body";
	public static final String TYPE = "type";
	public static final String UNREAD = "unread";
	public static final String SENT = "sent";
	public static final String DIRECTION = "direction";
	public static final String GROUP_TYPE = "group_type";
	public static final String DATE = "created";
	public static final String DISP_DATE = "displayed";
	public static final String SENDER = "sender"; // only valid in group chatting
	public static final String SMS_CHAT = "sms_chat";
	
	// Group Table
	public static final String CONTACT_LIST = "contact_list";
	public static final String CONTACT_COUNT = "contact_count";
	public static final String GROUP_NAME = "group_name";
	
	// Group Table
	public static final String NOTIFICATION = "message";
	
	// Group
	public static final String GROUP_EDIT_MODE = "group_edit_mode";
	public static final String GROUP_GROUP_NAME = "group_edit_name";

    // MSG
    public static final int RECORD_START = 100;
    public static final int RECORD_STOP = 101;
    
    // Record Path
    public static final String RECORD_PATH = "record_path";
    
    public static final String UPLOAD_PATH = "filename";
    public static final String FILE_PATH = "filepath";
    
    // stage list
    public static final String THUMBNAIL = "thumbnail";
    public static final String CONTENT = "content";
    public static final String MODIFY_DATE = "modifydate";
    
    // history list
    public static final String COMMENT_COUNT = "commentnum";
    public static final String LIKE_COUNT = "favonum";
    public static final String FAVORITED_FLAG = "favorited";
    
    // User List
    public static final String USER_THUMBNAIL = "userthumb";
    public static final String USER_RECEIVE_NUM = "userreceivenum";
    public static final String USER_POINT_NUM = "userpointnum";
    public static final String USER_ADDRESS = "useraddr";
    
    public static final String USER_ = "userthumb";
    
    public static final int		TEMP_STAGE_MODE = 0;
    public static final int		SELF_STAGE_MODE = 1;
    public static final int		OTHER_STAGE_MODE = 2;
    
    // user info
    public static final String MY_RECEIVE_NUM = "receivenum";
    public static final String MY_POINT_NUM = "pointnum";
    
    public static final String MODE = "mode";
}
