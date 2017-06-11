package com.example.administrator.surprisegiftserver.config;

/**
 * Created by Administrator on 17/5/2017.
 */

public class Config {
    public static String HOST="http://192.168.1.198/";
    public static String IN="NotificationBirthday/Login.php";
    public static String UP="NotificationBirthday/SignUp.php";
    public static String EVENT="NotificationBirthday/Event.php";
    public static String CLIENTS="NotificationBirthday/Clients.php";
    public static String GET_EVENT="NotificationBirthday/Events.php";
    public static String _CLIENT="NotificationBirthday/testClient.php";
    public static String EVENT_CLIENT="NotificationBirthday/pushEventClient.php";
    public static String LOG_IN =HOST+IN;
    public static String SIGN_UP=HOST+UP;
    public static String PUSH_EVENT=HOST+EVENT;
    public static String GET_ALL_EVENT=HOST+GET_EVENT;
    public static String PREF="pref";
    public static String DATA_BASE_NAME="gift_db";
    public static int VER=1;
    public static String GET_CLIENTS=HOST+CLIENTS;
    public static String TEST_CLIENT=HOST+_CLIENT;
    public static String PUSH_EVENT_CLIENT=HOST+EVENT_CLIENT;

}
