package card.maybank.springboot_card.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class RequestResponseLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestResponseLoggingFilter.class);
    private static final int MAX_BODY = 2000;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws ServletException, IOException {
        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(request, 10_000);
        ContentCachingResponseWrapper res = new ContentCachingResponseWrapper(response);
        long start = System.currentTimeMillis();

        try {
            chain.doFilter(req, res);
        } finally {
            long ms = System.currentTimeMillis() - start;
            String query = req.getQueryString() == null ? "" : "?" + req.getQueryString();

            log.info("REQUEST  {} {}{} body={}", req.getMethod(), req.getRequestURI(), query,
                    body(req.getContentAsByteArray()));
            log.info("RESPONSE {} {}{} status={} time={}ms body={}", req.getMethod(), req.getRequestURI(), query,
                    res.getStatus(), ms, body(res.getContentAsByteArray()));

            res.copyBodyToResponse();
        }
    }

    private String body(byte[] bytes) {
        if (bytes.length == 0) return "";
        String text = new String(bytes, StandardCharsets.UTF_8);
        text = text.replaceAll("\\b\\d{13,19}\\b", "****"); // mask anything that looks like a card number
        return text.length() > MAX_BODY ? text.substring(0, MAX_BODY) + "...(truncated)" : text;
    }
}