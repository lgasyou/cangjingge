package cn.edu.bit.cangjingge.common.response;

public class ResponseUtil {

    private final static String EMPTY_DATA = "";

    public static <T> Response<T> success(T data) {
        Response<T> response = new Response<>();
        response.setStatus(ResponseStatusEnum.SUCCESS.getStatus());
        response.setTimestamp(timestamp());
        response.setMessage("success");
        response.setData(data);
        return response;
    }

    public static Response<String> success() {
        return success(EMPTY_DATA);
    }

    public static Response<String> error(int code, String reason) {
        Response<String> response = new Response<>();
        response.setStatus(code);
        response.setTimestamp(timestamp());
        response.setMessage("failed");
        response.setReason(reason);
        response.setData(EMPTY_DATA);
        return response;
    }

    private static long timestamp() {
        return System.currentTimeMillis() / 1000;
    }

}
