package com.jk.utils;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;


/**
 * @Author lh
 * @Description
 * @Date 19:16 2021/1/13
 * @Param
 * @return
 **/
public class HttpClientUtil {
    static CloseableHttpClient client = null;
    static {
        //1、创建httpClient实例
        client = HttpClients.createDefault();
    }

    /**
     * <pre>get(这里用一句话描述这个方法的作用)
     * 创建人：lyp
     * 创建时间：2020年11月19日 下午8:49:57
     * 修改人：lyp
     * 修改时间：2020年11月19日 下午8:49:57
     * 修改备注：
     * @param url
     * @param params
     * @return</pre>
     */
    public static String get(String url, HashMap<String, Object> params){
        try {
            //2、创建get对象
            HttpGet httpGet = new HttpGet();

            //3、拼接完成的get请求路径
            Set<String> keySet = params.keySet();
            StringBuffer stringBuffer = new StringBuffer();
            stringBuffer.append(url).append("?t=").append(System.currentTimeMillis());
            for (String key : keySet) {
                stringBuffer.append("&").append(key).append("=").append(params.get(key));
            }

            httpGet.setURI(new URI(stringBuffer.toString()));
            //4、执行execute方法，发送请求
            CloseableHttpResponse execute = client.execute(httpGet);
            //5、获取请求的状态码
            int statusCode = execute.getStatusLine().getStatusCode();
            //判断请求是否成功
            if (200 != statusCode) {
                return "";
            }
            return EntityUtils.toString(execute.getEntity(), "utf-8");
        }catch (Exception e) {
            //6、关闭连接
            try {
                client.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
            return null;
        }
    }

    /**
     * <pre>post(这里用一句话描述这个方法的作用)
     * 创建人：lyp
     * 创建时间：2020年11月19日 下午8:50:05
     * 修改人：lyp
     * 修改时间：2020年11月19日 下午8:50:05
     * 修改备注：
     * @param url
     * @param params
     * @return</pre>
     */
    public static String post(String url,HashMap<String, Object> params) {
        try {
            //创建post请求
            HttpPost httpPost = new HttpPost();
            //设置url地址
            httpPost.setURI(new URI(url));

            //设置参数
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            //遍历map集合 key--value
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                NameValuePair e = new BasicNameValuePair(key, params.get(key).toString());
                parameters.add(e);
            }

            HttpEntity entity = new UrlEncodedFormEntity(parameters , "utf-8");
            httpPost.setEntity(entity);

            //发送请求
            CloseableHttpResponse execute = client.execute(httpPost);

            //处理请求的状态码
            int statusCode = execute.getStatusLine().getStatusCode();
            if (200 != statusCode) {
                return "";
            }

            //获取请求的返回值
            return EntityUtils.toString(execute.getEntity(), "utf-8");
        }catch (Exception e) {
            //6、关闭连接
            try {
                client.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
            return null;
        }
    }

    //调用短信接口：给header传参
    public static String post2(String url,HashMap<String, Object> headerParam,HashMap<String, Object> params) {
        try {
            //创建post请求
            HttpPost httpPost = new HttpPost();
            //设置url地址
            httpPost.setURI(new URI(url));

            //设置header参数
            Set<String> headSet = headerParam.keySet();
            for (String key : headSet) {
                httpPost.setHeader(key,headerParam.get(key).toString());
            }

            //设置参数
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            //遍历map集合 key--value
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                NameValuePair e = new BasicNameValuePair(key, params.get(key).toString());
                parameters.add(e);
            }

            HttpEntity entity = new UrlEncodedFormEntity(parameters , "utf-8");
            httpPost.setEntity(entity);

            //发送请求
            CloseableHttpResponse execute = client.execute(httpPost);

            //处理请求的状态码
            int statusCode = execute.getStatusLine().getStatusCode();
            if (200 != statusCode) {
                return "";
            }

            //获取请求的返回值
            return EntityUtils.toString(execute.getEntity(), "utf-8");
        }catch (Exception e) {
            //6、关闭连接
            try {
                client.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            e.printStackTrace();
            return null;
        }
    }

    public static String post3(String url,String params) {
        try {
            HttpPost httpPost = new HttpPost();
            httpPost.setURI(new URI(url));
            //设置头信息
            httpPost.addHeader("Content-Type", "application/json");

            StringEntity stringEntity = new StringEntity(params,  "utf-8");
            httpPost.setEntity(stringEntity);

            CloseableHttpResponse execute = client.execute(httpPost);

            int statusCode = execute.getStatusLine().getStatusCode();
            if (200 != statusCode) {
                return "";
            }
            return EntityUtils.toString(execute.getEntity(), "utf-8");
        }catch (Exception e) {
            //6、关闭连接
/*			try {
				client.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}*/
            e.printStackTrace();
            return null;
        }
    }

    //发送短信验证码
    public static JSONObject sendMsgCode(String phone){
        String url = CommonConfig.SEND_MSG_URL;
        HashMap<String, Object> headerParam = new HashMap<String, Object>();
        HashMap<String, Object> params = new HashMap<String, Object>();

        //请求中Headers的设置
        //AppKey 开发者平台分配的appkey
        String appKey = CommonConfig.SEND_MSG_APPKEY;
        //Nonce	随机数（最大长度128个字符） 6位  100000 999999
        long nonce = Math.round((Math.random()*899999+100000));
        System.out.println(nonce);
        //CurTime	当前UTC时间戳，从1970年1月1日0点0 分0 秒开始到现在的秒数(String):获取当前系统的毫秒值、时间戳
        long curTime = System.currentTimeMillis()/1000;
        //CheckSum	SHA1(AppSecret + Nonce + CurTime)，三个参数拼接的字符串，进行SHA1哈希计算，转化成16进制字符(String，小写)
        //
        String appSecret = CommonConfig.SEND_MSG_APPSECRET;
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce+"", curTime+"");

        headerParam.put("AppKey", appKey);
        headerParam.put("Nonce", nonce);
        headerParam.put("CurTime", curTime);
        headerParam.put("CheckSum", checkSum);


        params.put("mobile", phone);//目标手机号
        params.put("templateid",  CommonConfig.SEND_MSG_TEMPLATEID);//模板编号(如不指定则使用配置的默认模版)
        params.put("authCode", nonce);//客户自定义验证码，长度为4～10个数字

        String value = HttpClientUtil.post2(url, headerParam, params);
        JSONObject valueObj = JSONObject.parseObject(value);
        int code = valueObj.getIntValue("code");
        System.out.println(value);
/*		if(code==200){//成功
			System.out.println("发送短信成功！！！");
		}else{
			System.out.println("发送短信失败！！！");
		}
		*/
        //1、成功、失败      2、 验证码
        return valueObj;
    }

