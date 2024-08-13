package entities.response;

import entities.request.Headers;

public class PDFResponse {

    private Headers headers;
    private Body body;

    public PDFResponse() {
    }

    public PDFResponse(Headers headers, Body body) {
        this.headers = headers;
        this.body = body;
    }

    public Headers getHeaders() {
        return headers;
    }

    public void setHeaders(Headers headers) {
        this.headers = headers;
    }

    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public static class Body {

        private String encode;
        private String failure;
        private String code;

        public Body(String encode, String failure, String code) {
            this.encode = encode;
            this.failure = failure;
            this.code = code;
        }

        public String getEncode() {
            return encode;
        }

        public void setEncode(String encode) {
            this.encode = encode;
        }

        public String getFailure() {
            return failure;
        }

        public void setFailure(String failure) {
            this.failure = failure;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
