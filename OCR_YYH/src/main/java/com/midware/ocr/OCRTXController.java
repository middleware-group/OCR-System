package com.midware.ocr;

import com.baidu.aip.ocr.AipOcr;
import com.qcloud.image.ImageClient;
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
public class OCRTXController {

    @RequestMapping(value = "/ocrtx",method = RequestMethod.POST)
    public static Map<String, Object> ocr(@RequestParam("file") MultipartFile file) throws Exception{

        //保存文件
        String path = System.getProperty("user.dir") + "\\src\\main\\resources\\images";
        String name = file.getOriginalFilename();//直接返回文件的名字
        String subffix = name.substring(name.lastIndexOf(".") + 1, name.length());//我这里取得文件后缀
        String fileName = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        File newFile = new File(path+"\\" + fileName + "."+subffix);
        file.transferTo(newFile);

        OCRTX ocrtx = new OCRTX();
        Object result = ocrtx.doOcr(newFile);

        String realpath = "http://" + "localhost" + ":" + "2020" + "/image" + "/" + fileName+"."+ subffix;

        HashMap<String,Object> options = new HashMap<String,Object>();
        options.put("result",result);
        options.put("fileName",realpath);
        return options;
    }
}