    //发送短信验证码
    public static void main(String[] args) {
        String url = "https://api.netease.im/sms/sendcode.action";
        HashMap<String, Object> headerParam = new HashMap<String, Object>();
        HashMap<String, Object> params = new HashMap<String, Object>();

        //请求中Headers的设置
        //AppKey 开发者平台分配的appkey
        String appKey = "b61192321e6efe152fe7be5db4ec6e98";
        //Nonce	随机数（最大长度128个字符） 6位  100000 999999
        long nonce = Math.round((Math.random()*899999+100000));
        System.out.println(nonce);
        //CurTime	当前UTC时间戳，从1970年1月1日0点0 分0 秒开始到现在的秒数(String):获取当前系统的毫秒值、时间戳
        long curTime = System.currentTimeMillis()/1000;
        //CheckSum	SHA1(AppSecret + Nonce + CurTime)，三个参数拼接的字符串，进行SHA1哈希计算，转化成16进制字符(String，小写)
        //
        String appSecret = "01edbcd47cef";
        String checkSum = CheckSumBuilder.getCheckSum(appSecret, nonce+"", curTime+"");

        headerParam.put("AppKey", appKey);
        headerParam.put("Nonce", nonce);
        headerParam.put("CurTime", curTime);
        headerParam.put("CheckSum", checkSum);


        params.put("mobile", "18210845283");//目标手机号
        params.put("templateid", "14887545");//模板编号(如不指定则使用配置的默认模版)
        params.put("authCode", nonce);//客户自定义验证码，长度为4～10个数字

        String value = HttpClientUtil.post2(url, headerParam, params);
        JSONObject valueObj = JSONObject.parseObject(value);
        int code = valueObj.getIntValue("code");
        System.out.println(value);
        if(code==200){//成功
            System.out.println("发送短信成功！！！");
        }else{
            System.out.println("发送短信失败！！！");
        }
    }

    //调用天气
    public static void main1(String[] args) {
        //请求链接：http://t.weather.itboy.net/api/weather/city/101030100就这个链接，然后get请求，不用再带任何参数。
        String url = "http://t.weather.itboy.net/api/weather/city/101030100";
        HashMap<String, Object> params = new HashMap<String, Object>();
        String value = HttpClientUtil.get(url, params);
        //先判断状态码：看请求是否成功
        //把json字符串转换成json对象
        JSONObject jsonObj = JSONObject.parseObject(value);
        //从json对象中获取属性值
        int status = jsonObj.getIntValue("status");
        String time = jsonObj.getString("time");

        JSONObject jsonObject = jsonObj.getJSONObject("cityInfo");
        String city = jsonObject.getString("city");

        JSONObject jsonObject2 = jsonObj.getJSONObject("data");
        String wendu = jsonObject2.getString("wendu");

        JSONObject jsonObject3 = jsonObject2.getJSONObject("yesterday");
        String week = jsonObject3.getString("week");

        JSONArray jsonArray = jsonObject2.getJSONArray("forecast");
        JSONObject jsonObject4 = jsonArray.getJSONObject(1);
        String week2 = jsonObject4.getString("week");

        System.out.println("状态码："+status);
        System.out.println("time："+time);
        System.out.println("city："+city);
        System.out.println("wendu："+wendu);
        System.out.println("week："+week);
        System.out.println("week2："+week2);

    }
}
