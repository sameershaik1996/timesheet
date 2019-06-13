package us.redshift.zuul.exception;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.CharStreams;
import com.google.gson.Gson;
import com.netflix.zuul.exception.ZuulException;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.*;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.io.*;
import java.util.ArrayList;

@Component
public class RErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder = new Default();

    @Autowired
    Gson gson;

    @Override
    public Exception decode(String s, Response response)throws CustomException {

        String message = null;
        Reader reader = null;
        String result =null;
        HttpHeaders responseHeaders = new HttpHeaders();
        response.headers().entrySet().stream()
                .forEach(entry -> responseHeaders.put(entry.getKey(), new ArrayList<>(entry.getValue())));

        HttpStatus statusCode = HttpStatus.valueOf(response.status());
        String statusText = response.reason();

        byte[] responseBody=null;
        try {
            reader = response.body().asReader();
            //Easy way to read the stream and get a String object
             result = CharStreams.toString(reader);

            //System.out.println.println(result);

            ApiError error=gson.fromJson(reader, ApiError.class);
            //System.out.println.println("error: "+ error);
            responseBody = IOUtils.toByteArray(response.body().asInputStream());
            //use a Jackson ObjectMapper to convert the Json String into a
            //Pojo
            /*ObjectMapper mapper = new ObjectMapper();
            //just in case you missed an attribute in the Pojo
            mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
            //init the Pojo
            ExceptionMessage exceptionMessage = mapper.readValue(result,
                    ExceptionMessage.class);

            message = exceptionMessage.message;*/
            //message=error.getMessage();

        } catch (IOException e) {

            e.printStackTrace();
        }finally {

            //It is the responsibility of the caller to close the stream.
            try {

                if (reader != null)
                    reader.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        switch (response.status()) {

            case 404:
                return new CustomException(result);

            case 401:
                try {
                    JSONObject jsonObject = new JSONObject(result).getJSONObject("apierror");


                    ZuulException zuulException = new ZuulException(jsonObject.getString("message"), HttpStatus.UNAUTHORIZED.value(), "unauthorized access");
                    return zuulException;
                }catch (Exception e){e.printStackTrace();}



        }

        return errorDecoder.decode(s, response);
    }

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @ToString
    public static class ExceptionMessage{

        private String timestamp;
        private int status;
        private String error;
        private String message;
        private String path;

    }
}