package cn.home.spider.xyspider.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class HttpClientUtils {
	private static PoolingHttpClientConnectionManager cm;
	private static String EMPTY_STR = "";
	private static String UTF_8 = "UTF-8";

	private static void init() {
		if (cm == null) {
			cm = new PoolingHttpClientConnectionManager();
			cm.setMaxTotal(50);// 整个连接池最大连接数
			cm.setDefaultMaxPerRoute(5);// 每路由最大连接数，默认值是2
		}
	}

	/**
	 * 通过连接池获取HttpClient
	 * 
	 * @return
	 */
	private static CloseableHttpClient getHttpClient() {
		init();
		return HttpClients.custom().setConnectionManager(cm).build();
	}

	/**
	 *
	 * @param url
	 * @return
	 */
	public static String httpGetRequest(String url) {

		HttpGet httpGet = new HttpGet(url);

		httpGet.addHeader("authority", "s.2.taobao.com");
		httpGet.addHeader("method", "GET");
		httpGet.addHeader("path", "/list/list.htm?q=switch&search_type=item&_input_charset=utf8");
		httpGet.addHeader("scheme", "https");
		httpGet.addHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpGet.addHeader("accept-encoding", "gzip, deflate, sdch");
		httpGet.addHeader("accept-language", "zh-CN,zh;q=0.8");
		httpGet.addHeader("cookie",
				"_uab_collina=153811514407666353852588; swfstore=223730; v=0; _tb_token_=3baf077be1611; cna=ILQ0FFwyQWQCAWXkY27/wHlb; unb=667234611; sg=11c; t=ddee7b1a5a5b4636ae918c39dfd2e57f; _l_g_=Ug%3D%3D; skt=c5d63135e313f0b1; cookie2=111fd69282045e8d970333361a6207f7; cookie1=AVNTcifkgZGt1YGr7xahwlJ1520cHvvrv8Lu3WDRF9s%3D; csg=4113cc85; uc3=vt3=F8dByRuRs6MVQXocOoo%3D&id2=VWn7gwQFdPO9&nk2=EFeeTVjo8BGjug%3D%3D&lg2=U%2BGCWk%2F75gdr5Q%3D%3D; existShop=MTUzODExNTExNA%3D%3D; tracknick=sing%5Cu6B872011; lgc=sing%5Cu6B872011; _cc_=WqG3DMC9EA%3D%3D; dnk=sing%5Cu6B872011; _nk_=sing%5Cu6B872011; cookie17=VWn7gwQFdPO9; tg=0; UM_distinctid=1661ecf8189105-038c90dcb91977-414a0229-1fa400-1661ecf818a799; mt=ci=1_1; CNZZDATA30057895=cnzz_eid%3D50889380-1538110359-%26ntime%3D1538110359; whl=-1%260%260%261538116826238; enc=TQHKf9PWmzz8lOwjLYg0hNFfPCAeGhYiQ1KLv55nspHqXQYSZasiXo3NUTJOqQZIRBiLoZsuxfXNyJgkewmm3Q%3D%3D; uc1=\"cookie15=UIHiLt3xD8xYTw%3D%3D\"; hng=CN%7Czh-CN%7CCNY%7C156; thw=cn; _umdata=BA335E4DD2FD504FEF0A519C6E53011E2A3D12D925F079E143888C837C307F9F7B2F1E7046D9CE6ECD43AD3E795C914CA56C3330668B3737CF3E246AF7BE9D13; x5sec=7b2269646c653b32223a226631633961383736393764393937633138383934613965376637303531343136434c656c74393046454c72597150544f6a70535249526f4d4e6a59334d6a4d304e6a45784f7a4934227d; CNZZDATA1252911424=443682555-1538114571-%7C1538114571; CNZZDATA30058275=cnzz_eid%3D1865872348-1538111370-null%26ntime%3D1538116770; x=e%3D1%26p%3D*%26s%3D0%26c%3D0%26f%3D0%26g%3D0%26t%3D0%26__ll%3D-1%26_ato%3D0; isg=BHt7Dcd0pC-qOJiS8Z1kGDr_Cl_l0I_SSGn5RG04PnqRzJuu9aLQItgG4iwnbOfK");
		httpGet.addHeader("upgrade-insecure-requests", "1");
		httpGet.addHeader("user-agent",
				"Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
		return getResult(httpGet);
	}

	public static String httpGetRequest(String url, Map<String, Object> params) throws URISyntaxException {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);

		HttpGet httpGet = new HttpGet(ub.build());
		return getResult(httpGet);
	}

	public static String httpGetRequest(String url, Map<String, Object> headers, Map<String, Object> params)
			throws URISyntaxException {
		URIBuilder ub = new URIBuilder();
		ub.setPath(url);

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		ub.setParameters(pairs);

		HttpGet httpGet = new HttpGet(ub.build());
		for (Map.Entry<String, Object> param : headers.entrySet()) {
			httpGet.addHeader(param.getKey(), param.getValue().toString());
		}
		return getResult(httpGet);
	}

	public static String httpPostRequest(String url) {
		HttpPost httpPost = new HttpPost(url);
		return getResult(httpPost);
	}

	public static String httpPostRequest(String url, Map<String, Object> params) throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);
		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));
		return getResult(httpPost);
	}

	public static String httpPostRequest(String url, Map<String, Object> headers, Map<String, Object> params)
			throws UnsupportedEncodingException {
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
		for (Map.Entry<String, Object> param : headers.entrySet()) {
			httpPost.addHeader(param.getKey(), param.getValue().toString());
		}

		ArrayList<NameValuePair> pairs = covertParams2NVPS(params);
		httpPost.setEntity(new UrlEncodedFormEntity(pairs, UTF_8));

		return getResult(httpPost);
	}

	public static String httpPostRequest(String url, String json) {
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.CONTENT_TYPE, "application/json;charset=utf-8");
		StringEntity entity = new StringEntity(json, ContentType.APPLICATION_JSON);
		entity.setContentType("application/json;charset=utf-8");
		httpPost.setEntity(entity);
		return getResult(httpPost);
	}

	private static ArrayList<NameValuePair> covertParams2NVPS(Map<String, Object> params) {
		ArrayList<NameValuePair> pairs = new ArrayList<NameValuePair>();
		for (Map.Entry<String, Object> param : params.entrySet()) {
			pairs.add(new BasicNameValuePair(param.getKey(), param.getValue().toString()));
		}

		return pairs;
	}

	public static void main(String[] args) {
		JSONObject jo = HttpClientUtils.uploadMedia(
				"https://oapi.dingtalk.com/media/upload?access_token=eb5c9d1b33f733f0a8ac4589d45329e7&type=file",
				new File("d:/1.xlsx"));
		// {"created_at":1476864812802,"media_id":"@lAzOfGC4MM5aqfNnzg-SUPo","type":"file"}
		System.out.println(jo.toString());
	}

	public static JSONObject uploadMedia(String url, File file) {
		HttpPost httpPost = new HttpPost(url);
		CloseableHttpResponse response = null;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(2000).setConnectTimeout(2000).build();
		httpPost.setConfig(requestConfig);

		HttpEntity requestEntity = MultipartEntityBuilder.create()
				.addPart("media", new FileBody(file, ContentType.APPLICATION_OCTET_STREAM, file.getName())).build();
		httpPost.setEntity(requestEntity);

		try {
			response = httpClient.execute(httpPost, new BasicHttpContext());

			if (response.getStatusLine().getStatusCode() != 200) {

				System.out.println(
						"request url failed, http code=" + response.getStatusLine().getStatusCode() + ", url=" + url);
				return null;
			}
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				String resultStr = EntityUtils.toString(entity, "utf-8");

				JSONObject result = JSON.parseObject(resultStr);
				if (result.getInteger("errcode") == 0) {
					// 成功
					result.remove("errcode");
					result.remove("errmsg");
					return result;
				} else {
					System.out.println("request url=" + url + ",return value=");
					System.out.println(resultStr);
					int errCode = result.getInteger("errcode");
					String errMsg = result.getString("errmsg");
				}
			}
		} catch (IOException e) {
			System.out.println("request url=" + url + ", exception, msg=" + e.getMessage());
			e.printStackTrace();
		} finally {
			if (response != null)
				try {
					response.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}

		return null;
	}

	/**
	 * 处理Http请求
	 * 
	 * @param request
	 * @return
	 */
	private static String getResult(HttpRequestBase request) {
		// CloseableHttpClient httpClient = HttpClients.createDefault();
		CloseableHttpClient httpClient = getHttpClient();
		try {
			CloseableHttpResponse response = httpClient.execute(request);
			// response.getStatusLine().getStatusCode();
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// long len = entity.getContentLength();// -1 表示长度未知
				String result = EntityUtils.toString(entity, UTF_8);
				response.close();
				// httpClient.close();
				return result;
			}
		} catch (ClientProtocolException e) {
			LogFactory.getLog(HttpClientUtils.class).error("", e);
		} catch (IOException e) {
			LogFactory.getLog(HttpClientUtils.class).error("", e);
		} finally {

		}
		return EMPTY_STR;
	}

}
