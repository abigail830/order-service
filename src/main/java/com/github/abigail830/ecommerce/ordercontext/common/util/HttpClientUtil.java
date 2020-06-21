package com.github.abigail830.ecommerce.ordercontext.common.util;

import io.micrometer.core.instrument.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import org.springframework.http.HttpMethod;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class HttpClientUtil {

    private final static long DEFAULT_CONNECT_TIMEOUT = 60;
    private final static long DEFAULT_READ_TIMEOUT = 120;
    private final static ConnectionPool connectionPool = new ConnectionPool(50, 5, TimeUnit.MINUTES);

    private HttpClientUtil() {
    }

    public static HttpClientUtil instance() {
        return HttpUtilHolder.getClient();
    }

    public String getData(String url) {
        try {
            return this.send(url, HttpMethod.GET, null, null);
        } catch (IOException e) {
            log.error("exception occur while doing http get", e);
            throw new RuntimeException(e);
        }
    }

    public Response putBinary(String urlString, Map<String, String> params, Map<String, String> headers, byte[] content) throws IOException {
        OkHttpClient httpClient = buildHttpClient(urlString, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
        HttpUrl.Builder urlBuilder = HttpUrl.parse(urlString).newBuilder();
        setUrlParam(params, urlBuilder);
        HttpUrl url = urlBuilder.build();

        Request.Builder requestBuilder = new Request.Builder()
                .put(RequestBody.create(MediaType.parse(org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE), content))
                .url(url);

        if (headers != null) {
            for (Map.Entry<String, String> header : headers.entrySet()) {
                if (keyValueBothNotNull(header)) {
                    requestBuilder.addHeader(header.getKey(), header.getValue());
                }
            }
        }
        log.info("Request url is :{}, headers are :{}, params are :{}", url.toString(), params);

        return httpClient.newCall(requestBuilder.build()).execute();
    }

    public Response postBody(String urlString, String body, Map<String, String> headers)
            throws IOException {
        OkHttpClient httpClient = buildHttpClient(urlString, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
        HttpUrl url = HttpUrl.parse(urlString);
        Request.Builder requestBuilder = new Request.Builder()
                .url(url);

        setHeader(headers, requestBuilder);

        requestBuilder.post(RequestBody.create(
                MediaType.parse(headers != null && headers.containsKey("Content-Type")
                        ? headers.get("Content-Type") : "text/plain; charset=utf-8"), body));

        log.info("Request url is :{}, body is :{}", url.toString(), body);
        return httpClient.newCall(requestBuilder.build()).execute();
    }

    public Response putBody(String urlString, String body, Map<String, String> headers)
            throws IOException {
        OkHttpClient httpClient = buildHttpClient(urlString, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
        HttpUrl url = HttpUrl.parse(urlString);
        Request.Builder requestBuilder = new Request.Builder()
                .url(url);

        setHeader(headers, requestBuilder);

        requestBuilder.put(RequestBody.create(
                MediaType.parse(headers != null && headers.containsKey("Content-Type")
                        ? headers.get("Content-Type") : "text/plain; charset=utf-8"), body));

        log.info("Request url is :{}, body is :{}", url.toString(), body);
        return httpClient.newCall(requestBuilder.build()).execute();
    }


    public String send(String urlString, HttpMethod method,
                       Map<String, String> parameters, Map<String, String> headers)
            throws IOException {
        OkHttpClient httpClient = buildHttpClient(urlString, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
        Response response = sendHttpReq(urlString, method, parameters, headers, httpClient);
        return response.body().string();
    }

    private Response sendHttpReq(String urlString, HttpMethod method, Map<String, String> parameters,
                                 Map<String, String> headers, OkHttpClient httpClient) throws IOException {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(urlString).newBuilder();
        if (method != HttpMethod.POST && parameters != null) {
            setUrlParam(parameters, urlBuilder);
        }
        HttpUrl url = urlBuilder.build();

        Request.Builder requestBuilder = new Request.Builder()
                .url(url).addHeader("Content-Type", "text/plain; charset=utf-8");
        setHeader(headers, requestBuilder);

        if (method == HttpMethod.POST && parameters != null) {
            setFormBody(parameters, requestBuilder);
        }
        log.info("Request url is :{}, headers are :{}, params are :{}", url.toString(), parameters);
        return httpClient.newCall(requestBuilder.build()).execute();
    }

    private void setHeader(Map<String, String> headers, Request.Builder requestBuilder) {
        if (headers != null) {
            if (!headers.containsKey("Content-Type")) {
                requestBuilder.addHeader("Content-Type", "text/plain; charset=utf-8");
            }
            for (Map.Entry<String, String> header : headers.entrySet()) {
                if (keyValueBothNotNull(header)) {
                    requestBuilder.addHeader(header.getKey(), header.getValue());
                }
            }
        }
    }

    private void setFormBody(Map<String, String> parameters, Request.Builder requestBuilder) {
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        for (Map.Entry<String, String> param : parameters.entrySet()) {
            if (param.getValue() != null && param.getKey() != null) {
                bodyBuilder.add(param.getKey(), param.getValue());
            }
        }
        requestBuilder.post(bodyBuilder.build());
    }

    private void setUrlParam(Map<String, String> parameters, HttpUrl.Builder urlBuilder) {
        for (Map.Entry<String, String> param : parameters.entrySet()) {
            if (keyValueBothNotNull(param)) {
                urlBuilder.addQueryParameter(param.getKey(), param.getValue());
            }
        }
    }

    private boolean keyValueBothNotNull(Map.Entry<String, String> param) {
        return param.getKey() != null && param.getValue() != null;
    }

    public OkHttpClient buildHttpClient(String urlString, long connectTimeout, long readTimeOut) {
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .connectionPool(connectionPool)
                .retryOnConnectionFailure(true)
                .connectTimeout(connectTimeout, TimeUnit.SECONDS)
                .readTimeout(readTimeOut, TimeUnit.SECONDS);
        if (StringUtils.isNotBlank(urlString) && urlString.startsWith("https")) {
            try {
                SSLContext ctx = SSLContext.getInstance("SSL");
                X509TrustManager tm = new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] xcs,
                                                   String string) throws CertificateException {
                    }

                    public void checkServerTrusted(X509Certificate[] xcs,
                                                   String string) throws CertificateException {
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return new X509Certificate[0];
                    }
                };
                ctx.init(null, new TrustManager[]{tm}, null);
                httpClientBuilder.sslSocketFactory(ctx.getSocketFactory(), tm);
            } catch (KeyManagementException | NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
        return httpClientBuilder.build();
    }

    private final static class HttpUtilHolder {
        private final static HttpClientUtil INSTANCE = new HttpClientUtil();

        private static HttpClientUtil getClient() {
            return INSTANCE;
        }
    }
}
