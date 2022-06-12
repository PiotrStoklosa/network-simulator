package com.nokia.uwr.utility;

import lombok.experimental.UtilityClass;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UncheckedIOException;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author Piotr Stoklosa
 */
@UtilityClass
public class ResourceHandler {

    private final ResourceLoader resourceLoader = new DefaultResourceLoader();
    private final String CLASS_PATH = "classpath:";

    public String loadResource(String path){
        Resource resource = resourceLoader.getResource(CLASS_PATH + path);

        try (Reader reader = new InputStreamReader(resource.getInputStream(), UTF_8)) {
            return FileCopyUtils.copyToString(reader);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }

}
