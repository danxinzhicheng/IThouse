package com.danmo.commonapi.converter;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Converter;


public class StringResponseBodyConverter implements Converter<ResponseBody, String> {
    @Override
    public String convert(ResponseBody value) throws IOException {
        try {
            return value.string();
        } finally {
            value.close();
        }
    }
}
