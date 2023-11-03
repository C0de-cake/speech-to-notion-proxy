package fr.codecake.speechtonotionproxy.client;

import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

public interface HttpService {
    @PostExchange(value = "https://api.notion.com/v1/pages", contentType = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> createNotionPage(@RequestHeader("Authorization") String bearerToken,
                                 @RequestHeader("Notion-Version") String notionVersion,
                                 @RequestBody String json);

    @PostExchange(value = "https://api.openai.com/v1/audio/transcriptions", contentType = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<String> transcriptAudioToText(@RequestHeader("Authorization") String bearerToken,
                                                @RequestPart(name = "file") FileSystemResource file,
                                                @RequestPart(name = "model") String model,
                                                @RequestPart(name = "response_format") String responseFormat);

}
