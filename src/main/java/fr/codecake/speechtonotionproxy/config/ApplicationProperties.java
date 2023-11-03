package fr.codecake.speechtonotionproxy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final OpenAI openAI = new ApplicationProperties.OpenAI();

    private final Notion notion = new ApplicationProperties.Notion();


    public class OpenAI {

        final Whisper whisper = new OpenAI.Whisper();

        public class Whisper {
            String key = "";

            public String getKey() {
                return key;
            }

            public void setKey(String key) {
                this.key = key;
            }
        }

        public Whisper getWhisper() {
            return whisper;
        }
    }

    public class Notion {
        String key = "";


        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }
    }

    public OpenAI getOpenAI() {
        return openAI;
    }

    public Notion getNotion() {
        return notion;
    }
}
