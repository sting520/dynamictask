package com.power.taskcenter.util;

import java.security.MessageDigest;

/**
 * Created by IntelliJ IDEA.
 * User: 石铁军
 * Date: 2008-4-21
 * Time: 10:11:57
 * To change this template use File | Settings | File Templates.
 */
public class MD5Encode
{
    private static final String[] hexDigits
	= { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C",
	    "D", "E", "F" };

    public static String byteArrayToString(byte[] b) {
	StringBuffer resultSb = new StringBuffer();
	for (int i = 0; i < b.length; i++)
	    resultSb.append(byteToHexString(b[i]));
	return resultSb.toString();
    }

    private static String byteToNumString(byte b) {
	int _b = b;
	if (_b < 0)
	    _b = 256 + _b;
	return String.valueOf(_b);
    }

    private static String byteToHexString(byte b) {
	int n = b;
	if (n < 0)
	    n = 256 + n;
	int d1 = n / 16;
	int d2 = n % 16;
	return hexDigits[d1] + hexDigits[d2];
    }

    public static String MD5Encode(String origin) {
	String resultString = null;
	try {
	    resultString = new String(origin);
	    MessageDigest md = MessageDigest.getInstance("MD5");
	    resultString
		= byteArrayToString(md.digest(resultString.getBytes()));
	} catch (Exception ex) {
	    ex.printStackTrace();
	}
	return resultString;
    }
}
