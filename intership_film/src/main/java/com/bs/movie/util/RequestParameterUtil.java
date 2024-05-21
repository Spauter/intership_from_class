package com.bs.movie.util;



import com.bs.movie.core.Part;
import com.bs.movie.core.RequestParam;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

public class RequestParameterUtil {
    private byte[] saveUploadData;
    private int currentIndex;
    private int contentLength;

    public static RequestParam requestParam(HttpServletRequest request) throws IOException {
        return new RequestParameterUtil().parseRequestParam(request);
    }

    private RequestParam parseRequestParam(HttpServletRequest request) throws IOException {
      saveUploadData = getSaveUploadData(request);
      switch (request.getContentType().split(";")[0]) {
          case "multipart/form-data":
              return parseRequestParam(request);

          case "text/plain":
              return parseRequestParam(request);
          case "application/json":
              break;
          default:
              //TODO
              return null;

      }
        return null;
    }

    private byte[] getSaveUploadData(HttpServletRequest request) throws IOException {
        contentLength=request.getContentLength();
        ServletInputStream inputStream=request.getInputStream();
        if (contentLength==0) {
            return null;
        }
        byte[] saveUploadData=new byte[contentLength];
        int totalRead=0;
        int readLength=0;
        for (;totalRead<contentLength;totalRead+=readLength) {
            readLength=inputStream.read(saveUploadData,totalRead,contentLength-totalRead);
        }
        return saveUploadData;
    }

    private RequestParam parseMultipart() {
        Map<String, List<Part>>stringListMap=new Hashtable<>();
        Map<String,String> stringStringMap=new Hashtable<>();
        List<Part>parts=null;
        String boundStr=readBoundary();
        currentIndex+=2;
        String formInfo;
        byte[] data;
        do {
            if (currentIndex>=contentLength) {
                break;
            }

        }while(boundStr.length()>0);
        return null;
    }

    String readBoundary() {
        StringBuilder boundary=new StringBuilder();
        for (;currentIndex<saveUploadData.length;currentIndex++) {
            if (saveUploadData[currentIndex] == 13) {
                return boundary.toString();
            }
            boundary.append((char)saveUploadData[currentIndex]);
        }
        return boundary.toString();
    }

    private String readFormName() {
        int start=currentIndex;
        boolean check=false;
        int endData=0;
        while (!check) {
            if (saveUploadData[start] == 13 && saveUploadData[currentIndex + 2] == 13) {
                check = true;
                endData += 4;
            } else {
                ++ currentIndex;
            }
        }
        return new String(saveUploadData, start, endData-start);
    }

    private byte[] readFromData(String boundary) {
        int start=currentIndex;
        int boundIndex=0;
        int boundLen=boundary.length();
        while (currentIndex<contentLength) {
            if (saveUploadData[currentIndex] == (byte) boundary.charAt(boundIndex)) {

            }
        }
        return null;
    }
}
