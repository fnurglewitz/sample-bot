package it.pwned.telegram.samplebot.imgur;

import it.pwned.telegram.samplebot.imgur.type.GalleryImage;
import it.pwned.telegram.samplebot.imgur.type.ImgurResponse;
import it.pwned.telegram.samplebot.imgur.type.SubredditSort;
import it.pwned.telegram.samplebot.imgur.type.SubredditWindow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriTemplate;

import java.util.*;

public class ImgurClient {

    private static final Logger log = LoggerFactory.getLogger(ImgurClient.class);

    private final String clientId;
    private final RestTemplate rest;

    private static final UriTemplate subredditUri = new UriTemplate("https://api.imgur.com/3/gallery/r/{subreddit}/{sort}/{window}/{page}");

    public ImgurClient(String clientId, RestTemplate rest) {
        this.clientId = clientId;
        this.rest = rest;
    }

    public List<GalleryImage> subredditGallery(String subreddit, SubredditSort sort, int page, SubredditWindow window) {

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Client-ID " + this.clientId);

        Map<String, String> uriVariables = new HashMap<>();
        uriVariables.put("subreddit", subreddit);
        uriVariables.put("sort", sort.toString());
        uriVariables.put("window", window.toString());
        uriVariables.put("page", Integer.toString(page));

        HttpEntity<?> httpEntity = new HttpEntity<Object>(body, headers);

        ImgurResponse<GalleryImage> res;

        try {
            res = rest.exchange(subredditUri.expand(uriVariables), HttpMethod.GET, httpEntity,
                    new ParameterizedTypeReference<ImgurResponse<GalleryImage>>() {
                    }).getBody();

        } catch (RestClientException e) {
            int status = -1;

            if (e instanceof HttpClientErrorException) {
                status = ((HttpClientErrorException) e).getStatusCode().value();
            }

            res = new ImgurResponse<>(new GalleryImage[0], false, status);
        }

        return Collections.unmodifiableList(Arrays.asList(res.data));
    }

}
