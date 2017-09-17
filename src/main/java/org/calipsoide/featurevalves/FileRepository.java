package org.calipsoide.featurevalves;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;

import static java.nio.channels.AsynchronousFileChannel.open;
import static reactor.core.publisher.Mono.empty;
import static reactor.core.publisher.Mono.error;

/**
 * Created by epalumbo on 9/17/17.
 */
@Repository
public class FileRepository {

    private static final int BUFFER_SIZE = 1024 * 20; // should be enough to load config files at once

    private String base;

    public FileRepository(@Value("${git.repo.base.dir}") String base) {
        this.base = base;
    }

    public Mono<ByteBuffer> load(String folder, String name) {
        final FileSystem fileSystem = FileSystems.getDefault();
        final Path path = fileSystem.getPath(base + File.separator + folder, name);
        try {
            final AsynchronousFileChannel channel = open(path);
            return DataBufferUtils
                    .read(channel, new DefaultDataBufferFactory(), BUFFER_SIZE)
                    .map(DataBuffer::asByteBuffer)
                    .next();
        } catch (NoSuchFileException e) {
            return empty();
        } catch (IOException e) {
            return error(e);
        }
    }

}
