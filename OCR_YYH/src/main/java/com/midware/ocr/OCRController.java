package com.midware.ocr;

import com.baidu.aip.ocr.AipOcr;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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

    @RequestMapping(value = "/basicOcr",method = RequestMethod.POST)
    public Map<String, Object> basicOcr(@RequestParam("file") MultipartFile file) throws Exception{

        //获取文件信息
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\images";
        String name = file.getOriginalFilename();//直接返回文件的名字
        String subffix = name.substring(name.lastIndexOf(".") + 1, name.length());//我这里取得文件后缀
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        //OCR
        byte[] buf=file.getBytes();
        AipOcr client=new AipOcr(APP_ID,API_KEY,SECRET_KEY);
        HashMap<String,String> options=new HashMap<String,String>();
        options.put("language_type","CHN_ENG");
        JSONObject res=client.basicGeneral(buf,options);
        //System.out.println(res.toString(2));

        //保存文件
        file.transferTo(new File(path+"\\" + fileName + "."+subffix));
        String realpath = agreement + address + ":" + port + "/image" + "/" + fileName+"."+ subffix;
        res.put("fileName",realpath);

        return res.toMap();
    }

    @RequestMapping(value = "/accurateOcr",method = RequestMethod.POST)
    public Map<String, Object> accurateOcr(@RequestParam("file") MultipartFile file) throws Exception{

        //获取文件信息
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\images";
        String name = file.getOriginalFilename();//直接返回文件的名字
        String subffix = name.substring(name.lastIndexOf(".") + 1, name.length());//我这里取得文件后缀
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        //保存文件
        file.transferTo(new File(path+"\\" + fileName + "."+subffix));
        String realpath = agreement + address + ":" + port + "/image" + "/" + fileName+"."+ subffix;

        //预处理
        PreProcessService preProcessService = new PreProcessService();
        String filePath = path + "\\" + fileName + "." + subffix;
        preProcessService.preProcessImage(filePath);

        //OCRTX
        byte[] buf=getBytes(path + "\\" + fileName + "new." + subffix);
        AipOcr client=new AipOcr(APP_ID,API_KEY,SECRET_KEY);
        HashMap<String,String> options=new HashMap<String,String>();
        options.put("language_type","CHN_ENG");
        JSONObject res=client.accurateGeneral(buf,options);
        res.put("fileName",realpath);

        return res.toMap();
    }

    // ----------------获得指定文件的byte数组  ----------------
    public static byte[] getBytes(String filePath){
        byte[] buffer = null;
        try {
            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
            byte[] b = new byte[1000];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();
            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }
}
