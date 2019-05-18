package com.midware.ocr;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OCRController {

    private static final String APP_ID="16137877";
    private static final String API_KEY="6n1vGtKLFSZBHzFIRb1kt1VT";
    private static final String SECRET_KEY="SogkfUiPefY0Prfrg0zq4nMjadkv5RtL";
    @RequestMapping(value = "/ocr",method = RequestMethod.POST)
    public Map<String, Object> ocr(@RequestParam("file") MultipartFile file) throws Exception{
        byte[] buf=file.getBytes();
        AipOcr client=new AipOcr(APP_ID,API_KEY,SECRET_KEY);
        HashMap<String,String> options=new HashMap<String,String>();
        options.put("language_type","CHN_ENG");
        JSONObject res=client.basicGeneral(buf,options);
        System.out.println(res.toString(2));
        return res.toMap();
    }
}
