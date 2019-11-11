package com.power.taskcenter.util;

import cn.hutool.log.Log;
import cn.hutool.log.LogFactory;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;





/**
 * HTTP工具类
 * @author Louis
 * @date Jun 29, 2019
 */

public class HttpUtils {

	/**
	 * 获取HttpServletRequest对象
	 * @return
	 */
	private static final Log log = LogFactory.get();
	public static HttpServletRequest getHttpServletRequest() {
		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
	}
	public static JSONObject getJSONParam(HttpServletRequest request){
		JSONObject jsonParam = null;
		try {
			// 获取输入流
			BufferedReader streamReader = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
			// 写入数据到Stringbuilder
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = streamReader.readLine()) != null) {
				sb.append(line);
			}
			jsonParam = JSONObject.parseObject(sb.toString());
		} catch (Exception e) {
			jsonParam=null;
		}
		return jsonParam;
	}

	public static String post(String url, Map<String,String> params)throws Exception {


		CloseableHttpClient httpclient = HttpClients.createDefault();
 		HttpPost httpPost = new HttpPost(url);
		List<NameValuePair> plist=new ArrayList<NameValuePair>();
		for(String k:params.keySet()){
			plist.add(new BasicNameValuePair(k,params.get(k)));
		}
		UrlEncodedFormEntity e0=new UrlEncodedFormEntity(plist,"GBK");
		httpPost.setEntity(e0);
		CloseableHttpResponse r = null;
		try{
			r = httpclient.execute(httpPost);
			if(r.getStatusLine().getStatusCode() == 200){
				HttpEntity entity2 = r.getEntity();
				String result = EntityUtils.toString(entity2, "GBK");
				EntityUtils.consume(entity2);
				return result;
			}
		}catch (Exception ex){
			ex.printStackTrace();
		}  finally {
			if (r != null) {
                try {
                    r.close();
                } catch (IOException e) {

                }
            }
			httpPost.releaseConnection();
		}
		return null;
	}

