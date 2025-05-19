package pl.complaint.app.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
@RequiredArgsConstructor
public class CountryService {

    private final WebClient webClient;

    public String resolveCountry(String ip) {
        try {
            return webClient.get()
                    .uri("{ip}", ip)
                    .retrieve()
                    .bodyToMono(IpResponse.class)
                    .blockOptional()
                    .filter(i -> i.success)
                    .map(IpResponse::getCountryCode)
                    .orElse("UNKNOWN");
        } catch (Exception e) {
            return "UNKNOWN";
        }
    }

    private record IpResponse(boolean success, String country, String country_code) {
        public String getCountryCode() {
            return country_code != null ? country_code : "UNKNOWN";
        }
    }

}
