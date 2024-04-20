package com.tugalsan.tst.jgit;

import com.tugalsan.api.file.server.TS_DirectoryUtils;
import com.tugalsan.api.jgit.server.TS_JGitUtils;
import com.tugalsan.api.log.server.TS_Log;
import java.nio.file.Path;
import java.util.List;

public class Main {

    private static final TS_Log d = TS_Log.of(Main.class);

    //cd C:\me\codes\com.tugalsan\tst\com.tugalsan.tst.jgit
    //java --enable-preview --add-modules jdk.incubator.vector -jar target/com.tugalsan.tst.jgit-1.0-SNAPSHOT-jar-with-dependencies.jar
    //java -jar target/com.tugalsan.tst.jgit-1.0-SNAPSHOT-jar-with-dependencies.jar
    public static void main(String... args) {
        d.cr("main", "workSpaces", "------------------------------------------");
        var workSpaces = List.of("api", "lib", "app", "spi")
                .stream()
                .map(s -> Path.of("D:/git/" + s))
                .toList();
        workSpaces.forEach(ws -> d.cr("main", "workSpaces", "------------------------------------------"));
        workSpaces.forEach(ws -> d.cr("main", "workSpace", ws));

        d.cr("main", "repositories", "------------------------------------------");
        var repositories = workSpaces.stream()
                .flatMap(p -> TS_DirectoryUtils.subDirectories(p, true, false).stream())
                .toList();
        repositories.forEach(rep -> d.cr("main", "repository", rep));

        d.cr("main", "branchNames", "------------------------------------------");
        repositories.forEach(repo -> {
            var repoName = TS_DirectoryUtils.getName(repo);
            var u = TS_JGitUtils.listBranchNames(repo);
            if (u.isExcuse()) {
                d.ce("main", "branchNames", repoName, u.excuse().getMessage());
            } else {
                d.cr("main", "branchNames", repoName);
                u.value().stream().map(b -> b.name()).toList().forEach(name -> {
                    d.cr("main", "branchNames", "-", name);
                });
            }
        });

    }
}
