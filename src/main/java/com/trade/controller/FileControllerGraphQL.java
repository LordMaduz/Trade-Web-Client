package com.trade.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.http.MediaType;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Controller
@Slf4j
public class FileControllerGraphQL {


    @MutationMapping
    public FileUploadResult fileUpload(@Argument FilePart file) {

        log.info("Upload file: name={}", file.filename());
        Flux<String> stringFlux = getFileContent(file);
        stringFlux.subscribe(string-> {
            log.info("file Content: {}", string);
        });

        return new FileUploadResult(UUID.randomUUID());
    }

    @MutationMapping
    public Collection<FileUploadResult> multiFileUpload(@Argument Collection<FilePart> files) {

        for (FilePart filePart: files) {
            log.info("Upload file: name={}", filePart.filename());
        }
        return List.of(new FileUploadResult(UUID.randomUUID()));
    }

    private Flux<String> getFileContent(final FilePart filePart) {
        return filePart.content().map(dataBuffer -> {
            byte[] bytes = new byte[dataBuffer.readableByteCount()];
            dataBuffer.read(bytes);
            DataBufferUtils.release(dataBuffer);
            return new String(bytes, StandardCharsets.UTF_8);
        });

    }

}

class FileUploadResult {
    UUID id;

    public FileUploadResult(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
