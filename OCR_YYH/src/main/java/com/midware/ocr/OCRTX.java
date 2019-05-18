package com.midware.ocr;

import com.qcloud.image.ImageClient;
import com.qcloud.image.exception.AbstractImageException;
import com.qcloud.image.request.FaceAddFaceRequest;
import com.qcloud.image.request.FaceAddGroupIdsRequest;
import com.qcloud.image.request.FaceCompareRequest;
import com.qcloud.image.request.FaceDelFaceRequest;
import com.qcloud.image.request.FaceDelGroupIdsRequest;
import com.qcloud.image.request.FaceDelPersonRequest;
import com.qcloud.image.request.FaceDetectRequest;
import com.qcloud.image.request.FaceGetFaceIdsRequest;
import com.qcloud.image.request.FaceGetFaceInfoRequest;
import com.qcloud.image.request.FaceGetGroupIdsRequest;
import com.qcloud.image.request.FaceGetInfoRequest;
import com.qcloud.image.request.FaceGetPersonIdsRequest;
import com.qcloud.image.request.FaceIdCardCompareRequest;
import com.qcloud.image.request.FaceIdCardLiveDetectFourRequest;
import com.qcloud.image.request.FaceIdentifyRequest;
import com.qcloud.image.request.FaceLiveDetectFourRequest;
import com.qcloud.image.request.FaceLiveDetectPictureRequest;
import com.qcloud.image.request.FaceLiveGetFourRequest;
import com.qcloud.image.request.FaceMultiIdentifyRequest;
import com.qcloud.image.request.FaceNewPersonRequest;
import com.qcloud.image.request.FaceSetInfoRequest;
import com.qcloud.image.request.FaceShapeRequest;
import com.qcloud.image.request.FaceVerifyRequest;
import com.qcloud.image.request.GeneralOcrRequest;
import com.qcloud.image.request.IdcardDetectRequest;
import com.qcloud.image.request.NamecardDetectRequest;
import com.qcloud.image.request.OcrBankCardRequest;
import com.qcloud.image.request.OcrBizLicenseRequest;
import com.qcloud.image.request.OcrDrivingLicenceRequest;
import com.qcloud.image.request.OcrInvoiceRequest;
import com.qcloud.image.request.OcrPlateRequest;
import com.qcloud.image.request.PornDetectRequest;
import com.qcloud.image.request.TagDetectRequest;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.logging.Level;
import java.util.logging.Logger;

public class OCRTX {

//    public static void main(String[] args) {
//        String appId = "1256432124";//根据你的帐号信息修改
//        String secretId = "AKIDiFdmjTjDWL1UBoyQrSYOL5MODmtnfwDF";//根据你的帐号信息修改
//        String secretKey = "3wc01qZ5MRUN7g280c2D87dw8IDvPl5E";//根据你的帐号信息修改
//        String bucketName = "";//历史遗留字段, 无需修改
//        ImageClient imageClient = new ImageClient(appId, secretId, secretKey, ImageClient.NEW_DOMAIN_recognition_image_myqcloud_com);
//
//        /*文字识别系列 ( OCRTX )*/
//        //通用
//        ocrGeneral(imageClient, bucketName);
//        imageClient.shutdown();
//    }

    public static Object doOcr(File file) {
        String appId = "1256432124";//根据你的帐号信息修改
        String secretId = "AKIDiFdmjTjDWL1UBoyQrSYOL5MODmtnfwDF";//根据你的帐号信息修改
        String secretKey = "3wc01qZ5MRUN7g280c2D87dw8IDvPl5E";//根据你的帐号信息修改
        String bucketName = "";//历史遗留字段, 无需修改
        ImageClient imageClient = new ImageClient(appId, secretId, secretKey, ImageClient.NEW_DOMAIN_recognition_image_myqcloud_com);
        /*文字识别系列 ( OCRTX )*/
        //通用
        Object result = ocrGeneral(imageClient, bucketName, file);
        imageClient.shutdown();
        return result;
    }

    /**
     * 通用印刷体OCR
     */
    private static Object ocrGeneral(ImageClient imageClient, String bucketName, File file) {
        String ret = null;
        System.out.println("====================================================");
        //images_test2  assets
        GeneralOcrRequest request = new GeneralOcrRequest(bucketName, file);
        try {
            ret = imageClient.generalOcr(request);
        } catch (AbstractImageException e) {
            e.printStackTrace();
        }
        System.out.println("ocrGeneral:" + ret);

        Object jsonObject = JSONObject.stringToValue(ret);
        return jsonObject;
    }
}
