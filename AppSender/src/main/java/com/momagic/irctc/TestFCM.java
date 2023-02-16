package com.momagic.irctc;

import java.io.File;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import org.json.JSONObject;
import entity.FCM_Data;
import service.CampaignService;
import service.MessageService;

public class TestFCM {
	public static void main(String[] args) {
		JSONObject data1 = new JSONObject();
		data1.put("t",String.valueOf(88));
		System.out.println(data1);
		System.exit(0);



		String csvHeader = "gcmid";
		CampaignService campaign = new CampaignService(1);
		List<HashMap<String, String>> scheduledCampaigns = campaign.getScheduledCampaigns(3);
		for (HashMap<String, String> campaignData : scheduledCampaigns) {
			FCM_Data fcm_notification = new FCM_Data(campaignData);
			String api_key = new String(Base64.getDecoder().decode(fcm_notification.getTkn()));
			JSONObject data = new JSONObject();
			data.put("t", fcm_notification.getTitle());
			data.put("m", fcm_notification.getMessage());
			data.put("ln", fcm_notification.getLanding_url());
			data.put("bi", fcm_notification.getBanner_img_url());
			data.put("i", fcm_notification.getIcon_img_url());
			data.put("b", fcm_notification.getCta_button_count());
			data.put("b1", fcm_notification.getBtn_a_title());
			data.put("b2", fcm_notification.getBtn_b_title());
			data.put("l1", fcm_notification.getBtn_a_url());
			data.put("l2", fcm_notification.getBtn_b_url());
			data.put("cfg", fcm_notification.getCfg());
			data.put("ct", fcm_notification.getCt());
			data.put("id", fcm_notification.getIz_cid());
			data.put("k", fcm_notification.getK());
			data.put("r", fcm_notification.getR());
			data.put("ri", fcm_notification.getNotify_sticky());
			data.put("tg", fcm_notification.getTg());
			data.put("tl", fcm_notification.getTl());
			data.put("ia", fcm_notification.getOpen_inside_app());
			JSONObject notify_expire_in = new JSONObject();
			notify_expire_in.put("notify_expire_in", fcm_notification.getNotify_expire_in());
			JSONObject protocol = new JSONObject();
			protocol.put("data", data);
			protocol.put("time_to_live", Integer.parseInt(fcm_notification.getNotify_expire_in()));
			String csv_file_path = campaign.getUserCsv(campaignData);
			File csv_file = new File(csv_file_path);
			if (csv_file.exists()) {
				MessageService pushMsg = new MessageService();
				HashMap<String, Integer> response = pushMsg.sendMultiPushFromCsv(csvHeader, csv_file_path, protocol,
						api_key);
				System.out.println("response =>" + response);
				campaign.setToComplete(Integer.parseInt(campaignData.get("id")), response.get("total"),
						response.get("success"), Long.parseLong(fcm_notification.getRun_id()));

			}
		}
	}
}
