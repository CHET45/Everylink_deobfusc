package com.github.houbb.heaven.util.p010io.filewalker;

import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* JADX INFO: loaded from: classes3.dex */
public class AllFileVisitor extends SimpleFileVisitor<Path> {
    private List<Path> pathList = new ArrayList();

    @Override // java.nio.file.SimpleFileVisitor, java.nio.file.FileVisitor
    public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) {
        this.pathList.add(path);
        return FileVisitResult.CONTINUE;
    }

    public List<Path> getPathList() {
        return Collections.unmodifiableList(this.pathList);
    }
}
