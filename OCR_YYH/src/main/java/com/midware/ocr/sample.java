package com.midware.ocr;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;

import java.util.HashMap;

public class sample {
    private static final String APP_ID="16137877";
    private static final String API_KEY="6n1vGtKLFSZBHzFIRb1kt1VT";
    private static final String SECRET_KEY="SogkfUiPefY0Prfrg0zq4nMjadkv5RtL";
    public static void main(String[] args) {
        // 初始化一个AipOcr
        AipOcr client = new AipOcr(APP_ID, API_KEY, SECRET_KEY);
        // 调用接口
        String path = "D:\\厦门大学\\大三下\\中间件\\test.png";
        JSONObject res = client.basicGeneral(path, new HashMap<String, String>());
        System.out.println(res.toString(2));

    }

    }
