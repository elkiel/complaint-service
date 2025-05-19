package pl.complaint.app.util;

import jakarta.servlet.http.HttpServletRequest;

public class HttpReqRespUtils {

    public static final String UNKNOWN_IP = "UNKNOWN";
    private static final String HEADER_X_FORWARDED_FOR = "X-Forwarded-For";
    private static final String COMMA = ",";

    public static String resolveClientIp(HttpServletRequest request) {
        if (request == null) {
            return UNKNOWN_IP;
        }

        String ip = request.getHeader(HEADER_X_FORWARDED_FOR);
        if (ip == null || ip.isBlank() || UNKNOWN_IP.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        } else {
            ip = ip.split(COMMA)[0].trim();
        }

        return ip != null && !ip.isBlank() ? ip : UNKNOWN_IP;
    }

}
