package com.zyh.booksearch.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.zyh.booksearch.R;
import com.zyh.booksearch.entity.BookInfo;
import com.zyh.booksearch.entity.NetResponse;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class BookUtil {
	public static final String TAG = "BookSearcher";

	public static NetResponse download(String url) {
		Log.v(TAG, "下载URL："+url);
		
		NetResponse netResponse = downloadFromDouban(url);
		JSONObject object = null;
		try {
			object = new JSONObject(String.valueOf(netResponse.getMessage()));
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		switch (netResponse.getCode()) {
		case BookAPI.RESPONSE_CODE_SUCCEED:
			netResponse.setMessage(parseBookInfo(object));
			break;
		default:
			// 异常数据，返回错误原因
			int errorCode = parseErrorCode(object); 
			netResponse.setCode(errorCode);
			netResponse.setMessage(getErrorMessage(errorCode));
			break;
		}
		
		return null;
	}
	
	private static BookInfo parseBookInfo(JSONObject json) {
		BookInfo bookInfo = null;
		if(json!=null) {
			bookInfo = new BookInfo();
			try {
				bookInfo.setTitle(json.getString(BookAPI.TAG_TITLE));
				bookInfo.setCover(downloadCover(json.getString(BookAPI.TAG_COVER)));
				bookInfo.setAuthor(parseJSONArray2String(json.getJSONArray(BookAPI.TAG_AUTHOR), " "));
				bookInfo.setPublisher(json.getString(BookAPI.TAG_PUBLISHER));
				bookInfo.setPublishDate(json.getString(BookAPI.TAG_PUBLISH_DATE));
				bookInfo.setISBN(json.getString(BookAPI.TAG_ISBN));
				bookInfo.setSummary(json.getString(BookAPI.TAG_SUMMARY).replace("\n", "\n\n"));
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		return bookInfo;
	}
	
	/**
	 * 从豆瓣返回的错误消息中解析出错误码
	 */
	private static int parseErrorCode(JSONObject json) {
		int ret = BookAPI.RESPONSE_CODE_ERROR_NET_EXCEPTION;
		
		if (json == null) {
			return ret;
		}
		
		try {
			ret = json.getInt(BookAPI.TAG_ERROR_CODE);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	/**
	 * 根据错误码找到对应错误详情字符串的编号
	 */
	private static int getErrorMessage(int errorCode) {
		int ret = R.string.error_message_default;
		
		switch (errorCode) {
		case BookAPI.RESPONSE_CODE_ERROR_NET_EXCEPTION:
			ret = R.string.error_message_net_exception;
			break;
			
		case BookAPI.RESPONSE_CODE_ERROR_BOOK_NOT_FOUND:
			ret = R.string.error_message_book_not_found;
			break;

		default:
			break;
		}
		
		return ret;
	}
	
	private static String parseJSONArray2String(JSONArray jsonArray, String split) {
		if ((jsonArray == null) || (jsonArray.length() < 1)) {
			return null;
		}
		
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < jsonArray.length(); i++) {
			try {
				sb = sb.append(jsonArray.getString(i));
			} catch (JSONException e) {
				e.printStackTrace();
			}
			sb = sb.append(split);
		}
		sb.deleteCharAt(sb.length() - 1);
		
		Log.v(TAG, "parseJSONArray2String(" + jsonArray.toString() + "): " + sb.toString());
		return sb.toString();
	}

	private static Bitmap downloadCover(String cover) {
		Bitmap bitmap = null;
		HttpURLConnection conn = null;
		InputStream is = null;
        BufferedInputStream bis = null;
        
        try {
			conn = (HttpURLConnection) (new URL(cover)).openConnection();
			is = conn.getInputStream();
			bis = new BufferedInputStream(is);
			bitmap = BitmapFactory.decodeStream(bis);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
            try {
                if (bis != null) {
                    bis.close();
                }
                
                if (is != null) {
                    is.close();
                }
            } catch (IOException e) {
            	e.printStackTrace();
            }
        }
        return bitmap;		
	}
	
	private static NetResponse downloadFromDouban(String url) {
		NetResponse netResponse = new NetResponse(BookAPI.RESPONSE_CODE_ERROR_NET_EXCEPTION, null);
		
		BufferedReader reader = null;
		HttpClient client = null;
		HttpResponse response = null;
		
		BasicHttpParams params = new BasicHttpParams();
		SchemeRegistry registry = new SchemeRegistry();
		registry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));
		registry.register(new Scheme("https", SSLSocketFactory.getSocketFactory(), 443));
		
		ThreadSafeClientConnManager manage = new ThreadSafeClientConnManager(params, registry);
		
		try {
			client = new DefaultHttpClient(manage, params);
			response = client.execute(new HttpGet(url));
			netResponse.setCode(response.getStatusLine().getStatusCode());
			
			StringBuffer sb = new StringBuffer();
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			String result;
			while((result = reader.readLine())!=null) {
				sb.append(result);
			}
			
			Log.v(TAG, sb.toString());
			netResponse.setMessage(sb.toString());
			
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(client!=null) {
				client.getConnectionManager().shutdown();
			}
			if(reader!=null) {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					reader = null;
				}
			}
		}
		
		return netResponse;
		
	}
}
