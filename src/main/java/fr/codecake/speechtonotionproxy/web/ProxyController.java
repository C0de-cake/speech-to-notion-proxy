package fr.codecake.speechtonotionproxy.web;


import fr.codecake.speechtonotionproxy.client.HttpService;
import fr.codecake.speechtonotionproxy.utils.FileUtility;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.support.WebClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;
import reactor.netty.http.client.HttpClient;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class ProxyController {

    private final Logger log = LoggerFactory.getLogger(ProxyController.class);

    @Value("${application.notion.key}")
    private String notionAuthToken;

    @Value("${application.openai.whisper.key}")
    private String openAIAuthToken;
    private final HttpService speechToTextNotionAPIService;

    public ProxyController(HttpService speechToTextNotionAPIService) {
        this.speechToTextNotionAPIService = speechToTextNotionAPIService;
    }

    @RequestMapping(value = "/v1/notion/pages", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> createNotionPage(@RequestBody String requestBody) {

        ResponseEntity<String> notionPage = speechToTextNotionAPIService
                .createNotionPage("Bearer " + notionAuthToken,
                "2022-06-28", requestBody);

        return ResponseEntity
                .status(notionPage.getStatusCode())
                .body(notionPage.getBody());
    }

    @RequestMapping(value = "/v1/audio/transcriptions", method = RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> translateTextToSpeech(@RequestPart(name = "file") MultipartFile multipartFile,
                                                        @RequestPart(name = "model") String model,
                                                        @RequestPart(name = "response_format") String responseFormat) throws IOException {

        FileSystemResource file = new FileSystemResource(FileUtility.multipartFileToFile(multipartFile));
        ResponseEntity<String> translatedAudioResponse = speechToTextNotionAPIService
                .transcriptAudioToText("Bearer " + openAIAuthToken, file, model, responseFormat);

        return ResponseEntity
                .status(translatedAudioResponse.getStatusCode())
                .body(translatedAudioResponse.getBody());
    }
}