//
//	/**
//	 * 输出信息到浏览器
//	 * @param response
//	 * @throws IOException
//	 */
//	public static void write(HttpServletResponse response, Object data) throws IOException {
//		response.setContentType("application/json; charset=utf-8");
//        HttpResult result = HttpResult.ok(data);
//        String json = JSONObject.toJSONString(result);
//        response.getWriter().print(json);
//        response.getWriter().flush();
//        response.getWriter().close();
//	}



	public static void main(String[] args){

//		String password = new BCryptPasswordEncoder().encode("60d2d3adcbf5d9f22f20cb21bd58e12a");
//		$2a$10$bCXM7Bjaqqq9XV/xM15QOO4uLvl2H0SeZVljliYbEKgTs.sgwZnr2
//		System.out.println(password);
//String uuid = Base64Util.uuidStr();
//
//
//
//
//
//		HashMap<String, String> paramMap1 = new HashMap<>();
//		paramMap1.put("clientId","jsjk");
//		paramMap1.put("verify",MD5Encode.MD5Encode("jsjk"+uuid+"5e51f19371554bc9abff13730bcd9bcf"));
//		paramMap1.put("infoVersion","2019");
//		paramMap1.put("nonce",uuid);
//		paramMap1.put("information","{\"唯一编号\":\"55858\",\"身份证号码\":\"320100200101010002\",\"姓名\":\"周社\",\"性别\":\"女\",\"出生日期\":\"2001-01-01\",\"体检日期\":\"2018-10-15\",\"民族\":\"土著族\",\"寄宿与否\":\"否\",\"既往病史肝炎\":\"无\",\"既往病史肝炎诊断日期\":\"2018-01-01\",\"既往病史肺结核\":\"无\",\"既往病史肺结核诊断日期\":\"2018-01-01\",\"既往病史有无肺结核密切接触史\":\"无\",\"既往病史先天性心脏病\":\"无\",\"既往病史先天性心脏病诊断日期\":\"2018-01-01\",\"既往病史肾炎\":\"无\",\"既往病史肾炎诊断日期\":\"2018-01-01\",\"既往病史风湿病\":\"无\",\"既往病史风湿病诊断日期\":\"2018-01-01\",\"既往病史哮喘\":\"有\",\"既往病史哮喘诊断日期\":\"2018-01-01\",\"既往病史其他病史说明\":\"既往病史其他病史说明\",\"残疾视力\":\"无\",\"残疾听力\":\"无\",\"残疾言语\":\"无\",\"残疾肢体\":\"无\",\"残疾智力\":\"无\",\"残疾精神\":\"无\",\"其他残疾说明\":\"无\",\"家长签名\":\"家长签名\",\"身高(cm)\":\"160.0\",\"体重(kg)\":\"50.0\",\"收缩压(mmHg)\":\"120\",\"舒张压(mmHg)\":\"80\",\"医师签名(一般)\":\"一般医生\",\"结膜\":\"正常\",\"角膜\":\"正常\",\"晶体\":\"正常\",\"瞳孔\":\"正常\",\"眼位\":\"正常\",\"眼球运动\":\"正常\",\"异常视觉行为\":\"畏光\",\"异常视觉行为其他\":\"异常视觉行为其它\",\"带镜情况\":\"佩戴角膜塑形镜\",\"配戴角膜塑形镜右眼\":\"1.5\",\"配戴角膜塑形镜左眼\":\"1.5\",\"右眼裸眼视力(按5.0计数法)\":\"5.0\",\"左眼裸眼视力(按5.0计数法)\":\"5.1\",\"右眼带镜视力(按5.0计数法)\":\"5.0\",\"左眼带镜视力(按5.0计数法)\":\"5.1\",\"屈光度检查右眼球镜\":\"+\",\"屈光度检查右眼球镜(S)D\":\"1.5\",\"屈光度检查右眼柱镜\":\"+\",\"屈光度检查右眼柱镜(C)D\":\"1.5\",\"屈光度检查右眼轴向值度(A)\":\"100.0\",\"屈光度检查左眼球镜\":\"-\",\"屈光度检查左眼球镜(S)D\":\"1.5\",\"屈光度检查左眼柱镜\":\"+\",\"屈光度检查左眼柱镜(C)D\":\"1.5\",\"屈光度检查左眼轴向值度(A)\":\"100.0\",\"临床印象\":\"未见异常\",\"临床印象近视\":\"否\",\"临床印象远视\":\"否\",\"临床印象弱视\":\"否\",\"临床印象斜视\":\"否\",\"临床印象散光\":\"否\",\"临床印象沙眼\":\"无\",\"临床印象结膜炎\":\"无\",\"临床印象其他\":\"无\",\"临床印象其他详情\":\"临床印象其他\",\"右眼角膜曲率半径(mm)\":\"12.01\",\"左眼角膜曲率半径(mm)\":\"\",\"右眼眼轴长度(mm)\":\"32.11\",\"左眼眼轴长度(mm)\":\"\",\"随访\":\"随访(*)\",\"转诊\":\"转诊(*)\",\"色觉\":\"正常\",\"医师签名(眼)\":\"眼科医生\",\"齿列\":\"整齐\",\"牙周\":\"正常\",\"有无龋齿\":\"无\",\"乳龋患d\":\"0\",\"乳龋失m\":\"0\",\"乳龋补f\":\"0\",\"恒龋患D\":\"0\",\"恒龋失M\":\"0\",\"恒龋补F\":\"0\",\"口腔其他记录\":\"口腔其他记录\",\"医师签名(口腔)\":\"口腔医生\",\"皮肤\":\"口腔医生\",\"淋巴结\":\"口腔医生\",\"头部\":\"正常\",\"颈部\":\"正常\",\"脊柱\":\"口腔医生\",\"四肢\":\"口腔医生\",\"胸部\":\"口腔医生\",\"外科其他记录\":\"口腔医生\",\"医师签名(外科)\":\"口腔医生\",\"近期不适症状疲乏无力\":\"无\",\"近期不适症状低热\":\"无\",\"近期不适症状盗汗\":\"无\",\"近期不适症状胸痛\":\"无\",\"近期不适症状咳嗽\":\"无\",\"近期不适症状咳痰\":\"无\",\"近期不适症状食欲减退\":\"无\",\"近期不适症状消瘦\":\"无\",\"其他症状\":\"近期其它症状\",\"心率\":\"80.0\",\"心脏杂音\":\"无\",\"心律\":\"齐\",\"肺部罗音\":\"干罗音\",\"肝\":\"正常\",\"脾\":\"脾大\",\"内科其他记录\":\"内科其他记录\",\"医师签名(内科)\":\"内科医生\",\"肝功能谷丙转氨酶ALT(U/L)\":\"10\",\"肝功能胆红素TBIL(umol/L)\":\"10\",\"血红蛋白（g/L）\":\"100\",\"结核菌素试验\":\"阴性\",\"检验其他化验结果\":\"检验其他化验结果\",\"检验师签名\":\"检验师医生\",\"胸部X线检查\":\"未见异常\",\"胸部X线检查情况描述\":\"无异常\",\"医师签名(检查)\":\"检查医生\",\"需复查项目\":\"需复查项目\",\"本次体检结论\":\"1、轻度视力不良，正确佩戴眼镜，增加户外活动，减少手机、电脑等电子产品使用时间。2、超重、肥胖：合理饮食，减少能量摄入，加强体育锻炼与户外活动。3、已填充牙无龋，每天早晚两次刷牙，饭后漱口，少吃甜食和饮料。\",\"健康评价\":\"健康良好\",\"健康指导\":\"健康指导\",\"体检单位\":\"苏州高新区人民医院\",\"主检医生签名\":\"主检医生\"}");
//
//
//		try {
//			String 	result = post("https://tzjk.jse.edu.cn/fms/twy-tijian-API/set.action", paramMap1);
//            System.out.println(result);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}


	}


	
}
