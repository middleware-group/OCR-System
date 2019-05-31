package com.midware.ocr;

public class PreProcessService {

    public void preProcessImage(String imagePath) throws Exception{
        String path = System.getProperty("user.dir");
        String exe = "python";
        String command = path+"\\src\\preprocess\\image.py";
        imagePath = imagePath.replace('\\','/');
        try {
            String[] args = new String[]{"python",command,imagePath};
            System.out.println(imagePath);
            Process pr = Runtime.getRuntime().exec(args);
            pr.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
