package com.trade.controller;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DefaultDataBuffer;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.*;
import org.springframework.http.client.MultipartBodyBuilder;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@RestController
@RequestMapping("/file-upload")
@Slf4j
public class FileController {

    final List<String> PATHS = List.of("/Users/a.yaml","/Users/b.yaml");

    @PostMapping(path = "/return",consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Flux<FilePart> upload(@RequestPart final Flux<FilePart> files) {
        return files;
    }

    @GetMapping(produces = "application/zip")
    public Mono<DefaultDataBuffer> read()  {

//        return Flux.fromIterable(PATHS).flatMap(path-> {
//            try {
//
//                Mono<byte[]> fileMono = Mono.just(FileCopyUtils.copyToByteArray(new File(path)));
//                return fileMono.map(file-> {
//
//                    MultipartBodyBuilder builder = new MultipartBodyBuilder();
//                    builder.asyncPart("document", filePart.content(), DataBuffer.class).filename(filePart.filename());
//                    Map map = new HashMap();
//                    map.put("name", filePart.filename());
//                    map.put("content", filePart.content());
//                    return map;
//                });
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            }
//        });
        var responseDataBuffer = new DefaultDataBufferFactory().allocateBuffer(10);
        ZipOutputStream zipOutputStream = new ZipOutputStream(responseDataBuffer.asOutputStream());
        Mono<DefaultDataBuffer> buffer =  Flux.fromIterable(PATHS)
                .map(file -> putZipEntry(file, zipOutputStream))
                .then(Mono.fromCallable(()-> {
            closeZipOutputStream(zipOutputStream);
            return responseDataBuffer;
        }));

        return buffer;
    }

    @GetMapping(value = "/somepath/pdf",produces = "application/zip" )
    public ResponseEntity<?> generatePDF() {
        BinaryOutputWrapper output = new BinaryOutputWrapper();
        try {
            output = prepDownloadAsZIP();
            //or invoke prepDownloadAsZIP(...) with a list of filenames
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(output.getData(), output.getHeaders(), HttpStatus.OK);
    }

    private ZipOutputStream putZipEntry(String file, ZipOutputStream zipOutputStream) {
        try {
            zipOutputStream.putNextEntry(new ZipEntry(file));
            zipOutputStream.write(file.getBytes());
            zipOutputStream.closeEntry();
            return zipOutputStream;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private void closeZipOutputStream(ZipOutputStream zipOutputStream) {
        try {
            zipOutputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public BinaryOutputWrapper prepDownloadAsZIP() throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("application/zip"));
        String outputFilename = "output.zip";
        headers.setContentDispositionFormData(outputFilename, outputFilename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");

        ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream();
        ZipOutputStream zipOutputStream = new ZipOutputStream(byteOutputStream);

        for(String filename: PATHS) {
            File file = new File(filename);
            zipOutputStream.putNextEntry(new ZipEntry(filename));
            FileInputStream fileInputStream = new FileInputStream(file);
            IOUtils.copy(fileInputStream, zipOutputStream);
            fileInputStream.close();
            zipOutputStream.closeEntry();
        }
        zipOutputStream.close();
        return new BinaryOutputWrapper(byteOutputStream.toByteArray(), headers);
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Data
    class BinaryOutputWrapper{
        private byte[] data;
        private HttpHeaders headers;
    }
}
