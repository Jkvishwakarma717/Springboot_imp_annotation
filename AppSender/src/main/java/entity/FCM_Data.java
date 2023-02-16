package entity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class FCM_Data {

	private String title;
	private String message;
	private String landing_url;
	private String banner_img_url;
	private String icon_img_url;
	private String cta_button_count;
	private String btn_a_title;
	private String btn_b_title;
	private String btn_a_url;
	private String btn_b_url;
	private String cfg = "3";
	private String ct;
	private String iz_cid;
	private String k = "49696";
	private String r = "300000+11 DIGIT TIMESTAMP";
	private String notify_sticky;
	private String tg;
	private String tl = "0";
	private String open_inside_app;
	private String gcmid;
	private String notify_expire_in;
	private String tkn;
	private String run_id;

	public String getRun_id() {
		return run_id;
	}

	public void setRun_id(String run_id) {
		this.run_id = run_id;
	}

	public String getTkn() {
		return tkn;
	}

	public void setTkn(String tkn) {
		this.tkn = tkn;
	}

	public String getNotify_expire_in() {
		return notify_expire_in;
	}

	public void setNotify_expire_in(String notify_expire_in) {
		this.notify_expire_in = notify_expire_in;
	}

	public String getGcmid() {
		return gcmid;
	}

	public void setGcmid(String gcmid) {
		this.gcmid = gcmid;
	}

	public String getTl() {
		return tl;
	}

	public void setTl(String tl) {
		this.tl = tl;
	}

	public String getTg() {
		return tg;
	}

	public void setTg(String tg) {
		this.tg = tg;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getIz_cid() {
		return iz_cid;
	}

	public void setIz_cid(String iz_cid) {
		this.iz_cid = iz_cid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLanding_url() {
		return landing_url;
	}

	public void setLanding_url(String landing_url) {
		this.landing_url = landing_url;
	}

	public String getBanner_img_url() {
		return banner_img_url;
	}

	public void setBanner_img_url(String banner_img_url) {
		this.banner_img_url = banner_img_url;
	}

	public String getIcon_img_url() {
		return icon_img_url;
	}

	public void setIcon_img_url(String icon_img_url) {
		this.icon_img_url = icon_img_url;
	}

	public String getCta_button_count() {
		return cta_button_count;
	}

	public void setCta_button_count(String cta_button_count) {
		this.cta_button_count = cta_button_count;
	}

	public String getBtn_a_title() {
		return btn_a_title;
	}

	public void setBtn_a_title(String btn_a_title) {
		this.btn_a_title = btn_a_title;
	}

	public String getBtn_b_title() {
		return btn_b_title;
	}

	public void setBtn_b_title(String btn_b_title) {
		this.btn_b_title = btn_b_title;
	}

	public String getBtn_a_url() {
		return btn_a_url;
	}

	public void setBtn_a_url(String btn_a_url) {
		this.btn_a_url = btn_a_url;
	}

	public String getBtn_b_url() {
		return btn_b_url;
	}

	public void setBtn_b_url(String btn_b_url) {
		this.btn_b_url = btn_b_url;
	}

	public String getCfg() {
		return cfg;
	}

	public void setCfg(String cfg) {
		this.cfg = cfg;
	}

	public String getCt() {
		return ct;
	}

	public void setCt(String ct) {
		this.ct = ct;
	}

	public String getK() {
		return k;
	}

	public void setK(String k) {
		this.k = k;
	}

	public String getR() {
		return r;
	}

	public void setR(String r) {
		this.r = r;
	}

	public String getNotify_sticky() {
		return notify_sticky;
	}

	public void setNotify_sticky(String notify_sticky) {
		this.notify_sticky = notify_sticky;
	}

	public String getOpen_inside_app() {
		return open_inside_app;
	}

	public void setOpen_inside_app(String open_inside_app) {
		this.open_inside_app = open_inside_app;
	}

	Map<String, String> _raw_cData;

	public FCM_Data(HashMap<String, String> campaignData) {
		this._raw_cData = campaignData;
		assign_param_values();
	}

	private void assign_param_values() {
		if (this._raw_cData != null) {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd H:m:s", Locale.ENGLISH);
			this.title = this._raw_cData.containsKey("title") ? this._raw_cData.get("title").toString() : "";
			this.message = this._raw_cData.containsKey("title") ? this._raw_cData.get("message").toString() : "";
			this.landing_url = this._raw_cData.containsKey("landing_url")
					? this._raw_cData.get("landing_url").toString()
					: "";
			this.banner_img_url = this._raw_cData.containsKey("banner_img_url")
					? this._raw_cData.get("banner_img_url").toString()
					: "";
			this.icon_img_url = this._raw_cData.containsKey("icon_img_url")
					? this._raw_cData.get("icon_img_url").toString()
					: "";
			this.cta_button_count = this._raw_cData.containsKey("cta_button_count")
					? this._raw_cData.get("cta_button_count").toString()
					: "";
			this.btn_a_title = this._raw_cData.containsKey("btn_a_title")
					? this._raw_cData.get("btn_a_title").toString()
					: "";
			this.btn_b_title = this._raw_cData.containsKey("btn_b_title")
					? this._raw_cData.get("btn_b_title").toString()
					: "";
			this.btn_a_url = this._raw_cData.containsKey("btn_a_url") ? this._raw_cData.get("btn_a_url").toString()
					: "";
			this.btn_b_url = this._raw_cData.containsKey("btn_b_url") ? this._raw_cData.get("btn_b_url").toString()
					: "";
			this.cfg = this._raw_cData.containsKey("cfg") ? this._raw_cData.get("cfg").toString() : "";
			this.ct = String.valueOf(new Date().getTime());
			this.iz_cid = this._raw_cData.containsKey("iz_cid") ? this._raw_cData.get("iz_cid").toString() : "";
			this.r = this._raw_cData.containsKey("r") ? "300000" + this._raw_cData.get("r").toString() : "300000";
			this.notify_sticky = this._raw_cData.containsKey("notify_sticky")
					? this._raw_cData.get("notify_sticky").toString()
					: "0";
			this.tg = String.valueOf(new Date().getTime());
			this.tl = this._raw_cData.containsKey("tl") ? this._raw_cData.get("tl").toString() : "0";
			this.open_inside_app = this._raw_cData.containsKey("open_inside_app")
					? this._raw_cData.get("open_inside_app").toString()
					: "0";
			try {
				this.run_id = this._raw_cData.containsKey("run_time")
						? "300000" + formatter.parse(this._raw_cData.get("run_time")).getTime() / 1000
						: "300000";
			} catch (ParseException e) {
				this.run_id = "300000";
			}
			this.gcmid = this._raw_cData.containsKey("gcmid") ? this._raw_cData.get("gcmid").toString() : "";
			this.tkn = this._raw_cData.containsKey("tkn") ? this._raw_cData.get("tkn").toString() : "";
			this.notify_expire_in = this._raw_cData.containsKey("notify_expire_in")
					? this._raw_cData.get("notify_expire_in").toString()
					: "";
		}
	}

	@Override
	public String toString() {
		return '{' + "\"t\":\"" + title + "\"" + ",\"m\":\"" + message + "\"" + ",\"ln\":\"" + landing_url + "\""
				+ ",\"bi\":\"" + banner_img_url + "\"" + ",\"i\":\"" + icon_img_url + "\"" + ",\"b\":\""
				+ cta_button_count + "\"" + ",\"b1\":\"" + btn_a_title + "\"" + ",\"b2\":\"" + btn_b_title + "\""
				+ ",\"l1\":\"" + btn_a_url + "\"" + ",\"l2\":\"" + btn_b_url + "\"" + ",\"cfg\":\"" + cfg + "\""
				+ ",\"ct\":\"" + ct + "\"" + ",\"id\":\"" + iz_cid + "\"" + ",\"k\":\"" + k + "\"" + ",\"r\":\"" + r
				+ "\"" + ",\"ri\":\"" + notify_sticky + "\"" + ",\"tg\":\"" + tg + "\"" + ",\"tl\":\"" + tl + "\""
				+ ",\"ia\":\"" + open_inside_app + "\"" + ",\"gcmid\":\"" + gcmid + "\"" + ",\"tkn\":\"" + tkn + "\""
				+ ",\"time_to_live\":\"" + notify_expire_in + "\"" + '}';
	}

}
