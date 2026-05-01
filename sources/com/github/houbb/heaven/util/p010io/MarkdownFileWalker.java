package com.github.houbb.heaven.util.p010io;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class MarkdownFileWalker extends SimpleFileVisitor<Path> {
    private List<Path> pathList = new ArrayList();

    @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
    public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) {
        if (FileUtil.isMdFile(path.toString())) {
            this.pathList.add(path);
        }
        return FileVisitResult.CONTINUE;
    }

    public List<Path> getPathList() {
        return Collections.unmodifiableList(this.pathList);
    }
}
