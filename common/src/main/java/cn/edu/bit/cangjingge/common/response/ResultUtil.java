package cn.edu.bit.cangjingge.common.response;

public class ResultUtil {

    private final static String EMPTY_DATA = "";

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<>();
        result.setStatus(ResultStatusEnum.SUCCESS.getStatus());
        result.setTimestamp(timestamp());
        result.setMessage("success");
        result.setData(object);
        return result;
    }

    public static Result<String> success() {
        return success(EMPTY_DATA);
    }

    public static Result<String> error(int code, String reason) {
        Result<String> result = new Result<>();
        result.setStatus(code);
        result.setTimestamp(timestamp());
        result.setMessage("failed");
        result.setReason(reason);
        result.setData(EMPTY_DATA);
        return result;
    }

    private static long timestamp() {
        return System.currentTimeMillis() / 1000;
    }

}
