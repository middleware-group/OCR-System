package com.midware.ocr;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
public class OCRController {

    @Value("${server.address}")
    private String address;

    @Value("${server.port}")
    private String port;

    private String agreement = "http://";

    private static final String APP_ID="16137877";
    private static final String API_KEY="6n1vGtKLFSZBHzFIRb1kt1VT";
    private static final String SECRET_KEY="SogkfUiPefY0Prfrg0zq4nMjadkv5RtL";

    @RequestMapping(value = "/ocr",method = RequestMethod.POST)
    public Map<String, Object> ocr(@RequestParam("file") MultipartFile file) throws Exception{

        //OCRTX
        byte[] buf=file.getBytes();
        AipOcr client=new AipOcr(APP_ID,API_KEY,SECRET_KEY);
        HashMap<String,String> options=new HashMap<String,String>();
        options.put("language_type","CHN_ENG");
        JSONObject res=client.basicGeneral(buf,options);
        System.out.println(res.toString(2));

        //保存文件
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\images";
        String name = file.getOriginalFilename();//直接返回文件的名字
        String subffix = name.substring(name.lastIndexOf(".") + 1, name.length());//我这里取得文件后缀
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        file.transferTo(new File(path+"\\" + fileName + "."+subffix));
        String realpath = agreement + address + ":" + port + "/image" + "/" + fileName+"."+ subffix;
        res.put("fileName",realpath);
        return res.toMap();
    }
}
